package andrews.table_top_craft.util;

import andrews.table_top_craft.network.TTCNetwork;
import andrews.table_top_craft.network.server.MessageServerLoadFEN;
import andrews.table_top_craft.network.server.MessageServerNewChessGame;
import andrews.table_top_craft.network.server.MessageServerSetColor;
import andrews.table_top_craft.network.server.MessageServerSetColors;
import andrews.table_top_craft.network.server.MessageServerShowAvailableMoves;
import andrews.table_top_craft.network.server.MessageServerShowPreviousMove;
import andrews.table_top_craft.network.server.MessageServerShowTileInfo;
import andrews.table_top_craft.network.server.MessageServerUseCustomPlate;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class NetworkUtil
{
	/**
	 * Sends a message to the server that the player pressed the new chess game button
	 * @param bufflonEntity - The Bufflon Entity that owns the Inventory
	 * @param shouldSit - Whether or not the entity should sit
	 */
	@OnlyIn(Dist.CLIENT)
	public static void newChessGameMessage(BlockPos pos)
	{
		TTCNetwork.CHANNEL.sendToServer(new MessageServerNewChessGame(pos));
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void showTileInfoMessage(BlockPos pos)
	{
		TTCNetwork.CHANNEL.sendToServer(new MessageServerShowTileInfo(pos));
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void loadFENMessage(BlockPos pos, String FEN)
	{
		TTCNetwork.CHANNEL.sendToServer(new MessageServerLoadFEN(pos, FEN));
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void showAvailableMovesMessage(BlockPos pos)
	{
		TTCNetwork.CHANNEL.sendToServer(new MessageServerShowAvailableMoves(pos));
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void showPreviousMoveMessage(BlockPos pos)
	{
		TTCNetwork.CHANNEL.sendToServer(new MessageServerShowPreviousMove(pos));
	}
	
	/**
	 * Sends a message to the server that the player pressed the set color button
	 * @param colorType - The color that will be set, 0 for tile info, 1 for legal move
	 * @param pos - The BlockPos of the ChessTileEntity
	 * @param color - The Color
	 */
	@OnlyIn(Dist.CLIENT)
	public static void setColorMessage(int colorType, BlockPos pos, String color)
	{
		TTCNetwork.CHANNEL.sendToServer(new MessageServerSetColor(colorType, pos, color));
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void useCustomPlateMessage(BlockPos pos)
	{
		TTCNetwork.CHANNEL.sendToServer(new MessageServerUseCustomPlate(pos));
	}
	
	/**
	 * Sends a message to the server that the player pressed the set colors button
	 * @param colorType - The colors that will be set, 0 for board tiles, 1 for pieces
	 * @param pos - The BlockPos of the ChessTileEntity
	 * @param color - The first Color for the White side
	 * @param color2 - The second Color for the Black side
	 */
	@OnlyIn(Dist.CLIENT)
	public static void setColorsMessage(int colorType, BlockPos pos, String color, String color2)
	{
		TTCNetwork.CHANNEL.sendToServer(new MessageServerSetColors(colorType, pos, color, color2));
	}
}
