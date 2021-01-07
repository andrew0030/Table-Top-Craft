package andrews.table_top_craft.util.obj.models;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import andrews.table_top_craft.util.Reference;
import andrews.table_top_craft.util.obj.ObjModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.profiler.IProfiler;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

public class ChessObjQueenModel extends Model
{
	private static final String MODEL_PATH = "models/pieces/queen.obj";
	
	private final ObjModel QUEEN_MODEL;
	
	public ChessObjQueenModel()
	{
		super(RenderType::getEntitySolid);
		QUEEN_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, MODEL_PATH));
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {}
	
	public void render()
	{	
		GL11.glPushMatrix();
		GL11.glScalef(1F, -1F, -1F);
		GL11.glScaled(0.1, 0.1, 0.1);
		QUEEN_MODEL.render();
		GL11.glPopMatrix();
	}
}