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

public class ChessBoardPieceColorsScreen extends Screen
{
	private static final ResourceLocation MENU_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/chess_menu.png");
	private static final ResourceLocation PREVIEW_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/preview_color.png");
	private static final ResourceLocation PREVIEW_FRAME_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/medium_chess_menu.png");
	private final String colorSelectionText = new TranslationTextComponent("gui.table_top_craft.chess.color.piece").getString();
	private final String previewColorText = new TranslationTextComponent("gui.table_top_craft.chess.color.colors_preview").getString();
	private final String whitePieceSettingsText = new TranslationTextComponent("gui.table_top_craft.chess.color.white_piece_settings").getString();
	private final String blackPieceSettingsText = new TranslationTextComponent("gui.table_top_craft.chess.color.black_piece_settings").getString();
	private ChessTileEntity chessTileEntity;
	private final int xSize = 177;
	private final int ySize = 198;
	private ChessRedColorSlider whiteRedColorSlider;
	private ChessGreenColorSlider whiteGreenColorSlider;
	private ChessBlueColorSlider whiteBlueColorSlider;
	private ChessRedColorSlider blackRedColorSlider;
	private ChessGreenColorSlider blackGreenColorSlider;
	private ChessBlueColorSlider blackBlueColorSlider;
	
	public ChessBoardPieceColorsScreen(ChessTileEntity chessTileEntity)
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
	    this.addButton(this.whiteRedColorSlider = new ChessRedColorSlider(x + 5, y + 92, 167, 12, NBTColorSaving.getRed(this.chessTileEntity.getWhitePiecesColor())));
	    this.addButton(this.whiteGreenColorSlider = new ChessGreenColorSlider(x + 5, y + 105, 167, 12, NBTColorSaving.getGreen(this.chessTileEntity.getWhitePiecesColor())));
	    this.addButton(this.whiteBlueColorSlider = new ChessBlueColorSlider(x + 5, y + 118, 167, 12, NBTColorSaving.getBlue(this.chessTileEntity.getWhitePiecesColor())));
	    this.addButton(this.blackRedColorSlider = new ChessRedColorSlider(x + 5, y + 141, 167, 12, NBTColorSaving.getRed(this.chessTileEntity.getBlackPiecesColor())));
	    this.addButton(this.blackGreenColorSlider = new ChessGreenColorSlider(x + 5, y + 154, 167, 12, NBTColorSaving.getGreen(this.chessTileEntity.getBlackPiecesColor())));
	    this.addButton(this.blackBlueColorSlider = new ChessBlueColorSlider(x + 5, y + 167, 167, 12, NBTColorSaving.getBlue(this.chessTileEntity.getBlackPiecesColor())));
		
		// The Buttons in the Gui Menu
	    this.addButton(new ChessRandomColorButton(this.whiteRedColorSlider, this.blackRedColorSlider, this.whiteGreenColorSlider, this.blackGreenColorSlider, this.whiteBlueColorSlider, this.blackBlueColorSlider, x + 5, y + 68));
	    this.addButton(new ChessResetColorButton(DefaultColorType.PIECES, this.whiteRedColorSlider, this.blackRedColorSlider, this.whiteGreenColorSlider, this.blackGreenColorSlider, this.whiteBlueColorSlider, this.blackBlueColorSlider, x + 90, y + 68));
	    this.addButton(new ChessCancelButton(this.chessTileEntity, ChessCancelMenuTarget.CHESS_BOARD_COLORS, ChessCancelButtonText.CANCEL, x + 5, y + 180));
	    this.addButton(new ChessConfirmColorButton(ColorMenuType.PIECES, this.chessTileEntity, this.whiteRedColorSlider, this.blackRedColorSlider, this.whiteGreenColorSlider, this.blackGreenColorSlider, this.whiteBlueColorSlider, this.blackBlueColorSlider, x + 90, y + 180));
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
		this.minecraft.getTextureManager().bindTexture(PREVIEW_FRAME_TEXTURE);
		this.blit(matrixStack, x + 25, y + 25, 0, 131, 42, 42);
		this.blit(matrixStack, x + 110, y + 25, 0, 131, 42, 42);
		this.minecraft.getTextureManager().bindTexture(PREVIEW_TEXTURE);
		// White Color Preview
		matrixStack.push();
		RenderSystem.color3f((1F / 255F) * this.whiteRedColorSlider.getValueInt(), (1F / 255F) * this.whiteGreenColorSlider.getValueInt(), (1F / 255F) * this.whiteBlueColorSlider.getValueInt());
		matrixStack.translate(x + 26, y + 26, 0);
		matrixStack.scale(2.5F, 2.5F, 2.5F);
		this.blit(matrixStack, 0, 0, 0, 0, 16, 16);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		matrixStack.pop();
		// Black Color Preview
		matrixStack.push();
		RenderSystem.color3f((1F / 255F) * this.blackRedColorSlider.getValueInt(), (1F / 255F) * this.blackGreenColorSlider.getValueInt(), (1F / 255F) * this.blackBlueColorSlider.getValueInt());
		matrixStack.translate(x + 111, y + 26, 0);
		matrixStack.scale(2.5F, 2.5F, 2.5F);
		this.blit(matrixStack, 0, 0, 0, 0, 16, 16);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		matrixStack.pop();
		
		// The Menu Title
		this.font.drawString(matrixStack, this.colorSelectionText, ((this.width / 2) - (this.font.getStringWidth(this.colorSelectionText) / 2)), y + 6, 4210752);
		// The preview String
		this.font.drawString(matrixStack, this.previewColorText, x + 49, y + 16, 0x000000);
		// The color descriptions above the sliders
		this.font.drawString(matrixStack, this.whitePieceSettingsText, x + 5, y + 83, 0x000000);
		this.font.drawString(matrixStack, this.blackPieceSettingsText, x + 5, y + 132, 0x000000);
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
