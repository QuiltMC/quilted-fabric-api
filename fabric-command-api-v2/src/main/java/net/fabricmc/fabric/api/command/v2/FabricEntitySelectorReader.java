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

package net.fabricmc.fabric.api.command.v2;

import net.minecraft.util.Identifier;

import org.quiltmc.qsl.command.api.QuiltEntitySelectorReader;

/**
 * Fabric extension to {@link net.minecraft.command.EntitySelectorReader}, implemented
 * using interface injection. This allows custom entity selectors to
 * set a custom flag to a reader. This can be used to implement mutually-exclusive
 * or non-repeatable entity selector option.
 *
 * @deprecated see {@link org.quiltmc.qsl.command.api.QuiltEntitySelectorReader}
 */
@Deprecated
public interface FabricEntitySelectorReader extends QuiltEntitySelectorReader {
	/**
	 * Sets a flag.
	 * @param key the key of the flag
	 * @param value the value of the flag
	 */
	default void setCustomFlag(Identifier key, boolean value) {
		this.setFlag(key.toUnderscoreSeparatedString(), value);
	}

	/**
	 * Gets the value of the flag.
	 * @param key the key of the flag
	 * @return the value, or {@code false} if the flag is not set
	 */
	default boolean getCustomFlag(Identifier key) {
		return this.getFlag(key.toUnderscoreSeparatedString());
	}
}
