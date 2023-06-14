package andrews.table_top_craft.network.server;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.util.NetworkUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageServerSetPieceSet
{
    private final BlockPos pos;
    private final int pieceSet;

    public MessageServerSetPieceSet(BlockPos pos, int pieceSet)
    {
        this.pos = pos;
        this.pieceSet = pieceSet;
    }

    public void serialize(FriendlyByteBuf buf)
    {
        buf.writeBlockPos(pos);
        buf.writeInt(pieceSet);
    }

    public static MessageServerSetPieceSet deserialize(FriendlyByteBuf buf)
    {
        BlockPos pos = buf.readBlockPos();
        int pieceSet = buf.readInt();
        return new MessageServerSetPieceSet(pos, pieceSet);
    }

    public static void handle(MessageServerSetPieceSet message, Supplier<NetworkEvent.Context> ctx)
    {
        NetworkEvent.Context context = ctx.get();
        Player player = context.getSender();
        Level level = player.getLevel();
        BlockPos chessPos = message.pos;
        int pieceSet = message.pieceSet;

        if(context.getDirection().getReceptionSide() == LogicalSide.SERVER)
        {
            context.enqueueWork(() ->
            {
                if(level != null)
                {
                    BlockEntity blockEntity = level.getBlockEntity(chessPos);
                    // We make sure the TileEntity is a ChessBlockEntity
                    if(blockEntity instanceof ChessBlockEntity chessBlockEntity)
                    {
                        switch(pieceSet)
                        {
                            case 0 -> chessBlockEntity.setPieceSet(0);
                            case 1 -> chessBlockEntity.setPieceSet(1);
                            case 2 -> chessBlockEntity.setPieceSet(2);
                        }
                        level.sendBlockUpdated(message.pos, level.getBlockState(chessPos), level.getBlockState(chessPos), 2);
                        chessBlockEntity.setChanged();
                        NetworkUtil.setChessAnimationForAllTracking(level, chessPos, (byte) 1);
                    }
                }
            });
            context.setPacketHandled(true);
        }
    }
}