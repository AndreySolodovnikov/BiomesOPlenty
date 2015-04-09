/*******************************************************************************
 * Copyright 2015, the Biomes O' Plenty Team
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 * 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/

package biomesoplenty.common.world.feature.weighted;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import biomesoplenty.common.block.BlockDecoration;
import biomesoplenty.common.util.biome.GeneratorUtils;

public class GeneratorGrass extends GeneratorFlora
{
    @Override
    public void scatter(World world, Random random, BlockPos pos, int amountPerChunk)
    {
        for (int i = 0; i < amountPerChunk; i++)
        {
            int x = random.nextInt(16) + 8;
            int z = random.nextInt(16) + 8;
            BlockPos genPos = pos.add(x, 0, z);
            int y = GeneratorUtils.safeNextInt(random, world.getHeight(genPos).getY() * 2);
            genPos = genPos.add(0, y, 0);

            generate(world, random, genPos, amountPerChunk);
        }
    }

    @Override
    public boolean generate(World world, Random random, BlockPos pos, int amountPerChunk)
    {
        Block block;

        do
        {
            block = world.getBlockState(pos).getBlock();
            if (!block.isAir(world, pos) && !block.isLeaves(world, pos)) break;
            pos = pos.down();
        } 
        while (pos.getY() > 0);

        for (int i = 0; i < 128; i++)
        {
            BlockPos genPos = pos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
            boolean canStay = block instanceof BlockDecoration ? ((BlockDecoration)block).canBlockStay(world, genPos, this.state) : block.canPlaceBlockAt(world, genPos);
            
            if (world.isAirBlock(genPos) && canStay)
            {
                world.setBlockState(genPos, this.state, 2);
            }
        }

        return true;
    }
}
