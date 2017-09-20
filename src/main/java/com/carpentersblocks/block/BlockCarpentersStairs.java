package com.carpentersblocks.block;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockCarpentersStairs extends BlockStairs
{ 
	public BlockCarpentersStairs(IBlockState material)
	{
		super(material); 
	} 
	 
	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		// TODO Auto-generated method stub
		return false;
	}
}
