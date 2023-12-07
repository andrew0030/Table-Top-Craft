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
import andrews.table_top_craft.screens.chess.sliders.ChessAlphaColorSlider;
import andrews.table_top_craft.screens.chess.sliders.ChessBlueColorSlider;
import andrews.table_top_craft.screens.chess.sliders.ChessGreenColorSlider;
import andrews.table_top_craft.screens.chess.sliders.ChessRedColorSlider;
import andrews.table_top_craft.screens.piece_figure.util.ColorPickerToggleButton;
import andrews.table_top_craft.screens.piece_figure.util.IColorPicker;
import andrews.table_top_craft.screens.piece_figure.util.SaturationSlider;
import andrews.table_top_craft.screens.piece_figure.util.TTCColorPicker;
import andrews.table_top_craft.util.Color;
import andrews.table_top_craft.util.NBTColorSaving;
import andrews.table_top_craft.util.Reference;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ChessBoardAttackMoveColorScreen extends BaseScreen implements IColorPicker
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/medium_chess_menu.png");
	private static final ResourceLocation PREVIEW_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/preview_color.png");
	private static final ResourceLocation COLOR_PICKER_FRAME_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/color_picker/color_picker_frame.png");
	private static final Component TITLE = Component.translatable("gui.table_top_craft.chess.color.attack_move");
	private static final Component PREVIEW_COLOR_TXT = Component.translatable("gui.table_top_craft.chess.color.color_preview");
	private final ChessBlockEntity chessBlockEntity;
	private final boolean isColorPickerActive;
	private ChessAlphaColorSlider alphaColorSlider;
	private ChessRedColorSlider redColorSlider;
	private ChessGreenColorSlider greenColorSlider;
	private ChessBlueColorSlider blueColorSlider;
	private TTCColorPicker colorPicker;
	private SaturationSlider saturationSlider;
	
	public ChessBoardAttackMoveColorScreen(ChessBlockEntity chessBlockEntity, boolean isColorPickerActive)
	{
		super(TEXTURE, 177, 131, TITLE);
		this.chessBlockEntity = chessBlockEntity;
		this.isColorPickerActive = isColorPickerActive;
	}
	
	@Override
	protected void init()
	{
		super.init();
		// Color Picker Toggle Button
		this.addRenderableWidget(new ColorPickerToggleButton(this.chessBlockEntity, this, false, this.x + 48, this.y + 47));
		// RGBA Sliders
	    this.addRenderableWidget(this.redColorSlider = new ChessRedColorSlider(this.x + 5, this.y + 61, 167, 12, NBTColorSaving.getRed(this.chessBlockEntity.getAttackMoveColor()), this));
	    this.addRenderableWidget(this.greenColorSlider = new ChessGreenColorSlider(this.x + 5, this.y + 74, 167, 12, NBTColorSaving.getGreen(this.chessBlockEntity.getAttackMoveColor()), this));
	    this.addRenderableWidget(this.blueColorSlider = new ChessBlueColorSlider(this.x + 5, this.y + 87, 167, 12, NBTColorSaving.getBlue(this.chessBlockEntity.getAttackMoveColor()), this));
	    this.addRenderableWidget(this.alphaColorSlider = new ChessAlphaColorSlider(this.x + 5, this.y + 100, 167, 12, NBTColorSaving.getAlpha(this.chessBlockEntity.getAttackMoveColor())));
		// The Color Buttons in the Gui Menu
	    this.addRenderableWidget(new ChessRandomColorButton(this, this.x + 69, this.y + 33));
	    this.addRenderableWidget(new ChessResetColorButton(DefaultColorType.ATTACK_MOVE, this, this.x + 69, this.y + 47));
	    this.addRenderableWidget(new ChessCancelButton(this.chessBlockEntity, ChessCancelMenuTarget.CHESS_BOARD_COLORS, ChessCancelButtonText.CANCEL, this.x + 5, this.y + 113));
	    this.addRenderableWidget(new ChessConfirmColorButton(ColorMenuType.ATTACK_MOVE, this.chessBlockEntity, this.redColorSlider, this.greenColorSlider, this.blueColorSlider, this.alphaColorSlider, this.x + 90, this.y + 113));
		// The Color Picker
		if(isColorPickerActive)
		{
			Color color = new Color(redColorSlider.getValueInt(), greenColorSlider.getValueInt(), blueColorSlider.getValueInt());
			this.addRenderableWidget(colorPicker = new TTCColorPicker(this.x - 131, this.y - 5, this, color.getHue() / 360F, 1.0F - color.getValue()));
			// The saturation Slider
			float saturation = color.getSaturation() * 100;
			this.addRenderableWidget(saturationSlider = new SaturationSlider(this.x - 132, this.y + 125, 130, 12, Math.round(saturation), this));
		}
	}

	// We need to render the Color Picker behind the menu in some cases, I'm achieving this by using the render method.
	// This may cause problems in combination with using a gradiant, but given the menu doesn't have one it should be fine.
	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
	{
		// Color Picker Frame
		if(isColorPickerActive)
			graphics.blit(COLOR_PICKER_FRAME_TEXTURE, this.x - 136, this.y - 10, 0, 0, 139, 151);
		super.render(graphics, mouseX, mouseY, partialTick);
	}

	@Override
	public void renderScreenContents(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
	{
		// Preview Color Frame
		graphics.blit(TEXTURE, this.x + 5, this.y + 18, 0, 131, 42, 42);
		// Preview Color
		graphics.pose().pushPose();
		graphics.setColor((1F / 255F) * this.redColorSlider.getValueInt(), (1F / 255F) * this.greenColorSlider.getValueInt(), (1F / 255F) * this.blueColorSlider.getValueInt(), (1F / 255F) * this.alphaColorSlider.getValueInt());
		graphics.pose().translate(this.x + 6, this.y + 19, 0);
		graphics.pose().scale(2.5F, 2.5F, 2.5F);
		graphics.blit(PREVIEW_TEXTURE, 0, 0, 0, 0, 16, 16);
		graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
		graphics.pose().popPose();
		// The Menu Title
		this.drawCenteredString(TITLE, this.width / 2 + (this.isColorPickerActive ? 68 : 0), this.y + 6, 4210752, false, graphics);
		// The Preview String
		graphics.drawString(this.font, PREVIEW_COLOR_TXT, this.x + 49, this.y + 18, 0x000000, false);
	}

	@Override
	public int offsetX()
	{
		return isColorPickerActive ? 136 : super.offsetX();
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
		return this.redColorSlider;
	}

	@Override
	public BaseSlider getGreenSlider()
	{
		return this.greenColorSlider;
	}

	@Override
	public BaseSlider getBlueSlider()
	{
		return this.blueColorSlider;
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
}