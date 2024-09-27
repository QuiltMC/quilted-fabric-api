/*
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

package net.fabricmc.fabric.impl.networking;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

import org.quiltmc.qsl.networking.api.LoginPacketSender;
import org.quiltmc.qsl.networking.api.PacketSender;

public class QuiltUtil {
	public static <C> PacketSender<C> toQuiltSender(net.fabricmc.fabric.api.networking.v1.PacketSender sender) {
		if (sender instanceof Quilt2FabricPacketSender s) {
			return (PacketSender<C>) s.sender;
		} else {
			return new Fabric2QuiltPacketSender<>(sender);
		}
	}

	public static net.fabricmc.fabric.api.networking.v1.PacketSender toFabricSender(PacketSender<?> sender) {
		if (sender instanceof Fabric2QuiltPacketSender<?> s) {
			return s.sender;
		} else {
			return new Quilt2FabricPacketSender<>(sender);
		}
	}

	public static LoginPacketSender toQuiltLoginSender(net.fabricmc.fabric.api.networking.v1.PacketSender sender) {
		if (sender instanceof Quilt2FabricLoginPacketSender s) {
			return s.sender;
		} else {
			return new Fabric2QuiltLoginPacketSender(sender);
		}
	}

	public static net.fabricmc.fabric.api.networking.v1.LoginPacketSender toFabricLoginSender(LoginPacketSender sender) {
		if (sender instanceof net.fabricmc.fabric.api.networking.v1.LoginPacketSender) {
			return ((net.fabricmc.fabric.api.networking.v1.LoginPacketSender) sender);
		} else {
			return new Quilt2FabricLoginPacketSender(sender);
		}
	}

	public static <B extends PacketByteBuf> PayloadTypeRegistry<B> fromQuilt(org.quiltmc.qsl.networking.api.PayloadTypeRegistry<B> quilt) {
		record Impl<B extends PacketByteBuf>(org.quiltmc.qsl.networking.api.PayloadTypeRegistry<B> quilt) implements PayloadTypeRegistry<B> {
			@Override
			public <T extends CustomPayload> CustomPayload.Type<? super B, T> register(CustomPayload.Id<T> id, PacketCodec<? super B, T> codec) {
				return Impl.this.quilt.register(id, codec);
			}
		}

		return new Impl<>(quilt);
	}

	public static @NotNull List<Identifier> toIdentifiers(List<CustomPayload.Id<?>> channels) {
		return channels.stream().map(CustomPayload.Id::id).toList();
	}

	public static @NotNull Set<Identifier> toIdentifiers(Set<CustomPayload.Id<?>> channels) {
		return channels.stream().map(CustomPayload.Id::id).collect(Collectors.toSet());
	}

	public static @NotNull List<CustomPayload.Id<?>> toPayloadIds(List<Identifier> channels) {
		List<CustomPayload.Id<?>> ids = new ArrayList<>();

		for (Identifier channel : channels) {
			CustomPayload.Id<?> id = new CustomPayload.Id<>(channel);
			ids.add(id);
		}

		return ids;
	}
}
