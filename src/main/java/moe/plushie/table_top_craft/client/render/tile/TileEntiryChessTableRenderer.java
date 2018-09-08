package moe.plushie.table_top_craft.client.render.tile;

import moe.plushie.table_top_craft.client.model.ChessKing;
import moe.plushie.table_top_craft.client.model.ChessKnight;
import moe.plushie.table_top_craft.client.model.ChessPawn;
import moe.plushie.table_top_craft.client.model.ChessRook;
import moe.plushie.table_top_craft.common.games.chess.ChessPieceType;
import moe.plushie.table_top_craft.common.lib.ModReference;
import moe.plushie.table_top_craft.common.tileentities.TileEntityChessTable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntiryChessTableRenderer extends TileEntitySpecialRenderer<TileEntityChessTable> {

    private static final ResourceLocation PAWN_TEXTURE_BLACK = new ResourceLocation(ModReference.MOD_ID, "textures/models/black_pieces.png");
    private static final ResourceLocation PAWN_TEXTURE_WHITE = new ResourceLocation(ModReference.MOD_ID, "textures/models/white_pieces.png");
    
    private final ChessPawn pawnModel;
    private final ChessRook rookModel;
    private final ChessKnight knightModel;
    private final ChessKing kingModel;
    
    private static final float SCALE_MC = 0.0625F;
    private static final float SCALE_CHESS = 0.125F;
    
    public TileEntiryChessTableRenderer() {
        pawnModel = new ChessPawn();
        rookModel = new ChessRook();
        knightModel = new ChessKnight();
        kingModel = new ChessKing();
    }
    
    @Override
    public void render(TileEntityChessTable te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        GlStateManager.translate(x + 1F, y + 1.02F, z);
        GlStateManager.scale(1F, -1F, -1F);
        GlStateManager.enableNormalize();
        GlStateManager.translate(-SCALE_CHESS, 0F, -SCALE_CHESS);
        renderWhitePieces();
        renderBlackPieces();
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }
    
    private void renderWhitePieces() {
        bindTexture(PAWN_TEXTURE_WHITE);
        // pawn row
        for (int i = 0; i < 8; i++) {
            renderChessPiece(ChessPieceType.PAWN, i, 1, false);
        }
        renderChessPiece(ChessPieceType.ROOK, 0, 0, false);
        renderChessPiece(ChessPieceType.ROOK, 7, 0, false);
        
        renderChessPiece(ChessPieceType.KNIGHT, 1, 0, false);
        renderChessPiece(ChessPieceType.KNIGHT, 6, 0, false);
        
        renderChessPiece(ChessPieceType.BISHOP, 2, 0, false);
        renderChessPiece(ChessPieceType.BISHOP, 5, 0, false);
        
        renderChessPiece(ChessPieceType.QUEEN, 3, 0, false);
        renderChessPiece(ChessPieceType.KING, 4, 0, false);
    }
    
    private void renderBlackPieces() {
        bindTexture(PAWN_TEXTURE_BLACK);
        // pawn row
        for (int i = 0; i < 8; i++) {
            renderChessPiece(ChessPieceType.PAWN, i, 6, true);
        }
        renderChessPiece(ChessPieceType.ROOK, 0, 7, true);
        renderChessPiece(ChessPieceType.ROOK, 7, 7, true);
        
        renderChessPiece(ChessPieceType.KNIGHT, 1, 7, true);
        renderChessPiece(ChessPieceType.KNIGHT, 6, 7, true);
        
        renderChessPiece(ChessPieceType.BISHOP, 2, 7, true);
        renderChessPiece(ChessPieceType.BISHOP, 5, 7, true);
        
        renderChessPiece(ChessPieceType.QUEEN, 3, 7, true);
        renderChessPiece(ChessPieceType.KING, 4, 7, true);
    }
    
    private void renderChessPiece(ChessPieceType pieceType, int x, int y, boolean flipped) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(-SCALE_CHESS * x, 0, -SCALE_CHESS * y);
        if (flipped) {
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(-SCALE_CHESS, 0, -SCALE_CHESS);
        }
        switch (pieceType) {
        case KING:
            kingModel.render(null, 0, 0, 0, 0, 0, SCALE_MC);
            break;
        case QUEEN:
            //queenModel.render(null, 0, 0, 0, 0, 0, SCALE_MC);
            break;
        case KNIGHT:
            knightModel.render(null, 0, 0, 0, 0, 0, SCALE_MC);
            break;
        case BISHOP:
            //bishopModel.render(null, 0, 0, 0, 0, 0, SCALE_MC);
            break;
        case ROOK:
            rookModel.render(null, 0, 0, 0, 0, 0, SCALE_MC);
            break;
        case PAWN:
            pawnModel.render(null, 0, 0, 0, 0, 0, SCALE_MC);
            break;
        }
        GlStateManager.popMatrix();
    }
}
