package andrews.table_top_craft;

import andrews.table_top_craft.criteria.TTCCriteriaTriggers;
import andrews.table_top_craft.network.TTCNetwork;
import andrews.table_top_craft.registry.TTCBlocks;
import andrews.table_top_craft.registry.TTCLootItemFunctions;
import andrews.table_top_craft.registry.TTCTileEntities;
import andrews.table_top_craft.util.Reference;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class TableTopCraft implements ModInitializer
{
    public static final CreativeModeTab TABLE_TOP_CRAFT_GROUP = FabricItemGroupBuilder.build(new ResourceLocation(Reference.MODID, "tab"), () -> new ItemStack(TTCBlocks.OAK_CHESS));

    @Override
    public void onInitialize()
    {
        TTCBlocks.init();
        TTCTileEntities.init();
        TTCCriteriaTriggers.init();
        TTCLootItemFunctions.init();

        TTCNetwork.registerNetworkMessages();
    }
//     A little something to let people know the Mod won't fully work with Shaders installed.
//		try {
//			Class<?> clazz = Class.forName("net.optifine.Config");
//			if (clazz != null)
//			{
//				ModLoader.get().addWarning(new ModLoadingWarning(ModLoadingContext.get().getActiveContainer().getModInfo(), ModLoadingStage.CONSTRUCT,
//						ChatFormatting.YELLOW + "Table Top Craft" + ChatFormatting.RESET + "\nOptifine Shaders and Table Top Craft are" + ChatFormatting.RED + ChatFormatting.BOLD + " incompatible " + ChatFormatting.RESET + "with each other."
//				));
//			}
//		} catch (Throwable ignored) {}
//	}
}