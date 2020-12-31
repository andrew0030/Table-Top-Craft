package andrews.table_top_craft.game_logic.chess.board.moves;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;

public class AttackMove extends BaseMove
{
	final BasePiece attackedPiece;
	
	public AttackMove(Board board, BasePiece piece, int destinationCoordinate, final BasePiece attackedPiece)
	{
		super(board, piece, destinationCoordinate);
		this.attackedPiece = attackedPiece;
	}

	@Override
	public int hashCode()
	{
		return this.attackedPiece.hashCode() + super.hashCode();
	}
	
	@Override
	public boolean equals(final Object object)
	{
		if(this == object)
			return true;
		if(!(object instanceof AttackMove))
			return false;
		final AttackMove otherAttackMove = (AttackMove) object;
		return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
	}
	
	@Override
	public boolean isAttack()
	{
		return true;
	}
	
	@Override
	public BasePiece getAttackedPiece()
	{
		return this.attackedPiece;
	}
}
