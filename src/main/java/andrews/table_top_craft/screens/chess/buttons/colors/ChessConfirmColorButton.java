package andrews.table_top_craft.screens.chess.buttons.colors;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import andrews.table_top_craft.screens.chess.sliders.ChessAlphaColorSlider;
import andrews.table_top_craft.screens.chess.sliders.ChessBlueColorSlider;
import andrews.table_top_craft.screens.chess.sliders.ChessGreenColorSlider;
import andrews.table_top_craft.screens.chess.sliders.ChessRedColorSlider;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.NBTColorSaving;
import andrews.table_top_craft.util.NetworkUtil;
import andrews.table_top_craft.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class ChessConfirmColorButton extends Button
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
	private final String buttonText = new TranslationTextComponent("gui.table_top_craft.chess.confirm_color").getString();
	private final String buttonText2 = new TranslationTextComponent("gui.table_top_craft.chess.confirm_colors").getString();
	private final FontRenderer fontRenderer;
	private static ChessTileEntity chessTileEntity;
	private static final int buttonWidth = 82;
	private static final int buttonHeight = 13;
	private int u = 0;
	private int v = 0;
	private static ChessRedColorSlider redSlider;
	private static ChessGreenColorSlider greenSlider;
	private static ChessBlueColorSlider blueSlider;
	private static ChessAlphaColorSlider alphaSlider;
	private static ChessRedColorSlider optionalRedSlider;
	private static ChessGreenColorSlider optionalGreenSlider;
	private static ChessBlueColorSlider optionalBlueSlider;
	private static ColorMenuType colorMenuType;
	
	public ChessConfirmColorButton(ColorMenuType colorMenu, ChessTileEntity tileEntity, ChessRedColorSlider red, ChessGreenColorSlider green, ChessBlueColorSlider blue, int xPos, int yPos) 
	{
		super(xPos, yPos, buttonWidth, buttonHeight, new StringTextComponent(""), (button) -> { handleButtonPress(); });
		this.fontRenderer = Minecraft.getInstance().fontRenderer;
		colorMenuType = colorMenu;
		chessTileEntity = tileEntity;
		redSlider = red;
		greenSlider = green;
		blueSlider = blue;
	}
	
	public ChessConfirmColorButton(ColorMenuType colorMenu, ChessTileEntity tileEntity, ChessRedColorSlider red, ChessGreenColorSlider green, ChessBlueColorSlider blue, ChessAlphaColorSlider alpha, int xPos, int yPos) 
	{
		this(colorMenu, tileEntity, red, green, blue, xPos, yPos);
		alphaSlider = alpha;
	}
	
	public ChessConfirmColorButton(ColorMenuType colorMenu, ChessTileEntity tileEntity, ChessRedColorSlider red, ChessRedColorSlider optionalRed, ChessGreenColorSlider green, ChessGreenColorSlider optionalGreen, ChessBlueColorSlider blue, ChessBlueColorSlider optionalBlue, int xPos, int yPos) 
	{
		this(colorMenu, tileEntity, red, green, blue, xPos, yPos);
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
		switch(colorMenuType)
		{
		default:
		case TILE_INFO:
			NetworkUtil.setColorMessage(0, chessTileEntity.getPos(), NBTColorSaving.saveColor(redSlider.getValueInt(), greenSlider.getValueInt(), blueSlider.getValueInt()));
			break;
		case BOARD_PLATE:
			if(optionalRedSlider != null && optionalGreenSlider != null && optionalBlueSlider != null)
				NetworkUtil.setColorsMessage(0, chessTileEntity.getPos(), NBTColorSaving.saveColor(redSlider.getValueInt(), greenSlider.getValueInt(), blueSlider.getValueInt()), NBTColorSaving.saveColor(optionalRedSlider.getValueInt(), optionalGreenSlider.getValueInt(), optionalBlueSlider.getValueInt()));
			break;
		case PIECES:
			if(optionalRedSlider != null && optionalGreenSlider != null && optionalBlueSlider != null)
				NetworkUtil.setColorsMessage(1, chessTileEntity.getPos(), NBTColorSaving.saveColor(redSlider.getValueInt(), greenSlider.getValueInt(), blueSlider.getValueInt()), NBTColorSaving.saveColor(optionalRedSlider.getValueInt(), optionalGreenSlider.getValueInt(), optionalBlueSlider.getValueInt()));
			break;
		case LEGAL_MOVE:
			if(alphaSlider != null)
				NetworkUtil.setColorMessage(1, chessTileEntity.getPos(), NBTColorSaving.saveColor(redSlider.getValueInt(), greenSlider.getValueInt(), blueSlider.getValueInt(), alphaSlider.getValueInt()));
			break;
		case INVALID_MOVE:
			if(alphaSlider != null)
				NetworkUtil.setColorMessage(2, chessTileEntity.getPos(), NBTColorSaving.saveColor(redSlider.getValueInt(), greenSlider.getValueInt(), blueSlider.getValueInt(), alphaSlider.getValueInt()));
			break;
		case ATTACK_MOVE:
			if(alphaSlider != null)
				NetworkUtil.setColorMessage(3, chessTileEntity.getPos(), NBTColorSaving.saveColor(redSlider.getValueInt(), greenSlider.getValueInt(), blueSlider.getValueInt(), alphaSlider.getValueInt()));
			break;
		case PREVIOUS_MOVE:
			if(alphaSlider != null)
				NetworkUtil.setColorMessage(4, chessTileEntity.getPos(), NBTColorSaving.saveColor(redSlider.getValueInt(), greenSlider.getValueInt(), blueSlider.getValueInt(), alphaSlider.getValueInt()));
			break;
		case CASTLE_MOVE:
			if(alphaSlider != null)
				NetworkUtil.setColorMessage(5, chessTileEntity.getPos(), NBTColorSaving.saveColor(redSlider.getValueInt(), greenSlider.getValueInt(), blueSlider.getValueInt(), alphaSlider.getValueInt()));
		}
	}
	
	public enum ColorMenuType
	{
		TILE_INFO,
		BOARD_PLATE,
		PIECES,
		LEGAL_MOVE,
		INVALID_MOVE,
		ATTACK_MOVE,
		PREVIOUS_MOVE,
		CASTLE_MOVE;
	}
}