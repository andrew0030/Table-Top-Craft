package andrews.table_top_craft.screens.piece_figure.sliders;

import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ForgeSlider;

public class ChessPieceFigureScaleSlider extends ForgeSlider
{
    private final static Component SCALE_TXT = Component.translatable("gui.table_top_craft.chess_piece_figure.sliders.scale");

    public ChessPieceFigureScaleSlider(int pX, int pY, int width, int height, double currentValue)
    {
        super(pX, pY, width, height, SCALE_TXT, Component.literal(""), 1, 5, currentValue, 0.01D, 0, true);
    }

    // We override with an empty method to disable the sound
    @Override
    public void onRelease(double pMouseX, double pMouseY) {}

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        return false;
    }
}