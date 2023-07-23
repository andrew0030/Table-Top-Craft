package andrews.table_top_craft.network.server;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.util.NetworkUtil;
import andrews.table_top_craft.util.Reference;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MessageServerOpenGUIWithServerPlayer
{
    public static ResourceLocation PACKET_ID = new ResourceLocation(Reference.MODID, "open_gui_with_server_player_packet");

    public static void registerPacket()
    {
        ServerPlayNetworking.registerGlobalReceiver(PACKET_ID, (minecraftServer, serverPlayer, packetListener, buf, packetSender) ->
        {
            BlockPos pos = buf.readBlockPos();

            minecraftServer.execute(() ->
            {
                if(serverPlayer == null)
                    return;

                Level level = serverPlayer.getLevel();
                if(level != null)
                {
                    BlockEntity blockEntity = level.getBlockEntity(pos);
                    // We make sure the TileEntity is a ChessBlockEntity
                    if(blockEntity instanceof ChessBlockEntity chessBlockEntity)
                    {
                        ServerAdvancementManager serveradvancementmanager = serverPlayer.getServer().getAdvancements();
                        // We get the piece set advancements
                        Advancement standardAdvancement = serveradvancementmanager.getAdvancement(new ResourceLocation("table_top_craft:standard_piece_collector"));
                        Advancement classicAdvancement = serveradvancementmanager.getAdvancement(new ResourceLocation("table_top_craft:classic_piece_collector"));
                        Advancement pandorasCreaturesAdvancement = serveradvancementmanager.getAdvancement(new ResourceLocation("table_top_craft:pandoras_creatures_piece_collector"));
                        // We check if the player has completed the advancements
                        boolean isStandardSetUnlocked = standardAdvancement == null || serverPlayer.getAdvancements().getOrStartProgress(standardAdvancement).isDone();
                        boolean isClassicSetUnlocked = classicAdvancement == null || serverPlayer.getAdvancements().getOrStartProgress(classicAdvancement).isDone();
                        boolean isPandorasCreaturesSetUnlocked = pandorasCreaturesAdvancement == null || serverPlayer.getAdvancements().getOrStartProgress(pandorasCreaturesAdvancement).isDone();
                        // We send a message to the client, to open the menu while taking into consideration which piece sets have been unlocked
                        NetworkUtil.openChessPieceSelectionFromServer(pos, isStandardSetUnlocked, isClassicSetUnlocked, isPandorasCreaturesSetUnlocked, serverPlayer);
                    }
                }
            });
        });
    }
}