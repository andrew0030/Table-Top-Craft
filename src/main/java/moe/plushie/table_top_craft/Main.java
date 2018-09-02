package moe.plushie.table_top_craft;

import moe.plushie.table_top_craft.proxy.CommonProxy;
import moe.plushie.table_top_craft.util.registrymanager.RegistryManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, version = Reference.VERSION, name = Reference.NAME)
public class Main {
	
//An instance for the Mod
	@Instance(Reference.MODID)
    public static Main instance;
	
//Server and Client Proxy
    @SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.SERVER)
    public static CommonProxy proxy;
    
//PreInit
    @EventHandler
    public static void preinit(FMLPreInitializationEvent event) {
    	RegistryManager.preInitRegistries();
    }
    
//Init
    @EventHandler
    public static void init(FMLInitializationEvent event) {
    	RegistryManager.initRegistries();
    }
    
//PostInit
    @EventHandler
    public static void postinit(FMLPostInitializationEvent event) {
    	RegistryManager.postInitRegistries();
    }  
}