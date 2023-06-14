package andrews.table_top_craft.screens.piece_figure.buttons;

import andrews.table_top_craft.block_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.screens.chess.sliders.ChessBlueColorSlider;
import andrews.table_top_craft.screens.chess.sliders.ChessGreenColorSlider;
import andrews.table_top_craft.screens.chess.sliders.ChessRedColorSlider;
import andrews.table_top_craft.util.NBTColorSaving;
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

public class ChessPieceFigureConfirmColorButton extends Button
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
    private final String buttonText = Component.translatable("gui.table_top_craft.chess.confirm_color").getString();
    private final Font fontRenderer;
    private static ChessPieceFigureBlockEntity chessPieceFigureBlockEntity;
    private static final int buttonWidth = 82;
    private static final int buttonHeight = 13;
    private int u = 0;
    private int v = 0;
    private static ChessRedColorSlider redSlider;
    private static ChessGreenColorSlider greenSlider;
    private static ChessBlueColorSlider blueSlider;

    public ChessPieceFigureConfirmColorButton(ChessPieceFigureBlockEntity blockEntity, ChessRedColorSlider red, ChessGreenColorSlider green, ChessBlueColorSlider blue, int xPos, int yPos)
    {
        super(xPos, yPos, buttonWidth, buttonHeight, Component.literal(""), (button) -> { handleButtonPress(); }, DEFAULT_NARRATION);
        this.fontRenderer = Minecraft.getInstance().font;
        chessPieceFigureBlockEntity = blockEntity;
        redSlider = red;
        greenSlider = green;
        blueSlider = blue;
    }

    @Override
    public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
    {
        this.isHovered = mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height || this.isFocused();

        this.u = 0;
        if(this.isHovered)
            this.u = 82;

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
        NetworkUtil.setColorMessage(6, chessPieceFigureBlockEntity.getBlockPos(), NBTColorSaving.saveColor(redSlider.getValueInt(), greenSlider.getValueInt(), blueSlider.getValueInt()));
    }
}