package andrews.table_top_craft.game_logic.chess.board.moves;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.BoardUtils;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;

public final class MajorMove extends BaseMove
{
	public MajorMove(Board board, BasePiece piece, int destinationCoordinate)
	{
		super(board, piece, destinationCoordinate);
	}
	
	@Override
	public boolean equals(final Object object)
	{
		return this == object || object instanceof MajorMove && super.equals(object);
	}
	
	@Override
	public String toString()
	{
		return movedPiece.getPieceType().toString() + BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
	}
}
