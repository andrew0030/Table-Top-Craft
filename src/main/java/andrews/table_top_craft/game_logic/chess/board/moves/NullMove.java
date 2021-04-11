package andrews.table_top_craft.game_logic.chess.board.moves;

import andrews.table_top_craft.game_logic.chess.board.Board;

public final class NullMove extends BaseMove
{
	public NullMove()
	{
		super(null, -1);
	}
	
	@Override
	public Board execute()
	{
		throw new RuntimeException("Can not execute the null Move!");
	}
	
	@Override
	public int getCurrentCoordinate()
	{
		return -1;
	}
	
	@Override
	public String saveToNBT()
	{
		return "";
	}
}