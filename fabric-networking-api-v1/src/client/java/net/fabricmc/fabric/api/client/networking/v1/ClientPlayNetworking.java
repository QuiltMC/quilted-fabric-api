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

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.listener.ServerCommonPacketListener;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.impl.networking.QuiltUtil;
import net.fabricmc.fabric.mixin.networking.client.accessor.ClientPlayNetworkAddonAccessor;

/**
 * Offers access to play stage client-side networking functionalities.
 *
 * <p>Client-side networking functionalities include receiving clientbound packets,
 * sending serverbound packets, and events related to client-side network handlers.
 * Packets <strong>received</strong> by this class must be registered to {@link PayloadTypeRegistry#playS2C()} on both ends.
 * Packets <strong>sent</strong> by this class must be registered to {@link PayloadTypeRegistry#playC2S()} on both ends.
 * Packets must be registered before registering any receivers.
 *
 * <p>This class should be only used on the physical client and for the logical client.
 *
 * <p>See {@link ServerPlayNetworking} for information on how to use the payload
 * object-based API.
 *
 * @see ClientLoginNetworking
 * @see ClientConfigurationNetworking
 * @see ServerPlayNetworking
 *
 * @deprecated see {@link org.quiltmc.qsl.networking.api.client.ClientPlayNetworking ClientPlayNetworking}
 */
@Deprecated
public final class ClientPlayNetworking {
	/**
	 * Registers a handler for a payload type.
	 * A global receiver is registered to all connections, in the present and future.
	 *
	 * <p>If a handler is already registered for the {@code type}, this method will return {@code false}, and no change will be made.
	 * Use {@link #unregisterGlobalReceiver(Identifier)} to unregister the existing handler.
	 *
	 * @param type the payload type
	 * @param handler the handler
	 * @return false if a handler is already registered to the channel
	 * @throws IllegalArgumentException if the codec for {@code type} has not been {@linkplain PayloadTypeRegistry#playS2C() registered} yet
	 * @see ClientPlayNetworking#unregisterGlobalReceiver(Identifier)
	 * @see ClientPlayNetworking#registerReceiver(CustomPayload.Id, PlayPayloadHandler)
	 */
	public static <T extends CustomPayload> boolean registerGlobalReceiver(CustomPayload.Id<T> type, PlayPayloadHandler<T> handler) {
		return org.quiltmc.qsl.networking.api.client.ClientPlayNetworking.registerGlobalReceiver(type, handler);
	}

	/**
	 * Removes the handler for a payload type.
	 * A global receiver is registered to all connections, in the present and future.
	 *
	 * <p>The {@code type} is guaranteed not to have an associated handler after this call.
	 *
	 * @param id the payload id
	 * @return the previous handler, or {@code null} if no handler was bound to the channel,
	 * or it was not registered using {@link #registerGlobalReceiver(CustomPayload.Id, PlayPayloadHandler)}
	 * @see ClientPlayNetworking#registerGlobalReceiver(CustomPayload.Id, PlayPayloadHandler)
	 * @see ClientPlayNetworking#unregisterReceiver(Identifier)
	 */
	@Nullable
	public static ClientPlayNetworking.PlayPayloadHandler<?> unregisterGlobalReceiver(Identifier id) {
		org.quiltmc.qsl.networking.api.client.ClientPlayNetworking.CustomChannelReceiver<?> old = org.quiltmc.qsl.networking.api.client.ClientPlayNetworking.unregisterGlobalReceiver(new CustomPayload.Id<>(id));
		return convertToFabricHandler(old);
	}

	/**
	 * Gets all channel names which global receivers are registered for.
	 * A global receiver is registered to all connections, in the present and future.
	 *
	 * @return all channel names which global receivers are registered for.
	 */
	public static Set<Identifier> getGlobalReceivers() {
		return QuiltUtil.toIdentifiers(org.quiltmc.qsl.networking.api.client.ClientPlayNetworking.getGlobalReceivers());
	}

