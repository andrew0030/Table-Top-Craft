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
                    // Misc
                    populator.accept(TTCBlocks.TIC_TAC_TOE);
                    populator.accept(TTCBlocks.CHESS_PIECE_FIGURE);
                    // Chess
                    populator.accept(TTCBlocks.OAK_CHESS);
                    populator.accept(TTCBlocks.SPRUCE_CHESS);
                    populator.accept(TTCBlocks.BIRCH_CHESS);
                    populator.accept(TTCBlocks.JUNGLE_CHESS);
                    populator.accept(TTCBlocks.ACACIA_CHESS);
                    populator.accept(TTCBlocks.DARK_OAK_CHESS);
                    populator.accept(TTCBlocks.MANGROVE_CHESS);
                    populator.accept(TTCBlocks.CRIMSON_CHESS);
                    populator.accept(TTCBlocks.WARPED_CHESS);
                    populator.accept(TTCBlocks.WHITE_TERRACOTTA_CHESS);
                    populator.accept(TTCBlocks.LIGHT_GRAY_TERRACOTTA_CHESS);
                    populator.accept(TTCBlocks.GRAY_TERRACOTTA_CHESS);
                    populator.accept(TTCBlocks.BLACK_TERRACOTTA_CHESS);
                    populator.accept(TTCBlocks.BROWN_TERRACOTTA_CHESS);
                    populator.accept(TTCBlocks.RED_TERRACOTTA_CHESS);
                    populator.accept(TTCBlocks.ORANGE_TERRACOTTA_CHESS);
                    populator.accept(TTCBlocks.YELLOW_TERRACOTTA_CHESS);
                    populator.accept(TTCBlocks.LIME_TERRACOTTA_CHESS);
                    populator.accept(TTCBlocks.GREEN_TERRACOTTA_CHESS);
                    populator.accept(TTCBlocks.CYAN_TERRACOTTA_CHESS);
                    populator.accept(TTCBlocks.LIGHT_BLUE_TERRACOTTA_CHESS);
                    populator.accept(TTCBlocks.BLUE_TERRACOTTA_CHESS);
                    populator.accept(TTCBlocks.PURPLE_TERRACOTTA_CHESS);
                    populator.accept(TTCBlocks.MAGENTA_TERRACOTTA_CHESS);
                    populator.accept(TTCBlocks.PINK_TERRACOTTA_CHESS);
                    populator.accept(TTCBlocks.WHITE_CONCRETE_CHESS);
                    populator.accept(TTCBlocks.LIGHT_GRAY_CONCRETE_CHESS);
                    populator.accept(TTCBlocks.GRAY_CONCRETE_CHESS);
                    populator.accept(TTCBlocks.BLACK_CONCRETE_CHESS);
                    populator.accept(TTCBlocks.BROWN_CONCRETE_CHESS);
                    populator.accept(TTCBlocks.RED_CONCRETE_CHESS);
                    populator.accept(TTCBlocks.ORANGE_CONCRETE_CHESS);
                    populator.accept(TTCBlocks.YELLOW_CONCRETE_CHESS);
                    populator.accept(TTCBlocks.LIME_CONCRETE_CHESS);
                    populator.accept(TTCBlocks.GREEN_CONCRETE_CHESS);
                    populator.accept(TTCBlocks.CYAN_CONCRETE_CHESS);
                    populator.accept(TTCBlocks.LIGHT_BLUE_CONCRETE_CHESS);
                    populator.accept(TTCBlocks.BLUE_CONCRETE_CHESS);
                    populator.accept(TTCBlocks.PURPLE_CONCRETE_CHESS);
                    populator.accept(TTCBlocks.MAGENTA_CONCRETE_CHESS);
                    populator.accept(TTCBlocks.PINK_CONCRETE_CHESS);
                    // Timer
                    populator.accept(TTCBlocks.OAK_CHESS_TIMER);
                    populator.accept(TTCBlocks.SPRUCE_CHESS_TIMER);
                    populator.accept(TTCBlocks.BIRCH_CHESS_TIMER);
                    populator.accept(TTCBlocks.JUNGLE_CHESS_TIMER);
                    populator.accept(TTCBlocks.ACACIA_CHESS_TIMER);
                    populator.accept(TTCBlocks.DARK_OAK_CHESS_TIMER);
                    populator.accept(TTCBlocks.MANGROVE_CHESS_TIMER);
                    populator.accept(TTCBlocks.CRIMSON_CHESS_TIMER);
                    populator.accept(TTCBlocks.WARPED_CHESS_TIMER);
                    populator.accept(TTCBlocks.WHITE_TERRACOTTA_CHESS_TIMER);
                    populator.accept(TTCBlocks.LIGHT_GRAY_TERRACOTTA_CHESS_TIMER);
                    populator.accept(TTCBlocks.GRAY_TERRACOTTA_CHESS_TIMER);
                    populator.accept(TTCBlocks.BLACK_TERRACOTTA_CHESS_TIMER);
                    populator.accept(TTCBlocks.BROWN_TERRACOTTA_CHESS_TIMER);
                    populator.accept(TTCBlocks.RED_TERRACOTTA_CHESS_TIMER);
                    populator.accept(TTCBlocks.ORANGE_TERRACOTTA_CHESS_TIMER);
                    populator.accept(TTCBlocks.YELLOW_TERRACOTTA_CHESS_TIMER);
                    populator.accept(TTCBlocks.LIME_TERRACOTTA_CHESS_TIMER);
                    populator.accept(TTCBlocks.GREEN_TERRACOTTA_CHESS_TIMER);
                    populator.accept(TTCBlocks.CYAN_TERRACOTTA_CHESS_TIMER);
                    populator.accept(TTCBlocks.LIGHT_BLUE_TERRACOTTA_CHESS_TIMER);
                    populator.accept(TTCBlocks.BLUE_TERRACOTTA_CHESS_TIMER);
                    populator.accept(TTCBlocks.PURPLE_TERRACOTTA_CHESS_TIMER);
                    populator.accept(TTCBlocks.MAGENTA_TERRACOTTA_CHESS_TIMER);
                    populator.accept(TTCBlocks.PINK_TERRACOTTA_CHESS_TIMER);
                    populator.accept(TTCBlocks.WHITE_CONCRETE_CHESS_TIMER);
                    populator.accept(TTCBlocks.LIGHT_GRAY_CONCRETE_CHESS_TIMER);
                    populator.accept(TTCBlocks.GRAY_CONCRETE_CHESS_TIMER);
                    populator.accept(TTCBlocks.BLACK_CONCRETE_CHESS_TIMER);
                    populator.accept(TTCBlocks.BROWN_CONCRETE_CHESS_TIMER);
                    populator.accept(TTCBlocks.RED_CONCRETE_CHESS_TIMER);
                    populator.accept(TTCBlocks.ORANGE_CONCRETE_CHESS_TIMER);
                    populator.accept(TTCBlocks.YELLOW_CONCRETE_CHESS_TIMER);
                    populator.accept(TTCBlocks.LIME_CONCRETE_CHESS_TIMER);
                    populator.accept(TTCBlocks.GREEN_CONCRETE_CHESS_TIMER);
                    populator.accept(TTCBlocks.CYAN_CONCRETE_CHESS_TIMER);
                    populator.accept(TTCBlocks.LIGHT_BLUE_CONCRETE_CHESS_TIMER);
                    populator.accept(TTCBlocks.BLUE_CONCRETE_CHESS_TIMER);
                    populator.accept(TTCBlocks.PURPLE_CONCRETE_CHESS_TIMER);
                    populator.accept(TTCBlocks.MAGENTA_CONCRETE_CHESS_TIMER);
                    populator.accept(TTCBlocks.PINK_CONCRETE_CHESS_TIMER);
                    // Connect Four
                    populator.accept(TTCBlocks.OAK_CONNECT_FOUR);
                    populator.accept(TTCBlocks.SPRUCE_CONNECT_FOUR);
                    populator.accept(TTCBlocks.BIRCH_CONNECT_FOUR);
                    populator.accept(TTCBlocks.JUNGLE_CONNECT_FOUR);
                    populator.accept(TTCBlocks.ACACIA_CONNECT_FOUR);
                    populator.accept(TTCBlocks.DARK_OAK_CONNECT_FOUR);
                    populator.accept(TTCBlocks.MANGROVE_CONNECT_FOUR);
                    populator.accept(TTCBlocks.CRIMSON_CONNECT_FOUR);
                    populator.accept(TTCBlocks.WARPED_CONNECT_FOUR);
                    populator.accept(TTCBlocks.WHITE_TERRACOTTA_CONNECT_FOUR);
                    populator.accept(TTCBlocks.LIGHT_GRAY_TERRACOTTA_CONNECT_FOUR);
                    populator.accept(TTCBlocks.GRAY_TERRACOTTA_CONNECT_FOUR);
                    populator.accept(TTCBlocks.BLACK_TERRACOTTA_CONNECT_FOUR);
                    populator.accept(TTCBlocks.BROWN_TERRACOTTA_CONNECT_FOUR);
                    populator.accept(TTCBlocks.RED_TERRACOTTA_CONNECT_FOUR);
                    populator.accept(TTCBlocks.ORANGE_TERRACOTTA_CONNECT_FOUR);
                    populator.accept(TTCBlocks.YELLOW_TERRACOTTA_CONNECT_FOUR);
                    populator.accept(TTCBlocks.LIME_TERRACOTTA_CONNECT_FOUR);
                    populator.accept(TTCBlocks.GREEN_TERRACOTTA_CONNECT_FOUR);
                    populator.accept(TTCBlocks.CYAN_TERRACOTTA_CONNECT_FOUR);
                    populator.accept(TTCBlocks.LIGHT_BLUE_TERRACOTTA_CONNECT_FOUR);
                    populator.accept(TTCBlocks.BLUE_TERRACOTTA_CONNECT_FOUR);
                    populator.accept(TTCBlocks.PURPLE_TERRACOTTA_CONNECT_FOUR);
                    populator.accept(TTCBlocks.MAGENTA_TERRACOTTA_CONNECT_FOUR);
                    populator.accept(TTCBlocks.PINK_TERRACOTTA_CONNECT_FOUR);
                    populator.accept(TTCBlocks.WHITE_CONCRETE_CONNECT_FOUR);
                    populator.accept(TTCBlocks.LIGHT_GRAY_CONCRETE_CONNECT_FOUR);
                    populator.accept(TTCBlocks.GRAY_CONCRETE_CONNECT_FOUR);
                    populator.accept(TTCBlocks.BLACK_CONCRETE_CONNECT_FOUR);
                    populator.accept(TTCBlocks.BROWN_CONCRETE_CONNECT_FOUR);
                    populator.accept(TTCBlocks.RED_CONCRETE_CONNECT_FOUR);
                    populator.accept(TTCBlocks.ORANGE_CONCRETE_CONNECT_FOUR);
                    populator.accept(TTCBlocks.YELLOW_CONCRETE_CONNECT_FOUR);
                    populator.accept(TTCBlocks.LIME_CONCRETE_CONNECT_FOUR);
                    populator.accept(TTCBlocks.GREEN_CONCRETE_CONNECT_FOUR);
                    populator.accept(TTCBlocks.CYAN_CONCRETE_CONNECT_FOUR);
                    populator.accept(TTCBlocks.LIGHT_BLUE_CONCRETE_CONNECT_FOUR);
                    populator.accept(TTCBlocks.BLUE_CONCRETE_CONNECT_FOUR);
                    populator.accept(TTCBlocks.PURPLE_CONCRETE_CONNECT_FOUR);
                    populator.accept(TTCBlocks.MAGENTA_CONCRETE_CONNECT_FOUR);
                    populator.accept(TTCBlocks.PINK_CONCRETE_CONNECT_FOUR);
                }).build();
    }
}