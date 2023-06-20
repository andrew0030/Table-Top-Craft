package andrews.table_top_craft.util;

import andrews.table_top_craft.network.client.*;
import andrews.table_top_craft.network.server.*;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class NetworkUtil
{
	public static void openChessPieceSelectionFromServer(BlockPos pos, boolean isStandardSetUnlocked, boolean isClassicSetUnlocked, boolean isPandorasCreaturesSetUnlocked, ServerPlayer serverPlayer)
	{
		FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
		passedData.writeBlockPos(pos);
		passedData.writeBoolean(isStandardSetUnlocked);
		passedData.writeBoolean(isClassicSetUnlocked);
		passedData.writeBoolean(isPandorasCreaturesSetUnlocked);
		ServerPlayNetworking.send(serverPlayer, MessageClientOpenChessPieceSelectionScreen.PACKET_ID, passedData);
	}

	public static void setChessAnimationForAllTracking(ServerLevel level, BlockPos pos, byte actionType, byte currentCord, byte destCord)
	{
		FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
		passedData.writeBlockPos(pos);
		passedData.writeByte(actionType);
		passedData.writeByte(currentCord);
		passedData.writeByte(destCord);
		for (ServerPlayer player : PlayerLookup.tracking(level, pos))
			ServerPlayNetworking.send(player, MessageClientChessAnimationState.PACKET_ID, passedData);
	}

	public static void setChessAnimationForAllTracking(ServerLevel level, BlockPos pos, byte actionType)
	{
		NetworkUtil.setChessAnimationForAllTracking(level, pos, actionType, (byte) 0, (byte) 0);
	}

	public static void setConnectFourAnimationForAllTracking(ServerLevel level, BlockPos pos, byte destCord)
	{
		FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
		passedData.writeBlockPos(pos);
		passedData.writeByte(destCord);
		for (ServerPlayer player : PlayerLookup.tracking(level, pos))
			ServerPlayNetworking.send(player, MessageClientConnectFourAnimationState.PACKET_ID, passedData);
	}

	public static void openChessPromotionFromServer(BlockPos pos, boolean isWhite, ServerPlayer serverPlayer)
	{
		FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
		passedData.writeBlockPos(pos);
		passedData.writeBoolean(isWhite);
		ServerPlayNetworking.send(serverPlayer, MessageClientOpenChessPromotionScreen.PACKET_ID, passedData);
	}

	public static void playChesTimerSoundFromServer(ServerLevel level, BlockPos pos, byte id)
	{
		FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
		passedData.writeBlockPos(pos);
		passedData.writeByte(id);
		for (ServerPlayer player : PlayerLookup.tracking(level, pos))
			ServerPlayNetworking.send(player, MessageClientPlayChessTimerSound.PACKET_ID, passedData);
	}

	public static void playChesParticlesFromServer(ServerLevel level, BlockPos pos, byte destCord, boolean isWhite, float xSpeed, float ySpeed, float zSpeed)
	{
		FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
		passedData.writeBlockPos(pos);
		passedData.writeByte(destCord);
		passedData.writeBoolean(isWhite);
		passedData.writeFloat(xSpeed);
		passedData.writeFloat(ySpeed);
		passedData.writeFloat(zSpeed);
		for (ServerPlayer player : PlayerLookup.tracking(level, pos))
			ServerPlayNetworking.send(player, MessageClientChessParticles.PACKET_ID, passedData);
	}

	public static void newChessGameMessage(BlockPos pos)
	{
		FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
		passedData.writeBlockPos(pos);
		ClientPlayNetworking.send(MessageServerNewChessGame.PACKET_ID, passedData);
	}

	public static void showTileInfoMessage(BlockPos pos)
	{
		FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
		passedData.writeBlockPos(pos);
		ClientPlayNetworking.send(MessageServerShowTileInfo.PACKET_ID, passedData);
	}

	public static void loadFENMessage(BlockPos pos, String FEN)
	{
		FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
		passedData.writeBlockPos(pos);
		passedData.writeUtf(FEN);
		ClientPlayNetworking.send(MessageServerLoadFEN.PACKET_ID, passedData);
	}

	public static void showAvailableMovesMessage(BlockPos pos)
	{
		FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
		passedData.writeBlockPos(pos);
		ClientPlayNetworking.send(MessageServerShowAvailableMoves.PACKET_ID, passedData);
	}

	public static void showPreviousMoveMessage(BlockPos pos)
	{
		FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
		passedData.writeBlockPos(pos);
		ClientPlayNetworking.send(MessageServerShowPreviousMove.PACKET_ID, passedData);
	}

	public static void setColorMessage(int colorType, BlockPos pos, String color)
	{
		FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
		passedData.writeInt(colorType);
		passedData.writeBlockPos(pos);
		passedData.writeUtf(color);
		ClientPlayNetworking.send(MessageServerSetColor.PACKET_ID, passedData);
	}

	public static void useCustomPlateMessage(BlockPos pos)
	{
		FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
		passedData.writeBlockPos(pos);
		ClientPlayNetworking.send(MessageServerUseCustomPlate.PACKET_ID, passedData);
	}

	public static void setColorsMessage(int colorType, BlockPos pos, String color, String color2)
	{
		FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
		passedData.writeInt(colorType);
		passedData.writeBlockPos(pos);
		passedData.writeUtf(color);
		passedData.writeUtf(color2);
		ClientPlayNetworking.send(MessageServerSetColors.PACKET_ID, passedData);
	}

	public static void rotateChessPieceFigure(BlockPos pos)
	{
		FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
		passedData.writeBlockPos(pos);
		ClientPlayNetworking.send(MessageServerRotateChessPieceFigure.PACKET_ID, passedData);
	}

	public static void doChessBoardInteraction(BlockPos pos, byte tileCoordinate)
	{
		FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
		passedData.writeBlockPos(pos);
		passedData.writeByte(tileCoordinate);
		ClientPlayNetworking.send(MessageServerDoChessBoardInteraction.PACKET_ID, passedData);
	}

	public static void setChessPieceSet(BlockPos pos, int pieceSet)
	{
		FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
		passedData.writeBlockPos(pos);
		passedData.writeInt(pieceSet);
		ClientPlayNetworking.send(MessageServerSetPieceSet.PACKET_ID, passedData);
	}

	public static void changePieceSet(BlockPos pos, byte value)
	{
		FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
		passedData.writeBlockPos(pos);
		passedData.writeByte(value);
		ClientPlayNetworking.send(MessageServerChangePieceSet.PACKET_ID, passedData);
	}

	public static void changePieceType(BlockPos pos, byte value)
	{
		FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
		passedData.writeBlockPos(pos);
		passedData.writeByte(value);
		ClientPlayNetworking.send(MessageServerChangePieceType.PACKET_ID, passedData);
	}

	public static void setPieceScale(BlockPos pos, double value)
	{
		FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
		passedData.writeBlockPos(pos);
		passedData.writeDouble(value);
		ClientPlayNetworking.send(MessageServerChangePieceScale.PACKET_ID, passedData);
	}

	public static void openGuiWithServerPlayer(BlockPos pos)
	{
		FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
		passedData.writeBlockPos(pos);
		ClientPlayNetworking.send(MessageServerOpenGUIWithServerPlayer.PACKET_ID, passedData);
	}

	public static void adjustChessTimerTime(BlockPos pos, byte type, byte category)
	{
		FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
		passedData.writeBlockPos(pos);
		passedData.writeByte(type);
		passedData.writeByte(category);
		ClientPlayNetworking.send(MessageServerAdjustChessTimerTime.PACKET_ID, passedData);
	}

	/**
	 * Used to toggle the Piece Animations and Particle options for Chess tables
	 * @param pos Chess BlockPos
	 * @param type Which one to switch. 0 = Animations and 1 = Particles
	 */
	public static void toggleChessVisuals(BlockPos pos, byte type)
	{
		FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
		passedData.writeBlockPos(pos);
		passedData.writeByte(type);
		ClientPlayNetworking.send(MessageServerChessVisuals.PACKET_ID, passedData);
	}

	public static void doPawnPromotion(BlockPos pos, byte type)
	{
		FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
		passedData.writeBlockPos(pos);
		passedData.writeByte(type);
		ClientPlayNetworking.send(MessageServerDoPawnPromotion.PACKET_ID, passedData);
	}

	public static void resetChessTimerTime(BlockPos pos)
	{
		FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
		passedData.writeBlockPos(pos);
		ClientPlayNetworking.send(MessageServerResetChessTimer.PACKET_ID, passedData);
	}

	public static void pauseChessTimerTime(BlockPos pos)
	{
		FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
		passedData.writeBlockPos(pos);
		ClientPlayNetworking.send(MessageServerPauseChessTimer.PACKET_ID, passedData);
	}

	public static void doConnectFourInteraction(BlockPos pos, byte column)
	{
		FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
		passedData.writeBlockPos(pos);
		passedData.writeByte(column);
		ClientPlayNetworking.send(MessageServerDoConnectFourInteraction.PACKET_ID, passedData);
	}
}