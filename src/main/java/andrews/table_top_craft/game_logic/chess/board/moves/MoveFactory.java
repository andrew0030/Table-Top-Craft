package andrews.table_top_craft.game_logic.chess.board.moves;

import andrews.table_top_craft.game_logic.chess.board.Board;

public class MoveFactory
{
	private MoveFactory()
	{
		
	}
	
	/**
	 * @param board - The Chess Board
	 * @param currentCoordinate - The current Coordinate
	 * @param destinationCoordinate - The destination Coordinate
	 * @return - This method returns the available Move from the given Coordinate to the given Destination Coordinate
	 */
	public static BaseMove createMove(final Board board, final int currentCoordinate, final int destinationCoordinate)
	{
		for(final BaseMove move : board.getAllLegalMoves())
		{
			if(move.getCurrentCoordinate() == currentCoordinate && move.getDestinationCoordinate() == destinationCoordinate)
			{
				return move;
			}
		}
		return BaseMove.NULL_MOVE;
	}
}