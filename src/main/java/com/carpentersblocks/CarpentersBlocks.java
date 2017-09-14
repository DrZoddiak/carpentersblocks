package com.carpentersblocks;

import com.carpentersblocks.proxy.CommonProxy;
import com.carpentersblocks.util.CarpentersBlocksTab; 
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import static com.carpentersblocks.Reference.*;

@Mod(
		name = Reference.MOD_NAME,
        modid = Reference.MOD_ID, 
        version = Reference.VERSION 
     )
public class CarpentersBlocks
{ 
    public static final CreativeTabs CREATIVE_TAB = new CarpentersBlocksTab(MOD_ID);
    public static FMLEventChannel channel;
    
    @SidedProxy(clientSide = CLIENT_PATH, serverSide = SERVER_PATH)
    public static CommonProxy proxy;
        
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(MOD_ID);
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();

        proxy.preInit(event, config);

        if (config.hasChanged()) 
        {
            config.save();
        }
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.init(event);
    }
    
}
