package andrews.table_top_craft.screens.piece_figure.util;

import andrews.table_top_craft.util.Color;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.lwjgl.glfw.GLFW;

public class TTCColorPicker extends AbstractSliderButton
{
    private static final ResourceLocation SLIDER_LOCATION = new ResourceLocation("textures/gui/slider.png");
    private static final ResourceLocation COLOR_CHART = new ResourceLocation(Reference.MODID + ":textures/gui/color_picker/color_chart.png");
    private static final ResourceLocation SATURATION_CHART = new ResourceLocation(Reference.MODID + ":textures/gui/color_picker/saturation_chart.png");
    private final Screen screen;
    private final Color color;
    private double valueY;

    public TTCColorPicker(int xPos, int yPos, Screen screen, double valueX, double valueY)
    {
        // The RGB color range is 0-255 so a total of 256 values. Thus, the texture is the perfect size for
        // minecraft's texture file. The issue is that a 256x25p Pixel texture is HUGE in any menu, so to fix
        // that we pass a size of 128x128 (half of 256) and simply use the PoseStack inside render() to mul.
        // the size by 0.5, effectively turning a 256x256 texture into a 128x128 one.
        super(xPos, yPos, 128, 128, Component.literal(""), valueX);
        this.screen = screen;
        this.valueY = valueY;
        this.color = new Color(0, 0, 0);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        boolean flagX = keyCode == GLFW.GLFW_KEY_LEFT;
        boolean flagY = keyCode == GLFW.GLFW_KEY_UP;
        if (flagX || keyCode == GLFW.GLFW_KEY_RIGHT)
        {
            float f = flagX ? -1F : 1F;
            this.value = this.snapToNearest(this.value + f * 0.0027777777777778D);
            this.updateMessage();
            this.updateRGBHSVSliders();
        }
        if (flagY || keyCode == GLFW.GLFW_KEY_DOWN)
        {
            float f = flagY ? -1F : 1F;
            this.valueY = this.snapToNearest(this.valueY + f * 0.0027777777777778D);
            this.updateMessage();
            this.updateRGBHSVSliders();
        }
        return false;
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        graphics.pose().pushPose();
        graphics.pose().translate(this.x, this.y, 0);
        graphics.pose().scale(0.5F, 0.5F, 0.5F);
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        // RGB chart
        graphics.blit(COLOR_CHART, 0, 0, 0, 0, 256, 256);
        // Saturation chart
        RenderSystem.enableBlend();
        if (this.screen instanceof IColorPicker colorPicker)
            graphics.setColor(1.0f, 1.0f, 1.0f, 1F - (colorPicker.getSaturationSlider().getValueInt() / 100F));
        graphics.blit(SATURATION_CHART, 0, 0, 0, 0, 256, 256);
        RenderSystem.disableBlend();
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        graphics.pose().popPose();

        // The Color Indicator in the Picker
        int begTexOffset = 1;
        int destTexOffset = 2;
        int xPosition = this.x + (int) (this.value * (this.width - destTexOffset)) - begTexOffset;
        int yPosition = this.y + (int) (this.valueY * (this.height - destTexOffset)) - begTexOffset;
        int size = 4;
        graphics.blitNineSliced(SLIDER_LOCATION, xPosition, yPosition, size, size, 2, 3, 200, 20, 0, (this.isHoveredOrFocused() ? 60 : 40));
    }

    // We override with an empty method to disable the sound
    @Override
    public void onRelease(double pMouseX, double pMouseY) {}

    @Override
    protected void onDrag(double mouseX, double mouseY, double dragX, double dragY)
    {
        super.onDrag(mouseX, mouseY, dragX, dragY);
        this.setValueFromMouse(mouseX, mouseY);
    }

    @Override
    public void onClick(double mouseX, double mouseY)
    {
        this.setSliderValue((mouseX - (this.x + 1)) / (this.width - 2),
                (mouseY - (this.y + 1)) / (this.height - 2));
    }

    @Override
    protected void updateMessage()
    {
        this.setMessage(Component.literal(""));
    }

    /**
     * Sets the value for the Color Picker on the X Axis
     * @param value The new slider value for the X Axis
     */
    public void setValueX(double value)
    {
        value = value / 360;
        this.value = this.snapToNearest(value);
        this.updateMessage();
    }

