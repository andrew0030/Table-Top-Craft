package andrews.table_top_craft.util.obj.models;

import andrews.table_top_craft.game_logic.chess.pieces.BasePiece.PieceModelSet;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece.PieceType;
import andrews.table_top_craft.util.Reference;
import andrews.table_top_craft.util.obj.ObjModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.resources.ResourceLocation;
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

	private static final String CLASSIC_PAWN_MODEL_PATH = "models/pieces/classic/classic_pawn.obj";
	private static final String CLASSIC_ROOK_MODEL_PATH = "models/pieces/classic/classic_rook.obj";
	private static final String CLASSIC_BISHOP_MODEL_PATH = "models/pieces/classic/classic_bishop.obj";
	private static final String CLASSIC_KNIGHT_MODEL_PATH = "models/pieces/classic/classic_knight.obj";
	private static final String CLASSIC_KING_MODEL_PATH = "models/pieces/classic/classic_king.obj";
	private static final String CLASSIC_QUEEN_MODEL_PATH = "models/pieces/classic/classic_queen.obj";
	
	private final ObjModel PAWN_MODEL;
	private final ObjModel ROOK_MODEL;
	private final ObjModel BISHOP_MODEL;
	private final ObjModel KNIGHT_MODEL;
	private final ObjModel KING_MODEL;
	private final ObjModel QUEEN_MODEL;

	private final ObjModel CLASSIC_PAWN_MODEL;
	private final ObjModel CLASSIC_ROOK_MODEL;
	private final ObjModel CLASSIC_BISHOP_MODEL;
	private final ObjModel CLASSIC_KNIGHT_MODEL;
	private final ObjModel CLASSIC_KING_MODEL;
	private final ObjModel CLASSIC_QUEEN_MODEL;
	
	public ChessObjModel()
	{
		PAWN_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, PAWN_MODEL_PATH));
		ROOK_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, ROOK_MODEL_PATH));
		BISHOP_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, BISHOP_MODEL_PATH));
		KNIGHT_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, KNIGHT_MODEL_PATH));
		KING_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, KING_MODEL_PATH));
		QUEEN_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, QUEEN_MODEL_PATH));

		CLASSIC_PAWN_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, CLASSIC_PAWN_MODEL_PATH));
		CLASSIC_ROOK_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, CLASSIC_ROOK_MODEL_PATH));
		CLASSIC_BISHOP_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, CLASSIC_BISHOP_MODEL_PATH));
		CLASSIC_KNIGHT_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, CLASSIC_KNIGHT_MODEL_PATH));
		CLASSIC_KING_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, CLASSIC_KING_MODEL_PATH));
		CLASSIC_QUEEN_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, CLASSIC_QUEEN_MODEL_PATH));
	}
	
	/**
	 * Renders the ObjModel
	 * @param stack The MatrixStack
	 * @param buffer The IRenderTypeBuffer
	 * @param pieceType The Chess Piece Type
	 */
	public void render(PoseStack stack, VertexConsumer buffer, PieceType pieceType, PieceModelSet pieceModelSet)
	{	
		stack.pushPose();
		// We scale the Piece and invert the rendering
		stack.scale(1F, -1F, -1F);
		stack.scale(0.1F, 0.1F, 0.1F);

		switch (pieceModelSet)
		{
			case STANDARD:
				switch (pieceType)
				{
					case PAWN -> PAWN_MODEL.render(stack, buffer);
					case ROOK -> ROOK_MODEL.render(stack, buffer);
					case BISHOP -> BISHOP_MODEL.render(stack, buffer);
					case KNIGHT -> KNIGHT_MODEL.render(stack, buffer);
					case KING -> KING_MODEL.render(stack, buffer);
					case QUEEN -> QUEEN_MODEL.render(stack, buffer);
				}
				break;
			case CLASSIC:
				switch (pieceType)
				{
					case PAWN -> CLASSIC_PAWN_MODEL.render(stack, buffer);
					case ROOK -> CLASSIC_ROOK_MODEL.render(stack, buffer);
					case BISHOP -> CLASSIC_BISHOP_MODEL.render(stack, buffer);
					case KNIGHT -> CLASSIC_KNIGHT_MODEL.render(stack, buffer);
					case KING -> CLASSIC_KING_MODEL.render(stack, buffer);
					case QUEEN -> CLASSIC_QUEEN_MODEL.render(stack, buffer);
				}
		}
		stack.popPose();
	}
}