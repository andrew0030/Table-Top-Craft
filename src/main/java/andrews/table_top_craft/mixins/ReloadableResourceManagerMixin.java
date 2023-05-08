package andrews.table_top_craft.mixins;

import andrews.table_top_craft.util.DrawScreenHelper;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.util.Unit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(ReloadableResourceManager.class)
public class ReloadableResourceManagerMixin
{
    @Inject(method = "createReload", at = @At(value = "RETURN"))
    public void injectCreateReload(Executor backgroundExecutor, Executor gameExecutor, CompletableFuture<Unit> waitingFor, List<PackResources> resourcePacks, CallbackInfoReturnable<ReloadInstance> cir)
    {
        DrawScreenHelper.BUFFERS.forEach((pieceTypePieceModelSetPair, vertexBuffer) -> { vertexBuffer.close(); });
        DrawScreenHelper.BUFFERS.clear();
        DrawScreenHelper.setup();
    }
}