package andrews.table_top_craft.network.server;

import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.Reference;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MessageServerChessVisuals
{
    public static ResourceLocation PACKET_ID = new ResourceLocation(Reference.MODID, "chess_visuals_packet");

    public static void registerPacket()
    {
        ServerPlayNetworking.registerGlobalReceiver(PACKET_ID, (minecraftServer, serverPlayer, packetListener, buf, packetSender) ->
        {
            BlockPos pos = buf.readBlockPos();
            byte type = buf.readByte();

            minecraftServer.execute(() ->
            {
                if(serverPlayer == null)
                    return;

                Level level = serverPlayer.getLevel();
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
            });
        });
    }
}