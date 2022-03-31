package andrews.table_top_craft.events;

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
	public static VertexBuffer queenBuffer;
	
	@SubscribeEvent
	public static void setup(final ScreenEvent.DrawScreenEvent event)
	{	
		if(pawnBuffer == null)
		{
			BufferBuilder pawnBuilder = new BufferBuilder(RenderType.TRANSIENT_BUFFER_SIZE);
			pawnBuilder.begin(VertexFormat.Mode.TRIANGLES, chessVertexFormat);
			CHESS_PIECE_MODEL.render(new PoseStack(), pawnBuilder, PieceType.PAWN);
			pawnBuilder.end(); // no longer adding new vertexes to the buffer
			pawnBuffer = new VertexBuffer();
			pawnBuffer.upload(pawnBuilder); // uploads the model the to GPU
			pawnBuilder.clear(); // frees up unneeded memory
		}
		
		if(rookBuffer == null)
		{
			BufferBuilder rookBuilder = new BufferBuilder(8342);
			rookBuilder.begin(VertexFormat.Mode.TRIANGLES, chessVertexFormat);
			CHESS_PIECE_MODEL.render(new PoseStack(), rookBuilder, PieceType.ROOK);
			rookBuilder.end();
			rookBuffer = new VertexBuffer();
			if(rookBuilder != null)
			{
				rookBuffer.upload(rookBuilder);
			}
		}
		
		if(bishopBuffer == null)
		{
			BufferBuilder bishopBuilder = new BufferBuilder(8342);
			bishopBuilder.begin(VertexFormat.Mode.TRIANGLES, chessVertexFormat);
			CHESS_PIECE_MODEL.render(new PoseStack(), bishopBuilder, PieceType.BISHOP);
			bishopBuilder.end();
			bishopBuffer = new VertexBuffer();
			if(bishopBuilder != null)
			{
				bishopBuffer.upload(bishopBuilder);
			}
		}
		
		if(knightBuffer == null)
		{
			BufferBuilder knightBuilder = new BufferBuilder(8342);
			knightBuilder.begin(VertexFormat.Mode.TRIANGLES, chessVertexFormat);
			CHESS_PIECE_MODEL.render(new PoseStack(), knightBuilder, PieceType.KNIGHT);
			knightBuilder.end();
			knightBuffer = new VertexBuffer();
			if(knightBuilder != null)
			{
				knightBuffer.upload(knightBuilder);
			}
		}
		
		if(kingBuffer == null)
		{
			BufferBuilder kingBuilder = new BufferBuilder(8342);
			kingBuilder.begin(VertexFormat.Mode.TRIANGLES, chessVertexFormat);
			CHESS_PIECE_MODEL.render(new PoseStack(), kingBuilder, PieceType.KING);
			kingBuilder.end();
			kingBuffer = new VertexBuffer();
			if(kingBuilder != null)
			{
				kingBuffer.upload(kingBuilder);
			}
		}
		
		if(queenBuffer == null)
		{
			BufferBuilder queenBuilder = new BufferBuilder(8342);
			queenBuilder.begin(VertexFormat.Mode.TRIANGLES, chessVertexFormat);
			CHESS_PIECE_MODEL.render(new PoseStack(), queenBuilder, PieceType.QUEEN);
			queenBuilder.end();
			queenBuffer = new VertexBuffer();
			if(queenBuilder != null)
			{
				queenBuffer.upload(queenBuilder);
			}
		}
	}
}