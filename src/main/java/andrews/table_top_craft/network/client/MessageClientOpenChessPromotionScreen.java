package andrews.table_top_craft.network.client;

import andrews.table_top_craft.network.client.util.ClientPacketHandlerClass;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageClientOpenChessPromotionScreen
{
    public BlockPos pos;
    public boolean isWhite;

    public MessageClientOpenChessPromotionScreen(BlockPos pos, boolean isWhite)
    {
        this.pos = pos;
        this.isWhite = isWhite;
    }

    public void serialize(FriendlyByteBuf buf)
    {
        buf.writeBlockPos(this.pos);
        buf.writeBoolean(this.isWhite);
    }

    public static MessageClientOpenChessPromotionScreen deserialize(FriendlyByteBuf buf)
    {
        BlockPos pos = buf.readBlockPos();
        boolean isWhite = buf.readBoolean();
        return new MessageClientOpenChessPromotionScreen(pos, isWhite);
    }

    public static void handle(MessageClientOpenChessPromotionScreen message, Supplier<NetworkEvent.Context> ctx)
    {
        NetworkEvent.Context context = ctx.get();
        if(context.getDirection().getReceptionSide() == LogicalSide.CLIENT)
        {
            context.enqueueWork(() ->
            {
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandlerClass.handleOpenChessPromotionPacket(message, ctx));
            });
            context.setPacketHandled(true);
        }
    }
}