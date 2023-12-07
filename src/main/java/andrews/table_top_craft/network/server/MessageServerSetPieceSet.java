package andrews.table_top_craft.network.server;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.util.NetworkUtil;
import andrews.table_top_craft.util.Reference;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
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

                ServerLevel level = serverPlayer.serverLevel();
                if(level != null)
                {
                    BlockEntity blockEntity = level.getBlockEntity(pos);
                    // We make sure the TileEntity is a ChessBlockEntity
                    if(blockEntity instanceof ChessBlockEntity chessBlockEntity)
                    {
                        switch(pieceSet)
                        {
                            case 0 -> chessBlockEntity.setPieceSet(0);
                            case 1 -> chessBlockEntity.setPieceSet(1);
                            case 2 -> chessBlockEntity.setPieceSet(2);
                        }
                        level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
                        chessBlockEntity.setChanged();
                        NetworkUtil.setChessAnimationForAllTracking(level, pos, (byte) 1);
                    }
                }
            });
        });
    }
}