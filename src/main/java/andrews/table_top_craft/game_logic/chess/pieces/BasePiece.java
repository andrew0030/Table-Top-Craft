package andrews.table_top_craft.game_logic.chess.pieces;

import java.util.Collection;

import andrews.table_top_craft.game_logic.chess.PieceColor;
import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.moves.BaseMove;

public abstract class BasePiece
{
	protected final PieceType pieceType;
	protected final int piecePosition;
	protected final PieceColor pieceColor;
	protected final boolean isFirstMove;
	private final int cachedHashCode;
	
	BasePiece(final PieceType pieceType, final int piecePosition, final PieceColor pieceColor, final boolean isFirstMove)
	{
		this.pieceType = pieceType;
		this.piecePosition = piecePosition;
		this.pieceColor = pieceColor;
		this.isFirstMove = isFirstMove;
		this.cachedHashCode = computeHashCode();
	}
	
	/**
	 * @return - A HashCode for this BasePiece
	 */
	private int computeHashCode()
	{
		int result = pieceType.hashCode();
		result = 31 * result + pieceColor.hashCode();
		result = 31 * result + piecePosition;
		result = 31 * result + (isFirstMove ? 1 : 0);
		
		return result;
	}

	@Override
	public boolean equals(final Object object)
	{
		if(this == object)
			return true;
		if(!(object instanceof BasePiece))
			return false;
		// After we got past all the if checks, we can cast the object to a BasePiece
		final BasePiece otherPiece = (BasePiece) object;
		
		return piecePosition == otherPiece.getPiecePosition() &&
			   pieceType == otherPiece.getPieceType() &&
			   pieceColor == otherPiece.getPieceColor() &&
			   isFirstMove == otherPiece.isFirstMove();
	}
	
	@Override
	public int hashCode()
	{
		return this.cachedHashCode;
	}
	
	/**
	 * @return - The Position of this Piece
	 */
	public int getPiecePosition()
	{
		return this.piecePosition;
	}
	
	/**
	 * @return - The PieceColor of this Piece
	 */
	public PieceColor getPieceColor()
	{
		return this.pieceColor;
	}
	
	/**
	 * @return - Whether or not this Piece has all ready been moved
	 */
	public boolean isFirstMove()
	{
		return this.isFirstMove;
	}
	
	/**
	 * @return - The PieceType of this Piece
	 */
	public PieceType getPieceType()
	{
		return this.pieceType;
	}
	
	/**
	 * @return - The Value of this Piece
	 */
	public int getPieceValue()
	{
		return this.pieceType.getPieceValue();
	}
	
	/**
	 * @param board - The Chess Board
	 * @return - A Collection of all legal Moves this Piece has
	 */
	public abstract Collection<BaseMove> calculateLegalMoves(final Board board);
	
	/**
	 * @return - The bonus value this Piece gets for being in a certain position
	 */
	public abstract int locationBonus();
	
	/**
	 * @param move - The Move that is being made
	 * @return - A new Piece that is just like the old one, with an updated Piece position
	 */
	public abstract BasePiece movePiece(BaseMove move);
	
	public enum PieceType
	{
		PAWN(100, "P")
		{
			@Override
			public boolean isKing()
			{
				return false;
			}

			@Override
			public boolean isRook()
			{
				return false;
			}
		},
		KNIGHT(300, "N")
		{
			@Override
			public boolean isKing()
			{
				return false;
			}

			@Override
			public boolean isRook()
			{
				return false;
			}
		},
		BISHOP(300, "B")
		{
			@Override
			public boolean isKing()
			{
				return false;
			}

			@Override
			public boolean isRook()
			{
				return false;
			}
		},
		ROOK(500, "R")
		{
			@Override
			public boolean isKing()
			{
				return false;
			}

			@Override
			public boolean isRook()
			{
				return true;
			}
		},
		QUEEN(900, "Q")
		{
			@Override
			public boolean isKing()
			{
				return false;
			}

			@Override
			public boolean isRook()
			{
				return false;
			}
		},
		KING(10000, "K")
		{
			@Override
			public boolean isKing()
			{
				return true;
			}

			@Override
			public boolean isRook()
			{
				return false;
			}
		};
		
		private int pieceValue;
		private String pieceName;
		
		PieceType(final int pieceValue, final String pieceName)
		{
			this.pieceValue = pieceValue;
			this.pieceName = pieceName;
		}
		
		@Override
		public String toString()
		{
			return this.pieceName;
		}
		
		public int getPieceValue()
		{
			return this.pieceValue;
		}
		
		/**
		 * @return - Whether or not this PieceType is a KingPiece
		 */
		public abstract boolean isKing();

		/**
		 * @return - Whether or not this PieceType is a RookPiece
		 */
		public abstract boolean isRook();
	}

	public enum PieceModelSet
	{
		STANDARD,
		CLASSIC,
		PANDORAS_CREATURES;
	}
}