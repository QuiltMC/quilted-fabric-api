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

import java.util.OptionalInt;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;

/**
 * Allows {@link Biome} properties to be modified.
 *
 * @deprecated see {@link org.quiltmc.qsl.worldgen.biome.api.BiomeModificationContext}.
 */
@Deprecated
public interface BiomeModificationContext extends org.quiltmc.qsl.worldgen.biome.api.BiomeModificationContext {
	/**
	 * Returns the modification context for the biomes weather properties.
	 */
	default WeatherContext getWeather() {
		return (WeatherContext) ((org.quiltmc.qsl.worldgen.biome.api.BiomeModificationContext) this).getWeather();
	}

	/**
	 * Returns the modification context for the biomes effects.
	 */
	default EffectsContext getEffects() {
		return (EffectsContext) ((org.quiltmc.qsl.worldgen.biome.api.BiomeModificationContext) this).getEffects();
	}

	/**
	 * Returns the modification context for the biomes generation settings.
	 */
	default GenerationSettingsContext getGenerationSettings() {
		return (GenerationSettingsContext) ((org.quiltmc.qsl.worldgen.biome.api.BiomeModificationContext) this).getGenerationSettings();
	}

	/**
	 * Returns the modification context for the biomes spawn settings.
	 */
	default SpawnSettingsContext getSpawnSettings() {
		return (SpawnSettingsContext) ((org.quiltmc.qsl.worldgen.biome.api.BiomeModificationContext) this).getSpawnSettings();
	}

	interface WeatherContext extends org.quiltmc.qsl.worldgen.biome.api.BiomeModificationContext.WeatherContext {
		/**
		 * @see Biome#hasPrecipitation()
		 * @see Biome.Builder#precipitation(boolean)
		 */
		default void setPrecipitation(boolean hasPrecipitation) {
			this.setHasPrecipitation(hasPrecipitation);
		}
	}

	interface EffectsContext extends org.quiltmc.qsl.worldgen.biome.api.BiomeModificationContext.EffectsContext {
		/**
		 * @see BiomeEffects#getFoliageColor()
		 * @see BiomeEffects.Builder#foliageColor(int)
		 */
		default void setFoliageColor(OptionalInt color) {
			color.ifPresentOrElse(this::setFoliageColor, this::clearFoliageColor);
		}

		/**
		 * @see BiomeEffects#getGrassColor()
		 * @see BiomeEffects.Builder#grassColor(int)
		 */
		default void setGrassColor(OptionalInt color) {
			color.ifPresentOrElse(this::setGrassColor, this::clearGrassColor);
		}
	}

	interface GenerationSettingsContext extends org.quiltmc.qsl.worldgen.biome.api.BiomeModificationContext.GenerationSettingsContext { }

	interface SpawnSettingsContext extends org.quiltmc.qsl.worldgen.biome.api.BiomeModificationContext.SpawnSettingsContext { }
}
