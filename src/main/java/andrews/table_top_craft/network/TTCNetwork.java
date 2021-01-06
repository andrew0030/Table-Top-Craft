package andrews.table_top_craft.network;

import andrews.table_top_craft.network.server.MessageServerLoadFEN;
import andrews.table_top_craft.network.server.MessageServerNewChessGame;
import andrews.table_top_craft.network.server.MessageServerSetColor;
import andrews.table_top_craft.network.server.MessageServerSetColors;
import andrews.table_top_craft.network.server.MessageServerShowAvailableMoves;
import andrews.table_top_craft.network.server.MessageServerShowPreviousMove;
import andrews.table_top_craft.network.server.MessageServerShowTileInfo;
import andrews.table_top_craft.network.server.MessageServerUseCustomPlate;
import andrews.table_top_craft.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class TTCNetwork
{
public static final String NETWORK_PROTOCOL = "1";
	
	public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(Reference.MODID, "net"))
		.networkProtocolVersion(() -> NETWORK_PROTOCOL)
		.clientAcceptedVersions(NETWORK_PROTOCOL::equals)
		.serverAcceptedVersions(NETWORK_PROTOCOL::equals)
		.simpleChannel();
	
	/**
	 * Used to set up all the Messages
	 */
	public static void setupMessages()
	{
		int id = -1;
		//Client Messages
		
		//Server Messages
		CHANNEL.messageBuilder(MessageServerNewChessGame.class, id++)
		.encoder(MessageServerNewChessGame::serialize).decoder(MessageServerNewChessGame::deserialize)
		.consumer(MessageServerNewChessGame::handle)
		.add();
		
		CHANNEL.messageBuilder(MessageServerShowTileInfo.class, id++)
		.encoder(MessageServerShowTileInfo::serialize).decoder(MessageServerShowTileInfo::deserialize)
		.consumer(MessageServerShowTileInfo::handle)
		.add();
		
		CHANNEL.messageBuilder(MessageServerLoadFEN.class, id++)
		.encoder(MessageServerLoadFEN::serialize).decoder(MessageServerLoadFEN::deserialize)
		.consumer(MessageServerLoadFEN::handle)
		.add();
		
		CHANNEL.messageBuilder(MessageServerShowAvailableMoves.class, id++)
		.encoder(MessageServerShowAvailableMoves::serialize).decoder(MessageServerShowAvailableMoves::deserialize)
		.consumer(MessageServerShowAvailableMoves::handle)
		.add();
		
		CHANNEL.messageBuilder(MessageServerShowPreviousMove.class, id++)
		.encoder(MessageServerShowPreviousMove::serialize).decoder(MessageServerShowPreviousMove::deserialize)
		.consumer(MessageServerShowPreviousMove::handle)
		.add();
		
		CHANNEL.messageBuilder(MessageServerSetColor.class, id++)
		.encoder(MessageServerSetColor::serialize).decoder(MessageServerSetColor::deserialize)
		.consumer(MessageServerSetColor::handle)
		.add();
		
		CHANNEL.messageBuilder(MessageServerUseCustomPlate.class, id++)
		.encoder(MessageServerUseCustomPlate::serialize).decoder(MessageServerUseCustomPlate::deserialize)
		.consumer(MessageServerUseCustomPlate::handle)
		.add();
		
		CHANNEL.messageBuilder(MessageServerSetColors.class, id++)
		.encoder(MessageServerSetColors::serialize).decoder(MessageServerSetColors::deserialize)
		.consumer(MessageServerSetColors::handle)
		.add();
	}
}
