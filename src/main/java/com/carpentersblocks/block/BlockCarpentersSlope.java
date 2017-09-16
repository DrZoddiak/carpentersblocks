package com.carpentersblocks.block;


import java.util.List;

import com.carpentersblocks.block.types.BlockCoverable;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlockCarpentersSlope extends BlockCoverable 
{ 
    public final static String slopeType[] = { "wedge", "obliqueInterior", "obliqueExterior", "prism", "prismWedge" };
    public final static String baseUnlocalName = "tile.blockCarpentersSlope.";

    public BlockCarpentersSlope(Material material)
    {
        super(material); 
    }
    
    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) 
    {
    	for(int i = 0; i<slopeType.length; i++)
    		list.add(new ItemStack(itemIn, 1, i));
  //  	list.add( new ItemStack(new BlockCarpentersSlope(Material.WOOD).setUnlocalizedName(baseUnlocalName)+slopeType[i])))
    }
    
    @Override
    public String getUnlocalizedName() 
    {
    	return getUnlocalizedName(new ItemStack(this));
    }
    
    public String getUnlocalizedName(ItemStack stack) 
    {
    	switch(stack.getItemDamage())
    	{
    		case 0:
    			return baseUnlocalName+slopeType[0];
    		case 1:
    			return baseUnlocalName+slopeType[1];
    		case 2:
    			return baseUnlocalName+slopeType[2];
    		case 3:
    			return baseUnlocalName+slopeType[3];
    		case 4:
    			return baseUnlocalName+slopeType[4];
    	}
    	return "test";
    }
}