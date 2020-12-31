package andrews.table_top_craft.game_logic.chess.unit_tests;

public class TestCheckMate
{
//	@Test
//    public void testFoolsMate()
//	{
//
//        final Board board = Board.createStandardBoard();
//        final MoveTransition t1 = board.getCurrentChessPlayer()
//                .makeMove(MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("f2"),
//                                BoardUtils.getCoordinateAtPosition("f3")));
//
//        assertTrue(t1.getMoveStatus().isDone());
//
//        final MoveTransition t2 = t1.getTransitionBoard()
//                .getCurrentChessPlayer()
//                .makeMove(MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e7"),
//                                BoardUtils.getCoordinateAtPosition("e5")));
//
//        assertTrue(t2.getMoveStatus().isDone());
//
//        final MoveTransition t3 = t2.getTransitionBoard()
//                .getCurrentChessPlayer()
//                .makeMove(MoveFactory.createMove(t2.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g2"),
//                                BoardUtils.getCoordinateAtPosition("g4")));
//
//        assertTrue(t3.getMoveStatus().isDone());
//
////        final MoveTransition t4 = t3.getTransitionBoard()
////                .getCurrentChessPlayer()
////                .makeMove(MoveFactory.createMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d8"),
////                                BoardUtils.getCoordinateAtPosition("h4")));
////
////        assertTrue(t4.getMoveStatus().isDone());
////        
////        assertTrue(t4.getTransitionBoard().getCurrentChessPlayer().isInCheckMate());
//        
//        final MoveStrategy strategy = new MiniMax(4);
//        final BaseMove aiMove = strategy.execute(t3.getTransitionBoard());
//        final BaseMove bestMove = MoveFactory.createMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d8"), BoardUtils.getCoordinateAtPosition("h4"));
//        
//        assertEquals(aiMove, bestMove);
//    }
}
