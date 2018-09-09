package moe.plushie.table_top_craft.common.games.chess.pieces;

import moe.plushie.table_top_craft.common.games.chess.ChessPieceType;
import moe.plushie.table_top_craft.common.games.chess.ChessTeam;

public class ChessPieceQueen extends ChessPiece {

    public ChessPieceQueen(ChessTeam chessTeam) {
        super(ChessPieceType.QUEEN, chessTeam);
    }

    @Override
    public boolean canMoveTo(ChessPiece[][] chessBoard, int curX, int curY, int newX, int newY) {
        // TODO Auto-generated method stub
        return false;
    }
}