package andrews.table_top_craft.network.server;

import andrews.table_top_craft.block_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.util.Reference;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MessageServerRotateChessPieceFigure
{
    public static ResourceLocation PACKET_ID = new ResourceLocation(Reference.MODID, "rotate_chess_piece_figure_packet");

    public static void registerPacket()
    {
        ServerPlayNetworking.registerGlobalReceiver(PACKET_ID, (minecraftServer, serverPlayer, packetListener, buf, packetSender) ->
        {
            BlockPos pos = buf.readBlockPos();

            minecraftServer.execute(() ->
            {
                if(serverPlayer == null)
                    return;

                Level level = serverPlayer.level();
                if(level != null)
                {
                    BlockEntity blockEntity = level.getBlockEntity(pos);
                    // We make sure the BlockEntity is a ChessPieceFigureBlockEntity
                    if(blockEntity instanceof ChessPieceFigureBlockEntity chessPieceFigureBlockEntity)
                    {
                        chessPieceFigureBlockEntity.setRotateChessPieceFigure(!chessPieceFigureBlockEntity.getRotateChessPieceFigure());
                        level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
                    }
                }
            });
        });
    }
}