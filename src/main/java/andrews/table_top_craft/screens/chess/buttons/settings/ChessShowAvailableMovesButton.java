package andrews.table_top_craft.screens.chess.buttons.settings;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.screens.base.buttons.BaseToggleButton;
import andrews.table_top_craft.util.NetworkUtil;

public class ChessShowAvailableMovesButton extends BaseToggleButton
{
	private final ChessBlockEntity blockEntity;
	
	public ChessShowAvailableMovesButton(ChessBlockEntity blockEntity, int pX, int pY)
	{
		super(pX, pY);
		this.blockEntity = blockEntity;
	}

	@Override
	public boolean isToggled()
	{
		return this.blockEntity.getShowAvailableMoves();
	}

	@Override
	public void onPress()
	{
		NetworkUtil.showAvailableMovesMessage(this.blockEntity.getBlockPos());
	}
}