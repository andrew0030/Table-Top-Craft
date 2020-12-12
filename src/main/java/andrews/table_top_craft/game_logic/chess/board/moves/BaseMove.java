package andrews.table_top_craft.game_logic.chess.board.moves;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;

public abstract class BaseMove
{
	final Board board;
	final BasePiece movedPiece;
	final int destinationCoordinate;
	
	BaseMove(final Board board, final BasePiece movedPiece, final int destinationCoordinate)
	{
		this.board = board;
		this.movedPiece = movedPiece;
		this.destinationCoordinate = destinationCoordinate;
	}
	
	/**
	 * @return - The Destination Coordinates of this Move
	 */
	public int getDestinationCoordinate()
	{
		return this.destinationCoordinate;
	}
	
	/**
	 * @return - The Moved Piece
	 */
	public BasePiece getMovedPiece()
	{
		return this.movedPiece;
	}

	public abstract Board execute();
}
