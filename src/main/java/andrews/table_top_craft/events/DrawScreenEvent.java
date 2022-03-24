package andrews.table_top_craft.events;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;

import andrews.table_top_craft.game_logic.chess.pieces.BasePiece.PieceType;
import andrews.table_top_craft.util.Reference;
import andrews.table_top_craft.util.TTCRenderTypes;
import andrews.table_top_craft.util.obj.models.ChessObjModel;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MODID, value = Dist.CLIENT)
public class DrawScreenEvent
{
	// The texture path is just a dummy texture used as a placeholder
	public static final VertexFormat chessVertexFormat = TTCRenderTypes.getChessPieceSolid(new ResourceLocation(Reference.MODID, "textures/tile/chess/pieces.png")).getVertexFormat();
	// Initializes the models
	private static final ChessObjModel CHESS_PIECE_MODEL = new ChessObjModel();
	// The model buffers, used to render the VOBs
	public static VertexBuffer pawnBuffer;
	public static VertexBuffer rookBuffer;
	public static VertexBuffer bishopBuffer;
	public static VertexBuffer knightBuffer;
	public static VertexBuffer kingBuffer;
	public static VertexBuffer queenBuffer;
	
	@SubscribeEvent
	public static void setup(final GuiScreenEvent.DrawScreenEvent event)
	{	
		if(pawnBuffer == null)
		{
			BufferBuilder pawnBuilder = new BufferBuilder(8342);
			pawnBuilder.begin(GL11.GL_TRIANGLES, chessVertexFormat);
			CHESS_PIECE_MODEL.render(new MatrixStack(), pawnBuilder, PieceType.PAWN);
			pawnBuilder.finishDrawing();
			pawnBuffer = new VertexBuffer(chessVertexFormat);
			if(pawnBuilder != null)
			{
				pawnBuffer.upload(pawnBuilder);
			}
		}
		
		if(rookBuffer == null)
		{
			BufferBuilder rookBuilder = new BufferBuilder(8342);
			rookBuilder.begin(GL11.GL_TRIANGLES, chessVertexFormat);
			CHESS_PIECE_MODEL.render(new MatrixStack(), rookBuilder, PieceType.ROOK);
			rookBuilder.finishDrawing();
			rookBuffer = new VertexBuffer(chessVertexFormat);
			if(rookBuilder != null)
			{
				rookBuffer.upload(rookBuilder);
			}
		}
		
		if(bishopBuffer == null)
		{
			BufferBuilder bishopBuilder = new BufferBuilder(8342);
			bishopBuilder.begin(GL11.GL_TRIANGLES, chessVertexFormat);
			CHESS_PIECE_MODEL.render(new MatrixStack(), bishopBuilder, PieceType.BISHOP);
			bishopBuilder.finishDrawing();
			bishopBuffer = new VertexBuffer(chessVertexFormat);
			if(bishopBuilder != null)
			{
				bishopBuffer.upload(bishopBuilder);
			}
		}
		
		if(knightBuffer == null)
		{
			BufferBuilder knightBuilder = new BufferBuilder(8342);
			knightBuilder.begin(GL11.GL_TRIANGLES, chessVertexFormat);
			CHESS_PIECE_MODEL.render(new MatrixStack(), knightBuilder, PieceType.KNIGHT);
			knightBuilder.finishDrawing();
			knightBuffer = new VertexBuffer(chessVertexFormat);
			if(knightBuilder != null)
			{
				knightBuffer.upload(knightBuilder);
			}
		}
		
		if(kingBuffer == null)
		{
			BufferBuilder kingBuilder = new BufferBuilder(8342);
			kingBuilder.begin(GL11.GL_TRIANGLES, chessVertexFormat);
			CHESS_PIECE_MODEL.render(new MatrixStack(), kingBuilder, PieceType.KING);
			kingBuilder.finishDrawing();
			kingBuffer = new VertexBuffer(chessVertexFormat);
			if(kingBuilder != null)
			{
				kingBuffer.upload(kingBuilder);
			}
		}
		
		if(queenBuffer == null)
		{
			BufferBuilder queenBuilder = new BufferBuilder(8342);
			queenBuilder.begin(GL11.GL_TRIANGLES, chessVertexFormat);
			CHESS_PIECE_MODEL.render(new MatrixStack(), queenBuilder, PieceType.QUEEN);
			queenBuilder.finishDrawing();
			queenBuffer = new VertexBuffer(chessVertexFormat);
			if(queenBuilder != null)
			{
				queenBuffer.upload(queenBuilder);
			}
		}
	}
}
