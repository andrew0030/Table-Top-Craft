package andrews.table_top_craft.tile_entities.model.chess;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChessHighlightModel
{
    public ModelRenderer highlight;

    public ChessHighlightModel()
    {
    	int[] textureSize = {16, 16};
        this.highlight = new ModelRenderer(textureSize[0], textureSize[1], 0, 0);
        this.highlight.setRotationPoint(0.0F, 22.7F, 0.0F);
        this.highlight.addBox(-1.0F, 0.0F, -1.0F, 2, 1, 2, 0.0F);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
