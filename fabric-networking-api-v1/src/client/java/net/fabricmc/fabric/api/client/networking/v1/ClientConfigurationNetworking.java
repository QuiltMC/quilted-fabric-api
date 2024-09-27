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

import java.util.Set;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientConfigurationNetworkHandler;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.thread.ThreadExecutor;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.impl.networking.QuiltUtil;
import net.fabricmc.fabric.mixin.networking.client.accessor.ClientConfigurationNetworkAddonAccessor;

import org.quiltmc.qsl.networking.impl.client.ClientConfigurationNetworkAddon;

/**
 * Offers access to configuration stage client-side networking functionalities.
 *
 * <p>Client-side networking functionalities include receiving clientbound packets,
 * sending serverbound packets, and events related to client-side network handlers.
 * Packets <strong>received</strong> by this class must be registered to {@link
 * PayloadTypeRegistry#configurationS2C()} on both ends.
 * Packets <strong>sent</strong> by this class must be registered to {@link
 * PayloadTypeRegistry#configurationC2S()} on both ends.
 * Packets must be registered before registering any receivers.
 *
 * <p>This class should be only used on the physical client and for the logical client.
 *
 * <p>See {@link ServerPlayNetworking} for information on how to use the packet
 * object-based API.
 *
 * @see ServerConfigurationNetworking
 *
 * @deprecated see {@link org.quiltmc.qsl.networking.api.client.ClientConfigurationNetworking ClientConfigurationNetworking}
 */
@Deprecated
public final class ClientConfigurationNetworking {
	/**
	 * Registers a handler for a packet type.
	 * A global receiver is registered to all connections, in the present and future.
	 *
	 * <p>If a handler is already registered for the {@code type}, this method will return {@code false}, and no change will be made.
	 * Use {@link #unregisterGlobalReceiver(CustomPayload.Id)} to unregister the existing handler.
	 *
	 * @param type the packet type
	 * @param handler the handler
	 * @return false if a handler is already registered to the channel
	 * @throws IllegalArgumentException if the codec for {@code type} has not been {@linkplain PayloadTypeRegistry#configurationS2C() registered} yet
	 * @see ClientConfigurationNetworking#unregisterGlobalReceiver(CustomPayload.Id)
	 * @see ClientConfigurationNetworking#registerReceiver(CustomPayload.Id, ConfigurationPayloadHandler)
	 */
	public static <T extends CustomPayload> boolean registerGlobalReceiver(CustomPayload.Id<T> type, ConfigurationPayloadHandler<T> handler) {
		return org.quiltmc.qsl.networking.api.client.ClientConfigurationNetworking.registerGlobalReceiver(type, handler);
	}

	/**
	 * Removes the handler for a packet type.
	 * A global receiver is registered to all connections, in the present and future.
	 *
	 * <p>The {@code type} is guaranteed not to have an associated handler after this call.
	 *
	 * @param id the packet id
	 * @return the previous handler, or {@code null} if no handler was bound to the channel,
	 * or it was not registered using {@link #registerGlobalReceiver(CustomPayload.Id, ConfigurationPayloadHandler)}
	 * @see ClientConfigurationNetworking#registerGlobalReceiver(CustomPayload.Id, ConfigurationPayloadHandler)
	 * @see ClientConfigurationNetworking#unregisterReceiver(Identifier)
	 */
	@Nullable
	public static ClientConfigurationNetworking.ConfigurationPayloadHandler<?> unregisterGlobalReceiver(CustomPayload.Id<?> id) {
		org.quiltmc.qsl.networking.api.client.ClientConfigurationNetworking.CustomChannelReceiver<?> old = org.quiltmc.qsl.networking.api.client.ClientConfigurationNetworking.unregisterGlobalReceiver(id);

		return convertToFabricHandler(old);
	}

	/**
	 * Gets all channel names which global receivers are registered for.
	 * A global receiver is registered to all connections, in the present and future.
	 *
	 * @return all channel names which global receivers are registered for.
	 */
	public static Set<Identifier> getGlobalReceivers() {
		return QuiltUtil.toIdentifiers(org.quiltmc.qsl.networking.api.client.ClientConfigurationNetworking.getGlobalReceivers());
	}

