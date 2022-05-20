package andrews.table_top_craft.util;

import andrews.table_top_craft.util.obj.models.ChessObjModel;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.resources.ResourceLocation;

public class TTCVBOs
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

    public static VertexBuffer classicPawnBuffer;
    public static VertexBuffer classicRookBuffer;
    public static VertexBuffer classicBishopBuffer;
    public static VertexBuffer classicKnightBuffer;
    public static VertexBuffer classicKingBuffer;
    public static VertexBuffer classicQueenBuffer;

    public static VertexBuffer pandorasCreaturesPawnBuffer;
    public static VertexBuffer pandorasCreaturesRookBuffer;
    public static VertexBuffer pandorasCreaturesBishopBuffer;
    public static VertexBuffer pandorasCreaturesKnightBuffer;
    public static VertexBuffer pandorasCreaturesKingBuffer;
    public static VertexBuffer pandorasCreaturesQueenBuffer;
}
