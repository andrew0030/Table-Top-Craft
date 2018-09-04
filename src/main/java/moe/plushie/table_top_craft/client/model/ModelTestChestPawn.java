package moe.plushie.table_top_craft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * TestChessPawn - andrew0030
 * Created using Tabula 7.0.0
 */
@SideOnly(Side.CLIENT)
public class ModelTestChestPawn extends ModelBase {
    
    public ModelRenderer shape1;
    public ModelRenderer figure;

    public ModelTestChestPawn() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.figure = new ModelRenderer(this, 9, 0);
        this.figure.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.figure.addBox(0.5F, -3.0F, 0.5F, 1, 3, 1, 0.0F);
        this.shape1 = new ModelRenderer(this, 0, 0);
        this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape1.addBox(0.0F, 0.0F, 0.0F, 2, 1, 2, 0.0F);
        this.shape1.addChild(this.figure);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.shape1.render(f5);
        figure.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
