package andrews.table_top_craft.game_logic.chess.board.moves;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.Board.Builder;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;
import andrews.table_top_craft.game_logic.chess.pieces.RookPiece;

abstract class CastleMove extends BaseMove
{
	protected final RookPiece castleRook;
	protected final int castleRookStart;
	protected final int castleRookDestination;
	
	public CastleMove(Board board, BasePiece movedPiece, int destinationCoordinate, final RookPiece castleRook, final int castleRookStart, final int castleRookDestination)
	{
		super(board, movedPiece, destinationCoordinate);
		this.castleRook = castleRook;
		this.castleRookStart = castleRookStart;
		this.castleRookDestination = castleRookDestination;
	}
	
	/**
	 * @return - The Castle RookPiece
	 */
	public RookPiece getCastleRook()
	{
		return this.castleRook;
	}
	
	@Override
	public boolean isCastlingMove()
	{
		return true;
	}
	
	@Override
	public Board execute()
	{
		final Builder builder = new Builder();
		
		// The Pieces of the current Chess Player
		for(final BasePiece piece : this.board.getCurrentChessPlayer().getActivePieces())
		{
			// We check if the Piece is equal to the one that was moved, or
			// if it is equal to the Castle RookPiece, and if it wasn't we just set it
			if(!this.movedPiece.equals(piece) && !this.castleRook.equals(piece))
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
		builder.setPiece(this.movedPiece.movePiece(this));
		builder.setPiece(new RookPiece(this.castleRook.getPieceColor(), this.castleRookDestination));//TODO first move
		builder.setMoveMaker(this.board.getCurrentChessPlayer().getOpponent().getPieceColor());
		
		return builder.build();
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.castleRook.hashCode();
		result = prime * result + this.castleRookDestination;
		return result;
	}
	
	@Override
	public boolean equals(final Object object)
	{
		if(this == object)
			return true;
		if(!(object instanceof CastleMove))
			return false;
		final CastleMove otherCastleMove = (CastleMove) object;
		return super.equals(otherCastleMove) && this.castleRook.equals(otherCastleMove.getCastleRook());
	}
}
