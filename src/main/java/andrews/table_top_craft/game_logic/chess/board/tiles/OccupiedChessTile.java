package andrews.table_top_craft.game_logic.chess.board.tiles;

import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;

public final class OccupiedChessTile extends BaseChessTile
{
	private final BasePiece pieceOnTile;
	
	OccupiedChessTile(int tileCoordinate, final BasePiece pieceOnTile)
	{
		super(tileCoordinate);
		this.pieceOnTile = pieceOnTile;
	}
	
	@Override
	public String toString()
	{
		return getPiece().getPieceColor().isBlack() ? getPiece().toString().toLowerCase() : getPiece().toString();
	}

	@Override
	public boolean isTileOccupied()
	{
		return true;
	}

	@Override
	public BasePiece getPiece()
	{
		return this.pieceOnTile;
	}

}
