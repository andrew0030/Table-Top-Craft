package andrews.table_top_craft.screens.chess.sliders;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;
import net.minecraftforge.fml.client.gui.widget.Slider;

public class ChessBlueColorSlider extends Slider
{
	private final static TranslationTextComponent blueValueText = new TranslationTextComponent("gui.table_top_craft.chess.sliders.blue");

	public ChessBlueColorSlider(int xPos, int yPos, int width, int height, int currentValue)
	{
		super(xPos, yPos, width, height, blueValueText, new StringTextComponent(""), 1, 255, currentValue, false, true, slider -> {});
	}
	
	@Override
    protected void renderBg(MatrixStack mStack, Minecraft par1Minecraft, int par2, int par3)
    {
        if(this.visible)
        {
            if(this.dragging)
            {
                this.sliderValue = (par2 - (this.x + 4)) / (float)(this.width - 8);
                updateSlider();
            }
            if(this.isHovered || this.isFocused())
            {
            	GuiUtils.drawContinuousTexturedBox(mStack, WIDGETS_LOCATION, this.x + (int)(this.sliderValue * (float)(this.width - 8)), this.y, 0, 86, 8, this.height, 200, 20, 2, 3, 2, 2, this.getBlitOffset());
            }
            else
            {
            	GuiUtils.drawContinuousTexturedBox(mStack, WIDGETS_LOCATION, this.x + (int)(this.sliderValue * (float)(this.width - 8)), this.y, 0, 66, 8, this.height, 200, 20, 2, 3, 2, 2, this.getBlitOffset());
            }
        }
    }
}