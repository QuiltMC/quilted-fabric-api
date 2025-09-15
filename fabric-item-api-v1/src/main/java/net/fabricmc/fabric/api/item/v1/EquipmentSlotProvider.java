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

package net.fabricmc.fabric.api.item.v1;

/**
 * A provider for the preferred equipment slot of an item.
 * This can be used to give non-armor items, such as blocks,
 * an armor slot that they can go in.
 *
 * <p>The preferred equipment slot of an item stack can be queried using
 * {@link LivingEntity#getPreferredEquipmentSlot(ItemStack) LivingEntity.getPreferredEquipmentSlot()}.
 *
 * <p>Equipment slot providers can be set with {@link FabricItem.Settings#equipmentSlot(EquipmentSlotProvider)}.
 *
 * <p>Note that items extending {@link net.minecraft.item.ArmorItem} don't need to use this
 * as there's {@link net.minecraft.item.ArmorItem#getSlotType()}.
 *
 * @deprecated Use Quilt Item Setting API's {@link org.quiltmc.qsl.item.setting.api.EquipmentSlotProvider} instead.
 */
@Deprecated
@FunctionalInterface
public interface EquipmentSlotProvider extends org.quiltmc.qsl.item.setting.api.EquipmentSlotProvider {
}
