package andrews.table_top_craft.screens.chess.menus;

import com.mojang.blaze3d.matrix.MatrixStack;

import andrews.table_top_craft.game_logic.chess.PieceColor;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessBoardColorSettingsButton;
import andrews.table_top_craft.screens.chess.buttons.settings.ChessBoardSettingsButton;
import andrews.table_top_craft.screens.chess.buttons.settings.ChessCopyFENButton;
import andrews.table_top_craft.screens.chess.buttons.settings.ChessEvaluateBoardButton;
import andrews.table_top_craft.screens.chess.buttons.settings.ChessLoadFENButton;
import andrews.table_top_craft.screens.chess.buttons.settings.ChessMoveLogDownButton;
import andrews.table_top_craft.screens.chess.buttons.settings.ChessMoveLogUpButton;
import andrews.table_top_craft.screens.chess.buttons.settings.ChessNewGameButton;
import andrews.table_top_craft.screens.chess.buttons.settings.ChessShowAvailableMovesButton;
import andrews.table_top_craft.screens.chess.buttons.settings.ChessShowPreviousMoveButton;
import andrews.table_top_craft.screens.chess.buttons.settings.ChessShowTileInfoButton;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ChessBoardSettingsScreen extends Screen
{
	private static final ResourceLocation MENU_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/chess_menu.png");
	private static final ResourceLocation MOVE_LOG_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/move_log.png");
	private final String chessBoardSettingsText = new TranslationTextComponent("gui.table_top_craft.chess.board_settings").getString();
	private final String moveLogText = new TranslationTextComponent("gui.table_top_craft.chess.move_log").getString();
	private final String moveLogWhiteText = new TranslationTextComponent("gui.table_top_craft.chess.move_log.white").getString();
	private final String moveLogBlackText = new TranslationTextComponent("gui.table_top_craft.chess.move_log.black").getString();
	private final String showTileInfoText = new TranslationTextComponent("gui.table_top_craft.chess.show_tile_info").getString();
	private final String showAvailableMovesText = new TranslationTextComponent("gui.table_top_craft.chess.show_available_moves").getString();
	private final String showPreviousMoveText = new TranslationTextComponent("gui.table_top_craft.chess.show_previous_move").getString();
	private ChessTileEntity chessTileEntity;
	private final int xSize = 177;
	private final int ySize = 198;
	private int moveLogOffset;
	
	public ChessBoardSettingsScreen(ChessTileEntity chessTileEntity)
	{
		super(new StringTextComponent(""));
		this.chessTileEntity = chessTileEntity;
		this.moveLogOffset = 0;
	}
	
	@Override
	public boolean isPauseScreen()
	{
		return false;
	}
	
	@Override
	protected void init()
	{
		super.init();
		// Values to calculate the relative position
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		// The Buttons in the Gui Menu
		this.addButton(new ChessBoardSettingsButton(this.chessTileEntity, x - 24, y + 16));
		this.addButton(new ChessBoardColorSettingsButton(this.chessTileEntity, x - 24, y + 42));
		
		this.addButton(new ChessNewGameButton(this.chessTileEntity.getPos(), x + 5, y + 16));
		this.addButton(new ChessEvaluateBoardButton(this.chessTileEntity, x + 90, y + 16));
		this.addButton(new ChessCopyFENButton(this.chessTileEntity, x + 5, y + 31));
		this.addButton(new ChessLoadFENButton(this.chessTileEntity, x + 90, y + 31));
		
		this.addButton(new ChessShowTileInfoButton(this.chessTileEntity, x + 5, y + 46));
		this.addButton(new ChessShowAvailableMovesButton(this.chessTileEntity, x + 5, y + 60));
		this.addButton(new ChessShowPreviousMoveButton(this.chessTileEntity, x + 5, y + 74));
		
		this.addButton(new ChessMoveLogDownButton(x + 161, y + 112, this));
		this.addButton(new ChessMoveLogUpButton(x + 161, y + 167, this, this.chessTileEntity));
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		
		this.minecraft.getTextureManager().bindTexture(MENU_TEXTURE);
		this.blit(matrixStack, x, y, 0, 0, this.xSize, this.ySize);
		this.blit(matrixStack, x, y + 15, 0, 198, 3, 26);
		this.minecraft.getTextureManager().bindTexture(MOVE_LOG_TEXTURE);
		this.blit(matrixStack, x + 5, y + 101, 0, 0, 155, 89);
		for(int i = 0; i < (this.moveLogOffset % 2 != 0 ? 3 : 4); i++)
		{
			this.blit(matrixStack, x + 6, y + 124 + (i * 22) - (this.moveLogOffset % 2 != 0 ? 0 : 11), 0, 89, 153, 10);
		}
		
		this.font.drawString(matrixStack, this.chessBoardSettingsText, ((this.width / 2) - (this.font.getStringWidth(this.chessBoardSettingsText) / 2)), y + 6, 4210752);
		this.font.drawString(matrixStack, this.moveLogText, ((this.width / 2) - (this.font.getStringWidth(this.moveLogText) / 2)), y + 90, 4210752);
		this.font.drawString(matrixStack, this.showTileInfoText, x + 20, y + 49, 0x000000);
		this.font.drawString(matrixStack, this.showAvailableMovesText, x + 20, y + 63, 0x000000);
		this.font.drawString(matrixStack, this.showPreviousMoveText, x + 20, y + 77, 0x000000);
		this.font.drawString(matrixStack, this.moveLogWhiteText, x + 54 - (this.font.getStringWidth(this.moveLogWhiteText) / 2), y + 103, 0x000000);
		this.font.drawString(matrixStack, this.moveLogBlackText, x + 124 - (this.font.getStringWidth(this.moveLogBlackText) / 2), y + 103, 0x000000);
		
		int offset = 0;
		int currentMoveId = 1;
		if(this.chessTileEntity.getMoveLog() != null)
		{
			
			offset -= this.moveLogOffset * 11;
			
			for(int i = 0; i < this.chessTileEntity.getMoveLog().getMoves().size(); i++)
			{
				PieceColor pieceColor = this.chessTileEntity.getMoveLog().getMoves().get(i).getMovedPiece().getPieceColor();
				String moveName = this.chessTileEntity.getMoveLog().getMoves().get(i).toString();
				if((this.moveLogOffset * 2) <= i && (this.moveLogOffset * 2 + 14) > i)
				{
					this.font.drawString(matrixStack, moveName, x - ((moveName.length() * 6) / 2) + 54 + (pieceColor == PieceColor.WHITE ? 0 : 70), y + 114 + offset, 0x000000);
					if(i % 2 == 0)
					{
						this.font.drawString(matrixStack, String.valueOf(currentMoveId + this.moveLogOffset), x + 7 + (currentMoveId + this.moveLogOffset > 9 ? 0 : 3), y + 114 + offset, 0x000000);
						currentMoveId += 1;
					}
				}
				
				if(i % 2 != 0)
					offset += 11;
			}
		}
		// Renders the Buttons we added in init
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers)
	{
		super.keyPressed(keyCode, scanCode, modifiers);
		InputMappings.Input mouseKey = InputMappings.getInputByCode(keyCode, scanCode);
		if(this.minecraft.gameSettings.keyBindInventory.isActiveAndMatches(mouseKey))
			this.closeScreen();
		return true;
	}
	
	public int getMoveLogOffset()
	{
		return this.moveLogOffset;
	}
	
	public void decreaseMoveLogOffset()
	{
		if(this.moveLogOffset > 0)
			this.moveLogOffset--;
	}
	
	public void increaseMoveLogOffset()
	{
		this.moveLogOffset++;
	}
	
	/**
	 * Used to open this Gui, because class loading is a little child that screams if it does not like you
	 * @param chessTileEntity - The Chess Tile Entity
	 */
	public static void open(ChessTileEntity chessTileEntity)
	{
		Minecraft.getInstance().displayGuiScreen(new ChessBoardSettingsScreen(chessTileEntity));
	}
}
