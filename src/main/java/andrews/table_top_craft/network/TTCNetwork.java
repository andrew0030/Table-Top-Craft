package andrews.table_top_craft.network;

import andrews.table_top_craft.network.client.MessageClientOpenChessPieceSelectionScreen;
import andrews.table_top_craft.network.server.*;

public class TTCNetwork
{
	public static void registerClientNetworkMessages()
	{
		MessageClientOpenChessPieceSelectionScreen.registerPacket();
	}

	public static void registerNetworkMessages()
	{
		MessageServerNewChessGame.registerPacket();
		MessageServerShowTileInfo.registerPacket();
		MessageServerLoadFEN.registerPacket();
		MessageServerShowAvailableMoves.registerPacket();
		MessageServerShowPreviousMove.registerPacket();
		MessageServerSetColor.registerPacket();
		MessageServerUseCustomPlate.registerPacket();
		MessageServerSetColors.registerPacket();
		MessageServerRotateChessPieceFigure.registerPacket();
		MessageServerDoChessBoardInteraction.registerPacket();
		MessageServerSetPieceSet.registerPacket();
		MessageServerChangePieceSet.registerPacket();
		MessageServerChangePieceType.registerPacket();
		MessageServerChangePieceScale.registerPacket();
		MessageServerOpenGUIWithServerPlayer.registerPacket();
	}
}