package andrews.table_top_craft.network.client;

import andrews.table_top_craft.network.client.util.ClientPacketHandlerClass;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.Reference;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class MessageClientChessParticles
{
    public static ResourceLocation PACKET_ID = new ResourceLocation(Reference.MODID, "chess_particles_packet");

    public static void registerPacket()
    {
        ClientPlayNetworking.registerGlobalReceiver(PACKET_ID, (client, handler, buf, responseSender) ->
        {
            BlockPos pos = buf.readBlockPos();
            byte destCord = buf.readByte();
            boolean isWhite = buf.readBoolean();
            float xSpeed = buf.readFloat();
            float ySpeed = buf.readFloat();
            float zSpeed = buf.readFloat();

            client.execute(() ->
            {
                if(Minecraft.getInstance().player == null) return;

                if(Minecraft.getInstance().player.getLevel().getBlockEntity(pos) instanceof ChessTileEntity)
                    ClientPacketHandlerClass.handlePlayChessParticlesPacket(pos, destCord, isWhite, xSpeed, ySpeed, zSpeed);
            });
        });
    }
}