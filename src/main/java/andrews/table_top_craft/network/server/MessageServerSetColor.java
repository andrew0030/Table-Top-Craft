package andrews.table_top_craft.network.server;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.block_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.util.Reference;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MessageServerSetColor
{
	public static ResourceLocation PACKET_ID = new ResourceLocation(Reference.MODID, "set_color_packet");

	public static void registerPacket()
	{
		ServerPlayNetworking.registerGlobalReceiver(PACKET_ID, (minecraftServer, serverPlayer, packetListener, buf, packetSender) ->
		{
			int colorType = buf.readInt();
			BlockPos pos = buf.readBlockPos();
			String color = buf.readUtf(Short.MAX_VALUE);

			minecraftServer.execute(() ->
			{
				if(serverPlayer == null)
					return;

				Level level = serverPlayer.level();
				if(level != null)
				{
					BlockEntity blockEntity = level.getBlockEntity(pos);
					// We make sure the TileEntity is a ChessBlockEntity
					if(blockEntity instanceof ChessBlockEntity chessBlockEntity)
			        {
						switch(colorType)
						{
							case 0 -> chessBlockEntity.setTileInfoColor(color);
							case 1 -> chessBlockEntity.setLegalMoveColor(color);
							case 2 -> chessBlockEntity.setInvalidMoveColor(color);
							case 3 -> chessBlockEntity.setAttackMoveColor(color);
							case 4 -> chessBlockEntity.setPreviousMoveColor(color);
							case 5 -> chessBlockEntity.setCastleMoveColor(color);
						}
						level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
						chessBlockEntity.setChanged();
			        }
					else if(blockEntity instanceof ChessPieceFigureBlockEntity chessPieceFigureBlockEntity)
					{
						chessPieceFigureBlockEntity.setPieceColor(color);
						level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
						chessPieceFigureBlockEntity.setChanged();
					}
				}
			});
		});
	}
}