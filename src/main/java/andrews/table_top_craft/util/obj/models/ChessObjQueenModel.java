package andrews.table_top_craft.util.obj.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import andrews.table_top_craft.util.Reference;
import andrews.table_top_craft.util.obj.ObjModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.util.ResourceLocation;

public class ChessObjQueenModel extends Model
{
	private static final String MODEL_PATH = "models/pieces/queen.obj";
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/tile/chess/piece.png");

	private final ObjModel QUEEN_MODEL;
	
	public ChessObjQueenModel()
	{
		super(RenderType::getEntitySolid);
		QUEEN_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, MODEL_PATH));
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)
	{
		
	}
	
	public void render()
	{
		QUEEN_MODEL.render();
	}
}
