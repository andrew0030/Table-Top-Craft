package andrews.table_top_craft.screens.piece_figure.sliders;

import andrews.table_top_craft.screens.util.BaseSlider;
import andrews.table_top_craft.screens.util.GuiUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;

public class ChessPieceFigureScaleSlider extends BaseSlider
{
    private final static TranslatableComponent scaleValueText = new TranslatableComponent("gui.table_top_craft.chess_piece_figure.sliders.scale");

    public ChessPieceFigureScaleSlider(int xPos, int yPos, int width, int height, double currentValue)
    {
//        super(xPos, yPos, width, height, scaleValueText, new TextComponent(""), 0, 255, currentValue, true);
        super(xPos, yPos, width, height, scaleValueText, new TextComponent(""), 1, 5, currentValue, 0.01D, 0, true);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        return false;
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
    {
        Minecraft mc = Minecraft.getInstance();
        int k = this.getYImage(this.isHovered);
        GuiUtils.drawContinuousTexturedBox(poseStack, WIDGETS_LOCATION, this.x, this.y, 0, 46 + k * 20, this.width, this.height, 200, 20, 2, 3, 2, 2, this.getBlitOffset());
        this.renderBg(poseStack, mc, mouseX, mouseY);

        Component buttonText = this.getMessage();
        int strWidth = mc.font.width(buttonText);
        int ellipsisWidth = mc.font.width("...");

        if (strWidth > width - 6 && strWidth > ellipsisWidth)
            buttonText = new TextComponent(mc.font.substrByWidth(buttonText, width - 6 - ellipsisWidth).getString() + "...");

        drawCenteredString(poseStack, mc.font, buttonText, this.x + this.width / 2, this.y + (this.height - 8) / 2, getFGColor());
    }

    @Override
    protected void renderBg(PoseStack poseStack, Minecraft minecraft, int mouseX, int mouseY)
    {
        RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int offset = (this.isHoveredOrFocused() ? 2 : 1) * 20;
        GuiUtils.drawContinuousTexturedBox(poseStack, WIDGETS_LOCATION, this.x + (int)(this.value * (float)(this.width - 8)), this.y, 0, 46 + offset, 8, this.height, 200, 20, 2, 3, 2, 2, this.getBlitOffset());
    }
}