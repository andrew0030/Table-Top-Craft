package andrews.table_top_craft.network.server;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.util.Reference;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MessageServerSetColors
{
	public static ResourceLocation PACKET_ID = new ResourceLocation(Reference.MODID, "set_colors_packet");

	public static void registerPacket()
	{
		ServerPlayNetworking.registerGlobalReceiver(PACKET_ID, (minecraftServer, serverPlayer, packetListener, buf, packetSender) ->
		{
			int colorType = buf.readInt();
			BlockPos pos = buf.readBlockPos();
			String color = buf.readUtf(Short.MAX_VALUE);
			String color2 = buf.readUtf(Short.MAX_VALUE);

			minecraftServer.execute(() ->
			{
				if(serverPlayer == null)
					return;

				Level level = serverPlayer.getLevel();
				if(level != null)
				{
					BlockEntity blockEntity = level.getBlockEntity(pos);
					// We make sure the TileEntity is a ChessBlockEntity
					if(blockEntity instanceof ChessBlockEntity chessBlockEntity)
			        {
						switch (colorType)
						{
							case 0 ->
							{
								chessBlockEntity.setWhiteTilesColor(color);
								chessBlockEntity.setBlackTilesColor(color2);
							}
							case 1 ->
							{
								chessBlockEntity.setWhitePiecesColor(color);
								chessBlockEntity.setBlackPiecesColor(color2);
							}
						}
						level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
						chessBlockEntity.setChanged();
			        }
				}
			});
		});
	}
}