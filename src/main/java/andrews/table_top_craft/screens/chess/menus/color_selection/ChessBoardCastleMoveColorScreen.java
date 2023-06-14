package andrews.table_top_craft.screens.chess.menus.color_selection;

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
import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.util.Color;
import andrews.table_top_craft.util.NBTColorSaving;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.widget.ForgeSlider;

public class ChessBoardCastleMoveColorScreen extends Screen implements IColorPicker
{
	private static final ResourceLocation MENU_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/medium_chess_menu.png");
	private static final ResourceLocation PREVIEW_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/preview_color.png");
	private static final ResourceLocation COLOR_PICKER_FRAME_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/color_picker/color_picker_frame.png");
	private final String colorSelectionText = Component.translatable("gui.table_top_craft.chess.color.castle_move").getString();
	private final String previewColorText = Component.translatable("gui.table_top_craft.chess.color.color_preview").getString();
	private final ChessBlockEntity chessBlockEntity;
	private final int xSize = 177;
	private final int ySize = 131;
	private ChessAlphaColorSlider alphaColorSlider;
	private ChessRedColorSlider redColorSlider;
	private ChessGreenColorSlider greenColorSlider;
	private ChessBlueColorSlider blueColorSlider;
	private TTCColorPicker colorPicker;
	private SaturationSlider saturationSlider;
	private final boolean isColorPickerActive;
	
	public ChessBoardCastleMoveColorScreen(ChessBlockEntity chessBlockEntity, boolean isColorPickerActive)
	{
		super(Component.literal(""));
		this.chessBlockEntity = chessBlockEntity;
		this.isColorPickerActive = isColorPickerActive;
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
		int x = (this.width - (this.xSize - (this.isColorPickerActive ? 136 : 0))) / 2;
		int y = (this.height - this.ySize) / 2;

		this.addRenderableWidget(new ColorPickerToggleButton(this.chessBlockEntity, this, false, x + 48, y + 47));

		// RGBA Sliders
	    this.addRenderableWidget(this.redColorSlider = new ChessRedColorSlider(x + 5, y + 61, 167, 12, NBTColorSaving.getRed(this.chessBlockEntity.getCastleMoveColor()), this));
	    this.addRenderableWidget(this.greenColorSlider = new ChessGreenColorSlider(x + 5, y + 74, 167, 12, NBTColorSaving.getGreen(this.chessBlockEntity.getCastleMoveColor()), this));
	    this.addRenderableWidget(this.blueColorSlider = new ChessBlueColorSlider(x + 5, y + 87, 167, 12, NBTColorSaving.getBlue(this.chessBlockEntity.getCastleMoveColor()), this));
	    this.addRenderableWidget(this.alphaColorSlider = new ChessAlphaColorSlider(x + 5, y + 100, 167, 12, NBTColorSaving.getAlpha(this.chessBlockEntity.getCastleMoveColor())));
		
		// The Buttons in the Gui Menu
	    this.addRenderableWidget(new ChessRandomColorButton(this, x + 69, y + 33));
	    this.addRenderableWidget(new ChessResetColorButton(DefaultColorType.CASTLE_MOVE, this, x + 69, y + 47));
	    this.addRenderableWidget(new ChessCancelButton(this.chessBlockEntity, ChessCancelMenuTarget.CHESS_BOARD_COLORS, ChessCancelButtonText.CANCEL, x + 5, y + 113));
	    this.addRenderableWidget(new ChessConfirmColorButton(ColorMenuType.CASTLE_MOVE, this.chessBlockEntity, this.redColorSlider, this.greenColorSlider, this.blueColorSlider, this.alphaColorSlider, x + 90, y + 113));

		if(isColorPickerActive)
		{
			// The Color Picker
			Color color = new Color(redColorSlider.getValueInt(), greenColorSlider.getValueInt(), blueColorSlider.getValueInt());
			this.addRenderableWidget(colorPicker = new TTCColorPicker(x - 131, y - 5, this, color.getHue() / 360F, 1.0F - color.getValue()));
			// The saturation Slider
			float saturation = color.getSaturation() * 100;
			this.addRenderableWidget(saturationSlider = new SaturationSlider(x - 132, y + 125, 130, 12, Math.round(saturation), this));
		}
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
	{
		int x = (this.width - (this.xSize - (this.isColorPickerActive ? 136 : 0))) / 2;
		int y = (this.height - this.ySize) / 2;

		if(isColorPickerActive)
		{
			RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
			RenderSystem.setShaderTexture(0, COLOR_PICKER_FRAME_TEXTURE);
			GuiComponent.blit(poseStack, x - 136, y - 10, 0, 0, 139, 151);
		}

		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, MENU_TEXTURE);
		GuiComponent.blit(poseStack, x, y, 0, 0, this.xSize, this.ySize);
		
		// The Preview of the Color
		GuiComponent.blit(poseStack, x + 5, y + 18, 0, 131, 42, 42);
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, PREVIEW_TEXTURE);
		poseStack.pushPose();
		RenderSystem.setShaderColor((1F / 255F) * this.redColorSlider.getValueInt(), (1F / 255F) * this.greenColorSlider.getValueInt(), (1F / 255F) * this.blueColorSlider.getValueInt(), (1F / 255F) * this.alphaColorSlider.getValueInt());
		poseStack.translate(x + 6, y + 19, 0);
		poseStack.scale(2.5F, 2.5F, 2.5F);
		GuiComponent.blit(poseStack, 0, 0, 0, 0, 16, 16);
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		poseStack.popPose();
		
		// The Menu Title
		this.font.draw(poseStack, this.colorSelectionText, ((this.width / 2) - (this.font.width(this.colorSelectionText) / 2)) + (this.isColorPickerActive ? 68 : 0), y + 6, 4210752);
		// The preview String
		this.font.draw(poseStack, this.previewColorText, x + 49, y + 18, 0x000000);
		// Renders the Buttons we added in init
		super.render(poseStack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers)
	{
		super.keyPressed(keyCode, scanCode, modifiers);
		InputConstants.Key mouseKey = InputConstants.getKey(keyCode, scanCode);
		if(this.minecraft.options.keyInventory.isActiveAndMatches(mouseKey))
			this.onClose();
		return true;
	}

	@Override
	public TTCColorPicker getColorPicker()
	{
		return this.colorPicker;
	}

	@Override
	public ForgeSlider getRedSlider()
	{
		return this.redColorSlider;
	}

	@Override
	public ForgeSlider getGreenSlider()
	{
		return this.greenColorSlider;
	}

	@Override
	public ForgeSlider getBlueSlider()
	{
		return this.blueColorSlider;
	}

	@Override
	public ForgeSlider getSaturationSlider()
	{
		return this.saturationSlider;
	}

	@Override
	public boolean isColorPickerActive()
	{
		return this.isColorPickerActive;
	}
}