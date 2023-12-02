package andrews.table_top_craft.screens.chess.buttons.settings;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.game_logic.chess.board.ChessMoveLog;
import andrews.table_top_craft.screens.base.buttons.BaseChessMoveLogButton;
import andrews.table_top_craft.screens.chess.menus.ChessBoardSettingsScreen;
import andrews.table_top_craft.util.Reference;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class ChessMoveLogUpButton extends BaseChessMoveLogButton
{
	private final ChessBoardSettingsScreen screen;
	private ChessMoveLog moveLog;

	public ChessMoveLogUpButton(int pX, int pY, ChessBoardSettingsScreen currentScreen, ChessBlockEntity blockEntity)
	{
		super(pX, pY, 0, 112);
		this.screen = currentScreen;
		if(blockEntity.getMoveLog() != null)
			moveLog = blockEntity.getMoveLog();
	}

	@Override
	public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
	{
		if(this.moveLog != null)
			this.active = moveLog.getMoves().size() >= (15 + (screen.getMoveLogOffset() * 2));
		super.renderWidget(graphics, mouseX, mouseY, partialTick);
	}

	@Override
	public void onPress()
	{
		this.screen.increaseMoveLogOffset();
	}
}