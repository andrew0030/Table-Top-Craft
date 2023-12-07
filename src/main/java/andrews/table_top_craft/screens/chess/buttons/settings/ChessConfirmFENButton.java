package andrews.table_top_craft.screens.chess.buttons.settings;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.screens.base.buttons.BaseTextButtonSmall;
import andrews.table_top_craft.util.NetworkUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

public class ChessConfirmFENButton extends BaseTextButtonSmall
{
	private static final Component TEXT = Component.translatable("gui.table_top_craft.chess.confirm_fen");
	private final ChessBlockEntity blockEntity;
	private final EditBox fenStringField;
	
	public ChessConfirmFENButton(ChessBlockEntity blockEntity, EditBox textFieldWidget, int pX, int pY)
	{
		super(pX, pY, TEXT);
		this.blockEntity = blockEntity;
		this.fenStringField = textFieldWidget;
	}

	@Override
	public boolean setActive()
	{
		return !this.fenStringField.getValue().isEmpty();
	}

	@Override
	public void onPress()
	{
		if(!fenStringField.getValue().isEmpty())
		{
			NetworkUtil.loadFENMessage(this.blockEntity.getBlockPos(), this.fenStringField.getValue());
			Minecraft.getInstance().player.closeContainer();
		}
	}
}