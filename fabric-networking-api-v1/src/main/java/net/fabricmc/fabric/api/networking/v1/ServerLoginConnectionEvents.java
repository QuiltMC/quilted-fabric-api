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

package net.fabricmc.fabric.api.networking.v1;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerLoginNetworkHandler;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.impl.base.event.QuiltCompatEvent;
import net.fabricmc.fabric.impl.networking.QuiltUtil;

/**
 * Offers access to events related to the connection to a client on a logical server while a client is logging in.
 *
 * @deprecated see {@link org.quiltmc.qsl.networking.api.server.ServerLoginConnectionEvents ServerLoginConnectionEvents}
 */
@Deprecated
public final class ServerLoginConnectionEvents {
	/**
	 * Event indicating a connection entered the LOGIN state, ready for registering query response handlers.
	 *
	 * @see ServerLoginNetworking#registerReceiver(ServerLoginNetworkHandler, Identifier, ServerLoginNetworking.LoginQueryResponseHandler)
	 */
	public static final Event<Init> INIT = QuiltCompatEvent.fromQuilt(org.quiltmc.qsl.networking.api.server.ServerLoginConnectionEvents.INIT,
			init -> init::onLoginInit,
			invokerGetter -> invokerGetter.get()::onLoginInit
	);

	/**
	 * An event for the start of login queries of the server login network handler.
	 * This event may be used to register {@link ServerLoginNetworking.LoginQueryResponseHandler login query response handlers}
	 * using {@link ServerLoginNetworking#registerReceiver(ServerLoginNetworkHandler, Identifier, ServerLoginNetworking.LoginQueryResponseHandler)}
	 * since this event is fired just before the first login query response is processed.
	 *
	 * <p>You may send login queries to the connected client using the provided {@link LoginPacketSender}.
	 */
	public static final Event<QueryStart> QUERY_START = QuiltCompatEvent.fromQuilt(org.quiltmc.qsl.networking.api.server.ServerLoginConnectionEvents.QUERY_START,
			queryStart -> (handler, server, sender, synchronizer) -> queryStart.onLoginStart(handler, server, QuiltUtil.toFabricLoginSender(sender), synchronizer::waitFor),
			invokerGetter -> (handler, server, sender, synchronizer) -> invokerGetter.get().onLoginStart(handler, server, QuiltUtil.toQuiltLoginSender(sender), synchronizer)
	);

	/**
	 * An event for the disconnection of the server login network handler.
	 *
	 * <p>No packets should be sent when this event is invoked.
	 */
	public static final Event<Disconnect> DISCONNECT = QuiltCompatEvent.fromQuilt(org.quiltmc.qsl.networking.api.server.ServerLoginConnectionEvents.DISCONNECT,
			disconnect -> disconnect::onLoginDisconnect,
			invokerGetter -> invokerGetter.get()::onLoginDisconnect
	);

	private ServerLoginConnectionEvents() {
	}

	/**
	 * @see ServerLoginConnectionEvents#INIT
	 */
	@FunctionalInterface
	public interface Init {
		void onLoginInit(ServerLoginNetworkHandler handler, MinecraftServer server);
	}

	/**
	 * @see ServerLoginConnectionEvents#QUERY_START
	 */
	@FunctionalInterface
	public interface QueryStart {
		void onLoginStart(ServerLoginNetworkHandler handler, MinecraftServer server, LoginPacketSender sender, ServerLoginNetworking.LoginSynchronizer synchronizer);
	}

	/**
	 * @see ServerLoginConnectionEvents#DISCONNECT
	 */
	@FunctionalInterface
	public interface Disconnect {
		void onLoginDisconnect(ServerLoginNetworkHandler handler, MinecraftServer server);
	}
}
