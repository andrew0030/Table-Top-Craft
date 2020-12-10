package andrews.table_top_craft.game_logic.chess.board.moves;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;

public final class AttackMove extends BaseMove
{
	final BasePiece attackedPiece;
	
	public AttackMove(Board board, BasePiece piece, int destinationCoordinate, final BasePiece attackedPiece)
	{
		super(board, piece, destinationCoordinate);
		this.attackedPiece = attackedPiece;
	}
}
