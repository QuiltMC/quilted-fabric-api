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

package net.fabricmc.fabric.api.client.event.lifecycle.v1;

import net.minecraft.client.MinecraftClient;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.impl.base.event.QuiltCompatEvent;

public final class ClientLifecycleEvents {
	private ClientLifecycleEvents() {
	}

	/**
	 * Called when Minecraft has started and it's client about to tick for the first time.
	 *
	 * <p>This occurs while the splash screen is displayed.
	 *
	 * @deprecated see {@link org.quiltmc.qsl.lifecycle.api.client.event.ClientLifecycleEvents#READY READY}
	 */
	@Deprecated
	public static final Event<ClientStarted> CLIENT_STARTED = QuiltCompatEvent.fromQuilt(
			org.quiltmc.qsl.lifecycle.api.client.event.ClientLifecycleEvents.READY,
			event -> event::onClientStarted,
			event -> event.get()::readyClient
	);

	/**
	 * Called when Minecraft's client begins to stop.
	 * This is caused by quitting while in game, or closing the game window.
	 *
	 * <p>This will be called before the integrated server is stopped if it is running.
	 *
	 * @deprecated see {@link org.quiltmc.qsl.lifecycle.api.client.event.ClientLifecycleEvents#STOPPING STOPPING}
	 */
	@Deprecated
	public static final Event<ClientStopping> CLIENT_STOPPING = QuiltCompatEvent.fromQuilt(
			org.quiltmc.qsl.lifecycle.api.client.event.ClientLifecycleEvents.STOPPING,
			event -> event::onClientStopping,
			event -> event.get()::stoppingClient
	);

	@FunctionalInterface
	public interface ClientStarted {
		void onClientStarted(MinecraftClient client);
	}

	@FunctionalInterface
	public interface ClientStopping {
		void onClientStopping(MinecraftClient client);
	}
}
