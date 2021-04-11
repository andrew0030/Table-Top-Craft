package andrews.table_top_craft.screens.chess.menus.color_selection;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import andrews.table_top_craft.screens.chess.buttons.ChessCancelButton;
import andrews.table_top_craft.screens.chess.buttons.ChessCancelButton.ChessCancelButtonText;
import andrews.table_top_craft.screens.chess.buttons.ChessCancelButton.ChessCancelMenuTarget;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessConfirmColorButton;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessConfirmColorButton.ColorMenuType;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessRandomColorButton;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessResetColorButton;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessResetColorButton.DefaultColorType;
import andrews.table_top_craft.screens.chess.sliders.ChessAlphaColorSlider;
import andrews.table_top_craft.screens.chess.sliders.ChessBlueColorSlider;
import andrews.table_top_craft.screens.chess.sliders.ChessGreenColorSlider;
import andrews.table_top_craft.screens.chess.sliders.ChessRedColorSlider;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.NBTColorSaving;
import andrews.table_top_craft.util.Reference;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ChessBoardPreviousMoveColorScreen extends Screen
{
	private static final ResourceLocation MENU_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/medium_chess_menu.png");
	private static final ResourceLocation PREVIEW_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/preview_color.png");
	private final String colorSelectionText = new TranslationTextComponent("gui.table_top_craft.chess.color.previous_move").getString();
	private final String previewColorText = new TranslationTextComponent("gui.table_top_craft.chess.color.color_preview").getString();
	private ChessTileEntity chessTileEntity;
	private final int xSize = 177;
	private final int ySize = 131;
	private ChessAlphaColorSlider alphaColorSlider;
	private ChessRedColorSlider redColorSlider;
	private ChessGreenColorSlider greenColorSlider;
	private ChessBlueColorSlider blueColorSlider;
	
	public ChessBoardPreviousMoveColorScreen(ChessTileEntity chessTileEntity)
	{
		super(new StringTextComponent(""));
		this.chessTileEntity = chessTileEntity;
	}
	
	@Override
	public boolean isPauseScreen()
	{
		return false;
	}
	
	@Override
	protected void init()
	{
		super.init();
		// Values to calculate the relative position
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

	    // Sliders 
		// NOTE: We have to load the Buttons first because we use them in one of the Sliders
	    this.addButton(this.redColorSlider = new ChessRedColorSlider(x + 5, y + 61, 167, 12, NBTColorSaving.getRed(this.chessTileEntity.getPreviousMoveColor())));
	    this.addButton(this.greenColorSlider = new ChessGreenColorSlider(x + 5, y + 74, 167, 12, NBTColorSaving.getGreen(this.chessTileEntity.getPreviousMoveColor())));
	    this.addButton(this.blueColorSlider = new ChessBlueColorSlider(x + 5, y + 87, 167, 12, NBTColorSaving.getBlue(this.chessTileEntity.getPreviousMoveColor())));
	    this.addButton(this.alphaColorSlider = new ChessAlphaColorSlider(x + 5, y + 100, 167, 12, NBTColorSaving.getAlpha(this.chessTileEntity.getPreviousMoveColor())));
		
		// The Buttons in the Gui Menu
	    this.addButton(new ChessRandomColorButton(this.redColorSlider, this.greenColorSlider, this.blueColorSlider, x + 69, y + 33));
	    this.addButton(new ChessResetColorButton(DefaultColorType.PREVIOUS_MOVE, this.redColorSlider, this.greenColorSlider, this.blueColorSlider, x + 69, y + 47));
	    this.addButton(new ChessCancelButton(this.chessTileEntity, ChessCancelMenuTarget.CHESS_BOARD_COLORS, ChessCancelButtonText.CANCEL, x + 5, y + 113));
	    this.addButton(new ChessConfirmColorButton(ColorMenuType.PREVIOUS_MOVE, this.chessTileEntity, this.redColorSlider, this.greenColorSlider, this.blueColorSlider, this.alphaColorSlider, x + 90, y + 113));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		
		this.minecraft.getTextureManager().bindTexture(MENU_TEXTURE);
		this.blit(matrixStack, x, y, 0, 0, this.xSize, this.ySize);
		
		// The Preview of the Color
		this.blit(matrixStack, x + 5, y + 18, 0, 131, 42, 42);
		this.minecraft.getTextureManager().bindTexture(PREVIEW_TEXTURE);
		matrixStack.push();
		RenderSystem.color4f((1F / 255F) * this.redColorSlider.getValueInt(), (1F / 255F) * this.greenColorSlider.getValueInt(), (1F / 255F) * this.blueColorSlider.getValueInt(), (1F / 255F) * this.alphaColorSlider.getValueInt());
		matrixStack.translate(x + 6, y + 19, 0);
		matrixStack.scale(2.5F, 2.5F, 2.5F);
		this.blit(matrixStack, 0, 0, 0, 0, 16, 16);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		matrixStack.pop();
		
		// The Menu Title
		this.font.drawString(matrixStack, this.colorSelectionText, ((this.width / 2) - (this.font.getStringWidth(this.colorSelectionText) / 2)), y + 6, 4210752);
		// The preview String
		this.font.drawString(matrixStack, this.previewColorText, x + 49, y + 18, 0x000000);
		// Renders the Buttons we added in init
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers)
	{
		super.keyPressed(keyCode, scanCode, modifiers);
		InputMappings.Input mouseKey = InputMappings.getInputByCode(keyCode, scanCode);
		if(this.minecraft.gameSettings.keyBindInventory.isActiveAndMatches(mouseKey))
			this.closeScreen();
		return true;
	}
}