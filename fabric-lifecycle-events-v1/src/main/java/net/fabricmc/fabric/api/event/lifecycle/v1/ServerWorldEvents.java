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

package net.fabricmc.fabric.api.event.lifecycle.v1;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.impl.base.event.QuiltCompatEvent;

public final class ServerWorldEvents {
	/**
	 * Called just after a world is loaded by a Minecraft server.
	 *
	 * <p>This can be used to load world specific metadata or initialize a {@link PersistentState} on a server world.
	 *
	 * @deprecated see {@link org.quiltmc.qsl.lifecycle.api.event.ServerWorldLoadEvents#LOAD LOAD}
	 */
	@Deprecated
	public static final Event<Load> LOAD = QuiltCompatEvent.fromQuilt(
			org.quiltmc.qsl.lifecycle.api.event.ServerWorldLoadEvents.LOAD,
			load -> load::onWorldLoad,
			invokerGetter -> (server, world) -> invokerGetter.get().loadWorld(server, world)
	);

	/**
	 * Called before a world is unloaded by a Minecraft server.
	 *
	 * <p>This typically occurs after a server has {@link ServerLifecycleEvents#SERVER_STOPPING started shutting down}.
	 * Mods which allow dynamic world (un)registration should call this event so mods can let go of world handles when a world is removed.
	 *
	 * @deprecated see {@link org.quiltmc.qsl.lifecycle.api.event.ServerWorldLoadEvents#UNLOAD UNLOAD}
	 */
	@Deprecated
	public static final Event<Unload> UNLOAD = QuiltCompatEvent.fromQuilt(
			org.quiltmc.qsl.lifecycle.api.event.ServerWorldLoadEvents.UNLOAD,
			load -> load::onWorldUnload,
			invokerGetter -> (server, world) -> invokerGetter.get().unloadWorld(server, world)
	);

	@FunctionalInterface
	public interface Load {
		void onWorldLoad(MinecraftServer server, ServerWorld world);
	}

	@FunctionalInterface
	public interface Unload {
		void onWorldUnload(MinecraftServer server, ServerWorld world);
	}

	private ServerWorldEvents() {
	}
}
