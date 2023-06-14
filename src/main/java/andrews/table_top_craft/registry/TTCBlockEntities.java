package andrews.table_top_craft.registry;

import andrews.table_top_craft.objects.blocks.ChessBlock;
import andrews.table_top_craft.objects.blocks.ChessTimerBlock;
import andrews.table_top_craft.objects.blocks.ConnectFourBlock;
import andrews.table_top_craft.block_entities.*;
import andrews.table_top_craft.block_entities.render.*;
import andrews.table_top_craft.util.Reference;
import com.google.common.collect.Sets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class TTCBlockEntities
{
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Reference.MODID);

	public static final RegistryObject<BlockEntityType<ChessBlockEntity>> CHESS                              = BLOCK_ENTITY_TYPES.register("chess", () -> new BlockEntityType<>(ChessBlockEntity::new, TTCBlockEntities.getBlocksOfClass(ChessBlock.class), null));
    public static final RegistryObject<BlockEntityType<ChessPieceFigureBlockEntity>> CHESS_PIECE_FIGURE     = BLOCK_ENTITY_TYPES.register("chess_piece_figure", () -> new BlockEntityType<>(ChessPieceFigureBlockEntity::new, Sets.newHashSet(TTCBlocks.CHESS_PIECE_FIGURE.get()), null));
    public static final RegistryObject<BlockEntityType<TicTacToeBlockEntity>> TIC_TAC_TOE                   = BLOCK_ENTITY_TYPES.register("tic_tac_toe", () -> new BlockEntityType<>(TicTacToeBlockEntity::new, Sets.newHashSet(TTCBlocks.TIC_TAC_TOE.get()), null));
    public static final RegistryObject<BlockEntityType<ChessTimerBlockEntity>> CHESS_TIMER                  = BLOCK_ENTITY_TYPES.register("chess_timer", () -> new BlockEntityType<>(ChessTimerBlockEntity::new, TTCBlockEntities.getBlocksOfClass(ChessTimerBlock.class), null));
    public static final RegistryObject<BlockEntityType<ConnectFourBlockEntity>> CONNECT_FOUR                = BLOCK_ENTITY_TYPES.register("connect_four", () -> new BlockEntityType<>(ConnectFourBlockEntity::new, TTCBlockEntities.getBlocksOfClass(ConnectFourBlock.class), null));

    public static void registerTileRenders()
    {
        BlockEntityRenderers.register(CHESS.get(), ChessTileEntityRenderer::new);
        BlockEntityRenderers.register(CHESS_PIECE_FIGURE.get(), ChessPieceFigureTileEntityRenderer::new);
        BlockEntityRenderers.register(TIC_TAC_TOE.get(), TicTacToeBlockEntityRenderer::new);
        BlockEntityRenderers.register(CHESS_TIMER.get(), ChessTimerBlockEntityRenderer::new);
        BlockEntityRenderers.register(CONNECT_FOUR.get(), ConnectFourBlockEntityRenderer::new);
    }

    private static Set<Block> getBlocksOfClass(Class<?> clazz)
    {
        Set<Block> blocks = Sets.newHashSet();
        TTCBlocks.BLOCKS.getEntries().forEach(blockRO -> {
            if (clazz.isAssignableFrom(blockRO.get().getClass()))
                blocks.add(blockRO.get());
        });
        return blocks;
    }
}