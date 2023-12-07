package andrews.table_top_craft.screens.chess.buttons.settings;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.screens.base.buttons.BaseTextButtonSmall;
import andrews.table_top_craft.screens.chess.menus.ChessLoadFENScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class ChessLoadFENButton extends BaseTextButtonSmall
{
	private static final Component TEXT = Component.translatable("gui.table_top_craft.chess.load_fen");
	private final ChessBlockEntity blockEntity;
	
	public ChessLoadFENButton(ChessBlockEntity blockEntity, int pX, int pY)
	{
		super(pX, pY, TEXT);
		this.blockEntity = blockEntity;
	}

	@Override
	public void onPress()
	{
		Minecraft.getInstance().setScreen(new ChessLoadFENScreen(this.blockEntity));
	}
}