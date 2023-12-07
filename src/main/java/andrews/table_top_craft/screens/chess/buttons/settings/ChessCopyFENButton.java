package andrews.table_top_craft.screens.chess.buttons.settings;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.game_logic.chess.pgn.FenUtil;
import andrews.table_top_craft.screens.base.buttons.BaseTextButtonSmall;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class ChessCopyFENButton extends BaseTextButtonSmall
{
	private static final Component TEXT = Component.translatable("gui.table_top_craft.chess.copy_fen");
	private final ChessBlockEntity blockEntity;
	
	public ChessCopyFENButton(ChessBlockEntity blockEntity, int pX, int pY)
	{
		super(pX, pY, TEXT);
		this.blockEntity = blockEntity;
	}

	@Override
	public boolean setActive()
	{
		return this.blockEntity.getBoard() != null;
	}

	@Override
	public void onPress()
	{
		if(this.blockEntity.getBoard() != null)
		{
			String FENString = FenUtil.createFENFromGame(this.blockEntity.getBoard());
			Minecraft.getInstance().keyboardHandler.setClipboard(FENString);
		}
	}
}