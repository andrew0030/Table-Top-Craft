package andrews.table_top_craft.network.server;

import andrews.table_top_craft.screens.chess_timer.buttons.ChessTimerTimeAlteringButton.TimeCategory;
import andrews.table_top_craft.screens.chess_timer.buttons.ChessTimerTimeAlteringButton.TimeModifierType;
import andrews.table_top_craft.block_entities.ChessTimerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.Arrays;
import java.util.function.Supplier;

public class MessageServerAdjustChessTimerTime
{
    private final BlockPos pos;
    private final byte type;
    private final byte category;

    public MessageServerAdjustChessTimerTime(BlockPos pos, byte type, byte category)
    {
        this.pos = pos;
        this.type = type;
        this.category = category;
    }

    public void serialize(FriendlyByteBuf buf)
    {
        buf.writeBlockPos(pos);
        buf.writeByte(type);
        buf.writeByte(category);
    }

    public static MessageServerAdjustChessTimerTime deserialize(FriendlyByteBuf buf)
    {
        BlockPos pos = buf.readBlockPos();
        byte type = buf.readByte();
        byte category = buf.readByte();
        return new MessageServerAdjustChessTimerTime(pos, type, category);
    }

    public static void handle(MessageServerAdjustChessTimerTime message, Supplier<NetworkEvent.Context> ctx)
    {
        NetworkEvent.Context context = ctx.get();
        Player player = context.getSender();
        BlockPos pos = message.pos;
        TimeModifierType type = Arrays.stream(TimeModifierType.values()).filter(e -> e.getIdx() == message.type).findFirst().orElse(null);
        TimeCategory category = Arrays.stream(TimeCategory.values()).filter(e -> e.getIdx() == message.category).findFirst().orElse(null);

        if(context.getDirection().getReceptionSide() == LogicalSide.SERVER)
        {
            context.enqueueWork(() ->
            {
                if(player != null && type != null && category != null)
                {
                    Level level = player.getLevel();
                    BlockEntity blockEntity = level.getBlockEntity(pos);
                    if(blockEntity instanceof ChessTimerBlockEntity chessTimerBlockEntity)
                    {
                        if(category.getIdx() < 3) {
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
            context.setPacketHandled(true);
        }
    }
}