package andrews.table_top_craft;

import andrews.table_top_craft.criteria.TTCCriteriaTriggers;
import andrews.table_top_craft.network.TTCNetwork;
import andrews.table_top_craft.registry.*;
import andrews.table_top_craft.util.loot_table.TTCLootTableHandler;
import andrews.table_top_craft.util.shader_compat.ShaderCompatHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class TableTopCraft implements ModInitializer
{
    @Override
    public void onInitialize()
    {
        TTCBlocks.init();
        TTCBlockEntities.init();
        TTCCriteriaTriggers.init();
        TTCLootItemFunctions.init();
        TTCParticles.init();
        TTCCreativeTab.init();
        TTCLootTableHandler.init();

        TTCNetwork.registerNetworkMessages();

        try {
            Class<?> clazz = Class.forName("net.optifine.Config");
            if (clazz != null)
                ShaderCompatHandler.initOFCompat();
        } catch (Throwable ignored) {}

        if(FabricLoader.getInstance().isModLoaded("iris"))
            ShaderCompatHandler.initIrisCompat();
    }
}