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

import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.login.LoginQueryRequestPayload;

import org.quiltmc.qsl.networking.api.LoginPacketSender;

class Fabric2QuiltLoginPacketSender implements LoginPacketSender {
	final net.fabricmc.fabric.api.networking.v1.PacketSender sender;

	protected Fabric2QuiltLoginPacketSender(net.fabricmc.fabric.api.networking.v1.PacketSender fabric) {
		this.sender = fabric;
	}

	@Override
	public Packet<?> createPacket(LoginQueryRequestPayload payload) {
		if (this.sender instanceof Quilt2FabricLoginPacketSender quilt) {
			return quilt.sender.createPacket(payload);
		}

		throw new IllegalStateException("I don't think this is reachable!");
	}

	@Override
	public void sendPacket(Packet<?> packet) {
		this.sender.sendPacket(packet);
	}

	@Override
	public void sendPacket(Packet<?> packet, @Nullable PacketCallbacks listener) {
		this.sender.sendPacket(packet, listener);
	}
}
