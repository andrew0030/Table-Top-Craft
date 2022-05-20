package andrews.table_top_craft.screens.piece_figure.util;

import andrews.table_top_craft.screens.util.BaseSlider;

public interface IColorPickerExtended
{
    BaseSlider getOptionalRedSlider();
    BaseSlider getOptionalGreenSlider();
    BaseSlider getOptionalBlueSlider();
    boolean isOptionalColorPickerActive();
}
