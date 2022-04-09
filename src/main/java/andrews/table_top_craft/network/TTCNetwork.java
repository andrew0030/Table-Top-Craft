package andrews.table_top_craft.network;

import andrews.table_top_craft.network.server.*;
import andrews.table_top_craft.util.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

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
		// Client Messages
		
		// Server Messages
		CHANNEL.messageBuilder(MessageServerNewChessGame.class, id++)
		.encoder(MessageServerNewChessGame::serialize)
		.decoder(MessageServerNewChessGame::deserialize)
		.consumer(MessageServerNewChessGame::handle)
		.add();
		
		CHANNEL.messageBuilder(MessageServerShowTileInfo.class, id++)
		.encoder(MessageServerShowTileInfo::serialize)
		.decoder(MessageServerShowTileInfo::deserialize)
		.consumer(MessageServerShowTileInfo::handle)
		.add();
		
		CHANNEL.messageBuilder(MessageServerLoadFEN.class, id++)
		.encoder(MessageServerLoadFEN::serialize)
		.decoder(MessageServerLoadFEN::deserialize)
		.consumer(MessageServerLoadFEN::handle)
		.add();
		
		CHANNEL.messageBuilder(MessageServerShowAvailableMoves.class, id++)
		.encoder(MessageServerShowAvailableMoves::serialize)
		.decoder(MessageServerShowAvailableMoves::deserialize)
		.consumer(MessageServerShowAvailableMoves::handle)
		.add();
		
		CHANNEL.messageBuilder(MessageServerShowPreviousMove.class, id++)
		.encoder(MessageServerShowPreviousMove::serialize)
		.decoder(MessageServerShowPreviousMove::deserialize)
		.consumer(MessageServerShowPreviousMove::handle)
		.add();
		
		CHANNEL.messageBuilder(MessageServerSetColor.class, id++)
		.encoder(MessageServerSetColor::serialize)
		.decoder(MessageServerSetColor::deserialize)
		.consumer(MessageServerSetColor::handle)
		.add();
		
		CHANNEL.messageBuilder(MessageServerUseCustomPlate.class, id++)
		.encoder(MessageServerUseCustomPlate::serialize)
		.decoder(MessageServerUseCustomPlate::deserialize)
		.consumer(MessageServerUseCustomPlate::handle)
		.add();
		
		CHANNEL.messageBuilder(MessageServerSetColors.class, id++)
		.encoder(MessageServerSetColors::serialize)
		.decoder(MessageServerSetColors::deserialize)
		.consumer(MessageServerSetColors::handle)
		.add();

		CHANNEL.messageBuilder(MessageServerRotateChessPieceFigure.class, id++)
		.encoder(MessageServerRotateChessPieceFigure::serialize)
		.decoder(MessageServerRotateChessPieceFigure::deserialize)
		.consumer(MessageServerRotateChessPieceFigure::handle)
		.add();

		CHANNEL.messageBuilder(MessageServerDoChessBoardInteraction.class, id++)
		.encoder(MessageServerDoChessBoardInteraction::serialize)
		.decoder(MessageServerDoChessBoardInteraction::deserialize)
		.consumer(MessageServerDoChessBoardInteraction::handle)
		.add();

		CHANNEL.messageBuilder(MessageServerSetPieceSet.class, id++)
		.encoder(MessageServerSetPieceSet::serialize)
		.decoder(MessageServerSetPieceSet::deserialize)
		.consumer(MessageServerSetPieceSet::handle)
		.add();

		CHANNEL.messageBuilder(MessageServerChangePieceSet.class, id++)
		.encoder(MessageServerChangePieceSet::serialize)
		.decoder(MessageServerChangePieceSet::deserialize)
		.consumer(MessageServerChangePieceSet::handle)
		.add();

		CHANNEL.messageBuilder(MessageServerChangePieceType.class, id++)
		.encoder(MessageServerChangePieceType::serialize)
		.decoder(MessageServerChangePieceType::deserialize)
		.consumer(MessageServerChangePieceType::handle)
		.add();
	}
}