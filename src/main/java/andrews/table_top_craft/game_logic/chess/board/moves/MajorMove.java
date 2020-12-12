package andrews.table_top_craft.game_logic.chess.board.moves;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.Board.Builder;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;

public final class MajorMove extends BaseMove
{
	public MajorMove(Board board, BasePiece piece, int destinationCoordinate)
	{
		super(board, piece, destinationCoordinate);
	}

	@Override
	public Board execute()
	{
		final Builder builder = new Builder();
		
		// The Pieces of the current Chess Player
		for(final BasePiece piece : this.board.getCurrentChessPlayer().getActivePieces())
		{
			// We check if the Piece is equal to the one that was moved, and if it wasn't we just set them
			if(!this.movedPiece.equals(piece))//TODO hashcode and equals for Pieces
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
		// Lastly we set the Piece for the moved Piece
		builder.setPiece(this.movedPiece.movePiece(this));
		// We change the move maker to the opponent
		builder.setMoveMaker(this.board.getCurrentChessPlayer().getOpponent().getPieceColor());
		
		return builder.build();
	}
}
