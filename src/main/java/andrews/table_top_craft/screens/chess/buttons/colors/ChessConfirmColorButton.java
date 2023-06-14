package andrews.table_top_craft.screens.chess.buttons.colors;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.screens.chess.sliders.ChessAlphaColorSlider;
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

public class ChessConfirmColorButton extends Button
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
	private final String buttonText = Component.translatable("gui.table_top_craft.chess.confirm_color").getString();
	private final String buttonText2 = Component.translatable("gui.table_top_craft.chess.confirm_colors").getString();
	private final Font fontRenderer;
	private static ChessBlockEntity chessBlockEntity;
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
	
	public ChessConfirmColorButton(ColorMenuType colorMenu, ChessBlockEntity tileEntity, ChessRedColorSlider red, ChessGreenColorSlider green, ChessBlueColorSlider blue, int xPos, int yPos)
	{
		super(xPos, yPos, buttonWidth, buttonHeight, Component.literal(""), (button) -> { handleButtonPress(); }, DEFAULT_NARRATION);
		this.fontRenderer = Minecraft.getInstance().font;
		colorMenuType = colorMenu;
		chessBlockEntity = tileEntity;
		redSlider = red;
		greenSlider = green;
		blueSlider = blue;
	}
	
	public ChessConfirmColorButton(ColorMenuType colorMenu, ChessBlockEntity tileEntity, ChessRedColorSlider red, ChessGreenColorSlider green, ChessBlueColorSlider blue, ChessAlphaColorSlider alpha, int xPos, int yPos)
	{
		this(colorMenu, tileEntity, red, green, blue, xPos, yPos);
		alphaSlider = alpha;
	}
	
	public ChessConfirmColorButton(ColorMenuType colorMenu, ChessBlockEntity tileEntity, ChessRedColorSlider red, ChessRedColorSlider optionalRed, ChessGreenColorSlider green, ChessGreenColorSlider optionalGreen, ChessBlueColorSlider blue, ChessBlueColorSlider optionalBlue, int xPos, int yPos)
	{
		this(colorMenu, tileEntity, red, green, blue, xPos, yPos);
		optionalRedSlider = optionalRed;
		optionalGreenSlider = optionalGreen;
		optionalBlueSlider = optionalBlue;
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
		boolean useText2 = (optionalRedSlider != null && optionalGreenSlider != null && optionalBlueSlider != null);
		this.fontRenderer.draw(poseStack, useText2 ? this.buttonText2 : this.buttonText, x + ((this.width / 2) - (this.fontRenderer.width(useText2 ? this.buttonText2 : this.buttonText) / 2)), y + 3, 0x000000);
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
			NetworkUtil.setColorMessage(0, chessBlockEntity.getBlockPos(), NBTColorSaving.saveColor(redSlider.getValueInt(), greenSlider.getValueInt(), blueSlider.getValueInt()));
			break;
		case BOARD_PLATE:
			if(optionalRedSlider != null && optionalGreenSlider != null && optionalBlueSlider != null)
				NetworkUtil.setColorsMessage(0, chessBlockEntity.getBlockPos(), NBTColorSaving.saveColor(redSlider.getValueInt(), greenSlider.getValueInt(), blueSlider.getValueInt()), NBTColorSaving.saveColor(optionalRedSlider.getValueInt(), optionalGreenSlider.getValueInt(), optionalBlueSlider.getValueInt()));
			break;
		case PIECES:
			if(optionalRedSlider != null && optionalGreenSlider != null && optionalBlueSlider != null)
				NetworkUtil.setColorsMessage(1, chessBlockEntity.getBlockPos(), NBTColorSaving.saveColor(redSlider.getValueInt(), greenSlider.getValueInt(), blueSlider.getValueInt()), NBTColorSaving.saveColor(optionalRedSlider.getValueInt(), optionalGreenSlider.getValueInt(), optionalBlueSlider.getValueInt()));
			break;
		case LEGAL_MOVE:
			if(alphaSlider != null)
				NetworkUtil.setColorMessage(1, chessBlockEntity.getBlockPos(), NBTColorSaving.saveColor(redSlider.getValueInt(), greenSlider.getValueInt(), blueSlider.getValueInt(), alphaSlider.getValueInt()));
			break;
		case INVALID_MOVE:
			if(alphaSlider != null)
				NetworkUtil.setColorMessage(2, chessBlockEntity.getBlockPos(), NBTColorSaving.saveColor(redSlider.getValueInt(), greenSlider.getValueInt(), blueSlider.getValueInt(), alphaSlider.getValueInt()));
			break;
		case ATTACK_MOVE:
			if(alphaSlider != null)
				NetworkUtil.setColorMessage(3, chessBlockEntity.getBlockPos(), NBTColorSaving.saveColor(redSlider.getValueInt(), greenSlider.getValueInt(), blueSlider.getValueInt(), alphaSlider.getValueInt()));
			break;
		case PREVIOUS_MOVE:
			if(alphaSlider != null)
				NetworkUtil.setColorMessage(4, chessBlockEntity.getBlockPos(), NBTColorSaving.saveColor(redSlider.getValueInt(), greenSlider.getValueInt(), blueSlider.getValueInt(), alphaSlider.getValueInt()));
			break;
		case CASTLE_MOVE:
			if(alphaSlider != null)
				NetworkUtil.setColorMessage(5, chessBlockEntity.getBlockPos(), NBTColorSaving.saveColor(redSlider.getValueInt(), greenSlider.getValueInt(), blueSlider.getValueInt(), alphaSlider.getValueInt()));
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