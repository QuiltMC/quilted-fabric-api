/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
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

package net.fabricmc.fabric.impl.resource.loader;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import net.minecraft.registry.RegistryWrapper;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;

import org.quiltmc.qsl.resource.loader.api.ResourceLoader;

public class ResourceManagerHelperImpl implements ResourceManagerHelper {
	private static final Map<ResourceType, ResourceManagerHelperImpl> registryMap = new HashMap<>();

	private final ResourceLoader quiltLoader;

	private ResourceManagerHelperImpl(ResourceType type) {
		this.quiltLoader = ResourceLoader.get(type);
	}

	public static ResourceManagerHelperImpl get(ResourceType type) {
		return registryMap.computeIfAbsent(type, ResourceManagerHelperImpl::new);
	}

	@Override
	public void registerReloadListener(IdentifiableResourceReloadListener listener) {
		this.quiltLoader.registerReloader(listener);
	}

	@Override
	public void registerReloadListener(Identifier identifier, Function<RegistryWrapper.WrapperLookup, IdentifiableResourceReloadListener> listenerFactory) {
		this.quiltLoader.registerReloader(identifier, listenerFactory::apply);
	}
}
