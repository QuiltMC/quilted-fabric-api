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

import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

/**
 * Provides several convenient biome selectors that can be used with {@link BiomeModifications}.
 *
 * @deprecated see {@link org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors}.
 */
@Deprecated
public final class BiomeSelectors {
	private BiomeSelectors() {
	}

	/**
	 * Matches all Biomes. Use a more specific selector if possible.
	 */
	public static Predicate<BiomeSelectionContext> all() {
		return context -> org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors.all().test(context);
	}

	/**
	 * Returns a biome selector that will match all biomes from the minecraft namespace.
	 */
	public static Predicate<BiomeSelectionContext> vanilla() {
		return context -> org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors.foundInTheEnd().test(context);
	}

	/**
	 * Returns a biome selector that will match all biomes that would normally spawn in the Overworld,
	 * assuming Vanilla's default biome source is used.
	 */
	public static Predicate<BiomeSelectionContext> foundInOverworld() {
		return context -> org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors.foundInOverworld().test(context);
	}

	/**
	 * Returns a biome selector that will match all biomes that would normally spawn in the Nether,
	 * assuming Vanilla's default multi noise biome source with the nether preset is used.
	 *
	 * <p>This selector will also match modded biomes that have been added to the nether using {@link NetherBiomes}.
	 */
	public static Predicate<BiomeSelectionContext> foundInTheNether() {
		return context -> org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors.foundInTheNether().test(context);
	}

	/**
	 * Returns a biome selector that will match all biomes that would normally spawn in the End,
	 * assuming Vanilla's default End biome source is used.
	 */
	public static Predicate<BiomeSelectionContext> foundInTheEnd() {
		return context -> org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors.foundInTheEnd().test(context);
	}

	/**
	 * Returns a biome selector that will match all biomes in the given tag.
	 *
	 * @see net.minecraft.registry.tag.BiomeTags
	 */
	public static Predicate<BiomeSelectionContext> tag(TagKey<Biome> tag) {
		return context -> org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors.isIn(tag).test(context);
	}

	/**
	 * @see #excludeByKey(Collection)
	 */
	@SafeVarargs
	public static Predicate<BiomeSelectionContext> excludeByKey(RegistryKey<Biome>... keys) {
		return context -> org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors.excludeByKey(keys).test(context);
	}

	/**
	 * Returns a selector that will reject any biome whose key is in the given collection of keys.
	 *
	 * <p>This is useful for allowing a list of biomes to be defined in the config file, where
	 * a certain feature should not spawn.
	 */
	public static Predicate<BiomeSelectionContext> excludeByKey(Collection<RegistryKey<Biome>> keys) {
		return context -> org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors.excludeByKey(keys).test(context);
	}

	/**
	 * @see #includeByKey(Collection)
	 */
	@SafeVarargs
	public static Predicate<BiomeSelectionContext> includeByKey(RegistryKey<Biome>... keys) {
		return context -> org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors.includeByKey(keys).test(context);
	}

	/**
	 * Returns a selector that will accept only biomes whose keys are in the given collection of keys.
	 *
	 * <p>This is useful for allowing a list of biomes to be defined in the config file, where
	 * a certain feature should spawn exclusively.
	 */
	public static Predicate<BiomeSelectionContext> includeByKey(Collection<RegistryKey<Biome>> keys) {
		return context -> org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors.includeByKey(keys).test(context);
	}

	/**
	 * Returns a biome selector that will match biomes in which one of the given entity types can spawn.
	 *
	 * <p>Matches spawns in all {@link SpawnGroup spawn groups}.
	 */
	public static Predicate<BiomeSelectionContext> spawnsOneOf(EntityType<?>... entityTypes) {
		return context -> org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors.spawnsOneOf(entityTypes).test(context);
	}

	/**
	 * Returns a biome selector that will match biomes in which one of the given entity types can spawn.
	 *
	 * <p>Matches spawns in all {@link SpawnGroup spawn groups}.
	 */
	public static Predicate<BiomeSelectionContext> spawnsOneOf(Set<EntityType<?>> entityTypes) {
		return context -> org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors.spawnsOneOf(entityTypes).test(context);
	}
}
