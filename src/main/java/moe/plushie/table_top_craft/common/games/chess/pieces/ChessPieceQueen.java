package moe.plushie.table_top_craft.common.games.chess.pieces;

import moe.plushie.table_top_craft.common.games.chess.ChessPieceType;
import moe.plushie.table_top_craft.common.games.chess.ChessTeam;

public class ChessPieceQueen extends ChessPiece {

    public ChessPieceQueen(ChessTeam chessTeam) {
        super(ChessPieceType.QUEEN, chessTeam);
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
