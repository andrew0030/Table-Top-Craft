package andrews.table_top_craft.network.client;

import andrews.table_top_craft.network.client.util.ClientPacketHandlerClass;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageClientChessAnimationState
{
    public BlockPos pos;
    public byte actionType;
    public byte currentCord;
    public byte destCord;


    public MessageClientChessAnimationState(BlockPos pos, byte actionType, byte currentCord, byte destCord)
    {
        this.pos = pos;
        this.actionType = actionType;
        this.currentCord = currentCord;
        this.destCord = destCord;
    }

    public void serialize(FriendlyByteBuf buf)
    {
        buf.writeBlockPos(this.pos);
        buf.writeByte(this.actionType);
        buf.writeByte(this.currentCord);
        buf.writeByte(this.destCord);
    }

    public static MessageClientChessAnimationState deserialize(FriendlyByteBuf buf)
    {
        BlockPos pos = buf.readBlockPos();
        byte actionType = buf.readByte();
        byte currentCord = buf.readByte();
        byte destCord = buf.readByte();
        return new MessageClientChessAnimationState(pos, actionType, currentCord, destCord);
    }

    public static void handle(MessageClientChessAnimationState message, Supplier<NetworkEvent.Context> ctx)
    {
        NetworkEvent.Context context = ctx.get();
        if(context.getDirection().getReceptionSide() == LogicalSide.CLIENT)
        {
            context.enqueueWork(() ->
            {
                // We have to use this to avoid calling Screen on the server
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandlerClass.handleChessAnimationPacket(message, ctx));
            });
            context.setPacketHandled(true);
        }
    }
}