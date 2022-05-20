package andrews.table_top_craft.screens.piece_figure.util;

import andrews.table_top_craft.screens.util.BaseSlider;

public interface IColorPicker
{
    TTCColorPicker getColorPicker();
    BaseSlider getRedSlider();
    BaseSlider getGreenSlider();
    BaseSlider getBlueSlider();
    BaseSlider getSaturationSlider();
    boolean isColorPickerActive();
}