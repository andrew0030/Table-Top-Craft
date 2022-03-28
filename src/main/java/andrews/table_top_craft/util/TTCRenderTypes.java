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

	/*
	public static RenderType getEmissiveTransluscent(ResourceLocation texture, boolean outline)
	{
		RenderType.State state = RenderType.State.getBuilder().texture(new RenderState.TextureState(texture, false, false))
															  .transparency(TRANSLUCENT_TRANSPARENCY)
															  .diffuseLighting(DIFFUSE_LIGHTING_DISABLED)
															  .alpha(DEFAULT_ALPHA)
															  .cull(CULL_DISABLED)
															  .lightmap(LIGHTMAP_ENABLED)
															  .overlay(OVERLAY_ENABLED)
															  .build(outline);
		return RenderType.makeType(Reference.MODID + ":emmisive_translucent", DefaultVertexFormats.ENTITY, 7, 256, true, true, state);
	}
	 */

	public static RenderType getEmissiveTransluscent(ResourceLocation texture, boolean outline)
	{
		RenderType.CompositeState state = RenderType.CompositeState.builder()
				.setShaderState(NEW_ENTITY_SHADER)
				.setTextureState(new RenderStateShard.TextureStateShard(texture, false, false))
				.setTransparencyState(TRANSLUCENT_TRANSPARENCY)
				.setCullState(NO_CULL)
				.setWriteMaskState(COLOR_WRITE)
				.createCompositeState(outline);
		return RenderType.create(Reference.MODID + ":emmisive_translucent", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, RenderType.TRANSIENT_BUFFER_SIZE, true, true, state);
	};

	/*
	public static RenderType getChessPieceSolid(ResourceLocation texture)
	{
		RenderType.State state = RenderType.State.getBuilder()
															  .texture(new RenderState.TextureState(texture, false, false))
															  .texturing(DEFAULT_TEXTURING)
															  .transparency(NO_TRANSPARENCY)
															  .diffuseLighting(DIFFUSE_LIGHTING_ENABLED)
															  .lightmap(LIGHTMAP_ENABLED)
															  .build(true);
		return RenderType.makeType(Reference.MODID + ":chess_piece_solid", DefaultVertexFormats.BLOCK, GL11.GL_TRIANGLES, 2097152, true, false, state);
	}
	 */

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

//		RenderType.CompositeState state = RenderType.CompositeState.builder()
//				.setShaderState(RENDERTYPE_SOLID_SHADER)
//				.setTextureState(new RenderStateShard.TextureStateShard(texture, false, false))
//				.setLightmapState(LIGHTMAP)
//				.createCompositeState(true);
//
//		return RenderType.create(Reference.MODID + ":chess_piece_solid", DefaultVertexFormat.BLOCK, VertexFormat.Mode.TRIANGLES, RenderType.TRANSIENT_BUFFER_SIZE, true, false, state);
	};
}