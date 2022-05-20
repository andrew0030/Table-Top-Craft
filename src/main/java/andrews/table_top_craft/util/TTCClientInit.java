package andrews.table_top_craft.util;

import andrews.table_top_craft.network.TTCNetwork;
import andrews.table_top_craft.registry.TTCTileEntities;
import andrews.table_top_craft.tile_entities.model.chess.ChessBoardPlateModel;
import andrews.table_top_craft.tile_entities.model.chess.ChessHighlightModel;
import andrews.table_top_craft.tile_entities.model.chess.ChessTilesInfoModel;
import andrews.table_top_craft.tile_entities.model.piece_figure.ChessPieceFigureStandModel;
import andrews.table_top_craft.tile_entities.model.tic_tac_toe.TicTacToeModel;
import andrews.table_top_craft.tile_entities.render.item.TTCBlockEntityWithoutLevelRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.renderer.ShaderInstance;

import javax.annotation.Nullable;
import java.util.Objects;

public class TTCClientInit implements ClientModInitializer
{
    @Nullable
	public static ShaderInstance rendertypeSolidBlockEntityShader;

	/**
	 * @return The Table Top Craft chess piece Shader.
	 */
	public static ShaderInstance getSolidBlockEntityShader()
	{
		return Objects.requireNonNull(rendertypeSolidBlockEntityShader, "Attempted to call getSolidBlockEntityShader before shaders have finished loading.");
	}

    @Override
    public void onInitializeClient()
    {
        // We dont have any shaders in here, because I hardcoded the registration inside a Mixin as there is only 1 shader
        // Model Layers
        EntityModelLayerRegistry.registerModelLayer(ChessBoardPlateModel.CHESS_BOARD_PLATE_LAYER, ChessBoardPlateModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(ChessHighlightModel.CHESS_HIGHLIGHT_LAYER, ChessHighlightModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(ChessTilesInfoModel.CHESS_TILES_INFO_LAYER, ChessTilesInfoModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(ChessPieceFigureStandModel.CHESS_PIECE_FIGURE_LAYER, ChessPieceFigureStandModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(TicTacToeModel.TIC_TAC_TOE_LAYER, TicTacToeModel::createBodyLayer);
		// Block Entity Renderers
        TTCTileEntities.registerBlockEntityRenderers();
		TTCBlockEntityWithoutLevelRenderer.init();
		TTCNetwork.registerClientNetworkMessages();
    }
}