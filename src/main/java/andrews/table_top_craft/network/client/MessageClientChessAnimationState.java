package andrews.table_top_craft.network.client;

import andrews.table_top_craft.network.client.util.ClientPacketHandlerClass;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.Reference;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class MessageClientChessAnimationState
{
    public static ResourceLocation PACKET_ID = new ResourceLocation(Reference.MODID, "chess_animation_state_packet");

    public static void registerPacket()
    {
        ClientPlayNetworking.registerGlobalReceiver(PACKET_ID, (client, handler, buf, responseSender) ->
        {
            BlockPos pos = buf.readBlockPos();
            byte actionType = buf.readByte();
            byte currentCord = buf.readByte();
            byte destCord = buf.readByte();

            client.execute(() ->
            {
                if(Minecraft.getInstance().level == null) return;

                if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof ChessTileEntity chessTileEntity)
                    ClientPacketHandlerClass.handleChessAnimationPacket(chessTileEntity, actionType, currentCord, destCord);
            });
        });
    }
}