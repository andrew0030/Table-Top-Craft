package andrews.table_top_craft.network.server;

import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.Reference;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MessageServerSetPieceSet
{
    public static ResourceLocation PACKET_ID = new ResourceLocation(Reference.MODID, "set_piece_set_packet");

    public static void registerPacket()
    {
        ServerPlayNetworking.registerGlobalReceiver(PACKET_ID, (minecraftServer, serverPlayer, packetListener, buf, packetSender) ->
        {
            BlockPos pos = buf.readBlockPos();
            int pieceSet = buf.readInt();

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
                        switch(pieceSet)
                        {
                            case 0 -> chessTileEntity.setPieceSet(0);
                            case 1 -> chessTileEntity.setPieceSet(1);
                            case 2 -> chessTileEntity.setPieceSet(2);
                        }
                        level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
                        chessTileEntity.setChanged();
                    }
                }
            });
        });
    }
}