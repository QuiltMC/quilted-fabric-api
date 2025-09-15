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

package net.fabricmc.fabric.impl.content.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.tag.TagKey;

import net.fabricmc.fabric.api.registry.FuelRegistry;

import org.quiltmc.qsl.item.content.registry.api.ItemContentRegistries;
import org.quiltmc.quilted_fabric_api.impl.content.registry.util.QuiltDeferringQueues;

// Fabric To-Do: Clamp values to 32767 (+ add hook for mods which extend the limit to disable the check?)
// About that, Quilt's equivalent API supports integer values properly, no need for that
public final class FuelRegistryImpl implements FuelRegistry {
	public FuelRegistryImpl() { }

	@Override
	public Integer get(ItemConvertible item) {
		return ItemContentRegistries.FUEL_TIMES.getNullable(item.asItem());
	}

	@Override
	public void add(ItemConvertible item, Integer cookTime) {
		QuiltDeferringQueues.addEntryWithItemConvertible(ItemContentRegistries.FUEL_TIMES, item, cookTime);
	}

	@Override
	public void add(TagKey<Item> tag, Integer cookTime) {
		ItemContentRegistries.FUEL_TIMES.put(tag, cookTime);
	}

	@Override
	public void remove(ItemConvertible item) {
		ItemContentRegistries.FUEL_TIMES.put(item.asItem(), 0);
	}

	@Override
	public void remove(TagKey<Item> tag) {
		ItemContentRegistries.FUEL_TIMES.put(tag, 0);
	}

	@Override
	public void clear(ItemConvertible item) {
		ItemContentRegistries.FUEL_TIMES.remove(item.asItem());
	}

	@Override
	public void clear(TagKey<Item> tag) {
		ItemContentRegistries.FUEL_TIMES.remove(tag);
	}
}
