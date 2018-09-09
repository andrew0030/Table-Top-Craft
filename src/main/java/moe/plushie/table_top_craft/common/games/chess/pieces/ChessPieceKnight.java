package moe.plushie.table_top_craft.common.games.chess.pieces;

import moe.plushie.table_top_craft.common.games.chess.ChessPieceType;
import moe.plushie.table_top_craft.common.games.chess.ChessTeam;

public class ChessPieceKnight extends ChessPiece {

    public ChessPieceKnight(ChessTeam chessTeam) {
        super(ChessPieceType.KNIGHT, chessTeam);
    }

    @Override
    public boolean canMoveTo(ChessPiece[][] chessBoard, int xPos, int yPos) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean canTake(ChessPiece[][] chessBoard, int xPos, int yPos) {
        // TODO Auto-generated method stub
        return false;
    }
}
