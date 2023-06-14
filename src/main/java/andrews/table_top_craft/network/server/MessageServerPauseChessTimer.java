package andrews.table_top_craft.network.server;

import andrews.table_top_craft.objects.blocks.ChessTimerBlock;
import andrews.table_top_craft.util.NetworkUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageServerPauseChessTimer
{
    private final BlockPos pos;

    public MessageServerPauseChessTimer(BlockPos pos)
    {
        this.pos = pos;
    }

    public void serialize(FriendlyByteBuf buf)
    {
        buf.writeBlockPos(pos);
    }

    public static MessageServerPauseChessTimer deserialize(FriendlyByteBuf buf)
    {
        BlockPos pos = buf.readBlockPos();
        return new MessageServerPauseChessTimer(pos);
    }

    public static void handle(MessageServerPauseChessTimer message, Supplier<NetworkEvent.Context> ctx)
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
                    level.setBlock(pos, level.getBlockState(pos).setValue(ChessTimerBlock.PRESSED_BUTTON, ChessTimerBlock.PressedButton.NONE), 2);
                    NetworkUtil.playChesTimerSoundFromServer(level, pos, (byte) 0);
                }
            });
            context.setPacketHandled(true);
        }
    }
}