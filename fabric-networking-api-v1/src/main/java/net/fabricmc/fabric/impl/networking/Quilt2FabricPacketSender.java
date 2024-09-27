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

import org.jetbrains.annotations.Nullable;

import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.login.LoginQueryRequestPayload;
import net.minecraft.text.Text;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.mixin.networking.accessor.AbstractChanneledNetworkAddonAccessor;
import net.fabricmc.fabric.mixin.networking.accessor.ServerLoginNetworkAddonAccessor;

import org.quiltmc.qsl.networking.impl.AbstractChanneledNetworkAddon;
import org.quiltmc.qsl.networking.impl.server.ServerLoginNetworkAddon;

final class Quilt2FabricPacketSender<C> implements PacketSender {
	final org.quiltmc.qsl.networking.api.PacketSender<C> sender;

	Quilt2FabricPacketSender(org.quiltmc.qsl.networking.api.PacketSender<C> sender) {
		this.sender = sender;
	}

	@Override
	public Packet<?> createPacket(CustomPayload payload) {
		if (payload instanceof LoginQueryRequestPayload) {
			throw new UnsupportedOperationException("Cannot send CustomPayload during login");
		}

		return this.sender.createPacket((C) payload);
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
		if (this.sender instanceof AbstractChanneledNetworkAddon<?> channeledAddon) {
			((AbstractChanneledNetworkAddonAccessor) channeledAddon).getConnection().disconnect(disconnectReason);
		} else if (this.sender instanceof ServerLoginNetworkAddon loginAddon) {
			((ServerLoginNetworkAddonAccessor) (Object) loginAddon).getConnection().disconnect(disconnectReason);
		}
	}
}
