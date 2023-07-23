package andrews.table_top_craft.block_entities.render;

import andrews.table_top_craft.block_entities.TicTacToeBlockEntity;
import andrews.table_top_craft.block_entities.model.tic_tac_toe.TicTacToeModel;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

public class TicTacToeBlockEntityRenderer implements BlockEntityRenderer<TicTacToeBlockEntity>
{
    private static final ArrayList<ResourceLocation> CIRCLE_TEXTURES = new ArrayList<>();
    private static final ArrayList<ResourceLocation> CROSS_TEXTURES = new ArrayList<>();
    private final TicTacToeModel ticTacToeModel;

    public TicTacToeBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        for(int i = 0; i < 16; i++)
            CIRCLE_TEXTURES.add(new ResourceLocation(Reference.MODID, "textures/tile/tic_tac_toe/o/o_" + i + ".png"));
        for(int i = 0; i < 14; i++)
            CROSS_TEXTURES.add(new ResourceLocation(Reference.MODID, "textures/tile/tic_tac_toe/x/x_" + i + ".png"));
        ticTacToeModel = new TicTacToeModel(context.bakeLayer(TicTacToeModel.TIC_TAC_TOE_LAYER));
    }

    @Override
    public void render(TicTacToeBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay)
    {
        poseStack.pushPose();
        // Fixes the initial position
        poseStack.translate(0.5D, 1.5D, 0.5D);
        poseStack.scale(1.0F, -1.0F, -1.0F);
        poseStack.translate(0, -0.031746F, 0);
        // Fixes the Scale
        poseStack.scale(0.5F, 1.0F, 0.5F);

        String game = blockEntity.getTicTacToeGame();
        String[] rows = game.split("/");
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        if(blockEntity.isGameOver(game))
        {
            poseStack.pushPose();
            poseStack.translate(0, -0.001, 0);
            VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entityCutout(CROSS_TEXTURES.get(6)));

            if (rows[0].equals("XXX") || rows[0].equals("OOO")) {
                poseStack.translate(0.625, 0, 0);
                poseStack.scale(1.8F, 1, 3.95F);
            }
            else if(rows[1].equals("XXX") || rows[1].equals("OOO")) {
                poseStack.scale(1.8F, 1, 3.95F);
            }
            else if(rows[2].equals("XXX") || rows[2].equals("OOO")) {
                poseStack.translate(-0.625, 0, 0);
                poseStack.scale(1.8F, 1, 3.95F);
            }
            else if ((rows[0].charAt(0) == 'X' && rows[1].charAt(0) == 'X' && rows[2].charAt(0) == 'X') ||
                     (rows[0].charAt(0) == 'O' && rows[1].charAt(0) == 'O' && rows[2].charAt(0) == 'O')) {
                poseStack.translate(0, 0, 0.625);
                poseStack.mulPose(Axis.YN.rotationDegrees(90F));
                poseStack.scale(1.8F, 1, 3.95F);
            }
            else if ((rows[0].charAt(1) == 'X' && rows[1].charAt(1) == 'X' && rows[2].charAt(1) == 'X') ||
                    (rows[0].charAt(1) == 'O' && rows[1].charAt(1) == 'O' && rows[2].charAt(1) == 'O')) {
                poseStack.mulPose(Axis.YN.rotationDegrees(90F));
                poseStack.scale(1.8F, 1, 3.95F);
            }
            else if ((rows[0].charAt(2) == 'X' && rows[1].charAt(2) == 'X' && rows[2].charAt(2) == 'X') ||
                    (rows[0].charAt(2) == 'O' && rows[1].charAt(2) == 'O' && rows[2].charAt(2) == 'O')) {
                poseStack.translate(0, 0, -0.625);
                poseStack.mulPose(Axis.YN.rotationDegrees(90F));
                poseStack.scale(1.8F, 1, 3.95F);
            }
            else if ((rows[0].charAt(0) == 'X' && rows[1].charAt(1) == 'X' && rows[2].charAt(2) == 'X') ||
                    (rows[0].charAt(0) == 'O' && rows[1].charAt(1) == 'O' && rows[2].charAt(2) == 'O')) {
                poseStack.mulPose(Axis.YP.rotationDegrees(45F));
                poseStack.scale(1.8F, 1, 5.2F);
            }
            else if ((rows[0].charAt(2) == 'X' && rows[1].charAt(1) == 'X' && rows[2].charAt(0) == 'X') ||
                     (rows[0].charAt(2) == 'O' && rows[1].charAt(1) == 'O' && rows[2].charAt(0) == 'O')) {
                poseStack.mulPose(Axis.YN.rotationDegrees(45F));
                poseStack.scale(1.8F, 1, 5.2F);
            }
            ticTacToeModel.renderToBuffer(poseStack, vertexconsumer, packedLight, packedOverlay, 60 / 255F, 50 / 255F, 40 / 255F, 1.0F);
            poseStack.popPose();
        }

        String[] circleColor = blockEntity.getCircleColor().split("/");
        int[] rgb = new int[3];
        try {
            rgb[0] = Integer.parseInt(circleColor[0]);
            rgb[1] = Integer.parseInt(circleColor[1]);
            rgb[2] = Integer.parseInt(circleColor[2]);
        } catch (Exception ignored) {}

        for (int x = 0; x < rows.length; x++)
        {
            for (int y = 0; y < 3; y++)
            {
                char tileChar = rows[x].charAt(y);
                if(tileChar == 'O')
                {
                    poseStack.pushPose();
                    poseStack.translate(0.625F * -(x - 1), 0, 0);
                    poseStack.translate(0, 0, 0.625F * -(y - 1));
                    if(blockEntity.getTicTacToeFrame(y + 3 * x) != -1)
                    {
                        VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entityCutout(CIRCLE_TEXTURES.get(blockEntity.getTicTacToeFrame(y + 3 * x))));
                        ticTacToeModel.renderToBuffer(poseStack, vertexconsumer, packedLight, packedOverlay, rgb[0] / 255F, rgb[1] / 255F, rgb[2] / 255F, 1.0F);
                    }
                    poseStack.popPose();
                }
            }
        }

        String[] crossColor = blockEntity.getCrossColor().split("/");
        try {
            rgb[0] = Integer.parseInt(crossColor[0]);
            rgb[1] = Integer.parseInt(crossColor[1]);
            rgb[2] = Integer.parseInt(crossColor[2]);
        } catch (Exception ignored) {}
        for (int x = 0; x < rows.length; x++)
        {
            for (int y = 0; y < 3; y++)
            {
                char tileChar = rows[x].charAt(y);
                if(tileChar == 'X')
                {
                    poseStack.pushPose();
                    poseStack.translate(0.625F * -(x - 1), 0, 0);
                    poseStack.translate(0, 0, 0.625F * -(y - 1));
                    poseStack.mulPose(Axis.YN.rotationDegrees(45F));
                    if((blockEntity.getTicTacToeFrame(y + 3 * x) != -1) && (blockEntity.getTicTacToeFrame(y + 3 * x) <= 13))
                    {
                        VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entityCutout(CROSS_TEXTURES.get(blockEntity.getTicTacToeFrame(y + 3 * x))));
                        ticTacToeModel.renderToBuffer(poseStack, vertexconsumer, packedLight, packedOverlay, rgb[0] / 255F, rgb[1] / 255F, rgb[2] / 255F, 1.0F);
                    }
                    poseStack.popPose();
                }
            }
        }
        poseStack.popPose();
    }
}