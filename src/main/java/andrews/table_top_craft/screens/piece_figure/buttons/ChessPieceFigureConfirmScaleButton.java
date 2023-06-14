package andrews.table_top_craft.screens.piece_figure.buttons;

import andrews.table_top_craft.block_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.screens.piece_figure.sliders.ChessPieceFigureScaleSlider;
import andrews.table_top_craft.util.NetworkUtil;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ChessPieceFigureConfirmScaleButton extends Button
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
    private final String buttonText = Component.translatable("gui.table_top_craft.chess_piece_figure.confirm_scale").getString();
    private final Font fontRenderer;
    private static ChessPieceFigureBlockEntity chessPieceFigureBlockEntity;
    private static final int buttonWidth = 167;
    private static final int buttonHeight = 13;
    private int u = 0;
    private int v = 50;
    private static ChessPieceFigureScaleSlider scaleSlider;

    public ChessPieceFigureConfirmScaleButton(ChessPieceFigureBlockEntity blockEntity, ChessPieceFigureScaleSlider slider, int xPos, int yPos)
    {
        super(xPos, yPos, buttonWidth, buttonHeight, Component.literal(""), (button) -> { handleButtonPress(); }, DEFAULT_NARRATION);
        this.fontRenderer = Minecraft.getInstance().font;
        chessPieceFigureBlockEntity = blockEntity;
        scaleSlider = slider;
    }

    @Override
    public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
    {
        this.isHovered = mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height || this.isFocused();

        this.v = 50;
        if(this.isHovered)
            this.v += 13;

        // Renders the Button
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        poseStack.pushPose();
        RenderSystem.enableBlend();
        GuiComponent.blit(poseStack, x, y, u, v, width, height);
        RenderSystem.disableBlend();
        poseStack.popPose();
        this.fontRenderer.draw(poseStack, this.buttonText, x + ((this.width / 2) - (this.fontRenderer.width(this.buttonText) / 2)), y + 3, 0x000000);
    }

    /**
     * Gets called when the Button gets pressed
     */
    private static void handleButtonPress()
    {
        NetworkUtil.setPieceScale(chessPieceFigureBlockEntity.getBlockPos(), scaleSlider.getValue());
    }
}