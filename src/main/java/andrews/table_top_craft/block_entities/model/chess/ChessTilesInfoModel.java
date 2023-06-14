package andrews.table_top_craft.block_entities.model.chess;

import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

// Made with Blockbench 4.1.5 by andrew0030
public class ChessTilesInfoModel extends Model
{
    public static final ModelLayerLocation CHESS_TILES_INFO_LAYER = new ModelLayerLocation(new ResourceLocation(Reference.MODID, "chess_tiles_info_layer"), "main");
    private final ModelPart root;

    public ChessTilesInfoModel(ModelPart root)
    {
        super(RenderType::entityCutoutNoCull);
        this.root = root.getChild("root");
    }

    public static LayerDefinition createBodyLayer()
    {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition base = root.addOrReplaceChild("base", CubeListBuilder.create().texOffs(-20, 0).addBox(-10.0F, -1.0F, -10.0F, 20.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        root.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}