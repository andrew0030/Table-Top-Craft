package andrews.table_top_craft.network.client;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.network.client.util.ClientPacketHandlerClass;
import andrews.table_top_craft.util.Reference;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class MessageClientOpenChessPieceSelectionScreen
{
    public static ResourceLocation PACKET_ID = new ResourceLocation(Reference.MODID, "open_chess_piece_selection_screen_packet");

    public static void registerPacket()
    {
        ClientPlayNetworking.registerGlobalReceiver(PACKET_ID, (client, handler, buf, responseSender) ->
        {
            BlockPos pos = buf.readBlockPos();
            boolean isStandardSetUnlocked = buf.readBoolean();
            boolean isClassicSetUnlocked = buf.readBoolean();
            boolean isPandorasCreaturesSetUnlocked = buf.readBoolean();

            client.execute(() ->
            {
                if(Minecraft.getInstance().player == null) return;

                if(Minecraft.getInstance().player.level().getBlockEntity(pos) instanceof ChessBlockEntity chessBlockEntity)
                    ClientPacketHandlerClass.handleOpenChessPieceSelectionPacket(chessBlockEntity, isStandardSetUnlocked, isClassicSetUnlocked, isPandorasCreaturesSetUnlocked);
            });
        });
    }
}