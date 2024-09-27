/*
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

package net.fabricmc.fabric.impl.networking;

import org.jetbrains.annotations.Nullable;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.packet.Packet;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.networking.v1.LoginPacketSender;
import net.fabricmc.fabric.mixin.networking.accessor.ServerLoginNetworkAddonAccessor;

import org.quiltmc.qsl.networking.impl.payload.PacketByteBufLoginQueryRequestPayload;
import org.quiltmc.qsl.networking.impl.server.ServerLoginNetworkAddon;

final class Quilt2FabricLoginPacketSender implements LoginPacketSender {
	final org.quiltmc.qsl.networking.api.LoginPacketSender sender;

	Quilt2FabricLoginPacketSender(org.quiltmc.qsl.networking.api.LoginPacketSender sender) {
		this.sender = sender;
	}

	@Override
	public Packet<?> createPacket(Identifier channelName, PacketByteBuf buf) {
		// Copied from org.quiltmc.qsl.networking.api.LoginPacketSender
		return this.sender.createPacket(new PacketByteBufLoginQueryRequestPayload(channelName, buf));
	}

	@Override
	public Packet<?> createPacket(CustomPayload payload) {
		throw new UnsupportedOperationException("Cannot send CustomPayload during login");
	}

	@Override
	public void sendPacket(Packet<?> packet) {
		this.sender.sendPacket(packet);
	}

	@Override
	public void sendPacket(Packet<?> packet, @Nullable PacketCallbacks callback) {
		this.sender.sendPacket(packet, callback);
	}

	@Override
	public void disconnect(Text disconnectReason) {
		if (this.sender instanceof ServerLoginNetworkAddon loginAddon) {
			((ServerLoginNetworkAddonAccessor) (Object) loginAddon).getConnection().disconnect(disconnectReason);
		}
	}
}
