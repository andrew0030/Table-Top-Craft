package andrews.table_top_craft.screens.piece_figure.buttons;

import andrews.table_top_craft.block_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.screens.base.buttons.BaseToggleButton;
import andrews.table_top_craft.util.NetworkUtil;

public class ChessPieceFigureRotateButton extends BaseToggleButton
{
    private final ChessPieceFigureBlockEntity blockEntity;

    public ChessPieceFigureRotateButton(ChessPieceFigureBlockEntity blockEntity, int pX, int pY)
    {
        super(pX, pY);
        this.blockEntity = blockEntity;
    }

    @Override
    public boolean isToggled()
    {
        return this.blockEntity.getRotateChessPieceFigure();
    }

    @Override
    public void onPress()
    {
        NetworkUtil.rotateChessPieceFigure(this.blockEntity.getBlockPos());
    }
}