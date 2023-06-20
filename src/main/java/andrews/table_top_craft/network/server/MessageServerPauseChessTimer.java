package andrews.table_top_craft.network.server;

import andrews.table_top_craft.objects.blocks.ChessTimerBlock;
import andrews.table_top_craft.util.NetworkUtil;
import andrews.table_top_craft.util.Reference;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

public class MessageServerPauseChessTimer
{
    public static ResourceLocation PACKET_ID = new ResourceLocation(Reference.MODID, "pause_chess_timer_packet");

    public static void registerPacket()
    {
        ServerPlayNetworking.registerGlobalReceiver(PACKET_ID, (minecraftServer, serverPlayer, packetListener, buf, packetSender) ->
        {
            BlockPos pos = buf.readBlockPos();

            minecraftServer.execute(() ->
            {
                if(serverPlayer == null)
                    return;

                ServerLevel level = serverPlayer.getLevel();
                if(level != null)
                {
                    level.setBlock(pos, level.getBlockState(pos).setValue(ChessTimerBlock.PRESSED_BUTTON, ChessTimerBlock.PressedButton.NONE), 2);
                    NetworkUtil.playChesTimerSoundFromServer(level, pos, (byte) 0);
                }
            });
        });
    }
}