package moe.plushie.table_top_craft.common.games.chess.pieces;

import moe.plushie.table_top_craft.common.games.chess.ChessPieceType;
import moe.plushie.table_top_craft.common.games.chess.ChessTeam;

public class ChessPieceKing extends ChessPiece {

    public ChessPieceKing(ChessTeam chessTeam) {
        super(ChessPieceType.KING, chessTeam);
    }
    
    @Override
    public boolean canMoveTo(ChessPiece[][] chessBoard, int curX, int curY, int newX, int newY) {
        // TODO Auto-generated method stub
        return false;
    }
}
