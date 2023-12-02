package andrews.table_top_craft.screens.chess.buttons.colors;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.screens.base.buttons.BaseTextButton;
import andrews.table_top_craft.screens.chess.menus.color_selection.ChessBoardInvalidMoveColorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class ChessBoardInvalidMoveColorButton extends BaseTextButton
{
	private static final Component TEXT = Component.translatable("gui.table_top_craft.chess.color.button.invalid_move");
	private final ChessBlockEntity blockEntity;
	
	public ChessBoardInvalidMoveColorButton(ChessBlockEntity blockEntity, int pX, int pY)
	{
		super(pX, pY, TEXT);
		this.blockEntity = blockEntity;
	}

	@Override
	public void onPress()
	{
		Minecraft.getInstance().setScreen(new ChessBoardInvalidMoveColorScreen(this.blockEntity, false));
	}
}