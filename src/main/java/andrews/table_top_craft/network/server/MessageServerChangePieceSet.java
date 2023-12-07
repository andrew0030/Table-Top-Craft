package andrews.table_top_craft.network.server;

import andrews.table_top_craft.block_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.util.Reference;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MessageServerChangePieceSet
{
    public static ResourceLocation PACKET_ID = new ResourceLocation(Reference.MODID, "change_piece_set_packet");

    public static void registerPacket()
    {
        ServerPlayNetworking.registerGlobalReceiver(PACKET_ID, (minecraftServer, serverPlayer, packetListener, buf, packetSender) ->
        {
            BlockPos pos = buf.readBlockPos();
            byte value = buf.readByte();

            minecraftServer.execute(() ->
            {
                if(serverPlayer == null)
                    return;

                Level level = serverPlayer.level();
                if(level != null)
                {
                    BlockEntity blockEntity = level.getBlockEntity(pos);
                    // We make sure the TileEntity is a ChessPieceFigureBlockEntity
                    if(blockEntity instanceof ChessPieceFigureBlockEntity chessPieceFigureBlockEntity)
                    {
                        int currentSet = chessPieceFigureBlockEntity.getPieceSet();
                        if(value > 0)
                        {
                            if (currentSet < 3)
                                chessPieceFigureBlockEntity.setPieceSet(++currentSet);
                            else
                                chessPieceFigureBlockEntity.setPieceSet(1);
                        }
                        else if(value < 0)
                        {
                            if (currentSet > 1)
                                chessPieceFigureBlockEntity.setPieceSet(--currentSet);
                            else
                                chessPieceFigureBlockEntity.setPieceSet(3);
                        }
                        level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
                        chessPieceFigureBlockEntity.setChanged();
                    }
                }
            });
        });
    }
}