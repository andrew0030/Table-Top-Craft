package andrews.table_top_craft.screens.chess.menus;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.screens.chess.buttons.colors.*;
import andrews.table_top_craft.screens.chess.buttons.pieces.ChessBoardPieceSettingsButton;
import andrews.table_top_craft.screens.chess.buttons.settings.ChessBoardSettingsButton;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ChessColorSettingsScreen extends Screen
{
	private static final ResourceLocation MENU_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/chess_menu.png");
	private final String chessBoardColorsText = Component.translatable("gui.table_top_craft.chess.board_colors").getString();
	private final String useCustomPlateText = Component.translatable("gui.table_top_craft.chess.use_custom_plate").getString();
	private final String playPieceAnimationsText = Component.translatable("gui.table_top_craft.chess.play_piece_animations").getString();
	private final String displayParticlesText = Component.translatable("gui.table_top_craft.chess.display_particles").getString();
	private final ChessBlockEntity chessBlockEntity;
	private final int xSize = 177;
	private final int ySize = 198;
	
	public ChessColorSettingsScreen(ChessBlockEntity chessBlockEntity)
	{
		super(Component.literal(""));
		this.chessBlockEntity = chessBlockEntity;
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
		this.addRenderableWidget(new ChessBoardSettingsButton(this.chessBlockEntity, x - 24, y + 16));
		this.addRenderableWidget(new ChessBoardColorSettingsButton(this.chessBlockEntity, x - 24, y + 42));
		this.addRenderableWidget(new ChessBoardPieceSettingsButton(this.chessBlockEntity, x - 24, y + 68));

		this.addRenderableWidget(new ChessPlayAnimationsButton(this.chessBlockEntity, x + 5, y + 20));
		this.addRenderableWidget(new ChessDisplayParticlesButton(this.chessBlockEntity, x + 5, y + 35));
		this.addRenderableWidget(new ChessTileInfoColorsButton(this.chessBlockEntity, x + 5, y + 50));
		this.addRenderableWidget(new ChessUseCustomPlateButton(this.chessBlockEntity, x + 5, y + 65));
		this.addRenderableWidget(new ChessBoardTilesColorButton(this.chessBlockEntity, x + 5, y + 80));
		this.addRenderableWidget(new ChessBoardPieceColorsButton(this.chessBlockEntity, x + 5, y + 95));
		this.addRenderableWidget(new ChessBoardLegalMoveColorButton(this.chessBlockEntity, x + 5, y + 110));
		this.addRenderableWidget(new ChessBoardInvalidMoveColorButton(this.chessBlockEntity, x + 5, y + 125));
		this.addRenderableWidget(new ChessBoardAttackMoveColorButton(this.chessBlockEntity, x + 5, y + 140));
		this.addRenderableWidget(new ChessBoardPreviousMoveColorButton(this.chessBlockEntity, x + 5, y + 155));
		this.addRenderableWidget(new ChessBoardCastleMoveColorButton(this.chessBlockEntity, x + 5, y + 170));
	}
	
	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
	{
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, MENU_TEXTURE);
		GuiComponent.blit(poseStack, x, y, 0, 0, this.xSize, this.ySize);
		GuiComponent.blit(poseStack, x, y + 41, 0, 198, 3, 26);
		
		this.font.draw(poseStack, this.chessBoardColorsText, ((this.width / 2) - (this.font.width(this.chessBoardColorsText) / 2)), y + 6, 4210752);
		this.font.draw(poseStack, this.useCustomPlateText, x + 19, y + 68, 0x000000);
		this.font.draw(poseStack, this.playPieceAnimationsText, x + 19, y + 23, 0x000000);
		this.font.draw(poseStack, this.displayParticlesText, x + 19, y + 38, 0x000000);
		
		// Renders the Buttons we added in init
		super.render(poseStack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers)
	{
		super.keyPressed(keyCode, scanCode, modifiers);
		InputConstants.Key mouseKey = InputConstants.getKey(keyCode, scanCode);
		if(this.minecraft.options.keyInventory.isActiveAndMatches(mouseKey))
			this.onClose();
		return true;
	}
}