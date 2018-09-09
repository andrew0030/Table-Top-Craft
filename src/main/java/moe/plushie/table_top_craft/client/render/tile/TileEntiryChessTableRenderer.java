package moe.plushie.table_top_craft.client.render.tile;

import moe.plushie.table_top_craft.client.model.ChessKing;
import moe.plushie.table_top_craft.client.model.ChessKnight;
import moe.plushie.table_top_craft.client.model.ChessPawn;
import moe.plushie.table_top_craft.client.model.ChessRook;
import moe.plushie.table_top_craft.common.blocks.BlockChessTable;
import moe.plushie.table_top_craft.common.games.chess.ChessGame;
import moe.plushie.table_top_craft.common.games.chess.ChessPieceType;
import moe.plushie.table_top_craft.common.games.chess.ChessTeam;
import moe.plushie.table_top_craft.common.games.chess.pieces.ChessPiece;
import moe.plushie.table_top_craft.common.lib.ModReference;
import moe.plushie.table_top_craft.common.tileentities.TileEntityChessTable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
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
        IBlockState blockState = te.getWorld().getBlockState(te.getPos());
        EnumFacing facing = blockState.getValue(BlockChessTable.STATE_FACING);
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        GlStateManager.enableNormalize();
        GlStateManager.enableRescaleNormal();
        GlStateManager.translate(x + 0.5F, y + 1.02F, z + 0.5F);
        switch (facing) {
        case EAST:
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            break;
        case SOUTH:
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            break;
        case NORTH:
            GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
            break;
        default:
            break;
        }
        GlStateManager.translate(0.5F, 0, -0.5F);
        GlStateManager.scale(1F, -1F, -1F);
        GlStateManager.translate(-SCALE_CHESS, 0F, -SCALE_CHESS);
        ChessGame chessGame = te.getChessGame();
        renderWhitePieces(chessGame);
        renderBlackPieces(chessGame);
        GlStateManager.disableNormalize();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }
    
    private void renderWhitePieces(ChessGame chessGame) {
        bindTexture(PAWN_TEXTURE_WHITE);
        for (int ix = 0; ix < ChessGame.BOARD_SIZE; ix++) {
            for (int iy = 0; iy < ChessGame.BOARD_SIZE; iy++) {
                ChessPiece chessPiece = chessGame.getChessBoard()[ix][iy];
                if (chessPiece != null && chessPiece.getTeam() == ChessTeam.WHITE) {
                    renderChessPiece(chessPiece.getRenderPieceType(), ix, iy, chessPiece.getTeam() == ChessTeam.BLACK);
                }
            }
        }
    }
    
    private void renderBlackPieces(ChessGame chessGame) {
        bindTexture(PAWN_TEXTURE_BLACK);
        for (int ix = 0; ix < ChessGame.BOARD_SIZE; ix++) {
            for (int iy = 0; iy < ChessGame.BOARD_SIZE; iy++) {
                ChessPiece chessPiece = chessGame.getChessBoard()[ix][iy];
                if (chessPiece != null && chessPiece.getTeam() == ChessTeam.BLACK) {
                    renderChessPiece(chessPiece.getRenderPieceType(), ix, iy, chessPiece.getTeam() == ChessTeam.BLACK);
                }
            }
        }
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
