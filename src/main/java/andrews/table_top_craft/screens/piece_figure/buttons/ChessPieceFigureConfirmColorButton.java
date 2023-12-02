package andrews.table_top_craft.screens.piece_figure.buttons;

import andrews.table_top_craft.block_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.screens.base.buttons.BaseTextButtonSmall;
import andrews.table_top_craft.screens.chess.sliders.ChessBlueColorSlider;
import andrews.table_top_craft.screens.chess.sliders.ChessGreenColorSlider;
import andrews.table_top_craft.screens.chess.sliders.ChessRedColorSlider;
import andrews.table_top_craft.util.NBTColorSaving;
import andrews.table_top_craft.util.NetworkUtil;
import net.minecraft.network.chat.Component;

public class ChessPieceFigureConfirmColorButton extends BaseTextButtonSmall
{
    private static final Component TEXT = Component.translatable("gui.table_top_craft.chess.confirm_color");
    private final ChessPieceFigureBlockEntity blockEntity;
    private final ChessRedColorSlider redSlider;
    private final ChessGreenColorSlider greenSlider;
    private final ChessBlueColorSlider blueSlider;

    public ChessPieceFigureConfirmColorButton(ChessPieceFigureBlockEntity blockEntity, ChessRedColorSlider red, ChessGreenColorSlider green, ChessBlueColorSlider blue, int pX, int pY)
    {
        super(pX, pY, TEXT);
        this.blockEntity = blockEntity;
        this.redSlider = red;
        this.greenSlider = green;
        this.blueSlider = blue;
    }

    @Override
    public void onPress()
    {
        NetworkUtil.setColorMessage(6, this.blockEntity.getBlockPos(), NBTColorSaving.saveColor(this.redSlider.getValueInt(), this.greenSlider.getValueInt(), this.blueSlider.getValueInt()));
    }
}