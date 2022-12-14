package andrews.table_top_craft.mixins;

import andrews.table_top_craft.tile_entities.render.BufferHelpers;
import andrews.table_top_craft.util.DrawScreenHelper;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin
{
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