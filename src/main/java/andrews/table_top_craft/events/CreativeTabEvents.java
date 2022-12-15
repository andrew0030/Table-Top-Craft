package andrews.table_top_craft.events;

import andrews.table_top_craft.registry.TTCBlocks;
import andrews.table_top_craft.util.Reference;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class CreativeTabEvents
{
    public static void init()
    {
        CreativeModeTab ITEM_GROUP = FabricItemGroup.builder(new ResourceLocation(Reference.MODID, "tab"))
                .title(Component.translatable("itemGroup." + Reference.MODID + ".tab"))
                .icon(() -> new ItemStack(TTCBlocks.OAK_CHESS))
                .displayItems((enabledFlags, populator, hasPermissions) -> {
                    populator.accept(TTCBlocks.OAK_CHESS);
                    populator.accept(TTCBlocks.SPRUCE_CHESS);
                    populator.accept(TTCBlocks.BIRCH_CHESS);
                    populator.accept(TTCBlocks.JUNGLE_CHESS);
                    populator.accept(TTCBlocks.ACACIA_CHESS);
                    populator.accept(TTCBlocks.DARK_OAK_CHESS);
                    populator.accept(TTCBlocks.CRIMSON_CHESS);
                    populator.accept(TTCBlocks.WARPED_CHESS);
                    populator.accept(TTCBlocks.TIC_TAC_TOE);
                    populator.accept(TTCBlocks.CHESS_PIECE_FIGURE);
                }).build();
    }
}