package andrews.table_top_craft.screens.piece_figure.util;

import net.minecraftforge.client.gui.widget.ForgeSlider;

public interface IColorPicker
{
    TTCColorPicker getColorPicker();
    ForgeSlider getRedSlider();
    ForgeSlider getGreenSlider();
    ForgeSlider getBlueSlider();
    ForgeSlider getSaturationSlider();
    boolean isColorPickerActive();
}