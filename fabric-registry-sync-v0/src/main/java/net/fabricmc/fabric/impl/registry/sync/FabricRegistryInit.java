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

package net.fabricmc.fabric.impl.registry.sync;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking;
import net.fabricmc.fabric.impl.registry.sync.packet.DirectRegistryPacketHandler;

import org.quiltmc.qsl.networking.api.server.ServerConfigurationTaskManager;
import org.quiltmc.qsl.registry.impl.sync.server.SetupSyncTask;

public class FabricRegistryInit implements ModInitializer {
	@Override
	public void onInitialize() {
		PayloadTypeRegistry.configurationC2S().register(SyncCompletePayload.ID, SyncCompletePayload.CODEC);
		PayloadTypeRegistry.configurationS2C().register(DirectRegistryPacketHandler.Payload.ID, DirectRegistryPacketHandler.Payload.CODEC);

		SetupSyncTask.registerSyncTask(SetupSyncTask.QUILT_SYNC_PRIORITY + 1, new SetupSyncTask.SyncTask(
				"fabric",
				handler -> ServerConfigurationNetworking.canSend(handler, RegistrySyncManager.DIRECT_PACKET_HANDLER.getPacketId()),
				handler -> ((ServerConfigurationTaskManager) handler).addImmediateTask(new RegistrySyncManager.SyncConfigurationTask(handler, RegistrySyncManager.createAndPopulateRegistryMap()))
		));
		ServerConfigurationNetworking.registerGlobalReceiver(SyncCompletePayload.ID, (payload, context) -> {
			context.networkHandler().completeTask(RegistrySyncManager.SyncConfigurationTask.KEY);
		});
	}
}
