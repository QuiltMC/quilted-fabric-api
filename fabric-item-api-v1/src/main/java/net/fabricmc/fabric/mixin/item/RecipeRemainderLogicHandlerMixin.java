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

package net.fabricmc.fabric.mixin.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import org.quiltmc.qsl.item.setting.api.RecipeRemainderProvider;

@Mixin(org.quiltmc.qsl.item.setting.api.RecipeRemainderLogicHandler.class)
public interface RecipeRemainderLogicHandlerMixin {
	@ModifyVariable(method = "getRemainder", index = 4, at = @At(value = "INVOKE", target = "Ljava/util/Set;contains(Ljava/lang/Object;)Z"))
	private static RecipeRemainderProvider setDefaultProvider(RecipeRemainderProvider value) {
		return (original, recipe) -> original.getItem().getRecipeRemainder(original);
	}
}
