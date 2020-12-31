package andrews.table_top_craft.game_logic.chess.board.moves;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.Board.Builder;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;

public final class PawnEnPassantAttackMove extends PawnAttackMove
{
	public PawnEnPassantAttackMove(Board board, BasePiece movedPiece, int destinationCoordinate, final BasePiece attackedPiece)
	{
		super(board, movedPiece, destinationCoordinate, attackedPiece);
	}
	
	@Override
	public boolean equals(final Object object)
	{
		return this == object || object instanceof PawnEnPassantAttackMove && super.equals(object);		
	}
	
	@Override
	public Board execute()
	{
		final Builder builder = new Builder();
		for(final BasePiece piece : this.board.getCurrentChessPlayer().getActivePieces())
		{
			if(!this.movedPiece.equals(piece))
			{
				builder.setPiece(piece);
			}
		}
		for(final BasePiece piece : this.board.getCurrentChessPlayer().getOpponent().getActivePieces())
		{
			if(!piece.equals(this.attackedPiece))
			{
				builder.setPiece(piece);
			}
		}
		builder.setPiece(this.movedPiece.movePiece(this));
		builder.setMoveMaker(this.board.getCurrentChessPlayer().getOpponent().getPieceColor());
		return builder.build();
	}
}