package andrews.table_top_craft.network.server;

import andrews.table_top_craft.tile_entities.ChessTimerBlockEntity;
import andrews.table_top_craft.util.Reference;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MessageServerResetChessTimer
{
    public static ResourceLocation PACKET_ID = new ResourceLocation(Reference.MODID, "reset_chess_timer_packet");

    public static void registerPacket()
    {
        ServerPlayNetworking.registerGlobalReceiver(PACKET_ID, (minecraftServer, serverPlayer, packetListener, buf, packetSender) ->
        {
            BlockPos pos = buf.readBlockPos();

            minecraftServer.execute(() ->
            {
                if(serverPlayer == null) return;

                Level level = serverPlayer.getLevel();
                if(level != null)
                {
                    BlockEntity blockEntity = level.getBlockEntity(pos);
                    if(blockEntity instanceof ChessTimerBlockEntity chessTimerBlockEntity)
                    {
                        chessTimerBlockEntity.resetTimers();
                        chessTimerBlockEntity.lastSwitchTime = 0;
                        level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
                        level.updateNeighborsAt(pos, level.getBlockState(pos).getBlock());
                        chessTimerBlockEntity.setChanged();
                    }
                }
            });
        });
    }
}