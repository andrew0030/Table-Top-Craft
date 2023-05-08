package andrews.table_top_craft.network.client;

import andrews.table_top_craft.network.client.util.ClientPacketHandlerClass;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageClientChessParticles
{
    public BlockPos pos;
    public byte destCord;
    public boolean isWhite;
    public float xSpeed;
    public float ySpeed;
    public float zSpeed;

    public MessageClientChessParticles(BlockPos pos, byte destCord, boolean isWhite, float xSpeed, float ySpeed, float zSpeed)
    {
        this.pos = pos;
        this.destCord = destCord;
        this.isWhite = isWhite;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.zSpeed = zSpeed;
    }

    public void serialize(FriendlyByteBuf buf)
    {
        buf.writeBlockPos(this.pos);
        buf.writeByte(this.destCord);
        buf.writeBoolean(this.isWhite);
        buf.writeFloat(this.xSpeed);
        buf.writeFloat(this.ySpeed);
        buf.writeFloat(this.zSpeed);
    }

    public static MessageClientChessParticles deserialize(FriendlyByteBuf buf)
    {
        BlockPos pos = buf.readBlockPos();
        byte destCord = buf.readByte();
        boolean isWhite = buf.readBoolean();
        float xSpeed = buf.readFloat();
        float ySpeed = buf.readFloat();
        float zSpeed = buf.readFloat();
        return new MessageClientChessParticles(pos, destCord, isWhite, xSpeed, ySpeed, zSpeed);
    }

    public static void handle(MessageClientChessParticles message, Supplier<NetworkEvent.Context> ctx)
    {
        NetworkEvent.Context context = ctx.get();
        if(context.getDirection().getReceptionSide() == LogicalSide.CLIENT)
        {
            context.enqueueWork(() ->
            {
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandlerClass.handlePlayChessParticlesPacket(message, ctx));
            });
            context.setPacketHandled(true);
        }
    }
}