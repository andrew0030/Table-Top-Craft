package andrews.table_top_craft.screens.chess.menus;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.screens.base.BaseScreen;
import andrews.table_top_craft.screens.chess.buttons.ChessCancelButton;
import andrews.table_top_craft.screens.chess.buttons.ChessCancelButton.ChessCancelButtonText;
import andrews.table_top_craft.screens.chess.buttons.ChessCancelButton.ChessCancelMenuTarget;
import andrews.table_top_craft.screens.chess.buttons.settings.ChessConfirmFENButton;
import andrews.table_top_craft.util.Reference;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ChessLoadFENScreen extends BaseScreen
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/small_chess_menu.png");
	private static final Component TITLE = Component.translatable("gui.table_top_craft.chess.fen_loader");
	private final ChessBlockEntity chessBlockEntity;
	private EditBox fenStringField;
	
	public ChessLoadFENScreen(ChessBlockEntity chessBlockEntity)
	{
		super(TEXTURE, 177, 85, TITLE);
		this.chessBlockEntity = chessBlockEntity;
	}
	
	@Override
	public void tick()
	{
		this.fenStringField.tick();
	}
	
	@Override
	protected void init()
	{
		super.init();
		// The Text Field in which the Player enters the FEN String
		this.fenStringField = new EditBox(this.font, this.x + 5, this.y + 20, 167, 16, Component.literal("FEN Field"));
		this.fenStringField.setMaxLength(100);
	    this.fenStringField.setFocused(true);
		// The Buttons in the Gui Menu
	    this.addRenderableWidget(new ChessCancelButton(this.chessBlockEntity, ChessCancelMenuTarget.CHESS_BOARD_SETTINGS, ChessCancelButtonText.CANCEL, this.x + 5, this.y + 39));
	    this.addRenderableWidget(new ChessConfirmFENButton(this.chessBlockEntity, fenStringField, this.x + 90, this.y + 39));
	}

	@Override
	public void renderScreenContents(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
	{
		// The Menu Title
		this.drawCenteredString(TITLE, this.width / 2, this.y + 6, 4210752, false, graphics);
		// The FEN Text Box
		this.fenStringField.render(graphics, mouseX, mouseY, partialTick);
	}

	@Override
	public boolean isPauseScreen()
	{
		return false;
	}

	@Override
	public boolean charTyped(char codePoint, int modifiers)
	{
		this.fenStringField.charTyped(codePoint, modifiers);
		return super.charTyped(codePoint, modifiers);
	}
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers)
	{
		fenStringField.keyPressed(keyCode, scanCode, modifiers);
		return super.keyPressed(keyCode, scanCode, modifiers);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button)
	{
		this.fenStringField.mouseClicked(mouseX, mouseY, button);
		return super.mouseClicked(mouseX, mouseY, button);
	}
}