package moe.plushie.table_top_craft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

/**
 * ChessPawn - andrew0030
 * Created using Tabula 7.0.0
 */
public class ChessPawn extends ModelBase {
    public ModelRenderer Base;
    public ModelRenderer LowerLegRight;
    public ModelRenderer LoweLegLeft;
    public ModelRenderer LowerBody;
    public ModelRenderer UpperLegRight;
    public ModelRenderer UpperLegLeft;
    public ModelRenderer UpperBody;
    public ModelRenderer UpperArmRight;
    public ModelRenderer UpperArmLeft;
    public ModelRenderer Head;
    public ModelRenderer LowerArmRight;
    public ModelRenderer WeaponGrip;
    public ModelRenderer WeaponTop;
    public ModelRenderer WeaponSpikeRight;
    public ModelRenderer WeaponSpikeMid;
    public ModelRenderer WeaponSpikeLeft;
    public ModelRenderer LowerArmLeft;

    public ChessPawn() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.WeaponGrip = new ModelRenderer(this, 0, 27);
        this.WeaponGrip.setRotationPoint(0.5F, 0.0F, -5.0F);
        this.WeaponGrip.addBox(0.0F, 2.6F, 0.0F, 1, 1, 16, 0.0F);
        this.WeaponSpikeRight = new ModelRenderer(this, 0, 34);
        this.WeaponSpikeRight.setRotationPoint(0.0F, 0.0F, -4.0F);
        this.WeaponSpikeRight.addBox(0.0F, 0.0F, 0.0F, 1, 1, 4, 0.0F);
        this.WeaponSpikeMid = new ModelRenderer(this, 0, 27);
        this.WeaponSpikeMid.setRotationPoint(2.0F, 0.0F, -5.0F);
        this.WeaponSpikeMid.addBox(0.0F, 0.0F, 0.0F, 1, 1, 5, 0.0F);
        this.Head = new ModelRenderer(this, 31, 14);
        this.Head.setRotationPoint(-0.5F, 4.0F, -0.3F);
        this.Head.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(Head, -0.1651081472386636F, 0.3150319299849765F, -0.09005898940290741F);
        this.UpperBody = new ModelRenderer(this, 18, 0);
        this.UpperBody.setRotationPoint(0.0F, 0.0F, 2.0F);
        this.UpperBody.addBox(0.0F, 0.0F, 0.0F, 4, 4, 2, 0.0F);
        this.setRotateAngle(UpperBody, -2.9595548126067843F, 0.0F, 0.0F);
        this.LowerArmRight = new ModelRenderer(this, 31, 7);
        this.LowerArmRight.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.LowerArmRight.addBox(0.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(LowerArmRight, 2.7317893452215247F, 0.0F, 0.18203784098300857F);
        this.WeaponTop = new ModelRenderer(this, 0, 40);
        this.WeaponTop.setRotationPoint(-2.0F, 2.6F, -1.0F);
        this.WeaponTop.addBox(0.0F, 0.0F, 0.0F, 5, 1, 1, 0.0F);
        this.LowerBody = new ModelRenderer(this, 18, 7);
        this.LowerBody.setRotationPoint(6.0F, -10.5F, 10.2F);
        this.LowerBody.addBox(0.0F, 0.0F, 0.0F, 4, 3, 2, 0.0F);
        this.setRotateAngle(LowerBody, 0.0F, 0.3326088016698561F, 0.0F);
        this.UpperLegLeft = new ModelRenderer(this, 9, 7);
        this.UpperLegLeft.setRotationPoint(0.0F, 0.6F, 1.8F);
        this.UpperLegLeft.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(UpperLegLeft, 2.6406831582674206F, 0.0F, -0.10506882097005864F);
        this.WeaponSpikeLeft = new ModelRenderer(this, 0, 21);
        this.WeaponSpikeLeft.setRotationPoint(4.0F, 0.0F, -4.0F);
        this.WeaponSpikeLeft.addBox(0.0F, 0.0F, 0.0F, 1, 1, 4, 0.0F);
        this.UpperLegRight = new ModelRenderer(this, 0, 7);
        this.UpperLegRight.setRotationPoint(0.0F, 0.6F, 2.0F);
        this.UpperLegRight.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(UpperLegRight, 2.8228955321756284F, 0.0F, 0.12007865253720987F);
        this.LowerArmLeft = new ModelRenderer(this, 40, 7);
        this.LowerArmLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.LowerArmLeft.addBox(0.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(LowerArmLeft, 2.7317893452215247F, 0.0F, 0.0F);
        this.UpperArmLeft = new ModelRenderer(this, 40, 0);
        this.UpperArmLeft.setRotationPoint(4.7F, 0.0F, 0.0F);
        this.UpperArmLeft.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(UpperArmLeft, 0.0F, 0.0F, 0.1651081472386636F);
        this.LowerLegRight = new ModelRenderer(this, 0, 14);
        this.LowerLegRight.setRotationPoint(5.5F, -4.0F, 8.0F);
        this.LowerLegRight.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.LoweLegLeft = new ModelRenderer(this, 9, 14);
        this.LoweLegLeft.setRotationPoint(8.6F, -3.5F, 9.2F);
        this.LoweLegLeft.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(LoweLegLeft, 0.36425021489121656F, 0.31869712141416456F, -0.091106186954104F);
        this.Base = new ModelRenderer(this, 0, 45);
        this.Base.setRotationPoint(0.2F, 0.0F, 0.2F);
        this.Base.addBox(0.0F, 0.0F, 0.0F, 16, 3, 16, 0.0F);
        this.UpperArmRight = new ModelRenderer(this, 31, 0);
        this.UpperArmRight.setRotationPoint(-2.6F, 1.2F, 3.3F);
        this.UpperArmRight.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(UpperArmRight, -1.2747884856566583F, -0.045553093477052F, -0.18203784098300857F);
        this.LowerArmRight.addChild(this.WeaponGrip);
        this.WeaponTop.addChild(this.WeaponSpikeRight);
        this.WeaponTop.addChild(this.WeaponSpikeMid);
        this.UpperBody.addChild(this.Head);
        this.LowerBody.addChild(this.UpperBody);
        this.UpperArmRight.addChild(this.LowerArmRight);
        this.WeaponGrip.addChild(this.WeaponTop);
        this.Base.addChild(this.LowerBody);
        this.LoweLegLeft.addChild(this.UpperLegLeft);
        this.WeaponTop.addChild(this.WeaponSpikeLeft);
        this.LowerLegRight.addChild(this.UpperLegRight);
        this.UpperArmLeft.addChild(this.LowerArmLeft);
        this.UpperBody.addChild(this.UpperArmLeft);
        this.Base.addChild(this.LowerLegRight);
        this.Base.addChild(this.LoweLegLeft);
        this.UpperBody.addChild(this.UpperArmRight);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.Base.offsetX, this.Base.offsetY, this.Base.offsetZ);
        GlStateManager.translate(this.Base.rotationPointX * f5, this.Base.rotationPointY * f5, this.Base.rotationPointZ * f5);
        GlStateManager.scale(0.1D, 0.1D, 0.1D);
        GlStateManager.translate(-this.Base.offsetX, -this.Base.offsetY, -this.Base.offsetZ);
        GlStateManager.translate(-this.Base.rotationPointX * f5, -this.Base.rotationPointY * f5, -this.Base.rotationPointZ * f5);
        this.Base.render(f5);
        GlStateManager.popMatrix();
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
