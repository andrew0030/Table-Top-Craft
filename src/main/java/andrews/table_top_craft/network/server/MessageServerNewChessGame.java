package andrews.table_top_craft.network.server;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.NetworkUtil;
import andrews.table_top_craft.util.Reference;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MessageServerNewChessGame
{
	public static ResourceLocation PACKET_ID = new ResourceLocation(Reference.MODID, "new_chess_game_packet");

	public static void registerPacket()
	{
		ServerPlayNetworking.registerGlobalReceiver(PACKET_ID, (minecraftServer, serverPlayer, packetListener, buf, packetSender) ->
		{
			BlockPos pos = buf.readBlockPos();

			minecraftServer.execute(() ->
			{
				if(serverPlayer == null)
					return;

				ServerLevel level = serverPlayer.getLevel();
				if(level != null)
				{
					BlockEntity blockEntity = level.getBlockEntity(pos);
					// We make sure the TileEntity is a ChessTileEntity
					if(blockEntity instanceof ChessTileEntity chessTileEntity)
			        {
						Board board = Board.createStandardBoard();
						chessTileEntity.setBoard(board);
						chessTileEntity.getMoveLog().clear();
						chessTileEntity.setHumanMovedPiece(null);
						chessTileEntity.setSourceTile(null);
						chessTileEntity.setWaitingForPromotion(false);
						chessTileEntity.setPromotionCoordinate((byte) -1);
						chessTileEntity.doingAnimationTimer = 0;
						chessTileEntity.move = null;
						chessTileEntity.transition = null;
						level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
						// We start the placed Animation on server and client
						NetworkUtil.setChessAnimationForAllTracking(level, pos, (byte) 0);
			        }
				}
			});
		});
	}
}