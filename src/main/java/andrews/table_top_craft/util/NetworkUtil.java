package andrews.table_top_craft.util;

import andrews.table_top_craft.network.TTCNetwork;
import andrews.table_top_craft.network.client.*;
import andrews.table_top_craft.network.server.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;

public class NetworkUtil
{
	public static void openChessPieceSelectionFromServer(BlockPos pos, boolean isStandardSetUnlocked, boolean isClassicSetUnlocked, boolean isPandorasCreaturesSetUnlocked, ServerPlayer serverPlayer)
	{
		TTCNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new MessageClientOpenChessPieceSelectionScreen(pos, isStandardSetUnlocked, isClassicSetUnlocked, isPandorasCreaturesSetUnlocked));
	}

	public static void setChessAnimationForAllTracking(Level level, BlockPos pos, byte actionType, byte currentCord, byte destCord)
	{
		TTCNetwork.CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(pos)), new MessageClientChessAnimationState(pos, actionType, currentCord, destCord));
	}

	public static void setChessAnimationForAllTracking(Level level, BlockPos pos, byte actionType)
	{
		NetworkUtil.setChessAnimationForAllTracking(level, pos, actionType, (byte) 0, (byte) 0);
	}

	public static void setConnectFourAnimationForAllTracking(Level level, BlockPos pos, byte destCord)
	{
		TTCNetwork.CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(pos)), new MessageClientConnectFourAnimationState(pos, destCord));
	}

	public static void openChessPromotionFromServer(BlockPos pos, boolean isWhite, ServerPlayer serverPlayer)
	{
		TTCNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new MessageClientOpenChessPromotionScreen(pos, isWhite));
	}

	public static void playChesTimerSoundFromServer(Level level, BlockPos pos, byte id)
	{
		TTCNetwork.CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(pos)), new MessageClientPlayChessTimerSound(pos, id));
	}

	public static void playChesParticlesFromServer(Level level, BlockPos pos, byte destCord, boolean isWhite, float xSpeed, float ySpeed, float zSpeed)
	{
		TTCNetwork.CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(pos)), new MessageClientChessParticles(pos, destCord, isWhite, xSpeed, ySpeed, zSpeed));
	}

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
	 * @param colorType The color that will be set, 0 for tile info, 1 for legal move
	 * @param pos The BlockPos of the ChessBlockEntity
	 * @param color The Color
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
	 * @param colorType The colors that will be set, 0 for board tiles, 1 for pieces
	 * @param pos The BlockPos of the ChessBlockEntity
	 * @param color The first Color for the White side
	 * @param color2 The second Color for the Black side
	 */
	@OnlyIn(Dist.CLIENT)
	public static void setColorsMessage(int colorType, BlockPos pos, String color, String color2)
	{
		TTCNetwork.CHANNEL.sendToServer(new MessageServerSetColors(colorType, pos, color, color2));
	}

	@OnlyIn(Dist.CLIENT)
	public static void rotateChessPieceFigure(BlockPos pos)
	{
		TTCNetwork.CHANNEL.sendToServer(new MessageServerRotateChessPieceFigure(pos));
	}

	@OnlyIn(Dist.CLIENT)
	public static void doChessBoardInteraction(BlockPos pos, byte tileCoordinate)
	{
		TTCNetwork.CHANNEL.sendToServer(new MessageServerDoChessBoardInteraction(pos, tileCoordinate));
	}

	@OnlyIn(Dist.CLIENT)
	public static void setChessPieceSet(BlockPos pos, int pieceSet)
	{
		TTCNetwork.CHANNEL.sendToServer(new MessageServerSetPieceSet(pos, pieceSet));
	}

	@OnlyIn(Dist.CLIENT)
	public static void changePieceSet(BlockPos pos, byte value)
	{
		TTCNetwork.CHANNEL.sendToServer(new MessageServerChangePieceSet(pos, value));
	}

	@OnlyIn(Dist.CLIENT)
	public static void changePieceType(BlockPos pos, byte value)
	{
		TTCNetwork.CHANNEL.sendToServer(new MessageServerChangePieceType(pos, value));
	}

	@OnlyIn(Dist.CLIENT)
	public static void setPieceScale(BlockPos pos, double value)
	{
		TTCNetwork.CHANNEL.sendToServer(new MessageServerChangePieceScale(pos, value));
	}

	@OnlyIn(Dist.CLIENT)
	public static void openGuiWithServerPlayer(BlockPos pos)
	{
		TTCNetwork.CHANNEL.sendToServer(new MessageServerOpenGUIWithServerPlayer(pos));
	}

	@OnlyIn(Dist.CLIENT)
	public static void adjustChessTimerTime(BlockPos pos, byte type, byte category)
	{
		TTCNetwork.CHANNEL.sendToServer(new MessageServerAdjustChessTimerTime(pos, type, category));
	}

	/**
	 * Used to toggle the Piece Animations and Particle options for Chess tables
	 * @param pos Chess BlockPos
	 * @param type Which one to switch. 0 = Animations and 1 = Particles
	 */
	@OnlyIn(Dist.CLIENT)
	public static void toggleChessVisuals(BlockPos pos, byte type)
	{
		TTCNetwork.CHANNEL.sendToServer(new MessageServerChessVisuals(pos, type));
	}

	@OnlyIn(Dist.CLIENT)
	public static void doPawnPromotion(BlockPos pos, byte type)
	{
		TTCNetwork.CHANNEL.sendToServer(new MessageServerDoPawnPromotion(pos, type));
	}

	@OnlyIn(Dist.CLIENT)
	public static void resetChessTimerTime(BlockPos pos)
	{
		TTCNetwork.CHANNEL.sendToServer(new MessageServerResetChessTimer(pos));
	}

	@OnlyIn(Dist.CLIENT)
	public static void pauseChessTimerTime(BlockPos pos)
	{
		TTCNetwork.CHANNEL.sendToServer(new MessageServerPauseChessTimer(pos));
	}

	@OnlyIn(Dist.CLIENT)
	public static void doConnectFourInteraction(BlockPos pos, byte column)
	{
		TTCNetwork.CHANNEL.sendToServer(new MessageServerDoConnectFourInteraction(pos, column));
	}
}