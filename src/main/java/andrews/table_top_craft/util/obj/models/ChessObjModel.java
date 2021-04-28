package andrews.table_top_craft.util.obj.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import andrews.table_top_craft.game_logic.chess.PieceColor;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece.PieceType;
import andrews.table_top_craft.util.Reference;
import andrews.table_top_craft.util.obj.ObjModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
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
	
	private static final String PAWN_CLASSIC_MODEL_PATH = "models/pieces/classic/pawn.obj";
	private static final String ROOK_CLASSIC_MODEL_PATH = "models/pieces/classic/rook.obj";
	private static final String BISHOP_CLASSIC_MODEL_PATH = "models/pieces/classic/bishop.obj";
	private static final String KNIGHT_CLASSIC_MODEL_PATH = "models/pieces/classic/knight.obj";
	private static final String KING_CLASSIC_MODEL_PATH = "models/pieces/classic/king.obj";
	private static final String QUEEN_CLASSIC_MODEL_PATH = "models/pieces/classic/queen.obj";
	
	private final ObjModel PAWN_MODEL;
	private final ObjModel ROOK_MODEL;
	private final ObjModel BISHOP_MODEL;
	private final ObjModel KNIGHT_MODEL;
	private final ObjModel KING_MODEL;
	private final ObjModel QUEEN_MODEL;
	
	private final ObjModel PAWN_CLASSIC_MODEL;
	private final ObjModel ROOK_CLASSIC_MODEL;
	private final ObjModel BISHOP_CLASSIC_MODEL;
	private final ObjModel KNIGHT_CLASSIC_MODEL;
	private final ObjModel KING_CLASSIC_MODEL;
	private final ObjModel QUEEN_CLASSIC_MODEL;
	
	public ChessObjModel()
	{
		PAWN_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, PAWN_MODEL_PATH));
		ROOK_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, ROOK_MODEL_PATH));
		BISHOP_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, BISHOP_MODEL_PATH));
		KNIGHT_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, KNIGHT_MODEL_PATH));
		KING_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, KING_MODEL_PATH));
		QUEEN_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, QUEEN_MODEL_PATH));
		
		PAWN_CLASSIC_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, PAWN_CLASSIC_MODEL_PATH));
		ROOK_CLASSIC_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, ROOK_CLASSIC_MODEL_PATH));
		BISHOP_CLASSIC_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, BISHOP_CLASSIC_MODEL_PATH));
		KNIGHT_CLASSIC_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, KNIGHT_CLASSIC_MODEL_PATH));
		KING_CLASSIC_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, KING_CLASSIC_MODEL_PATH));
		QUEEN_CLASSIC_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, QUEEN_CLASSIC_MODEL_PATH));
	}
	
	/**
	 * Renders the ObjModel
	 * @param stack - The MatrixStack
	 * @param buffer - The IRenderTypeBuffer
	 * @param combinedLightIn - The combinedLightIn of the Block we are rendering on
	 * @param pieceType - The Chess Piece Type
	 * @param pieceColor - The Chess Piece Color
	 * @param wR - Whites RGB values, value: Red
	 * @param wG - Whites RGB values, value: Green
	 * @param wB - Whites RGB values, value: Blue
	 * @param bR - Blacks RGB values, value: Red
	 * @param bG - Blacks RGB values, value: Green
	 * @param bB - Blacks RGB values, value: Blue
	 */
	public void render(MatrixStack stack, IRenderTypeBuffer buffer, int combinedLightIn, PieceType pieceType, PieceColor pieceColor, float wR, float wG, float wB, float bR, float bG, float bB)
	{
		float red = pieceColor.isWhite() ? wR : bR;
        float green = pieceColor.isWhite() ? wG : bG;
        float blue = pieceColor.isWhite() ? wB : bB;
		
		stack.push();
		// We scale the Piece and invert the rendering
		stack.scale(1F, -1F, -1F);
		stack.scale(0.1F, 0.1F, 0.1F);
		// We rotate the Piece 180 Degrees if its Black and supposed to face the other way
		if(pieceColor.isWhite())
			stack.rotate(Vector3f.YN.rotationDegrees(180F));
		
		// We render a Piece depending on the PiceType
		switch(pieceType)
		{
		default:
		case PAWN:
			PAWN_MODEL.render(stack, buffer, combinedLightIn, red, green, blue);
//			PAWN_CLASSIC_MODEL.render(stack, buffer, combinedLightIn, pieceColor, wR, wG, wB, bR, bG, bB);
			break;
		case ROOK:
			ROOK_MODEL.render(stack, buffer, combinedLightIn, red, green, blue);
//			ROOK_CLASSIC_MODEL.render(stack, buffer, combinedLightIn, pieceColor, wR, wG, wB, bR, bG, bB);
			break;
		case BISHOP:
			BISHOP_MODEL.render(stack, buffer, combinedLightIn, red, green, blue);
//			BISHOP_CLASSIC_MODEL.render(stack, buffer, combinedLightIn, pieceColor, wR, wG, wB, bR, bG, bB);
			break;
		case KNIGHT:
			KNIGHT_MODEL.render(stack, buffer, combinedLightIn, red, green, blue);
//			KNIGHT_CLASSIC_MODEL.render(stack, buffer, combinedLightIn, pieceColor, wR, wG, wB, bR, bG, bB);
			break;
		case KING:
			KING_MODEL.render(stack, buffer, combinedLightIn, red, green, blue);
//			KING_CLASSIC_MODEL.render(stack, buffer, combinedLightIn, pieceColor, wR, wG, wB, bR, bG, bB);
			break;
		case QUEEN:
			QUEEN_MODEL.render(stack, buffer, combinedLightIn, red, green, blue);
//			QUEEN_CLASSIC_MODEL.render(stack, buffer, combinedLightIn, pieceColor, wR, wG, wB, bR, bG, bB);
		}
		stack.pop();
	}
}