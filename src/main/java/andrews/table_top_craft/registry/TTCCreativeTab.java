package andrews.table_top_craft.registry;

import andrews.table_top_craft.util.Reference;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class TTCCreativeTab
{
    public static final CreativeModeTab TTC_TAB = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, new ResourceLocation(Reference.MODID, "tab"), FabricItemGroup.builder()
        .title(Component.translatable("itemGroup." + Reference.MODID + ".tab"))
        .icon(() -> new ItemStack(TTCBlocks.OAK_CHESS))
        .displayItems((params, output) -> {
            // Misc
            output.accept(TTCBlocks.TIC_TAC_TOE);
            output.accept(TTCBlocks.CHESS_PIECE_FIGURE);
            // Chess
            output.accept(TTCBlocks.OAK_CHESS);
            output.accept(TTCBlocks.SPRUCE_CHESS);
            output.accept(TTCBlocks.BIRCH_CHESS);
            output.accept(TTCBlocks.JUNGLE_CHESS);
            output.accept(TTCBlocks.ACACIA_CHESS);
            output.accept(TTCBlocks.DARK_OAK_CHESS);
            output.accept(TTCBlocks.MANGROVE_CHESS);
            output.accept(TTCBlocks.CHERRY_CHESS);
            output.accept(TTCBlocks.BAMBOO_CHESS);
            output.accept(TTCBlocks.CRIMSON_CHESS);
            output.accept(TTCBlocks.WARPED_CHESS);
            output.accept(TTCBlocks.WHITE_TERRACOTTA_CHESS);
            output.accept(TTCBlocks.LIGHT_GRAY_TERRACOTTA_CHESS);
            output.accept(TTCBlocks.GRAY_TERRACOTTA_CHESS);
            output.accept(TTCBlocks.BLACK_TERRACOTTA_CHESS);
            output.accept(TTCBlocks.BROWN_TERRACOTTA_CHESS);
            output.accept(TTCBlocks.RED_TERRACOTTA_CHESS);
            output.accept(TTCBlocks.ORANGE_TERRACOTTA_CHESS);
            output.accept(TTCBlocks.YELLOW_TERRACOTTA_CHESS);
            output.accept(TTCBlocks.LIME_TERRACOTTA_CHESS);
            output.accept(TTCBlocks.GREEN_TERRACOTTA_CHESS);
            output.accept(TTCBlocks.CYAN_TERRACOTTA_CHESS);
            output.accept(TTCBlocks.LIGHT_BLUE_TERRACOTTA_CHESS);
            output.accept(TTCBlocks.BLUE_TERRACOTTA_CHESS);
            output.accept(TTCBlocks.PURPLE_TERRACOTTA_CHESS);
            output.accept(TTCBlocks.MAGENTA_TERRACOTTA_CHESS);
            output.accept(TTCBlocks.PINK_TERRACOTTA_CHESS);
            output.accept(TTCBlocks.WHITE_CONCRETE_CHESS);
            output.accept(TTCBlocks.LIGHT_GRAY_CONCRETE_CHESS);
            output.accept(TTCBlocks.GRAY_CONCRETE_CHESS);
            output.accept(TTCBlocks.BLACK_CONCRETE_CHESS);
            output.accept(TTCBlocks.BROWN_CONCRETE_CHESS);
            output.accept(TTCBlocks.RED_CONCRETE_CHESS);
            output.accept(TTCBlocks.ORANGE_CONCRETE_CHESS);
            output.accept(TTCBlocks.YELLOW_CONCRETE_CHESS);
            output.accept(TTCBlocks.LIME_CONCRETE_CHESS);
            output.accept(TTCBlocks.GREEN_CONCRETE_CHESS);
            output.accept(TTCBlocks.CYAN_CONCRETE_CHESS);
            output.accept(TTCBlocks.LIGHT_BLUE_CONCRETE_CHESS);
            output.accept(TTCBlocks.BLUE_CONCRETE_CHESS);
            output.accept(TTCBlocks.PURPLE_CONCRETE_CHESS);
            output.accept(TTCBlocks.MAGENTA_CONCRETE_CHESS);
            output.accept(TTCBlocks.PINK_CONCRETE_CHESS);
            // Timer
            output.accept(TTCBlocks.OAK_CHESS_TIMER);
            output.accept(TTCBlocks.SPRUCE_CHESS_TIMER);
            output.accept(TTCBlocks.BIRCH_CHESS_TIMER);
            output.accept(TTCBlocks.JUNGLE_CHESS_TIMER);
            output.accept(TTCBlocks.ACACIA_CHESS_TIMER);
            output.accept(TTCBlocks.DARK_OAK_CHESS_TIMER);
            output.accept(TTCBlocks.MANGROVE_CHESS_TIMER);
            output.accept(TTCBlocks.CHERRY_CHESS_TIMER);
            output.accept(TTCBlocks.BAMBOO_CHESS_TIMER);
            output.accept(TTCBlocks.CRIMSON_CHESS_TIMER);
            output.accept(TTCBlocks.WARPED_CHESS_TIMER);
            output.accept(TTCBlocks.WHITE_TERRACOTTA_CHESS_TIMER);
            output.accept(TTCBlocks.LIGHT_GRAY_TERRACOTTA_CHESS_TIMER);
            output.accept(TTCBlocks.GRAY_TERRACOTTA_CHESS_TIMER);
            output.accept(TTCBlocks.BLACK_TERRACOTTA_CHESS_TIMER);
            output.accept(TTCBlocks.BROWN_TERRACOTTA_CHESS_TIMER);
            output.accept(TTCBlocks.RED_TERRACOTTA_CHESS_TIMER);
            output.accept(TTCBlocks.ORANGE_TERRACOTTA_CHESS_TIMER);
            output.accept(TTCBlocks.YELLOW_TERRACOTTA_CHESS_TIMER);
            output.accept(TTCBlocks.LIME_TERRACOTTA_CHESS_TIMER);
            output.accept(TTCBlocks.GREEN_TERRACOTTA_CHESS_TIMER);
            output.accept(TTCBlocks.CYAN_TERRACOTTA_CHESS_TIMER);
            output.accept(TTCBlocks.LIGHT_BLUE_TERRACOTTA_CHESS_TIMER);
            output.accept(TTCBlocks.BLUE_TERRACOTTA_CHESS_TIMER);
            output.accept(TTCBlocks.PURPLE_TERRACOTTA_CHESS_TIMER);
            output.accept(TTCBlocks.MAGENTA_TERRACOTTA_CHESS_TIMER);
            output.accept(TTCBlocks.PINK_TERRACOTTA_CHESS_TIMER);
            output.accept(TTCBlocks.WHITE_CONCRETE_CHESS_TIMER);
            output.accept(TTCBlocks.LIGHT_GRAY_CONCRETE_CHESS_TIMER);
            output.accept(TTCBlocks.GRAY_CONCRETE_CHESS_TIMER);
            output.accept(TTCBlocks.BLACK_CONCRETE_CHESS_TIMER);
            output.accept(TTCBlocks.BROWN_CONCRETE_CHESS_TIMER);
            output.accept(TTCBlocks.RED_CONCRETE_CHESS_TIMER);
            output.accept(TTCBlocks.ORANGE_CONCRETE_CHESS_TIMER);
            output.accept(TTCBlocks.YELLOW_CONCRETE_CHESS_TIMER);
            output.accept(TTCBlocks.LIME_CONCRETE_CHESS_TIMER);
            output.accept(TTCBlocks.GREEN_CONCRETE_CHESS_TIMER);
            output.accept(TTCBlocks.CYAN_CONCRETE_CHESS_TIMER);
            output.accept(TTCBlocks.LIGHT_BLUE_CONCRETE_CHESS_TIMER);
            output.accept(TTCBlocks.BLUE_CONCRETE_CHESS_TIMER);
            output.accept(TTCBlocks.PURPLE_CONCRETE_CHESS_TIMER);
            output.accept(TTCBlocks.MAGENTA_CONCRETE_CHESS_TIMER);
            output.accept(TTCBlocks.PINK_CONCRETE_CHESS_TIMER);
            // Connect Four
            output.accept(TTCBlocks.OAK_CONNECT_FOUR);
            output.accept(TTCBlocks.SPRUCE_CONNECT_FOUR);
            output.accept(TTCBlocks.BIRCH_CONNECT_FOUR);
            output.accept(TTCBlocks.JUNGLE_CONNECT_FOUR);
            output.accept(TTCBlocks.ACACIA_CONNECT_FOUR);
            output.accept(TTCBlocks.DARK_OAK_CONNECT_FOUR);
            output.accept(TTCBlocks.MANGROVE_CONNECT_FOUR);
            output.accept(TTCBlocks.CHERRY_CONNECT_FOUR);
            output.accept(TTCBlocks.BAMBOO_CONNECT_FOUR);
            output.accept(TTCBlocks.CRIMSON_CONNECT_FOUR);
            output.accept(TTCBlocks.WARPED_CONNECT_FOUR);
            output.accept(TTCBlocks.WHITE_TERRACOTTA_CONNECT_FOUR);
            output.accept(TTCBlocks.LIGHT_GRAY_TERRACOTTA_CONNECT_FOUR);
            output.accept(TTCBlocks.GRAY_TERRACOTTA_CONNECT_FOUR);
            output.accept(TTCBlocks.BLACK_TERRACOTTA_CONNECT_FOUR);
            output.accept(TTCBlocks.BROWN_TERRACOTTA_CONNECT_FOUR);
            output.accept(TTCBlocks.RED_TERRACOTTA_CONNECT_FOUR);
            output.accept(TTCBlocks.ORANGE_TERRACOTTA_CONNECT_FOUR);
            output.accept(TTCBlocks.YELLOW_TERRACOTTA_CONNECT_FOUR);
            output.accept(TTCBlocks.LIME_TERRACOTTA_CONNECT_FOUR);
            output.accept(TTCBlocks.GREEN_TERRACOTTA_CONNECT_FOUR);
            output.accept(TTCBlocks.CYAN_TERRACOTTA_CONNECT_FOUR);
            output.accept(TTCBlocks.LIGHT_BLUE_TERRACOTTA_CONNECT_FOUR);
            output.accept(TTCBlocks.BLUE_TERRACOTTA_CONNECT_FOUR);
            output.accept(TTCBlocks.PURPLE_TERRACOTTA_CONNECT_FOUR);
            output.accept(TTCBlocks.MAGENTA_TERRACOTTA_CONNECT_FOUR);
            output.accept(TTCBlocks.PINK_TERRACOTTA_CONNECT_FOUR);
            output.accept(TTCBlocks.WHITE_CONCRETE_CONNECT_FOUR);
            output.accept(TTCBlocks.LIGHT_GRAY_CONCRETE_CONNECT_FOUR);
            output.accept(TTCBlocks.GRAY_CONCRETE_CONNECT_FOUR);
            output.accept(TTCBlocks.BLACK_CONCRETE_CONNECT_FOUR);
            output.accept(TTCBlocks.BROWN_CONCRETE_CONNECT_FOUR);
            output.accept(TTCBlocks.RED_CONCRETE_CONNECT_FOUR);
            output.accept(TTCBlocks.ORANGE_CONCRETE_CONNECT_FOUR);
            output.accept(TTCBlocks.YELLOW_CONCRETE_CONNECT_FOUR);
            output.accept(TTCBlocks.LIME_CONCRETE_CONNECT_FOUR);
            output.accept(TTCBlocks.GREEN_CONCRETE_CONNECT_FOUR);
            output.accept(TTCBlocks.CYAN_CONCRETE_CONNECT_FOUR);
            output.accept(TTCBlocks.LIGHT_BLUE_CONCRETE_CONNECT_FOUR);
            output.accept(TTCBlocks.BLUE_CONCRETE_CONNECT_FOUR);
            output.accept(TTCBlocks.PURPLE_CONCRETE_CONNECT_FOUR);
            output.accept(TTCBlocks.MAGENTA_CONCRETE_CONNECT_FOUR);
            output.accept(TTCBlocks.PINK_CONCRETE_CONNECT_FOUR);
        }).build()
    );

    public static void init() {}
}