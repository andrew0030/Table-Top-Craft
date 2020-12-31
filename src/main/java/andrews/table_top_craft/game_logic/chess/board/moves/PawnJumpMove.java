package andrews.table_top_craft.game_logic.chess.board.moves;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.Board.Builder;
import andrews.table_top_craft.game_logic.chess.board.BoardUtils;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;
import andrews.table_top_craft.game_logic.chess.pieces.PawnPiece;

public final class PawnJumpMove extends BaseMove
{
	public PawnJumpMove(Board board, BasePiece movedPiece, int destinationCoordinate)
	{
		super(board, movedPiece, destinationCoordinate);
	}
	
	@Override
	public Board execute()
	{
		final Builder builder = new Builder();
		
		// The Pieces of the current Chess Player
		for(final BasePiece piece : this.board.getCurrentChessPlayer().getActivePieces())
		{
			// We check if the Piece is equal to the one that was moved, and if it wasn't we just set it
			if(!this.movedPiece.equals(piece))
			{
				builder.setPiece(piece);
			}
		}
		
		// The Pieces of the opponent Chess Player
		for(final BasePiece piece : this.board.getCurrentChessPlayer().getOpponent().getActivePieces())
		{
			// We dont need a check as the opponents Pieces haven't moved for sure
			builder.setPiece(piece);
		}
		final PawnPiece movedPawn = (PawnPiece) this.movedPiece.movePiece(this);
		builder.setPiece(movedPawn);
		builder.setEnPassantPawn(movedPawn);
		builder.setMoveMaker(this.board.getCurrentChessPlayer().getOpponent().getPieceColor());
		
		return builder.build();
	}
	
	@Override
	public String toString()
	{
		return movedPiece.getPieceType().toString() + BoardUtils.getPositionAtCoordinate(destinationCoordinate);
	}
}
