package andrews.table_top_craft.tile_entities.model.chess;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChessBoardPlateModel
{
    public ModelRenderer plate;

    public ChessBoardPlateModel()
    {
    	int[] textureSize = {64, 32};
        this.plate = new ModelRenderer(textureSize[0], textureSize[1], 0, 0);
        this.plate.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.plate.addBox(-8.0F, 0.0F, -8.0F, 16, 0, 16, 0.0F);
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
