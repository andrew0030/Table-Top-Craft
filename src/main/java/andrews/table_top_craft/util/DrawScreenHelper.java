package andrews.table_top_craft.util;

import andrews.table_top_craft.game_logic.chess.pieces.BasePiece.PieceModelSet;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece.PieceType;
import andrews.table_top_craft.util.obj.models.ChessObjModel;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;

public class DrawScreenHelper
{
    // The texture path is just a dummy texture used as a placeholder
    public static final VertexFormat chessVertexFormat = TTCRenderTypes.getChessPieceSolid(new ResourceLocation(Reference.MODID, "textures/tile/chess/pieces.png")).format();
    // Initializes the models
    public static final ChessObjModel CHESS_PIECE_MODEL = new ChessObjModel();
    // The model buffers, used to render the VOBs
    public static final HashMap<Pair<PieceType, PieceModelSet>, VertexBuffer> BUFFERS = new HashMap<>();

    public static void setup()
    {
        BufferBuilder chessBuilder = new BufferBuilder(RenderType.TRANSIENT_BUFFER_SIZE);

        for (PieceType type : PieceType.values())
        {
            for (PieceModelSet set : PieceModelSet.values())
            {
                BUFFERS.put(Pair.of(type, set), generate(chessBuilder, chessVertexFormat, type, set));
            }
        }
    }

    private static VertexBuffer generate(BufferBuilder builder, VertexFormat format, PieceType type, PieceModelSet set)
    {
        builder.begin(VertexFormat.Mode.TRIANGLES, format);
        CHESS_PIECE_MODEL.render(new PoseStack(), builder, type, set);
        VertexBuffer buffer = BUFFERS.getOrDefault(Pair.of(type, set), null);
        if (buffer == null) buffer = new VertexBuffer();
        upload(buffer, builder);
        return buffer;
    }

    private static void upload(VertexBuffer buffer,BufferBuilder builder)
    {
        buffer.bind();
        buffer.upload(builder.end());
        VertexBuffer.unbind();
        builder.clear(); // frees up unneeded memory
    }

    public static VertexBuffer getBuffer(PieceModelSet set, PieceType piece)
    {
        return BUFFERS.get(Pair.of(piece, set));
    }
}