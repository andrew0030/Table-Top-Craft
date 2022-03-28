package andrews.table_top_craft.events;

import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Objects;

//@Mod.EventBusSubscriber(modid = Reference.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RegisterModShadersEvent
{

    //TODO moved everything to @TableTopCraft for now

//    @Nullable
//    public static ShaderInstance rendertypeSolidBlockEntityShader;
//
//    /**
//     * @return The Table Top Craft chess piece Shader.
//     */
//    public static ShaderInstance getSolidBlockEntityShader()
//    {
//        return Objects.requireNonNull(rendertypeSolidBlockEntityShader, "Attempted to call getSolidBlockEntityShader before shaders have finished loading.");
//    }

//    @SubscribeEvent
//    public static void registerShaders(RegisterShadersEvent event)
//    {
//        try
//        {
//            event.registerShader(new ShaderInstance(event.getResourceManager(), new ResourceLocation(Reference.MODID,"rendertype_solid"), DefaultVertexFormat.BLOCK), (shader) -> {
//                rendertypeSolidBlockEntityShader = shader;
//            });
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//    }
}