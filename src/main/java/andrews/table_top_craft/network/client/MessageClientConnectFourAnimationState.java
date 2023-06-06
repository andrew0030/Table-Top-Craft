package andrews.table_top_craft.network.client;

import andrews.table_top_craft.network.client.util.ClientPacketHandlerClass;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageClientConnectFourAnimationState
{
    public BlockPos pos;
    public byte destCord;


    public MessageClientConnectFourAnimationState(BlockPos pos, byte destCord)
    {
        this.pos = pos;
        this.destCord = destCord;
    }

    public void serialize(FriendlyByteBuf buf)
    {
        buf.writeBlockPos(this.pos);
        buf.writeByte(this.destCord);
    }

    public static MessageClientConnectFourAnimationState deserialize(FriendlyByteBuf buf)
    {
        BlockPos pos = buf.readBlockPos();
        byte destCord = buf.readByte();
        return new MessageClientConnectFourAnimationState(pos, destCord);
    }

    public static void handle(MessageClientConnectFourAnimationState message, Supplier<NetworkEvent.Context> ctx)
    {
        NetworkEvent.Context context = ctx.get();
        if(context.getDirection().getReceptionSide() == LogicalSide.CLIENT)
        {
            context.enqueueWork(() -> {
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandlerClass.handleConnectFourAnimationPacket(message, ctx));
            });
            context.setPacketHandled(true);
        }
    }
}
