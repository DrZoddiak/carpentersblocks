package com.carpentersblocks.block;

import net.minecraft.block.BlockButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCarpentersButton extends BlockButton
{

	public BlockCarpentersButton(boolean wooden) 
	{
		super(wooden); 
	}

	@Override
	protected void playClickSound(EntityPlayer player, World worldIn, BlockPos pos) {}

	@Override
	protected void playReleaseSound(World worldIn, BlockPos pos) {}

}
