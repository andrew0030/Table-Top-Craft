package andrews.table_top_craft.screens.piece_figure.buttons.creative_mode;

import andrews.table_top_craft.block_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.screens.base.buttons.BaseChessPieceFigureCreativeButton;
import andrews.table_top_craft.util.NetworkUtil;
import net.minecraft.network.chat.Component;

public class ChessPieceFigurePreviousSetButton extends BaseChessPieceFigureCreativeButton
{
    private static final Component TEXT = Component.translatable("gui.table_top_craft.chess_piece_figure.previous_set");
    private final ChessPieceFigureBlockEntity blockEntity;

    public ChessPieceFigurePreviousSetButton(ChessPieceFigureBlockEntity blockEntity, int pX, int pY)
    {
        super(pX, pY, 0, 135, TEXT);
        this.blockEntity = blockEntity;
    }

    @Override
    public void onPress()
    {
        NetworkUtil.changePieceSet(this.blockEntity.getBlockPos(), (byte) -1);
    }
}