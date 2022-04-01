package andrews.table_top_craft.util;

public class NBTColorSaving
{	
	public static String createWhiteColor()
	{
		return "255/255/255/255";
	}
	
	public static String createWhiteTilesColor()
	{
		return "208/177/141/255";
	}
	
	public static String createBlackTilesColor()
	{
		return "139/86/57/255";
	}
	
	public static String createWhitePiecesColor()
	{
		return "210/188/161/255";
	}
	
	public static String createBlackPiecesColor()
	{
		return "51/51/51/255";
	}
	
	public static String createLegalMoveColor()
	{
		return "1/255/1/100";
	}
	
	public static String createInvalidMoveColor()
	{
		return "255/255/1/100";
	}
	
	public static String createAttackMoveColor()
	{
		return "255/1/1/100";
	}
	
	public static String createPreviousMoveColor()
	{
		return "1/150/125/100";
	}
	
	public static String createCastleMoveColor()
	{
		return "125/1/255/100";
	}
	
	public static String saveColor(int red, int green, int blue)
	{
		return Integer.toString(red) + "/" + Integer.toString(green) + "/" + Integer.toString(blue) + "/1";
	}
	
	public static String saveColor(int red, int green, int blue, int alpha)
	{
		return Integer.toString(red) + "/" + Integer.toString(green) + "/" + Integer.toString(blue) + "/" + Integer.toString(alpha);
	}

	public static int getRed(String color)
	{
		String[] values = color.split("/");
		try
		{
			return Integer.parseInt(values[0]);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return 255;
	}
	
	public static int getGreen(String color)
	{
		String[] values = color.split("/");
		try
		{
			return Integer.parseInt(values[1]);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return 255;
	}
	
	public static int getBlue(String color)
	{
		String[] values = color.split("/");
		try
		{
			return Integer.parseInt(values[2]);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return 255;
	}
	
	public static int getAlpha(String color)
	{
		String[] values = color.split("/");
		try
		{
			return Integer.parseInt(values[3]);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return 255;
	}
}