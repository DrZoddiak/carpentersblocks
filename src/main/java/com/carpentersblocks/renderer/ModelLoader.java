package com.carpentersblocks.renderer;

import com.carpentersblocks.Reference;
import com.carpentersblocks.renderer.model.ModelBlock;
import com.carpentersblocks.renderer.model.ModelCollapsible;
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
	
    public static final ModelBlock BLOCK_MODEL = new ModelBlock();
    public static final ModelCollapsible COLLAPSIBLE_MODEL = new ModelCollapsible();

    @Override
    public boolean accepts(ResourceLocation modelLocation)
    {
    	return modelLocation.getResourceDomain().equals( MOD_ID) && Reference.REGISTRY_NAME_BLOCK.equals(modelLocation.getResourcePath()) ||
    		   modelLocation.getResourceDomain().equals( MOD_ID) && Reference.REGISTRY_NAME_COLLAPSIBLE.equals(modelLocation.getResourcePath());
    }

    @Override
    public IModel loadModel(ResourceLocation resourceLocation) throws Exception
    {
    	if (isModel(resourceLocation, Reference.REGISTRY_NAME_COLLAPSIBLE)) 
    	{
    		return COLLAPSIBLE_MODEL;
    	}
        return BLOCK_MODEL;
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) { }
    
    private boolean isModel(ResourceLocation resourceLocation, String registryName) 
    {
    	return resourceLocation.getResourceDomain().equals( MOD_ID) && registryName.equals(resourceLocation.getResourcePath());
    } 
}