package andrews.table_top_craft.block_entities.render;

import andrews.table_top_craft.util.*;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;

public class ShaderCompatTexture
{
    // Dynamic Texture
    private static final NativeImage image = new NativeImage(NativeImage.Format.RGBA, 1, 1, true);
    private static final DynamicTexture texture = new DynamicTexture(image);
    private static final ResourceLocation resourceLocation;
    // Shader Compat texture
    public static final ResourceLocation SHADER_COMPAT_WHITE = new ResourceLocation(Reference.MODID, "textures/tile/chess/chess_piece.png");

    static
    {
        // We create the dummy texture
        image.setPixelRGBA(0, 0, 16777215);
        texture.upload();
        resourceLocation = Minecraft.getInstance().getTextureManager().register("table_top_craft_dummy", texture);
    }
}