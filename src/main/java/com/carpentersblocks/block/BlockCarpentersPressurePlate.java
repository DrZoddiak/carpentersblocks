package com.carpentersblocks.block;
 
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCarpentersPressurePlate extends BlockPressurePlate
{ 
	public BlockCarpentersPressurePlate(Material material, Sensitivity sensitivity)
	{
		super(material, sensitivity); 
	} 
	
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() 
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
}
