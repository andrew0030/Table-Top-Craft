package andrews.table_top_craft.mixins;

import andrews.table_top_craft.tile_entities.render.BufferHelpers;
import andrews.table_top_craft.util.DrawScreenHelper;
import andrews.table_top_craft.util.Reference;
import andrews.table_top_craft.util.TTCClientInit;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(GameRenderer.class)
public class GameRendererMixin
{
    @Inject(at = @At(value = "TAIL"), method = "reloadShaders")
    public void reloadShaders(ResourceProvider resourceProvider, CallbackInfo ci)
    {
        if(TTCClientInit.rendertypeSolidBlockEntityShader != null)
            TTCClientInit.rendertypeSolidBlockEntityShader.close();
        try
        {
            TTCClientInit.rendertypeSolidBlockEntityShader = new ShaderInstance(resourceProvider, Reference.MODID + ":rendertype_solid", DefaultVertexFormat.BLOCK);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Inject(at = @At("HEAD"), method = "render")
    public void preRender(float pPartialTicks, long pNanoTime, boolean pRenderLevel, CallbackInfo ci)
    {
        BufferHelpers.shouldRefresh = true;
        BufferHelpers.useFallbackSystem = false;
    }

    @Mixin(targets = "net.minecraft.client.renderer.GameRenderer$1")
    public static class HackToGetReloadManager
    {
        @Inject(at = @At("TAIL"), method = "apply(Lnet/minecraft/client/renderer/GameRenderer$ResourceCache;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V")
        public void injectApply(GameRenderer.ResourceCache resourceCache, ResourceManager resourceManager, ProfilerFiller profilerFiller, CallbackInfo ci)
        {
            DrawScreenHelper.setup();
        }
    }
}