package andrews.table_top_craft.registry;

import andrews.table_top_craft.util.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TTCCreativeTab
{
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Reference.MODID);

    public static final RegistryObject<CreativeModeTab> TTC_TAB = TABS.register("tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + Reference.MODID + ".tab"))
            .icon(() -> new ItemStack(TTCBlocks.OAK_CHESS.get()))
            // Add default items to tab
            .displayItems((params, output) -> {
                // Misc
                output.accept(TTCBlocks.TIC_TAC_TOE.get());
                output.accept(TTCBlocks.CHESS_PIECE_FIGURE.get());
                // Chess
                output.accept(TTCBlocks.OAK_CHESS.get());
                output.accept(TTCBlocks.SPRUCE_CHESS.get());
                output.accept(TTCBlocks.BIRCH_CHESS.get());
                output.accept(TTCBlocks.JUNGLE_CHESS.get());
                output.accept(TTCBlocks.ACACIA_CHESS.get());
                output.accept(TTCBlocks.DARK_OAK_CHESS.get());
                output.accept(TTCBlocks.MANGROVE_CHESS.get());
                output.accept(TTCBlocks.CHERRY_CHESS.get());
                output.accept(TTCBlocks.BAMBOO_CHESS.get());
                output.accept(TTCBlocks.CRIMSON_CHESS.get());
                output.accept(TTCBlocks.WARPED_CHESS.get());
                output.accept(TTCBlocks.WHITE_TERRACOTTA_CHESS.get());
                output.accept(TTCBlocks.LIGHT_GRAY_TERRACOTTA_CHESS.get());
                output.accept(TTCBlocks.GRAY_TERRACOTTA_CHESS.get());
                output.accept(TTCBlocks.BLACK_TERRACOTTA_CHESS.get());
                output.accept(TTCBlocks.BROWN_TERRACOTTA_CHESS.get());
                output.accept(TTCBlocks.RED_TERRACOTTA_CHESS.get());
                output.accept(TTCBlocks.ORANGE_TERRACOTTA_CHESS.get());
                output.accept(TTCBlocks.YELLOW_TERRACOTTA_CHESS.get());
                output.accept(TTCBlocks.LIME_TERRACOTTA_CHESS.get());
                output.accept(TTCBlocks.GREEN_TERRACOTTA_CHESS.get());
                output.accept(TTCBlocks.CYAN_TERRACOTTA_CHESS.get());
                output.accept(TTCBlocks.LIGHT_BLUE_TERRACOTTA_CHESS.get());
                output.accept(TTCBlocks.BLUE_TERRACOTTA_CHESS.get());
                output.accept(TTCBlocks.PURPLE_TERRACOTTA_CHESS.get());
                output.accept(TTCBlocks.MAGENTA_TERRACOTTA_CHESS.get());
                output.accept(TTCBlocks.PINK_TERRACOTTA_CHESS.get());
                output.accept(TTCBlocks.WHITE_CONCRETE_CHESS.get());
                output.accept(TTCBlocks.LIGHT_GRAY_CONCRETE_CHESS.get());
                output.accept(TTCBlocks.GRAY_CONCRETE_CHESS.get());
                output.accept(TTCBlocks.BLACK_CONCRETE_CHESS.get());
                output.accept(TTCBlocks.BROWN_CONCRETE_CHESS.get());
                output.accept(TTCBlocks.RED_CONCRETE_CHESS.get());
                output.accept(TTCBlocks.ORANGE_CONCRETE_CHESS.get());
                output.accept(TTCBlocks.YELLOW_CONCRETE_CHESS.get());
                output.accept(TTCBlocks.LIME_CONCRETE_CHESS.get());
                output.accept(TTCBlocks.GREEN_CONCRETE_CHESS.get());
                output.accept(TTCBlocks.CYAN_CONCRETE_CHESS.get());
                output.accept(TTCBlocks.LIGHT_BLUE_CONCRETE_CHESS.get());
                output.accept(TTCBlocks.BLUE_CONCRETE_CHESS.get());
                output.accept(TTCBlocks.PURPLE_CONCRETE_CHESS.get());
                output.accept(TTCBlocks.MAGENTA_CONCRETE_CHESS.get());
                output.accept(TTCBlocks.PINK_CONCRETE_CHESS.get());
                // Timer
                output.accept(TTCBlocks.OAK_CHESS_TIMER.get());
                output.accept(TTCBlocks.SPRUCE_CHESS_TIMER.get());
                output.accept(TTCBlocks.BIRCH_CHESS_TIMER.get());
                output.accept(TTCBlocks.JUNGLE_CHESS_TIMER.get());
                output.accept(TTCBlocks.ACACIA_CHESS_TIMER.get());
                output.accept(TTCBlocks.DARK_OAK_CHESS_TIMER.get());
                output.accept(TTCBlocks.MANGROVE_CHESS_TIMER.get());
                output.accept(TTCBlocks.CHERRY_CHESS_TIMER.get());
                output.accept(TTCBlocks.BAMBOO_CHESS_TIMER.get());
                output.accept(TTCBlocks.CRIMSON_CHESS_TIMER.get());
                output.accept(TTCBlocks.WARPED_CHESS_TIMER.get());
                output.accept(TTCBlocks.WHITE_TERRACOTTA_CHESS_TIMER.get());
                output.accept(TTCBlocks.LIGHT_GRAY_TERRACOTTA_CHESS_TIMER.get());
                output.accept(TTCBlocks.GRAY_TERRACOTTA_CHESS_TIMER.get());
                output.accept(TTCBlocks.BLACK_TERRACOTTA_CHESS_TIMER.get());
                output.accept(TTCBlocks.BROWN_TERRACOTTA_CHESS_TIMER.get());
                output.accept(TTCBlocks.RED_TERRACOTTA_CHESS_TIMER.get());
                output.accept(TTCBlocks.ORANGE_TERRACOTTA_CHESS_TIMER.get());
                output.accept(TTCBlocks.YELLOW_TERRACOTTA_CHESS_TIMER.get());
                output.accept(TTCBlocks.LIME_TERRACOTTA_CHESS_TIMER.get());
                output.accept(TTCBlocks.GREEN_TERRACOTTA_CHESS_TIMER.get());
                output.accept(TTCBlocks.CYAN_TERRACOTTA_CHESS_TIMER.get());
                output.accept(TTCBlocks.LIGHT_BLUE_TERRACOTTA_CHESS_TIMER.get());
                output.accept(TTCBlocks.BLUE_TERRACOTTA_CHESS_TIMER.get());
                output.accept(TTCBlocks.PURPLE_TERRACOTTA_CHESS_TIMER.get());
                output.accept(TTCBlocks.MAGENTA_TERRACOTTA_CHESS_TIMER.get());
                output.accept(TTCBlocks.PINK_TERRACOTTA_CHESS_TIMER.get());
                output.accept(TTCBlocks.WHITE_CONCRETE_CHESS_TIMER.get());
                output.accept(TTCBlocks.LIGHT_GRAY_CONCRETE_CHESS_TIMER.get());
                output.accept(TTCBlocks.GRAY_CONCRETE_CHESS_TIMER.get());
                output.accept(TTCBlocks.BLACK_CONCRETE_CHESS_TIMER.get());
                output.accept(TTCBlocks.BROWN_CONCRETE_CHESS_TIMER.get());
                output.accept(TTCBlocks.RED_CONCRETE_CHESS_TIMER.get());
                output.accept(TTCBlocks.ORANGE_CONCRETE_CHESS_TIMER.get());
                output.accept(TTCBlocks.YELLOW_CONCRETE_CHESS_TIMER.get());
                output.accept(TTCBlocks.LIME_CONCRETE_CHESS_TIMER.get());
                output.accept(TTCBlocks.GREEN_CONCRETE_CHESS_TIMER.get());
                output.accept(TTCBlocks.CYAN_CONCRETE_CHESS_TIMER.get());
                output.accept(TTCBlocks.LIGHT_BLUE_CONCRETE_CHESS_TIMER.get());
                output.accept(TTCBlocks.BLUE_CONCRETE_CHESS_TIMER.get());
                output.accept(TTCBlocks.PURPLE_CONCRETE_CHESS_TIMER.get());
                output.accept(TTCBlocks.MAGENTA_CONCRETE_CHESS_TIMER.get());
                output.accept(TTCBlocks.PINK_CONCRETE_CHESS_TIMER.get());
                // Connect Four
                output.accept(TTCBlocks.OAK_CONNECT_FOUR.get());
                output.accept(TTCBlocks.SPRUCE_CONNECT_FOUR.get());
                output.accept(TTCBlocks.BIRCH_CONNECT_FOUR.get());
                output.accept(TTCBlocks.JUNGLE_CONNECT_FOUR.get());
                output.accept(TTCBlocks.ACACIA_CONNECT_FOUR.get());
                output.accept(TTCBlocks.DARK_OAK_CONNECT_FOUR.get());
                output.accept(TTCBlocks.MANGROVE_CONNECT_FOUR.get());
                output.accept(TTCBlocks.CHERRY_CONNECT_FOUR.get());
                output.accept(TTCBlocks.BAMBOO_CONNECT_FOUR.get());
                output.accept(TTCBlocks.CRIMSON_CONNECT_FOUR.get());
                output.accept(TTCBlocks.WARPED_CONNECT_FOUR.get());
                output.accept(TTCBlocks.WHITE_TERRACOTTA_CONNECT_FOUR.get());
                output.accept(TTCBlocks.LIGHT_GRAY_TERRACOTTA_CONNECT_FOUR.get());
                output.accept(TTCBlocks.GRAY_TERRACOTTA_CONNECT_FOUR.get());
                output.accept(TTCBlocks.BLACK_TERRACOTTA_CONNECT_FOUR.get());
                output.accept(TTCBlocks.BROWN_TERRACOTTA_CONNECT_FOUR.get());
                output.accept(TTCBlocks.RED_TERRACOTTA_CONNECT_FOUR.get());
                output.accept(TTCBlocks.ORANGE_TERRACOTTA_CONNECT_FOUR.get());
                output.accept(TTCBlocks.YELLOW_TERRACOTTA_CONNECT_FOUR.get());
                output.accept(TTCBlocks.LIME_TERRACOTTA_CONNECT_FOUR.get());
                output.accept(TTCBlocks.GREEN_TERRACOTTA_CONNECT_FOUR.get());
                output.accept(TTCBlocks.CYAN_TERRACOTTA_CONNECT_FOUR.get());
                output.accept(TTCBlocks.LIGHT_BLUE_TERRACOTTA_CONNECT_FOUR.get());
                output.accept(TTCBlocks.BLUE_TERRACOTTA_CONNECT_FOUR.get());
                output.accept(TTCBlocks.PURPLE_TERRACOTTA_CONNECT_FOUR.get());
                output.accept(TTCBlocks.MAGENTA_TERRACOTTA_CONNECT_FOUR.get());
                output.accept(TTCBlocks.PINK_TERRACOTTA_CONNECT_FOUR.get());
                output.accept(TTCBlocks.WHITE_CONCRETE_CONNECT_FOUR.get());
                output.accept(TTCBlocks.LIGHT_GRAY_CONCRETE_CONNECT_FOUR.get());
                output.accept(TTCBlocks.GRAY_CONCRETE_CONNECT_FOUR.get());
                output.accept(TTCBlocks.BLACK_CONCRETE_CONNECT_FOUR.get());
                output.accept(TTCBlocks.BROWN_CONCRETE_CONNECT_FOUR.get());
                output.accept(TTCBlocks.RED_CONCRETE_CONNECT_FOUR.get());
                output.accept(TTCBlocks.ORANGE_CONCRETE_CONNECT_FOUR.get());
                output.accept(TTCBlocks.YELLOW_CONCRETE_CONNECT_FOUR.get());
                output.accept(TTCBlocks.LIME_CONCRETE_CONNECT_FOUR.get());
                output.accept(TTCBlocks.GREEN_CONCRETE_CONNECT_FOUR.get());
                output.accept(TTCBlocks.CYAN_CONCRETE_CONNECT_FOUR.get());
                output.accept(TTCBlocks.LIGHT_BLUE_CONCRETE_CONNECT_FOUR.get());
                output.accept(TTCBlocks.BLUE_CONCRETE_CONNECT_FOUR.get());
                output.accept(TTCBlocks.PURPLE_CONCRETE_CONNECT_FOUR.get());
                output.accept(TTCBlocks.MAGENTA_CONCRETE_CONNECT_FOUR.get());
                output.accept(TTCBlocks.PINK_CONCRETE_CONNECT_FOUR.get());
            }).build()
    );
}