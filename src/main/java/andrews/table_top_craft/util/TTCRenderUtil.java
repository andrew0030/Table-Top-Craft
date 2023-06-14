package andrews.table_top_craft.util;

import andrews.table_top_craft.animation.model.AdvancedModelPart;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Vector3f;

import java.util.ArrayList;

public class TTCRenderUtil
{
    public static void renderLine(MultiBufferSource buffer, PoseStack poseStack, int red, int green, int blue, float startX, float startY, float startZ, float endX, float endY, float endZ)
    {
        VertexConsumer builder = buffer.getBuffer(TTCRenderTypes.skeletonDebugLines());
        // Start Vertex
        builder.vertex(poseStack.last().pose(), startX, startY, startZ);
        builder.color(red, green, blue, 255);
        builder.endVertex();
        // End Vertex
        builder.vertex(poseStack.last().pose(), endX, endY, endZ);
        builder.color(red, green, blue, 255);
        builder.endVertex();
    }

    public static void renderText(Component text, float posX, float posY, float posZ, float scale, PoseStack poseStack, MultiBufferSource buffer, int packedLight)
    {
//        poseStack.pushPose();
//        poseStack.translate(posX, posY, posZ);
//        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
//        poseStack.scale(-0.025F, -0.025F, 0.025F);
//        poseStack.scale(scale, scale, scale);
//        Font font = Minecraft.getInstance().font;
//        float centerOffset = (float)(-font.width(text) / 2);
//        font.drawInBatch(text, centerOffset, -9, -1, false, poseStack.last().pose(), buffer, true, 0, packedLight);
//        poseStack.popPose();
    }

    public static void renderQuad(MultiBufferSource buffer, PoseStack poseStack, int red, int green, int blue, float posX, float posY, float posZ, float size)
    {
        float scale = size / 2F;
        VertexConsumer builder = buffer.getBuffer(TTCRenderTypes.skeletonDebugQuad());
        poseStack.pushPose();
        poseStack.translate(posX, posY, posZ);
        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        builder.vertex(poseStack.last().pose(), -scale, scale, 0);
        builder.color(red, green, blue, 255);
        builder.endVertex();
        builder.vertex(poseStack.last().pose(), scale, scale, 0);
        builder.color(red, green, blue, 255);
        builder.endVertex();
        builder.vertex(poseStack.last().pose(), scale, -scale, 0);
        builder.color(red, green, blue, 255);
        builder.endVertex();
        builder.vertex(poseStack.last().pose(), -scale, -scale, 0);
        builder.color(red, green, blue, 255);
        builder.endVertex();
        poseStack.popPose();
    }

    public static void renderCross(MultiBufferSource buffer, PoseStack poseStack, int red, int green, int blue, float xPos, float yPos, float zPos, float size)
    {
        TTCRenderUtil.renderLine(buffer, poseStack, red, green, blue, xPos - (size / 2F), yPos, zPos, xPos + (size / 2F), yPos, zPos);
        TTCRenderUtil.renderLine(buffer, poseStack, red, green, blue, xPos, yPos - (size / 2F), zPos, xPos, yPos + (size / 2F), zPos);
        TTCRenderUtil.renderLine(buffer, poseStack, red, green, blue, xPos, yPos, zPos - (size / 2F), xPos, yPos, zPos + (size / 2F));
    }

    public static void renderCross(LivingEntity entity, MultiBufferSource buffer, PoseStack poseStack, float xPos, float yPos, float zPos, float xRot, float yRot, float zRot, float size)
    {
        poseStack.pushPose();
        poseStack.translate(xPos, yPos, zPos);
        poseStack.mulPose(Axis.YN.rotationDegrees(Mth.lerp(Minecraft.getInstance().getPartialTick(), entity.yBodyRotO, entity.yBodyRot)));
        poseStack.mulPose(Axis.ZN.rotation(zRot));
        poseStack.mulPose(Axis.YN.rotation(yRot));
        poseStack.mulPose(Axis.XP.rotation(xRot));
        TTCRenderUtil.renderLine(buffer, poseStack, 255, 0, 0, -size / 2F, 0, 0, size / 2F, 0, 0);
        TTCRenderUtil.renderLine(buffer, poseStack, 0, 255, 0, 0, -size / 2F, 0, 0, size / 2F, 0);
        TTCRenderUtil.renderLine(buffer, poseStack, 0, 0, 255, 0, 0, -size / 2F, 0, 0, size / 2F);
        poseStack.popPose();
    }

    public static void renderChainFromModelPart(LivingEntity entity, MultiBufferSource buffer, PoseStack poseStack, AdvancedModelPart part, int packedLight, boolean showNames)
    {
        ArrayList<Vector3f> partCoords = new ArrayList<>();
        ArrayList<String> partNames = new ArrayList<>();
        while(part != null) {
            // We store the name and coordinates of the current part
            partCoords.add(part.getModelSpace(entity));
            partNames.add(part.getName());
            // We set the part to the parent of the current part, if its null the loop will stop
            part = part.getParent();
        }
        // Renders the Lines
        for(int i = 1; i < partCoords.size(); i++)
        {
            Vector3f start = partCoords.get(i - 1);
            Vector3f end = partCoords.get(i);
            TTCRenderUtil.renderLine(buffer, poseStack, 0, 255, 100, start.x, start.y, start.z, end.x, end.y, end.z);
        }
        // Renders the Lines
        for(Vector3f pos : partCoords)
        {
            TTCRenderUtil.renderQuad(buffer, poseStack, 255, 255, 255, pos.x, pos.y, pos.z, 0.02F);
        }
        if(showNames)
        {
            // Renders the Names
            for (int i = 0; i < partCoords.size(); i++)
            {
                Vector3f pos = partCoords.get(i);
                String name = partNames.get(i);
                TTCRenderUtil.renderText(Component.literal(name), pos.x, (pos.y + 0.05F), pos.z, 0.2F, poseStack, buffer, packedLight);
            }
        }
    }
}