package com.carpentersblocks.util.registry;

import com.carpentersblocks.CarpentersBlocks;
import com.carpentersblocks.Reference;
import com.carpentersblocks.block.BlockCarpentersBarrier;
import com.carpentersblocks.block.BlockCarpentersBed;
import com.carpentersblocks.block.BlockCarpentersBlock;
import com.carpentersblocks.block.BlockCarpentersButton;
import com.carpentersblocks.block.BlockCarpentersCollapsibleBlock;
import com.carpentersblocks.block.BlockCarpentersDaylightSensor;
import com.carpentersblocks.block.BlockCarpentersDoor;
import com.carpentersblocks.block.BlockCarpentersFlowerPot;
import com.carpentersblocks.block.BlockCarpentersGarageDoor;
import com.carpentersblocks.block.BlockCarpentersGate;
import com.carpentersblocks.block.BlockCarpentersHatch;
import com.carpentersblocks.block.BlockCarpentersLadder;
import com.carpentersblocks.block.BlockCarpentersLever;
import com.carpentersblocks.block.BlockCarpentersPressurePlate;
import com.carpentersblocks.block.BlockCarpentersSafe;
import com.carpentersblocks.block.BlockCarpentersSlope;
import com.carpentersblocks.block.BlockCarpentersStairs;
import com.carpentersblocks.block.BlockCarpentersTorch;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPressurePlate.Sensitivity;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;
import static com.carpentersblocks.Reference.*;

public class BlockRegistry 
{	
	private static final Material material = Material.WOOD; 
	
    public static Block blockCarpentersBarrier;
    public static Block blockCarpentersBed;
    public static Block blockCarpentersBlock;
    public static Block blockCarpentersButton;
    public static Block blockCarpentersCollapsibleBlock;
    public static Block blockCarpentersDaylightSensor;
    public static Block blockCarpentersDoor;
    public static Block blockCarpentersFlowerPot;
    public static Block blockCarpentersGarageDoor;
    public static Block blockCarpentersGate;
    public static Block blockCarpentersHatch;
    public static Block blockCarpentersLadder;
    public static Block blockCarpentersLever;
    public static Block blockCarpentersPressurePlate;
    public static Block blockCarpentersSafe;
    public static Block blockCarpentersSlope;
    public static Block blockCarpentersStairs;
    public static Block blockCarpentersTorch;

