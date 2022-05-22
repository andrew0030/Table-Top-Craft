package andrews.table_top_craft.mixins;

import net.minecraft.client.renderer.ShaderInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/* Author: GiantLuigi4 */
@Mixin(value = ShaderInstance.class, priority = 0)
public class ShaderInstanceMixin {
	@ModifyArg(
			method = "<init>",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourceLocation;<init>(Ljava/lang/String;)V", ordinal = 0),
			require = 0
	)
	public String modifyRL0(String src) {
		return fix(src);
	}
	
	@ModifyVariable(
			method = "getOrCreate",
			at = @At(value = "STORE"),
			ordinal = 1,
			require = 0
	)
	private static String modifyRL1(String src) {
		return fix(src);
	}
	
	@Unique
	private static String fix(String src) {
		if (src.contains(":")) {
			String[] split = src.split(":");
			String left = split[0];
			if (left.startsWith("shaders/core/")) {
				left = left.substring("shaders/core/".length());
				src = left + ":shaders/core/" + split[1];
			}
		}
		return src;
	}

//    protected ResourceLocation trueId;
//
//    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourceLocation;<init>(Ljava/lang/String;)V"))
//    public void preMakeResourceName(ResourceProvider resourceProvider, String string, VertexFormat vertexFormat, CallbackInfo ci)
//    {
//        ResourceLocation nameId = new ResourceLocation(string);
//        trueId = new ResourceLocation(
//                nameId.getNamespace(),
//                "shaders/core/" + nameId.getPath() + ".json"
//        );
//    }
//
//    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourceLocation;<init>(Ljava/lang/String;)V"))
//    public void modifyId(Args args)
//    {
//        args.set(0, trueId.toString());
//    }
//
//    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/resources/ResourceProvider;getResource(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/server/packs/resources/Resource;"))
//    public void modifyResourceName(@NotNull Args args)
//    {
//        args.set(0, trueId);
//    }
//
//    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/ChainedJsonException;setFilenameAndFlush(Ljava/lang/String;)V"))
//    public void modifyFileName(Args args)
//    {
//        args.set(0, trueId.toString());
//    }
//
//    @Unique
//    private static ResourceLocation idLoad = null;
//
//    @ModifyArgs(method = "getOrCreate", at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourceLocation;<init>(Ljava/lang/String;)V"))
//    private static void modifyFileId(Args args)
//    {
//        if (!args.get(0).toString().contains(":")) idLoad = null;
//        else
//        {
//            String name = args.get(0).toString().substring("shaders/core/".length());
//            String nameWithType = name;
//            name = name.substring(0, name.length() - (".vsh".length()));
//            ResourceLocation nameId = new ResourceLocation(name);
//            idLoad = new ResourceLocation(
//                    nameId.getNamespace(),
//                    "shaders/core/" + nameId.getPath() + nameWithType.substring(name.length())
//            );
//            args.set(0, idLoad.toString());
//        }
//    }
//
//    @ModifyArgs(method = "getOrCreate", at = @At(value = "INVOKE", target = "Lnet/minecraft/FileUtil;getFullResourcePath(Ljava/lang/String;)Ljava/lang/String;"))
//    private static void modifyFileIdkWhatToCallThisMethodLOL(Args args)
//    {
//        if (idLoad == null) return;
//        args.set(0, idLoad.toString());
//    }
}