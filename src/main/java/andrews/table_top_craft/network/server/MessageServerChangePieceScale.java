package andrews.table_top_craft.network.server;

import andrews.table_top_craft.block_entities.ChessPieceFigureBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageServerChangePieceScale
{
    private final BlockPos pos;
    private final double value;

    public MessageServerChangePieceScale(BlockPos pos, double value)
    {
        this.pos = pos;
        this.value = value;
    }

    public void serialize(FriendlyByteBuf buf)
    {
        buf.writeBlockPos(pos);
        buf.writeDouble(value);
    }

    public static MessageServerChangePieceScale deserialize(FriendlyByteBuf buf)
    {
        BlockPos pos = buf.readBlockPos();
        double value = buf.readDouble();
        return new MessageServerChangePieceScale(pos, value);
    }

    public static void handle(MessageServerChangePieceScale message, Supplier<NetworkEvent.Context> ctx)
    {
        NetworkEvent.Context context = ctx.get();
        Player player = context.getSender();
        Level level = player.getLevel();
        BlockPos blockPos = message.pos;
        double value = message.value;

        if(context.getDirection().getReceptionSide() == LogicalSide.SERVER)
        {
            context.enqueueWork(() ->
            {
                if(level != null)
                {
                    BlockEntity blockEntity = level.getBlockEntity(blockPos);
                    // We make sure the TileEntity is a ChessPieceFigureBlockEntity
                    if(blockEntity instanceof ChessPieceFigureBlockEntity chessPieceFigureBlockEntity)
                    {
                        chessPieceFigureBlockEntity.setPieceScale(value);
                        level.sendBlockUpdated(message.pos, level.getBlockState(blockPos), level.getBlockState(blockPos), 2);
                        chessPieceFigureBlockEntity.setChanged();
                    }
                }
            });
            context.setPacketHandled(true);
        }
    }
}