package andrews.table_top_craft.tile_entities.model.chess;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChessTilesInfoModel
{
	public ModelRenderer base;

    public ChessTilesInfoModel()
    {
        int[] textureSize = {64, 64};
        this.base = new ModelRenderer(textureSize[0], textureSize[1], -20, 0);
        this.base.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.base.addBox(-10.0F, 0.0F, -10.0F, 20, 0, 20, 0.0F);
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