    /**
     * Block preinitialization.
     */
    public static void preInit(FMLPreInitializationEvent event, Configuration config)
    {
        enableBarrier          = config.get("blocks",           "Enable Barrier",          enableBarrier).getBoolean(enableBarrier);
        enableBed              = config.get("blocks",               "Enable Bed",              enableBed).getBoolean(enableBed);
        enableButton           = config.get("blocks",            "Enable Button",           enableButton).getBoolean(enableButton);
        enableCollapsibleBlock = config.get("blocks", "Enable Collapsible Block", enableCollapsibleBlock).getBoolean(enableCollapsibleBlock);
        enableDaylightSensor   = config.get("blocks",   "Enable Daylight Sensor",   enableDaylightSensor).getBoolean(enableDaylightSensor);
        enableDoor             = config.get("blocks",              "Enable Door",             enableDoor).getBoolean(enableDoor);
        enableFlowerPot        = config.get("blocks",        "Enable Flower Pot",        enableFlowerPot).getBoolean(enableFlowerPot);
        enableGarageDoor       = config.get("blocks",       "Enable Garage Door",       enableGarageDoor).getBoolean(enableGarageDoor);
        enableGate             = config.get("blocks",              "Enable Gate",             enableGate).getBoolean(enableGate);
        enableHatch            = config.get("blocks",             "Enable Hatch",            enableHatch).getBoolean(enableHatch);
        enableLadder           = config.get("blocks",            "Enable Ladder",           enableLadder).getBoolean(enableLadder);
        enableLever            = config.get("blocks",             "Enable Lever",            enableLever).getBoolean(enableLever);
        enablePressurePlate    = config.get("blocks",    "Enable Pressure Plate",    enablePressurePlate).getBoolean(enablePressurePlate);
        enableSafe             = config.get("blocks",              "Enable Safe",             enableSafe).getBoolean(enableSafe);
        enableSlope            = config.get("blocks",             "Enable Slope",            enableSlope).getBoolean(enableSlope);
        enableStairs           = config.get("blocks",            "Enable Stairs",           enableStairs).getBoolean(enableStairs);
        enableTorch            = config.get("blocks",             "Enable Torch",            enableTorch).getBoolean(enableTorch);

        recipeQuantityBarrier          = config.get("recipe quantities",           "Barrier",          recipeQuantityBarrier).getInt(recipeQuantityBarrier);
        recipeQuantityBed              = config.get("recipe quantities",               "Bed",              recipeQuantityBed).getInt(recipeQuantityBed);
        recipeQuantityBlock            = config.get("recipe quantities",             "Block",            recipeQuantityBlock).getInt(recipeQuantityBlock);
        recipeQuantityButton           = config.get("recipe quantities",            "Button",           recipeQuantityButton).getInt(recipeQuantityButton);
        recipeQuantityCollapsibleBlock = config.get("recipe quantities", "Collapsible Block", recipeQuantityCollapsibleBlock).getInt(recipeQuantityCollapsibleBlock);
        recipeQuantityDaylightSensor   = config.get("recipe quantities",   "Daylight Sensor",   recipeQuantityDaylightSensor).getInt(recipeQuantityDaylightSensor);
        recipeQuantityDoor             = config.get("recipe quantities",              "Door",             recipeQuantityDoor).getInt(recipeQuantityDoor);
        recipeQuantityFlowerPot        = config.get("recipe quantities",        "Flower Pot",        recipeQuantityFlowerPot).getInt(recipeQuantityFlowerPot);
        recipeQuantityGarageDoor       = config.get("recipe quantities",       "Garage Door",       recipeQuantityGarageDoor).getInt(recipeQuantityGarageDoor);
        recipeQuantityGate             = config.get("recipe quantities",              "Gate",             recipeQuantityGate).getInt(recipeQuantityGate);
        recipeQuantityHatch            = config.get("recipe quantities",             "Hatch",            recipeQuantityHatch).getInt(recipeQuantityHatch);
        recipeQuantityLadder           = config.get("recipe quantities",            "Ladder",           recipeQuantityLadder).getInt(recipeQuantityLadder);
        recipeQuantityLever            = config.get("recipe quantities",             "Lever",            recipeQuantityLever).getInt(recipeQuantityLever);
        recipeQuantityPressurePlate    = config.get("recipe quantities",    "Pressure Plate",    recipeQuantityPressurePlate).getInt(recipeQuantityPressurePlate);
        recipeQuantitySafe             = config.get("recipe quantities",              "Safe",             recipeQuantitySafe).getInt(recipeQuantitySafe);
        recipeQuantitySlope            = config.get("recipe quantities",             "Slope",            recipeQuantitySlope).getInt(recipeQuantitySlope);
        recipeQuantityStairs           = config.get("recipe quantities",            "Stairs",           recipeQuantityStairs).getInt(recipeQuantityStairs);
        recipeQuantityTorch            = config.get("recipe quantities",             "Torch",            recipeQuantityTorch).getInt(recipeQuantityTorch);

        // Register blocks
        
    	blockCarpentersBlock = new BlockCarpentersBlock(material)
    		.setUnlocalizedName("blockCarpentersBlock")
        	.setRegistryName(REGISTRY_NAME_BLOCK)
        	.setHardness(0.2F)
            .setCreativeTab(CarpentersBlocks.CREATIVE_TAB);
        GameRegistry.register(blockCarpentersBlock);
        GameRegistry.register(new ItemBlock(blockCarpentersBlock), blockCarpentersBlock.getRegistryName());
        Blocks.FIRE.setFireInfo(blockCarpentersBlock, 5, 20);

         if(enableBarrier)
         {
             blockCarpentersBarrier = new BlockCarpentersBarrier(material)
                 .setUnlocalizedName("blockCarpentersBarrier")
                 .setRegistryName(REGISTRY_NAME_BARRIER)
                 .setHardness(0.2F)
                 .setCreativeTab(CarpentersBlocks.CREATIVE_TAB);
             GameRegistry.register(blockCarpentersBarrier);
       //      GameRegistry.register(blockCarpentersBarrier, blockCarpentersBarrier.getRegistryName());
             Blocks.FIRE.setFireInfo(blockCarpentersBarrier, 5, 20);
         }
        
        if(enableBed) 
        {
            blockCarpentersBed = new BlockCarpentersBed(material)
                .setUnlocalizedName("blockCarpentersBed")
                .setRegistryName(REGISTRY_NAME_BED)
                .setHardness(0.4F)
                .setCreativeTab(CarpentersBlocks.CREATIVE_TAB); 
            GameRegistry.register(blockCarpentersBed);
            GameRegistry.register(new ItemBlock(blockCarpentersBed), blockCarpentersBed.getRegistryName());
            Blocks.FIRE.setFireInfo(blockCarpentersBed, 5, 20);
        }
        
        if(enableButton) 
        {
            blockCarpentersButton = new BlockCarpentersButton(true)
                .setUnlocalizedName("blockCarpentersButton")
                .setRegistryName(REGISTRY_NAME_BUTTON)
                .setHardness(0.2F)
                .setCreativeTab(CarpentersBlocks.CREATIVE_TAB);
            GameRegistry.register(blockCarpentersButton);
            GameRegistry.register(new ItemBlock(blockCarpentersButton), blockCarpentersButton.getRegistryName());
            Blocks.FIRE.setFireInfo(blockCarpentersButton, 5, 20);
        }

        if(enableCollapsibleBlock) 
        {
            blockCarpentersCollapsibleBlock = new BlockCarpentersCollapsibleBlock(material)
                .setUnlocalizedName("blockCarpentersCollapsibleBlock")
                .setRegistryName(REGISTRY_NAME_COLLAPSIBLE)
                .setHardness(0.2F)
                .setCreativeTab(CarpentersBlocks.CREATIVE_TAB);
            GameRegistry.register(blockCarpentersCollapsibleBlock);
            GameRegistry.register(new ItemBlock(blockCarpentersCollapsibleBlock), blockCarpentersCollapsibleBlock.getRegistryName());
            Blocks.FIRE.setFireInfo(blockCarpentersCollapsibleBlock, 5, 20);
        } 
        
        if(enableDaylightSensor) 
        {
            blockCarpentersDaylightSensor = new BlockCarpentersDaylightSensor(false)
                .setUnlocalizedName("blockCarpentersDaylightSensor")
                .setRegistryName(REGISTRY_NAME_DAYLIGHT_SENSOR)
                .setHardness(0.2F)
                .setCreativeTab(CarpentersBlocks.CREATIVE_TAB);
            GameRegistry.register(blockCarpentersDaylightSensor);
            GameRegistry.register(new ItemBlock(blockCarpentersDaylightSensor), blockCarpentersDaylightSensor.getRegistryName());
            Blocks.FIRE.setFireInfo(blockCarpentersDaylightSensor, 5, 20);
        }

        if(enableDoor) 
        {
            blockCarpentersDoor = new BlockCarpentersDoor(material)
                .setUnlocalizedName("blockCarpentersDoor")
                .setRegistryName(REGISTRY_NAME_DOOR)
                .setHardness(0.2F)
                .setCreativeTab(CarpentersBlocks.CREATIVE_TAB);
            GameRegistry.register(blockCarpentersDoor);
            GameRegistry.register(new ItemBlock(blockCarpentersDoor), blockCarpentersDoor.getRegistryName());
            Blocks.FIRE.setFireInfo(blockCarpentersDoor, 5, 20);
        }

        if(enableFlowerPot) 
        {
            blockCarpentersFlowerPot = new BlockCarpentersFlowerPot(Material.CIRCUITS)
                .setUnlocalizedName("blockCarpentersFlowerPot")
                .setRegistryName(REGISTRY_NAME_FLOWER_POT)
                .setHardness(0.5F)
                .setCreativeTab(CarpentersBlocks.CREATIVE_TAB);
            GameRegistry.register(blockCarpentersFlowerPot);
            GameRegistry.register(new ItemBlock(blockCarpentersFlowerPot), blockCarpentersFlowerPot.getRegistryName());
            Blocks.FIRE.setFireInfo(blockCarpentersFlowerPot, 5, 20);
        }

        if(enableGarageDoor) 
        {
            blockCarpentersGarageDoor = new BlockCarpentersGarageDoor(material)
                .setUnlocalizedName("blockCarpentersGarageDoor")
                .setRegistryName(REGISTRY_NAME_GARAGE_DOOR)
                .setHardness(0.2F)
                .setCreativeTab(CarpentersBlocks.CREATIVE_TAB);
            GameRegistry.register(blockCarpentersGarageDoor);
            GameRegistry.register(new ItemBlock(blockCarpentersGarageDoor), blockCarpentersGarageDoor.getRegistryName());
            Blocks.FIRE.setFireInfo(blockCarpentersGarageDoor, 5, 20);
        }

        if(enableGate) 
        {
            blockCarpentersGate = new BlockCarpentersGate(material)
                .setUnlocalizedName("blockCarpentersGate")
                .setRegistryName(REGISTRY_NAME_GATE)
                .setHardness(0.2F)
                .setCreativeTab(CarpentersBlocks.CREATIVE_TAB);
            GameRegistry.register(blockCarpentersGate);
            GameRegistry.register(new ItemBlock(blockCarpentersGate), blockCarpentersGate.getRegistryName());
            Blocks.FIRE.setFireInfo(blockCarpentersGate, 5, 20);
        }

        if(enableHatch) 
        {
            blockCarpentersHatch = new BlockCarpentersHatch(material)
                .setUnlocalizedName("blockCarpentersHatch")
                .setRegistryName(REGISTRY_NAME_HATCH)
                .setHardness(0.2F)
                .setCreativeTab(CarpentersBlocks.CREATIVE_TAB);
            GameRegistry.register(blockCarpentersHatch);
            GameRegistry.register(new ItemBlock(blockCarpentersHatch), blockCarpentersHatch.getRegistryName());
            Blocks.FIRE.setFireInfo(blockCarpentersHatch, 5, 20);
        }

        if(enableLadder) 
        {
            blockCarpentersLadder = new BlockCarpentersLadder(material)
                .setUnlocalizedName("blockCarpentersLadder")
                .setRegistryName(REGISTRY_NAME_LADDER)
                .setHardness(0.2F)
                .setCreativeTab(CarpentersBlocks.CREATIVE_TAB);
            GameRegistry.register(blockCarpentersLadder);
            GameRegistry.register(new ItemBlock(blockCarpentersLadder), blockCarpentersLadder.getRegistryName());
            Blocks.FIRE.setFireInfo(blockCarpentersLadder, 5, 20);
        }

        if(enableLever) 
        {
            blockCarpentersLever = new BlockCarpentersLever(Material.CIRCUITS)
                .setUnlocalizedName("blockCarpentersLever")
                .setRegistryName(REGISTRY_NAME_LEVER)
                .setHardness(0.2F)
                .setCreativeTab(CarpentersBlocks.CREATIVE_TAB);
            GameRegistry.register(blockCarpentersLever);
            GameRegistry.register(new ItemBlock(blockCarpentersLever), blockCarpentersLever.getRegistryName());
            Blocks.FIRE.setFireInfo(blockCarpentersLever, 5, 20);
        }

        if(enablePressurePlate) 
        {
            blockCarpentersPressurePlate = new BlockCarpentersPressurePlate(Material.CIRCUITS, Sensitivity.EVERYTHING)
                .setUnlocalizedName("blockCarpentersPressurePlate")
                .setRegistryName(REGISTRY_NAME_PRESSURE_PLATE)
                .setHardness(0.2F)
                .setCreativeTab(CarpentersBlocks.CREATIVE_TAB);
            GameRegistry.register(blockCarpentersPressurePlate);
            GameRegistry.register(new ItemBlock(blockCarpentersPressurePlate), blockCarpentersPressurePlate.getRegistryName());
            Blocks.FIRE.setFireInfo(blockCarpentersPressurePlate, 5, 20);
        }

        if(enableSafe) 
        {
            blockCarpentersSafe = new BlockCarpentersSafe(material)
                .setUnlocalizedName("blockCarpentersSafe")
                .setRegistryName(REGISTRY_NAME_SAFE)
                .setHardness(2.5F)
                .setCreativeTab(CarpentersBlocks.CREATIVE_TAB);
            GameRegistry.register(blockCarpentersSafe);
            GameRegistry.register(new ItemBlock(blockCarpentersSafe), blockCarpentersSafe.getRegistryName());
            Blocks.FIRE.setFireInfo(blockCarpentersSafe, 5, 20);
        }
        
        if (enableSlope) 
        {
        		blockCarpentersSlope = new BlockCarpentersSlope(material)
                        .setRegistryName(REGISTRY_NAME_SLOPE)
                        .setUnlocalizedName("blockCarpentersSlope")
                        .setHardness(0.2F)
                        .setCreativeTab(CarpentersBlocks.CREATIVE_TAB);
                    GameRegistry.register(blockCarpentersSlope);
                    GameRegistry.register(new ItemBlock(blockCarpentersSlope), blockCarpentersSlope.getRegistryName());
        	
        }

        if(enableStairs) 
        {
            blockCarpentersStairs = new BlockCarpentersStairs(material)
                .setUnlocalizedName("blockCarpentersStairs")
                .setRegistryName(REGISTRY_NAME_STAIRS)
                .setHardness(0.2F)
                .setCreativeTab(CarpentersBlocks.CREATIVE_TAB);
            GameRegistry.register(blockCarpentersStairs);
            GameRegistry.register(new ItemBlock(blockCarpentersStairs), blockCarpentersStairs.getRegistryName());
            Blocks.FIRE.setFireInfo(blockCarpentersStairs, 5, 20);
        }

        if(enableTorch) 
        {
            blockCarpentersTorch = new BlockCarpentersTorch()
                .setUnlocalizedName("blockCarpentersTorch")
                .setRegistryName(REGISTRY_NAME_TORCH)
                .setHardness(0.2F)
                .setCreativeTab(CarpentersBlocks.CREATIVE_TAB)
                .setLightLevel(1.0F);

            if (Reference.enableTorchWeatherEffects) 
            {
            	blockCarpentersTorch.setTickRandomly(true);
            }
            
            GameRegistry.register(blockCarpentersTorch);
            GameRegistry.register(new ItemBlock(blockCarpentersTorch), blockCarpentersTorch.getRegistryName());
            Blocks.FIRE.setFireInfo(blockCarpentersTorch, 5, 20);
        }
    }
    
