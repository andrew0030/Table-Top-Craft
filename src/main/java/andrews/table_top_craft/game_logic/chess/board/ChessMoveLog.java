package andrews.table_top_craft.game_logic.chess.board;

import java.util.ArrayList;
import java.util.List;

import andrews.table_top_craft.game_logic.chess.board.moves.BaseMove;

public class ChessMoveLog
{
	private final List<BaseMove> moves;
	
	public ChessMoveLog()
	{
		this.moves = new ArrayList<>();
	}
	
	/**
	 * @return - A List of all the Moves that have been made since the start of the Chess Game
	 */
	public List<BaseMove> getMoves()
	{
		return this.moves;
	}
	
	/**
	 * Adds a Move to the List of Moves in the MoveLog
	 * @param move - The Move that was made
	 */
	public void addMove(final BaseMove move)
	{
		this.moves.add(move);
	}
	
	/**
	 * @return - The amount of Moves that have been stored in the MoveLog List
	 */
	public int size()
	{
		return this.moves.size();
	}
	
	/**
	 * Removes all Moves from the MoveLog List
	 */
	public void clear()
	{
		this.moves.clear();
	}
	
	/**
	 * @param index - The Index of the Move that should be removed
	 * @return - Removes the Move at the given Index
	 */
	public BaseMove removeMove(int index)
	{
		return this.moves.remove(index);
	}
	
	/**
	 * @param move - The Move that should be removed
	 * @return - Removes the given Move
	 */
	public boolean removeMove(final BaseMove move)
	{
		return this.moves.remove(move);
	}
}
