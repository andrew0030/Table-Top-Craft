package andrews.table_top_craft.game_logic.chess.board.moves;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;
import andrews.table_top_craft.game_logic.chess.pieces.RookPiece;

public final class QueenSideCastleMove extends CastleMove
{

	public QueenSideCastleMove(Board board, BasePiece movedPiece, int destinationCoordinate, final RookPiece castleRook, final int castleRookStart, final int castleRookDestination)
	{
		super(board, movedPiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
	}
	
	@Override
	public boolean equals(final Object object)
	{
		return this == object || object instanceof QueenSideCastleMove && super.equals(object);		
	}
	
	@Override
	public String toString()
	{
		return "O-O-O";
	}
	
	@Override
	public String saveToNBT()
	{
		return "queen_side_castle/" + getColorForPiece(this.movedPiece) + "/" + this.movedPiece.getPiecePosition() + "/" + this.destinationCoordinate + "/" + this.castleRook.getPiecePosition() + "/" + this.castleRookStart + "/" + this.castleRookDestination;
	}
}