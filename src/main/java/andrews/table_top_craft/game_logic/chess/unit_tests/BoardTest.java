package andrews.table_top_craft.game_logic.chess.unit_tests;

public class BoardTest
{

//	@Test
//    public void initialBoard()
//	{
//        final Board board = Board.createStandardBoard();
//        assertEquals(board.getCurrentChessPlayer().getLegalMoves().size(), 20);
//        assertEquals(board.getCurrentChessPlayer().getOpponent().getLegalMoves().size(), 20);
//        assertFalse(board.getCurrentChessPlayer().isInCheck());
//        assertFalse(board.getCurrentChessPlayer().isInCheckMate());
//        assertFalse(board.getCurrentChessPlayer().isCastled());
////        assertTrue(board.getCurrentChessPlayer().isKingSideCastleCapable());
////        assertTrue(board.getCurrentChessPlayer().isQueenSideCastleCapable());
//        assertEquals(board.getCurrentChessPlayer(), board.getWhiteChessPlayer());
//        assertEquals(board.getCurrentChessPlayer().getOpponent(), board.getBlackChessPlayer());
//        assertFalse(board.getCurrentChessPlayer().getOpponent().isInCheck());
//        assertFalse(board.getCurrentChessPlayer().getOpponent().isInCheckMate());
//        assertFalse(board.getCurrentChessPlayer().getOpponent().isCastled());
////        assertTrue(board.getCurrentChessPlayer().getOpponent().isKingSideCastleCapable());
////        assertTrue(board.getCurrentChessPlayer().getOpponent().isQueenSideCastleCapable());
//        assertTrue(board.getWhiteChessPlayer().toString().equals("White"));
//        assertTrue(board.getBlackChessPlayer().toString().equals("Black"));
//
////        final Iterable<Piece> allPieces = board.getAllPieces();
////        final Iterable<Move> allMoves = Iterables.concat(board.whitePlayer().getLegalMoves(), board.blackPlayer().getLegalMoves());
////        for(final Move move : allMoves) {
////            assertFalse(move.isAttack());
////            assertFalse(move.isCastlingMove());
////            assertEquals(MoveUtils.exchangeScore(move), 1);
////        }
//
////        assertEquals(Iterables.size(allMoves), 40);
////        assertEquals(Iterables.size(allPieces), 32);
////        assertFalse(BoardUtils.isEndGame(board));
////        assertFalse(BoardUtils.isThreatenedBoardImmediate(board));
////        assertEquals(StandardBoardEvaluator.get().evaluate(board, 0), 0);
////        assertEquals(board.getPiece(35), null);
//    }
//
//	@Test
//    public void testPlainKingMove()
//	{
//        final Builder builder = new Builder();
//        // Black Layout
////        builder.setPiece(new KingPiece(PieceColor.BLACK, 4, false, false));
//        builder.setPiece(new KingPiece(PieceColor.BLACK, 4));
//        builder.setPiece(new PawnPiece(PieceColor.BLACK, 12));
//        // White Layout
//        builder.setPiece(new PawnPiece(PieceColor.WHITE, 52));
////        builder.setPiece(new KingPiece(PieceColor.WHITE, 60, false, false));
//        builder.setPiece(new KingPiece(PieceColor.WHITE, 60));
//        builder.setMoveMaker(PieceColor.WHITE);
//        // Set the current player
//        final Board board = builder.build();
//        System.out.println(board);
//
//        assertEquals(board.getWhiteChessPlayer().getLegalMoves().size(), 6);
//        assertEquals(board.getBlackChessPlayer().getLegalMoves().size(), 6);
//        assertFalse(board.getCurrentChessPlayer().isInCheck());
//        assertFalse(board.getCurrentChessPlayer().isInCheckMate());
//        assertFalse(board.getCurrentChessPlayer().getOpponent().isInCheck());
//        assertFalse(board.getCurrentChessPlayer().getOpponent().isInCheckMate());
//        assertEquals(board.getCurrentChessPlayer(), board.getWhiteChessPlayer());
//        assertEquals(board.getCurrentChessPlayer().getOpponent(), board.getBlackChessPlayer());
////        BoardEvaluator evaluator = StandardBoardEvaluator.get();
////        System.out.println(evaluator.evaluate(board, 0));
////        assertEquals(StandardBoardEvaluator.get().evaluate(board, 0), 0);
//
//        final BaseMove move = MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e1"),
//              BoardUtils.getCoordinateAtPosition("f1"));
//
//        final MoveTransition moveTransition = board.getCurrentChessPlayer().makeMove(move);
//
////        assertEquals(moveTransition.getTransitionMove(), move);
////        assertEquals(moveTransition.getFromBoard(), board);
////        assertEquals(moveTransition.getToBoard().currentPlayer(), moveTransition.getToBoard().blackPlayer());
//
//        assertTrue(moveTransition.getMoveStatus().isDone());
////        assertEquals(moveTransition.getToBoard().whitePlayer().getPlayerKing().getPiecePosition(), 61);
////        System.out.println(moveTransition.getToBoard());
//
//    }
}
