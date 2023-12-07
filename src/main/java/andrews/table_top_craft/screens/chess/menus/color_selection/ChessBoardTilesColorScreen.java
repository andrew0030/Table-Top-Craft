package andrews.table_top_craft.screens.chess.menus.color_selection;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.screens.base.BaseScreen;
import andrews.table_top_craft.screens.base.BaseSlider;
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
import andrews.table_top_craft.screens.piece_figure.util.*;
import andrews.table_top_craft.util.Color;
import andrews.table_top_craft.util.NBTColorSaving;
import andrews.table_top_craft.util.Reference;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ChessBoardTilesColorScreen extends BaseScreen implements IColorPicker, IColorPickerExtended
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/chess_menu.png");
	private static final ResourceLocation PREVIEW_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/preview_color.png");
	private static final ResourceLocation PREVIEW_FRAME_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/medium_chess_menu.png");
	private static final ResourceLocation COLOR_PICKER_FRAME_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/color_picker/color_picker_frame.png");
	private static final Component TITLE = Component.translatable("gui.table_top_craft.chess.color.tiles");
	private static final Component PREVIEW_COLOR_TXT = Component.translatable("gui.table_top_craft.chess.color.colors_preview");
	private static final Component WHITE_PREVIEW_COLOR_TXT = Component.translatable("gui.table_top_craft.chess.color.white_tiles_settings");
	private static final Component BLACK_PREVIEW_COLOR_TXT = Component.translatable("gui.table_top_craft.chess.color.black_tiles_settings");
	private final ChessBlockEntity chessBlockEntity;
	private final boolean isColorPickerActive;
	private final boolean isOptionalColorPickerActive;
	private ChessRedColorSlider whiteRedColorSlider;
	private ChessGreenColorSlider whiteGreenColorSlider;
	private ChessBlueColorSlider whiteBlueColorSlider;
	private ChessRedColorSlider blackRedColorSlider;
	private ChessGreenColorSlider blackGreenColorSlider;
	private ChessBlueColorSlider blackBlueColorSlider;
	private TTCColorPicker colorPicker;
	private SaturationSlider saturationSlider;
	
	public ChessBoardTilesColorScreen(ChessBlockEntity chessBlockEntity, boolean isColorPickerActive, boolean isOptionalColorPickerActive)
	{
		super(TEXTURE, 177, 198, TITLE);
		this.chessBlockEntity = chessBlockEntity;
		this.isColorPickerActive = isColorPickerActive;
		this.isOptionalColorPickerActive = isOptionalColorPickerActive;
	}
	
	@Override
	protected void init()
	{
		super.init();
		// Color Picker Toggle Buttons
		this.addRenderableWidget(new ColorPickerToggleButton(this.chessBlockEntity, this, false, this.x + 68, this.y + 54));
		this.addRenderableWidget(new ColorPickerToggleButton(this.chessBlockEntity, this, true, this.x + 153, this.y + 54));
		// RGB Sliders
	    this.addRenderableWidget(this.whiteRedColorSlider = new ChessRedColorSlider(this.x + 5, this.y + 92, 167, 12, NBTColorSaving.getRed(this.chessBlockEntity.getWhiteTilesColor()), this));
	    this.addRenderableWidget(this.whiteGreenColorSlider = new ChessGreenColorSlider(this.x + 5, this.y + 105, 167, 12, NBTColorSaving.getGreen(this.chessBlockEntity.getWhiteTilesColor()), this));
	    this.addRenderableWidget(this.whiteBlueColorSlider = new ChessBlueColorSlider(this.x + 5, this.y + 118, 167, 12, NBTColorSaving.getBlue(this.chessBlockEntity.getWhiteTilesColor()), this));
	    this.addRenderableWidget(this.blackRedColorSlider = new ChessRedColorSlider(this.x + 5, this.y + 141, 167, 12, NBTColorSaving.getRed(this.chessBlockEntity.getBlackTilesColor()), this));
	    this.addRenderableWidget(this.blackGreenColorSlider = new ChessGreenColorSlider(this.x + 5, this.y + 154, 167, 12, NBTColorSaving.getGreen(this.chessBlockEntity.getBlackTilesColor()), this));
	    this.addRenderableWidget(this.blackBlueColorSlider = new ChessBlueColorSlider(this.x + 5, this.y + 167, 167, 12, NBTColorSaving.getBlue(this.chessBlockEntity.getBlackTilesColor()), this));
		// The Color Buttons in the Gui Menu
	    this.addRenderableWidget(new ChessRandomColorButton(this, this.x + 5, this.y + 68));
	    this.addRenderableWidget(new ChessResetColorButton(DefaultColorType.BOARD_TILES, this, this.x + 90, this.y + 68));
	    this.addRenderableWidget(new ChessCancelButton(this.chessBlockEntity, ChessCancelMenuTarget.CHESS_BOARD_COLORS, ChessCancelButtonText.CANCEL, this.x + 5, this.y + 180));
	    this.addRenderableWidget(new ChessConfirmColorButton(ColorMenuType.BOARD_PLATE, this.chessBlockEntity, this.whiteRedColorSlider, this.blackRedColorSlider, this.whiteGreenColorSlider, this.blackGreenColorSlider, this.whiteBlueColorSlider, this.blackBlueColorSlider, this.x + 90, this.y + 180));
		// The Color Pickers
		if(isColorPickerActive)
		{
			Color color = new Color(whiteRedColorSlider.getValueInt(), whiteGreenColorSlider.getValueInt(), whiteBlueColorSlider.getValueInt());
			this.addRenderableWidget(colorPicker = new TTCColorPicker(this.x - 131, this.y + 28, this, color.getHue() / 360F, 1.0F - color.getValue()));
			// The saturation Slider
			float saturation = color.getSaturation() * 100;
			this.addRenderableWidget(saturationSlider = new SaturationSlider(this.x - 132, this.y + 158, 130, 12, Math.round(saturation), this));
		}
		else if(isOptionalColorPickerActive)
		{
			Color color = new Color(blackRedColorSlider.getValueInt(), blackGreenColorSlider.getValueInt(), blackBlueColorSlider.getValueInt());
			this.addRenderableWidget(colorPicker = new TTCColorPicker(this.x - 131, this.y + 28, this, color.getHue() / 360F, 1.0F - color.getValue()));
			// The saturation Slider
			float saturation = color.getSaturation() * 100;
			this.addRenderableWidget(saturationSlider = new SaturationSlider(this.x - 132, this.y + 158, 130, 12, Math.round(saturation), this));
		}
	}

	@Override
	public void renderScreenContents(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
	{
		// Color Picker Frame
		if(isColorPickerActive || isOptionalColorPickerActive)
			graphics.blit(COLOR_PICKER_FRAME_TEXTURE, this.x - 136, this.y + 23, 0, 0, 136, 151);
		// Preview Color Frame
		graphics.blit(PREVIEW_FRAME_TEXTURE, this.x + 25, this.y + 25, 0, 131, 42, 42);
		graphics.blit(PREVIEW_FRAME_TEXTURE, this.x + 110, this.y + 25, 0, 131, 42, 42);
		// White Preview Color
		graphics.pose().pushPose();
		graphics.setColor((1F / 255F) * this.whiteRedColorSlider.getValueInt(), (1F / 255F) * this.whiteGreenColorSlider.getValueInt(), (1F / 255F) * this.whiteBlueColorSlider.getValueInt(), 1.0f);
		graphics.pose().translate(this.x + 26, this.y + 26, 0);
		graphics.pose().scale(2.5F, 2.5F, 2.5F);
		graphics.blit(PREVIEW_TEXTURE, 0, 0, 0, 0, 16, 16);
		graphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		graphics.pose().popPose();
		// Black Preview Color
		graphics.pose().pushPose();
		graphics.setColor((1F / 255F) * this.blackRedColorSlider.getValueInt(), (1F / 255F) * this.blackGreenColorSlider.getValueInt(), (1F / 255F) * this.blackBlueColorSlider.getValueInt(), 1.0f);
		graphics.pose().translate(this.x + 111, this.y + 26, 0);
		graphics.pose().scale(2.5F, 2.5F, 2.5F);
		graphics.blit(PREVIEW_TEXTURE, 0, 0, 0, 0, 16, 16);
		graphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		graphics.pose().popPose();
		// The Menu Title
		this.drawCenteredString(TITLE, this.width / 2 + (this.isColorPickerActive || this.isOptionalColorPickerActive ? 68 : 0), this.y + 6, 4210752, false, graphics);
		// The preview String
		graphics.drawString(this.font, PREVIEW_COLOR_TXT, this.x + 88 - (this.font.width(PREVIEW_COLOR_TXT) / 2), this.y + 16, 0x000000, false);
		// The color descriptions above the sliders
		graphics.drawString(this.font, WHITE_PREVIEW_COLOR_TXT, this.x + 5, this.y + 83, 0x000000, false);
		graphics.drawString(this.font, BLACK_PREVIEW_COLOR_TXT, this.x + 5, this.y + 132, 0x000000, false);
	}

	@Override
	public int offsetX()
	{
		return isColorPickerActive || isOptionalColorPickerActive ? 136 : super.offsetX();
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers)
	{
		super.keyPressed(keyCode, scanCode, modifiers);
		if(this.minecraft.options.keyInventory.matches(keyCode, scanCode))
			this.onClose();
		return true;
	}

	@Override
	public boolean isPauseScreen()
	{
		return false;
	}

	@Override
	public TTCColorPicker getColorPicker()
	{
		return this.colorPicker;
	}

	@Override
	public BaseSlider getRedSlider()
	{
		return this.whiteRedColorSlider;
	}

	@Override
	public BaseSlider getGreenSlider()
	{
		return this.whiteGreenColorSlider;
	}

	@Override
	public BaseSlider getBlueSlider()
	{
		return this.whiteBlueColorSlider;
	}

	@Override
	public BaseSlider getSaturationSlider()
	{
		return this.saturationSlider;
	}

	@Override
	public boolean isColorPickerActive()
	{
		return this.isColorPickerActive;
	}

	@Override
	public BaseSlider getOptionalRedSlider()
	{
		return this.blackRedColorSlider;
	}

	@Override
	public BaseSlider getOptionalGreenSlider()
	{
		return this.blackGreenColorSlider;
	}

	@Override
	public BaseSlider getOptionalBlueSlider()
	{
		return this.blackBlueColorSlider;
	}

	@Override
	public boolean isOptionalColorPickerActive()
	{
		return this.isOptionalColorPickerActive;
	}
}