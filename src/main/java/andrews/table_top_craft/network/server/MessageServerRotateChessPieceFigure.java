package andrews.table_top_craft.network.server;

import java.util.function.Supplier;

import andrews.table_top_craft.block_entities.ChessPieceFigureBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

public class MessageServerRotateChessPieceFigure
{
    private final BlockPos pos;

    public MessageServerRotateChessPieceFigure(BlockPos pos)
    {
        this.pos = pos;
    }

    public void serialize(FriendlyByteBuf buf)
    {
        buf.writeBlockPos(pos);
    }

    public static MessageServerRotateChessPieceFigure deserialize(FriendlyByteBuf buf)
    {
        BlockPos pos = buf.readBlockPos();
        return new MessageServerRotateChessPieceFigure(pos);
    }

    public static void handle(MessageServerRotateChessPieceFigure message, Supplier<NetworkEvent.Context> ctx)
    {
        NetworkEvent.Context context = ctx.get();
        Player player = context.getSender();
        Level level = player.getLevel();
        BlockPos chessPieceFigurePos = message.pos;

        if(context.getDirection().getReceptionSide() == LogicalSide.SERVER)
        {
            context.enqueueWork(() ->
            {
                if(level != null)
                {
                    BlockEntity blockEntity = level.getBlockEntity(chessPieceFigurePos);
                    // We make sure the TileEntity is a ChessBlockEntity
                    if(blockEntity instanceof ChessPieceFigureBlockEntity chessPieceFigureBlockEntity)
                    {
                        chessPieceFigureBlockEntity.setRotateChessPieceFigure(!chessPieceFigureBlockEntity.getRotateChessPieceFigure());
                        level.sendBlockUpdated(message.pos, level.getBlockState(chessPieceFigurePos), level.getBlockState(chessPieceFigurePos), 2);
                    }
                }
            });
            context.setPacketHandled(true);
        }
    }
}