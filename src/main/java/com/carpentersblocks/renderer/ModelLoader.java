package com.carpentersblocks.renderer;

import com.carpentersblocks.Reference;
import com.carpentersblocks.renderer.model.ModelBed;
import com.carpentersblocks.renderer.model.ModelBlock;
import com.carpentersblocks.renderer.model.ModelCollapsible;
import com.carpentersblocks.renderer.model.ModelFlowerPot;
import com.carpentersblocks.renderer.model.ModelSafe;
import com.carpentersblocks.renderer.model.ModelSlope;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import static com.carpentersblocks.Reference.*;
public class ModelLoader implements ICustomModelLoader
{ 
    public static final ModelResourceLocation RESOURCE_BLOCK = new ModelResourceLocation( MOD_ID + ":block/carpenters_block");
    public static final ModelResourceLocation RESOURCE_COLLAPSIBLE_BLOCK = new ModelResourceLocation( MOD_ID + ":block/carpenters_collapsible_block");
    public static final ModelResourceLocation RESOURCE_SLOPE = new ModelResourceLocation( MOD_ID + ":block/carpenters_slope");
    public static final ModelResourceLocation RESOURCE_SAFE = new ModelResourceLocation( MOD_ID + ":block/carpenters_safe");
    public static final ModelResourceLocation RESOURCE_FLOWER_POT = new ModelResourceLocation( MOD_ID + ":block/carpenters_flower_pot");
    public static final ModelResourceLocation RESOURCE_BED = new ModelResourceLocation( MOD_ID + ":block/carpenters_bed");
    
    public static final ModelBlock BLOCK_MODEL = new ModelBlock();
    public static final ModelCollapsible COLLAPSIBLE_MODEL = new ModelCollapsible();
    public static final IModel SLOPE_MODEL = new ModelSlope(); 
    public static final IModel SAFE_MODEL = new ModelSafe();
    public static final IModel FLOWER_MODEL = new ModelFlowerPot();
    public static final IModel BED_MODEL = new ModelBed();
    
    @Override
    public boolean accepts(ResourceLocation modelLocation)
    {
    	String path = modelLocation.getResourcePath();
    	
    	if(modelLocation.getResourceDomain().equals(MOD_ID))
    			if(path.equals(REGISTRY_NAME_BLOCK) ||
    					 path.equals(REGISTRY_NAME_COLLAPSIBLE) ||
    					 path.equals(REGISTRY_NAME_SLOPE) ||
    					 path.equals(REGISTRY_NAME_SAFE) ||
    			     	 path.equals(REGISTRY_NAME_FLOWER_POT) ||
    			     	 path.equals(REGISTRY_NAME_BED)) 
    				return true;
		return false;
    }

    @Override
    public IModel loadModel(ResourceLocation resourceLocation) throws Exception
    {	
    	if (isModel(resourceLocation, Reference.REGISTRY_NAME_COLLAPSIBLE)) 
    	{
    		return COLLAPSIBLE_MODEL;
    	} 
    	else if (isModel(resourceLocation, Reference.REGISTRY_NAME_BED)) 
    	{
    		return BED_MODEL;
    	}
    	else if (isModel(resourceLocation, Reference.REGISTRY_NAME_FLOWER_POT)) 
    	{
    		return FLOWER_MODEL;
    	}
    	else if (isModel(resourceLocation, Reference.REGISTRY_NAME_SLOPE)) 
    	{
    		return SLOPE_MODEL;
    	}
    	else if (isModel(resourceLocation, Reference.REGISTRY_NAME_SAFE)) 
    	{
    		return SAFE_MODEL;
    	}
        return BLOCK_MODEL;
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager){}
    
    private boolean isModel(ResourceLocation resourceLocation, String registryName) 
    {
    	return resourceLocation.getResourceDomain().equals(MOD_ID) && registryName.equals(resourceLocation.getResourcePath());
    } 
}