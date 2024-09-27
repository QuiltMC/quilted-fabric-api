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

package net.fabricmc.fabric.api.client.networking.v1;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientLoginNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.networking.v1.ServerLoginNetworking;

/**
 * Offers access to login stage client-side networking functionalities.
 *
 * <p>The Minecraft login protocol only allows the client to respond to a server's request, but not initiate one of its own.
 *
 * @see ClientPlayNetworking
 * @see ServerLoginNetworking
 *
 * @deprecated see {@link org.quiltmc.qsl.networking.api.client.ClientLoginNetworking ClientLoginNetworking}
 */
@Deprecated
public final class ClientLoginNetworking {
	/**
	 * Registers a handler to a query request channel.
	 * A global receiver is registered to all connections, in the present and future.
	 *
	 * <p>If a handler is already registered to the {@code channel}, this method will return {@code false}, and no change will be made.
	 * Use {@link #unregisterGlobalReceiver(Identifier)} to unregister the existing handler.
	 *
	 * @param channelName the id of the channel
	 * @param queryHandler the handler
	 * @return false if a handler is already registered to the channel
	 * @see ClientLoginNetworking#unregisterGlobalReceiver(Identifier)
	 * @see ClientLoginNetworking#registerReceiver(Identifier, LoginQueryRequestHandler)
	 */
	public static boolean registerGlobalReceiver(Identifier channelName, LoginQueryRequestHandler queryHandler) {
		return org.quiltmc.qsl.networking.api.client.ClientLoginNetworking.registerGlobalReceiver(channelName, queryHandler);
	}

	/**
	 * Removes the handler of a query request channel.
	 * A global receiver is registered to all connections, in the present and future.
	 *
	 * <p>The {@code channel} is guaranteed not to have a handler after this call.
	 *
	 * @param channelName the id of the channel
	 * @return the previous handler, or {@code null} if no handler was bound to the channel
	 * @see ClientLoginNetworking#registerGlobalReceiver(Identifier, LoginQueryRequestHandler)
	 * @see ClientLoginNetworking#unregisterReceiver(Identifier)
	 */
	@Nullable
	public static ClientLoginNetworking.LoginQueryRequestHandler unregisterGlobalReceiver(Identifier channelName) {
		org.quiltmc.qsl.networking.api.client.ClientLoginNetworking.QueryRequestReceiver old = org.quiltmc.qsl.networking.api.client.ClientLoginNetworking.unregisterGlobalReceiver(channelName);

		if (old instanceof LoginQueryRequestHandler fabric) {
			return fabric;
		} else if (old != null) {
			return old::receive;
		} else {
			return null;
		}
	}

	/**
	 * Gets all query request channel names which global receivers are registered for.
	 * A global receiver is registered to all connections, in the present and future.
	 *
	 * @return all channel names which global receivers are registered for.
	 */
	public static Set<Identifier> getGlobalReceivers() {
		return org.quiltmc.qsl.networking.api.client.ClientLoginNetworking.getGlobalReceivers();
	}

	/**
	 * Registers a handler to a query request channel.
	 *
	 * <p>If a handler is already registered to the {@code channelName}, this method will return {@code false}, and no change will be made.
	 * Use {@link #unregisterReceiver(Identifier)} to unregister the existing handler.
	 *
	 * @param channelName the id of the channel
	 * @param queryHandler the handler
	 * @return false if a handler is already registered to the channel name
	 * @throws IllegalStateException if the client is not logging in
	 */
	public static boolean registerReceiver(Identifier channelName, LoginQueryRequestHandler queryHandler) throws IllegalStateException {
		return org.quiltmc.qsl.networking.api.client.ClientLoginNetworking.registerReceiver(channelName, queryHandler);
	}

	/**
	 * Removes the handler of a query request channel.
	 *
	 * <p>The {@code channelName} is guaranteed not to have a handler after this call.
	 *
	 * @param channelName the id of the channel
	 * @return the previous handler, or {@code null} if no handler was bound to the channel name
	 * @throws IllegalStateException if the client is not logging in
	 */
	@Nullable
	public static LoginQueryRequestHandler unregisterReceiver(Identifier channelName) throws IllegalStateException {
		org.quiltmc.qsl.networking.api.client.ClientLoginNetworking.QueryRequestReceiver old = org.quiltmc.qsl.networking.api.client.ClientLoginNetworking.unregisterReceiver(channelName);

		if (old instanceof LoginQueryRequestHandler fabric) {
			return fabric;
		} else if (old != null) {
			return old::receive;
		} else {
			return null;
		}
	}

	private ClientLoginNetworking() {
	}

	@FunctionalInterface
	public interface LoginQueryRequestHandler extends org.quiltmc.qsl.networking.api.client.ClientLoginNetworking.QueryRequestReceiver {
		/**
		 * Handles an incoming query request from a server.
		 *
		 * <p>This method is executed on {@linkplain io.netty.channel.EventLoop netty's event loops}.
		 * Modification to the game should be {@linkplain net.minecraft.util.thread.ThreadExecutor#submit(Runnable) scheduled} using the provided Minecraft client instance.
		 *
		 * <p>The return value of this method is a completable future that may be used to delay the login process to the server until a task {@link CompletableFuture#isDone() is done}.
		 * The future should complete in reasonably time to prevent disconnection by the server.
		 * If your request processes instantly, you may use {@link CompletableFuture#completedFuture(Object)} to wrap your response for immediate sending.
		 *
		 * @param client the client
		 * @param handler the network handler that received this packet
		 * @param buf the payload of the packet
		 * @param callbacksConsumer listeners to be called when the response packet is sent to the server
		 * @return a completable future which contains the payload to respond to the server with.
		 * If the future contains {@code null}, then the server will be notified that the client did not understand the query.
		 */
		CompletableFuture<@Nullable PacketByteBuf> receive(MinecraftClient client, ClientLoginNetworkHandler handler, PacketByteBuf buf, Consumer<PacketCallbacks> callbacksConsumer);
	}
}
