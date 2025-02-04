/*
    dtaf2025
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/dtaf2025
    Licence: GNU LGPLv3
*/

package com.mclegoman.dtaf2025.mixin.client.dimension;

import com.mclegoman.dtaf2025.client.world.dimension.DimensionRegistry;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionEffects.class)
public abstract class DimensionEffectsMixin {
	@Inject(method = "byDimensionType", at = @At("HEAD"), cancellable = true)
	private static void dtaf2025$byDimensionType(DimensionType dimensionType, CallbackInfoReturnable<DimensionEffects> cir) {
		DimensionEffects effects = DimensionRegistry.getEffectType(dimensionType.effects());
		if (effects != null) cir.setReturnValue(effects);
	}
}