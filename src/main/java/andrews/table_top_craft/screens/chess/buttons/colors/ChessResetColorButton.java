package andrews.table_top_craft.screens.chess.buttons.colors;

import andrews.table_top_craft.screens.base.buttons.BaseTextButtonSmall;
import andrews.table_top_craft.screens.piece_figure.util.IColorPicker;
import andrews.table_top_craft.screens.piece_figure.util.IColorPickerExtended;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ChessResetColorButton extends BaseTextButtonSmall
{
	private static final Component TEXT = Component.translatable("gui.table_top_craft.chess.color.reset_color");
	private static final Component TEXT_PLURAL = Component.translatable("gui.table_top_craft.chess.color.reset_colors");
	private final DefaultColorType colorType;
	private final Screen screen;

	public ChessResetColorButton(DefaultColorType colorType, Screen screen, int pX, int pY)
	{
		super(pX, pY, (screen instanceof IColorPickerExtended) ? TEXT_PLURAL : TEXT);
		this.colorType = colorType;
		this.screen = screen;
	}

	@Override
	public void onPress()
	{
		if (screen instanceof IColorPicker colorPicker)
		{
			switch (colorType)
			{
				default:
				case TILE_INFO_COLOR:
					colorPicker.getRedSlider().setValue(255F);
					colorPicker.getGreenSlider().setValue(255F);
					colorPicker.getBlueSlider().setValue(255F);
					break;
				case BOARD_TILES:
					if (screen instanceof IColorPickerExtended colorPickerExtended)
					{
						colorPicker.getRedSlider().setValue(208F);
						colorPicker.getGreenSlider().setValue(177F);
						colorPicker.getBlueSlider().setValue(141F);
						colorPickerExtended.getOptionalRedSlider().setValue(139F);
						colorPickerExtended.getOptionalGreenSlider().setValue(86F);
						colorPickerExtended.getOptionalBlueSlider().setValue(57F);
						// Technically this isnt the ideal way to do it because it gets updated twice when the
						// none optional Color Picker is Active, but it will do the trick.
						if(colorPickerExtended.isOptionalColorPickerActive())
							colorPicker.getColorPicker().updateColorPickerFromSliders();
					}
					break;
				case PIECES:
					if (screen instanceof IColorPickerExtended colorPickerExtended)
					{
						colorPicker.getRedSlider().setValue(210F);
						colorPicker.getGreenSlider().setValue(188F);
						colorPicker.getBlueSlider().setValue(161F);
						colorPickerExtended.getOptionalRedSlider().setValue(51F);
						colorPickerExtended.getOptionalGreenSlider().setValue(51F);
						colorPickerExtended.getOptionalBlueSlider().setValue(51F);
						// Technically this isnt the ideal way to do it because it gets updated twice when the
						// none optional Color Picker is Active, but it will do the trick.
						if(colorPickerExtended.isOptionalColorPickerActive())
							colorPicker.getColorPicker().updateColorPickerFromSliders();
					}
					break;
				case LEGAL_MOVE:
					colorPicker.getRedSlider().setValue(1F);
					colorPicker.getGreenSlider().setValue(255F);
					colorPicker.getBlueSlider().setValue(1F);
					break;
				case INVALID_MOVE:
					colorPicker.getRedSlider().setValue(255F);
					colorPicker.getGreenSlider().setValue(255F);
					colorPicker.getBlueSlider().setValue(1F);
					break;
				case ATTACK_MOVE:
					colorPicker.getRedSlider().setValue(255F);
					colorPicker.getGreenSlider().setValue(1F);
					colorPicker.getBlueSlider().setValue(1F);
					break;
				case PREVIOUS_MOVE:
					colorPicker.getRedSlider().setValue(1F);
					colorPicker.getGreenSlider().setValue(150F);
					colorPicker.getBlueSlider().setValue(125F);
					break;
				case CASTLE_MOVE:
					colorPicker.getRedSlider().setValue(125F);
					colorPicker.getGreenSlider().setValue(1F);
					colorPicker.getBlueSlider().setValue(255F);
			}
			if(colorPicker.isColorPickerActive())
				colorPicker.getColorPicker().updateColorPickerFromSliders();
		}
	}
	
	public enum DefaultColorType
	{
		TILE_INFO_COLOR,
		BOARD_TILES,
		PIECES,
		LEGAL_MOVE,
		INVALID_MOVE,
		ATTACK_MOVE,
		PREVIOUS_MOVE,
		CASTLE_MOVE;
	}
}