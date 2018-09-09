package moe.plushie.table_top_craft.common.games.chess.pieces;

import moe.plushie.table_top_craft.common.games.chess.ChessGame;
import moe.plushie.table_top_craft.common.games.chess.ChessPieceType;
import moe.plushie.table_top_craft.common.games.chess.ChessTeam;

public abstract class ChessPiece {
    
    public static final ChessPiece WHITE_KING = new ChessPieceKing(ChessTeam.WHITE);
    public static final ChessPiece WHITE_QUEEN = new ChessPieceQueen(ChessTeam.WHITE);
    public static final ChessPiece WHITE_BISHOP = new ChessPieceBishop(ChessTeam.WHITE);
    public static final ChessPiece WHITE_KNIGHT = new ChessPieceKnight(ChessTeam.WHITE);
    public static final ChessPiece WHITE_ROOK = new ChessPieceRook(ChessTeam.WHITE);
    public static final ChessPiece WHITE_PAWN = new ChessPiecePawn(ChessTeam.WHITE);
    
    public static final ChessPiece BLACK_KING = new ChessPieceKing(ChessTeam.BLACK);
    public static final ChessPiece BLACK_QUEEN = new ChessPieceQueen(ChessTeam.BLACK);
    public static final ChessPiece BLACK_BISHOP = new ChessPieceBishop(ChessTeam.BLACK);
    public static final ChessPiece BLACK_KNIGHT = new ChessPieceKnight(ChessTeam.BLACK);
    public static final ChessPiece BLACK_ROOK = new ChessPieceRook(ChessTeam.BLACK);
    public static final ChessPiece BLACK_PAWN = new ChessPiecePawn(ChessTeam.BLACK);
    
    private final ChessPieceType pieceType;
    private final ChessTeam chessTeam;
    private int moves;
    
    public ChessPiece(ChessPieceType pieceType, ChessTeam chessTeam) {
        this.pieceType = pieceType;
        this.chessTeam = chessTeam;
        this.moves = 0;
    }
    
    public abstract boolean canMoveTo(ChessPiece[][] chessBoard, int curX, int curY, int newX, int newY);
    
    public boolean canTake(ChessPiece[][] chessBoard, int curX, int curY, int newX, int newY) {
        return canMoveTo(chessBoard, curX, curY, newX, newY);
    }
    
    public void moveTo(ChessPiece[][] chessBoard, int curX, int curY, int newX, int newY) {
        if (canMoveTo(chessBoard, curX, curY, newX, newY)) {
            chessBoard[curX][curY] = null;
            chessBoard[newX][newY] = this;
            moves++;
        }
    }
    
    protected boolean isOnBoard(int x, int y) {
        return x >= 0 & y >=0 & x < ChessGame.BOARD_SIZE & y < ChessGame.BOARD_SIZE;
    }
    
    public int getMoves() {
        return moves;
    }
    
    public ChessTeam getTeam() {
        return chessTeam;
    }
    
    public ChessPieceType getRenderPieceType() {
        return pieceType;
    }
}
