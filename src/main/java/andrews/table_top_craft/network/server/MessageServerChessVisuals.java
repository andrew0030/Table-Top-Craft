package andrews.table_top_craft.network.server;

import andrews.table_top_craft.objects.blocks.ChessBlock;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageServerChessVisuals
{
    private final BlockPos pos;
    private final byte type;

    public MessageServerChessVisuals(BlockPos pos, byte type)
    {
        this.pos = pos;
        this.type = type;
    }

    public void serialize(FriendlyByteBuf buf)
    {
        buf.writeBlockPos(pos);
        buf.writeByte(type);
    }

    public static MessageServerChessVisuals deserialize(FriendlyByteBuf buf)
    {
        BlockPos pos = buf.readBlockPos();
        byte type = buf.readByte();
        return new MessageServerChessVisuals(pos, type);
    }

    public static void handle(MessageServerChessVisuals message, Supplier<NetworkEvent.Context> ctx)
    {
        NetworkEvent.Context context = ctx.get();
        Player player = context.getSender();
        BlockPos pos = message.pos;
        byte type = message.type;

        if (context.getDirection().getReceptionSide() == LogicalSide.SERVER)
        {
            context.enqueueWork(() ->
            {
                if (player != null)
                {
                    Level level = player.getLevel();
                    BlockEntity blockEntity = level.getBlockEntity(pos);
                    if (blockEntity instanceof ChessTileEntity chessTileEntity)
                    {
                        switch (type) {
                            default -> chessTileEntity.setPlayPieceAnimations(!chessTileEntity.getPlayPieceAnimations());
                            case 1 -> chessTileEntity.setDisplayParticles(!chessTileEntity.getDisplayParticles());
                        }
                        level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
                        chessTileEntity.setChanged();
                    }
                }
            });
            context.setPacketHandled(true);
        }
    }
}