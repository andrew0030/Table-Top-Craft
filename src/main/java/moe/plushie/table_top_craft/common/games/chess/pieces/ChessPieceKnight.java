package moe.plushie.table_top_craft.common.games.chess.pieces;

import moe.plushie.table_top_craft.common.games.chess.ChessPieceType;
import moe.plushie.table_top_craft.common.games.chess.ChessTeam;

public class ChessPieceKnight extends ChessPiece {

    public ChessPieceKnight(ChessTeam chessTeam) {
        super(ChessPieceType.KNIGHT, chessTeam);
    }

    @Override
    public boolean canMoveTo(ChessPiece[][] chessBoard, int curX, int curY, int newX, int newY) {
        // TODO Auto-generated method stub
        return false;
    }
}
