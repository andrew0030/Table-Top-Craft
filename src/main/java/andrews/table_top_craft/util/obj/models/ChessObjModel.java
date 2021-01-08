package andrews.table_top_craft.util.obj.models;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;

import andrews.table_top_craft.game_logic.chess.PieceColor;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece.PieceType;
import andrews.table_top_craft.util.Reference;
import andrews.table_top_craft.util.obj.ObjModel;
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
	
	private final ObjModel PAWN_MODEL;
	private final ObjModel ROOK_MODEL;
	private final ObjModel BISHOP_MODEL;
	private final ObjModel KNIGHT_MODEL;
	private final ObjModel KING_MODEL;
	private final ObjModel QUEEN_MODEL;
	
	public ChessObjModel()
	{
		PAWN_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, PAWN_MODEL_PATH));
		ROOK_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, ROOK_MODEL_PATH));
		BISHOP_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, BISHOP_MODEL_PATH));
		KNIGHT_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, KNIGHT_MODEL_PATH));
		KING_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, KING_MODEL_PATH));
		QUEEN_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, QUEEN_MODEL_PATH));
	}

	@SuppressWarnings("deprecation")
	public void render(PieceType pieceType, PieceColor pieceColor, float wR, float wG, float wB, float bR, float bG, float bB)
	{	
		GL11.glPushMatrix();
		// Size and Inverted Rendering Fix
		GL11.glScalef(1F, -1F, -1F);
		GL11.glScaled(0.1, 0.1, 0.1);
		// Lighting Fix
        RenderSystem.enableRescaleNormal();
        GL11.glEnable(GL11.GL_LIGHTING);
        // Color and or Lighting Fix
        RenderSystem.enableLighting();
        RenderSystem.enableColorMaterial();
        // Adjust Color
        float red = pieceColor.isWhite() ? wR : bR;
        float green = pieceColor.isWhite() ? wG : bG;
        float blue = pieceColor.isWhite() ? wB : bB;
        RenderSystem.color3f(red, green, blue);
        // Rotates the Piece if needed
        if(pieceColor.isBlack())
        	GL11.glRotatef(180F, 0, 1, 0);
        switch(pieceType)
		{
		default:
		case PAWN:
			PAWN_MODEL.render();
			break;
		case ROOK:
			ROOK_MODEL.render();
			break;
		case BISHOP:
			BISHOP_MODEL.render();
			break;
		case KNIGHT:
			KNIGHT_MODEL.render();
			break;
		case KING:
			KING_MODEL.render();
			break;
		case QUEEN:
			QUEEN_MODEL.render();
		}
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
}