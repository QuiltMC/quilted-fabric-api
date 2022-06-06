/*
 * Copyright 2016, 2017, 2018, 2019 FabricMC
 * Copyright 2022 QuiltMC
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

import java.util.function.Consumer;
import java.util.function.Predicate;

import org.jetbrains.annotations.ApiStatus;

import net.minecraft.util.Identifier;

/**
 * <b>Experimental feature</b>, may be removed or changed without further notice.
 *
 * @see BiomeModifications
 */
@Deprecated
public class BiomeModification {
	private final Identifier id;
	public static final org.quiltmc.qsl.worldgen.biome.impl.modification.BiomeModificationImpl QUILT_INSTANCE = org.quiltmc.qsl.worldgen.biome.impl.modification.BiomeModificationImpl.INSTANCE;

	@ApiStatus.Internal
	BiomeModification(Identifier id) {
		this.id = id;
	}

	/**
	 * Adds a modifier that is not sensitive to the current state of the biome when it is applied, examples
	 * for this are modifiers that simply add or remove features unconditionally, or change other values
	 * to constants.
	 */
	public <T extends org.quiltmc.qsl.worldgen.biome.api.BiomeSelectionContext, V extends org.quiltmc.qsl.worldgen.biome.api.BiomeModificationContext> BiomeModification add(ModificationPhase phase, Predicate<T> selector, Consumer<V> modifier) {
		QUILT_INSTANCE.addModifier(id, phase.asQuiltPhase(), (Predicate<org.quiltmc.qsl.worldgen.biome.api.BiomeSelectionContext>) selector, (Consumer<org.quiltmc.qsl.worldgen.biome.api.BiomeModificationContext>) modifier);
		return this;
	}

	/**
	 * Adds a modifier that is sensitive to the current state of the biome when it is applied.
	 * Examples for this are modifiers that apply scales to existing values (e.g. half the temperature).
	 *
	 * <p>For modifiers that should only be applied if a given condition is met for a Biome, please add these
	 * conditions to the selector, and use a context-free modifier instead, as this will greatly help
	 * with debugging world generation issues.
	 */
	public <T extends org.quiltmc.qsl.worldgen.biome.api.BiomeSelectionContext, V extends org.quiltmc.qsl.worldgen.biome.api.BiomeModificationContext> BiomeModification add(Identifier id, ModificationPhase phase, Predicate<T> selector, Consumer<V> modifier) {
		QUILT_INSTANCE.addModifier(id, phase.asQuiltPhase(), (Predicate<org.quiltmc.qsl.worldgen.biome.api.BiomeSelectionContext>) selector, (Consumer<org.quiltmc.qsl.worldgen.biome.api.BiomeModificationContext>) modifier);
		return this;
	}
}
