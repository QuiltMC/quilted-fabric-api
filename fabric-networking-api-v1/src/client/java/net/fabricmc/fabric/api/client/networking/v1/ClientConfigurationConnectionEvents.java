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

package net.fabricmc.fabric.api.client.networking.v1;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientConfigurationNetworkHandler;
import net.minecraft.network.packet.CustomPayload;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.impl.base.event.QuiltCompatEvent;

import org.quiltmc.qsl.networking.impl.AbstractChanneledNetworkAddon;
import org.quiltmc.qsl.networking.impl.NetworkHandlerExtensions;

/**
 * Offers access to events related to the configuration connection to a server on a logical client.
 *
 * @deprecated see {@link org.quiltmc.qsl.networking.api.client.ClientConfigurationConnectionEvents ClientConfigurationConnectionEvents}
 */
@Deprecated
public final class ClientConfigurationConnectionEvents {
	/**
	 * Event indicating a connection entering the CONFIGURATION state, ready for registering channel handlers.
	 *
	 * <p>No packets should be sent when this event is invoked.
	 *
	 * @see ClientConfigurationNetworking#registerReceiver(CustomPayload.Id, ClientConfigurationNetworking.ConfigurationPayloadHandler)
	 *
	 * @deprecated see {@link org.quiltmc.qsl.networking.api.client.ClientConfigurationConnectionEvents#INIT INIT}
	 */
	@Deprecated
	public static final Event<ClientConfigurationConnectionEvents.Init> INIT = QuiltCompatEvent.fromQuilt(org.quiltmc.qsl.networking.api.client.ClientConfigurationConnectionEvents.INIT,
			init -> init::onConfigurationInit,
			invokerGetter -> invokerGetter.get()::onConfigurationInit
	);

	/**
	 * An event called after the connection has been initialized and is ready to start sending and receiving configuration packets.
	 *
	 * <p>Packets may be sent during this event.
	 *
	 * @deprecated see {@link org.quiltmc.qsl.networking.api.client.ClientConfigurationConnectionEvents#START START}
	 */
	@Deprecated
	public static final Event<ClientConfigurationConnectionEvents.Start> START = QuiltCompatEvent.fromQuilt(org.quiltmc.qsl.networking.api.client.ClientConfigurationConnectionEvents.START,
			init -> (handler, sender, client) -> init.onConfigurationStart(handler, client),
			invokerGetter -> (handler, client) -> invokerGetter.get().onConfigurationStart(handler, ((AbstractChanneledNetworkAddon<?>) ((NetworkHandlerExtensions) handler).getAddon()), client)
	);

	/**
	 * An event called after the ReadyS2CPacket has been received, just before switching to the PLAY state.
	 *
	 * <p>No packets should be sent when this event is invoked.
	 *
	 * @deprecated see {@link org.quiltmc.qsl.networking.api.client.ClientConfigurationConnectionEvents#CONFIGURED CONFIGURED}
	 */
	@Deprecated
	public static final Event<ClientConfigurationConnectionEvents.Complete> COMPLETE = QuiltCompatEvent.fromQuilt(org.quiltmc.qsl.networking.api.client.ClientConfigurationConnectionEvents.CONFIGURED,
			init -> init::onConfigurationComplete,
			invokerGetter -> invokerGetter.get()::onConfigured
	);

	/**
	 * An event for the disconnection of the client configuration network handler.
	 *
	 * <p>No packets should be sent when this event is invoked.
	 *
	 * @deprecated see {@link org.quiltmc.qsl.networking.api.client.ClientConfigurationConnectionEvents#DISCONNECT DISCONNECT}
	 */
	@Deprecated
	public static final Event<ClientConfigurationConnectionEvents.Disconnect> DISCONNECT = QuiltCompatEvent.fromQuilt(org.quiltmc.qsl.networking.api.client.ClientConfigurationConnectionEvents.DISCONNECT,
			init -> init::onConfigurationDisconnect,
			invokerGetter -> invokerGetter.get()::onConfigurationDisconnect
	);

	private ClientConfigurationConnectionEvents() {
	}

	@FunctionalInterface
	public interface Init {
		void onConfigurationInit(ClientConfigurationNetworkHandler handler, MinecraftClient client);
	}

	@FunctionalInterface
	public interface Start {
		void onConfigurationStart(ClientConfigurationNetworkHandler handler, MinecraftClient client);
	}

	@FunctionalInterface
	public interface Complete {
		void onConfigurationComplete(ClientConfigurationNetworkHandler handler, MinecraftClient client);
	}

	@FunctionalInterface
	public interface Disconnect {
		void onConfigurationDisconnect(ClientConfigurationNetworkHandler handler, MinecraftClient client);
	}

	// Deprecated:

	/**
	 * @deprecated replaced by {@link #COMPLETE}
	 */
	@Deprecated
	public static final Event<ClientConfigurationConnectionEvents.Ready> READY = EventFactory.createArrayBacked(ClientConfigurationConnectionEvents.Ready.class, callbacks -> (handler, client) -> {
		for (ClientConfigurationConnectionEvents.Ready callback : callbacks) {
			callback.onConfigurationReady(handler, client);
		}
	});

	/**
	 * @deprecated replaced by {@link ClientConfigurationConnectionEvents.Complete}
	 */
	@Deprecated
	@FunctionalInterface
	public interface Ready {
		void onConfigurationReady(ClientConfigurationNetworkHandler handler, MinecraftClient client);
	}
}
