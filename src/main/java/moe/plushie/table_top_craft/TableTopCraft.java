package moe.plushie.table_top_craft;

import org.apache.logging.log4j.Logger;

import moe.plushie.table_top_craft.common.lib.ModReference;
import moe.plushie.table_top_craft.common.tab.ModTab;
import moe.plushie.table_top_craft.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModReference.MOD_ID, version = ModReference.VERSION, name = ModReference.NAME)
public class TableTopCraft {

    // An instance for the Mod
    @Instance(ModReference.MOD_ID)
    private static TableTopCraft instance;

    // Server and Client Proxy
    @SidedProxy(clientSide = ModReference.PROXY_CLIENT, serverSide = ModReference.PROXY_SERVER)
    private static CommonProxy proxy;

    // Sets the Tab
    private static final CreativeTabs MOD_TAB = new ModTab("table_top_craft");

    private static Logger logger;

    // PreInit
    @EventHandler
    public static void preinit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        logger.info(String.format("Loading %s version %s.", ModReference.NAME, ModReference.VERSION));
        proxy.preinit();
    }

    // Init
    @EventHandler
    public static void init(FMLInitializationEvent event) {
        proxy.init();
    }

    // PostInit
    @EventHandler
    public static void postinit(FMLPostInitializationEvent event) {
        proxy.postinit();
    }

    public static TableTopCraft getInstance() {
        return instance;
    }

    public static CommonProxy getProxy() {
        return proxy;
    }

    public static CreativeTabs getModtab() {
        return MOD_TAB;
    }

    public static Logger getLogger() {
        return logger;
    }
}