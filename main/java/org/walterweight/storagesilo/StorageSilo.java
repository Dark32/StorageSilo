package org.walterweight.storagesilo;


import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.walterweight.storagesilo.blocks.ModBlocks;
import org.walterweight.storagesilo.blocks.storagesilo.TileEntityStorageSilo;
import org.walterweight.storagesilo.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MODID, version = Reference.VERSION, name = Reference.MODNAME)
public class StorageSilo
{

    public static int siloCapacity;

    @Instance(Reference.MODID)
    public static StorageSilo instance;

    @SidedProxy(clientSide = "org.walterweight.storagesilo.proxy.ClientProxy", serverSide = "org.walterweight.storagesilo.proxy.CommonProxy")
    public static CommonProxy proxy;
  
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ModBlocks.init();
        registerTileEntities();
        getConfiguration(event);
    }

    private void registerTileEntities()
    {
        GameRegistry.registerTileEntity(TileEntityStorageSilo.class, "tileDeepStorageChest");
    }

    private void getConfiguration(FMLPreInitializationEvent event){
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        siloCapacity = config.getInt("StorageSiloCapacity", Configuration.CATEGORY_GENERAL, 999, 54, 999, "The number of available slots in each StorageSilo");
        config.save();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.initCommon();
        proxy.initServer();
        proxy.initClient();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        new CraftingRecipes().init();
    }
    
    @EventHandler
    public void serverStopping(FMLServerStoppingEvent event){
    }
    
    @EventHandler
    public void serverStarted(FMLServerStartedEvent event){
    }

}
