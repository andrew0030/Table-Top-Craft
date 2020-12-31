package andrews.table_top_craft.game_logic.chess.board.moves;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.BoardUtils;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;

public class PawnAttackMove extends AttackMove
{
	public PawnAttackMove(Board board, BasePiece movedPiece, int destinationCoordinate, final BasePiece attackedPiece)
	{
		super(board, movedPiece, destinationCoordinate, attackedPiece);
	}
	
	@Override
	public boolean equals(final Object object)
	{
		return this == object || object instanceof PawnAttackMove && super.equals(object);		
	}
	
	@Override
	public String toString()
	{
		return BoardUtils.getPositionAtCoordinate(this.movedPiece.getPiecePosition()).substring(0, 1) + "x" + BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
	}
}