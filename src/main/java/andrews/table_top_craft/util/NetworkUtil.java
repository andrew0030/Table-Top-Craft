package andrews.table_top_craft.util;

import andrews.table_top_craft.network.client.MessageClientOpenChessPieceSelectionScreen;
import andrews.table_top_craft.network.server.*;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
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
}