package andrews.table_top_craft.screens.chess.buttons.settings;

import andrews.table_top_craft.screens.base.buttons.BaseTextButtonSmall;
import andrews.table_top_craft.util.NetworkUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

public class ChessNewGameButton extends BaseTextButtonSmall
{
	private static final Component TEXT = Component.translatable("gui.table_top_craft.chess.new_game");
	private final BlockPos chessTablePosition;
	
	public ChessNewGameButton(BlockPos pos, int pX, int pY)
	{
		super(pX, pY, TEXT);
		this.chessTablePosition = pos;
	}

	@Override
	public void onPress()
	{
		NetworkUtil.newChessGameMessage(this.chessTablePosition);
		Minecraft.getInstance().player.closeContainer();
	}
}