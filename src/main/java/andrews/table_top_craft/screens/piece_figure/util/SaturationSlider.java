package andrews.table_top_craft.screens.piece_figure.util;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ForgeSlider;

public class SaturationSlider extends ForgeSlider
{
    private final static Component SATURATION_TXT = Component.translatable("gui.table_top_craft.sliders.saturation");
    private Screen screen;

    public SaturationSlider(int pX, int pY, int width, int height, int currentValue)
    {
        super(pX, pY, width, height, SATURATION_TXT, Component.literal(""), 0, 100, currentValue, true);
    }

    public SaturationSlider(int pX, int pY, int width, int height, int currentValue, Screen screen)
    {
        this(pX, pY, width, height, currentValue);
        this.screen = screen;
    }

    // We override with an empty method to disable the sound
    @Override
    public void onRelease(double pMouseX, double pMouseY) {}

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        super.keyPressed(keyCode, scanCode, modifiers);
        if(this.screen != null && this.screen instanceof IColorPicker colorPicker)
        {
            if(colorPicker.isColorPickerActive())
                colorPicker.getColorPicker().updateRGBHSVSliders();
        }
        return true;
    }

    @Override
    protected void onDrag(double mouseX, double mouseY, double dragX, double dragY)
    {
        super.onDrag(mouseX, mouseY, dragX, dragY);
        if(this.screen != null && this.screen instanceof IColorPicker colorPicker && this.screen instanceof IColorPickerExtended colorPickerExtended)
        {
            if(colorPicker.isColorPickerActive() || colorPickerExtended.isOptionalColorPickerActive())
                colorPicker.getColorPicker().updateRGBHSVSliders();
        }
        else if(this.screen != null && this.screen instanceof IColorPicker colorPicker)
        {
            if(colorPicker.isColorPickerActive())
                colorPicker.getColorPicker().updateRGBHSVSliders();
        }
    }

    @Override
    public void onClick(double mouseX, double mouseY)
    {
        super.onClick(mouseX, mouseY);
        if(this.screen != null && this.screen instanceof IColorPicker colorPicker && this.screen instanceof IColorPickerExtended colorPickerExtended)
        {
            if(colorPicker.isColorPickerActive() || colorPickerExtended.isOptionalColorPickerActive())
                colorPicker.getColorPicker().updateRGBHSVSliders();
        }
        else if(this.screen != null && this.screen instanceof IColorPicker colorPicker)
        {
            if(colorPicker.isColorPickerActive())
                colorPicker.getColorPicker().updateRGBHSVSliders();
        }
    }
}