package andrews.table_top_craft.network.server;

import andrews.table_top_craft.tile_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
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

				Level level = serverPlayer.getLevel();
				if(level != null)
				{
					BlockEntity blockEntity = level.getBlockEntity(pos);
					// We make sure the TileEntity is a ChessTileEntity
					if(blockEntity instanceof ChessTileEntity chessTileEntity)
			        {
						switch(colorType)
						{
							case 0 -> chessTileEntity.setTileInfoColor(color);
							case 1 -> chessTileEntity.setLegalMoveColor(color);
							case 2 -> chessTileEntity.setInvalidMoveColor(color);
							case 3 -> chessTileEntity.setAttackMoveColor(color);
							case 4 -> chessTileEntity.setPreviousMoveColor(color);
							case 5 -> chessTileEntity.setCastleMoveColor(color);
						}
						level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
						chessTileEntity.setChanged();
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