	/**
	 * Registers a handler for a payload type.
	 *
	 * <p>If a handler is already registered for the {@code type}, this method will return {@code false}, and no change will be made.
	 * Use {@link #unregisterReceiver(Identifier)} to unregister the existing handler.
	 *
	 * <p>For example, if you only register a receiver using this method when a {@linkplain ClientLoginNetworking#registerGlobalReceiver(Identifier, ClientLoginNetworking.LoginQueryRequestHandler)}
	 * login query has been received, you should use {@link ClientPlayConnectionEvents#INIT} to register the channel handler.
	 *
	 * @param type the payload type
	 * @param handler the handler
	 * @return {@code false} if a handler is already registered for the type
	 * @throws IllegalArgumentException if the codec for {@code type} has not been {@linkplain PayloadTypeRegistry#playS2C() registered} yet
	 * @throws IllegalStateException if the client is not connected to a server
	 * @see ClientPlayConnectionEvents#INIT
	 */
	public static <T extends CustomPayload> boolean registerReceiver(CustomPayload.Id<T> type, PlayPayloadHandler<T> handler) {
		return org.quiltmc.qsl.networking.api.client.ClientPlayNetworking.registerReceiver(type, handler);
	}

	/**
	 * Removes the handler for a payload id.
	 *
	 * <p>The {@code type} is guaranteed not to have an associated handler after this call.
	 *
	 * @param id the payload id
	 * @return the previous handler, or {@code null} if no handler was bound to the channel,
	 * or it was not registered using {@link #registerReceiver(CustomPayload.Id, PlayPayloadHandler)}
	 * @throws IllegalStateException if the client is not connected to a server
	 */
	@Nullable
	public static ClientPlayNetworking.PlayPayloadHandler<?> unregisterReceiver(Identifier id) {
		org.quiltmc.qsl.networking.api.client.ClientPlayNetworking.CustomChannelReceiver<?> old = org.quiltmc.qsl.networking.api.client.ClientPlayNetworking.unregisterReceiver(new CustomPayload.Id<>(id));

		return convertToFabricHandler(old);
	}

	/**
	 * Gets all the channel names that the client can receive packets on.
	 *
	 * @return All the channel names that the client can receive packets on
	 * @throws IllegalStateException if the client is not connected to a server
	 */
	public static Set<Identifier> getReceived() throws IllegalStateException {
		return QuiltUtil.toIdentifiers(org.quiltmc.qsl.networking.api.client.ClientPlayNetworking.getReceived());
	}

	/**
	 * Gets all channel names that the connected server declared the ability to receive a packets on.
	 *
	 * @return All the channel names the connected server declared the ability to receive a packets on
	 * @throws IllegalStateException if the client is not connected to a server
	 */
	public static Set<Identifier> getSendable() throws IllegalStateException {
		return QuiltUtil.toIdentifiers(org.quiltmc.qsl.networking.api.client.ClientPlayNetworking.getSendable());
	}

	/**
	 * Checks if the connected server declared the ability to receive a payload on a specified channel name.
	 *
	 * @param channelName the channel name
	 * @return {@code true} if the connected server has declared the ability to receive a payload on the specified channel.
	 * False if the client is not in game.
	 */
	public static boolean canSend(Identifier channelName) throws IllegalArgumentException {
		return org.quiltmc.qsl.networking.api.client.ClientPlayNetworking.canSend(new CustomPayload.Id<>(channelName));
	}

	/**
	 * Checks if the connected server declared the ability to receive a payload on a specified channel name.
	 * This returns {@code false} if the client is not in game.
	 *
	 * @param type the payload type
	 * @return {@code true} if the connected server has declared the ability to receive a payload on the specified channel
	 */
	public static boolean canSend(CustomPayload.Id<?> type) {
		return org.quiltmc.qsl.networking.api.client.ClientPlayNetworking.canSend(type);
	}

