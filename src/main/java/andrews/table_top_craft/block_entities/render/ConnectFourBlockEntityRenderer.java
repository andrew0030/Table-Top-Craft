package andrews.table_top_craft.block_entities.render;

import andrews.table_top_craft.objects.blocks.ConnectFourBlock;
import andrews.table_top_craft.block_entities.ConnectFourBlockEntity;
import andrews.table_top_craft.block_entities.model.connect_four.ConnectFourFallingPieceModel;
import andrews.table_top_craft.block_entities.model.connect_four.ConnectFourMeshModel;
import andrews.table_top_craft.block_entities.model.connect_four.ConnectFourPieceModel;
import andrews.table_top_craft.util.Color;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.List;

public class ConnectFourBlockEntityRenderer implements BlockEntityRenderer<ConnectFourBlockEntity>
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/tile/connect_four/connect_four.png");
    private final ConnectFourMeshModel meshModel;
    private final ConnectFourPieceModel pieceModel;
    private final ConnectFourFallingPieceModel fallingPieceModel;
    private final float PIXEL_SIZE = 0.0625F;

    public ConnectFourBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        meshModel = new ConnectFourMeshModel(context.bakeLayer(ConnectFourMeshModel.LAYER));
        pieceModel = new ConnectFourPieceModel(context.bakeLayer(ConnectFourPieceModel.LAYER));
        fallingPieceModel = new ConnectFourFallingPieceModel(context.bakeLayer(ConnectFourFallingPieceModel.LAYER));
    }

    @Override
    public void render(ConnectFourBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay)
    {
        fallingPieceModel.updateAnimations(blockEntity, partialTick);
        Direction facing = Direction.NORTH;
        if(blockEntity.getBlockState().hasProperty(ConnectFourBlock.FACING))
            facing = blockEntity.getBlockState().getValue(ConnectFourBlock.FACING);
        poseStack.pushPose();
        poseStack.translate(0.5D, 1.5D, 0.5D);
        poseStack.scale(1.0F, -1.0F, -1.0F);
        poseStack.translate(0.0F, -PIXEL_SIZE * 2F, 0.0F);

        switch(facing) {
            default -> {}
            case WEST, EAST -> poseStack.mulPose(Axis.YN.rotationDegrees(270.0F));
        }

        // Renders the Mesh
        meshModel.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutout(TEXTURE));
        poseStack.translate(-6 * PIXEL_SIZE, 0.0F, 0.0F);

        String[] columns = blockEntity.getColumns();
        List<Integer> winIdxList = blockEntity.getFourInRow();

        // Color stuff used to render the pieces and change their appearance as needed
        Color colorIron = new Color(blockEntity.ironColor);
        float brightnessIron = (0.2126F * colorIron.getRed()) + (0.7152F * colorIron.getGreen()) + (0.0722F * colorIron.getBlue());
        Color colorGold = new Color(blockEntity.goldColor);
        float brightnessGold = (0.2126F * colorGold.getRed()) + (0.7152F * colorGold.getGreen()) + (0.0722F * colorGold.getBlue());
        Color altColorIron = brightnessIron > 128 ? colorIron.darker(0.75F, 0.0F) : colorIron.brighter(0.75F, 0.0F);
        Color altColorGold = brightnessGold > 128 ? colorGold.darker(0.75F, 0.0F) : colorGold.brighter(0.75F, 0.0F);

        int idx = -1;
        for(int x = 0; x < 7; x++)
        {
            for (int y = 0; y < 6; y++)
            {
                idx++;
                poseStack.pushPose();
                poseStack.translate(0.125F * x, -0.125 * y, 0.0F);
                if (columns[x].charAt(y) == 'i' || columns[x].charAt(y) == 'g')
                {
                    boolean isWhite = columns[x].charAt(y) == 'i';
                    float red = isWhite ? colorIron.getRed() : colorGold.getRed();
                    float green = isWhite ? colorIron.getGreen() : colorGold.getGreen();
                    float blue = isWhite ? colorIron.getBlue() : colorGold.getBlue();

                    if(!winIdxList.isEmpty() && winIdxList.contains(idx))
                    {
                        int tickSwapSpeed = 10;
                        float deltaTime = (blockEntity.getTicksExisted() / tickSwapSpeed) % 2 == 0 ? (blockEntity.getTicksExisted() % tickSwapSpeed + partialTick) / tickSwapSpeed : 1.0F - (blockEntity.getTicksExisted() % tickSwapSpeed + partialTick) / tickSwapSpeed;
                        red = isWhite ? Mth.lerp(deltaTime, altColorIron.getRed(), colorIron.getRed()) : Mth.lerp(deltaTime, altColorGold.getRed(), colorGold.getRed());
                        green = isWhite ? Mth.lerp(deltaTime, altColorIron.getGreen(), colorIron.getGreen()) : Mth.lerp(deltaTime, altColorGold.getGreen(), colorGold.getGreen());
                        blue = isWhite ? Mth.lerp(deltaTime, altColorIron.getBlue(), colorIron.getBlue()) : Mth.lerp(deltaTime, altColorGold.getBlue(), colorGold.getBlue());
                    }

                    if(blockEntity.movingPiece == idx)
                    {
                        poseStack.pushPose();
                        poseStack.scale(1.0F, 1.0F, 0.95F);
                        fallingPieceModel.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, red / 255F, green / 255F, blue / 255F, 1.0F);
                        poseStack.popPose();
                    } else {
                        pieceModel.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, red / 255F, green / 255F, blue / 255F, 1.0F);
                    }
                }
                poseStack.popPose();
            }
        }
        poseStack.popPose();
    }
}