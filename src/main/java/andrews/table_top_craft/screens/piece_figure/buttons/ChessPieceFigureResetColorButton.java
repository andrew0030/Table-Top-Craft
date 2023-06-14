package andrews.table_top_craft.screens.piece_figure.buttons;

import andrews.table_top_craft.screens.piece_figure.menus.ChessPieceFigureSettingsScreen;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ChessPieceFigureResetColorButton extends Button
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
    private final String buttonText = Component.translatable("gui.table_top_craft.chess.color.reset_color").getString();
    private final Font fontRenderer;
    private static final int buttonWidth = 82;
    private static final int buttonHeight = 13;
    private int u = 0;
    private int v = 0;
    private final ChessPieceFigureSettingsScreen screen;

    public ChessPieceFigureResetColorButton(ChessPieceFigureSettingsScreen chessPieceFigureSettingsScreen, int xPos, int yPos)
    {
        super(xPos, yPos, buttonWidth, buttonHeight, Component.literal(""), Button::onPress, DEFAULT_NARRATION);
        this.fontRenderer = Minecraft.getInstance().font;
        this.screen = chessPieceFigureSettingsScreen;
    }

    @Override
    public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
    {
        this.isHovered = mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height || isFocused();
        this.u = isHovered ? 82 : 0;
        // Renders the Button
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        GuiComponent.blit(poseStack, x, y, u, v, width, height);
        this.fontRenderer.draw(poseStack, this.buttonText, x + ((this.width / 2) - (this.fontRenderer.width(this.buttonText) / 2)), y + 3, 0x000000);
    }

    @Override
    public void onPress()
    {
        screen.getRedSlider().setValue(210F);
        screen.getGreenSlider().setValue(188F);
        screen.getBlueSlider().setValue(161F);
        if(screen.isColorPickerActive())
            screen.getColorPicker().updateColorPickerFromSliders();
    }
}