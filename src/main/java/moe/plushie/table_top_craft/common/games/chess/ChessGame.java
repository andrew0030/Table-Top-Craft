package moe.plushie.table_top_craft.common.games.chess;

import moe.plushie.table_top_craft.common.games.chess.pieces.ChessPiece;

public class ChessGame {
    
    public final static int BOARD_SIZE = 8;
    private ChessPiece[][] chessBoard;
    private ChessTeam teamTurn;
    
    public ChessGame() {
        chessBoard = new ChessPiece[BOARD_SIZE][BOARD_SIZE];
        resetGame();
    }
    
    public void resetGame() {
        clearBoard();
        setupWhitePieces();
        setupBlackPieces();
        teamTurn = ChessTeam.WHITE;
    }
    
    private void setupWhitePieces() {
        chessBoard[0][0] = ChessPiece.WHITE_ROOK;
        chessBoard[7][0] = ChessPiece.WHITE_ROOK;
        
        chessBoard[1][0] = ChessPiece.WHITE_KNIGHT;
        chessBoard[6][0] = ChessPiece.WHITE_KNIGHT;
        
        chessBoard[2][0] = ChessPiece.WHITE_BISHOP;
        chessBoard[5][0] = ChessPiece.WHITE_BISHOP;
        
        chessBoard[3][0] = ChessPiece.WHITE_KING;
        chessBoard[4][0] = ChessPiece.WHITE_QUEEN;
        
        for (int ix = 0; ix < BOARD_SIZE; ix++) {
            chessBoard[ix][1] = ChessPiece.WHITE_PAWN;
        }
    }
    
    private void setupBlackPieces() {
        chessBoard[0][7] = ChessPiece.BLACK_ROOK;
        chessBoard[7][7] = ChessPiece.BLACK_ROOK;
        
        chessBoard[1][7] = ChessPiece.BLACK_KNIGHT;
        chessBoard[6][7] = ChessPiece.BLACK_KNIGHT;
        
        chessBoard[2][7] = ChessPiece.BLACK_BISHOP;
        chessBoard[5][7] = ChessPiece.BLACK_BISHOP;
        
        chessBoard[3][7] = ChessPiece.BLACK_KING;
        chessBoard[4][7] = ChessPiece.BLACK_QUEEN;
        
        for (int ix = 0; ix < BOARD_SIZE; ix++) {
            chessBoard[ix][6] = ChessPiece.BLACK_PAWN;
        }
    }
    
    private void clearBoard() {
        for (int ix = 0; ix < BOARD_SIZE; ix++) {
            for (int iy = 0; iy < BOARD_SIZE; iy++) {
                chessBoard[ix][iy] = null;
            }
        }
    }
    
    public ChessPiece[][] getChessBoard() {
        return chessBoard;
    }
}
