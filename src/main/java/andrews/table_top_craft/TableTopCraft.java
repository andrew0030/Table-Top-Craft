package andrews.table_top_craft;

import andrews.table_top_craft.criteria.TTCCriteriaTriggers;
import andrews.table_top_craft.events.CreativeTabEvents;
import andrews.table_top_craft.network.TTCNetwork;
import andrews.table_top_craft.registry.TTCBlockEntities;
import andrews.table_top_craft.registry.TTCBlocks;
import andrews.table_top_craft.registry.TTCLootItemFunctions;
import andrews.table_top_craft.registry.TTCParticles;
import andrews.table_top_craft.util.TTCResourceManager;
import andrews.table_top_craft.util.shader_compat.ShaderCompatHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class TableTopCraft implements ModInitializer
{
    @Override
    public void onInitialize()
    {
        // Resource Reload Listener
        TTCResourceManager.init();

        TTCBlocks.init();
        TTCBlockEntities.init();
        TTCCriteriaTriggers.init();
        TTCLootItemFunctions.init();
        TTCParticles.init();

        TTCNetwork.registerNetworkMessages();

        CreativeTabEvents.init();

        try {
            Class<?> clazz = Class.forName("net.optifine.Config");
            if (clazz != null)
                ShaderCompatHandler.initOFCompat();
        } catch (Throwable ignored) {}

        if(FabricLoader.getInstance().isModLoaded("iris"))
            ShaderCompatHandler.initIrisCompat();
    }
}