	/**
	 * Registers a handler for a packet type.
	 *
	 * <p>If a handler is already registered for the {@code type}, this method will return {@code false}, and no change will be made.
	 * Use {@link #unregisterReceiver(Identifier)} to unregister the existing handler.
	 *
	 * <p>For example, if you only register a receiver using this method when a {@linkplain ClientLoginNetworking#registerGlobalReceiver(Identifier, ClientLoginNetworking.LoginQueryRequestHandler)}
	 * login query has been received, you should use {@link ClientPlayConnectionEvents#INIT} to register the channel handler.
	 *
	 * @param id the payload id
	 * @param handler the handler
	 * @return {@code false} if a handler is already registered for the type
	 * @throws IllegalArgumentException if the codec for {@code type} has not been {@linkplain PayloadTypeRegistry#configurationS2C() registered} yet
	 * @throws IllegalStateException if the client is not connected to a server
	 * @see ClientPlayConnectionEvents#INIT
	 */
	public static <T extends CustomPayload> boolean registerReceiver(CustomPayload.Id<T> id, ConfigurationPayloadHandler<T> handler) {
		return org.quiltmc.qsl.networking.api.client.ClientConfigurationNetworking.registerReceiver(id, handler);
	}

	/**
	 * Removes the handler for a packet type.
	 *
	 * <p>The {@code type} is guaranteed not to have an associated handler after this call.
	 *
	 * @param id the payload id to unregister
	 * @return the previous handler, or {@code null} if no handler was bound to the channel,
	 * or it was not registered using {@link #registerReceiver(CustomPayload.Id, ConfigurationPayloadHandler)}
	 * @throws IllegalStateException if the client is not connected to a server
	 */
	@Nullable
	public static ClientConfigurationNetworking.ConfigurationPayloadHandler<?> unregisterReceiver(Identifier id) {
		org.quiltmc.qsl.networking.api.client.ClientConfigurationNetworking.CustomChannelReceiver<?> old = org.quiltmc.qsl.networking.api.client.ClientConfigurationNetworking.unregisterReceiver(new CustomPayload.Id<>(id));

		return convertToFabricHandler(old);
	}

	/**
	 * Gets all the channel names that the client can receive packets on.
	 *
	 * @return All the channel names that the client can receive packets on
	 * @throws IllegalStateException if the client is not connected to a server
	 */
	public static Set<Identifier> getReceived() throws IllegalStateException {
		return QuiltUtil.toIdentifiers(org.quiltmc.qsl.networking.api.client.ClientConfigurationNetworking.getReceived());
	}

	/**
	 * Gets all channel names that the connected server declared the ability to receive a packets on.
	 *
	 * @return All the channel names the connected server declared the ability to receive a packets on
	 * @throws IllegalStateException if the client is not connected to a server
	 */
	public static Set<Identifier> getSendable() throws IllegalStateException {
		return QuiltUtil.toIdentifiers(org.quiltmc.qsl.networking.api.client.ClientConfigurationNetworking.getSendable());
	}

	/**
	 * Checks if the connected server declared the ability to receive a packet on a specified channel name.
	 *
	 * @param channelName the channel name
	 * @return {@code true} if the connected server has declared the ability to receive a packet on the specified channel.
	 * False if the client is not in game.
	 */
	public static boolean canSend(Identifier channelName) throws IllegalArgumentException {
		return org.quiltmc.qsl.networking.api.client.ClientConfigurationNetworking.canSend(new CustomPayload.Id<>(channelName));
	}

	/**
	 * Checks if the connected server declared the ability to receive a packet on a specified channel name.
	 * This returns {@code false} if the client is not in game.
	 *
	 * @param type the packet type
	 * @return {@code true} if the connected server has declared the ability to receive a packet on the specified channel
	 */
	public static boolean canSend(CustomPayload.Id<?> type) {
		return org.quiltmc.qsl.networking.api.client.ClientConfigurationNetworking.canSend(type);
	}

