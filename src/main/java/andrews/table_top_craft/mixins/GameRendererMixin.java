package andrews.table_top_craft.mixins;

import andrews.table_top_craft.game_logic.chess.pieces.BasePiece.PieceModelSet;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece.PieceType;
import andrews.table_top_craft.util.Reference;
import andrews.table_top_craft.util.TTCClientInit;
import andrews.table_top_craft.util.TTCVBOs;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(GameRenderer.class)
public class GameRendererMixin
{
    @Inject(at = @At(value = "TAIL"), method = "reloadShaders")
    public void reloadShaders(ResourceManager resourceManager, CallbackInfo ci)
    {
        if(TTCClientInit.rendertypeSolidBlockEntityShader != null)
            TTCClientInit.rendertypeSolidBlockEntityShader.close();
        try
        {
            TTCClientInit.rendertypeSolidBlockEntityShader = new ShaderInstance(resourceManager, Reference.MODID + ":rendertype_solid", DefaultVertexFormat.BLOCK);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Inject(at = @At(value = "TAIL"), method = "onResourceManagerReload")
    public void onResourceManagerReload(ResourceManager resourceManager, CallbackInfo ci)
    {
        BufferBuilder chessBuilder = new BufferBuilder(RenderType.TRANSIENT_BUFFER_SIZE);

        if(TTCVBOs.pawnBuffer == null)
        {
            chessBuilder.begin(VertexFormat.Mode.TRIANGLES, TTCVBOs.chessVertexFormat);
            TTCVBOs.CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.PAWN, PieceModelSet.STANDARD);
            chessBuilder.end(); // no longer adding new vertexes to the buffer
            TTCVBOs.pawnBuffer = new VertexBuffer();
            TTCVBOs.pawnBuffer.upload(chessBuilder); // uploads the model to the GPU
            chessBuilder.clear(); // frees up unneeded memory
        }

        if(TTCVBOs.rookBuffer == null)
        {
            chessBuilder.begin(VertexFormat.Mode.TRIANGLES, TTCVBOs.chessVertexFormat);
            TTCVBOs.CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.ROOK, PieceModelSet.STANDARD);
            chessBuilder.end();
            TTCVBOs.rookBuffer = new VertexBuffer();
            TTCVBOs.rookBuffer.upload(chessBuilder);
            chessBuilder.clear();
        }

        if(TTCVBOs.bishopBuffer == null)
        {
            chessBuilder.begin(VertexFormat.Mode.TRIANGLES, TTCVBOs.chessVertexFormat);
            TTCVBOs.CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.BISHOP, PieceModelSet.STANDARD);
            chessBuilder.end();
            TTCVBOs.bishopBuffer = new VertexBuffer();
            TTCVBOs.bishopBuffer.upload(chessBuilder);
            chessBuilder.clear();
        }

