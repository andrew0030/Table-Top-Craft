package andrews.table_top_craft.network.server;

import andrews.table_top_craft.criteria.TTCCriteriaTriggers;
import andrews.table_top_craft.block_entities.ConnectFourBlockEntity;
import andrews.table_top_craft.util.NetworkUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageServerDoConnectFourInteraction
{
    private final BlockPos pos;
    private final byte column;

    public MessageServerDoConnectFourInteraction(BlockPos pos, byte column)
    {
        this.pos = pos;
        this.column = column;
    }

    public void serialize(FriendlyByteBuf buf)
    {
        buf.writeBlockPos(pos);
        buf.writeByte(column);
    }

    public static MessageServerDoConnectFourInteraction deserialize(FriendlyByteBuf buf)
    {
        BlockPos pos = buf.readBlockPos();
        byte column = buf.readByte();
        return new MessageServerDoConnectFourInteraction(pos, column);
    }

    public static void handle(MessageServerDoConnectFourInteraction message, Supplier<NetworkEvent.Context> ctx)
    {
        NetworkEvent.Context context = ctx.get();
        ServerPlayer player = context.getSender();
        BlockPos pos = message.pos;
        byte column = message.column;

        if(context.getDirection().getReceptionSide() == LogicalSide.SERVER)
        {
            context.enqueueWork(() ->
            {
                if(player != null)
                {
                    Level level = player.getLevel();
                    BlockEntity blockEntity = level.getBlockEntity(pos);
                    if(blockEntity instanceof ConnectFourBlockEntity connectFourBlockEntity)
                    {
                        connectFourBlockEntity.addPieceToColumn(column);
                        level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
                        connectFourBlockEntity.setChanged();

                        if(!level.isClientSide() && connectFourBlockEntity.hasFourInARow())
                            TTCCriteriaTriggers.MAKE_CONNECT_FOUR_VICTORY_MOVE.trigger(player);

                        NetworkUtil.setConnectFourAnimationForAllTracking(level, pos, (byte) (connectFourBlockEntity.getTopPieceInColumn(column) + 6 * column));
                    }
                }
            });
            context.setPacketHandled(true);
        }
    }
}