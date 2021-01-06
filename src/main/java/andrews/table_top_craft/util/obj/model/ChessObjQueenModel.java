package andrews.table_top_craft.util.obj.model;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import andrews.table_top_craft.util.Reference;
import andrews.table_top_craft.util.obj.ObjModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.util.ResourceLocation;

public class ChessObjQueenModel extends Model
{
	private static final String MODEL_PATH = "models/pieces/queen.obj";
//  private static final String MODEL_PATH_LOD = "models/dakimakura-lod-%d.obj";
    
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/tile/chess/piece.png");
    
    private final ObjModel QUEEN_MODEL;
//  private final ObjModel[] DAKIMAKURA_MODEL_LODS;
    
//    private final Profiler profiler;
    
	public ChessObjQueenModel()
	{
		super(RenderType::getEntitySolid);
		QUEEN_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, MODEL_PATH));
//        DAKIMAKURA_MODEL_LODS = new ObjModel[4];
//        for(int i = 0; i < DAKIMAKURA_MODEL_LODS.length; i++)
//        {
//            DAKIMAKURA_MODEL_LODS[i] = ObjModel.loadModel(new ResourceLocation(LibModInfo.ID, String.format(MODEL_PATH_LOD, i + 1)));
//        }
//        this.dakiTextureManager = dakiTextureManager;
//        profiler = (Profiler) Minecraft.getInstance().getProfiler();
		
	}
    
    @Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)
	{
		matrixStackIn.push();
//		GL11.glPushAttrib(GL11.GL_POLYGON_BIT | GL11.GL_ENABLE_BIT | GL11.GL_LIGHTING_BIT);
//		
//		 matrixStackIn.scale(0.55F, 0.55F, 0.55F);
//		 matrixStackIn.translate(0, 0.35F, 0);
//		 matrixStackIn.scale(-1, -1, 1);
//		 
//		 GL11.glCullFace(GL11.GL_BACK);
//		 GL11.glEnable(GL11.GL_CULL_FACE);
//		 GL11.glEnable(GL11.GL_NORMALIZE);
//		 GL11.glEnable(GL11.GL_LIGHTING);
//		 
//		 Minecraft.getInstance().getRenderManager().textureManager.bindTexture(TEXTURE);
		 QUEEN_MODEL.render();
		 
		 matrixStackIn.pop();
	}
}
