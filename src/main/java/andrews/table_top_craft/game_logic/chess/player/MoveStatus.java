package andrews.table_top_craft.game_logic.chess.player;

public enum MoveStatus
{
	DONE
	{
		@Override
		boolean isDone()
		{
			return true;
		}
	},
	ILLEGAL_MOVE
	{
		@Override
		boolean isDone()
		{
			return false;
		}
	},
	LEAVES_PLAYER_IN_CHECK
	{
		@Override
		boolean isDone()
		{
			return false;
		}
	};
	
	abstract boolean isDone();
}
