package andrews.table_top_craft.network.server;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.pgn.FenUtil;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.Reference;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.apache.commons.lang3.StringUtils;

public class MessageServerLoadFEN
{
	public static ResourceLocation PACKET_ID = new ResourceLocation(Reference.MODID, "load_fen_packet");

	public static void registerPacket()
	{
		ServerPlayNetworking.registerGlobalReceiver(PACKET_ID, (minecraftServer, serverPlayer, packetListener, buf, packetSender) ->
		{
			BlockPos pos = buf.readBlockPos();
			String FEN = buf.readUtf(Short.MAX_VALUE);

			minecraftServer.execute(() ->
			{
				if(serverPlayer == null)
					return;

				Level level = serverPlayer.getLevel();
				if(level != null)
				{
					BlockEntity blockEntity = level.getBlockEntity(pos);
					// We make sure the TileEntity is a ChessTileEntity
					if(blockEntity instanceof ChessTileEntity chessTileEntity)
			        {
						Board board = Board.createStandardBoard();
						if(isFENValid(FEN))
						{
							board = FenUtil.createGameFromFEN(FEN);
						}
						else
						{
							serverPlayer.sendSystemMessage(Component.translatable("message.table_top_craft.chess.invalidFEN").withStyle(ChatFormatting.RED));//TODO make sure this works
						}
						chessTileEntity.setBoard(board);
						chessTileEntity.getMoveLog().clear();
						level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
			        }
				}
			});
		});
	}
	
	private static boolean isFENValid(String FEN)
	{
		String[] FENInfo = FEN.split(" ");
		String[] boardTileInfo = FENInfo[0].split("/");
		int splitCounter = StringUtils.countMatches(FENInfo[0], "/");
		int whiteKingCounter = StringUtils.countMatches(FENInfo[0], "K");
		int blackKingCounter = StringUtils.countMatches(FENInfo[0], "k");
		
		if(!(FENInfo.length == 6))
			return false;
		if(!(splitCounter == 7))
			return false;
		for(int i = 0; i < 8; i++)
		{
			String rowInfo = boardTileInfo[i];
			int valueCounter = 0;
			
			for(int j = 0; j < rowInfo.length(); j++)
			{
				if(rowInfo.charAt(j) == '1')
					valueCounter += 1;
				else if(rowInfo.charAt(j) == '2')
					valueCounter += 2;
				else if(rowInfo.charAt(j) == '3')
					valueCounter += 3;
				else if(rowInfo.charAt(j) == '4')
					valueCounter += 4;
				else if(rowInfo.charAt(j) == '5')
					valueCounter += 5;
				else if(rowInfo.charAt(j) == '6')
					valueCounter += 6;
				else if(rowInfo.charAt(j) == '7')
					valueCounter += 7;
				else if(rowInfo.charAt(j) == '8')
					valueCounter += 8;
				else
					valueCounter += 1;
			}
			
			if(!(valueCounter == 8))
				return false;
		}
		if(!(whiteKingCounter == 1))
			return false;
		if(!(blackKingCounter == 1))
			return false;
		return FENInfo[1].equals("w") || FENInfo[1].equals("b");
	}
}