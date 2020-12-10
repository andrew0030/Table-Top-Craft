package andrews.table_top_craft.game_logic.chess.board.tiles;

import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;

public final class OccupiedChessTile extends BaseChessTile
{
	private final BasePiece piece;
	
	OccupiedChessTile(int tileCoordinate, final BasePiece piece)
	{
		super(tileCoordinate);
		this.piece = piece;
	}

	@Override
	public boolean isTileOccupied()
	{
		return true;
	}

	@Override
	public BasePiece getPiece()
	{
		return this.piece;
	}

}
