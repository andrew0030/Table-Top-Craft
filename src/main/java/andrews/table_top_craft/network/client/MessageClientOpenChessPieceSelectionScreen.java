package andrews.table_top_craft.network.client;

import andrews.table_top_craft.network.client.util.ClientPacketHandlerClass;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageClientOpenChessPieceSelectionScreen
{
    public BlockPos pos;
    public boolean isStandardSetUnlocked;
    public boolean isClassicSetUnlocked;
    public boolean isPandorasCreaturesSetUnlocked;

    public MessageClientOpenChessPieceSelectionScreen(BlockPos pos, boolean isStandardSetUnlocked, boolean isClassicSetUnlocked, boolean isPandorasCreaturesSetUnlocked)
    {
        this.pos = pos;
        this.isStandardSetUnlocked = isStandardSetUnlocked;
        this.isClassicSetUnlocked = isClassicSetUnlocked;
        this.isPandorasCreaturesSetUnlocked = isPandorasCreaturesSetUnlocked;
    }

    public void serialize(FriendlyByteBuf buf)
    {
        buf.writeBlockPos(this.pos);
        buf.writeBoolean(this.isStandardSetUnlocked);
        buf.writeBoolean(this.isClassicSetUnlocked);
        buf.writeBoolean(this.isPandorasCreaturesSetUnlocked);
    }

    public static MessageClientOpenChessPieceSelectionScreen deserialize(FriendlyByteBuf buf)
    {
        BlockPos pos = buf.readBlockPos();
        boolean isStandardSetUnlocked = buf.readBoolean();
        boolean isClassicSetUnlocked = buf.readBoolean();
        boolean isPandorasCreaturesSetUnlocked = buf.readBoolean();
        return new MessageClientOpenChessPieceSelectionScreen(pos, isStandardSetUnlocked, isClassicSetUnlocked, isPandorasCreaturesSetUnlocked);
    }

    public static void handle(MessageClientOpenChessPieceSelectionScreen message, Supplier<NetworkEvent.Context> ctx)
    {
        NetworkEvent.Context context = ctx.get();
        if(context.getDirection().getReceptionSide() == LogicalSide.CLIENT)
        {
            context.enqueueWork(() ->
            {
                // We have to use this to avoid calling Screen on the server
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandlerClass.handleOpenChessPieceSelectionPacket(message, ctx));
            });
            context.setPacketHandled(true);
        }
    }
}