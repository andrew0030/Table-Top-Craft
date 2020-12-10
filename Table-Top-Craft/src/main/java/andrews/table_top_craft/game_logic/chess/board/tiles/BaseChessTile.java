package andrews.table_top_craft.game_logic.chess.board.tiles;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import andrews.table_top_craft.game_logic.chess.board.BoardUtils;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;

public abstract class BaseChessTile
{
	protected final int tileCoordinate;
	// A Map that stores all possible EmptyTiles
	private static final Map<Integer, EmptyChessTile> EMPTY_TILES_CACHE = createAllEmptyTiles();
	
	BaseChessTile(final int tileCoordinate)
	{
		this.tileCoordinate = tileCoordinate;
	}
	
	/**
	 * @param tileCoordinate - The tile coordinate
	 * @param piece - The Piece that occupies this tile
	 * @return - A new OccupiedChessTile or points to an all ready created EmptyChessTile, stored in EMPTY_TILES_CACHE
	 */
	public BaseChessTile createTile(final int tileCoordinate, final BasePiece piece)
	{
		return piece != null ? new OccupiedChessTile(tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
	}
	
	/**
	 * Creates a Map with all possible EmptyChessTiles
	 */
	private static Map<Integer, EmptyChessTile> createAllEmptyTiles()
	{
		final Map<Integer, EmptyChessTile> emptyTileMap = new HashMap<>();
		
		for(int i = 0; i < BoardUtils.NUM_TILES; i++)
		{
			emptyTileMap.put(i, new EmptyChessTile(i));
		}
		return ImmutableMap.copyOf(emptyTileMap);
	}

	/**
	 * @return - Whether or not this Tile is occupied by a Piece
	 */
	public abstract boolean isTileOccupied();
	
	/**
	 * @return - The Piece that is occupying this Tile
	 */
	public abstract BasePiece getPiece();
}
