package moe.plushie.table_top_craft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

/**
 * ChessQueen - andrew0030
 * Created using Tabula 7.0.0
 */
public class ChessQueen extends ModelBase {
    public ModelRenderer Base;
    public ModelRenderer HumanUpperBody;
    public ModelRenderer HumanLowerBody;
    public ModelRenderer Head;
    public ModelRenderer HumanUpperArmRight;
    public ModelRenderer HumanUpperArmLeft;
    public ModelRenderer BoobRight;
    public ModelRenderer HumanShoulderRight;
    public ModelRenderer HumanShoulderLeft;
    public ModelRenderer BoobLeft;
    public ModelRenderer WeaponGrip;
    public ModelRenderer HumanUpperLegLeft;
    public ModelRenderer HumanUpperLegRight;
    public ModelRenderer HumanLowerBack;
    public ModelRenderer HumanLowerLegLeft;
    public ModelRenderer HumanLowerLegRight;
    public ModelRenderer Hair;
    public ModelRenderer Hair_1;
    public ModelRenderer Hair_2;
    public ModelRenderer Hair_3;
    public ModelRenderer Hair_4;
    public ModelRenderer Hair_5;
    public ModelRenderer Hair_6;
    public ModelRenderer Hair_7;
    public ModelRenderer Hair_8;
    public ModelRenderer Hair_9;
    public ModelRenderer Hair_10;
    public ModelRenderer Hair_11;
    public ModelRenderer Hair_12;
    public ModelRenderer Hair_13;
    public ModelRenderer Hair_14;
    public ModelRenderer Hair_15;
    public ModelRenderer Hair_16;
    public ModelRenderer Hair_17;
    public ModelRenderer Hair_18;
    public ModelRenderer Hair_19;
    public ModelRenderer Hair_20;
    public ModelRenderer Hair_21;
    public ModelRenderer Hair_22;
    public ModelRenderer Hair_23;
    public ModelRenderer Hair_24;
    public ModelRenderer Hair_25;
    public ModelRenderer Hair_26;
    public ModelRenderer Hair_27;
    public ModelRenderer Hair_28;
    public ModelRenderer Hair_29;
    public ModelRenderer Hair_30;
    public ModelRenderer Hair_31;
    public ModelRenderer HumanLowerArmRight;
    public ModelRenderer WeaponStick;
    public ModelRenderer WeaponTop;
    public ModelRenderer WeaponBladeRight;
    public ModelRenderer WeaponBladeLeft;
    public ModelRenderer HumanLowerArmLeft;
    public ModelRenderer Shield;
    public ModelRenderer ShieldRight;
    public ModelRenderer ShieldLeft;
    public ModelRenderer ShieldTop;
    public ModelRenderer ShieldBack;
    public ModelRenderer ShieldXPart1;
    public ModelRenderer ShieldXpart2;
    public ModelRenderer ShieldGripFront;
    public ModelRenderer ShieldGripBack;
    public ModelRenderer BoobRightMid;
    public ModelRenderer HumanChestRight;
    public ModelRenderer HumanChestLeft;
    public ModelRenderer BoobLeftMid;
    public ModelRenderer WeaponMidPart;
    public ModelRenderer WeaponBlade;
    public ModelRenderer WeaponBladeTop;
    public ModelRenderer WeaponHolder;
    public ModelRenderer WeaponBladeBack;
    public ModelRenderer WeaponBladeFront;

