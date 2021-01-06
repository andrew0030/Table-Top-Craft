package andrews.table_top_craft.screens.chess.menus;

import com.mojang.blaze3d.matrix.MatrixStack;

import andrews.table_top_craft.screens.chess.buttons.colors.ChessBoardAttackMoveColorButton;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessBoardCastleMoveColorButton;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessBoardColorSettingsButton;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessBoardInvalidMoveColorButton;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessBoardLegalMoveColorButton;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessBoardPieceColorsButton;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessBoardPreviousMoveColorButton;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessBoardTilesColorButton;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessTileInfoColorsButton;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessUseCustomPlateButton;
import andrews.table_top_craft.screens.chess.buttons.settings.ChessBoardSettingsButton;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.Reference;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ChessColorSettingsScreen extends Screen
{
	private static final ResourceLocation MENU_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/chess_menu.png");
	private final String chessBoardColorsText = new TranslationTextComponent("gui.table_top_craft.chess.board_colors").getString();
	private final String useCustomPlateText = new TranslationTextComponent("gui.table_top_craft.chess.use_custom_plate").getString();
	private ChessTileEntity chessTileEntity;
	private final int xSize = 177;
	private final int ySize = 198;
	
	public ChessColorSettingsScreen(ChessTileEntity chessTileEntity)
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
		this.addButton(new ChessTileInfoColorsButton(this.chessTileEntity, x + 5, y + 20));
		this.addButton(new ChessUseCustomPlateButton(this.chessTileEntity, x + 5, y + 35));
		this.addButton(new ChessBoardTilesColorButton(this.chessTileEntity, x + 5, y + 50));
		this.addButton(new ChessBoardPieceColorsButton(this.chessTileEntity, x + 5, y + 65));
		this.addButton(new ChessBoardLegalMoveColorButton(this.chessTileEntity, x + 5, y + 80));
		this.addButton(new ChessBoardInvalidMoveColorButton(this.chessTileEntity, x + 5, y + 95));
		this.addButton(new ChessBoardAttackMoveColorButton(this.chessTileEntity, x + 5, y + 110));
		this.addButton(new ChessBoardPreviousMoveColorButton(this.chessTileEntity, x + 5, y + 125));
		this.addButton(new ChessBoardCastleMoveColorButton(this.chessTileEntity, x + 5, y + 140));
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		
		this.minecraft.getTextureManager().bindTexture(MENU_TEXTURE);
		this.blit(matrixStack, x, y, 0, 0, this.xSize, this.ySize);
		this.blit(matrixStack, x, y + 41, 0, 198, 3, 26);
		
		this.font.drawString(matrixStack, this.chessBoardColorsText, ((this.width / 2) - (this.font.getStringWidth(this.chessBoardColorsText) / 2)), y + 6, 4210752);
		this.font.drawString(matrixStack, this.useCustomPlateText, x + 19, y + 37, 0x000000);
		
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
