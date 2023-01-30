package andrews.table_top_craft.tile_entities.model.chess;

import andrews.table_top_craft.animation.model.AdvancedMeshDefinition;
import andrews.table_top_craft.animation.model.AdvancedModelPart;
import andrews.table_top_craft.animation.model.AdvancedPartDefinition;
import andrews.table_top_craft.animation.model.AnimatedBlockEntityModel;
import andrews.table_top_craft.animation.system.base.AnimatedBlockEntity;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class GhostModel extends AnimatedBlockEntityModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation(Reference.MODID, "ghost_model"), "main");
    public final AdvancedModelPart root;

    public GhostModel(ModelPart root)
    {
        super(RenderType::entityCutoutNoCull);
        this.root = (AdvancedModelPart) root.getChild("root");
    }

    public static LayerDefinition createBodyLayer()
    {
        AdvancedMeshDefinition meshDefinition = new AdvancedMeshDefinition();
        AdvancedPartDefinition partDefinition = meshDefinition.getAdvancedRoot();
        AdvancedPartDefinition root = partDefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(meshDefinition.overwriteRootChildren(partDefinition), 0, 0);
    }

    @Override
    public void updateAnimations(AnimatedBlockEntity blockEntity, float partialTick)
    {
        if(blockEntity instanceof ChessTileEntity animated)
        {
            this.root.getAllParts().forEach(ModelPart::resetPose);
            animated.lingeringStates.removeIf(state -> !state.isStarted());
            animated.lingeringStates.forEach(state -> this.animate(state, animated.getTicksExisted() + partialTick, 1.0F));
            this.animate(animated.selectedPieceState, animated.getTicksExisted() + partialTick, 1.0F);
            this.animate(animated.placedState, animated.getTicksExisted() + partialTick, 1.0F);
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
