/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
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

package net.fabricmc.fabric.api.client.event.lifecycle.v1;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.impl.base.event.QuiltCompatEvent;

/**
 * @deprecated see {@link org.quiltmc.qsl.entity.event.api.client.ClientEntityLoadEvents ClientEntityLoadEvents}
 */
@Deprecated
public final class ClientEntityEvents {
	private ClientEntityEvents() {
	}

	/**
	 * Called when an Entity is loaded into a ClientWorld.
	 *
	 * <p>When this event is called, the chunk is already in the world.
	 */
	public static final Event<ClientEntityEvents.Load> ENTITY_LOAD = QuiltCompatEvent.fromQuilt(
			org.quiltmc.qsl.entity.event.api.client.ClientEntityLoadEvents.AFTER_LOAD,
			load -> load::onLoad,
			invokerGetter -> (entity, world) -> invokerGetter.get().onLoadClient(entity, world)
	);

	/**
	 * Called when an Entity is about to be unloaded from a ClientWorld.
	 *
	 * <p>This event is called before the entity is unloaded from the world.
	 */
	public static final Event<ClientEntityEvents.Unload> ENTITY_UNLOAD = QuiltCompatEvent.fromQuilt(
			org.quiltmc.qsl.entity.event.api.client.ClientEntityLoadEvents.AFTER_UNLOAD,
			unload -> unload::onUnload,
			invokerGetter -> (entity, world) -> invokerGetter.get().onUnloadClient(entity, world)
	);

	@FunctionalInterface
	public interface Load {
		void onLoad(Entity entity, ClientWorld world);
	}

	@FunctionalInterface
	public interface Unload {
		void onUnload(Entity entity, ClientWorld world);
	}
}
