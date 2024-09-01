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

package net.fabricmc.fabric.impl.blockrenderlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;

public class BlockRenderLayerMapImpl implements BlockRenderLayerMap {
	public BlockRenderLayerMapImpl() { }

	@Override
	public void putBlock(Block block, RenderLayer renderLayer) {
		org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap.put(renderLayer, block);
	}

	@Override
	public void putBlocks(RenderLayer renderLayer, Block... blocks) {
		org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap.put(renderLayer, blocks);
	}

	@Override
	public void putItem(Item item, RenderLayer renderLayer) {
		if (item == null) throw new IllegalArgumentException("Request to map null item to BlockRenderLayer");
		if (renderLayer == null) throw new IllegalArgumentException("Request to map item " + item.toString() + " to null BlockRenderLayer");

		itemHandler.accept(item, renderLayer);
	}

	@Override
	public void putItems(RenderLayer renderLayer, Item... items) {
		for (Item item : items) {
			putItem(item, renderLayer);
		}
	}

	@Override
	public void putFluid(Fluid fluid, RenderLayer renderLayer) {
		org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap.put(renderLayer, fluid);
	}

	@Override
	public void putFluids(RenderLayer renderLayer, Fluid... fluids) {
		org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap.put(renderLayer, fluids);
	}

	private static Map<Item, RenderLayer> itemRenderLayerMap = new HashMap<>();

	//This consumers initially add to the maps above, and then are later set (when initialize is called) to insert straight into the target map.
	private static BiConsumer<Item, RenderLayer> itemHandler = (i, l) -> itemRenderLayerMap.put(i, l);

	public static void initialize(BiConsumer<Block, RenderLayer> blockHandlerIn) {
		//Done to handle backwards compat, in previous snapshots Items had their own map for render layers, now the BlockItem is used.
		BiConsumer<Item, RenderLayer> itemHandlerIn = (item, renderLayer) -> blockHandlerIn.accept(Block.getBlockFromItem(item), renderLayer);

		//Add all the pre existing render layers
		itemRenderLayerMap.forEach(itemHandlerIn);

		//Set the handlers to directly accept later additions
		itemHandler = itemHandlerIn;
	}
}
