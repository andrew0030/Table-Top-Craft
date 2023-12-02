package andrews.table_top_craft.screens.chess_timer.buttons;

import andrews.table_top_craft.block_entities.ChessTimerBlockEntity;
import andrews.table_top_craft.objects.blocks.ChessTimerBlock;
import andrews.table_top_craft.screens.base.buttons.BaseChessTimerTextButton;
import andrews.table_top_craft.util.NetworkUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

public class ChessTimerResetButton extends BaseChessTimerTextButton
{
    private static final Component TEXT = Component.translatable("gui.table_top_craft.chess_timer.buttons.reset");
    private final ChessTimerBlockEntity blockEntity;

    public ChessTimerResetButton(ChessTimerBlockEntity blockEntity, int pX, int pY)
    {
        super(pX, pY, TEXT);
        this.blockEntity = blockEntity;
    }

    @Override
    public boolean setActive()
    {
        if(this.blockEntity.getLeftTimer() == 1800000 && this.blockEntity.getRightTimer() == 1800000)
            return false;
        if(Minecraft.getInstance().level != null)
        {
            BlockState state = Minecraft.getInstance().level.getBlockState(this.blockEntity.getBlockPos());
            return state.getValue(ChessTimerBlock.PRESSED_BUTTON).equals(ChessTimerBlock.PressedButton.NONE);
        }
        return false;
    }

    @Override
    public void onPress()
    {
        NetworkUtil.resetChessTimerTime(this.blockEntity.getBlockPos());
    }
}