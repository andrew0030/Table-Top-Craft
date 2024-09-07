package andrews.table_top_craft.registry;

import andrews.table_top_craft.objects.blocks.ChessBlock;
import andrews.table_top_craft.tile_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.tile_entities.TicTacToeBlockEntity;
import andrews.table_top_craft.tile_entities.render.ChessPieceFigureTileEntityRenderer;
import andrews.table_top_craft.tile_entities.render.ChessTileEntityRenderer;
import andrews.table_top_craft.tile_entities.render.TicTacToeBlockEntityRenderer;
import andrews.table_top_craft.util.Reference;
import com.google.common.collect.Sets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class TTCTileEntities
{
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Reference.MODID);
	
	public static final RegistryObject<BlockEntityType<ChessTileEntity>> CHESS                              = BLOCK_ENTITY_TYPES.register("chess", () -> new BlockEntityType<>(ChessTileEntity::new, TTCTileEntities.getBlocksOfClass(ChessBlock.class), null));
    public static final RegistryObject<BlockEntityType<ChessPieceFigureBlockEntity>> CHESS_PIECE_FIGURE     = BLOCK_ENTITY_TYPES.register("chess_piece_figure", () -> new BlockEntityType<>(ChessPieceFigureBlockEntity::new, Sets.newHashSet(TTCBlocks.CHESS_PIECE_FIGURE.get()), null));
    public static final RegistryObject<BlockEntityType<TicTacToeBlockEntity>> TIC_TAC_TOE                   = BLOCK_ENTITY_TYPES.register("tic_tac_toe", () -> new BlockEntityType<>(TicTacToeBlockEntity::new, Sets.newHashSet(TTCBlocks.TIC_TAC_TOE.get()), null));

    public static void registerTileRenders()
    {
        BlockEntityRenderers.register(CHESS.get(), ChessTileEntityRenderer::new);
        BlockEntityRenderers.register(CHESS_PIECE_FIGURE.get(), ChessPieceFigureTileEntityRenderer::new);
        BlockEntityRenderers.register(TIC_TAC_TOE.get(), TicTacToeBlockEntityRenderer::new);
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