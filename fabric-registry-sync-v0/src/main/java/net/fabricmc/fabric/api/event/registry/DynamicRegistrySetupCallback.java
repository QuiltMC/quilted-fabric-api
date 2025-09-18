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

package net.fabricmc.fabric.api.event.registry;

import org.jetbrains.annotations.NotNull;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.resource.ResourceManager;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.impl.base.event.QuiltCompatEvent;

import org.quiltmc.qsl.registry.api.event.DynamicRegistryManagerSetupContext;
import org.quiltmc.qsl.registry.api.event.RegistryEvents;

/**
 * This event gets triggered before a dynamic registry is being loaded.
 * Therefore, this is the ideal place to register callbacks to dynamic registries.
 * For example, the following code is used to register a callback that gets triggered for any registered Biome:
 *
 * <pre>
 * {@code
 * DynamicRegistrySetupCallback.EVENT.register(registryView -> {
 *     registryView.registerEntryAdded(RegistryKeys.BIOME, (rawId, id, object) -> {
 *         // Do something
 *     });
 * });
 * }
 * </pre>
 *
 * @see DynamicRegistryView
 * @see net.minecraft.registry.ServerDynamicRegistryType
 *
 * @deprecated see {@link org.quiltmc.qsl.registry.api.event.RegistryEvents.DynamicRegistrySetupCallback}
 * @see org.quiltmc.qsl.registry.api.event.RegistryEvents#DYNAMIC_REGISTRY_SETUP
 */
@Deprecated
@FunctionalInterface
public interface DynamicRegistrySetupCallback {
	void onRegistrySetup(DynamicRegistryView registryView);

	Event<DynamicRegistrySetupCallback> EVENT = QuiltCompatEvent.fromQuilt(
			RegistryEvents.DYNAMIC_REGISTRY_SETUP,
			onDynamicRegistrySetupCallback -> context -> onDynamicRegistrySetupCallback.onRegistrySetup((DynamicRegistryView) context),
			invokerGetter -> view -> invokerGetter.get().onDynamicRegistrySetup(new DynamicRegistryManagerSetupContext() {
				@Override
				public @NotNull DynamicRegistryManager registryManager() {
					return view.asDynamicRegistryManager();
				}

				// In theory? This lossy conversion should be fine since only Fabric API invokes this event.
				// A fix should be investigated though if shenanigans happen.
				@Override
				public @NotNull ResourceManager resourceManager() {
					return null;
				}
			})
	);
}
