package andrews.table_top_craft.mixins;

import andrews.table_top_craft.tile_entities.render.BufferHelpers;
import andrews.table_top_craft.util.DrawScreenHelper;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin
{
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