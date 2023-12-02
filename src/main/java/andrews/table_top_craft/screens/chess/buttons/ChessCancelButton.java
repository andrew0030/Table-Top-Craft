package andrews.table_top_craft.screens.chess.buttons;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.screens.base.buttons.BaseTextButtonSmall;
import andrews.table_top_craft.screens.chess.menus.ChessBoardSettingsScreen;
import andrews.table_top_craft.screens.chess.menus.ChessColorSettingsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class ChessCancelButton extends BaseTextButtonSmall
{
	private static final Component CANCEL_TXT = Component.translatable("gui.table_top_craft.chess.cancel");
	private static final Component BACK_TXT = Component.translatable("gui.table_top_craft.chess.back");
	private final ChessBlockEntity blockEntity;
	private final ChessCancelMenuTarget targetScreen;
	
	public ChessCancelButton(ChessBlockEntity blockEntity, ChessCancelMenuTarget target, ChessCancelButtonText targetText, int pX, int pY)
	{
		super(pX, pY, (targetText.equals(ChessCancelButtonText.CANCEL)) ? CANCEL_TXT : BACK_TXT);
		this.blockEntity = blockEntity;
		this.targetScreen = target;
	}

	@Override
	public void onPress()
	{
		switch (this.targetScreen)
		{
			case CHESS_BOARD_SETTINGS -> Minecraft.getInstance().setScreen(new ChessBoardSettingsScreen(this.blockEntity));
			case CHESS_BOARD_COLORS -> Minecraft.getInstance().setScreen(new ChessColorSettingsScreen(this.blockEntity));
		}
	}
	
	public enum ChessCancelMenuTarget
	{
		CHESS_BOARD_SETTINGS,
		CHESS_BOARD_COLORS;
	}
	
	public enum ChessCancelButtonText
	{
		CANCEL,
		BACK;
	}
}