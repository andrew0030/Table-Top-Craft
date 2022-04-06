package andrews.table_top_craft.events;

import andrews.table_top_craft.game_logic.chess.pieces.BasePiece.PieceModelSet;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece.PieceType;
import andrews.table_top_craft.util.Reference;
import andrews.table_top_craft.util.TTCRenderTypes;
import andrews.table_top_craft.util.obj.models.ChessObjModel;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MODID, value = Dist.CLIENT)
public class DrawScreenEvent
{
	// The texture path is just a dummy texture used as a placeholder
	public static final VertexFormat chessVertexFormat = TTCRenderTypes.getChessPieceSolid(new ResourceLocation(Reference.MODID, "textures/tile/chess/pieces.png")).format();
	// Initializes the models
	public static final ChessObjModel CHESS_PIECE_MODEL = new ChessObjModel();
	// The model buffers, used to render the VOBs
	public static VertexBuffer pawnBuffer;
	public static VertexBuffer rookBuffer;
	public static VertexBuffer bishopBuffer;
	public static VertexBuffer knightBuffer;
	public static VertexBuffer kingBuffer;
	public static VertexBuffer queenBuffer; //TODO use HashMap for this instead

	public static VertexBuffer classicPawnBuffer;
	public static VertexBuffer classicRookBuffer;
	public static VertexBuffer classicBishopBuffer;
	public static VertexBuffer classicKnightBuffer;
	public static VertexBuffer classicKingBuffer;
	public static VertexBuffer classicQueenBuffer;
	
	@SubscribeEvent
	public static void setup(final ScreenEvent.DrawScreenEvent event)
	{
		BufferBuilder chessBuilder = new BufferBuilder(RenderType.TRANSIENT_BUFFER_SIZE);

		if(pawnBuffer == null)
		{
			chessBuilder.begin(VertexFormat.Mode.TRIANGLES, chessVertexFormat);
			CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.PAWN, PieceModelSet.STANDARD);
			chessBuilder.end(); // no longer adding new vertexes to the buffer
			pawnBuffer = new VertexBuffer();
			pawnBuffer.upload(chessBuilder); // uploads the model to the GPU
			chessBuilder.clear(); // frees up unneeded memory
		}
		
		if(rookBuffer == null)
		{
			chessBuilder.begin(VertexFormat.Mode.TRIANGLES, chessVertexFormat);
			CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.ROOK, PieceModelSet.STANDARD);
			chessBuilder.end();
			rookBuffer = new VertexBuffer();
			rookBuffer.upload(chessBuilder);
			chessBuilder.clear();
		}
		
		if(bishopBuffer == null)
		{
			chessBuilder.begin(VertexFormat.Mode.TRIANGLES, chessVertexFormat);
			CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.BISHOP, PieceModelSet.STANDARD);
			chessBuilder.end();
			bishopBuffer = new VertexBuffer();
			bishopBuffer.upload(chessBuilder);
			chessBuilder.clear();
		}
		
		if(knightBuffer == null)
		{
			chessBuilder.begin(VertexFormat.Mode.TRIANGLES, chessVertexFormat);
			CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.KNIGHT, PieceModelSet.STANDARD);
			chessBuilder.end();
			knightBuffer = new VertexBuffer();
			knightBuffer.upload(chessBuilder);
			chessBuilder.clear();
		}
		
		if(kingBuffer == null)
		{
			chessBuilder.begin(VertexFormat.Mode.TRIANGLES, chessVertexFormat);
			CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.KING, PieceModelSet.STANDARD);
			chessBuilder.end();
			kingBuffer = new VertexBuffer();
			kingBuffer.upload(chessBuilder);
			chessBuilder.clear();
		}
		
		if(queenBuffer == null)
		{
			chessBuilder.begin(VertexFormat.Mode.TRIANGLES, chessVertexFormat);
			CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.QUEEN, PieceModelSet.STANDARD);
			chessBuilder.end();
			queenBuffer = new VertexBuffer();
			queenBuffer.upload(chessBuilder);
			chessBuilder.clear();
		}

		if(classicPawnBuffer == null)
		{
			chessBuilder.begin(VertexFormat.Mode.TRIANGLES, chessVertexFormat);
			CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.PAWN, PieceModelSet.CLASSIC);
			chessBuilder.end(); // no longer adding new vertexes to the buffer
			classicPawnBuffer = new VertexBuffer();
			classicPawnBuffer.upload(chessBuilder); // uploads the model to the GPU
			chessBuilder.clear(); // frees up unneeded memory
		}

		if(classicRookBuffer == null)
		{
			chessBuilder.begin(VertexFormat.Mode.TRIANGLES, chessVertexFormat);
			CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.ROOK, PieceModelSet.CLASSIC);
			chessBuilder.end();
			classicRookBuffer = new VertexBuffer();
			classicRookBuffer.upload(chessBuilder);
			chessBuilder.clear();
		}

		if(classicBishopBuffer == null)
		{
			chessBuilder.begin(VertexFormat.Mode.TRIANGLES, chessVertexFormat);
			CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.BISHOP, PieceModelSet.CLASSIC);
			chessBuilder.end();
			classicBishopBuffer = new VertexBuffer();
			classicBishopBuffer.upload(chessBuilder);
			chessBuilder.clear();
		}

		if(classicKnightBuffer == null)
		{
			chessBuilder.begin(VertexFormat.Mode.TRIANGLES, chessVertexFormat);
			CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.KNIGHT, PieceModelSet.CLASSIC);
			chessBuilder.end();
			classicKnightBuffer = new VertexBuffer();
			classicKnightBuffer.upload(chessBuilder);
			chessBuilder.clear();
		}

		if(classicKingBuffer == null)
		{
			chessBuilder.begin(VertexFormat.Mode.TRIANGLES, chessVertexFormat);
			CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.KING, PieceModelSet.CLASSIC);
			chessBuilder.end();
			classicKingBuffer = new VertexBuffer();
			classicKingBuffer.upload(chessBuilder);
			chessBuilder.clear();
		}

		if(classicQueenBuffer == null)
		{
			chessBuilder.begin(VertexFormat.Mode.TRIANGLES, chessVertexFormat);
			CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.QUEEN, PieceModelSet.CLASSIC);
			chessBuilder.end();
			classicQueenBuffer = new VertexBuffer();
			classicQueenBuffer.upload(chessBuilder);
			chessBuilder.clear();
		}
	}
}