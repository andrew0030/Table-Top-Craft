package andrews.table_top_craft.tile_entities.render;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.VertexBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;

public class BufferHelpers {
	public static boolean useFallbackSystem = true;
	public static boolean shouldRefresh = true;

	public static void setupRender(ShaderInstance pShaderInstance, int lightU, int ilghtV /* GiantLuigi4 (Jason): no I will not correct this typo */) {
		pShaderInstance.apply();
		if (shouldRefresh || useFallbackSystem) {
			RenderSystem.assertOnRenderThread();
			BufferUploader.reset();

			Minecraft.getInstance().gameRenderer.lightTexture().turnOnLightLayer();

			for (int i = 0; i < 12; ++i) {
				int j = RenderSystem.getShaderTexture(i);
				pShaderInstance.setSampler("Sampler" + i, j);
			}

			if (pShaderInstance.COLOR_MODULATOR != null)
				pShaderInstance.COLOR_MODULATOR.set(RenderSystem.getShaderColor());
			if (pShaderInstance.TEXTURE_MATRIX != null)
				pShaderInstance.TEXTURE_MATRIX.set(RenderSystem.getTextureMatrix());
			if (pShaderInstance.GAME_TIME != null) pShaderInstance.GAME_TIME.set(RenderSystem.getShaderGameTime());
			if (pShaderInstance.SCREEN_SIZE != null) {
				Window window = Minecraft.getInstance().getWindow();
				pShaderInstance.SCREEN_SIZE.set((float) window.getWidth(), (float) window.getHeight());
			}
			if (pShaderInstance.LIGHT0_DIRECTION != null)
				pShaderInstance.LIGHT0_DIRECTION.set(0.457495710997814F, 0.7624928516630234F, -0.457495710997814F);
			if (pShaderInstance.LIGHT1_DIRECTION != null)
				pShaderInstance.LIGHT1_DIRECTION.set(-0.27617238536949695F, 0.9205746178983233F, 0.27617238536949695F);

			shouldRefresh = false;
		}

		if (pShaderInstance.FOG_START != null) {
			pShaderInstance.FOG_START.set(RenderSystem.getShaderFogStart());
			pShaderInstance.FOG_START.upload();
		}
		if (pShaderInstance.FOG_END != null) {
			pShaderInstance.FOG_END.set(RenderSystem.getShaderFogEnd());
			pShaderInstance.FOG_END.upload();
		}
		if (pShaderInstance.FOG_COLOR != null) {
			pShaderInstance.FOG_COLOR.set(RenderSystem.getShaderFogColor());
			pShaderInstance.FOG_COLOR.upload();
		}
		if (pShaderInstance.FOG_SHAPE != null) {
			pShaderInstance.FOG_SHAPE.set(RenderSystem.getShaderFogShape().getIndex());
			pShaderInstance.FOG_SHAPE.upload();
		}

		Uniform uniform = pShaderInstance.getUniform("LightUV");
		if (uniform != null) {
			uniform.set((float) lightU, ilghtV);
			uniform.upload();
		}
		if (pShaderInstance.INVERSE_VIEW_ROTATION_MATRIX != null) {
			pShaderInstance.INVERSE_VIEW_ROTATION_MATRIX.set(RenderSystem.getInverseViewRotationMatrix());
			pShaderInstance.INVERSE_VIEW_ROTATION_MATRIX.upload();
		}
		if (pShaderInstance.PROJECTION_MATRIX != null)
			pShaderInstance.PROJECTION_MATRIX.upload();
	}

	public static void updateColor(ShaderInstance pShaderInstance, float[] floats) {
		if (pShaderInstance.COLOR_MODULATOR != null) pShaderInstance.COLOR_MODULATOR.set(floats);
	}

	public static void draw(VertexBuffer buffer) {
		ShaderInstance instance = RenderSystem.getShader();
		if (buffer != null) {
			if (instance.MODEL_VIEW_MATRIX != null) instance.MODEL_VIEW_MATRIX.upload();
			if (instance.COLOR_MODULATOR != null) instance.COLOR_MODULATOR.upload();
			buffer.bind();
			buffer.draw();
		}
	}
}