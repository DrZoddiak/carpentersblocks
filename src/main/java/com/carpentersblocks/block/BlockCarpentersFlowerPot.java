package com.carpentersblocks.block;

import java.util.Random;

import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class BlockCarpentersFlowerPot extends BlockFlowerPot
{ 
	public BlockCarpentersFlowerPot(Material material)
	{
		super(); 
	} 
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		// TODO Auto-generated method stub
		return Item.getItemFromBlock(this);
	}
}
