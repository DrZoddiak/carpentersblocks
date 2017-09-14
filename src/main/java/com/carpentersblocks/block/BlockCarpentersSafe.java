package com.carpentersblocks.block;

import com.carpentersblocks.block.types.BlockCoverable;
import com.carpentersblocks.tileentity.CbTileEntity;
import com.carpentersblocks.util.protection.IProtected;
import com.carpentersblocks.util.protection.ProtectedObject;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCarpentersSafe extends BlockCoverable 
{
	public BlockCarpentersSafe(Material material) 
	{
		super(material);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
		CbTileEntity cbTileEntity = getTileEntity(world, pos);
		if(cbTileEntity.getOwner().equals(entity.getName()))
			return false;
		return true;
	}
	
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos blockPos, IBlockState blockState,
			EntityLivingBase entityLivingBase, ItemStack itemStack) 
	{
		if (!world.isRemote) 
		{
	        CbTileEntity cbTileEntity = getTileEntity(world, blockPos);
	        if (cbTileEntity != null) 
	        {
	            cbTileEntity.setOwner(new ProtectedObject((EntityPlayer)entityLivingBase));
	        }
	    }
	}

} 