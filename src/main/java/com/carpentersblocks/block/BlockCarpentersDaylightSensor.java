package com.carpentersblocks.block;

import net.minecraft.block.BlockDaylightDetector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCarpentersDaylightSensor extends BlockDaylightDetector
{
	private boolean inverted;
	
	public BlockCarpentersDaylightSensor(boolean inverted) 
	{
		super(inverted);
		this.inverted = inverted;
	} 

}
