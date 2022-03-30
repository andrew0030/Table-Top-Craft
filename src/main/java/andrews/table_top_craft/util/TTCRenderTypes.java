package andrews.table_top_craft.util;

import andrews.table_top_craft.TableTopCraft;
import andrews.table_top_craft.events.RegisterModShadersEvent;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.RenderType;

import java.util.function.BiFunction;

public class TTCRenderTypes extends RenderStateShard
{
	private static final RenderStateShard.ShaderStateShard RENDERTYPE_SOLID_BLOCK_ENTITY_SHADER = new RenderStateShard.ShaderStateShard(TableTopCraft::getSolidBlockEntityShader);

	public TTCRenderTypes(String nameIn, Runnable setupTaskIn, Runnable clearTaskIn)
	{
		super(nameIn, setupTaskIn, clearTaskIn);
	}

	//TODO #############################################################################################
	//TODO # Figure out how to apply the block light to this, for some reason its glowing in the dark! #
	//TODO #############################################################################################
	public static RenderType getEmissiveTransluscent(ResourceLocation texture, boolean outline)
	{
		RenderType.CompositeState state = RenderType.CompositeState.builder()
				.setShaderState(POSITION_COLOR_TEX_LIGHTMAP_SHADER)
				.setTextureState(new RenderStateShard.TextureStateShard(texture, false, false))
				.setTransparencyState(TRANSLUCENT_TRANSPARENCY)
				.setCullState(NO_CULL)
				.createCompositeState(outline);
		return RenderType.create(Reference.MODID + ":emmisive_translucent", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, RenderType.TRANSIENT_BUFFER_SIZE, true, true, state);
	};

	public static RenderType getChessPieceSolid(ResourceLocation texture)
	{
		RenderType.CompositeState state = RenderType.CompositeState.builder()
				.setShaderState(RENDERTYPE_SOLID_BLOCK_ENTITY_SHADER)
				.setTextureState(new RenderStateShard.TextureStateShard(texture, false, false))
				.setTransparencyState(NO_TRANSPARENCY)
				.setLightmapState(LIGHTMAP)
				.setOverlayState(OVERLAY)
				.createCompositeState(true);
		return RenderType.create(Reference.MODID + ":chess_piece_solid", DefaultVertexFormat.BLOCK, VertexFormat.Mode.TRIANGLES, RenderType.TRANSIENT_BUFFER_SIZE, true, false, state);
	};
}