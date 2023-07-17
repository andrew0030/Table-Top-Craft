package andrews.table_top_craft.mixins;

import andrews.table_top_craft.util.Reference;
import andrews.table_top_craft.util.shader_compat.ShaderCompatHandler;
import net.minecraft.client.renderer.ShaderInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = ShaderInstance.class, priority = 2000) // High priority so it runs after Fabrics Mixin
public class ShaderInstanceJankFixMixin
{
    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourceLocation;<init>(Ljava/lang/String;)V", ordinal = 0), require = 0)
    public String modifyRL0(String src)
    {
        return fix(src);
    }

    @ModifyVariable(method = "getOrCreate", at = @At(value = "STORE"), ordinal = 1, require = 0)
    private static String modifyRL1(String src)
    {
        return fix(src);
    }

    @Unique
    private static String fix(String src)
    {
        if(src.contains(Reference.MODID + ":"))
        {
            src = src.replaceAll(Reference.MODID + ":", ""); // We remove all instances of the prefix
            if(!ShaderCompatHandler.isOFLoaded()) // If OF is Loaded we dont add the ID because OF does
                src = Reference.MODID + ":" + src; // We add the prefix a single time
        }
        return src;
    }
}