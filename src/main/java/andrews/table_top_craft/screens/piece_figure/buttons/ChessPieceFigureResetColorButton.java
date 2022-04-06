package andrews.table_top_craft.screens.piece_figure.buttons;

import andrews.table_top_craft.screens.chess.sliders.ChessBlueColorSlider;
import andrews.table_top_craft.screens.chess.sliders.ChessGreenColorSlider;
import andrews.table_top_craft.screens.chess.sliders.ChessRedColorSlider;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class ChessPieceFigureResetColorButton extends Button
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
    private final String buttonText = new TranslatableComponent("gui.table_top_craft.chess.color.reset_color").getString();
    private final Font fontRenderer;
    private static final int buttonWidth = 82;
    private static final int buttonHeight = 13;
    private int u = 0;
    private int v = 0;
    private static ChessRedColorSlider redSlider;
    private static ChessGreenColorSlider greenSlider;
    private static ChessBlueColorSlider blueSlider;

    public ChessPieceFigureResetColorButton(ChessRedColorSlider red, ChessGreenColorSlider green, ChessBlueColorSlider blue, int xPos, int yPos)
    {
        super(xPos, yPos, buttonWidth, buttonHeight, new TextComponent(""), (button) -> { handleButtonPress(); });
        this.fontRenderer = Minecraft.getInstance().font;
        redSlider = red;
        greenSlider = green;
        blueSlider = blue;
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
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
        this.blit(poseStack, x, y, u, v, width, height);
        RenderSystem.disableBlend();
        poseStack.popPose();
        this.fontRenderer.draw(poseStack, this.buttonText, x + ((this.width / 2) - (this.fontRenderer.width(this.buttonText) / 2)), y + 3, 0x000000);
    }

    /**
     * Gets called when the Button gets pressed
     */
    private static void handleButtonPress()
    {
        redSlider.setValue(210F);
        greenSlider.setValue(188F);
        blueSlider.setValue(161F);
    }
}