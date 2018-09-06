package moe.plushie.table_top_craft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

/**
 * ChessRook - andrew0030
 * Created using Tabula 7.0.0
 */
public class ChessRook extends ModelBase {
    public ModelRenderer GolemBodyTop;
    public ModelRenderer Base;
    public ModelRenderer GolemBodyBottom;
    public ModelRenderer GolemArmRight;
    public ModelRenderer GolemArmLeft;
    public ModelRenderer GolemHead;
    public ModelRenderer GolemLegRight;
    public ModelRenderer GolemLegLeft;
    public ModelRenderer GolemNose;
    public ModelRenderer BodyBottom;
    public ModelRenderer BodyTop;
    public ModelRenderer LegTopRight;
    public ModelRenderer LegTopLeft;
    public ModelRenderer ArmTopRight;
    public ModelRenderer ArmTopLeft;
    public ModelRenderer Head;
    public ModelRenderer ArmBottomRight;
    public ModelRenderer ArmBottomLeft;
    public ModelRenderer LegBottomRight;
    public ModelRenderer LegBottomLeft;

    public ChessRook() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.ArmTopRight = new ModelRenderer(this, 83, 0);
        this.ArmTopRight.setRotationPoint(0.0F, -4.0F, -2.0F);
        this.ArmTopRight.addBox(-2.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(ArmTopRight, -0.8726646259971648F, 0.0F, 0.0F);
        this.GolemArmLeft = new ModelRenderer(this, 63, 58);
        this.GolemArmLeft.setRotationPoint(1.0F, 0.0F, 0.0F);
        this.GolemArmLeft.addBox(8.0F, -2.5F, -3.0F, 4, 30, 6, 0.0F);
        this.setRotateAngle(GolemArmLeft, -1.284736862393026F, 0.0F, 0.0F);
        this.GolemNose = new ModelRenderer(this, 24, 0);
        this.GolemNose.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.GolemNose.addBox(-1.0F, -5.0F, -7.5F, 2, 4, 2, 0.0F);
        this.ArmTopLeft = new ModelRenderer(this, 92, 0);
        this.ArmTopLeft.setRotationPoint(4.0F, -4.0F, -2.0F);
        this.ArmTopLeft.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(ArmTopLeft, -0.8726646259971648F, 0.0F, 0.0F);
        this.GolemBodyTop = new ModelRenderer(this, 0, 40);
        this.GolemBodyTop.setRotationPoint(1.0F, -1.7F, 1.0F);
        this.GolemBodyTop.addBox(-10.0F, -2.0F, -6.0F, 20, 12, 11, 0.0F);
        this.setRotateAngle(GolemBodyTop, 0.890117918517108F, 0.0F, 0.0F);
        this.LegBottomRight = new ModelRenderer(this, 101, 7);
        this.LegBottomRight.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.LegBottomRight.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(LegBottomRight, 0.8726646259971648F, 0.0F, 0.0F);
        this.GolemLegLeft = new ModelRenderer(this, 60, 0);
        this.GolemLegLeft.mirror = true;
        this.GolemLegLeft.setRotationPoint(5.0F, 18.0F, 0.0F);
        this.GolemLegLeft.addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, 0.0F);
        this.setRotateAngle(GolemLegLeft, -0.5061454830783556F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 32);
        this.Head.setRotationPoint(0.0F, -8.0F, -3.0F);
        this.Head.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(Head, 0.07051130178057091F, 0.0F, 0.0F);
        this.BodyTop = new ModelRenderer(this, 0, 19);
        this.BodyTop.setRotationPoint(0.0F, 0.0F, 2.0F);
        this.BodyTop.addBox(0.0F, -4.0F, -2.0F, 4, 4, 2, 0.0F);
        this.setRotateAngle(BodyTop, 0.5696754678509492F, 0.0F, 0.0F);
        this.ArmBottomRight = new ModelRenderer(this, 83, 7);
        this.ArmBottomRight.setRotationPoint(0.0F, 4.0F, 2.0F);
        this.ArmBottomRight.addBox(-2.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(ArmBottomRight, -0.8377580409572781F, 0.0F, 0.0F);
        this.GolemHead = new ModelRenderer(this, 0, 0);
        this.GolemHead.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.GolemHead.addBox(-4.0F, -12.0F, -5.5F, 8, 10, 8, 0.0F);
        this.setRotateAngle(GolemHead, -0.36425021489121656F, 0.0F, 0.0F);
        this.Base = new ModelRenderer(this, 0, 82);
        this.Base.setRotationPoint(0.2F, 0.0F, 0.2F);
        this.Base.addBox(0.0F, 0.0F, 0.0F, 16, 3, 16, 0.0F);
        this.ArmBottomLeft = new ModelRenderer(this, 92, 7);
        this.ArmBottomLeft.setRotationPoint(0.0F, 4.0F, 2.0F);
        this.ArmBottomLeft.addBox(0.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(ArmBottomLeft, -0.8377580409572781F, 0.0F, 0.0F);
        this.GolemArmRight = new ModelRenderer(this, 63, 21);
        this.GolemArmRight.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.GolemArmRight.addBox(-13.0F, -2.5F, -3.0F, 4, 30, 6, 0.0F);
        this.setRotateAngle(GolemArmRight, -1.284736862393026F, 0.0F, 0.0F);
        this.BodyBottom = new ModelRenderer(this, 0, 26);
        this.BodyBottom.setRotationPoint(6.0F, -24.0F, 7.0F);
        this.BodyBottom.addBox(0.0F, 0.0F, 0.0F, 4, 3, 2, 0.0F);
        this.setRotateAngle(BodyBottom, -0.136659280431156F, 0.0F, 0.0F);
        this.GolemBodyBottom = new ModelRenderer(this, 0, 70);
        this.GolemBodyBottom.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.GolemBodyBottom.addBox(-4.5F, 10.0F, -3.0F, 9, 5, 6, 0.0F);
        this.setRotateAngle(GolemBodyBottom, -0.33161255787892263F, 0.0F, 0.0F);
        this.GolemLegRight = new ModelRenderer(this, 37, 0);
        this.GolemLegRight.setRotationPoint(-4.0F, 18.0F, 0.0F);
        this.GolemLegRight.addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, 0.0F);
        this.setRotateAngle(GolemLegRight, -0.5061454830783556F, 0.0F, 0.0F);
        this.LegTopRight = new ModelRenderer(this, 101, 0);
        this.LegTopRight.setRotationPoint(-1.0F, 3.0F, 1.5F);
        this.LegTopRight.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(LegTopRight, -1.2915436464758039F, 0.4363323129985824F, 0.0F);
        this.LegBottomLeft = new ModelRenderer(this, 110, 7);
        this.LegBottomLeft.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.LegBottomLeft.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(LegBottomLeft, 0.8726646259971648F, 0.0F, 0.0F);
        this.LegTopLeft = new ModelRenderer(this, 110, 0);
        this.LegTopLeft.setRotationPoint(3.0F, 3.0F, 1.5F);
        this.LegTopLeft.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(LegTopLeft, -1.2915436464758039F, -0.4363323129985824F, 0.0F);
        this.BodyTop.addChild(this.ArmTopRight);
        this.GolemBodyTop.addChild(this.GolemArmLeft);
        this.GolemHead.addChild(this.GolemNose);
        this.BodyTop.addChild(this.ArmTopLeft);
        this.LegTopRight.addChild(this.LegBottomRight);
        this.GolemBodyBottom.addChild(this.GolemLegLeft);
        this.BodyTop.addChild(this.Head);
        this.BodyBottom.addChild(this.BodyTop);
        this.ArmTopRight.addChild(this.ArmBottomRight);
        this.GolemBodyTop.addChild(this.GolemHead);
        this.ArmTopLeft.addChild(this.ArmBottomLeft);
        this.GolemBodyTop.addChild(this.GolemArmRight);
        this.Base.addChild(this.BodyBottom);
        this.GolemBodyTop.addChild(this.GolemBodyBottom);
        this.GolemBodyBottom.addChild(this.GolemLegRight);
        this.BodyBottom.addChild(this.LegTopRight);
        this.LegTopLeft.addChild(this.LegBottomLeft);
        this.BodyBottom.addChild(this.LegTopLeft);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.GolemBodyTop.offsetX, this.GolemBodyTop.offsetY, this.GolemBodyTop.offsetZ);
        GlStateManager.translate(this.GolemBodyTop.rotationPointX * f5, this.GolemBodyTop.rotationPointY * f5, this.GolemBodyTop.rotationPointZ * f5);
        GlStateManager.scale(0.06D, 0.06D, 0.06D);
        GlStateManager.translate(-this.GolemBodyTop.offsetX, -this.GolemBodyTop.offsetY, -this.GolemBodyTop.offsetZ);
        GlStateManager.translate(-this.GolemBodyTop.rotationPointX * f5, -this.GolemBodyTop.rotationPointY * f5, -this.GolemBodyTop.rotationPointZ * f5);
        this.GolemBodyTop.render(f5);
        GlStateManager.popMatrix();
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
