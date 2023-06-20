package andrews.table_top_craft.util;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fabricmc.fabric.impl.resource.loader.ResourceManagerHelperImpl;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;

public class TTCResourceManager
{
    public static void init()
    {
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener()
        {
            @Override
            public ResourceLocation getFabricId()
            {
                return new ResourceLocation(Reference.MODID, "resource_manager");
            }

            @Override
            public void onResourceManagerReload(ResourceManager resourceManager)
            {
                // Shader Reloading
                if(TTCClientInit.rendertypeSolidBlockEntityShader != null)
                    TTCClientInit.rendertypeSolidBlockEntityShader.close();
                try {
                    TTCClientInit.rendertypeSolidBlockEntityShader = new ShaderInstance(resourceManager, Reference.MODID + ":rendertype_solid", DefaultVertexFormat.BLOCK);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Buffers Reloading
                DrawScreenHelper.BUFFERS.forEach((pieceTypePieceModelSetPair, vertexBuffer) -> { vertexBuffer.close(); });
                DrawScreenHelper.BUFFERS.clear();
                DrawScreenHelper.setup();
            }
        });
    }
}