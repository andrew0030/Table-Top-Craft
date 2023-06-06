package andrews.table_top_craft.events;

import andrews.table_top_craft.objects.blocks.ChessBlock;
import andrews.table_top_craft.registry.TTCBlocks;
import andrews.table_top_craft.util.Reference;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeTabEvents
{
    @SubscribeEvent
    public static void buildContents(CreativeModeTabEvent.Register event)
    {
        event.registerCreativeModeTab(new ResourceLocation(Reference.MODID, "tab"), builder ->
                builder.title(Component.translatable("itemGroup." + Reference.MODID + ".tab"))
                        .icon(() -> new ItemStack(TTCBlocks.OAK_CHESS.get()))
                        .displayItems((enabledFlags, populator, hasPermissions) -> {
                            // Misc
                            populator.accept(TTCBlocks.TIC_TAC_TOE.get());
                            populator.accept(TTCBlocks.CHESS_PIECE_FIGURE.get());
                            // Chess
                            populator.accept(TTCBlocks.OAK_CHESS.get());
                            populator.accept(TTCBlocks.SPRUCE_CHESS.get());
                            populator.accept(TTCBlocks.BIRCH_CHESS.get());
                            populator.accept(TTCBlocks.JUNGLE_CHESS.get());
                            populator.accept(TTCBlocks.ACACIA_CHESS.get());
                            populator.accept(TTCBlocks.DARK_OAK_CHESS.get());
                            populator.accept(TTCBlocks.MANGROVE_CHESS.get());
                            populator.accept(TTCBlocks.CRIMSON_CHESS.get());
                            populator.accept(TTCBlocks.WARPED_CHESS.get());
                            populator.accept(TTCBlocks.WHITE_TERRACOTTA_CHESS.get());
                            populator.accept(TTCBlocks.LIGHT_GRAY_TERRACOTTA_CHESS.get());
                            populator.accept(TTCBlocks.GRAY_TERRACOTTA_CHESS.get());
                            populator.accept(TTCBlocks.BLACK_TERRACOTTA_CHESS.get());
                            populator.accept(TTCBlocks.BROWN_TERRACOTTA_CHESS.get());
                            populator.accept(TTCBlocks.RED_TERRACOTTA_CHESS.get());
                            populator.accept(TTCBlocks.ORANGE_TERRACOTTA_CHESS.get());
                            populator.accept(TTCBlocks.YELLOW_TERRACOTTA_CHESS.get());
                            populator.accept(TTCBlocks.LIME_TERRACOTTA_CHESS.get());
                            populator.accept(TTCBlocks.GREEN_TERRACOTTA_CHESS.get());
                            populator.accept(TTCBlocks.CYAN_TERRACOTTA_CHESS.get());
                            populator.accept(TTCBlocks.LIGHT_BLUE_TERRACOTTA_CHESS.get());
                            populator.accept(TTCBlocks.BLUE_TERRACOTTA_CHESS.get());
                            populator.accept(TTCBlocks.PURPLE_TERRACOTTA_CHESS.get());
                            populator.accept(TTCBlocks.MAGENTA_TERRACOTTA_CHESS.get());
                            populator.accept(TTCBlocks.PINK_TERRACOTTA_CHESS.get());
                            populator.accept(TTCBlocks.WHITE_CONCRETE_CHESS.get());
                            populator.accept(TTCBlocks.LIGHT_GRAY_CONCRETE_CHESS.get());
                            populator.accept(TTCBlocks.GRAY_CONCRETE_CHESS.get());
                            populator.accept(TTCBlocks.BLACK_CONCRETE_CHESS.get());
                            populator.accept(TTCBlocks.BROWN_CONCRETE_CHESS.get());
                            populator.accept(TTCBlocks.RED_CONCRETE_CHESS.get());
                            populator.accept(TTCBlocks.ORANGE_CONCRETE_CHESS.get());
                            populator.accept(TTCBlocks.YELLOW_CONCRETE_CHESS.get());
                            populator.accept(TTCBlocks.LIME_CONCRETE_CHESS.get());
                            populator.accept(TTCBlocks.GREEN_CONCRETE_CHESS.get());
                            populator.accept(TTCBlocks.CYAN_CONCRETE_CHESS.get());
                            populator.accept(TTCBlocks.LIGHT_BLUE_CONCRETE_CHESS.get());
                            populator.accept(TTCBlocks.BLUE_CONCRETE_CHESS.get());
                            populator.accept(TTCBlocks.PURPLE_CONCRETE_CHESS.get());
                            populator.accept(TTCBlocks.MAGENTA_CONCRETE_CHESS.get());
                            populator.accept(TTCBlocks.PINK_CONCRETE_CHESS.get());
                            // Timer
                            populator.accept(TTCBlocks.OAK_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.SPRUCE_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.BIRCH_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.JUNGLE_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.ACACIA_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.DARK_OAK_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.MANGROVE_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.CRIMSON_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.WARPED_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.WHITE_TERRACOTTA_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.LIGHT_GRAY_TERRACOTTA_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.GRAY_TERRACOTTA_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.BLACK_TERRACOTTA_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.BROWN_TERRACOTTA_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.RED_TERRACOTTA_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.ORANGE_TERRACOTTA_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.YELLOW_TERRACOTTA_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.LIME_TERRACOTTA_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.GREEN_TERRACOTTA_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.CYAN_TERRACOTTA_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.LIGHT_BLUE_TERRACOTTA_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.BLUE_TERRACOTTA_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.PURPLE_TERRACOTTA_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.MAGENTA_TERRACOTTA_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.PINK_TERRACOTTA_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.WHITE_CONCRETE_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.LIGHT_GRAY_CONCRETE_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.GRAY_CONCRETE_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.BLACK_CONCRETE_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.BROWN_CONCRETE_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.RED_CONCRETE_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.ORANGE_CONCRETE_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.YELLOW_CONCRETE_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.LIME_CONCRETE_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.GREEN_CONCRETE_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.CYAN_CONCRETE_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.LIGHT_BLUE_CONCRETE_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.BLUE_CONCRETE_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.PURPLE_CONCRETE_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.MAGENTA_CONCRETE_CHESS_TIMER.get());
                            populator.accept(TTCBlocks.PINK_CONCRETE_CHESS_TIMER.get());
                            // Connect Four
                            populator.accept(TTCBlocks.OAK_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.SPRUCE_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.BIRCH_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.JUNGLE_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.ACACIA_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.DARK_OAK_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.MANGROVE_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.CRIMSON_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.WARPED_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.WHITE_TERRACOTTA_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.LIGHT_GRAY_TERRACOTTA_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.GRAY_TERRACOTTA_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.BLACK_TERRACOTTA_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.BROWN_TERRACOTTA_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.RED_TERRACOTTA_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.ORANGE_TERRACOTTA_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.YELLOW_TERRACOTTA_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.LIME_TERRACOTTA_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.GREEN_TERRACOTTA_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.CYAN_TERRACOTTA_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.LIGHT_BLUE_TERRACOTTA_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.BLUE_TERRACOTTA_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.PURPLE_TERRACOTTA_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.MAGENTA_TERRACOTTA_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.PINK_TERRACOTTA_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.WHITE_CONCRETE_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.LIGHT_GRAY_CONCRETE_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.GRAY_CONCRETE_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.BLACK_CONCRETE_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.BROWN_CONCRETE_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.RED_CONCRETE_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.ORANGE_CONCRETE_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.YELLOW_CONCRETE_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.LIME_CONCRETE_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.GREEN_CONCRETE_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.CYAN_CONCRETE_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.LIGHT_BLUE_CONCRETE_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.BLUE_CONCRETE_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.PURPLE_CONCRETE_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.MAGENTA_CONCRETE_CONNECT_FOUR.get());
                            populator.accept(TTCBlocks.PINK_CONCRETE_CONNECT_FOUR.get());
                        })
        );
    }
}