    public ChessQueen() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.WeaponTop = new ModelRenderer(this, 0, 0);
        this.WeaponTop.setRotationPoint(0.5F, 1.0F, 17.0F);
        this.WeaponTop.addBox(0.0F, -1.0F, 0.0F, 1, 1, 1, 0.0F);
        this.setRotateAngle(WeaponTop, 0.0F, -0.7853981633974483F, 0.0F);
        this.Hair_28 = new ModelRenderer(this, 0, 0);
        this.Hair_28.setRotationPoint(0.0F, 1.8F, 1.0F);
        this.Hair_28.addBox(0.2F, 0.0F, -1.0F, 1, 2, 1, 0.0F);
        this.setRotateAngle(Hair_28, -0.36425021489121656F, 0.091106186954104F, 0.22759093446006054F);
        this.ShieldGripBack = new ModelRenderer(this, 0, 0);
        this.ShieldGripBack.setRotationPoint(1.0F, 1.0F, -1.0F);
        this.ShieldGripBack.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, 0.0F);
        this.Hair_17 = new ModelRenderer(this, 0, 0);
        this.Hair_17.setRotationPoint(-2.0F, -4.4F, -0.5F);
        this.Hair_17.addBox(0.0F, 0.0F, -1.0F, 1, 2, 1, 0.0F);
        this.setRotateAngle(Hair_17, 0.8203047484373349F, 0.0F, 0.24434609527920614F);
        this.BoobLeft = new ModelRenderer(this, 0, 0);
        this.BoobLeft.setRotationPoint(1.55F, 0.0F, -1.7F);
        this.BoobLeft.addBox(-1.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.setRotateAngle(BoobLeft, -0.4363323129985824F, -0.08726646259971647F, 0.0F);
        this.WeaponBlade = new ModelRenderer(this, 0, 102);
        this.WeaponBlade.setRotationPoint(0.5F, 0.5F, -7.0F);
        this.WeaponBlade.addBox(0.0F, 0.0F, 0.0F, 1, 2, 7, 0.0F);
        this.HumanShoulderRight = new ModelRenderer(this, 0, 0);
        this.HumanShoulderRight.setRotationPoint(-2.0F, 0.0F, -2.0F);
        this.HumanShoulderRight.addBox(0.0F, 0.0F, 0.0F, 1, 2, 2, 0.0F);
        this.HumanLowerBack = new ModelRenderer(this, 0, 0);
        this.HumanLowerBack.setRotationPoint(-1.5F, 0.0F, -2.0F);
        this.HumanLowerBack.addBox(0.0F, 0.0F, 0.0F, 3, 1, 2, 0.0F);
        this.Hair_29 = new ModelRenderer(this, 0, 0);
        this.Hair_29.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.Hair_29.addBox(0.2F, 0.0F, -1.0F, 1, 2, 1, 0.0F);
        this.setRotateAngle(Hair_29, -0.5009094953223726F, 0.0F, 0.0F);
        this.ShieldLeft = new ModelRenderer(this, 0, 0);
        this.ShieldLeft.setRotationPoint(-1.0F, -1.0F, 0.3F);
        this.ShieldLeft.addBox(0.0F, 0.0F, 0.0F, 7, 1, 1, 0.0F);
        this.HumanChestLeft = new ModelRenderer(this, 0, 0);
        this.HumanChestLeft.setRotationPoint(1.0F, 2.0F, 0.0F);
        this.HumanChestLeft.addBox(-1.0F, 0.0F, 0.0F, 1, 2, 2, 0.0F);
        this.setRotateAngle(HumanChestLeft, 0.0F, 0.0F, 0.2617993877991494F);
        this.WeaponMidPart = new ModelRenderer(this, 9, 97);
        this.WeaponMidPart.setRotationPoint(-0.5F, -1.0F, -1.0F);
        this.WeaponMidPart.addBox(0.0F, 0.0F, 0.0F, 2, 3, 1, 0.0F);
        this.HumanUpperArmRight = new ModelRenderer(this, 0, 0);
        this.HumanUpperArmRight.setRotationPoint(-2.0F, 1.5F, -1.0F);
        this.HumanUpperArmRight.addBox(-2.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(HumanUpperArmRight, -2.5953045977155678F, 0.22759093446006054F, -0.22759093446006054F);
        this.WeaponStick = new ModelRenderer(this, 0, 0);
        this.WeaponStick.setRotationPoint(-0.5F, 2.5F, -11.0F);
        this.WeaponStick.addBox(0.0F, 0.0F, 0.0F, 1, 1, 17, 0.0F);
        this.ShieldXPart1 = new ModelRenderer(this, 0, 0);
        this.ShieldXPart1.setRotationPoint(-0.6F, 0.0F, 0.2F);
        this.ShieldXPart1.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1, 0.0F);
        this.setRotateAngle(ShieldXPart1, 0.0F, 0.0F, -0.7853981633974483F);
        this.Hair_26 = new ModelRenderer(this, 0, 0);
        this.Hair_26.setRotationPoint(-1.0F, 4.0F, 0.0F);
        this.Hair_26.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
        this.WeaponHolder = new ModelRenderer(this, 0, 0);
        this.WeaponHolder.setRotationPoint(-0.8F, -0.5F, 4.8F);
        this.WeaponHolder.addBox(0.0F, 0.0F, 0.0F, 2, 3, 1, 0.0F);
        this.Hair_7 = new ModelRenderer(this, 0, 0);
        this.Hair_7.setRotationPoint(-0.8F, -4.2F, -2.1F);
        this.Hair_7.addBox(-1.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.setRotateAngle(Hair_7, -0.24434609527920614F, 0.0F, 0.22689280275926282F);
        this.Hair_4 = new ModelRenderer(this, 0, 0);
        this.Hair_4.setRotationPoint(0.8F, -4.2F, -2.0F);
        this.Hair_4.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.setRotateAngle(Hair_4, -0.24434609527920614F, 0.0F, -0.22689280275926282F);
        this.BoobRight = new ModelRenderer(this, 0, 0);
        this.BoobRight.setRotationPoint(-1.55F, 0.0F, -1.7F);
        this.BoobRight.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.setRotateAngle(BoobRight, -0.4363323129985824F, 0.08726646259971647F, 0.0F);
        this.Hair_21 = new ModelRenderer(this, 0, 0);
        this.Hair_21.setRotationPoint(0.0F, 0.0F, 2.0F);
        this.Hair_21.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(Hair_21, -0.17453292519943295F, 0.0F, 0.0F);
        this.Hair_6 = new ModelRenderer(this, 0, 0);
        this.Hair_6.setRotationPoint(0.0F, -4.0F, -2.0F);
        this.Hair_6.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.setRotateAngle(Hair_6, -0.24434609527920614F, 0.0F, -0.6370451769779303F);
        this.WeaponGrip = new ModelRenderer(this, 0, 97);
        this.WeaponGrip.setRotationPoint(2.3F, 0.7F, 0.0F);
        this.WeaponGrip.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(WeaponGrip, 0.0F, -1.5707963267948966F, 2.2689280275926285F);
        this.BoobLeftMid = new ModelRenderer(this, 0, 0);
        this.BoobLeftMid.setRotationPoint(-0.5F, 0.0F, 0.0F);
        this.BoobLeftMid.addBox(-1.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.Hair_8 = new ModelRenderer(this, 0, 0);
        this.Hair_8.setRotationPoint(-0.9F, -4.4F, 1.5F);
        this.Hair_8.addBox(-1.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
        this.setRotateAngle(Hair_8, 0.08726646259971647F, -0.17453292519943295F, -0.045553093477052F);
        this.WeaponBladeFront = new ModelRenderer(this, 13, 112);
        this.WeaponBladeFront.setRotationPoint(0.0F, 2.0F, 2.0F);
        this.WeaponBladeFront.addBox(0.0F, -1.0F, 0.0F, 1, 1, 5, 0.0F);
        this.setRotateAngle(WeaponBladeFront, 0.9075712110370513F, 0.0F, 0.0F);
        this.ShieldBack = new ModelRenderer(this, 0, 0);
        this.ShieldBack.setRotationPoint(-1.0F, 0.0F, 0.3F);
        this.ShieldBack.addBox(0.0F, 0.0F, 0.0F, 1, 5, 1, 0.0F);
        this.setRotateAngle(ShieldBack, 0.0F, 0.012566370614359173F, 0.0F);
        this.ShieldTop = new ModelRenderer(this, 0, 0);
        this.ShieldTop.setRotationPoint(5.0F, 0.0F, 0.3F);
        this.ShieldTop.addBox(0.0F, 0.0F, 0.0F, 1, 5, 1, 0.0F);
        this.Hair_25 = new ModelRenderer(this, 0, 0);
        this.Hair_25.setRotationPoint(-1.0F, 3.5F, 0.0F);
        this.Hair_25.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
        this.setRotateAngle(Hair_25, 0.13962634015954636F, 0.0F, -0.136659280431156F);
        this.Hair_10 = new ModelRenderer(this, 0, 0);
        this.Hair_10.setRotationPoint(-0.05F, -4.5F, 1.6F);
        this.Hair_10.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
        this.setRotateAngle(Hair_10, 0.13962634015954636F, 0.08726646259971647F, 0.0F);
        this.Hair_30 = new ModelRenderer(this, 0, 0);
        this.Hair_30.setRotationPoint(0.0F, 3.0F, 0.0F);
        this.Hair_30.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.setRotateAngle(Hair_30, 0.22759093446006054F, 0.0F, -0.36425021489121656F);
        this.Hair_3 = new ModelRenderer(this, 0, 0);
        this.Hair_3.setRotationPoint(2.0F, -4.4F, -2.0F);
        this.Hair_3.addBox(-1.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(Hair_3, 0.08726646259971647F, 0.0F, 0.17453292519943295F);
        this.HumanUpperLegRight = new ModelRenderer(this, 0, 0);
        this.HumanUpperLegRight.setRotationPoint(0.0F, 3.5F, -1.0F);
        this.HumanUpperLegRight.addBox(-2.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(HumanUpperLegRight, 0.5291838292046808F, -0.045553093477052F, 0.19205794974932466F);
        this.Hair_9 = new ModelRenderer(this, 0, 0);
        this.Hair_9.setRotationPoint(0.05F, -4.5F, 1.6F);
        this.Hair_9.addBox(-1.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
        this.setRotateAngle(Hair_9, 0.17453292519943295F, -0.08726646259971647F, 0.0F);
        this.ShieldRight = new ModelRenderer(this, 0, 0);
        this.ShieldRight.setRotationPoint(-1.0F, 5.0F, 0.3F);
        this.ShieldRight.addBox(0.0F, 0.0F, 0.0F, 7, 1, 1, 0.0F);
        this.HumanUpperArmLeft = new ModelRenderer(this, 0, 0);
        this.HumanUpperArmLeft.setRotationPoint(2.5F, 2.5F, -1.0F);
        this.HumanUpperArmLeft.addBox(0.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(HumanUpperArmLeft, -1.2747884856566583F, -0.27314402793711257F, -1.4570008595648662F);
        this.Hair_2 = new ModelRenderer(this, 0, 0);
        this.Hair_2.setRotationPoint(1.0F, -4.6F, -2.0F);
        this.Hair_2.addBox(-1.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(Hair_2, 0.08726646259971647F, 0.0F, 0.08726646259971647F);
        this.Hair_23 = new ModelRenderer(this, 0, 0);
        this.Hair_23.setRotationPoint(-1.0F, 0.0F, 2.0F);
        this.Hair_23.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(Hair_23, -0.17453292519943295F, 0.0F, 0.0F);
        this.HumanLowerArmLeft = new ModelRenderer(this, 0, 0);
        this.HumanLowerArmLeft.setRotationPoint(1.0F, 4.0F, 1.0F);
        this.HumanLowerArmLeft.addBox(-1.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(HumanLowerArmLeft, -1.0016444577195458F, 0.0F, 0.0F);
        this.ShieldXpart2 = new ModelRenderer(this, 0, 0);
        this.ShieldXpart2.setRotationPoint(0.0F, 4.4F, 0.15F);
        this.ShieldXpart2.addBox(0.0F, -7.0F, 0.0F, 1, 8, 1, 0.0F);
        this.setRotateAngle(ShieldXpart2, 0.0F, 0.0F, 0.7853981633974483F);
        this.Hair_14 = new ModelRenderer(this, 0, 0);
        this.Hair_14.setRotationPoint(1.0F, -4.4F, -0.25F);
        this.Hair_14.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(Hair_14, -0.8196066167365371F, 0.0F, -0.22689280275926282F);
        this.HumanUpperLegLeft = new ModelRenderer(this, 0, 0);
        this.HumanUpperLegLeft.setRotationPoint(0.0F, 3.5F, -1.0F);
        this.HumanUpperLegLeft.addBox(0.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(HumanUpperLegLeft, -1.075995483854504F, -0.22759093446006054F, -0.045553093477052F);
        this.Hair_20 = new ModelRenderer(this, 0, 0);
        this.Hair_20.setRotationPoint(0.0F, 0.0F, 2.0F);
        this.Hair_20.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(Hair_20, -0.17453292519943295F, 0.0F, 0.0F);
        this.Hair_19 = new ModelRenderer(this, 0, 0);
        this.Hair_19.setRotationPoint(-2.1F, -4.2F, 1.0F);
        this.Hair_19.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(Hair_19, 0.136659280431156F, 0.0F, 0.136659280431156F);
        this.Hair_16 = new ModelRenderer(this, 0, 0);
        this.Hair_16.setRotationPoint(-2.0F, -4.5F, -2.0F);
        this.Hair_16.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.setRotateAngle(Hair_16, 0.0F, 0.0F, 0.19198621771937624F);
        this.Hair_15 = new ModelRenderer(this, 0, 0);
        this.Hair_15.setRotationPoint(1.0F, -4.2F, -1.5F);
        this.Hair_15.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.setRotateAngle(Hair_15, -0.22759093446006054F, 0.0F, -0.2617993877991494F);
        this.Shield = new ModelRenderer(this, 0, 0);
        this.Shield.setRotationPoint(-2.5F, -0.5F, 0.0F);
        this.Shield.addBox(0.0F, 0.0F, 0.0F, 5, 5, 1, 0.0F);
        this.WeaponBladeTop = new ModelRenderer(this, 0, 102);
        this.WeaponBladeTop.setRotationPoint(0.0F, -0.4F, 0.0F);
        this.WeaponBladeTop.addBox(0.0F, 0.0F, 0.0F, 1, 2, 2, 0.0F);
        this.setRotateAngle(WeaponBladeTop, -0.7853981633974483F, 0.0F, 0.0F);
        this.WeaponBladeRight = new ModelRenderer(this, 0, 0);
        this.WeaponBladeRight.setRotationPoint(1.0F, 0.0F, 0.0F);
        this.WeaponBladeRight.addBox(0.0F, -1.0F, 0.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(WeaponBladeRight, 0.0F, -2.7227136331111543F, 0.0F);
        this.BoobRightMid = new ModelRenderer(this, 0, 0);
        this.BoobRightMid.setRotationPoint(0.5F, 0.0F, 0.0F);
        this.BoobRightMid.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.HumanLowerLegLeft = new ModelRenderer(this, 0, 0);
        this.HumanLowerLegLeft.setRotationPoint(1.0F, 4.0F, -1.0F);
        this.HumanLowerLegLeft.addBox(-1.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(HumanLowerLegLeft, 0.7056264192920628F, 0.0F, 0.0F);
        this.HumanLowerBody = new ModelRenderer(this, 0, 0);
        this.HumanLowerBody.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.HumanLowerBody.addBox(-2.0F, 1.0F, -2.0F, 4, 2, 2, 0.0F);
        this.setRotateAngle(HumanLowerBody, -0.2617993877991494F, 0.0F, 0.0F);
        this.Base = new ModelRenderer(this, 0, 0);
        this.Base.setRotationPoint(0.2F, 0.0F, 0.2F);
        this.Base.addBox(0.0F, 0.0F, 0.0F, 16, 3, 16, 0.0F);
        this.HumanUpperBody = new ModelRenderer(this, 0, 0);
        this.HumanUpperBody.setRotationPoint(8.7F, -12.9F, 8.0F);
        this.HumanUpperBody.addBox(-1.5F, 0.0F, -2.0F, 3, 4, 2, 0.0F);
        this.setRotateAngle(HumanUpperBody, 0.45378560551852565F, 0.0F, 0.0F);
        this.Hair_27 = new ModelRenderer(this, 0, 0);
        this.Hair_27.setRotationPoint(-0.95F, 3.4F, 0.0F);
        this.Hair_27.addBox(1.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(Hair_27, 0.017453292519943295F, 0.0F, 0.22759093446006054F);
        this.HumanLowerLegRight = new ModelRenderer(this, 0, 0);
        this.HumanLowerLegRight.setRotationPoint(-1.0F, 4.0F, -1.0F);
        this.HumanLowerLegRight.addBox(-1.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(HumanLowerLegRight, 0.4939384935044441F, 0.0F, 0.0F);
        this.Hair_5 = new ModelRenderer(this, 0, 0);
        this.Hair_5.setRotationPoint(-0.7F, -4.6F, -2.0F);
        this.Hair_5.addBox(-1.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.setRotateAngle(Hair_5, -0.17453292519943295F, -0.18203784098300857F, -1.1344640137963142F);
        this.Hair_1 = new ModelRenderer(this, 0, 0);
        this.Hair_1.setRotationPoint(-1.05F, -4.6F, -2.0F);
        this.Hair_1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(Hair_1, 0.08726646259971647F, 0.0F, -0.08726646259971647F);
        this.WeaponBladeLeft = new ModelRenderer(this, 0, 0);
        this.WeaponBladeLeft.setRotationPoint(0.0F, -1.0F, 1.0F);
        this.WeaponBladeLeft.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
        this.setRotateAngle(WeaponBladeLeft, 0.0F, 2.7227136331111543F, 0.0F);
        this.Hair_11 = new ModelRenderer(this, 0, 0);
        this.Hair_11.setRotationPoint(0.9F, -4.4F, 1.5F);
        this.Hair_11.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
        this.setRotateAngle(Hair_11, 0.03490658503988659F, 0.17453292519943295F, 0.0F);
        this.WeaponBladeBack = new ModelRenderer(this, 0, 112);
        this.WeaponBladeBack.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.WeaponBladeBack.addBox(0.0F, 0.0F, 0.0F, 1, 1, 5, 0.0F);
        this.setRotateAngle(WeaponBladeBack, 0.6806784082777886F, 0.0F, 0.0F);
        this.HumanShoulderLeft = new ModelRenderer(this, 0, 0);
        this.HumanShoulderLeft.setRotationPoint(1.0F, 0.0F, -2.0F);
        this.HumanShoulderLeft.addBox(0.0F, 0.0F, 0.0F, 1, 2, 2, 0.0F);
        this.Hair = new ModelRenderer(this, 0, 0);
        this.Hair.setRotationPoint(-2.05F, -4.4F, -2.0F);
        this.Hair.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(Hair, 0.08726646259971647F, 0.0F, -0.17453292519943295F);
        this.Hair_22 = new ModelRenderer(this, 0, 0);
        this.Hair_22.setRotationPoint(0.0F, 0.0F, 2.0F);
        this.Hair_22.addBox(-1.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(Hair_22, -0.17453292519943295F, 0.0F, 0.0F);
        this.HumanLowerArmRight = new ModelRenderer(this, 0, 0);
        this.HumanLowerArmRight.setRotationPoint(-1.0F, 4.0F, 1.0F);
        this.HumanLowerArmRight.addBox(-1.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(HumanLowerArmRight, -0.9105382707654417F, 0.0F, 0.0F);
        this.Hair_12 = new ModelRenderer(this, 0, 0);
        this.Hair_12.setRotationPoint(1.0F, -4.2F, 0.0F);
        this.Hair_12.addBox(0.2F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.setRotateAngle(Hair_12, 0.22759093446006054F, 0.0F, -0.24434609527920614F);
        this.Hair_13 = new ModelRenderer(this, 0, 0);
        this.Hair_13.setRotationPoint(1.2F, -4.2F, 1.0F);
        this.Hair_13.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(Hair_13, 0.136659280431156F, 0.0F, -0.24434609527920614F);
        this.ShieldGripFront = new ModelRenderer(this, 0, 0);
        this.ShieldGripFront.setRotationPoint(1.0F, 3.0F, -1.0F);
        this.ShieldGripFront.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 0.0F, -1.0F);
        this.Head.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(Head, -0.31869712141416456F, 0.22759093446006054F, -0.045553093477052F);
        this.Hair_18 = new ModelRenderer(this, 0, 0);
        this.Hair_18.setRotationPoint(-2.1F, -4.2F, 0.0F);
        this.Hair_18.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(Hair_18, 0.22689280275926282F, 0.0F, 0.17453292519943295F);
        this.Hair_24 = new ModelRenderer(this, 0, 0);
        this.Hair_24.setRotationPoint(1.0F, 1.8F, 1.0F);
        this.Hair_24.addBox(-1.0F, 0.0F, -1.0F, 1, 2, 1, 0.0F);
        this.setRotateAngle(Hair_24, 0.18203784098300857F, 0.0F, 0.27314402793711257F);
        this.Hair_31 = new ModelRenderer(this, 0, 0);
        this.Hair_31.setRotationPoint(0.0F, 3.0F, 0.0F);
        this.Hair_31.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
        this.setRotateAngle(Hair_31, 0.091106186954104F, 0.0F, -0.31869712141416456F);
        this.HumanChestRight = new ModelRenderer(this, 0, 0);
        this.HumanChestRight.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.HumanChestRight.addBox(0.0F, 0.0F, 0.0F, 1, 2, 2, 0.0F);
        this.setRotateAngle(HumanChestRight, 0.0F, 0.0F, -0.2617993877991494F);
        this.WeaponStick.addChild(this.WeaponTop);
        this.Hair_12.addChild(this.Hair_28);
        this.Shield.addChild(this.ShieldGripBack);
        this.Head.addChild(this.Hair_17);
        this.HumanUpperBody.addChild(this.BoobLeft);
        this.WeaponMidPart.addChild(this.WeaponBlade);
        this.HumanUpperBody.addChild(this.HumanShoulderRight);
        this.HumanLowerBody.addChild(this.HumanLowerBack);
        this.Hair_28.addChild(this.Hair_29);
        this.Shield.addChild(this.ShieldLeft);
        this.HumanShoulderLeft.addChild(this.HumanChestLeft);
        this.WeaponGrip.addChild(this.WeaponMidPart);
        this.HumanUpperBody.addChild(this.HumanUpperArmRight);
        this.HumanLowerArmRight.addChild(this.WeaponStick);
        this.Shield.addChild(this.ShieldXPart1);
        this.Hair_9.addChild(this.Hair_26);
        this.WeaponBlade.addChild(this.WeaponHolder);
        this.Head.addChild(this.Hair_7);
        this.Head.addChild(this.Hair_4);
        this.HumanUpperBody.addChild(this.BoobRight);
        this.Hair_1.addChild(this.Hair_21);
        this.Head.addChild(this.Hair_6);
        this.HumanUpperBody.addChild(this.WeaponGrip);
        this.BoobLeft.addChild(this.BoobLeftMid);
        this.Head.addChild(this.Hair_8);
        this.WeaponBladeTop.addChild(this.WeaponBladeFront);
        this.Shield.addChild(this.ShieldBack);
        this.Shield.addChild(this.ShieldTop);
        this.Hair_8.addChild(this.Hair_25);
        this.Head.addChild(this.Hair_10);
        this.Hair_18.addChild(this.Hair_30);
        this.Head.addChild(this.Hair_3);
        this.HumanLowerBody.addChild(this.HumanUpperLegRight);
        this.Head.addChild(this.Hair_9);
        this.Shield.addChild(this.ShieldRight);
        this.HumanUpperBody.addChild(this.HumanUpperArmLeft);
        this.Head.addChild(this.Hair_2);
        this.Hair_3.addChild(this.Hair_23);
        this.HumanUpperArmLeft.addChild(this.HumanLowerArmLeft);
        this.Shield.addChild(this.ShieldXpart2);
        this.Head.addChild(this.Hair_14);
        this.HumanLowerBody.addChild(this.HumanUpperLegLeft);
        this.Hair.addChild(this.Hair_20);
        this.Head.addChild(this.Hair_19);
        this.Head.addChild(this.Hair_16);
        this.Head.addChild(this.Hair_15);
        this.HumanLowerArmLeft.addChild(this.Shield);
        this.WeaponBlade.addChild(this.WeaponBladeTop);
        this.WeaponTop.addChild(this.WeaponBladeRight);
        this.BoobRight.addChild(this.BoobRightMid);
        this.HumanUpperLegLeft.addChild(this.HumanLowerLegLeft);
        this.HumanUpperBody.addChild(this.HumanLowerBody);
        this.Base.addChild(this.HumanUpperBody);
        this.Hair_10.addChild(this.Hair_27);
        this.HumanUpperLegRight.addChild(this.HumanLowerLegRight);
        this.Head.addChild(this.Hair_5);
        this.Head.addChild(this.Hair_1);
        this.WeaponTop.addChild(this.WeaponBladeLeft);
        this.Head.addChild(this.Hair_11);
        this.WeaponBladeTop.addChild(this.WeaponBladeBack);
        this.HumanUpperBody.addChild(this.HumanShoulderLeft);
        this.Head.addChild(this.Hair);
        this.Hair_2.addChild(this.Hair_22);
        this.HumanUpperArmRight.addChild(this.HumanLowerArmRight);
        this.Head.addChild(this.Hair_12);
        this.Head.addChild(this.Hair_13);
        this.Shield.addChild(this.ShieldGripFront);
        this.HumanUpperBody.addChild(this.Head);
        this.Head.addChild(this.Hair_18);
        this.Hair_4.addChild(this.Hair_24);
        this.Hair_19.addChild(this.Hair_31);
        this.HumanShoulderRight.addChild(this.HumanChestRight);
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
