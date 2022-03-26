package andrews.table_top_craft.screens.chess.sliders;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.client.gui.GuiUtils;
import net.minecraftforge.client.gui.widget.Slider;

public class ChessAlphaColorSlider extends Slider
{
	private final static TranslatableComponent alphaValueText = new TranslatableComponent("gui.table_top_craft.chess.sliders.alpha");

	public ChessAlphaColorSlider(int xPos, int yPos, int width, int height, int currentValue)
	{
		super(xPos, yPos, width, height, alphaValueText, new TextComponent(""), 26, 255, currentValue, false, true, slider -> {});
	}
	
	@Override
    protected void renderBg(PoseStack poseStack, Minecraft minecraft, int mouseX, int mouseY)
    {
        if(this.visible)
        {
            if(this.dragging)
            {
                this.sliderValue = (mouseX - (this.x + 4)) / (float)(this.width - 8);
                updateSlider();
            }
            if(this.isHovered || this.isFocused())
            {
                // TODO maybe replace with blit
            	GuiUtils.drawContinuousTexturedBox(poseStack, WIDGETS_LOCATION, this.x + (int)(this.sliderValue * (float)(this.width - 8)), this.y, 0, 86, 8, this.height, 200, 20, 2, 3, 2, 2, this.getBlitOffset());
            }
            else
            {
                // TODO maybe replace with blit
            	GuiUtils.drawContinuousTexturedBox(poseStack, WIDGETS_LOCATION, this.x + (int)(this.sliderValue * (float)(this.width - 8)), this.y, 0, 66, 8, this.height, 200, 20, 2, 3, 2, 2, this.getBlitOffset());
            }
        }
    }
}