package andrews.table_top_craft.network.server;

import andrews.table_top_craft.criteria.TTCCriteriaTriggers;
import andrews.table_top_craft.tile_entities.ConnectFourBlockEntity;
import andrews.table_top_craft.util.NetworkUtil;
import andrews.table_top_craft.util.Reference;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MessageServerDoConnectFourInteraction
{
    public static ResourceLocation PACKET_ID = new ResourceLocation(Reference.MODID, "do_connect_four_interaction_packet");

    public static void registerPacket()
    {
        ServerPlayNetworking.registerGlobalReceiver(PACKET_ID, (minecraftServer, serverPlayer, packetListener, buf, packetSender) ->
        {
            BlockPos pos = buf.readBlockPos();
            byte column = buf.readByte();

            minecraftServer.execute(() ->
            {
                if(serverPlayer == null)
                    return;

                ServerLevel level = serverPlayer.getLevel();
                if(level != null)
                {
                    BlockEntity blockEntity = level.getBlockEntity(pos);
                    if(blockEntity instanceof ConnectFourBlockEntity connectFourBlockEntity)
                    {
                        connectFourBlockEntity.addPieceToColumn(column);
                        level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
                        connectFourBlockEntity.setChanged();

                        if(!level.isClientSide() && connectFourBlockEntity.hasFourInARow())
                            TTCCriteriaTriggers.MAKE_CONNECT_FOUR_VICTORY_MOVE.trigger(serverPlayer);

                        NetworkUtil.setConnectFourAnimationForAllTracking(level, pos, (byte) (connectFourBlockEntity.getTopPieceInColumn(column) + 6 * column));
                    }
                }
            });
        });
    }
}