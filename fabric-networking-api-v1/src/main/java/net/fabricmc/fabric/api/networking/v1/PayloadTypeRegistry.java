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

package net.fabricmc.fabric.api.networking.v1;

import org.jetbrains.annotations.ApiStatus;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

import net.fabricmc.fabric.impl.networking.QuiltUtil;

/**
 * A registry for payload types.
 *
 * @deprecated see {@link org.quiltmc.qsl.networking.api.PayloadTypeRegistry PayloadTypeRegistry}
 */
@Deprecated
@ApiStatus.NonExtendable
public interface PayloadTypeRegistry<B extends PacketByteBuf> {
	/**
	 * Registers a custom payload type.
	 *
	 * <p>This must be done on both the sending and receiving side, usually during mod initialization
	 * and <strong>before registering a packet handler</strong>.
	 *
	 * @param id    the id of the payload type
	 * @param codec the codec for the payload type
	 * @param <T>   the payload type
	 * @return the registered payload type
	 */
	<T extends CustomPayload> CustomPayload.Type<? super B, T> register(CustomPayload.Id<T> id, PacketCodec<? super B, T> codec);

	/**
	 * @return the {@link PayloadTypeRegistry} instance for the client to server configuration channel.
	 */
	static PayloadTypeRegistry<PacketByteBuf> configurationC2S() {
		return QuiltUtil.fromQuilt(org.quiltmc.qsl.networking.api.PayloadTypeRegistry.configurationC2S());
	}

	/**
	 * @return the {@link PayloadTypeRegistry} instance for the server to client configuration channel.
	 */
	static PayloadTypeRegistry<PacketByteBuf> configurationS2C() {
		return QuiltUtil.fromQuilt(org.quiltmc.qsl.networking.api.PayloadTypeRegistry.configurationS2C());
	}

	/**
	 * @return the {@link PayloadTypeRegistry} instance for the client to server play channel.
	 */
	static PayloadTypeRegistry<RegistryByteBuf> playC2S() {
		return QuiltUtil.fromQuilt(org.quiltmc.qsl.networking.api.PayloadTypeRegistry.playC2S());
	}

	/**
	 * @return the {@link PayloadTypeRegistry} instance for the server to client play channel.
	 */
	static PayloadTypeRegistry<RegistryByteBuf> playS2C() {
		return QuiltUtil.fromQuilt(org.quiltmc.qsl.networking.api.PayloadTypeRegistry.playS2C());
	}
}
