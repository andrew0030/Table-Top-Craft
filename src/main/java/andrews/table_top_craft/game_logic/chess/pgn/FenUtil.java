package andrews.table_top_craft.game_logic.chess.pgn;

import andrews.table_top_craft.game_logic.chess.PieceColor;
import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.Board.Builder;
import andrews.table_top_craft.game_logic.chess.board.BoardUtils;
import andrews.table_top_craft.game_logic.chess.pieces.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class FenUtil
{
    private FenUtil() {}

    public static Board createGameFromFEN(final String fenString, final String firstMovesList, boolean isWhiteCastled, boolean isBlackCastled)
    {
        return parseFEN(fenString, firstMovesList, isWhiteCastled, isBlackCastled);
    }
    
    public static Board createGameFromFEN(final String fenString)
    {
        return parseFEN(fenString);
    }

    public static String createFENFromGame(final Board board)
    {
        return calculateBoardText(board) + " " +
               calculateCurrentPlayerText(board) + " " +
               calculateCastleText(board) + " " +
               calculateEnPassantSquare(board) + " " +
               "0 0";
    }

    /**
     * @param fenString - The FEN String
     * @return - A Chess Board based on the given FEN String
     */
    private static Board parseFEN(final String fenString, final String firstMovesList, boolean isWhiteCastled, boolean isBlackCastled)
    {
        final String[] fenPartitions = fenString.trim().split(" ");
        final String[] firstMoveTiles = firstMovesList.trim().split("/");
        final Builder builder = new Builder();
        final boolean whiteKingSideCastle = whiteKingSideCastle(fenPartitions[2]);
        final boolean whiteQueenSideCastle = whiteQueenSideCastle(fenPartitions[2]);
        final boolean blackKingSideCastle = blackKingSideCastle(fenPartitions[2]);
        final boolean blackQueenSideCastle = blackQueenSideCastle(fenPartitions[2]);
        final String gameConfiguration = fenPartitions[0];
        final char[] boardTiles = gameConfiguration.replaceAll("/", "")
                .replaceAll("8", "--------")
                .replaceAll("7", "-------")
                .replaceAll("6", "------")
                .replaceAll("5", "-----")
                .replaceAll("4", "----")
                .replaceAll("3", "---")
                .replaceAll("2", "--")
                .replaceAll("1", "-")
                .toCharArray();
        int i = 0;
        int enPassantCoordinate = -1;
        if(Arrays.stream(BoardUtils.ALGEBRAIC_NOTATION).anyMatch(fenPartitions[3]::equals))
        	enPassantCoordinate = BoardUtils.getCoordinateAtPosition(fenPartitions[3]);
        while(i < boardTiles.length)
        {	
        	// Black End Passant Pawn
        	if(enPassantCoordinate != -1 && enPassantCoordinate == (i - 8) && BoardUtils.SIXTH_RANK[enPassantCoordinate])
        	{
        		PawnPiece enPassantPawn = new PawnPiece(PieceColor.BLACK, i);
        		builder.setPiece(enPassantPawn);
        		builder.setEnPassantPawn(enPassantPawn);
        		i++;
        		continue;
        	}
        	// White End Passant Pawn
        	else if(enPassantCoordinate != -1 && enPassantCoordinate == (i + 8) && BoardUtils.THIRD_RANK[enPassantCoordinate])
        	{
        		PawnPiece enPassantPawn = new PawnPiece(PieceColor.WHITE, i);
        		builder.setPiece(enPassantPawn);
        		builder.setEnPassantPawn(enPassantPawn);
        		i++;
        		continue;
        	}
        	
            switch(boardTiles[i])
            {
                case 'r':
                    builder.setPiece(new RookPiece(PieceColor.BLACK, i, checkIsFirstMove(i, firstMoveTiles)));
                    i++;
                    break;
                case 'n':
                    builder.setPiece(new KnightPiece(PieceColor.BLACK, i, checkIsFirstMove(i, firstMoveTiles)));
                    i++;
                    break;
                case 'b':
                    builder.setPiece(new BishopPiece(PieceColor.BLACK, i, checkIsFirstMove(i, firstMoveTiles)));
                    i++;
                    break;
                case 'q':
                    builder.setPiece(new QueenPiece(PieceColor.BLACK, i, checkIsFirstMove(i, firstMoveTiles)));
                    i++;
                    break;
                case 'k':
                    builder.setPiece(new KingPiece(PieceColor.BLACK, i, checkIsFirstMove(i, firstMoveTiles), isBlackCastled, blackKingSideCastle, blackQueenSideCastle));
                    i++;
                    break;
                case 'p':
                    builder.setPiece(new PawnPiece(PieceColor.BLACK, i, checkIsFirstMove(i, firstMoveTiles)));
                    i++;
                    break;
                case 'R':
                    builder.setPiece(new RookPiece(PieceColor.WHITE, i, checkIsFirstMove(i, firstMoveTiles)));
                    i++;
                    break;
                case 'N':
                    builder.setPiece(new KnightPiece(PieceColor.WHITE, i, checkIsFirstMove(i, firstMoveTiles)));
                    i++;
                    break;
                case 'B':
                    builder.setPiece(new BishopPiece(PieceColor.WHITE, i, checkIsFirstMove(i, firstMoveTiles)));
                    i++;
                    break;
                case 'Q':
                    builder.setPiece(new QueenPiece(PieceColor.WHITE, i, checkIsFirstMove(i, firstMoveTiles)));
                    i++;
                    break;
                case 'K':
                    builder.setPiece(new KingPiece(PieceColor.WHITE, i, checkIsFirstMove(i, firstMoveTiles), isWhiteCastled, whiteKingSideCastle, whiteQueenSideCastle));
                    i++;
                    break;
                case 'P':
                    builder.setPiece(new PawnPiece(PieceColor.WHITE, i, checkIsFirstMove(i, firstMoveTiles)));
                    i++;
                    break;
                case '-':
                    i++;
                    break;
                default:
                    throw new RuntimeException("Invalid FEN String " + gameConfiguration);
            }
        }
        builder.setMoveMaker(moveMaker(fenPartitions[1]));
        return builder.build();
    }
    
    /**
     * @param fenString - The FEN String
     * @return - A Chess Board based on the given FEN String
     */
    private static Board parseFEN(final String fenString)
    {
        final String[] fenPartitions = fenString.trim().split(" ");
        final Builder builder = new Builder();
        final boolean whiteKingSideCastle = whiteKingSideCastle(fenPartitions[2]);
        final boolean whiteQueenSideCastle = whiteQueenSideCastle(fenPartitions[2]);
        final boolean blackKingSideCastle = blackKingSideCastle(fenPartitions[2]);
        final boolean blackQueenSideCastle = blackQueenSideCastle(fenPartitions[2]);
        final String gameConfiguration = fenPartitions[0];
        final char[] boardTiles = gameConfiguration.replaceAll("/", "")
                .replaceAll("8", "--------")
                .replaceAll("7", "-------")
                .replaceAll("6", "------")
                .replaceAll("5", "-----")
                .replaceAll("4", "----")
                .replaceAll("3", "---")
                .replaceAll("2", "--")
                .replaceAll("1", "-")
                .toCharArray();
        int i = 0;
        int enPassantCoordinate = -1;
        if(Arrays.stream(BoardUtils.ALGEBRAIC_NOTATION).anyMatch(fenPartitions[3]::equals))
        	enPassantCoordinate = BoardUtils.getCoordinateAtPosition(fenPartitions[3]);
        while(i < boardTiles.length)
        {
        	// Black End Passant Pawn
        	if(enPassantCoordinate != -1 && enPassantCoordinate == (i - 8) && BoardUtils.SIXTH_RANK[enPassantCoordinate])
        	{
        		PawnPiece enPassantPawn = new PawnPiece(PieceColor.BLACK, i);
        		builder.setPiece(enPassantPawn);
        		builder.setEnPassantPawn(enPassantPawn);
        		i++;
        		continue;
        	}
        	// White End Passant Pawn
        	else if(enPassantCoordinate != -1 && enPassantCoordinate == (i + 8) && BoardUtils.THIRD_RANK[enPassantCoordinate])
        	{
        		PawnPiece enPassantPawn = new PawnPiece(PieceColor.WHITE, i);
        		builder.setPiece(enPassantPawn);
        		builder.setEnPassantPawn(enPassantPawn);
        		i++;
        		continue;
        	}

            switch(boardTiles[i])
            {
                case 'r':
                    builder.setPiece(new RookPiece(PieceColor.BLACK, i));
                    i++;
                    break;
                case 'n':
                    builder.setPiece(new KnightPiece(PieceColor.BLACK, i));
                    i++;
                    break;
                case 'b':
                    builder.setPiece(new BishopPiece(PieceColor.BLACK, i));
                    i++;
                    break;
                case 'q':
                    builder.setPiece(new QueenPiece(PieceColor.BLACK, i));
                    i++;
                    break;
                case 'k':
                    builder.setPiece(new KingPiece(PieceColor.BLACK, i, blackKingSideCastle, blackQueenSideCastle));
                    i++;
                    break;
                case 'p':
                    builder.setPiece(new PawnPiece(PieceColor.BLACK, i));
                    i++;
                    break;
                case 'R':
                    builder.setPiece(new RookPiece(PieceColor.WHITE, i));
                    i++;
                    break;
                case 'N':
                    builder.setPiece(new KnightPiece(PieceColor.WHITE, i));
                    i++;
                    break;
                case 'B':
                    builder.setPiece(new BishopPiece(PieceColor.WHITE, i));
                    i++;
                    break;
                case 'Q':
                    builder.setPiece(new QueenPiece(PieceColor.WHITE, i));
                    i++;
                    break;
                case 'K':
                    builder.setPiece(new KingPiece(PieceColor.WHITE, i, whiteKingSideCastle, whiteQueenSideCastle));
                    i++;
                    break;
                case 'P':
                    builder.setPiece(new PawnPiece(PieceColor.WHITE, i));
                    i++;
                    break;
                case '-':
                    i++;
                    break;
                default:
                    throw new RuntimeException("Invalid FEN String " + gameConfiguration);
            }
        }
        builder.setMoveMaker(moveMaker(fenPartitions[1]));
        return builder.build();
    }
    
    private static boolean checkIsFirstMove(Integer currentPos, String[] firstMoveList)
    {
    	return Arrays.asList(firstMoveList).contains(currentPos.toString());
    }

    private static PieceColor moveMaker(final String moveMakerString)
    {
        if(moveMakerString.equals("w"))
        {
            return PieceColor.WHITE;
        }
        else if(moveMakerString.equals("b"))
        {
            return PieceColor.BLACK;
        }
        throw new RuntimeException("Invalid FEN String " +moveMakerString);
    }

    private static boolean whiteKingSideCastle(final String fenCastleString)
    {
        return fenCastleString.contains("K");
    }

    private static boolean whiteQueenSideCastle(final String fenCastleString)
    {
        return fenCastleString.contains("Q");
    }

    private static boolean blackKingSideCastle(final String fenCastleString)
    {
        return fenCastleString.contains("k");
    }

    private static boolean blackQueenSideCastle(final String fenCastleString)
    {
        return fenCastleString.contains("q");
    }

    /**
     * @param board - The Chess Board
     * @return - A String that shows whether or not there are available castles on the Board
     */
    private static String calculateCastleText(final Board board)
    {
        final StringBuilder builder = new StringBuilder();
        if(board.getWhiteChessPlayer().isKingSideCastleCapable())
        {
            builder.append("K");
        }
        if(board.getWhiteChessPlayer().isQueenSideCastleCapable())
        {
            builder.append("Q");
        }
        if(board.getBlackChessPlayer().isKingSideCastleCapable())
        {
            builder.append("k");
        }
        if(board.getBlackChessPlayer().isQueenSideCastleCapable())
        {
            builder.append("q");
        }
        final String result = builder.toString();
        return result.isEmpty() ? "-" : result;
    }

    /**
     * @param board - The Chess Board
     * @return - The En Passant Pawn Square
     */
    private static String calculateEnPassantSquare(final Board board)
    {
        final PawnPiece enPassantPawn = board.getEnPassantPawn();
        if(enPassantPawn != null)
        {
            return BoardUtils.getPositionAtCoordinate(enPassantPawn.getPiecePosition() + (8) * enPassantPawn.getPieceColor().getOppositeDirection());
        }
        return "-";
    }

    /**
     * @param board - The Chess Board
     * @return - A String that represents the Chess Board and all pieces currently on it
     */
    private static String calculateBoardText(final Board board)
    {
        final StringBuilder builder = new StringBuilder();
        for(int i = 0; i < BoardUtils.NUM_TILES; i++)
        {
            final String tileText = board.getTile(i).getPiece() == null ? "-" :
                   					board.getTile(i).getPiece().getPieceColor().isWhite() ?
                   					board.getTile(i).getPiece().toString() :
                   					board.getTile(i).getPiece().toString().toLowerCase();
            builder.append(tileText);
        }
        builder.insert(8, "/");
        builder.insert(17, "/");
        builder.insert(26, "/");
        builder.insert(35, "/");
        builder.insert(44, "/");
        builder.insert(53, "/");
        builder.insert(62, "/");
        return builder.toString()
                	  .replaceAll("--------", "8")
                	  .replaceAll("-------", "7")
                	  .replaceAll("------", "6")
                	  .replaceAll("-----", "5")
                	  .replaceAll("----", "4")
                	  .replaceAll("---", "3")
                	  .replaceAll("--", "2")
                	  .replaceAll("-", "1");
    }

    /**
     * @param board - The Chess Board
     * @return - A lower case latter that represents the current Chess Player 
     */
    private static String calculateCurrentPlayerText(final Board board)
    {
        return board.getCurrentChessPlayer().toString().substring(0, 1).toLowerCase();
    }

    public static boolean isFENValid(String FEN)
    {
        String[] FENInfo = FEN.split(" ");
        String[] boardTileInfo = FENInfo[0].split("/");
        int splitCounter = StringUtils.countMatches(FENInfo[0], "/");
        int whiteKingCounter = StringUtils.countMatches(FENInfo[0], "K");
        int blackKingCounter = StringUtils.countMatches(FENInfo[0], "k");

        // If the FEN has more/less than 6 elements its invalid
        if(FENInfo.length != 6)
            return false;
        // We have 8 tile rows, so we need exactly 7 splits
        if(splitCounter != 7)
            return false;

        for(int i = 0; i < 8; i++)
        {
            String rowInfo = boardTileInfo[i];
            int valueCounter = 0;

            for(int j = 0; j < rowInfo.length(); j++)
            {
                if(rowInfo.charAt(j) == '1')
                    valueCounter += 1;
                else if(rowInfo.charAt(j) == '2')
                    valueCounter += 2;
                else if(rowInfo.charAt(j) == '3')
                    valueCounter += 3;
                else if(rowInfo.charAt(j) == '4')
                    valueCounter += 4;
                else if(rowInfo.charAt(j) == '5')
                    valueCounter += 5;
                else if(rowInfo.charAt(j) == '6')
                    valueCounter += 6;
                else if(rowInfo.charAt(j) == '7')
                    valueCounter += 7;
                else if(rowInfo.charAt(j) == '8')
                    valueCounter += 8;
                else
                    valueCounter += 1;
            }
            // If the contents of a row don't add up to a value of 8 its invalid
            if(valueCounter != 8)
                return false;
        }
        // We need exactly 1 white king
        if(whiteKingCounter != 1)
            return false;
        // We need exactly 1 black king
        if(blackKingCounter != 1)
            return false;
        // We ensure the next move player is a valid input
        if(!(FENInfo[1].equals("w") || FENInfo[1].equals("b")))
            return false;
        // If we made it this far it's time for the REALLY mighty checks...
        // Basically at this point we know the FEN is good enough to create a Board,
        // but it can still crash the game in some edge cases, so we create a temporary
        // Board to perform some more checks.
        Board board = FenUtil.createGameFromFEN(FEN);
        // If both players are in check the board is invalid
        if(board.getBlackChessPlayer().isInCheck() && board.getWhiteChessPlayer().isInCheck())
            return false;
        // If the non-active player is in check the board is invalid
        if(FENInfo[1].equals("w") && board.getBlackChessPlayer().isInCheck())
            return false;
        if(FENInfo[1].equals("b") && board.getWhiteChessPlayer().isInCheck())
            return false;
        return true;
    }
}