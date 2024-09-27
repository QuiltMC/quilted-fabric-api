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

package net.fabricmc.fabric.api.event.lifecycle.v1;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.impl.base.event.QuiltCompatEvent;

public final class ServerEntityEvents {
	private ServerEntityEvents() {
	}

	/**
	 * Called when an Entity is loaded into a ServerWorld.
	 *
	 * <p>When this event is called, the entity is already in the world.
	 *
	 * @deprecated Use Quilt Entity Events' {@link org.quiltmc.qsl.entity.event.api.ServerEntityLoadEvents#AFTER_LOAD} instead.
	 */
	@Deprecated
	public static final Event<ServerEntityEvents.Load> ENTITY_LOAD = QuiltCompatEvent.fromQuilt(
			org.quiltmc.qsl.entity.event.api.ServerEntityLoadEvents.AFTER_LOAD,
			load -> load::onLoad,
			invokerGetter -> (entity, world) -> invokerGetter.get().onLoad(entity, world)
	);

	/**
	 * Called when an Entity is unloaded from a ServerWorld.
	 *
	 * <p>This event is called before the entity is removed from the world.
	 *
	 * @deprecated Use Quilt Entity Events' {@link org.quiltmc.qsl.entity.event.api.ServerEntityLoadEvents#AFTER_UNLOAD} instead.
	 */
	@Deprecated
	public static final Event<ServerEntityEvents.Unload> ENTITY_UNLOAD = QuiltCompatEvent.fromQuilt(
			org.quiltmc.qsl.entity.event.api.ServerEntityLoadEvents.AFTER_UNLOAD,
			unload -> unload::onUnload,
			invokerGetter -> (entity, world) -> invokerGetter.get().onUnload(entity, world)
	);

	/**
	 * Called during {@link LivingEntity#tick()} if the Entity's equipment has been changed or mutated.
	 *
	 * <p>This event is also called when the entity joins the world.
	 * A change in equipment is determined by {@link ItemStack#areEqual(ItemStack, ItemStack)}.
	 */
	public static final Event<EquipmentChange> EQUIPMENT_CHANGE = EventFactory.createArrayBacked(ServerEntityEvents.EquipmentChange.class, callbacks -> (livingEntity, equipmentSlot, previous, next) -> {
		for (EquipmentChange callback : callbacks) {
			callback.onChange(livingEntity, equipmentSlot, previous, next);
		}
	});

	@FunctionalInterface
	public interface Load {
		void onLoad(Entity entity, ServerWorld world);
	}

	@FunctionalInterface
	public interface Unload {
		void onUnload(Entity entity, ServerWorld world);
	}

	@FunctionalInterface
	public interface EquipmentChange {
		void onChange(LivingEntity livingEntity, EquipmentSlot equipmentSlot, ItemStack previousStack, ItemStack currentStack);
	}
}
