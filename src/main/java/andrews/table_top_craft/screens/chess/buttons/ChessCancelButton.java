package andrews.table_top_craft.screens.chess.buttons;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import andrews.table_top_craft.screens.chess.menus.ChessBoardSettingsScreen;
import andrews.table_top_craft.screens.chess.menus.ChessColorSettingsScreen;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class ChessCancelButton extends Button
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
	private final String buttonText = new TranslationTextComponent("gui.table_top_craft.chess.cancel").getString();
	private static ChessCancelMenuTarget targetScreen;
	private final FontRenderer fontRenderer;
	private static ChessTileEntity chessTileEntity;
	private static final int buttonWidth = 82;
	private static final int buttonHeight = 13;
	private int u = 0;
	private int v = 0;
	
	public ChessCancelButton(ChessTileEntity tileEntity, ChessCancelMenuTarget target, int xPos, int yPos) 
	{
		super(xPos, yPos, buttonWidth, buttonHeight, new StringTextComponent(""), (button) -> { handleButtonPress(); });
		this.fontRenderer = Minecraft.getInstance().fontRenderer;
		chessTileEntity = tileEntity;
		targetScreen = target;
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
		this.fontRenderer.drawString(matrixStack, this.buttonText, x + ((this.width / 2) - (this.fontRenderer.getStringWidth(this.buttonText) / 2)), y + 3, 0x000000);
	}
	
	/**
	 * Gets called when the Button gets pressed
	 */
	private static void handleButtonPress()
	{
		switch(targetScreen)
		{
		default:
		case CHESS_BOARD_SETTINGS:
			Minecraft.getInstance().displayGuiScreen(new ChessBoardSettingsScreen(chessTileEntity));
			break;
		case CHESS_BOARD_COLORS:
			Minecraft.getInstance().displayGuiScreen(new ChessColorSettingsScreen(chessTileEntity));
		}
	}
	
	public enum ChessCancelMenuTarget
	{
		CHESS_BOARD_SETTINGS,
		CHESS_BOARD_COLORS;
	}
}