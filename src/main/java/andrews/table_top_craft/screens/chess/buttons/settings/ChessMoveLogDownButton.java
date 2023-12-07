package andrews.table_top_craft.screens.chess.buttons.settings;

import andrews.table_top_craft.screens.base.buttons.BaseChessMoveLogButton;
import andrews.table_top_craft.screens.chess.menus.ChessBoardSettingsScreen;
import net.minecraft.client.gui.GuiGraphics;

public class ChessMoveLogDownButton extends BaseChessMoveLogButton
{
	private final ChessBoardSettingsScreen screen;
	
	public ChessMoveLogDownButton(int pX, int pY, ChessBoardSettingsScreen currentScreen)
	{
		super(pX, pY, 0, 89);
		this.screen = currentScreen;
	}

	@Override
	public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
	{
		this.active = this.screen.getMoveLogOffset() > 0;
		super.renderWidget(graphics, mouseX, mouseY, partialTick);
	}

	@Override
	public void onPress()
	{
		this.screen.decreaseMoveLogOffset();
	}
}