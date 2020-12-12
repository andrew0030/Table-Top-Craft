package andrews.table_top_craft.game_logic.chess.board.tiles;

import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;

public final class EmptyChessTile extends BaseChessTile
{
	EmptyChessTile(final int tileCoordinate)
	{
		super(tileCoordinate);
	}

	@Override
	public String toString()
	{
		return "-";
	}
	
	@Override
	public boolean isTileOccupied()
	{
		return false;
	}

	@Override
	public BasePiece getPiece()
	{
		return null;
	}
}
