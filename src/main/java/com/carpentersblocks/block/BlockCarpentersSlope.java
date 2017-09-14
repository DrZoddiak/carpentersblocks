package com.carpentersblocks.block;


import com.carpentersblocks.block.types.BlockCoverable;

import net.minecraft.block.material.Material;

public class BlockCarpentersSlope extends BlockCoverable 
{ 
    public final static String slopeType[] = { "wedge", "obliqueInterior", "obliqueExterior", "prism", "prismWedge" };
    public final static String baseUnlocalName = "blockCarpentersSlope.";

    public BlockCarpentersSlope(Material material)
    {
        super(material);
    } 
}