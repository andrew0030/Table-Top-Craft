package andrews.table_top_craft.events;

import andrews.table_top_craft.registry.TTCBlocks;
import andrews.table_top_craft.util.Reference;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
                            populator.accept(TTCBlocks.OAK_CHESS.get());
                            populator.accept(TTCBlocks.SPRUCE_CHESS.get());
                            populator.accept(TTCBlocks.BIRCH_CHESS.get());
                            populator.accept(TTCBlocks.JUNGLE_CHESS.get());
                            populator.accept(TTCBlocks.ACACIA_CHESS.get());
                            populator.accept(TTCBlocks.DARK_OAK_CHESS.get());
                            populator.accept(TTCBlocks.MANGROVE_CHESS.get());
                            populator.accept(TTCBlocks.CRIMSON_CHESS.get());
                            populator.accept(TTCBlocks.WARPED_CHESS.get());
                            populator.accept(TTCBlocks.CHESS_PIECE_FIGURE.get());

                            populator.accept(TTCBlocks.WHITE_CHESS_TIMER.get());

                            populator.accept(TTCBlocks.TIC_TAC_TOE.get());
                        })
        );
    }
}