    @SideOnly(Side.CLIENT)
    private static void registerItemBlockRender(Block block) 
    {
		ModelResourceLocation modelResourceLocation = new ModelResourceLocation(block.getRegistryName(), "inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, modelResourceLocation);
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenderers() 
    {
    	registerItemBlockRender(blockCarpentersBlock);

        if(enableBarrier) 
        {
        	registerItemBlockRender(blockCarpentersBarrier);
        }
        
        if(enableButton) 
        {
        	registerItemBlockRender(blockCarpentersButton);
        }
        
        if(enableDaylightSensor) 
        {
        	registerItemBlockRender(blockCarpentersDaylightSensor);
        }
        
        if(enableGarageDoor) 
        {
        	registerItemBlockRender(blockCarpentersDoor);
        }
        
        if(enableGate) 
        {
        	registerItemBlockRender(blockCarpentersGate);
        }
        
        if(enableLever) 
        {
        	registerItemBlockRender(blockCarpentersLever);
        }
        
        if(enablePressurePlate) 
        {
            registerItemBlockRender(blockCarpentersPressurePlate);
        }
        
        if(enableSlope) 
        {
        	registerItemBlockRender(blockCarpentersSlope);
        }
        
        if(enableStairs) 
        {
        	registerItemBlockRender(blockCarpentersStairs);
        }
        
        if(enableHatch) 
        {
        	registerItemBlockRender(blockCarpentersHatch);
        }
        if(enableDoor) 
        {
        	registerItemBlockRender(blockCarpentersDoor);
        }
        
        if(enableBed) 
        {
        	registerItemBlockRender(blockCarpentersBed);
        }
        
        if(enableLadder) 
        {
        	registerItemBlockRender(blockCarpentersLadder);
        }
        if(enableCollapsibleBlock) 
        {
        	registerItemBlockRender(blockCarpentersCollapsibleBlock);
        }
        
        if(enableTorch) 
        {
        	registerItemBlockRender(blockCarpentersTorch);
        }
        
        if(enableSafe) 
        {
        	registerItemBlockRender(blockCarpentersSafe);
        }
        
        if(enableFlowerPot) 
        {
        	registerItemBlockRender(blockCarpentersFlowerPot);
        }
    }
    
     /*
     * Block initialiation.
     */
    public static void init(FMLInitializationEvent event) 
    {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockCarpentersBlock, recipeQuantityBlock), "XXX", "XYX", "XXX", 'X', "stickWood", 'Y', "plankWood"));

