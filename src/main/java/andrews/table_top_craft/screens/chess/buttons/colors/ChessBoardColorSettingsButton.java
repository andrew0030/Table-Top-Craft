package andrews.table_top_craft.screens.chess.buttons.colors;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.screens.base.buttons.BaseChessPageButton;
import andrews.table_top_craft.screens.chess.menus.ChessColorSettingsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class ChessBoardColorSettingsButton extends BaseChessPageButton
{
	private static final Component TEXT = Component.translatable("gui.table_top_craft.chess.button.colors");
	private final ChessBlockEntity blockEntity;
	
	public ChessBoardColorSettingsButton(ChessBlockEntity blockEntity, int pX, int pY)
	{
		super(pX, pY, 24, 26, TEXT);
		this.blockEntity = blockEntity;
		this.active = !(Minecraft.getInstance().screen instanceof ChessColorSettingsScreen);
	}

	@Override
	public void onPress()
	{
		Minecraft.getInstance().setScreen(new ChessColorSettingsScreen(this.blockEntity));
	}
}