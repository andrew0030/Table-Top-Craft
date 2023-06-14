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

public class MessageServerChangePieceType
{
    private final BlockPos pos;
    private final byte value;

    public MessageServerChangePieceType(BlockPos pos, byte value)
    {
        this.pos = pos;
        this.value = value;
    }

    public void serialize(FriendlyByteBuf buf)
    {
        buf.writeBlockPos(pos);
        buf.writeByte(value);
    }

    public static MessageServerChangePieceType deserialize(FriendlyByteBuf buf)
    {
        BlockPos pos = buf.readBlockPos();
        byte value = buf.readByte();
        return new MessageServerChangePieceType(pos, value);
    }

    public static void handle(MessageServerChangePieceType message, Supplier<NetworkEvent.Context> ctx)
    {
        NetworkEvent.Context context = ctx.get();
        Player player = context.getSender();
        Level level = player.getLevel();
        BlockPos blockPos = message.pos;
        byte value = message.value;

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
                        int currentType = chessPieceFigureBlockEntity.getPieceType();
                        if(value > 0)
                        {
                            if (currentType < 6)
                                chessPieceFigureBlockEntity.setPieceType(++currentType);
                            else
                                chessPieceFigureBlockEntity.setPieceType(1);
                        }
                        else if(value < 0)
                        {
                            if (currentType > 1)
                                chessPieceFigureBlockEntity.setPieceType(--currentType);
                            else
                                chessPieceFigureBlockEntity.setPieceType(6);
                        }
                        level.sendBlockUpdated(message.pos, level.getBlockState(blockPos), level.getBlockState(blockPos), 2);
                        chessPieceFigureBlockEntity.setChanged();
                    }
                }
            });
            context.setPacketHandled(true);
        }
    }
}