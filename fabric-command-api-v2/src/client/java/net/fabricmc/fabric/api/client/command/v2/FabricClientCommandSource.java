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

package net.fabricmc.fabric.api.client.command.v2;

import net.minecraft.command.CommandSource;

import org.quiltmc.qsl.command.api.client.QuiltClientCommandSource;

/**
 * Extensions to {@link CommandSource} for client-sided commands.
 *
 * @deprecated see {@link org.quiltmc.qsl.command.api.client.QuiltClientCommandSource}
 */
@Deprecated
public interface FabricClientCommandSource extends QuiltClientCommandSource {
	/**
	 * Gets the meta property under {@code key} that was assigned to this source.
	 *
	 * <p>This method should return the same result for every call with the same {@code key}.
	 *
	 * @param key the meta key
	 * @return the meta
	 */
	default Object getMeta(String key) {
		return null;
	}
}
