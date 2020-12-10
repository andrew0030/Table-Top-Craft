package andrews.table_top_craft.game_logic.chess.board;

/**
 * This class contains a few Methods used by multiple Pieces, for game logic
 */
public class BoardUtils
{	
	public static final boolean[] FIRST_COLUMN = initColumn(0);
	public static final boolean[] SECOND_COLUMN = initColumn(1);
	public static final boolean[] SEVENTH_COLUMN = initColumn(6);
	public static final boolean[] EIGHTH_COLUMN = initColumn(7);
	
	public static final int NUM_TILES = 64;
	public static final int NUM_TILES_PER_ROW = 8;

	/**
	 * @param coordinate - The coordinate to check
	 * @return - Wether or not the given coordinate is inside the bounds of 0 and 64
	 */
	public static boolean isValidTileCoordinate(final int coordinate)
	{
		return coordinate >= 0 && coordinate < NUM_TILES;
	}

	/**
	 * @param columnNumber - The Column number
	 * @return - The column numbers that are still within the bounds of 64 after adding 8 to them
	 */
	private static boolean[] initColumn(int columnNumber)
	{
		final boolean[] column = new boolean[NUM_TILES];
		
		do 
		{
			column[columnNumber] = true;
			columnNumber += NUM_TILES_PER_ROW;
		}
		while(columnNumber < NUM_TILES);

		return column;
	}
}