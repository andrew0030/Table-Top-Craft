package andrews.table_top_craft.block_entities.render;

import andrews.table_top_craft.objects.blocks.ChessTimerBlock;
import andrews.table_top_craft.objects.blocks.ChessTimerBlock.PressedButton;
import andrews.table_top_craft.block_entities.ChessTimerBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;

public class ChessTimerBlockEntityRenderer implements BlockEntityRenderer<ChessTimerBlockEntity>
{
    public ChessTimerBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(ChessTimerBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay)
    {
        Font font = Minecraft.getInstance().font;
        float scale = 0.46F;
        Direction facing = Direction.NORTH;
        PressedButton pressedButton = PressedButton.NONE;

        long leftTimerSeconds = (blockEntity.getLeftTimer() / 1000) % 60;
        long leftTimerMinutes = ((blockEntity.getLeftTimer() / 1000) / 60) % 60;
        long leftTimerHours = (blockEntity.getLeftTimer() / 1000) / 3600;
        String leftTimerText = (leftTimerHours > 0) ?
                String.format("%02d:%02d", leftTimerHours, leftTimerMinutes) :
                String.format("%02d:%02d", leftTimerMinutes, leftTimerSeconds);

        long rightTimerSeconds = (blockEntity.getRightTimer() / 1000) % 60;
        long rightTimerMinutes = ((blockEntity.getRightTimer() / 1000) / 60) % 60;
        long rightTimerHours = (blockEntity.getRightTimer() / 1000) / 3600;
        String rightTimerText = (rightTimerHours > 0) ?
                String.format("%02d:%02d", rightTimerHours, rightTimerMinutes) :
                String.format("%02d:%02d", rightTimerMinutes, rightTimerSeconds);

        if(blockEntity.getLeftTimer() == 0)
            leftTimerText = "§c" + leftTimerText;
        if(blockEntity.getRightTimer() == 0)
            rightTimerText = "§c" + rightTimerText;

        String text = leftTimerText + "§f  " + rightTimerText;

        poseStack.pushPose();
        poseStack.translate(0.5F, 0F, 0.5F);

        if(blockEntity.getBlockState().hasProperty(ChessTimerBlock.FACING) && blockEntity.getBlockState().hasProperty(ChessTimerBlock.PRESSED_BUTTON))
        {
            facing = blockEntity.getBlockState().getValue(ChessTimerBlock.FACING);
            pressedButton = blockEntity.getBlockState().getValue(ChessTimerBlock.PRESSED_BUTTON);
        }

        switch(facing) {
            default:
            case NORTH:
                break;
            case SOUTH:
                poseStack.mulPose(Axis.YN.rotationDegrees(180.0F));
                break;
            case WEST:
                poseStack.mulPose(Axis.YN.rotationDegrees(270.0F));
                break;
            case EAST:
                poseStack.mulPose(Axis.YN.rotationDegrees(90.0F));
        }

        poseStack.translate(0.0F, 0.0625F * 2F, 0.0625F * -2.01F);
        poseStack.scale(-0.025F, -0.025F, 0.025F);
        poseStack.scale(scale, scale, scale);
        float centerOffset = -font.width(text) / 2F;
        font.draw(poseStack, text, centerOffset, -9, 16777215);
        String playerIndicatorText = pressedButton.equals(PressedButton.NONE) ? "-" : (pressedButton.equals(PressedButton.LEFT) ? ">" : "<");
        font.draw(poseStack, playerIndicatorText, -font.width(playerIndicatorText) / 2F, -9, 0x7a7a7a);
        poseStack.popPose();
    }
}