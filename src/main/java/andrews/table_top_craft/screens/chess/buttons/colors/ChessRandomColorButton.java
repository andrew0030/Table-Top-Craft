package andrews.table_top_craft.screens.chess.buttons.colors;

import andrews.table_top_craft.screens.piece_figure.util.IColorPicker;
import andrews.table_top_craft.screens.piece_figure.util.IColorPickerExtended;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Random;

public class ChessRandomColorButton extends Button
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
	private final String buttonText = Component.translatable("gui.table_top_craft.chess.color.random_color").getString();
	private final String buttonText2 = Component.translatable("gui.table_top_craft.chess.color.random_colors").getString();
	private final Font fontRenderer;
	private static final int buttonWidth = 82;
	private static final int buttonHeight = 13;
	private int u = 0;
	private int v = 0;
	private static Screen screen;

	public ChessRandomColorButton(Screen screenIn, int xPos, int yPos)
	{
		super(xPos, yPos, buttonWidth, buttonHeight, Component.literal(""), (button) -> { handleButtonPress(); }, DEFAULT_NARRATION);
		this.fontRenderer = Minecraft.getInstance().font;
		screen = screenIn;
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
		boolean useText2 = screen instanceof IColorPickerExtended;
		this.fontRenderer.draw(poseStack, useText2 ? this.buttonText2 : this.buttonText, x + ((this.width / 2) - (this.fontRenderer.width(useText2 ? this.buttonText2 : this.buttonText) / 2)), y + 3, 0x000000);
	}
	
	/**
	 * Gets called when the Button gets pressed
	 */
	private static void handleButtonPress()
	{
		Random rand = new Random();
		try
		{
			if (screen instanceof IColorPicker colorPicker && screen instanceof IColorPickerExtended colorPickerExtended)
			{
				colorPicker.getRedSlider().setValue(rand.nextInt(256));
				colorPicker.getGreenSlider().setValue(rand.nextInt(256));
				colorPicker.getBlueSlider().setValue(rand.nextInt(256));
				colorPickerExtended.getOptionalRedSlider().setValue(rand.nextInt(256));
				colorPickerExtended.getOptionalGreenSlider().setValue(rand.nextInt(256));
				colorPickerExtended.getOptionalBlueSlider().setValue(rand.nextInt(256));

				if (colorPicker.isColorPickerActive() || colorPickerExtended.isOptionalColorPickerActive())
					colorPicker.getColorPicker().updateColorPickerFromSliders();
			}
			else if(screen instanceof IColorPicker colorPicker)
			{
				colorPicker.getRedSlider().setValue(rand.nextInt(256));
				colorPicker.getGreenSlider().setValue(rand.nextInt(256));
				colorPicker.getBlueSlider().setValue(rand.nextInt(256));

				if (colorPicker.isColorPickerActive())
					colorPicker.getColorPicker().updateColorPickerFromSliders();
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			System.out.println("Wasn't able to randomize the color slider values!");
		}
	}
}