    public Color getColor()
    {
        float colorHue = (float) (this.value * 360);
        float colorSaturation = 1.0F;
        if(this.screen instanceof IColorPicker colorPicker)
            colorSaturation = colorPicker.getSaturationSlider().getValueInt() / 100F;
        float colorValue = 1.0F - (float) this.valueY;

        return color.fromHSV(colorHue, colorSaturation, colorValue);
    }

    private void setValueFromMouse(double mouseX, double mouseY)
    {
        this.setSliderValue((mouseX - (this.x + 1)) / (this.width - 2),
                            (mouseY - (this.y + 1)) / (this.height - 2));
    }

    private void setSliderValue(double mouseX, double mouseY)
    {
        double oldValueX = this.value;
        double oldValueY = this.valueY;
        // X Value
        this.value = this.snapToNearest(mouseX);
        if (!Mth.equal(oldValueX, this.value))
            this.applyValue();
        // Y Value
        this.valueY = this.snapToNearest(mouseY);
        if (!Mth.equal(oldValueY, this.valueY))
            this.applyValue();
        // Update the text message, we don't have one but if I decide to add one, and it won't
        // work for some reason I would go nuts, so for my own safety ill leave it here.
        this.updateMessage();
        this.updateRGBHSVSliders();
    }

    public void updateRGBHSVSliders()
    {
        Color color = getColor();
        if(screen instanceof IColorPicker colorPicker && screen instanceof IColorPickerExtended colorPickerExtended)
        {
            if(colorPicker.isColorPickerActive())
            {
                colorPicker.getRedSlider().setValue(color.getRed());
                colorPicker.getGreenSlider().setValue(color.getGreen());
                colorPicker.getBlueSlider().setValue(color.getBlue());
            }
            if(colorPickerExtended.isOptionalColorPickerActive())
            {
                colorPickerExtended.getOptionalRedSlider().setValue(color.getRed());
                colorPickerExtended.getOptionalGreenSlider().setValue(color.getGreen());
                colorPickerExtended.getOptionalBlueSlider().setValue(color.getBlue());
            }
        }
        else if(screen instanceof IColorPicker colorPicker)
        {
            colorPicker.getRedSlider().setValue(color.getRed());
            colorPicker.getGreenSlider().setValue(color.getGreen());
            colorPicker.getBlueSlider().setValue(color.getBlue());
        }
    }

    public void updateColorPickerFromSliders()
    {
        if(screen instanceof IColorPicker colorPicker && screen instanceof IColorPickerExtended colorPickerExtended)
        {
            int red = colorPicker.getRedSlider().getValueInt();
            int green = colorPicker.getGreenSlider().getValueInt();
            int blue = colorPicker.getBlueSlider().getValueInt();
            if(colorPickerExtended.isOptionalColorPickerActive())
            {
                red = colorPickerExtended.getOptionalRedSlider().getValueInt();
                green = colorPickerExtended.getOptionalGreenSlider().getValueInt();
                blue = colorPickerExtended.getOptionalBlueSlider().getValueInt();
            }
            Color color = new Color(red, green, blue);
            setValueX(color.getHue());
            setValueY(1F - color.getValue());
            colorPicker.getSaturationSlider().setValue(color.getSaturation() * 100);
        }
        else if(screen instanceof IColorPicker colorPicker)
        {
            int red = colorPicker.getRedSlider().getValueInt();
            int green = colorPicker.getGreenSlider().getValueInt();
            int blue = colorPicker.getBlueSlider().getValueInt();
            Color color = new Color(red, green, blue);
            setValueX(color.getHue());
            setValueY(1F - color.getValue());
            colorPicker.getSaturationSlider().setValue(color.getSaturation() * 100);
        }
    }

    private double snapToNearest(double value)
    {
        double stepSize = 0.0027777777777778D;
        double minValue = 0.0D;
        double maxValue = 1.0D;

        value = Mth.lerp(Mth.clamp(value, 0D, 1D), minValue, maxValue);
        value = (stepSize * Math.round(value / stepSize));
        value = Mth.clamp(value, minValue, maxValue);

        return Mth.map(value, minValue, maxValue, 0D, 1D);
    }

    /**
     * Sets the value for the Color Picker on the Y Axis
     * @param value The new slider value for the Y Axis
     */
    public void setValueY(double value)
    {
        this.valueY = this.snapToNearest(value);
        this.updateMessage();
    }

    /**
     * Implemented because we have to, it doesn't really do anything as we handle the value from other methods.
     */
    @Override
    protected void applyValue() {}
}