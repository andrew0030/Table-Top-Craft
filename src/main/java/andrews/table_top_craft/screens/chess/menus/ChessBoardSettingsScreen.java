package andrews.table_top_craft.screens.chess.menus;

import com.mojang.blaze3d.matrix.MatrixStack;

import andrews.table_top_craft.screens.chess.buttons.ChessBoardColorSettingsButton;
import andrews.table_top_craft.screens.chess.buttons.ChessBoardSettingsButton;
import andrews.table_top_craft.screens.chess.buttons.ChessCopyFENButton;
import andrews.table_top_craft.screens.chess.buttons.ChessLoadFENButton;
import andrews.table_top_craft.screens.chess.buttons.ChessNewGameButton;
import andrews.table_top_craft.screens.chess.buttons.ChessShowAvailableMovesButton;
import andrews.table_top_craft.screens.chess.buttons.ChessShowPreviousMoveButton;
import andrews.table_top_craft.screens.chess.buttons.ChessShowTileInfoButton;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.Reference;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ChessBoardSettingsScreen extends Screen
{
	private static final ResourceLocation MENU_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/chess_menu.png");
	private final String chessBoardSettingsText = new TranslationTextComponent("gui.table_top_craft.chess.board_settings").getString();
	private final String showTileInfoText = new TranslationTextComponent("gui.table_top_craft.chess.show_tile_info").getString();
	private final String showAvailableMovesText = new TranslationTextComponent("gui.table_top_craft.chess.show_available_moves").getString();
	private final String showPreviousMoveText = new TranslationTextComponent("gui.table_top_craft.chess.show_previous_move").getString();
	private ChessTileEntity chessTileEntity;
	private final int xSize = 177;
	private final int ySize = 198;
	
	public ChessBoardSettingsScreen(ChessTileEntity chessTileEntity)
	{
		super(new StringTextComponent(""));
		this.chessTileEntity = chessTileEntity;
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
		this.addButton(new ChessCopyFENButton(this.chessTileEntity.getPos(), x + 5, y + 31));
		this.addButton(new ChessLoadFENButton(this.chessTileEntity, x + 89, y + 31));
		
		this.addButton(new ChessShowTileInfoButton(this.chessTileEntity, x + 5, y + 46));
		this.addButton(new ChessShowAvailableMovesButton(this.chessTileEntity, x + 5, y + 60));
		this.addButton(new ChessShowPreviousMoveButton(this.chessTileEntity, x + 5, y + 74));
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		
		this.minecraft.getTextureManager().bindTexture(MENU_TEXTURE);
		this.blit(matrixStack, x, y, 0, 0, this.xSize, this.ySize);
		this.blit(matrixStack, x, y + 15, 0, 198, 3, 26);
		
		this.font.drawString(matrixStack, this.chessBoardSettingsText, ((this.width / 2) - (this.font.getStringWidth(this.chessBoardSettingsText) / 2)), y + 6, 4210752);
		this.font.drawString(matrixStack, this.showTileInfoText, x + 20, y + 49, 0x000000);
		this.font.drawString(matrixStack, this.showAvailableMovesText, x + 20, y + 63, 0x000000);
		this.font.drawString(matrixStack, this.showPreviousMoveText, x + 20, y + 77, 0x000000);
		
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
}
