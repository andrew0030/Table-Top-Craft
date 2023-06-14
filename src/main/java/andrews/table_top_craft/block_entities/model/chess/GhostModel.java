package andrews.table_top_craft.block_entities.model.chess;

import andrews.table_top_craft.animation.model.AdvancedMeshDefinition;
import andrews.table_top_craft.animation.model.AdvancedModelPart;
import andrews.table_top_craft.animation.model.AdvancedPartDefinition;
import andrews.table_top_craft.animation.model.AnimatedBlockEntityModel;
import andrews.table_top_craft.animation.system.base.AnimatedBlockEntity;
import andrews.table_top_craft.block_entities.ChessBlockEntity;
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
    public final AdvancedModelPart selected;
    public final AdvancedModelPart moved;
    public final AdvancedModelPart affected;

    public GhostModel(ModelPart root)
    {
        super(RenderType::entityCutoutNoCull);
        this.root = (AdvancedModelPart) root.getChild("root");
        this.selected = this.root.getChild("selected");
        this.moved = this.root.getChild("moved");
        this.affected = this.root.getChild("affected");
    }

    public static LayerDefinition createBodyLayer()
    {
        AdvancedMeshDefinition meshDefinition = new AdvancedMeshDefinition();
        AdvancedPartDefinition partDefinition = meshDefinition.getAdvancedRoot();
        AdvancedPartDefinition root = partDefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        AdvancedPartDefinition selected = root.addOrReplaceChild("selected", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        AdvancedPartDefinition moved = root.addOrReplaceChild("moved", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        AdvancedPartDefinition affected = root.addOrReplaceChild("affected", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(meshDefinition.overwriteRootChildren(partDefinition), 0, 0);
    }

    @Override
    public void updateAnimations(AnimatedBlockEntity blockEntity, float partialTick)
    {
        if(blockEntity instanceof ChessBlockEntity animated)
        {
            this.root.getAllParts().forEach(ModelPart::resetPose);
            animated.lingeringStates.removeIf(state -> !state.isStarted());
            animated.lingeringStates.forEach(state -> this.animate(state, animated.getTicksExisted() + partialTick, 1.0F));
            this.animate(animated.selectedPieceState, animated.getTicksExisted() + partialTick, 1.0F);
            this.animate(animated.placedState, animated.getTicksExisted() + partialTick, 1.0F);
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
