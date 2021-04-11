package andrews.table_top_craft.game_logic.chess.board.moves;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.BoardUtils;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;

public class MajorAttackMove extends AttackMove
{
	public MajorAttackMove(final Board board, final BasePiece piece, final int destinationCoordinate, final BasePiece attackedPiece)
	{
		super(board, piece, destinationCoordinate, attackedPiece);
	}
	
	@Override
	public boolean equals(final Object object)
	{
		return this == object || object instanceof MajorAttackMove && super.equals(object);		
	}
	
	@Override
	public String toString()
	{
		return this.movedPiece.getPieceType() + "x" + BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
	}
	
	@Override
	public String saveToNBT()
	{
		return "major_attack/" + getColorForPiece(this.movedPiece) + "/" + this.movedPiece.getPiecePosition() + "/" + this.movedPiece.getPieceType().toString() + "/" + this.destinationCoordinate + "/" + this.attackedPiece.getPiecePosition() + "/" + this.attackedPiece.getPieceType().toString();
	}
}
