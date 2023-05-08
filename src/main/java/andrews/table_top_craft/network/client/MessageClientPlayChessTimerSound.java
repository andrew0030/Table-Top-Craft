package andrews.table_top_craft.network.client;

import andrews.table_top_craft.network.client.util.ClientPacketHandlerClass;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageClientPlayChessTimerSound
{
    public BlockPos pos;
    public byte id;

    public MessageClientPlayChessTimerSound(BlockPos pos, byte id)
    {
        this.pos = pos;
        this.id = id;
    }

    public void serialize(FriendlyByteBuf buf)
    {
        buf.writeBlockPos(this.pos);
        buf.writeByte(this.id);
    }

    public static MessageClientPlayChessTimerSound deserialize(FriendlyByteBuf buf)
    {
        BlockPos pos = buf.readBlockPos();
        byte id = buf.readByte();
        return new MessageClientPlayChessTimerSound(pos, id);
    }

    public static void handle(MessageClientPlayChessTimerSound message, Supplier<NetworkEvent.Context> ctx)
    {
        NetworkEvent.Context context = ctx.get();
        if(context.getDirection().getReceptionSide() == LogicalSide.CLIENT)
        {
            context.enqueueWork(() ->
            {
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandlerClass.handlePlayChessTimerSoundPacket(message, ctx));
            });
            context.setPacketHandled(true);
        }
    }
}