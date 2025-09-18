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

package net.fabricmc.fabric.impl.registry.sync;

import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;

import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.fabricmc.fabric.api.event.registry.RegistryAttributeHolder;

import org.quiltmc.qsl.registry.impl.sync.registry.SynchronizedRegistry;

public final class RegistryAttributeImpl implements RegistryAttributeHolder {
	private static final Map<RegistryKey<?>, RegistryAttributeHolder> HOLDER_MAP = new ConcurrentHashMap<>();
	private final RegistryKey<?> registryKey;

	public static RegistryAttributeHolder getHolder(RegistryKey<?> registryKey) {
		return HOLDER_MAP.computeIfAbsent(registryKey, RegistryAttributeImpl::new);
	}

	private final EnumSet<RegistryAttribute> attributes = EnumSet.noneOf(RegistryAttribute.class);

	private RegistryAttributeImpl(RegistryKey<?> registryKey) {
		this.registryKey = registryKey;
	}

	@Override
	public RegistryAttributeHolder addAttribute(RegistryAttribute attribute) {
		attributes.add(attribute);
		return this;
	}

	@Override
	public boolean hasAttribute(RegistryAttribute attribute) {
		if (attributes.contains(attribute)) {
			return true;
		}

		SynchronizedRegistry<?> registry = (SynchronizedRegistry<?>) Registries.REGISTRIES.get((RegistryKey) this.registryKey);
		return switch (attribute) {
		case MODDED -> registry.quilt$getContentStatus() != SynchronizedRegistry.Status.VANILLA;
		case SYNCED -> registry.quilt$requiresSyncing();
		};
	}
}
