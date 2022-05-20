package andrews.table_top_craft.mixins;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(ShaderInstance.class)
public class ShaderInstanceMixin
{
//    @Shadow
//    @Final
//    private String name;
    protected ResourceLocation trueId;

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourceLocation;<init>(Ljava/lang/String;)V"))
    public void preMakeResourceName(ResourceProvider resourceProvider, String string, VertexFormat vertexFormat, CallbackInfo ci)
    {
        ResourceLocation nameId = new ResourceLocation(string);
        trueId = new ResourceLocation(
                nameId.getNamespace(),
                "shaders/core/" + nameId.getPath() + ".json"
        );
    }

    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourceLocation;<init>(Ljava/lang/String;)V"))
    public void modifyId(Args args)
    {
        args.set(0, trueId.toString());
    }

    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/resources/ResourceProvider;getResource(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/server/packs/resources/Resource;"))
    public void modifyResourceName(@NotNull Args args)
    {
        args.set(0, trueId);
    }

    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/ChainedJsonException;setFilenameAndFlush(Ljava/lang/String;)V"))
    public void modifyFileName(Args args)
    {
        args.set(0, trueId.toString());
    }

    @Unique
    private static ResourceLocation idLoad = null;

    @ModifyArgs(method = "getOrCreate", at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourceLocation;<init>(Ljava/lang/String;)V"))
    private static void modifyFileId(Args args)
    {
        if (!args.get(0).toString().contains(":")) idLoad = null;
        else
        {
            String name = args.get(0).toString().substring("shaders/core/".length());
            String nameWithType = name;
            name = name.substring(0, name.length() - (".vsh".length()));
            ResourceLocation nameId = new ResourceLocation(name);
            idLoad = new ResourceLocation(
                    nameId.getNamespace(),
                    "shaders/core/" + nameId.getPath() + nameWithType.substring(name.length())
            );
            args.set(0, idLoad.toString());
        }
    }

    @ModifyArgs(method = "getOrCreate", at = @At(value = "INVOKE", target = "Lnet/minecraft/FileUtil;getFullResourcePath(Ljava/lang/String;)Ljava/lang/String;"))
    private static void modifyFileIdkWhatToCallThisMethodLOL(Args args)
    {
        if (idLoad == null) return;
        args.set(0, idLoad.toString());
    }
}