package andrews.table_top_craft.tile_entities.render;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;

public class BufferHelpers {
	public static void setupRender(ShaderInstance pShaderInstance, int lightU, int ilghtV /* GiantLuigi4 (Jason): no I will not correct this typo */) {
		Uniform uniform = pShaderInstance.getUniform("LightUV");
		RenderSystem.assertOnRenderThread();
		BufferUploader.reset();

//		RenderSystem.setShaderTexture(1, );
		Minecraft.getInstance().gameRenderer.lightTexture().turnOnLightLayer();
		
		for (int i = 0; i < 12; ++i) {
			int j = RenderSystem.getShaderTexture(i);
			pShaderInstance.setSampler("Sampler" + i, j);
		}
		
		if (uniform != null) uniform.set((float) lightU, ilghtV);
		if (pShaderInstance.INVERSE_VIEW_ROTATION_MATRIX != null) pShaderInstance.INVERSE_VIEW_ROTATION_MATRIX.set(RenderSystem.getInverseViewRotationMatrix());
		if (pShaderInstance.COLOR_MODULATOR != null) pShaderInstance.COLOR_MODULATOR.set(RenderSystem.getShaderColor());
		if (pShaderInstance.FOG_START != null) pShaderInstance.FOG_START.set(RenderSystem.getShaderFogStart());
		if (pShaderInstance.FOG_END != null) pShaderInstance.FOG_END.set(RenderSystem.getShaderFogEnd());
		if (pShaderInstance.FOG_COLOR != null) pShaderInstance.FOG_COLOR.set(RenderSystem.getShaderFogColor());
		if (pShaderInstance.FOG_SHAPE != null)
			pShaderInstance.FOG_SHAPE.set(RenderSystem.getShaderFogShape().getIndex());
		if (pShaderInstance.TEXTURE_MATRIX != null) pShaderInstance.TEXTURE_MATRIX.set(RenderSystem.getTextureMatrix());
		if (pShaderInstance.GAME_TIME != null) pShaderInstance.GAME_TIME.set(RenderSystem.getShaderGameTime());
//		if (pShaderInstance.LINE_WIDTH != null && (this.modae == VertexFormat.Mode.LINES || this.mode == VertexFormat.Mode.LINE_STRIP))
//			pShaderInstance.LINE_WIDTH.set(RenderSystem.getShaderLineWidth());
		if (pShaderInstance.SCREEN_SIZE != null) {
			Window window = Minecraft.getInstance().getWindow();
			pShaderInstance.SCREEN_SIZE.set((float) window.getWidth(), (float) window.getHeight());
		}
//		RenderSystem.setupShaderLights(pShaderInstance);
		if (pShaderInstance.LIGHT0_DIRECTION != null)
			pShaderInstance.LIGHT0_DIRECTION.set(0.457495710997814F, 0.7624928516630234F, -0.457495710997814F);
		if (pShaderInstance.LIGHT1_DIRECTION != null)
			pShaderInstance.LIGHT1_DIRECTION.set(-0.27617238536949695F, 0.9205746178983233F, 0.27617238536949695F);
	}
	
	public static void setMatrix(Matrix4f mat, ShaderInstance pShaderInstance) {
		if (pShaderInstance.MODEL_VIEW_MATRIX != null) pShaderInstance.MODEL_VIEW_MATRIX.set(mat);
		mat = mat.copy();
		mat.invert();
		if (pShaderInstance.INVERSE_VIEW_ROTATION_MATRIX != null) pShaderInstance.INVERSE_VIEW_ROTATION_MATRIX.set(mat);
	}
	
	public static void updateColor(ShaderInstance pShaderInstance) {
		if (pShaderInstance.COLOR_MODULATOR != null) pShaderInstance.COLOR_MODULATOR.set(RenderSystem.getShaderColor());
	}
	
	public static void draw(VertexBuffer buffer, ShaderInstance pShaderInstance) {
		//buffer.bindVertexArray(); TODO fix this
//		buffer.bind();
//		buffer.getFormat().setupBufferState();
//		pShaderInstance.apply();
//		RenderSystem.drawElements(buffer.mode.asGLMode, buffer.indexCount, buffer.indexType.asGLType);
//		buffer.draw();
//		pShaderInstance.clear();
//		buffer.getFormat().clearBufferState();
//		teardownRender();
	}
	
	public static void teardownRender() {
//		VertexBuffer.unbind();
		//VertexBuffer.unbindVertexArray(); TODO fix this
	}
}