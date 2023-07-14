package andrews.table_top_craft.screens.chess.menus;

import andrews.table_top_craft.screens.chess.buttons.colors.*;
import andrews.table_top_craft.screens.chess.buttons.pieces.ChessBoardPieceSettingsButton;
import andrews.table_top_craft.screens.chess.buttons.settings.ChessBoardSettingsButton;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
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
	private final ChessTileEntity chessTileEntity;
	private final int xSize = 177;
	private final int ySize = 198;
	
	public ChessColorSettingsScreen(ChessTileEntity chessTileEntity)
	{
		super(Component.literal(""));
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
		this.addRenderableWidget(new ChessBoardSettingsButton(this.chessTileEntity, x - 24, y + 16));
		this.addRenderableWidget(new ChessBoardColorSettingsButton(this.chessTileEntity, x - 24, y + 42));
		this.addRenderableWidget(new ChessBoardPieceSettingsButton(this.chessTileEntity, x - 24, y + 68));

		this.addRenderableWidget(new ChessPlayAnimationsButton(this.chessTileEntity, x + 5, y + 20));
		this.addRenderableWidget(new ChessDisplayParticlesButton(this.chessTileEntity, x + 5, y + 35));
		this.addRenderableWidget(new ChessTileInfoColorsButton(this.chessTileEntity, x + 5, y + 50));
		this.addRenderableWidget(new ChessUseCustomPlateButton(this.chessTileEntity, x + 5, y + 65));
		this.addRenderableWidget(new ChessBoardTilesColorButton(this.chessTileEntity, x + 5, y + 80));
		this.addRenderableWidget(new ChessBoardPieceColorsButton(this.chessTileEntity, x + 5, y + 95));
		this.addRenderableWidget(new ChessBoardLegalMoveColorButton(this.chessTileEntity, x + 5, y + 110));
		this.addRenderableWidget(new ChessBoardInvalidMoveColorButton(this.chessTileEntity, x + 5, y + 125));
		this.addRenderableWidget(new ChessBoardAttackMoveColorButton(this.chessTileEntity, x + 5, y + 140));
		this.addRenderableWidget(new ChessBoardPreviousMoveColorButton(this.chessTileEntity, x + 5, y + 155));
		this.addRenderableWidget(new ChessBoardCastleMoveColorButton(this.chessTileEntity, x + 5, y + 170));
	}
	
	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
	{
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, MENU_TEXTURE);
		this.blit(poseStack, x, y, 0, 0, this.xSize, this.ySize);
		this.blit(poseStack, x, y + 41, 0, 198, 3, 26);
		
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
		if(this.minecraft.options.keyInventory.matches(keyCode, scanCode))
			this.onClose();
		return true;
	}
}