package andrews.table_top_craft.network.server;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.pgn.FenUtil;
import andrews.table_top_craft.util.NetworkUtil;
import andrews.table_top_craft.util.Reference;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;

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

				ServerLevel level = serverPlayer.getLevel();
				if(level != null)
				{
					BlockEntity blockEntity = level.getBlockEntity(pos);
					// We make sure the TileEntity is a ChessBlockEntity
					if(blockEntity instanceof ChessBlockEntity chessBlockEntity)
			        {
						Board board = Board.createStandardBoard();
						if(FenUtil.isFENValid(FEN))
						{
							board = FenUtil.createGameFromFEN(FEN);
						}
						else
						{
							serverPlayer.sendSystemMessage(Component.translatable("message.table_top_craft.chess.invalidFEN").withStyle(ChatFormatting.RED));
						}
						chessBlockEntity.setBoard(board);
						chessBlockEntity.getMoveLog().clear();
						chessBlockEntity.setHumanMovedPiece(null);
						chessBlockEntity.setSourceTile(null);
						chessBlockEntity.setWaitingForPromotion(false);
						chessBlockEntity.setPromotionCoordinate((byte) -1);
						chessBlockEntity.doingAnimationTimer = 0;
						chessBlockEntity.move = null;
						chessBlockEntity.transition = null;
						level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
						// We start the placed Animation on server and client
						NetworkUtil.setChessAnimationForAllTracking(level, pos, (byte) 0);
			        }
				}
			});
		});
	}
}