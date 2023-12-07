package andrews.table_top_craft.screens.piece_figure.buttons;

import andrews.table_top_craft.screens.base.buttons.BaseTextButtonSmall;
import andrews.table_top_craft.screens.piece_figure.menus.ChessPieceFigureSettingsScreen;
import net.minecraft.network.chat.Component;

public class ChessPieceFigureResetColorButton extends BaseTextButtonSmall
{
    private static final Component TEXT = Component.translatable("gui.table_top_craft.chess.color.reset_color");
    private final ChessPieceFigureSettingsScreen screen;

    public ChessPieceFigureResetColorButton(ChessPieceFigureSettingsScreen chessPieceFigureSettingsScreen, int pX, int pY)
    {
        super(pX, pY, TEXT);
        this.screen = chessPieceFigureSettingsScreen;
    }

    @Override
    public void onPress()
    {
        this.screen.getRedSlider().setValue(210F);
        this.screen.getGreenSlider().setValue(188F);
        this.screen.getBlueSlider().setValue(161F);
        if(this.screen.isColorPickerActive())
            this.screen.getColorPicker().updateColorPickerFromSliders();
    }
}