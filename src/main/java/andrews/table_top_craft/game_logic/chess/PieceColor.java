package andrews.table_top_craft.game_logic.chess;

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
	},
	BLACK
	{
		@Override
		public int getDirection()
		{
			return 1;
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
	};
	
	/**
	 * @return - This method is used to determine the direction, Pieces like the Pawns should move on
	 */
	public abstract int getDirection();
	
	/**
	 * @return - Whether or not this Piece is White
	 */
	public abstract boolean isWhite();
	
	/**
	 * @return - Whether or not this Piece is Black
	 */
	public abstract boolean isBlack();

	public abstract BaseChessPlayer chooseChessPlayer(WhiteChessPlayer whiteChessPlayer, BlackChessPlayer blackChessPlayer);
}
