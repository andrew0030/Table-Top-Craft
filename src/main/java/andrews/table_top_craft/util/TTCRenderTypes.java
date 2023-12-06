package andrews.table_top_craft.util;

import andrews.table_top_craft.TableTopCraft;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class TTCRenderTypes extends RenderStateShard
{
	private static final RenderStateShard.ShaderStateShard RENDERTYPE_SOLID_BLOCK_ENTITY_SHADER = new RenderStateShard.ShaderStateShard(TableTopCraft::getSolidBlockEntityShader);

	public TTCRenderTypes(String nameIn, Runnable setupTaskIn, Runnable clearTaskIn)
	{
		super(nameIn, setupTaskIn, clearTaskIn);
	}

	public static RenderType getEmissiveTransluscent(ResourceLocation texture, boolean outline)
	{
		RenderType.CompositeState state = RenderType.CompositeState.builder()
				.setShaderState(RenderStateShard.RENDERTYPE_CUTOUT_SHADER)
				.setTextureState(new RenderStateShard.TextureStateShard(texture, false, false))
				.setTransparencyState(TRANSLUCENT_TRANSPARENCY)
				.setCullState(NO_CULL)
				.setLightmapState(LIGHTMAP)
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

	public static RenderType skeletonDebugLines()
	{
		RenderType.CompositeState state = RenderType.CompositeState.builder()
				.setShaderState(POSITION_COLOR_SHADER) // The used Shader
				.setCullState(NO_CULL) // No Cull so we see it from everywhere
				.setDepthTestState(NO_DEPTH_TEST) // We render over everything
				.createCompositeState(false);
		return RenderType.create(Reference.MODID+":skeleton_debug_lines", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.DEBUG_LINES, RenderType.TRANSIENT_BUFFER_SIZE, false, false, state);
	};

	public static RenderType skeletonDebugQuad()
	{
		RenderType.CompositeState state = RenderType.CompositeState.builder()
				.setShaderState(POSITION_COLOR_SHADER) // The used Shader
				.setDepthTestState(NO_DEPTH_TEST) // We render over everything
				.createCompositeState(false);
		return RenderType.create(Reference.MODID+":skeleton_debug_quad", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, RenderType.TRANSIENT_BUFFER_SIZE, false, false, state);
	};
}