        if(TTCVBOs.knightBuffer == null)
        {
            chessBuilder.begin(VertexFormat.Mode.TRIANGLES, TTCVBOs.chessVertexFormat);
            TTCVBOs.CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.KNIGHT, PieceModelSet.STANDARD);
            chessBuilder.end();
            TTCVBOs.knightBuffer = new VertexBuffer();
            TTCVBOs.knightBuffer.upload(chessBuilder);
            chessBuilder.clear();
        }

        if(TTCVBOs.kingBuffer == null)
        {
            chessBuilder.begin(VertexFormat.Mode.TRIANGLES, TTCVBOs.chessVertexFormat);
            TTCVBOs.CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.KING, PieceModelSet.STANDARD);
            chessBuilder.end();
            TTCVBOs.kingBuffer = new VertexBuffer();
            TTCVBOs.kingBuffer.upload(chessBuilder);
            chessBuilder.clear();
        }

        if(TTCVBOs.queenBuffer == null)
        {
            chessBuilder.begin(VertexFormat.Mode.TRIANGLES, TTCVBOs.chessVertexFormat);
            TTCVBOs.CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.QUEEN, PieceModelSet.STANDARD);
            chessBuilder.end();
            TTCVBOs.queenBuffer = new VertexBuffer();
            TTCVBOs.queenBuffer.upload(chessBuilder);
            chessBuilder.clear();
        }

        if(TTCVBOs.classicPawnBuffer == null)
        {
            chessBuilder.begin(VertexFormat.Mode.TRIANGLES, TTCVBOs.chessVertexFormat);
            TTCVBOs.CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.PAWN, PieceModelSet.CLASSIC);
            chessBuilder.end(); // no longer adding new vertexes to the buffer
            TTCVBOs.classicPawnBuffer = new VertexBuffer();
            TTCVBOs.classicPawnBuffer.upload(chessBuilder); // uploads the model to the GPU
            chessBuilder.clear(); // frees up unneeded memory
        }

        if(TTCVBOs.classicRookBuffer == null)
        {
            chessBuilder.begin(VertexFormat.Mode.TRIANGLES, TTCVBOs.chessVertexFormat);
            TTCVBOs.CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.ROOK, PieceModelSet.CLASSIC);
            chessBuilder.end();
            TTCVBOs.classicRookBuffer = new VertexBuffer();
            TTCVBOs.classicRookBuffer.upload(chessBuilder);
            chessBuilder.clear();
        }

        if(TTCVBOs.classicBishopBuffer == null)
        {
            chessBuilder.begin(VertexFormat.Mode.TRIANGLES, TTCVBOs.chessVertexFormat);
            TTCVBOs.CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.BISHOP, PieceModelSet.CLASSIC);
            chessBuilder.end();
            TTCVBOs.classicBishopBuffer = new VertexBuffer();
            TTCVBOs.classicBishopBuffer.upload(chessBuilder);
            chessBuilder.clear();
        }

        if(TTCVBOs.classicKnightBuffer == null)
        {
            chessBuilder.begin(VertexFormat.Mode.TRIANGLES, TTCVBOs.chessVertexFormat);
            TTCVBOs.CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.KNIGHT, PieceModelSet.CLASSIC);
            chessBuilder.end();
            TTCVBOs.classicKnightBuffer = new VertexBuffer();
            TTCVBOs.classicKnightBuffer.upload(chessBuilder);
            chessBuilder.clear();
        }

        if(TTCVBOs.classicKingBuffer == null)
        {
            chessBuilder.begin(VertexFormat.Mode.TRIANGLES, TTCVBOs.chessVertexFormat);
            TTCVBOs.CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.KING, PieceModelSet.CLASSIC);
            chessBuilder.end();
            TTCVBOs.classicKingBuffer = new VertexBuffer();
            TTCVBOs.classicKingBuffer.upload(chessBuilder);
            chessBuilder.clear();
        }

        if(TTCVBOs.classicQueenBuffer == null)
        {
            chessBuilder.begin(VertexFormat.Mode.TRIANGLES, TTCVBOs.chessVertexFormat);
            TTCVBOs.CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.QUEEN, PieceModelSet.CLASSIC);
            chessBuilder.end();
            TTCVBOs.classicQueenBuffer = new VertexBuffer();
            TTCVBOs.classicQueenBuffer.upload(chessBuilder);
            chessBuilder.clear();
        }

        if(TTCVBOs.pandorasCreaturesPawnBuffer == null)
        {
            chessBuilder.begin(VertexFormat.Mode.TRIANGLES, TTCVBOs.chessVertexFormat);
            TTCVBOs.CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.PAWN, PieceModelSet.PANDORAS_CREATURES);
            chessBuilder.end(); // no longer adding new vertexes to the buffer
            TTCVBOs.pandorasCreaturesPawnBuffer = new VertexBuffer();
            TTCVBOs.pandorasCreaturesPawnBuffer.upload(chessBuilder); // uploads the model to the GPU
            chessBuilder.clear(); // frees up unneeded memory
        }

        if(TTCVBOs.pandorasCreaturesRookBuffer == null)
        {
            chessBuilder.begin(VertexFormat.Mode.TRIANGLES, TTCVBOs.chessVertexFormat);
            TTCVBOs.CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.ROOK, PieceModelSet.PANDORAS_CREATURES);
            chessBuilder.end();
            TTCVBOs.pandorasCreaturesRookBuffer = new VertexBuffer();
            TTCVBOs.pandorasCreaturesRookBuffer.upload(chessBuilder);
            chessBuilder.clear();
        }

        if(TTCVBOs.pandorasCreaturesBishopBuffer == null)
        {
            chessBuilder.begin(VertexFormat.Mode.TRIANGLES, TTCVBOs.chessVertexFormat);
            TTCVBOs.CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.BISHOP, PieceModelSet.PANDORAS_CREATURES);
            chessBuilder.end();
            TTCVBOs.pandorasCreaturesBishopBuffer = new VertexBuffer();
            TTCVBOs.pandorasCreaturesBishopBuffer.upload(chessBuilder);
            chessBuilder.clear();
        }

        if(TTCVBOs.pandorasCreaturesKnightBuffer == null)
        {
            chessBuilder.begin(VertexFormat.Mode.TRIANGLES, TTCVBOs.chessVertexFormat);
            TTCVBOs.CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.KNIGHT, PieceModelSet.PANDORAS_CREATURES);
            chessBuilder.end();
            TTCVBOs.pandorasCreaturesKnightBuffer = new VertexBuffer();
            TTCVBOs.pandorasCreaturesKnightBuffer.upload(chessBuilder);
            chessBuilder.clear();
        }

        if(TTCVBOs.pandorasCreaturesKingBuffer == null)
        {
            chessBuilder.begin(VertexFormat.Mode.TRIANGLES, TTCVBOs.chessVertexFormat);
            TTCVBOs.CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.KING, PieceModelSet.PANDORAS_CREATURES);
            chessBuilder.end();
            TTCVBOs.pandorasCreaturesKingBuffer = new VertexBuffer();
            TTCVBOs.pandorasCreaturesKingBuffer.upload(chessBuilder);
            chessBuilder.clear();
        }

        if(TTCVBOs.pandorasCreaturesQueenBuffer == null)
        {
            chessBuilder.begin(VertexFormat.Mode.TRIANGLES, TTCVBOs.chessVertexFormat);
            TTCVBOs.CHESS_PIECE_MODEL.render(new PoseStack(), chessBuilder, PieceType.QUEEN, PieceModelSet.PANDORAS_CREATURES);
            chessBuilder.end();
            TTCVBOs.pandorasCreaturesQueenBuffer = new VertexBuffer();
            TTCVBOs.pandorasCreaturesQueenBuffer.upload(chessBuilder);
            chessBuilder.clear();
        }
    }
}