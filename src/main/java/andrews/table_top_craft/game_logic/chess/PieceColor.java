package andrews.table_top_craft.game_logic.chess;

import andrews.table_top_craft.game_logic.chess.board.BoardUtils;
import andrews.table_top_craft.game_logic.chess.player.BaseChessPlayer;
import andrews.table_top_craft.game_logic.chess.player.BlackChessPlayer;
import andrews.table_top_craft.game_logic.chess.player.WhiteChessPlayer;

public enum PieceColor
{
	WHITE
	{
		@Override
		public int getDirection()
		{
			return -1;
		}
		
		@Override
		public int getOppositeDirection()
		{
			return 1;
		}

		@Override
		public boolean isWhite()
		{
			return true;
		}

		@Override
		public boolean isBlack()
		{
			return false;
		}

		@Override
		public BaseChessPlayer chooseChessPlayer(final WhiteChessPlayer whiteChessPlayer, final BlackChessPlayer blackChessPlayer)
		{
			return whiteChessPlayer;
		}

		@Override
		public boolean isPawnPromotionSquare(int position)
		{
			return BoardUtils.EIGHTH_RANK[position];
		}
		
		@Override
        public String toString()
		{
            return "White";
        }
	},
	BLACK
	{
		@Override
		public int getDirection()
		{
			return 1;
		}
		
		@Override
		public int getOppositeDirection()
		{
			return -1;
		}

		@Override
		public boolean isWhite()
		{
			return false;
		}

		@Override
		public boolean isBlack()
		{
			return true;
		}

		@Override
		public BaseChessPlayer chooseChessPlayer(final WhiteChessPlayer whiteChessPlayer, final BlackChessPlayer blackChessPlayer)
		{
			return blackChessPlayer;
		}

		@Override
		public boolean isPawnPromotionSquare(int position)
		{
			return BoardUtils.FIRST_RANK[position];
		}
		
		@Override
        public String toString()
		{
            return "Black";
        }
	};
	
	/**
	 * @return - This method is used to determine the direction, Pieces like the Pawns should move on
	 */
	public abstract int getDirection();
	
	/**
	 * @return - This method is used to determine the direction, Pieces like the Pawns should move on
	 */
	public abstract int getOppositeDirection();
	
	/**
	 * @return - Whether or not this Piece is White
	 */
	public abstract boolean isWhite();
	
	/**
	 * @return - Whether or not this Piece is Black
	 */
	public abstract boolean isBlack();
	
	/**
	 * @param position - The Tile to check
	 * @return - Whether or not the given Tile is valid for Pawn promotion
	 */
	public abstract boolean isPawnPromotionSquare(int position);

	public abstract BaseChessPlayer chooseChessPlayer(WhiteChessPlayer whiteChessPlayer, BlackChessPlayer blackChessPlayer);
}
