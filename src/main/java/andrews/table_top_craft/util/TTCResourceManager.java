package andrews.table_top_craft.util;

import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TTCResourceManager
{
    @SubscribeEvent
    public static void init(RegisterClientReloadListenersEvent event)
    {
        event.registerReloadListener((ResourceManagerReloadListener) resourceManager ->
        {
            DrawScreenHelper.BUFFERS.forEach((pieceTypePieceModelSetPair, vertexBuffer) -> { vertexBuffer.close(); });
            DrawScreenHelper.BUFFERS.clear();
            DrawScreenHelper.setup();
        });
    }
}