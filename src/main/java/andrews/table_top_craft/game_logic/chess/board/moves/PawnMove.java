package andrews.table_top_craft.game_logic.chess.board.moves;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.BoardUtils;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;

public final class PawnMove extends BaseMove
{
	public PawnMove(Board board, BasePiece movedPiece, int destinationCoordinate)
	{
		super(board, movedPiece, destinationCoordinate);
	}
	
	@Override
	public boolean equals(final Object object)
	{
		return this == object || object instanceof PawnMove && super.equals(object);		
	}
	
	@Override
	public String toString()
	{
		return BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
	}
}