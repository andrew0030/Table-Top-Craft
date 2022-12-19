package andrews.table_top_craft.network.server;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.pgn.FenUtil;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.Reference;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
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
						if(FenUtil.isFENValid(FEN))
						{
							board = FenUtil.createGameFromFEN(FEN);
						}
						else
						{
							serverPlayer.sendMessage(new TranslatableComponent("message.table_top_craft.chess.invalidFEN").withStyle(ChatFormatting.RED), serverPlayer.getUUID());
						}
						chessTileEntity.setBoard(board);
						chessTileEntity.getMoveLog().clear();
						level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
			        }
				}
			});
		});
	}
}