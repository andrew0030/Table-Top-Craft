package andrews.table_top_craft.util.obj.models;

import com.mojang.blaze3d.matrix.MatrixStack;

import andrews.table_top_craft.game_logic.chess.PieceColor;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece.PieceType;
import andrews.table_top_craft.util.Reference;
import andrews.table_top_craft.util.obj.ObjModel;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChessObjModel
{
	private static final String PAWN_MODEL_PATH = "models/pieces/pawn.obj";
	private static final String ROOK_MODEL_PATH = "models/pieces/rook.obj";
	private static final String BISHOP_MODEL_PATH = "models/pieces/bishop.obj";
	private static final String KNIGHT_MODEL_PATH = "models/pieces/knight.obj";
	private static final String KING_MODEL_PATH = "models/pieces/king.obj";
	private static final String QUEEN_MODEL_PATH = "models/pieces/queen.obj";
	
//	private static final String PAWN_CLASSIC_MODEL_PATH = "models/pieces/classic/pawn.obj";
//	private static final String ROOK_CLASSIC_MODEL_PATH = "models/pieces/classic/rook.obj";
//	private static final String BISHOP_CLASSIC_MODEL_PATH = "models/pieces/classic/bishop.obj";
//	private static final String KNIGHT_CLASSIC_MODEL_PATH = "models/pieces/classic/knight.obj";
//	private static final String KING_CLASSIC_MODEL_PATH = "models/pieces/classic/king.obj";
//	private static final String QUEEN_CLASSIC_MODEL_PATH = "models/pieces/classic/queen.obj";
	
	private final ObjModel PAWN_MODEL;
	private final ObjModel ROOK_MODEL;
	private final ObjModel BISHOP_MODEL;
	private final ObjModel KNIGHT_MODEL;
	private final ObjModel KING_MODEL;
	private final ObjModel QUEEN_MODEL;
	
//	private final ObjModel PAWN_CLASSIC_MODEL;
//	private final ObjModel ROOK_CLASSIC_MODEL;
//	private final ObjModel BISHOP_CLASSIC_MODEL;
//	private final ObjModel KNIGHT_CLASSIC_MODEL;
//	private final ObjModel KING_CLASSIC_MODEL;
//	private final ObjModel QUEEN_CLASSIC_MODEL;
	
	public ChessObjModel()
	{
		PAWN_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, PAWN_MODEL_PATH));
		ROOK_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, ROOK_MODEL_PATH));
		BISHOP_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, BISHOP_MODEL_PATH));
		KNIGHT_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, KNIGHT_MODEL_PATH));
		KING_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, KING_MODEL_PATH));
		QUEEN_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, QUEEN_MODEL_PATH));
		
//		PAWN_CLASSIC_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, PAWN_CLASSIC_MODEL_PATH));
//		ROOK_CLASSIC_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, ROOK_CLASSIC_MODEL_PATH));
//		BISHOP_CLASSIC_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, BISHOP_CLASSIC_MODEL_PATH));
//		KNIGHT_CLASSIC_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, KNIGHT_CLASSIC_MODEL_PATH));
//		KING_CLASSIC_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, KING_CLASSIC_MODEL_PATH));
//		QUEEN_CLASSIC_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, QUEEN_CLASSIC_MODEL_PATH));
	}
	
	/**
	 * Renders the ObjModel
	 * @param stack - The MatrixStack
	 * @param buffer - The IRenderTypeBuffer
	 * @param pieceType - The Chess Piece Type
	 */
	public void render(MatrixStack stack, BufferBuilder buffer, PieceType pieceType)
	{	
		stack.push();
		// We scale the Piece and invert the rendering
		stack.scale(1F, -1F, -1F);
		stack.scale(0.1F, 0.1F, 0.1F);
		
		// We render a Piece depending on the PiceType
		switch(pieceType)
		{
		default:
		case PAWN:
			PAWN_MODEL.render(stack, buffer);
//			PAWN_CLASSIC_MODEL.render(stack, buffer, combinedLightIn, pieceColor, wR, wG, wB, bR, bG, bB);
			break;
		case ROOK:
			ROOK_MODEL.render(stack, buffer);
//			ROOK_CLASSIC_MODEL.render(stack, buffer, combinedLightIn, pieceColor, wR, wG, wB, bR, bG, bB);
			break;
		case BISHOP:
			BISHOP_MODEL.render(stack, buffer);
//			BISHOP_CLASSIC_MODEL.render(stack, buffer, combinedLightIn, pieceColor, wR, wG, wB, bR, bG, bB);
			break;
		case KNIGHT:
			KNIGHT_MODEL.render(stack, buffer);
//			KNIGHT_CLASSIC_MODEL.render(stack, buffer, combinedLightIn, pieceColor, wR, wG, wB, bR, bG, bB);
			break;
		case KING:
			KING_MODEL.render(stack, buffer);
//			KING_CLASSIC_MODEL.render(stack, buffer, combinedLightIn, pieceColor, wR, wG, wB, bR, bG, bB);
			break;
		case QUEEN:
			QUEEN_MODEL.render(stack, buffer);
//			QUEEN_CLASSIC_MODEL.render(stack, buffer, combinedLightIn, pieceColor, wR, wG, wB, bR, bG, bB);
		}
		stack.pop();
	}
}