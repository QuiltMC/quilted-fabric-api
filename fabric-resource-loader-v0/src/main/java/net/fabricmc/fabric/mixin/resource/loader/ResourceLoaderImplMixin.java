/*
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

package net.fabricmc.fabric.mixin.resource.loader;

import java.util.List;

import com.llamalad7.mixinextras.sugar.Local;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.registry.RegistryWrapper;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;

import org.quiltmc.qsl.base.api.phase.PhaseData;
import org.quiltmc.qsl.resource.loader.impl.ResourceLoaderImpl;

@Mixin(ResourceLoaderImpl.class)
public class ResourceLoaderImplMixin {
	@Inject(
			method = "sort(Ljava/util/List;Lnet/minecraft/registry/RegistryWrapper$WrapperLookup;)V",
			at = @At(
					value = "INVOKE",
					target = "Lit/unimi/dsi/fastutil/objects/Object2ObjectOpenHashMap;values()Lit/unimi/dsi/fastutil/objects/ObjectCollection;",
					ordinal = 0
			)
	)
	public void addFabricOrdering(List<ResourceReloader> reloaders, RegistryWrapper.WrapperLookup provider, CallbackInfo ci, @Coerce @Local Object2ObjectOpenHashMap<Identifier, Object> runtimePhases) {
		for (ResourceReloader reloader : reloaders) {
			if (reloader instanceof IdentifiableResourceReloadListener fabric) {
				Object second = runtimePhases.get(fabric.getQuiltId());

				if (second != null) {
					for (Identifier fabricDependency : fabric.getFabricDependencies()) {
						Object first = runtimePhases.get(fabricDependency);

						if (first != null) {
							PhaseData.link((PhaseData) first, (PhaseData) second);
						}
					}
				}
			}
		}
	}
}
