package andrews.table_top_craft.game_logic.chess.board.moves;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;

public abstract class BaseMove
{
	final Board board;
	final BasePiece piece;
	final int destinationCoordinate;
	
	BaseMove(final Board board, final BasePiece piece, final int destinationCoordinate)
	{
		this.board = board;
		this.piece = piece;
		this.destinationCoordinate = destinationCoordinate;
	}
}
