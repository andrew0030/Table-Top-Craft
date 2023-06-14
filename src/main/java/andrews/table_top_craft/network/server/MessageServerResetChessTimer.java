package andrews.table_top_craft.network.server;

import andrews.table_top_craft.block_entities.ChessTimerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageServerResetChessTimer
{
    private final BlockPos pos;

    public MessageServerResetChessTimer(BlockPos pos)
    {
        this.pos = pos;
    }

    public void serialize(FriendlyByteBuf buf)
    {
        buf.writeBlockPos(pos);
    }

    public static MessageServerResetChessTimer deserialize(FriendlyByteBuf buf)
    {
        BlockPos pos = buf.readBlockPos();
        return new MessageServerResetChessTimer(pos);
    }

    public static void handle(MessageServerResetChessTimer message, Supplier<NetworkEvent.Context> ctx)
    {
        NetworkEvent.Context context = ctx.get();
        Player player = context.getSender();
        BlockPos pos = message.pos;

        if(context.getDirection().getReceptionSide() == LogicalSide.SERVER)
        {
            context.enqueueWork(() ->
            {
                if(player != null)
                {
                    Level level = player.getLevel();
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
            context.setPacketHandled(true);
        }
    }
}