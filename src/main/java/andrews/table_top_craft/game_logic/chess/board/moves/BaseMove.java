package andrews.table_top_craft.game_logic.chess.board.moves;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.Board.Builder;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;

public abstract class BaseMove
{
	public static final BaseMove NULL_MOVE = new NullMove();
	
	protected final Board board;
	protected final BasePiece movedPiece;
	protected final int destinationCoordinate;
	protected final boolean isFirstMove;
	
	BaseMove(final Board board, final BasePiece movedPiece, final int destinationCoordinate)
	{
		this.board = board;
		this.movedPiece = movedPiece;
		this.destinationCoordinate = destinationCoordinate;
		this.isFirstMove = movedPiece.isFirstMove();
	}
	
	BaseMove(final Board board, final int destinationCoordinate)
	{
		this.board = board;
		this.destinationCoordinate = destinationCoordinate;
		this.movedPiece = null;
		this.isFirstMove = false;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + this.destinationCoordinate;
		result = prime * result + this.movedPiece.hashCode();
		result = prime * result + this.movedPiece.getPiecePosition();
		return result;
	}
	
	@Override
	public boolean equals(final Object object)
	{
		if(this == object)
			return true;
		if(!(object instanceof BaseMove))
			return false;
		final BaseMove otherMove = (BaseMove) object;
		return getCurrentCoordinate() == otherMove.getCurrentCoordinate() &&
			   getDestinationCoordinate() == otherMove.getDestinationCoordinate() &&
			   getMovedPiece().equals(otherMove.getMovedPiece());
	}
	
	/**
	 * @return The current Chess Board
	 */
	public Board getBoard()
	{
		return this.board;
	}
	
	/**
	 * @return The current Coordinate of the moved Piece
	 */
	public int getCurrentCoordinate()
	{
		return this.movedPiece.getPiecePosition();
	}
	
	/**
	 * @return The Destination Coordinates of this Move
	 */
	public int getDestinationCoordinate()
	{
		return this.destinationCoordinate;
	}
	
	/**
	 * @return The Moved Piece
	 */
	public BasePiece getMovedPiece()
	{
		return this.movedPiece;
	}
	
	/**
	 * @return Whether this Move is an Attack Move
	 */
	public boolean isAttack()
	{
		return false;
	}
	
	/**
	 * @return Whether this Move is a Castling Move
	 */
	public boolean isCastlingMove()
	{
		return false;
	}

	/**
	 * @return Whether this Move is an En Passant Move
	 */
	public boolean isEnPassantMove()
	{
		return false;
	}

	/**
	 * @return Whether this Move is a Pawn Promotion Move
	 */
	public boolean isPawnPromotion()
	{
		return false;
	}
	
	/**
	 * @return The Piece that was attacked with this Move
	 */
	public BasePiece getAttackedPiece()
	{
		return null;
	}

	public Board execute()
	{
		final Builder builder = new Builder();
		
		// The Pieces of the current Chess Player
		for(final BasePiece piece : this.board.getCurrentChessPlayer().getActivePieces())
		{
			// We check if the Piece is equal to the one that was moved, and if it wasn't we just set them
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
		// Lastly we set the Piece for the moved Piece
		builder.setPiece(this.movedPiece.movePiece(this));
		// We change the move maker to the opponent
		builder.setMoveMaker(this.board.getCurrentChessPlayer().getOpponent().getPieceColor());
		
		return builder.build();
	}
	
	public String saveToNBT()
	{
		return "";
	}
	
	public String getColorForPiece(BasePiece piece)
	{
		return piece.getPieceColor().isWhite() ? "W" : "B";
	}
}
