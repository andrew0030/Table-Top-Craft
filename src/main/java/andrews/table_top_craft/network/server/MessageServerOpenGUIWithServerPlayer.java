package andrews.table_top_craft.network.server;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.util.NetworkUtil;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageServerOpenGUIWithServerPlayer
{
    private final BlockPos pos;

    public MessageServerOpenGUIWithServerPlayer(BlockPos pos)
    {
        this.pos = pos;
    }

    public void serialize(FriendlyByteBuf buf)
    {
        buf.writeBlockPos(pos);
    }

    public static MessageServerOpenGUIWithServerPlayer deserialize(FriendlyByteBuf buf)
    {
        BlockPos pos = buf.readBlockPos();
        return new MessageServerOpenGUIWithServerPlayer(pos);
    }

    public static void handle(MessageServerOpenGUIWithServerPlayer message, Supplier<NetworkEvent.Context> ctx)
    {
        NetworkEvent.Context context = ctx.get();
        ServerPlayer player = context.getSender();
        Level level = player.getLevel();
        BlockPos pos = message.pos;

        if(context.getDirection().getReceptionSide() == LogicalSide.SERVER)
        {
            context.enqueueWork(() ->
            {
                if(level != null)
                {
                    BlockEntity blockEntity = level.getBlockEntity(pos);
                    // We make sure the TileEntity is a ChessBlockEntity
                    if(blockEntity instanceof ChessBlockEntity chessBlockEntity)
                    {
                        ServerAdvancementManager serveradvancementmanager = player.getServer().getAdvancements();
                        // We get the piece set advancements
                        Advancement standardAdvancement = serveradvancementmanager.getAdvancement(new ResourceLocation("table_top_craft:standard_piece_collector"));
                        Advancement classicAdvancement = serveradvancementmanager.getAdvancement(new ResourceLocation("table_top_craft:classic_piece_collector"));
                        Advancement pandorasCreaturesAdvancement = serveradvancementmanager.getAdvancement(new ResourceLocation("table_top_craft:pandoras_creatures_piece_collector"));
                        // We check if the player has completed the advancements
                        boolean isStandardSetUnlocked = standardAdvancement == null || player.getAdvancements().getOrStartProgress(standardAdvancement).isDone();
                        boolean isClassicSetUnlocked = classicAdvancement == null || player.getAdvancements().getOrStartProgress(classicAdvancement).isDone();
                        boolean isPandorasCreaturesSetUnlocked = pandorasCreaturesAdvancement == null || player.getAdvancements().getOrStartProgress(pandorasCreaturesAdvancement).isDone();
                        // We send a message to the client, to open the menu while taking into consideration which piece sets have been unlocked
                        NetworkUtil.openChessPieceSelectionFromServer(pos, isStandardSetUnlocked, isClassicSetUnlocked, isPandorasCreaturesSetUnlocked, player);
                    }
                }
            });
            context.setPacketHandled(true);
        }
    }
}