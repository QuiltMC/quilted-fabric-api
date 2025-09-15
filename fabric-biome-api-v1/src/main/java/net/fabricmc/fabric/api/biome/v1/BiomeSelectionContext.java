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

package net.fabricmc.fabric.api.biome.v1;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

/**
 * Context given to a biome selector for deciding whether it applies to a biome or not.
 *
 * @deprecated see {@link org.quiltmc.qsl.worldgen.biome.api.BiomeSelectionContext}.
 */
@Deprecated
public interface BiomeSelectionContext extends org.quiltmc.qsl.worldgen.biome.api.BiomeSelectionContext {
	default RegistryEntry<Biome> getBiomeRegistryEntry() {
		return this.getBiomeHolder();
	}

	/**
	 * {@return true if this biome is in the given {@link TagKey}}.
	 */
	default boolean hasTag(TagKey<Biome> tag) {
		return this.isIn(tag);
	}
}
