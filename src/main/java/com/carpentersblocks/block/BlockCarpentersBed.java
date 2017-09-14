package com.carpentersblocks.block;

import net.minecraft.block.BlockBed;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockCarpentersBed extends BlockBed
{

	public BlockCarpentersBed(Material material) {
		super(); 
		// TODO Auto-generated constructor stub
	} 
	
	@Override
	public boolean isBed(IBlockState state, IBlockAccess world, BlockPos pos, Entity player) {
		// TODO Auto-generated method stub
		return true;
	}
	
	
}