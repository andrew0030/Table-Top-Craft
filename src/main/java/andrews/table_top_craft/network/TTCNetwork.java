package andrews.table_top_craft.network;

import andrews.table_top_craft.network.client.*;
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
		CHANNEL.messageBuilder(MessageClientOpenChessPieceSelectionScreen.class, id++)
		.encoder(MessageClientOpenChessPieceSelectionScreen::serialize)
		.decoder(MessageClientOpenChessPieceSelectionScreen::deserialize)
		.consumerMainThread(MessageClientOpenChessPieceSelectionScreen::handle)
		.add();

		CHANNEL.messageBuilder(MessageClientChessAnimationState.class, id++)
		.encoder(MessageClientChessAnimationState::serialize)
		.decoder(MessageClientChessAnimationState::deserialize)
		.consumerMainThread(MessageClientChessAnimationState::handle)
		.add();

		CHANNEL.messageBuilder(MessageClientOpenChessPromotionScreen.class, id++)
		.encoder(MessageClientOpenChessPromotionScreen::serialize)
		.decoder(MessageClientOpenChessPromotionScreen::deserialize)
		.consumerMainThread(MessageClientOpenChessPromotionScreen::handle)
		.add();

		CHANNEL.messageBuilder(MessageClientPlayChessTimerSound.class, id++)
		.encoder(MessageClientPlayChessTimerSound::serialize)
		.decoder(MessageClientPlayChessTimerSound::deserialize)
		.consumerMainThread(MessageClientPlayChessTimerSound::handle)
		.add();

		CHANNEL.messageBuilder(MessageClientChessParticles.class, id++)
		.encoder(MessageClientChessParticles::serialize)
		.decoder(MessageClientChessParticles::deserialize)
		.consumerMainThread(MessageClientChessParticles::handle)
		.add();

		CHANNEL.messageBuilder(MessageClientConnectFourAnimationState.class, id++)
		.encoder(MessageClientConnectFourAnimationState::serialize)
		.decoder(MessageClientConnectFourAnimationState::deserialize)
		.consumerMainThread(MessageClientConnectFourAnimationState::handle)
		.add();

		// Server Messages
		CHANNEL.messageBuilder(MessageServerNewChessGame.class, id++)
		.encoder(MessageServerNewChessGame::serialize)
		.decoder(MessageServerNewChessGame::deserialize)
		.consumerMainThread(MessageServerNewChessGame::handle)
		.add();
		
		CHANNEL.messageBuilder(MessageServerShowTileInfo.class, id++)
		.encoder(MessageServerShowTileInfo::serialize)
		.decoder(MessageServerShowTileInfo::deserialize)
		.consumerMainThread(MessageServerShowTileInfo::handle)
		.add();
		
		CHANNEL.messageBuilder(MessageServerLoadFEN.class, id++)
		.encoder(MessageServerLoadFEN::serialize)
		.decoder(MessageServerLoadFEN::deserialize)
		.consumerMainThread(MessageServerLoadFEN::handle)
		.add();
		
		CHANNEL.messageBuilder(MessageServerShowAvailableMoves.class, id++)
		.encoder(MessageServerShowAvailableMoves::serialize)
		.decoder(MessageServerShowAvailableMoves::deserialize)
		.consumerMainThread(MessageServerShowAvailableMoves::handle)
		.add();
		
		CHANNEL.messageBuilder(MessageServerShowPreviousMove.class, id++)
		.encoder(MessageServerShowPreviousMove::serialize)
		.decoder(MessageServerShowPreviousMove::deserialize)
		.consumerMainThread(MessageServerShowPreviousMove::handle)
		.add();
		
		CHANNEL.messageBuilder(MessageServerSetColor.class, id++)
		.encoder(MessageServerSetColor::serialize)
		.decoder(MessageServerSetColor::deserialize)
		.consumerMainThread(MessageServerSetColor::handle)
		.add();
		
		CHANNEL.messageBuilder(MessageServerUseCustomPlate.class, id++)
		.encoder(MessageServerUseCustomPlate::serialize)
		.decoder(MessageServerUseCustomPlate::deserialize)
		.consumerMainThread(MessageServerUseCustomPlate::handle)
		.add();
		
		CHANNEL.messageBuilder(MessageServerSetColors.class, id++)
		.encoder(MessageServerSetColors::serialize)
		.decoder(MessageServerSetColors::deserialize)
		.consumerMainThread(MessageServerSetColors::handle)
		.add();

		CHANNEL.messageBuilder(MessageServerRotateChessPieceFigure.class, id++)
		.encoder(MessageServerRotateChessPieceFigure::serialize)
		.decoder(MessageServerRotateChessPieceFigure::deserialize)
		.consumerMainThread(MessageServerRotateChessPieceFigure::handle)
		.add();

		CHANNEL.messageBuilder(MessageServerDoChessBoardInteraction.class, id++)
		.encoder(MessageServerDoChessBoardInteraction::serialize)
		.decoder(MessageServerDoChessBoardInteraction::deserialize)
		.consumerMainThread(MessageServerDoChessBoardInteraction::handle)
		.add();

		CHANNEL.messageBuilder(MessageServerSetPieceSet.class, id++)
		.encoder(MessageServerSetPieceSet::serialize)
		.decoder(MessageServerSetPieceSet::deserialize)
		.consumerMainThread(MessageServerSetPieceSet::handle)
		.add();

		CHANNEL.messageBuilder(MessageServerChangePieceSet.class, id++)
		.encoder(MessageServerChangePieceSet::serialize)
		.decoder(MessageServerChangePieceSet::deserialize)
		.consumerMainThread(MessageServerChangePieceSet::handle)
		.add();

		CHANNEL.messageBuilder(MessageServerChangePieceType.class, id++)
		.encoder(MessageServerChangePieceType::serialize)
		.decoder(MessageServerChangePieceType::deserialize)
		.consumerMainThread(MessageServerChangePieceType::handle)
		.add();

		CHANNEL.messageBuilder(MessageServerChangePieceScale.class, id++)
		.encoder(MessageServerChangePieceScale::serialize)
		.decoder(MessageServerChangePieceScale::deserialize)
		.consumerMainThread(MessageServerChangePieceScale::handle)
		.add();

		CHANNEL.messageBuilder(MessageServerOpenGUIWithServerPlayer.class, id++)
		.encoder(MessageServerOpenGUIWithServerPlayer::serialize)
		.decoder(MessageServerOpenGUIWithServerPlayer::deserialize)
		.consumerMainThread(MessageServerOpenGUIWithServerPlayer::handle)
		.add();

		CHANNEL.messageBuilder(MessageServerAdjustChessTimerTime.class, id++)
		.encoder(MessageServerAdjustChessTimerTime::serialize)
		.decoder(MessageServerAdjustChessTimerTime::deserialize)
		.consumerMainThread(MessageServerAdjustChessTimerTime::handle)
		.add();

		CHANNEL.messageBuilder(MessageServerChessVisuals.class, id++)
		.encoder(MessageServerChessVisuals::serialize)
		.decoder(MessageServerChessVisuals::deserialize)
		.consumerMainThread(MessageServerChessVisuals::handle)
		.add();

		CHANNEL.messageBuilder(MessageServerDoPawnPromotion.class, id++)
		.encoder(MessageServerDoPawnPromotion::serialize)
		.decoder(MessageServerDoPawnPromotion::deserialize)
		.consumerMainThread(MessageServerDoPawnPromotion::handle)
		.add();

		CHANNEL.messageBuilder(MessageServerResetChessTimer.class, id++)
		.encoder(MessageServerResetChessTimer::serialize)
		.decoder(MessageServerResetChessTimer::deserialize)
		.consumerMainThread(MessageServerResetChessTimer::handle)
		.add();

		CHANNEL.messageBuilder(MessageServerPauseChessTimer.class, id++)
		.encoder(MessageServerPauseChessTimer::serialize)
		.decoder(MessageServerPauseChessTimer::deserialize)
		.consumerMainThread(MessageServerPauseChessTimer::handle)
		.add();

		CHANNEL.messageBuilder(MessageServerDoConnectFourInteraction.class, id++)
		.encoder(MessageServerDoConnectFourInteraction::serialize)
		.decoder(MessageServerDoConnectFourInteraction::deserialize)
		.consumerMainThread(MessageServerDoConnectFourInteraction::handle)
		.add();
	}
}