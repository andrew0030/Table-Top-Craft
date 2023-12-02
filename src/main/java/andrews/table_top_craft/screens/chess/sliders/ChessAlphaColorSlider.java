package andrews.table_top_craft.screens.chess.sliders;

import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ForgeSlider;

public class ChessAlphaColorSlider extends ForgeSlider
{
	private final static Component ALPHA_TXT = Component.translatable("gui.table_top_craft.chess.sliders.alpha");

	public ChessAlphaColorSlider(int xPos, int yPos, int width, int height, int currentValue)
	{
        super(xPos, yPos, width, height, ALPHA_TXT, Component.literal(""), 1, 255, currentValue, true);
	}

	// We override with an empty method to disable the sound
	@Override
	public void onRelease(double pMouseX, double pMouseY) {}
}