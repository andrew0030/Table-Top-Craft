package andrews.table_top_craft.screens.piece_figure.buttons;

import andrews.table_top_craft.block_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.screens.base.buttons.BaseTextButton;
import andrews.table_top_craft.screens.piece_figure.sliders.ChessPieceFigureScaleSlider;
import andrews.table_top_craft.util.NetworkUtil;
import net.minecraft.network.chat.Component;

public class ChessPieceFigureConfirmScaleButton extends BaseTextButton
{
    private static final Component TEXT = Component.translatable("gui.table_top_craft.chess_piece_figure.confirm_scale");
    private final ChessPieceFigureBlockEntity blockEntity;
    private final ChessPieceFigureScaleSlider scaleSlider;

    public ChessPieceFigureConfirmScaleButton(ChessPieceFigureBlockEntity blockEntity, ChessPieceFigureScaleSlider scaleSlider, int pX, int pY)
    {
        super(pX, pY, TEXT);
        this.blockEntity = blockEntity;
        this.scaleSlider = scaleSlider;
    }

    @Override
    public void onPress()
    {
        NetworkUtil.setPieceScale(this.blockEntity.getBlockPos(), this.scaleSlider.getValue());
    }
}