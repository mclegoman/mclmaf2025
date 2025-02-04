/*
    dtaf2025
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/dtaf2025
    Licence: GNU LGPLv3
*/

package com.mclegoman.dtaf2025.common.world.feature.crater;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.function.Consumer;

public class CraterFeature extends Feature<CraterFeatureConfig> {
	public CraterFeature(Codec<CraterFeatureConfig> codec) {
		super(codec);
	}
	public boolean generate(FeatureContext<CraterFeatureConfig> context) {
		StructureWorldAccess structureWorldAccess = context.getWorld();
		BlockPos blockPos = structureWorldAccess.getTopPosition(Heightmap.Type.WORLD_SURFACE, context.getOrigin()).down();
		Random random = context.getRandom();
		CraterFeatureConfig config = context.getConfig();
		int radius = config.radius().get(random);
		int depth = config.depth().get(random);
		if (depth > radius) return false;
		else {
			int k = (depth * depth + radius * radius) / (2 * depth);
			BlockPos blockPos2 = blockPos.up(k - depth);
			BlockPos.Mutable mutable = blockPos.mutableCopy();
			Consumer<WorldAccess> consumer = (worldAccess) -> {
				for (int kx = -depth; kx <= k; ++kx) {
					boolean bl = false;

					for (int l = -k; l <= k; ++l) {
						for (int m = -k; m <= k; ++m) {
							mutable.set(blockPos, l, kx, m);
							if (mutable.getSquaredDistance(blockPos2) < (double)(k * k) && !worldAccess.getBlockState(mutable).isAir()) {
								bl = true;
								worldAccess.setBlockState(mutable, Blocks.AIR.getDefaultState(), 3);
							}
						}
					}
					if (!bl && kx > 0) break;
				}
			};
			if (k < 15) consumer.accept(structureWorldAccess);
			else {
				ServerWorld serverWorld = structureWorldAccess.toServerWorld();
				serverWorld.getServer().execute(() -> consumer.accept(serverWorld));
			}
			return true;
		}
	}
}
