package andrews.table_top_craft.screens.piece_figure.util;

import net.minecraftforge.client.gui.widget.ForgeSlider;

public interface IColorPickerExtended
{
    ForgeSlider getOptionalRedSlider();
    ForgeSlider getOptionalGreenSlider();
    ForgeSlider getOptionalBlueSlider();
    boolean isOptionalColorPickerActive();
}
