package andrews.table_top_craft.mixins;

import net.minecraft.client.renderer.ShaderInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/* Author: GiantLuigi4 */
/* For the targets, I took reference from: https://github.com/Fabricators-of-Create/Porting-Lib/blob/1.18.2-dev/src/main/java/io/github/fabricators_of_create/porting_lib/mixin/client/ShaderInstanceMixin.java */
/* this should be a part of fapi */
@Mixin(value = ShaderInstance.class, priority = 5000)
public class ShaderInstanceMixin
{
	@ModifyArg(
			method = "<init>",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourceLocation;<init>(Ljava/lang/String;)V", ordinal = 0),
			require = 0
	)
	public String modifyRL0(String src)
	{
		return fix(src);
	}

	@ModifyVariable(
			method = "getOrCreate",
			at = @At(value = "STORE"),
			ordinal = 1,
			require = 0
	)
	private static String modifyRL1(String src)
	{
		return fix(src);
	}

	@Unique
	private static String fix(String src)
	{
		if (src.contains(":"))
		{
			String[] split = src.split(":");
			String left = split[0];
			if (left.startsWith("shaders/core/"))
			{
				left = left.substring("shaders/core/".length());
				src = left + ":shaders/core/" + split[1];
			}
		}
		return src;
	}
}