	/**
	 * Creates a payload which may be sent to the connected server.
	 *
	 * @param packet the fabric payload
	 * @return a new payload
	 */
	public static <T extends CustomPayload> Packet<ServerCommonPacketListener> createC2SPacket(T packet) {
		return org.quiltmc.qsl.networking.api.client.ClientPlayNetworking.createC2SPacket(packet);
	}

	/**
	 * Gets the payload sender which sends packets to the connected server.
	 *
	 * @return the client's payload sender
	 * @throws IllegalStateException if the client is not connected to a server
	 */
	public static PacketSender getSender() throws IllegalStateException {
		return QuiltUtil.toFabricSender(org.quiltmc.qsl.networking.api.client.ClientPlayNetworking.getSender());
	}

	/**
	 * Sends a payload to the connected server.
	 *
	 * <p>Any packets sent must be {@linkplain PayloadTypeRegistry#playC2S() registered}.</p>
	 *
	 * @param payload the payload
	 * @throws IllegalStateException if the client is not connected to a server
	 */
	public static void send(CustomPayload payload) {
		org.quiltmc.qsl.networking.api.client.ClientPlayNetworking.send(payload);
	}

	private ClientPlayNetworking() {
	}

	/**
	 * A thread-safe payload handler utilizing {@link CustomPayload}.
	 * @param <T> the type of the payload
	 */
	@FunctionalInterface
	public interface PlayPayloadHandler<T extends CustomPayload> extends org.quiltmc.qsl.networking.api.client.ClientPlayNetworking.CustomChannelReceiver<T> {
		/**
		 * Handles the incoming payload. This is called on the render thread, and can safely
		 * call client methods.
		 *
		 * <p>An example usage of this is to display an overlay message:
		 * <pre>{@code
		 * // use PayloadTypeRegistry for registering the payload
		 * ClientPlayNetworking.registerReceiver(OVERLAY_PACKET_TYPE, (payload, context) -> {
		 * 	context.client().inGameHud.setOverlayMessage(payload.message(), true);
		 * });
		 * }</pre>
		 *
		 * <p>The network handler can be accessed via {@link ClientPlayerEntity#networkHandler}.
		 *
		 * @param payload the packet payload
		 * @param context the play networking context
		 * @see CustomPayload
		 */
		void receive(T payload, Context context);

		@Override
		default void receive(MinecraftClient client, ClientPlayNetworkHandler handler, T payload, org.quiltmc.qsl.networking.api.PacketSender<CustomPayload> responseSender) {
			record ContextImpl(MinecraftClient client, ClientPlayerEntity player, PacketSender responseSender) implements Context { }
			this.receive(payload, new ContextImpl(client, client.player, QuiltUtil.toFabricSender(responseSender)));
		}
	}

	@ApiStatus.NonExtendable
	public interface Context {
		/**
		 * @return The MinecraftClient instance
		 */
		MinecraftClient client();

		/**
		 * @return The player that received the payload
		 */
		ClientPlayerEntity player();

		/**
		 * @return The packet sender
		 */
		PacketSender responseSender();
	}

	private static @Nullable PlayPayloadHandler<?> convertToFabricHandler(org.quiltmc.qsl.networking.api.client.ClientPlayNetworking.CustomChannelReceiver<?> old) {
		if (old instanceof PlayPayloadHandler<?> fabric) {
			return fabric;
		} else if (old != null) {
			return (payload, context) -> {
				if (old instanceof org.quiltmc.qsl.networking.api.client.ClientPlayNetworking.CustomChannelReceiver r) {
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

	private static ClientPlayNetworkHandler getHandler(org.quiltmc.qsl.networking.api.PacketSender<CustomPayload> sender) {
		if (sender instanceof org.quiltmc.qsl.networking.impl.client.ClientPlayNetworkAddon addon) {
			return ((ClientPlayNetworkAddonAccessor) (Object) addon).getHandler();
		}

		throw new IllegalStateException("Packet Sender was not from a ClientConfigurationNetworkAddon!");
	}
}
