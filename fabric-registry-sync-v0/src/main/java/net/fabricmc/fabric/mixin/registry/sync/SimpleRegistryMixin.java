/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 * Copyright 2022 The Quilt Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.fabric.mixin.registry.sync;

import java.util.Collection;

import com.mojang.serialization.Lifecycle;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.registry.MutableRegistry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.event.registry.RegistryIdRemapCallback;
import net.fabricmc.fabric.impl.base.event.QuiltCompatEvent;
import net.fabricmc.fabric.impl.registry.sync.ListenableRegistry;

import org.quiltmc.qsl.registry.api.event.RegistryEvents;
import org.quiltmc.qsl.registry.impl.event.MutableRegistryEntryContextImpl;
import org.quiltmc.qsl.registry.impl.sync.registry.SynchronizedRegistry;

@Mixin(value = {SimpleRegistry.class}, priority = 1001)
public abstract class SimpleRegistryMixin<T> implements MutableRegistry<T>, ListenableRegistry<T> {
	@Unique
	private Event<RegistryEntryAddedCallback<T>> fabric_addObjectEvent;

	@Unique
	private Event<RegistryIdRemapCallback<T>> fabric_postRemapEvent;

	@Override
	public Event<RegistryEntryAddedCallback<T>> fabric_getAddObjectEvent() {
		return fabric_addObjectEvent;
	}

	@Override
	public Event<RegistryIdRemapCallback<T>> fabric_getRemapEvent() {
		return fabric_postRemapEvent;
	}

	@Inject(method = "<init>(Lnet/minecraft/registry/RegistryKey;Lcom/mojang/serialization/Lifecycle;Z)V", at = @At("TAIL"))
	private void init(RegistryKey key, Lifecycle lifecycle, boolean intrusive, CallbackInfo ci) {
		fabric_addObjectEvent = QuiltCompatEvent.fromQuilt(
				RegistryEvents.getEntryAddEvent(this),
				callback -> context -> callback.onEntryAdded(context.rawId(), context.id(), context.value()),
				invokerGetter -> (rawId, id, value) -> {
					MutableRegistryEntryContextImpl<T> context = new MutableRegistryEntryContextImpl<>(this);
					context.set(id, value, rawId);
					invokerGetter.get().onAdded(context);
				}
		);
		fabric_postRemapEvent = EventFactory.createArrayBacked(RegistryIdRemapCallback.class,
			(callbacks) -> (a) -> {
				for (RegistryIdRemapCallback<T> callback : callbacks) {
					callback.onRemap(a);
				}
			}
		);
	}

	@Inject(method = "quilt$applySyncMap", remap = false, at = @At("TAIL"))
	private void quilt$applySyncMap(CallbackInfoReturnable<Collection<SynchronizedRegistry.MissingEntry>> ci) {
		// TODO: how many people actually use the information from the state?
		fabric_postRemapEvent.invoker().onRemap(new RegistryIdRemapCallback.RemapState<T>() {
			@Override
			public Int2IntMap getRawIdChangeMap() {
				throw new UnsupportedOperationException("QFAPI does not support the remap state. If this breaks your mod please submit an issue");
			}

			@Override
			public Identifier getIdFromOld(int oldRawId) {
				throw new UnsupportedOperationException("QFAPI does not support the remap state. If this breaks your mod please submit an issue");
			}

			@Override
			public Identifier getIdFromNew(int newRawId) {
				throw new UnsupportedOperationException("QFAPI does not support the remap state. If this breaks your mod please submit an issue");
			}
		});
	}
}
