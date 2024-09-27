/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 * Copyright 2024 The Quilt Project
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

package net.fabricmc.fabric.mixin.networking;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.server.network.ServerConfigurationNetworkHandler;
import net.minecraft.server.network.ServerPlayerConfigurationTask;

import net.fabricmc.fabric.api.networking.v1.FabricServerConfigurationNetworkHandler;

@Mixin(ServerConfigurationNetworkHandler.class)
public abstract class ServerConfigurationNetworkHandlerMixin implements FabricServerConfigurationNetworkHandler {
	@Override
	public void completeTask(ServerPlayerConfigurationTask.Key key) {
		this.finishTask(key);
	}
}
