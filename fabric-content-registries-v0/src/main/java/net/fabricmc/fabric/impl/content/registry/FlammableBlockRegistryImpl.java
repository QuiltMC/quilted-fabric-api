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

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.registry.tag.TagKey;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;

import org.quiltmc.qsl.block.content.registry.api.BlockContentRegistries;
import org.quiltmc.qsl.block.content.registry.api.FlammableBlockEntry;
import org.quiltmc.quilted_fabric_api.impl.content.registry.util.QuiltDeferringQueues;

public class FlammableBlockRegistryImpl implements FlammableBlockRegistry {
	private static final FlammableBlockEntry DEFAULT_ENTRY = new FlammableBlockEntry(0, 0);
	private static final Map<Block, FlammableBlockRegistryImpl> REGISTRIES = new HashMap<>();

	private FlammableBlockRegistryImpl(Block key) { }

	// User-facing fire registry interface - queries vanilla fire block
	@Override
	public Entry get(Block block) {
		return Entry.fromQuilt(BlockContentRegistries.FLAMMABLE.get(block).orElse(DEFAULT_ENTRY));
	}

	@Override
	public void add(Block block, Entry value) {
		QuiltDeferringQueues.addEntry(BlockContentRegistries.FLAMMABLE, block, value.toQuilt());
	}

	@Override
	public void add(TagKey<Block> tag, Entry value) {
		BlockContentRegistries.FLAMMABLE.put(tag, value.toQuilt());
	}

	@Override
	public void remove(Block block) {
		BlockContentRegistries.FLAMMABLE.put(block, DEFAULT_ENTRY);
	}

	@Override
	public void remove(TagKey<Block> tag) {
		BlockContentRegistries.FLAMMABLE.put(tag, DEFAULT_ENTRY);
	}

	@Override
	public void clear(Block block) {
		BlockContentRegistries.FLAMMABLE.remove(block);
	}

	@Override
	public void clear(TagKey<Block> tag) {
		BlockContentRegistries.FLAMMABLE.remove(tag);
	}

	public static FlammableBlockRegistryImpl getInstance(Block block) {
		return REGISTRIES.computeIfAbsent(block, FlammableBlockRegistryImpl::new);
	}
}
