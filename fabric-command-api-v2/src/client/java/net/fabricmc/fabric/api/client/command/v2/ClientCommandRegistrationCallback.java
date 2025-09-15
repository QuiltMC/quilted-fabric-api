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

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.impl.base.event.QuiltCompatEvent;

import org.quiltmc.qsl.command.api.client.QuiltClientCommandSource;

/**
 * Callback for when client commands are registered to the dispatcher.
 *
 * <p>To register some commands, you would register an event listener and implement the callback.
 *
 * <p>See {@link ClientCommandManager} for more details and an example.
 *
 * @deprecated see {@link org.quiltmc.qsl.command.api.client.ClientCommandRegistrationCallback}
 */
@Deprecated
public interface ClientCommandRegistrationCallback {
	Event<ClientCommandRegistrationCallback> EVENT = QuiltCompatEvent.fromQuilt(org.quiltmc.qsl.command.api.client.ClientCommandRegistrationCallback.EVENT,
			callback -> (dispatcher, buildContext, environment) -> callback.register((CommandDispatcher<FabricClientCommandSource>) (Object) dispatcher, buildContext),
			invokerGetter -> (dispatcher, registryAccess) -> invokerGetter.get().registerCommands((CommandDispatcher<QuiltClientCommandSource>) (Object) dispatcher, registryAccess, CommandManager.RegistrationEnvironment.ALL));
	/**
	 * Called when registering client commands.
	 *
	 * @param dispatcher the command dispatcher to register commands to
	 * @param registryAccess object exposing access to the game's registries
	 */
	void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess);
}
