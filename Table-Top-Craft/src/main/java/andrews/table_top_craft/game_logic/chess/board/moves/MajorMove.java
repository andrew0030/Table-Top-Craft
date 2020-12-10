package andrews.table_top_craft.game_logic.chess.board.moves;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;

public final class MajorMove extends BaseMove
{
	public MajorMove(Board board, BasePiece piece, int destinationCoordinate)
	{
		super(board, piece, destinationCoordinate);
	}
}
