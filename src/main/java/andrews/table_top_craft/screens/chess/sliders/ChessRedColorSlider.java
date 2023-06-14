package andrews.table_top_craft.screens.chess.sliders;

import andrews.table_top_craft.screens.piece_figure.util.IColorPicker;
import andrews.table_top_craft.screens.piece_figure.util.IColorPickerExtended;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.ScreenUtils;
import net.minecraftforge.client.gui.widget.ForgeSlider;

public class ChessRedColorSlider extends ForgeSlider
{
	private final static Component redValueText = Component.translatable("gui.table_top_craft.chess.sliders.red");
	private final Screen menuIn;

	public ChessRedColorSlider(int xPos, int yPos, int width, int height, int currentValue, Screen menuIn)
	{
		super(xPos, yPos, width, height, redValueText, Component.literal(""), 0, 255, currentValue, true);
		this.menuIn = menuIn;
	}

	@Override
	public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
	{
		Minecraft mc = Minecraft.getInstance();
		ScreenUtils.blitWithBorder(poseStack, WIDGETS_LOCATION, this.x, this.y, 0, 46, this.width, this.height, 200, 20, 2, 3, 2, 2, 0);
		this.renderBg(poseStack, mc, mouseX, mouseY);

		Component buttonText = this.getMessage();
		int strWidth = mc.font.width(buttonText);
		int ellipsisWidth = mc.font.width("...");

		if (strWidth > width - 6 && strWidth > ellipsisWidth)
			buttonText = Component.literal(mc.font.substrByWidth(buttonText, width - 6 - ellipsisWidth).getString() + "...");

		drawCenteredString(poseStack, mc.font, buttonText, this.x + this.width / 2, this.y + (this.height - 8) / 2, getFGColor());
	}

	protected void renderBg(PoseStack poseStack, Minecraft minecraft, int mouseX, int mouseY)
	{
		RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		int offset = (this.isHoveredOrFocused() ? 2 : 1) * 20;
		ScreenUtils.blitWithBorder(poseStack, WIDGETS_LOCATION, this.x + (int)(this.value * (float)(this.width - 8)), this.y, 0, 46 + offset, 8, this.height, 200, 20, 2, 3, 2, 2, 0);
	}

	@Override
	protected void onDrag(double mouseX, double mouseY, double dragX, double dragY)
	{
		super.onDrag(mouseX, mouseY, dragX, dragY);
		if(this.menuIn != null && menuIn instanceof IColorPicker colorPicker && menuIn instanceof IColorPickerExtended colorPickerExtended)
		{
			if(colorPicker.isColorPickerActive() || colorPickerExtended.isOptionalColorPickerActive())
				colorPicker.getColorPicker().updateColorPickerFromSliders();
		}
		else if(this.menuIn != null && menuIn instanceof IColorPicker colorPicker)
		{
			if(colorPicker.isColorPickerActive())
				colorPicker.getColorPicker().updateColorPickerFromSliders();
		}
	}

	@Override
	public void onClick(double mouseX, double mouseY)
	{
		super.onClick(mouseX, mouseY);
		if(this.menuIn != null && menuIn instanceof IColorPicker colorPicker && menuIn instanceof IColorPickerExtended colorPickerExtended)
		{
			if(colorPicker.isColorPickerActive() || colorPickerExtended.isOptionalColorPickerActive())
				colorPicker.getColorPicker().updateColorPickerFromSliders();
		}
		else if(this.menuIn != null && menuIn instanceof IColorPicker colorPicker)
		{
			if(colorPicker.isColorPickerActive())
				colorPicker.getColorPicker().updateColorPickerFromSliders();
		}
	}
}