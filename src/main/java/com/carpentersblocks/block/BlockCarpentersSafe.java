package com.carpentersblocks.block;

import com.carpentersblocks.util.handler.ChatHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCarpentersSafe extends Block
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool LOCKED = PropertyBool.create("locked");  
	
	public BlockCarpentersSafe(Material material) 
	{
		//@TODO rotation 
		super(material); 
		this.setDefaultState(this.blockState.getBaseState().withProperty(LOCKED, Boolean.valueOf(true)));
				//.withProperty(FACING, EnumFacing.NORTH) 
	} 
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {LOCKED});
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{ 
		return state.getValue(LOCKED).booleanValue() == true ?  0:1;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) 
	{
		return this.getDefaultState().withProperty(LOCKED, meta == 0); 
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(playerIn.isSneaking())
		{
			worldIn.setBlockState(pos, state.withProperty(LOCKED, state.getValue(LOCKED).booleanValue() == false ? true:false));
			if(worldIn.isRemote)
				ChatHandler.sendMessageToPlayer("You have "+(state.getValue(LOCKED).booleanValue() == true ? "UNLOCKED ":"LOCKED ")+"the safe!", playerIn);
			return true;
		}
		else
		{
			
			if(state.getValue(LOCKED).booleanValue())
				if(worldIn.isRemote)
					ChatHandler.sendMessageToPlayer("I am LOCKED, you cannot view my contents!", playerIn);
		}
		return true;
	}   
} 