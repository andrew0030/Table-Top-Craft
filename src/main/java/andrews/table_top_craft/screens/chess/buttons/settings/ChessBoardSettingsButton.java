package andrews.table_top_craft.screens.chess.buttons.settings;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.screens.base.buttons.BaseChessPageButton;
import andrews.table_top_craft.screens.chess.menus.ChessBoardSettingsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class ChessBoardSettingsButton extends BaseChessPageButton
{
	private static final Component TEXT = Component.translatable("gui.table_top_craft.chess.button.settings");
	private final ChessBlockEntity blockEntity;
	
	public ChessBoardSettingsButton(ChessBlockEntity blockEntity, int pX, int pY)
	{
		super(pX, pY, 0, 26, TEXT);
		this.blockEntity = blockEntity;
		this.active = !(Minecraft.getInstance().screen instanceof ChessBoardSettingsScreen);
	}

	@Override
	public void onPress()
	{
		Minecraft.getInstance().setScreen(new ChessBoardSettingsScreen(this.blockEntity));
	}
}