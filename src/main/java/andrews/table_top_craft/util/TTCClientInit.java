package andrews.table_top_craft.util;

import andrews.table_top_craft.block_entities.model.chess.ChessBoardPlateModel;
import andrews.table_top_craft.block_entities.model.chess.ChessHighlightModel;
import andrews.table_top_craft.block_entities.model.chess.ChessTilesInfoModel;
import andrews.table_top_craft.block_entities.model.chess.GhostModel;
import andrews.table_top_craft.block_entities.model.connect_four.ConnectFourFallingPieceModel;
import andrews.table_top_craft.block_entities.model.connect_four.ConnectFourMeshModel;
import andrews.table_top_craft.block_entities.model.connect_four.ConnectFourPieceModel;
import andrews.table_top_craft.block_entities.model.piece_figure.ChessPieceFigureStandModel;
import andrews.table_top_craft.block_entities.model.tic_tac_toe.TicTacToeModel;
import andrews.table_top_craft.block_entities.render.item.TTCBlockEntityWithoutLevelRenderer;
import andrews.table_top_craft.network.TTCNetwork;
import andrews.table_top_craft.registry.TTCBlockEntities;
import andrews.table_top_craft.registry.TTCParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.renderer.ShaderInstance;
import org.jetbrains.annotations.Nullable;

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
        // Model Layers
        EntityModelLayerRegistry.registerModelLayer(ChessBoardPlateModel.CHESS_BOARD_PLATE_LAYER, ChessBoardPlateModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(ChessHighlightModel.CHESS_HIGHLIGHT_LAYER, ChessHighlightModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(ChessTilesInfoModel.CHESS_TILES_INFO_LAYER, ChessTilesInfoModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(ChessPieceFigureStandModel.CHESS_PIECE_FIGURE_LAYER, ChessPieceFigureStandModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(TicTacToeModel.TIC_TAC_TOE_LAYER, TicTacToeModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(GhostModel.LAYER, GhostModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(ConnectFourMeshModel.LAYER, ConnectFourMeshModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(ConnectFourPieceModel.LAYER, ConnectFourPieceModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(ConnectFourFallingPieceModel.LAYER, ConnectFourFallingPieceModel::createBodyLayer);
		// Block Entity Renderers
        TTCBlockEntities.registerBlockEntityRenderers();
		TTCBlockEntityWithoutLevelRenderer.init();
		// Particles
		TTCParticles.registerParticles();
		// Networking
		TTCNetwork.registerClientNetworkMessages();
	}
}