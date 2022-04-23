package andrews.table_top_craft.network.client.util;

import andrews.table_top_craft.network.client.MessageClientOpenChessPieceSelectionScreen;
import andrews.table_top_craft.screens.chess.menus.ChessPieceSelectionScreen;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientPacketHandlerClass
{
    public static void handleOpenChessPieceSelectionPacket(MessageClientOpenChessPieceSelectionScreen msg, Supplier<NetworkEvent.Context> ctx)
    {
        BlockPos pos = msg.pos;
        boolean isStandardSetUnlocked = msg.isStandardSetUnlocked;
        boolean isClassicSetUnlocked = msg.isClassicSetUnlocked;
        boolean isPandorasCreaturesSetUnlocked = msg.isPandorasCreaturesSetUnlocked;
        if(Minecraft.getInstance().player.getLevel().getBlockEntity(pos) != null && Minecraft.getInstance().player.getLevel().getBlockEntity(pos) instanceof ChessTileEntity chessTileEntity)
        {
            Minecraft.getInstance().setScreen(new ChessPieceSelectionScreen(chessTileEntity, isStandardSetUnlocked, isClassicSetUnlocked, isPandorasCreaturesSetUnlocked));
        }
    }
}