	/**
	 * Gets the packet sender which sends packets to the connected server.
	 *
	 * @return the client's packet sender
	 * @throws IllegalStateException if the client is not connected to a server
	 */
	public static PacketSender getSender() throws IllegalStateException {
		return QuiltUtil.toFabricSender(org.quiltmc.qsl.networking.api.client.ClientConfigurationNetworking.getSender());
	}

	/**
	 * Sends a packet to the connected server.
	 *
	 * <p>Any packets sent must be {@linkplain PayloadTypeRegistry#configurationC2S() registered}.</p>
	 *
	 * @param payload to be sent
	 * @throws IllegalStateException if the client is not connected to a server
	 */
	public static void send(CustomPayload payload) {
		org.quiltmc.qsl.networking.api.client.ClientConfigurationNetworking.send(payload);
	}

	private ClientConfigurationNetworking() {
	}

	/**
	 * A packet handler utilizing {@link CustomPayload}.
	 * @param <T> the type of the packet
	 */
	@FunctionalInterface
	public interface ConfigurationPayloadHandler<T extends CustomPayload> extends org.quiltmc.qsl.networking.api.client.ClientConfigurationNetworking.CustomChannelReceiver<T> {
		/**
		 * Handles the incoming packet.
		 *
		 * <p>Unlike {@link ClientPlayNetworking.PlayPayloadHandler} this method is executed on {@linkplain io.netty.channel.EventLoop netty's event loops}.
		 * Modification to the game should be {@linkplain ThreadExecutor#submit(Runnable) scheduled}.
		 *
		 * <p>An example usage of this:
		 * <pre>{@code
		 * // use PayloadTypeRegistry for registering the payload
		 * ClientConfigurationNetworking.registerReceiver(OVERLAY_PACKET_TYPE, (payload, context) -> {
		 *
		 * });
		 * }</pre>
		 *
		 * @param payload the packet payload
		 * @param context the configuration networking context
		 * @see CustomPayload
		 */
		void receive(T payload, Context context);

		@Override
		default void receive(MinecraftClient client, ClientConfigurationNetworkHandler handler, T payload, org.quiltmc.qsl.networking.api.PacketSender<CustomPayload> responseSender) {
			record ContextImpl(MinecraftClient client, ClientConfigurationNetworkHandler networkHandler, PacketSender responseSender) implements Context { }
			this.receive(payload, new ContextImpl(client, handler, QuiltUtil.toFabricSender(responseSender)));
		}
	}

	@ApiStatus.NonExtendable
	public interface Context {
		/**
		 * @return The MinecraftClient instance
		 */
		MinecraftClient client();

		/**
		 * @return The ClientConfigurationNetworkHandler instance
		 */
		ClientConfigurationNetworkHandler networkHandler();

		/**
		 * @return The packet sender
		 */
		PacketSender responseSender();
	}

	private static @Nullable ConfigurationPayloadHandler<?> convertToFabricHandler(org.quiltmc.qsl.networking.api.client.ClientConfigurationNetworking.CustomChannelReceiver<?> old) {
		if (old instanceof ConfigurationPayloadHandler<?> fabric) {
			return fabric;
		} else if (old != null) {
			return (payload, context) -> {
				if (old instanceof org.quiltmc.qsl.networking.api.client.ClientConfigurationNetworking.CustomChannelReceiver r) {
					org.quiltmc.qsl.networking.api.PacketSender<CustomPayload> sender = QuiltUtil.toQuiltSender(context.responseSender());
					r.receive(context.client(), getHandler(sender), payload, sender);
				} else {
					// This should never happen!
					throw new UnsupportedOperationException("Unknown receiver type: " + old.getClass().getName());
				}
			};
		} else {
			return null;
		}
	}

	private static ClientConfigurationNetworkHandler getHandler(org.quiltmc.qsl.networking.api.PacketSender<CustomPayload> sender) {
		if (sender instanceof ClientConfigurationNetworkAddon addon) {
			return ((ClientConfigurationNetworkAddonAccessor) (Object) addon).getHandler();
		}

		throw new IllegalStateException("Packet Sender was not from a ClientConfigurationNetworkAddon!");
	}
}
