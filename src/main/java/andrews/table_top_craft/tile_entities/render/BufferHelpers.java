package andrews.table_top_craft.tile_entities.render;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;

public class BufferHelpers {
	public static void setupRender(ShaderInstance pShaderInstance) {
		RenderSystem.assertOnRenderThread();
		BufferUploader.reset();
		
		for (int i = 0; i < 12; ++i) {
			int j = RenderSystem.getShaderTexture(i);
			pShaderInstance.setSampler("Sampler" + i, j);
		}
		
		if (pShaderInstance.INVERSE_VIEW_ROTATION_MATRIX != null) pShaderInstance.INVERSE_VIEW_ROTATION_MATRIX.set(RenderSystem.getInverseViewRotationMatrix());
		if (pShaderInstance.COLOR_MODULATOR != null) pShaderInstance.COLOR_MODULATOR.set(RenderSystem.getShaderColor());
		if (pShaderInstance.FOG_START != null) pShaderInstance.FOG_START.set(RenderSystem.getShaderFogStart());
		if (pShaderInstance.FOG_END != null) pShaderInstance.FOG_END.set(RenderSystem.getShaderFogEnd());
		if (pShaderInstance.FOG_COLOR != null) pShaderInstance.FOG_COLOR.set(RenderSystem.getShaderFogColor());
		if (pShaderInstance.FOG_SHAPE != null) pShaderInstance.FOG_SHAPE.set(RenderSystem.getShaderFogShape().getIndex());
		if (pShaderInstance.TEXTURE_MATRIX != null) pShaderInstance.TEXTURE_MATRIX.set(RenderSystem.getTextureMatrix());
		if (pShaderInstance.GAME_TIME != null) pShaderInstance.GAME_TIME.set(RenderSystem.getShaderGameTime());
//		if (pShaderInstance.LINE_WIDTH != null && (this.mode == VertexFormat.Mode.LINES || this.mode == VertexFormat.Mode.LINE_STRIP))
//			pShaderInstance.LINE_WIDTH.set(RenderSystem.getShaderLineWidth());
		if (pShaderInstance.SCREEN_SIZE != null) {
			Window window = Minecraft.getInstance().getWindow();
			pShaderInstance.SCREEN_SIZE.set((float) window.getWidth(), (float) window.getHeight());
		}
		RenderSystem.setupShaderLights(pShaderInstance);
	}
	
	public static void updateColor(ShaderInstance pShaderInstance) {
		if (pShaderInstance.COLOR_MODULATOR != null) pShaderInstance.COLOR_MODULATOR.set(RenderSystem.getShaderColor());
	}
	
	public static void draw(VertexBuffer buffer, ShaderInstance pShaderInstance) {
		buffer.bindVertexArray();
		buffer.bind();
		buffer.getFormat().setupBufferState();
		pShaderInstance.apply();
//		RenderSystem.drawElements(buffer.mode.asGLMode, buffer.indexCount, buffer.indexType.asGLType);
		buffer.draw();
		pShaderInstance.clear();
		buffer.getFormat().clearBufferState();
		teardownRender();
	}
	
	public static void teardownRender() {
		VertexBuffer.unbind();
		VertexBuffer.unbindVertexArray();
	}
}
