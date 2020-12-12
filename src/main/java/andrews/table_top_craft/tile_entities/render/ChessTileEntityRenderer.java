package andrews.table_top_craft.tile_entities.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.tile_entities.model.chess.ChessPawnModel;
import andrews.table_top_craft.util.Reference;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class ChessTileEntityRenderer extends TileEntityRenderer<ChessTileEntity>
{
	private static final ResourceLocation PAWN_TEXTURE_BLACK = new ResourceLocation(Reference.MODID, "textures/tile/chess/black_pieces.png");
    private static final ResourceLocation PAWN_TEXTURE_WHITE = new ResourceLocation(Reference.MODID, "textures/tile/chess/white_pieces.png");
    private static final float MC_SCALE = 0.0625F;
    private static final float CHESS_SCALE = 0.125F;
    private final float CHESS_PIECE_SCALE = 0.1F;
	
    //Chess Figures Models
    private final ChessPawnModel pawnModel;
//    private final ChessRookModel rookModel;
//    private final ChessKnightModel knightModel;
//    private final ChessKingModel kingModel;
//    private final ChessQueenModel queenModel;
//    private final ChessBishopModel bishopModel;
    
	public ChessTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
	{
		super(rendererDispatcherIn);
		pawnModel = new ChessPawnModel();
	}

	@Override
	public void render(ChessTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		matrixStackIn.push();
		
		matrixStackIn.translate(0.5D, 0.9D, 0.5D);
		matrixStackIn.scale(1.0F, -1.0F, -1.0F);
		
		renderPawn(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
		
		matrixStackIn.pop();
	}
	
	private void renderPawn(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		IVertexBuilder builderPawn = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(PAWN_TEXTURE_WHITE));
		
		matrixStackIn.rotate(Vector3f.YN.rotationDegrees(90F));
		matrixStackIn.translate(CHESS_SCALE / 2D, 0.0D, CHESS_SCALE / 2D);
		matrixStackIn.translate(CHESS_SCALE * 3D, 0.0D, CHESS_SCALE * 2D);
		matrixStackIn.scale(CHESS_PIECE_SCALE, CHESS_PIECE_SCALE, CHESS_PIECE_SCALE);
		pawnModel.Base.render(matrixStackIn, builderPawn, combinedLightIn, combinedOverlayIn);
	}
}