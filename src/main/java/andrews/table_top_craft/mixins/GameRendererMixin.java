package andrews.table_top_craft.mixins;

import andrews.table_top_craft.events.DrawScreenEvent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin
{
	@Unique
	boolean firstInit = true;
	
	@Inject(at = @At("TAIL"), method = "onResourceManagerReload")
	public void postLoadShaders(ResourceManager pResourceManager, CallbackInfo ci)
	{
		DrawScreenEvent.setup();
	}
}
