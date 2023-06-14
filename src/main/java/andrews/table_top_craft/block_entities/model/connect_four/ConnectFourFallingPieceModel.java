package andrews.table_top_craft.block_entities.model.connect_four;

import andrews.table_top_craft.animation.model.AdvancedMeshDefinition;
import andrews.table_top_craft.animation.model.AdvancedModelPart;
import andrews.table_top_craft.animation.model.AdvancedPartDefinition;
import andrews.table_top_craft.animation.model.AnimatedBlockEntityModel;
import andrews.table_top_craft.animation.system.base.AnimatedBlockEntity;
import andrews.table_top_craft.block_entities.ConnectFourBlockEntity;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class ConnectFourFallingPieceModel extends AnimatedBlockEntityModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation(Reference.MODID, "connect_four_falling_piece_layer"), "main");
    public final AdvancedModelPart root;

    public ConnectFourFallingPieceModel(ModelPart root)
    {
        super(RenderType::entityCutout);
        this.root = (AdvancedModelPart) root.getChild("root");
    }

    public static LayerDefinition createBodyLayer()
    {
        AdvancedMeshDefinition meshdefinition = new AdvancedMeshDefinition();
        AdvancedPartDefinition partdefinition = meshdefinition.getAdvancedRoot();
        AdvancedPartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 14).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        return LayerDefinition.create(meshdefinition.overwriteRootChildren(partdefinition), 32, 16);
    }

    @Override
    public void updateAnimations(AnimatedBlockEntity blockEntity, float partialTick)
    {
        if(blockEntity instanceof ConnectFourBlockEntity animated)
        {
            this.root.getAllParts().forEach(ModelPart::resetPose);
            if(animated.moveState != null)
                this.animate(animated.moveState, animated.getTicksExisted() + partialTick, 1.0F);
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public AdvancedModelPart root()
    {
        return this.root;
    }
}
