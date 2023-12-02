package andrews.table_top_craft.screens.chess.buttons.settings;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.screens.base.buttons.BaseTextButtonSmall;
import andrews.table_top_craft.screens.chess.menus.ChessBoardEvaluatorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class ChessEvaluateBoardButton extends BaseTextButtonSmall
{
	private static final Component TEXT = Component.translatable("gui.table_top_craft.chess.evaluate");
	private final ChessBlockEntity blockEntity;
	
	public ChessEvaluateBoardButton(ChessBlockEntity blockEntity, int pX, int pY)
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
			Minecraft.getInstance().setScreen(new ChessBoardEvaluatorScreen(this.blockEntity));
	}
}