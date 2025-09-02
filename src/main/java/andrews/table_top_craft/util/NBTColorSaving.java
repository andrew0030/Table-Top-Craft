package andrews.table_top_craft.util;

public class NBTColorSaving
{	
	public static int createWhiteColor()
	{
		return Color.pack(255, 255, 255, 255);
	}
	
	public static int createWhiteTilesColor()
	{
		return Color.pack(208, 177, 141, 255);
	}
	
	public static int createBlackTilesColor()
	{
		return Color.pack(139, 86, 57, 255);
	}
	
	public static int createWhitePiecesColor()
	{
		return Color.pack(210, 188, 161, 255);
	}
	
	public static int createBlackPiecesColor()
	{
		return Color.pack(51, 51, 51, 255);
	}
	
	public static int createLegalMoveColor()
	{
		return Color.pack(1, 255, 1, 100);
	}
	
	public static int createInvalidMoveColor()
	{
		return Color.pack(255, 255, 1, 100);
	}
	
	public static int createAttackMoveColor()
	{
		return Color.pack(255, 1, 1, 100);
	}
	
	public static int createPreviousMoveColor()
	{
		return Color.pack(1, 150, 125, 100);
	}
	
	public static int createCastleMoveColor()
	{
		return Color.pack(125, 1, 255, 100);
	}
	
	public static String saveColor(int red, int green, int blue)
	{
		return red + "/" + green + "/" + blue + "/255";
	}
	
	public static String saveColor(int red, int green, int blue, int alpha)
	{
		return red + "/" + green + "/" + blue + "/" + alpha;
	}

	public static int getRed(int color)
	{
		return (color >> 16) & 0xff;
	}
	
	public static int getGreen(int color)
	{
		return (color >> 8) & 0xff;
	}
	
	public static int getBlue(int color)
	{
		return (color) & 0xff;
	}
	
	public static int getAlpha(int color)
	{
		return (color >> 24) & 0xff;
	}

	public static String getString(int color) {
		return getRed(color) + "/" + getGreen(color) + "/" + getBlue(color) + "/" + getAlpha(color);
	}

	public static Integer getRGB(String tileInfoColor) {
		String[] args = tileInfoColor.split("/");
		return Color.pack(
				Integer.parseInt(args[0]),
				Integer.parseInt(args[1]),
				Integer.parseInt(args[2]),
				Integer.parseInt(args[3])
		);
	}
}