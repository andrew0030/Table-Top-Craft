package moe.plushie.table_top_craft.common.games.chess.pieces;

import moe.plushie.table_top_craft.common.games.chess.ChessPieceType;
import moe.plushie.table_top_craft.common.games.chess.ChessTeam;

public class ChessPiecePawn extends ChessPiece {
    
    public ChessPiecePawn(ChessTeam chessTeam) {
        super(ChessPieceType.PAWN, chessTeam);
    }
    
    @Override
    public boolean canMoveTo(ChessPiece[][] chessBoard, int curX, int curY, int newX, int newY) {
        if (getMoves() == 0) {
            
        }
        return false;
    }
    
    @Override
    public boolean canTake(ChessPiece[][] chessBoard, int curX, int curY, int newX, int newY) {
        // TODO Auto-generated method stub
        return false;
    }
}
