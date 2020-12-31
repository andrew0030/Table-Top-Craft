package andrews.table_top_craft.util;

import andrews.table_top_craft.network.TTCNetwork;
import andrews.table_top_craft.network.server.MessageServerLoadFEN;
import andrews.table_top_craft.network.server.MessageServerNewChessGame;
import andrews.table_top_craft.network.server.MessageServerShowAvailableMoves;
import andrews.table_top_craft.network.server.MessageServerShowPreviousMove;
import andrews.table_top_craft.network.server.MessageServerShowTileInfo;
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
}
