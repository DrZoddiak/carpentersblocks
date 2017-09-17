package com.carpentersblocks.block;

import com.carpentersblocks.Reference;

import net.minecraft.block.BlockLadder;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCarpentersLadder extends BlockLadder
{ 
	public BlockCarpentersLadder(Material material) 
	{
		super(); 
	} 
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		if(Reference.enableFreeStandingLadders)
			return true;
		return super.canPlaceBlockAt(worldIn, pos);
	}
	
	@Override
	protected boolean canBlockStay(World worldIn, BlockPos pos, EnumFacing facing) 
	{
		if(Reference.enableFreeStandingLadders)
			return true;
		return super.canBlockStay(worldIn, pos, facing);
	} 
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
			int meta, EntityLivingBase placer) 
	{
		if(Reference.enableFreeStandingLadders)
		{
			facing = placer.getHorizontalFacing().getOpposite();
		}
		return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
	}
}
