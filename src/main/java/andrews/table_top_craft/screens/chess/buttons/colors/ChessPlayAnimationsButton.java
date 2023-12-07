package andrews.table_top_craft.screens.chess.buttons.colors;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.screens.base.buttons.BaseToggleButton;
import andrews.table_top_craft.util.NetworkUtil;

public class ChessPlayAnimationsButton extends BaseToggleButton
{
    private final ChessBlockEntity blockEntity;

    public ChessPlayAnimationsButton(ChessBlockEntity blockEntity, int pX, int pY)
    {
        super(pX, pY);
        this.blockEntity = blockEntity;
    }

    @Override
    public boolean isToggled()
    {
        return this.blockEntity.getPlayPieceAnimations();
    }

    @Override
    public void onPress()
    {
        NetworkUtil.toggleChessVisuals(this.blockEntity.getBlockPos(), (byte) 0);
    }
}