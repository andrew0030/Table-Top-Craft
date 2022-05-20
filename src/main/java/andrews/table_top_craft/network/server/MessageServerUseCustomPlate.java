package andrews.table_top_craft.network.server;

import andrews.table_top_craft.objects.blocks.ChessBlock;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.Reference;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MessageServerUseCustomPlate
{
	public static ResourceLocation PACKET_ID = new ResourceLocation(Reference.MODID, "use_custom_plate_packet");

	public static void registerPacket()
	{
		ServerPlayNetworking.registerGlobalReceiver(PACKET_ID, (minecraftServer, serverPlayer, packetListener, buf, packetSender) ->
		{
			BlockPos pos = buf.readBlockPos();

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
						if(chessTileEntity.getUseCustomPlate())
						{
							chessTileEntity.setUseCustomPlate(false);
							level.setBlockAndUpdate(pos, level.getBlockState(pos).setValue(ChessBlock.SHOW_PLATE, true));
						}
						else
						{
							chessTileEntity.setUseCustomPlate(true);
							level.setBlockAndUpdate(pos, level.getBlockState(pos).setValue(ChessBlock.SHOW_PLATE, false));
						}
						level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
			        }
				}
			});
		});
	}
}