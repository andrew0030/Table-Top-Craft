package andrews.table_top_craft.screens.chess.buttons.colors;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.screens.base.buttons.BaseToggleButton;
import andrews.table_top_craft.util.NetworkUtil;

public class ChessUseCustomPlateButton extends BaseToggleButton
{
	private final ChessBlockEntity blockEntity;
	
	public ChessUseCustomPlateButton(ChessBlockEntity blockEntity, int pX, int pY)
	{
		super(pX, pY);
		this.blockEntity = blockEntity;
	}

	@Override
	public boolean isToggled()
	{
		return this.blockEntity.getUseCustomPlate();
	}

	@Override
	public void onPress()
	{
		NetworkUtil.useCustomPlateMessage(this.blockEntity.getBlockPos());
	}
}