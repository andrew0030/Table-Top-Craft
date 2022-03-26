package andrews.table_top_craft.screens.chess.buttons.colors;

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

import java.util.Random;

public class ChessRandomColorButton extends Button
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
	private final String buttonText = new TranslatableComponent("gui.table_top_craft.chess.color.random_color").getString();
	private final String buttonText2 = new TranslatableComponent("gui.table_top_craft.chess.color.random_colors").getString();
	private final Font fontRenderer;
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
		super(xPos, yPos, buttonWidth, buttonHeight, new TextComponent(""), (button) -> { handleButtonPress(); });
		this.fontRenderer = Minecraft.getInstance().font;
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
			System.out.println("Wasn't able to randomize the color slider values!");
		}
	}
}