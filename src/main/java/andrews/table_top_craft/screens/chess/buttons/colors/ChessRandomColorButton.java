package andrews.table_top_craft.screens.chess.buttons.colors;

import andrews.table_top_craft.screens.base.buttons.BaseTextButtonSmall;
import andrews.table_top_craft.screens.piece_figure.util.IColorPicker;
import andrews.table_top_craft.screens.piece_figure.util.IColorPickerExtended;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.Random;

public class ChessRandomColorButton extends BaseTextButtonSmall
{
	private static final Component TEXT = Component.translatable("gui.table_top_craft.chess.color.random_color");
	private static final Component TEXT_PLURAL = Component.translatable("gui.table_top_craft.chess.color.random_colors");
	private final Screen screen;

	public ChessRandomColorButton(Screen screen, int pX, int pY)
	{
		super(pX, pY, (screen instanceof IColorPickerExtended) ? TEXT_PLURAL : TEXT);
		this.screen = screen;
	}

	@Override
	public void onPress()
	{
		Random rand = new Random();
		try
		{
			if (this.screen instanceof IColorPicker colorPicker && this.screen instanceof IColorPickerExtended colorPickerExtended)
			{
				colorPicker.getRedSlider().setValue(rand.nextInt(256));
				colorPicker.getGreenSlider().setValue(rand.nextInt(256));
				colorPicker.getBlueSlider().setValue(rand.nextInt(256));
				colorPickerExtended.getOptionalRedSlider().setValue(rand.nextInt(256));
				colorPickerExtended.getOptionalGreenSlider().setValue(rand.nextInt(256));
				colorPickerExtended.getOptionalBlueSlider().setValue(rand.nextInt(256));

				if (colorPicker.isColorPickerActive() || colorPickerExtended.isOptionalColorPickerActive())
					colorPicker.getColorPicker().updateColorPickerFromSliders();
			}
			else if(this.screen instanceof IColorPicker colorPicker)
			{
				colorPicker.getRedSlider().setValue(rand.nextInt(256));
				colorPicker.getGreenSlider().setValue(rand.nextInt(256));
				colorPicker.getBlueSlider().setValue(rand.nextInt(256));

				if (colorPicker.isColorPickerActive())
					colorPicker.getColorPicker().updateColorPickerFromSliders();
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			System.out.println("Wasn't able to randomize the color slider values!");
		}
	}
}