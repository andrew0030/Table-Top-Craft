package andrews.table_top_craft.tile_entities.model.chess;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChessPawnModel
{
	public ModelRenderer Base;
    public ModelRenderer LowerLegRight;
    public ModelRenderer LowerBody;
    public ModelRenderer UpperLegRight;
    public ModelRenderer UpperBody;
    public ModelRenderer UpperLegLeft;
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
    public ModelRenderer LoweLegLeft;

    public ChessPawnModel()
    {
    	int[] textureSize = {128, 128};
        this.UpperLegRight = new ModelRenderer(textureSize[0], textureSize[1], 0, 7);
        this.UpperLegRight.setRotationPoint(0.0F, 0.6F, 2.0F);
        this.UpperLegRight.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(UpperLegRight, 2.8228955321756284F, 0.0F, 0.12007865253720987F);
        this.LowerLegRight = new ModelRenderer(textureSize[0], textureSize[1], 0, 14);
        this.LowerLegRight.setRotationPoint(-2.5F, -7.0F, 0.0F);
        this.LowerLegRight.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.WeaponSpikeLeft = new ModelRenderer(textureSize[0], textureSize[1], 0, 21);
        this.WeaponSpikeLeft.setRotationPoint(4.0F, 0.0F, -4.0F);
        this.WeaponSpikeLeft.addBox(0.0F, 0.0F, 0.0F, 1, 1, 4, 0.0F);
        this.WeaponTop = new ModelRenderer(textureSize[0], textureSize[1], 0, 40);
        this.WeaponTop.setRotationPoint(-2.0F, 2.6F, -1.0F);
        this.WeaponTop.addBox(0.0F, 0.0F, 0.0F, 5, 1, 1, 0.0F);
        this.LoweLegLeft = new ModelRenderer(textureSize[0], textureSize[1], 9, 14);
        this.LoweLegLeft.setRotationPoint(0.01F, 4.0F, -1.0F);
        this.LoweLegLeft.addBox(-1.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(LoweLegLeft, 0.4705407663376713F, 0.0F, 0.0F);
        this.UpperBody = new ModelRenderer(textureSize[0], textureSize[1], 18, 0);
        this.UpperBody.setRotationPoint(0.0F, 0.0F, 2.0F);
        this.UpperBody.addBox(0.0F, 0.0F, 0.0F, 4, 4, 2, 0.0F);
        this.setRotateAngle(UpperBody, -2.9595548126067843F, 0.0F, 0.0F);
        this.WeaponGrip = new ModelRenderer(textureSize[0], textureSize[1], 0, 27);
        this.WeaponGrip.setRotationPoint(0.5F, 0.0F, -5.0F);
        this.WeaponGrip.addBox(0.0F, 2.6F, 0.0F, 1, 1, 16, 0.0F);
        this.LowerArmLeft = new ModelRenderer(textureSize[0], textureSize[1], 40, 7);
        this.LowerArmLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.LowerArmLeft.addBox(0.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(LowerArmLeft, 2.7317893452215247F, 0.0F, 0.0F);
        this.WeaponSpikeRight = new ModelRenderer(textureSize[0], textureSize[1], 0, 34);
        this.WeaponSpikeRight.setRotationPoint(0.0F, 0.0F, -4.0F);
        this.WeaponSpikeRight.addBox(0.0F, 0.0F, 0.0F, 1, 1, 4, 0.0F);
        this.LowerArmRight = new ModelRenderer(textureSize[0], textureSize[1], 31, 7);
        this.LowerArmRight.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.LowerArmRight.addBox(0.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(LowerArmRight, 2.7317893452215247F, 0.0F, 0.18203784098300857F);
        this.UpperLegLeft = new ModelRenderer(textureSize[0], textureSize[1], 9, 7);
        this.UpperLegLeft.setRotationPoint(3.0F, 3.0F, 1.0F);
        this.UpperLegLeft.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(UpperLegLeft, -0.18039820389254219F, -0.2902058062619157F, -0.20392840440026505F);
        this.UpperArmLeft = new ModelRenderer(textureSize[0], textureSize[1], 40, 0);
        this.UpperArmLeft.setRotationPoint(4.7F, 0.0F, 0.0F);
        this.UpperArmLeft.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(UpperArmLeft, 0.0F, 0.0F, 0.1651081472386636F);
        this.WeaponSpikeMid = new ModelRenderer(textureSize[0], textureSize[1], 0, 27);
        this.WeaponSpikeMid.setRotationPoint(2.0F, 0.0F, -5.0F);
        this.WeaponSpikeMid.addBox(0.0F, 0.0F, 0.0F, 1, 1, 5, 0.0F);
        this.UpperArmRight = new ModelRenderer(textureSize[0], textureSize[1], 31, 0);
        this.UpperArmRight.setRotationPoint(-2.6F, 1.2F, 3.3F);
        this.UpperArmRight.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(UpperArmRight, -1.2747884856566583F, -0.045553093477052F, -0.18203784098300857F);
        this.Head = new ModelRenderer(textureSize[0], textureSize[1], 31, 14);
        this.Head.setRotationPoint(-0.5F, 4.0F, -0.3F);
        this.Head.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(Head, -0.1651081472386636F, 0.3150319299849765F, -0.09005898940290741F);
        this.Base = new ModelRenderer(textureSize[0], textureSize[1], 0, 45);
        this.Base.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.Base.addBox(-8.0F, -3.1F, -8.0F, 16, 3, 16, 0.0F);
        this.LowerBody = new ModelRenderer(textureSize[0], textureSize[1], 18, 7);
        this.LowerBody.setRotationPoint(-2.0F, -13.5F, 2.2F);
        this.LowerBody.addBox(0.0F, 0.0F, 0.0F, 4, 3, 2, 0.0F);
        this.setRotateAngle(LowerBody, 0.0F, 0.33265975543011916F, 0.0F);
        this.LowerLegRight.addChild(this.UpperLegRight);
        this.Base.addChild(this.LowerLegRight);
        this.WeaponTop.addChild(this.WeaponSpikeLeft);
        this.WeaponGrip.addChild(this.WeaponTop);
        this.UpperLegLeft.addChild(this.LoweLegLeft);
        this.LowerBody.addChild(this.UpperBody);
        this.LowerArmRight.addChild(this.WeaponGrip);
        this.UpperArmLeft.addChild(this.LowerArmLeft);
        this.WeaponTop.addChild(this.WeaponSpikeRight);
        this.UpperArmRight.addChild(this.LowerArmRight);
        this.LowerBody.addChild(this.UpperLegLeft);
        this.UpperBody.addChild(this.UpperArmLeft);
        this.WeaponTop.addChild(this.WeaponSpikeMid);
        this.UpperBody.addChild(this.UpperArmRight);
        this.UpperBody.addChild(this.Head);
        this.Base.addChild(this.LowerBody);
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
