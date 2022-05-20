package andrews.table_top_craft.registry;

import andrews.table_top_craft.tile_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.tile_entities.TicTacToeBlockEntity;
import andrews.table_top_craft.tile_entities.render.ChessPieceFigureTileEntityRenderer;
import andrews.table_top_craft.tile_entities.render.ChessTileEntityRenderer;
import andrews.table_top_craft.tile_entities.render.TicTacToeBlockEntityRenderer;
import andrews.table_top_craft.util.Reference;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class TTCTileEntities
{
	public static final BlockEntityType<ChessTileEntity> CHESS                              = Registry.register(Registry.BLOCK_ENTITY_TYPE, new ResourceLocation(Reference.MODID, "chess"), FabricBlockEntityTypeBuilder.create(ChessTileEntity::new, TTCBlocks.OAK_CHESS, TTCBlocks.SPRUCE_CHESS, TTCBlocks.BIRCH_CHESS, TTCBlocks.JUNGLE_CHESS, TTCBlocks.ACACIA_CHESS, TTCBlocks.DARK_OAK_CHESS, TTCBlocks.CRIMSON_CHESS, TTCBlocks.WARPED_CHESS).build(null));
    public static final BlockEntityType<ChessPieceFigureBlockEntity> CHESS_PIECE_FIGURE     = Registry.register(Registry.BLOCK_ENTITY_TYPE, new ResourceLocation(Reference.MODID, "chess_piece_figure"), FabricBlockEntityTypeBuilder.create(ChessPieceFigureBlockEntity::new, TTCBlocks.CHESS_PIECE_FIGURE).build(null));
    public static final BlockEntityType<TicTacToeBlockEntity> TIC_TAC_TOE                   = Registry.register(Registry.BLOCK_ENTITY_TYPE, new ResourceLocation(Reference.MODID, "tic_tac_toe"), FabricBlockEntityTypeBuilder.create(TicTacToeBlockEntity::new, TTCBlocks.TIC_TAC_TOE).build(null));

    public static void init() {}

    public static void registerBlockEntityRenderers()
    {
        BlockEntityRendererRegistry.register(CHESS, ChessTileEntityRenderer::new);
        BlockEntityRendererRegistry.register(CHESS_PIECE_FIGURE, ChessPieceFigureTileEntityRenderer::new);
        BlockEntityRendererRegistry.register(TIC_TAC_TOE, TicTacToeBlockEntityRenderer::new);
    }
}