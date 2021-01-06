package andrews.table_top_craft.screens.chess.buttons.colors;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import andrews.table_top_craft.screens.chess.sliders.ChessBlueColorSlider;
import andrews.table_top_craft.screens.chess.sliders.ChessGreenColorSlider;
import andrews.table_top_craft.screens.chess.sliders.ChessRedColorSlider;
import andrews.table_top_craft.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class ChessRandomColorButton extends Button
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
	private final String buttonText = new TranslationTextComponent("gui.table_top_craft.chess.color.random_color").getString();
	private final String buttonText2 = new TranslationTextComponent("gui.table_top_craft.chess.color.random_colors").getString();
	private final FontRenderer fontRenderer;
	private static final int buttonWidth = 82;
	private static final int buttonHeight = 13;
	private int u = 0;
	private int v = 0;
	private static ChessRedColorSlider redSlider;
	private static ChessGreenColorSlider greenSlider;
	private static ChessBlueColorSlider blueSlider;
	private static ChessRedColorSlider optionalRedSlider;
	private static ChessGreenColorSlider optionalGreenSlider;
	private static ChessBlueColorSlider optionalBlueSlider;
	
	public ChessRandomColorButton(ChessRedColorSlider red, ChessGreenColorSlider green, ChessBlueColorSlider blue, int xPos, int yPos) 
	{
		super(xPos, yPos, buttonWidth, buttonHeight, new StringTextComponent(""), (button) -> { handleButtonPress(); });
		this.fontRenderer = Minecraft.getInstance().fontRenderer;
		redSlider = red;
		greenSlider = green;
		blueSlider = blue;
	}
	
	public ChessRandomColorButton(ChessRedColorSlider red, ChessRedColorSlider optionalRed, ChessGreenColorSlider green, ChessGreenColorSlider optionalGreen, ChessBlueColorSlider blue, ChessBlueColorSlider optionalBlue, int xPos, int yPos) 
	{
		this(red, green, blue, xPos, yPos);
		optionalRedSlider = optionalRed;
		optionalGreenSlider = optionalGreen;
		optionalBlueSlider = optionalBlue; 
	}
	
	@Override
	public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		this.isHovered = false;
		if(mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height || this.isFocused())
			this.isHovered = true;
		
		this.u = 0;
		if(this.isHovered)
			this.u = 82;
		
		//Renders the Button
		Minecraft.getInstance().getRenderManager().textureManager.bindTexture(TEXTURE);
		matrixStack.push();
		RenderSystem.enableBlend();
		GuiUtils.drawTexturedModalRect(matrixStack, x, y, u, v, width, height, 0);
		RenderSystem.disableBlend();
		matrixStack.pop();
		boolean useText2 = (optionalRedSlider != null && optionalGreenSlider != null && optionalBlueSlider != null);
		this.fontRenderer.drawString(matrixStack, useText2 ? this.buttonText2 : this.buttonText, x + ((this.width / 2) - (this.fontRenderer.getStringWidth(useText2 ? this.buttonText2 : this.buttonText) / 2)), y + 3, 0x000000);
	}
	
	/**
	 * Gets called when the Button gets pressed
	 */
	private static void handleButtonPress()
	{
		Random rand = new Random();
		try
		{
			redSlider.setValue(rand.nextInt(255) + 1);
			greenSlider.setValue(rand.nextInt(255) + 1);
			blueSlider.setValue(rand.nextInt(255) + 1);
			// We need to update the sliders so the text that displays the value is correct
			redSlider.updateSlider();
			greenSlider.updateSlider();
			blueSlider.updateSlider();
			if(optionalRedSlider != null && optionalGreenSlider != null && optionalBlueSlider != null)
			{
				optionalRedSlider.setValue(rand.nextInt(255) + 1);
				optionalGreenSlider.setValue(rand.nextInt(255) + 1);
				optionalBlueSlider.setValue(rand.nextInt(255) + 1);
				// We need to update the sliders so the text that displays the value is correct
				optionalRedSlider.updateSlider();
				optionalGreenSlider.updateSlider();
				optionalBlueSlider.updateSlider();
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			System.out.println("Wasnt able to randomize the color slider values!");
		}
	}
}

