package andrews.table_top_craft.screens.chess.menus;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.screens.base.BaseScreen;
import andrews.table_top_craft.screens.chess.buttons.colors.*;
import andrews.table_top_craft.screens.chess.buttons.pieces.ChessBoardPieceSettingsButton;
import andrews.table_top_craft.screens.chess.buttons.settings.ChessBoardSettingsButton;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ChessColorSettingsScreen extends BaseScreen
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/chess_menu.png");
	private static final Component TITLE = Component.translatable("gui.table_top_craft.chess.board_colors");
	private static final Component USE_CUSTOM_PLATE_TXT = Component.translatable("gui.table_top_craft.chess.use_custom_plate");
	private static final Component PLAY_PIECE_ANIMATIONS_TXT = Component.translatable("gui.table_top_craft.chess.play_piece_animations");
	private static final Component DISPLAY_PARTICLES_TXT = Component.translatable("gui.table_top_craft.chess.display_particles");
	private final ChessBlockEntity chessBlockEntity;
	
	public ChessColorSettingsScreen(ChessBlockEntity chessBlockEntity)
	{
		super(TEXTURE, 177, 198, TITLE);
		this.chessBlockEntity = chessBlockEntity;
	}
	
	@Override
	protected void init()
	{
		super.init();
		// The Page Selection Buttons in the Gui Menu
		this.addRenderableWidget(new ChessBoardSettingsButton(this.chessBlockEntity, this.x - 24, this.y + 16));
		this.addRenderableWidget(new ChessBoardColorSettingsButton(this.chessBlockEntity, this.x - 24, this.y + 42));
		this.addRenderableWidget(new ChessBoardPieceSettingsButton(this.chessBlockEntity, this.x - 24, this.y + 68));
		// The Animations Toggle Button
		this.addRenderableWidget(new ChessPlayAnimationsButton(this.chessBlockEntity, this.x + 5, this.y + 20));
		// The Particles Toggle Button
		this.addRenderableWidget(new ChessDisplayParticlesButton(this.chessBlockEntity, this.x + 5, this.y + 35));
		// Color Modification Buttons
		this.addRenderableWidget(new ChessTileInfoColorsButton(this.chessBlockEntity, this.x + 5, this.y + 50));
		this.addRenderableWidget(new ChessUseCustomPlateButton(this.chessBlockEntity, this.x + 5, this.y + 65));
		this.addRenderableWidget(new ChessBoardTilesColorButton(this.chessBlockEntity, this.x + 5, this.y + 80));
		this.addRenderableWidget(new ChessBoardPieceColorsButton(this.chessBlockEntity, this.x + 5, this.y + 95));
		this.addRenderableWidget(new ChessBoardLegalMoveColorButton(this.chessBlockEntity, this.x + 5, this.y + 110));
		this.addRenderableWidget(new ChessBoardInvalidMoveColorButton(this.chessBlockEntity, this.x + 5, this.y + 125));
		this.addRenderableWidget(new ChessBoardAttackMoveColorButton(this.chessBlockEntity, this.x + 5, this.y + 140));
		this.addRenderableWidget(new ChessBoardPreviousMoveColorButton(this.chessBlockEntity, this.x + 5, this.y + 155));
		this.addRenderableWidget(new ChessBoardCastleMoveColorButton(this.chessBlockEntity, this.x + 5, this.y + 170));
	}

	@Override
	public void renderScreenContents(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
	{
		// Renders a bar over the side of the panel to make it seamless
		graphics.blit(TEXTURE, this.x, this.y + 41, 0, 198, 3, 26);
		// Renders all the text in the menu
		this.drawCenteredString(TITLE, this.width / 2, this.y + 6, 4210752, false, graphics);
		graphics.drawString(this.font, USE_CUSTOM_PLATE_TXT, this.x + 19, this.y + 68, 0x000000, false);
		graphics.drawString(this.font, PLAY_PIECE_ANIMATIONS_TXT, this.x + 19, this.y + 23, 0x000000, false);
		graphics.drawString(this.font, DISPLAY_PARTICLES_TXT, this.x + 19, this.y + 38, 0x000000, false);
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
}