        if(enableBarrier) 
        {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockCarpentersBarrier, recipeQuantityBarrier), " Y ", "XYX", 'X', "stickWood", 'Y', blockCarpentersBlock));
        }
        
        if(enableButton) 
        {
            GameRegistry.addRecipe(new ItemStack(blockCarpentersButton, recipeQuantityButton), new Object[] { "X", 'X', blockCarpentersBlock });
        }
        
        if(enableCollapsibleBlock) 
        {
            GameRegistry.addRecipe(new ItemStack(blockCarpentersCollapsibleBlock, recipeQuantityCollapsibleBlock), new Object[] { "XXX", "XXX", "XXX", 'X', blockCarpentersBlock });
        }
        
        if(enableDaylightSensor) 
        {
            GameRegistry.addRecipe(new ItemStack(blockCarpentersDaylightSensor, recipeQuantityDaylightSensor), new Object[] { "WWW", "XYX", "ZZZ", 'W', Blocks.GLASS, 'X', Items.REDSTONE, 'Y', new ItemStack(Items.DYE, 1, 4), 'Z', blockCarpentersBlock });
        }
        
        if(enableFlowerPot) 
        {
            GameRegistry.addRecipe(new ItemStack(blockCarpentersFlowerPot, recipeQuantityFlowerPot), new Object[] { "X X", " X ", 'X', blockCarpentersBlock });
        }
        
        if(enableGarageDoor) 
        {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockCarpentersGarageDoor, recipeQuantityGarageDoor), "XXX", "X X", "XXX", 'X', blockCarpentersBlock));
        }
        
        if(enableGate) 
        {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockCarpentersGate, recipeQuantityGate), "XYX", "XYX", 'X', "stickWood", 'Y', blockCarpentersBlock));
        }
        
        if(enableHatch) 
        {
            GameRegistry.addRecipe(new ItemStack(blockCarpentersHatch, recipeQuantityHatch), new Object[] { "XXX", "XXX", 'X', blockCarpentersBlock });
        }
        
        if(enableLadder) 
        {
            GameRegistry.addRecipe(new ItemStack(blockCarpentersLadder, recipeQuantityLadder), new Object[] { "X X", "XXX", "X X", 'X', blockCarpentersBlock });
        }
        
        if(enableLever) 
        {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockCarpentersLever, recipeQuantityLever), "X", "Y", 'X', "stickWood", 'Y', blockCarpentersBlock));
        }
        
        if(enablePressurePlate) 
        {
            GameRegistry.addRecipe(new ItemStack(blockCarpentersPressurePlate, recipeQuantityPressurePlate), new Object[] { "XX", 'X', blockCarpentersBlock });
        }
        
        if(enableSafe) 
        {
            GameRegistry.addRecipe(new ItemStack(blockCarpentersSafe, recipeQuantitySafe), new Object[] { "XXX", "XYX", "XZX", 'X', blockCarpentersBlock, 'Y', Blocks.IRON_BLOCK, 'Z', Items.REDSTONE });
        }
        
        if(enableSlope) 
        {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockCarpentersSlope, recipeQuantitySlope), "  X", " XY", "XYY", 'X', "stickWood", 'Y', blockCarpentersBlock));
        }
        
        if(enableStairs) 
        {
            GameRegistry.addRecipe(new ItemStack(blockCarpentersStairs, recipeQuantityStairs), new Object[] { "  X", " XX", "XXX", 'X', blockCarpentersBlock });
        }
        
        if(enableTorch) 
        {
            GameRegistry.addRecipe(new ItemStack(blockCarpentersTorch, recipeQuantityTorch), new Object[] { "X", "Y", 'X', new ItemStack(Items.COAL, 1, 0), 'Y', blockCarpentersBlock });
            GameRegistry.addRecipe(new ItemStack(blockCarpentersTorch, recipeQuantityTorch), new Object[] { "X", "Y", 'X', new ItemStack(Items.COAL, 1, 1), 'Y', blockCarpentersBlock });
        }
    }
}