package andrews.table_top_craft.network.server;

import andrews.table_top_craft.screens.chess_timer.buttons.ChessTimerTimeAlteringButton.TimeCategory;
import andrews.table_top_craft.screens.chess_timer.buttons.ChessTimerTimeAlteringButton.TimeModifierType;
import andrews.table_top_craft.tile_entities.ChessTimerBlockEntity;
import andrews.table_top_craft.util.Reference;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Arrays;

public class MessageServerAdjustChessTimerTime
{
    public static ResourceLocation PACKET_ID = new ResourceLocation(Reference.MODID, "adjust_chess_timer_time_packet");

    public static void registerPacket()
    {
        ServerPlayNetworking.registerGlobalReceiver(PACKET_ID, (minecraftServer, serverPlayer, packetListener, buf, packetSender) ->
        {
            BlockPos pos = buf.readBlockPos();
            byte typeMsg = buf.readByte();
            byte categoryMsg = buf.readByte();
            TimeModifierType type = Arrays.stream(TimeModifierType.values()).filter(e -> e.getIdx() == typeMsg).findFirst().orElse(null);
            TimeCategory category = Arrays.stream(TimeCategory.values()).filter(e -> e.getIdx() == categoryMsg).findFirst().orElse(null);

            minecraftServer.execute(() ->
            {
                if(serverPlayer == null) return;

                Level level = serverPlayer.getLevel();
                if(level != null)
                {
                    BlockEntity blockEntity = level.getBlockEntity(pos);
                    if (blockEntity instanceof ChessTimerBlockEntity chessTimerBlockEntity) {
                        if (category.getIdx() < 3) {
                            chessTimerBlockEntity.modifyLeftTimer(type.getModifier() * category.getMillis());
                        } else {
                            chessTimerBlockEntity.modifyRightTimer(type.getModifier() * category.getMillis());
                        }
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