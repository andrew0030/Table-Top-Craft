package andrews.table_top_craft.mixins;

import andrews.table_top_craft.game_logic.chess.pieces.BasePiece.PieceModelSet;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece.PieceType;
import andrews.table_top_craft.tile_entities.render.BufferHelpers;
import andrews.table_top_craft.util.DrawScreenHelper;
import andrews.table_top_craft.util.Reference;
import andrews.table_top_craft.util.TTCClientInit;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(GameRenderer.class)
public class GameRendererMixin
{
    @Inject(at = @At(value = "TAIL"), method = "reloadShaders")
    public void reloadShaders(ResourceManager resourceManager, CallbackInfo ci)
    {
        if(TTCClientInit.rendertypeSolidBlockEntityShader != null)
            TTCClientInit.rendertypeSolidBlockEntityShader.close();
        try
        {
            TTCClientInit.rendertypeSolidBlockEntityShader = new ShaderInstance(resourceManager, Reference.MODID + ":rendertype_solid", DefaultVertexFormat.BLOCK);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Inject(at = @At("TAIL"), method = "onResourceManagerReload")
    public void postLoadShaders(ResourceManager pResourceManager, CallbackInfo ci)
    {
        DrawScreenHelper.setup();
    }

    @Inject(at = @At("HEAD"), method = "render")
    public void preRender(float pPartialTicks, long pNanoTime, boolean pRenderLevel, CallbackInfo ci)
    {
        BufferHelpers.shouldRefresh = true;
        BufferHelpers.useFallbackSystem = false;
    }
}