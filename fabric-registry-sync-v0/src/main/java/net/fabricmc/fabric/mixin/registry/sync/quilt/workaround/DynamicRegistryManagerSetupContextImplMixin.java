/*
 * Copyright 2023 The Quilt Project
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

package net.fabricmc.fabric.mixin.registry.sync.quilt.workaround;

import java.util.Map;
import java.util.Optional;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.registry.MutableRegistry;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

import net.fabricmc.fabric.api.event.registry.DynamicRegistryView;

import org.quiltmc.qsl.registry.impl.DynamicRegistryManagerSetupContextImpl;

@Mixin(DynamicRegistryManagerSetupContextImpl.class)
public abstract class DynamicRegistryManagerSetupContextImplMixin implements DynamicRegistryView {
	@Shadow(remap = false)
	@Final
	private Map<RegistryKey<?>, MutableRegistry<?>> registries;

	// We duplicate the logic here in order to have it be the implementation of Fabric's version of getOptional
	@Override
	public <E> Optional<Registry<E>> getOptional(RegistryKey<? extends Registry<? extends E>> key) {
		return Optional.ofNullable((Registry) this.registries.get(key)).map(registry -> registry);
	}
}
