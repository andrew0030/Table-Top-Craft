package andrews.table_top_craft.screens.chess.menus;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.game_logic.chess.PieceColor;
import andrews.table_top_craft.screens.base.BaseScreen;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessBoardColorSettingsButton;
import andrews.table_top_craft.screens.chess.buttons.pieces.ChessBoardPieceSettingsButton;
import andrews.table_top_craft.screens.chess.buttons.settings.*;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ChessBoardSettingsScreen extends BaseScreen
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/chess_menu.png");
	private static final ResourceLocation MOVE_LOG_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/move_log.png");
	private static final Component TITLE = Component.translatable("gui.table_top_craft.chess.board_settings");
	private static final Component MOVE_LOG_TXT = Component.translatable("gui.table_top_craft.chess.move_log");
	private static final Component MOVE_LOG_WHITE_TXT = Component.translatable("gui.table_top_craft.chess.move_log.white");
	private static final Component MOVE_LOG_BLACK_TXT = Component.translatable("gui.table_top_craft.chess.move_log.black");
	private static final Component SHOW_TILE_INFO_TXT = Component.translatable("gui.table_top_craft.chess.show_tile_info");
	private static final Component SHOW_AVAILABLE_MOVES_TXT = Component.translatable("gui.table_top_craft.chess.show_available_moves");
	private static final Component SHOW_PREVIOUS_MOVE_TXT = Component.translatable("gui.table_top_craft.chess.show_previous_move");
	private final ChessBlockEntity chessBlockEntity;
	private int moveLogOffset;
	
	public ChessBoardSettingsScreen(ChessBlockEntity chessBlockEntity)
	{
		super(TEXTURE, 177, 198, TITLE);
		this.chessBlockEntity = chessBlockEntity;
		this.moveLogOffset = 0;
	}
	
	@Override
	protected void init()
	{
		super.init();
		// The Page Selection Buttons in the Gui Menu
		this.addRenderableWidget(new ChessBoardSettingsButton(this.chessBlockEntity, this.x - 24, this.y + 16));
		this.addRenderableWidget(new ChessBoardColorSettingsButton(this.chessBlockEntity, this.x - 24, this.y + 42));
		this.addRenderableWidget(new ChessBoardPieceSettingsButton(this.chessBlockEntity, this.x - 24, this.y + 68));
		// Chess Game Buttons
		this.addRenderableWidget(new ChessNewGameButton(this.chessBlockEntity.getBlockPos(), this.x + 5, this.y + 16));
		this.addRenderableWidget(new ChessEvaluateBoardButton(this.chessBlockEntity, this.x + 90, this.y + 16));
		this.addRenderableWidget(new ChessCopyFENButton(this.chessBlockEntity, this.x + 5, this.y + 31));
		this.addRenderableWidget(new ChessLoadFENButton(this.chessBlockEntity, this.x + 90, this.y + 31));
		// Chess Toggle Buttons
		this.addRenderableWidget(new ChessShowTileInfoButton(this.chessBlockEntity, this.x + 5, this.y + 46));
		this.addRenderableWidget(new ChessShowAvailableMovesButton(this.chessBlockEntity, this.x + 5, this.y + 60));
		this.addRenderableWidget(new ChessShowPreviousMoveButton(this.chessBlockEntity, this.x + 5, this.y + 74));
		// Chess Move Log Buttons
		this.addRenderableWidget(new ChessMoveLogDownButton(this.x + 161, this.y + 112, this));
		this.addRenderableWidget(new ChessMoveLogUpButton(this.x + 161, this.y + 167, this, this.chessBlockEntity));
	}

	@Override
	public void renderScreenContents(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
	{
		// Renders a bar over the side of the panel to make it seamless
		graphics.blit(TEXTURE, this.x, this.y + 15, 0, 198, 3, 26);
		// Renders the Move Log related stuff
		graphics.blit(MOVE_LOG_TEXTURE, this.x + 5, this.y + 101, 0, 0, 155, 89);
		for(int i = 0; i < (this.moveLogOffset % 2 != 0 ? 3 : 4); i++)
			graphics.blit(MOVE_LOG_TEXTURE, this.x + 6, this.y + 124 + (i * 22) - (this.moveLogOffset % 2 != 0 ? 0 : 11), 0, 89, 153, 10);
		// Renders all the text in the menu
		this.drawCenteredString(TITLE, this.width / 2, this.y + 6, 4210752, false, graphics);
		this.drawCenteredString(MOVE_LOG_TXT, this.width / 2, this.y + 90, 4210752, false, graphics);
		graphics.drawString(this.font, SHOW_TILE_INFO_TXT, this.x + 20, this.y + 49, 0x000000, false);
		graphics.drawString(this.font, SHOW_AVAILABLE_MOVES_TXT, this.x + 20, this.y + 63, 0x000000, false);
		graphics.drawString(this.font, SHOW_PREVIOUS_MOVE_TXT, this.x + 20, this.y + 77, 0x000000, false);
		this.drawCenteredString(MOVE_LOG_WHITE_TXT, this.x + 54, this.y + 103, 0x000000, false, graphics);
		this.drawCenteredString(MOVE_LOG_BLACK_TXT, this.x + 124, this.y + 103, 0x000000, false, graphics);
		// Renders the text of the moves inside the Move Log
		int offset = 0;
		int currentMoveId = 1;
		if(this.chessBlockEntity.getMoveLog() != null)
		{
			offset -= this.moveLogOffset * 11;
			for(int i = 0; i < this.chessBlockEntity.getMoveLog().getMoves().size(); i++)
			{
				PieceColor pieceColor = this.chessBlockEntity.getMoveLog().getMoves().get(i).getMovedPiece().getPieceColor();
				String moveName = this.chessBlockEntity.getMoveLog().getMoves().get(i).toString();
				if((this.moveLogOffset * 2) <= i && (this.moveLogOffset * 2 + 14) > i)
				{
					graphics.drawString(this.font, moveName, this.x - ((moveName.length() * 6) / 2) + 54 + (pieceColor == PieceColor.WHITE ? 0 : 70), this.y + 114 + offset, 0x000000, false);
					if(i % 2 == 0)
					{
						graphics.drawString(this.font, String.valueOf(currentMoveId + this.moveLogOffset), this.x + 7 + (currentMoveId + this.moveLogOffset > 9 ? 0 : 3), this.y + 114 + offset, 0x000000, false);
						currentMoveId += 1;
					}
				}
				if(i % 2 != 0)
					offset += 11;
			}
		}
	}
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers)
	{
		super.keyPressed(keyCode, scanCode, modifiers);
		if(this.getMinecraft().options.keyInventory.isActiveAndMatches(InputConstants.getKey(keyCode, scanCode)))
			this.onClose();
		return true;
	}

	@Override
	public boolean isPauseScreen()
	{
		return false;
	}
	
	public int getMoveLogOffset()
	{
		return this.moveLogOffset;
	}
	
	public void decreaseMoveLogOffset()
	{
		if(this.moveLogOffset > 0) this.moveLogOffset--;
	}
	
	public void increaseMoveLogOffset()
	{
		this.moveLogOffset++;
	}
	
	/**
	 * Used to open this Gui, because class loading is a little child that screams if it does not like you
	 * @param chessBlockEntity The Chess Tile Entity
	 */
	public static void open(ChessBlockEntity chessBlockEntity)
	{
		Minecraft.getInstance().setScreen(new ChessBoardSettingsScreen(chessBlockEntity));
	}
}