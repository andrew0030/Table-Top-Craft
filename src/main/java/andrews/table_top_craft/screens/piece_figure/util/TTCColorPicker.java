package andrews.table_top_craft.screens.piece_figure.util;

import andrews.table_top_craft.util.Color;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.client.gui.ScreenUtils;
import org.lwjgl.glfw.GLFW;

public class TTCColorPicker extends AbstractSliderButton
{
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
    public void renderWidget(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick)
    {
        pPoseStack.pushPose();
        pPoseStack.translate(x, y, 0);
        pPoseStack.scale(0.5F, 0.5F, 0.5F);
        // RGB chart
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, COLOR_CHART);
        GuiComponent.blit(pPoseStack, 0, 0, 0, 0, 256, 256);
        // Saturation chart
        RenderSystem.enableBlend();
        if (this.screen instanceof IColorPicker colorPicker)
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1F - (colorPicker.getSaturationSlider().getValueInt() / 100F));
        RenderSystem.setShaderTexture(0, SATURATION_CHART);
        GuiComponent.blit(pPoseStack, 0, 0, 0, 0, 256, 256);
        RenderSystem.disableBlend();
        pPoseStack.popPose();

        Minecraft mc = Minecraft.getInstance();
        this.renderBg(pPoseStack, mc, pMouseX, pMouseY);
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

    protected void renderBg(PoseStack poseStack, Minecraft minecraft, int mouseX, int mouseY)
    {
        RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int offset = (this.isHoveredOrFocused() ? 2 : 1) * 20;

        int begTexOffset = 1;
        int destTexOffset = 2;

        int xPosition = this.x + (int)(this.value * (float)(this.width - destTexOffset)) - begTexOffset;
        int yPosition = this.y + (int)(this.valueY * (float)(this.height - destTexOffset)) - begTexOffset;

        int size = 4;

        ScreenUtils.blitWithBorder(poseStack, WIDGETS_LOCATION, xPosition, yPosition, 0, 46 + offset, size, size, 200, 20, 1, 1, 1, 1, 0);
    }

    @Override
    protected void onDrag(double mouseX, double mouseY, double dragX, double dragY)
    {
        super.onDrag(mouseX, mouseY, dragX, dragY);
        this.setValueFromMouse(mouseX, mouseY);
    }

    private void setValueFromMouse(double mouseX, double mouseY)
    {
        this.setSliderValue((mouseX - (this.x + 1)) / (this.width - 2),
                            (mouseY - (this.y + 1)) / (this.height - 2));
    }

    @Override
    public void onClick(double mouseX, double mouseY)
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
