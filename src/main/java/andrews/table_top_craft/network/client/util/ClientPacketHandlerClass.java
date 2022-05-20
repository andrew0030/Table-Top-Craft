package andrews.table_top_craft.network.client.util;

import andrews.table_top_craft.network.client.MessageClientOpenChessPieceSelectionScreen;
import andrews.table_top_craft.screens.chess.menus.ChessPieceSelectionScreen;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

public class ClientPacketHandlerClass
{
    public static void handleOpenChessPieceSelectionPacket(ChessTileEntity chessTileEntity, boolean isStandardSetUnlocked, boolean isClassicSetUnlocked,boolean isPandorasCreaturesSetUnlocked)
    {
        Minecraft.getInstance().setScreen(new ChessPieceSelectionScreen(chessTileEntity, isStandardSetUnlocked, isClassicSetUnlocked, isPandorasCreaturesSetUnlocked));
    }
}