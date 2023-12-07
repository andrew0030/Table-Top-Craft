package andrews.table_top_craft.screens.chess.sliders;

import andrews.table_top_craft.screens.base.BaseSlider;
import andrews.table_top_craft.screens.piece_figure.util.IColorPicker;
import andrews.table_top_craft.screens.piece_figure.util.IColorPickerExtended;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ChessRedColorSlider extends BaseSlider
{
	private final static Component RED_TXT = Component.translatable("gui.table_top_craft.chess.sliders.red");
	private final Screen screen;

	public ChessRedColorSlider(int pX, int pY, int width, int height, int currentValue, Screen screen)
	{
		super(pX, pY, width, height, RED_TXT, Component.literal(""), 0, 255, currentValue, true);
		this.screen = screen;
	}

	// We override with an empty method to disable the sound
	@Override
	public void onRelease(double pMouseX, double pMouseY) {}

	@Override
	protected void onDrag(double mouseX, double mouseY, double dragX, double dragY)
	{
		super.onDrag(mouseX, mouseY, dragX, dragY);
		if(this.screen != null && this.screen instanceof IColorPicker colorPicker && this.screen instanceof IColorPickerExtended colorPickerExtended)
		{
			if(colorPicker.isColorPickerActive() || colorPickerExtended.isOptionalColorPickerActive())
				colorPicker.getColorPicker().updateColorPickerFromSliders();
		}
		else if(this.screen != null && this.screen instanceof IColorPicker colorPicker)
		{
			if(colorPicker.isColorPickerActive())
				colorPicker.getColorPicker().updateColorPickerFromSliders();
		}
	}

	@Override
	public void onClick(double mouseX, double mouseY)
	{
		super.onClick(mouseX, mouseY);
		if(this.screen != null && this.screen instanceof IColorPicker colorPicker && this.screen instanceof IColorPickerExtended colorPickerExtended)
		{
			if(colorPicker.isColorPickerActive() || colorPickerExtended.isOptionalColorPickerActive())
				colorPicker.getColorPicker().updateColorPickerFromSliders();
		}
		else if(this.screen != null && this.screen instanceof IColorPicker colorPicker)
		{
			if(colorPicker.isColorPickerActive())
				colorPicker.getColorPicker().updateColorPickerFromSliders();
		}
	}
}