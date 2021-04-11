package andrews.table_top_craft.game_logic.chess.player;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.moves.BaseMove;

/**
 * Handles the transition from one Chess Board to another when
 * the Chess Player makes a Move
 */
public class MoveTransition
{
	private final Board transitionBoard;
	private final BaseMove move;
	private final MoveStatus moveStatus;
	
	public MoveTransition(final Board transitionBoard, final BaseMove move, final MoveStatus moveStatus)
	{
		this.transitionBoard = transitionBoard;
		this.move = move;
		this.moveStatus = moveStatus;
	}
	
	/**
	 * @return - The current MoveStatus
	 */
	public MoveStatus getMoveStatus()
	{
		return this.moveStatus;
	}
	
	/**
	 * @return - The Board we are transitioning to
	 */
	public Board getTransitionBoard()
	{
		return this.transitionBoard;
	}
	
	public BaseMove getMove()
	{
		return this.move;
	}
}
