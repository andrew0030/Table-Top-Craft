package andrews.table_top_craft.screens.chess.buttons.settings;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import andrews.table_top_craft.screens.chess.menus.ChessBoardSettingsScreen;
import andrews.table_top_craft.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class ChessMoveLogDownButton extends Button
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
	private static final int buttonWidth = 12;
	private static final int buttonHeight = 23;
	private int u = 0;
	private int v = 89;
	private static ChessBoardSettingsScreen screen;
	
	public ChessMoveLogDownButton(int xPos, int yPos, ChessBoardSettingsScreen currentScreen) 
	{
		super(xPos, yPos, buttonWidth, buttonHeight, new StringTextComponent(""), (button) -> { handleButtonPress(); });
		screen = currentScreen;
	}
	
	@Override
	public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		this.isHovered = false;
		if(mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height || this.isFocused())
			this.isHovered = true;
		
		this.u = 0;
		if(this.isHovered)
			this.u += 12;
		
		this.active = true;
		if(screen.getMoveLogOffset() <= 0)
		{
			this.active = false;
			this.u = 24;
		}
		
		//Renders the Button
		Minecraft.getInstance().getRenderManager().textureManager.bindTexture(TEXTURE);
		matrixStack.push();
		RenderSystem.enableBlend();
		GuiUtils.drawTexturedModalRect(matrixStack, x, y, u, v, width, height, 0);
		RenderSystem.disableBlend();
		matrixStack.pop();
	}
	
	/**
	 * Gets called when the Button gets pressed
	 */
	private static void handleButtonPress()
	{
		screen.decreaseMoveLogOffset();
	}
}