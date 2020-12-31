package andrews.table_top_craft.game_logic.chess.player.ai;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.moves.BaseMove;
import andrews.table_top_craft.game_logic.chess.player.MoveTransition;

public class MiniMax implements MoveStrategy
{
	private final BoardEvaluator boardEvaluator;
	private final int searchDepth;
	
	public MiniMax(final int searchDepth)
	{
		this.boardEvaluator = new StandardBoardEvaluator();
		this.searchDepth = searchDepth;
	}
	
	@Override
	public String toString()
	{
		return "MiniMax";
	}
	
	@Override
	public BaseMove execute(Board board)
	{
		final long startTime = System.currentTimeMillis();
		BaseMove bestMove = null;
		int highestSeenValue = Integer.MIN_VALUE;
		int lowestSeenValue = Integer.MAX_VALUE;
		int currentValue;
		
		System.out.println(board.getCurrentChessPlayer() + " is thinking with depth: " + this.searchDepth);//TODO remove
		
		int numMoves = board.getCurrentChessPlayer().getLegalMoves().size();
		for(final BaseMove move : board.getCurrentChessPlayer().getLegalMoves())
		{
			final MoveTransition moveTransition = board.getCurrentChessPlayer().makeMove(move);
			if(moveTransition.getMoveStatus().isDone())
			{
				currentValue = board.getCurrentChessPlayer().getPieceColor().isWhite() ?
							   min(moveTransition.getTransitionBoard(), this.searchDepth - 1) :
							   max(moveTransition.getTransitionBoard(), this.searchDepth - 1);
							   
				if(board.getCurrentChessPlayer().getPieceColor().isWhite() && currentValue >= highestSeenValue)
				{
					highestSeenValue = currentValue;
					bestMove = move;
				}
				else if(board.getCurrentChessPlayer().getPieceColor().isBlack() && currentValue <= lowestSeenValue)
				{
					lowestSeenValue = currentValue;
					bestMove = move;
				}
			}
		}
		final long executionTime = System.currentTimeMillis() - startTime;
		return bestMove;
	}
	
	public int min(final Board board, final int depth)
	{
		if(depth == 0 || isEndGameScenario(board))
			return this.boardEvaluator.evaluate(board, this.searchDepth);
		int lowestSeenValue = Integer.MAX_VALUE;
		for(final BaseMove move : board.getCurrentChessPlayer().getLegalMoves())
		{
			final MoveTransition moveTransition = board.getCurrentChessPlayer().makeMove(move);
			if(moveTransition.getMoveStatus().isDone())
			{
				final int currentValue = max(moveTransition.getTransitionBoard(), depth - 1);
				if(currentValue <= lowestSeenValue)
					lowestSeenValue = currentValue;
			}
		}
		return lowestSeenValue;
	}
	
	public int max(final Board board, final int depth)
	{
		if(depth == 0 || isEndGameScenario(board))
			return this.boardEvaluator.evaluate(board, this.searchDepth);
		int highestSeenValue = Integer.MIN_VALUE;
		for(final BaseMove move : board.getCurrentChessPlayer().getLegalMoves())
		{
			final MoveTransition moveTransition = board.getCurrentChessPlayer().makeMove(move);
			if(moveTransition.getMoveStatus().isDone())
			{
				final int currentValue = min(moveTransition.getTransitionBoard(), depth - 1);
				if(currentValue >= highestSeenValue)
					highestSeenValue = currentValue;
			}
		}
		return highestSeenValue;
	}
	
	private static boolean isEndGameScenario(final Board board)//TODO add 3 moves draw
	{
		return board.getCurrentChessPlayer().isInCheckMate() || board.getCurrentChessPlayer().isInStaleMate();
	}
}
