/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
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

package net.fabricmc.fabric.mixin.crash.report.info;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.util.SystemDetails;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

@Mixin(SystemDetails.class)
public abstract class SystemDetailsMixin {
	@Shadow
	public abstract void addSection(String string, Supplier<String> supplier);

	@Shadow
	public abstract void addSection(String name, String value);

	@Inject(at = @At("RETURN"), method = "<init>")
	private void fillSystemDetails(CallbackInfo info) {
		addSection("Quilted Fabric API", "!! WARNING !! This instance is using Fabric API modules "
				+ "re-implemented by QSL. If the issue comes from Quilted Fabric API, "
				+ "DO NOT report to Fabric; report them to Quilt instead!");
		if (true) return; // Quilt: we already add the mods
		addSection("Fabric Mods", () -> {
			ArrayList<ModContainer> topLevelMods = new ArrayList<>();

			for (ModContainer container : FabricLoader.getInstance().getAllMods()) {
				if (container.getContainingMod().isEmpty()) {
					topLevelMods.add(container);
				}
			}

			StringBuilder modString = new StringBuilder();

			appendMods(modString, 2, topLevelMods);

			return modString.toString();
		});
	}

	private static void appendMods(StringBuilder modString, int depth, ArrayList<ModContainer> mods) {
		mods.sort(Comparator.comparing(mod -> mod.getMetadata().getId()));

		for (ModContainer mod : mods) {
			modString.append('\n');
			modString.append("\t".repeat(depth));
			modString.append(mod.getMetadata().getId());
			modString.append(": ");
			modString.append(mod.getMetadata().getName());
			modString.append(' ');
			modString.append(mod.getMetadata().getVersion().getFriendlyString());

			if (!mod.getContainedMods().isEmpty()) {
				ArrayList<ModContainer> childMods = new ArrayList<>(mod.getContainedMods());
				appendMods(modString, depth + 1, childMods);
			}
		}
	}
}
