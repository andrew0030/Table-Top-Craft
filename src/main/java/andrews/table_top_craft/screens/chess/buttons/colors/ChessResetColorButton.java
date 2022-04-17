package andrews.table_top_craft.screens.chess.buttons.colors;

import andrews.table_top_craft.screens.chess.sliders.ChessBlueColorSlider;
import andrews.table_top_craft.screens.chess.sliders.ChessGreenColorSlider;
import andrews.table_top_craft.screens.chess.sliders.ChessRedColorSlider;
import andrews.table_top_craft.screens.piece_figure.util.IColorPicker;
import andrews.table_top_craft.screens.piece_figure.util.IColorPickerExtended;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class ChessResetColorButton extends Button
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
	private final String buttonText = new TranslatableComponent("gui.table_top_craft.chess.color.reset_color").getString();
	private final String buttonText2 = new TranslatableComponent("gui.table_top_craft.chess.color.reset_colors").getString();
	private final Font fontRenderer;
	private static final int buttonWidth = 82;
	private static final int buttonHeight = 13;
	private int u = 0;
	private int v = 0;
	private static DefaultColorType colorType;
	private static ChessRedColorSlider redSlider;
	private static ChessGreenColorSlider greenSlider;
	private static ChessBlueColorSlider blueSlider;
	private static ChessRedColorSlider optionalRedSlider;
	private static ChessGreenColorSlider optionalGreenSlider;
	private static ChessBlueColorSlider optionalBlueSlider;
	private static Screen screen;

	//TODO replace with IColorPicker screen
	@Deprecated
	public ChessResetColorButton(DefaultColorType defaultColorType, ChessRedColorSlider red, ChessGreenColorSlider green, ChessBlueColorSlider blue, int xPos, int yPos) 
	{
		super(xPos, yPos, buttonWidth, buttonHeight, new TextComponent(""), (button) -> { handleButtonPress(); });
		this.fontRenderer = Minecraft.getInstance().font;
		redSlider = red;
		greenSlider = green;
		blueSlider = blue;
		colorType = defaultColorType;
	}

	//TODO replace with IColorPicker screen
	@Deprecated
	public ChessResetColorButton(DefaultColorType defaultColorType, ChessRedColorSlider red, ChessRedColorSlider optionalRed, ChessGreenColorSlider green, ChessGreenColorSlider optionalGreen, ChessBlueColorSlider blue, ChessBlueColorSlider optionalBlue, int xPos, int yPos) 
	{
		this(defaultColorType, red, green, blue, xPos, yPos);
		optionalRedSlider = optionalRed;
		optionalGreenSlider = optionalGreen;
		optionalBlueSlider = optionalBlue; 
	}

	public ChessResetColorButton(DefaultColorType defaultColorType, Screen screenIn, int xPos, int yPos)
	{
		super(xPos, yPos, buttonWidth, buttonHeight, new TextComponent(""), (button) -> { handleButtonPress(); });
		this.fontRenderer = Minecraft.getInstance().font;
		colorType = defaultColorType;
		screen = screenIn;
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
		boolean useText2 = (optionalRedSlider != null && optionalGreenSlider != null && optionalBlueSlider != null);
		this.fontRenderer.draw(poseStack, useText2 ? this.buttonText2 : this.buttonText, x + ((this.width / 2) - (this.fontRenderer.width(useText2 ? this.buttonText2 : this.buttonText) / 2)), y + 3, 0x000000);
	}
	
	/**
	 * Gets called when the Button gets pressed
	 */
	private static void handleButtonPress()
	{
		if (screen instanceof IColorPicker colorPicker)
		{
			switch (colorType)
			{
				default:
				case TILE_INFO_COLOR:
					colorPicker.getRedSlider().setValue(255F);
					colorPicker.getGreenSlider().setValue(255F);
					colorPicker.getBlueSlider().setValue(255F);
					break;
				case BOARD_TILES:
					if (screen instanceof IColorPickerExtended colorPickerExtended)
					{
						colorPicker.getRedSlider().setValue(208F);
						colorPicker.getGreenSlider().setValue(177F);
						colorPicker.getBlueSlider().setValue(141F);
						colorPickerExtended.getOptionalRedSlider().setValue(139F);
						colorPickerExtended.getOptionalGreenSlider().setValue(86F);
						colorPickerExtended.getOptionalBlueSlider().setValue(57F);
						// Technically this isnt the ideal way to do it because it gets updated twice when the
						// none optional Color Picker is Active, but it will do the trick.
						if(colorPickerExtended.isOptionalColorPickerActive())
							colorPicker.getColorPicker().updateColorPickerFromSliders();
					}
					break;
				case PIECES:
					if (screen instanceof IColorPickerExtended colorPickerExtended)
					{
						colorPicker.getRedSlider().setValue(210F);
						colorPicker.getGreenSlider().setValue(188F);
						colorPicker.getBlueSlider().setValue(161F);
						colorPickerExtended.getOptionalRedSlider().setValue(51F);
						colorPickerExtended.getOptionalGreenSlider().setValue(51F);
						colorPickerExtended.getOptionalBlueSlider().setValue(51F);
						// Technically this isnt the ideal way to do it because it gets updated twice when the
						// none optional Color Picker is Active, but it will do the trick.
						if(colorPickerExtended.isOptionalColorPickerActive())
							colorPicker.getColorPicker().updateColorPickerFromSliders();
					}
					break;
				case LEGAL_MOVE:
					colorPicker.getRedSlider().setValue(1F);
					colorPicker.getGreenSlider().setValue(255F);
					colorPicker.getBlueSlider().setValue(1F);
					break;
				case INVALID_MOVE:
					colorPicker.getRedSlider().setValue(255F);
					colorPicker.getGreenSlider().setValue(255F);
					colorPicker.getBlueSlider().setValue(1F);
					break;
				case ATTACK_MOVE:
					colorPicker.getRedSlider().setValue(255F);
					colorPicker.getGreenSlider().setValue(1F);
					colorPicker.getBlueSlider().setValue(1F);
					break;
				case PREVIOUS_MOVE:
					colorPicker.getRedSlider().setValue(1F);
					colorPicker.getGreenSlider().setValue(150F);
					colorPicker.getBlueSlider().setValue(125F);
					break;
				case CASTLE_MOVE:
					colorPicker.getRedSlider().setValue(125F);
					colorPicker.getGreenSlider().setValue(1F);
					colorPicker.getBlueSlider().setValue(255F);
			}

			if(colorPicker.isColorPickerActive())
				colorPicker.getColorPicker().updateColorPickerFromSliders();
		}
	}
	
	public enum DefaultColorType
	{
		TILE_INFO_COLOR,
		BOARD_TILES,
		PIECES,
		LEGAL_MOVE,
		INVALID_MOVE,
		ATTACK_MOVE,
		PREVIOUS_MOVE,
		CASTLE_MOVE;
	}
}