package andrews.table_top_craft.screens.chess.menus;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.game_logic.chess.PieceColor;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessBoardColorSettingsButton;
import andrews.table_top_craft.screens.chess.buttons.pieces.ChessBoardPieceSettingsButton;
import andrews.table_top_craft.screens.chess.buttons.settings.*;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ChessBoardSettingsScreen extends Screen
{
	private static final ResourceLocation MENU_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/chess_menu.png");
	private static final ResourceLocation MOVE_LOG_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/move_log.png");
	private final String chessBoardSettingsText = Component.translatable("gui.table_top_craft.chess.board_settings").getString();
	private final String moveLogText = Component.translatable("gui.table_top_craft.chess.move_log").getString();
	private final String moveLogWhiteText = Component.translatable("gui.table_top_craft.chess.move_log.white").getString();
	private final String moveLogBlackText = Component.translatable("gui.table_top_craft.chess.move_log.black").getString();
	private final String showTileInfoText = Component.translatable("gui.table_top_craft.chess.show_tile_info").getString();
	private final String showAvailableMovesText = Component.translatable("gui.table_top_craft.chess.show_available_moves").getString();
	private final String showPreviousMoveText = Component.translatable("gui.table_top_craft.chess.show_previous_move").getString();
	private final ChessBlockEntity chessBlockEntity;
	private final int xSize = 177;
	private final int ySize = 198;
	private int moveLogOffset;
	
	public ChessBoardSettingsScreen(ChessBlockEntity chessBlockEntity)
	{
		super(Component.literal(""));
		this.chessBlockEntity = chessBlockEntity;
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
		this.addRenderableWidget(new ChessBoardSettingsButton(this.chessBlockEntity, x - 24, y + 16));
		this.addRenderableWidget(new ChessBoardColorSettingsButton(this.chessBlockEntity, x - 24, y + 42));
		this.addRenderableWidget(new ChessBoardPieceSettingsButton(this.chessBlockEntity, x - 24, y + 68));
		
		this.addRenderableWidget(new ChessNewGameButton(this.chessBlockEntity.getBlockPos(), x + 5, y + 16));
		this.addRenderableWidget(new ChessEvaluateBoardButton(this.chessBlockEntity, x + 90, y + 16));
		this.addRenderableWidget(new ChessCopyFENButton(this.chessBlockEntity, x + 5, y + 31));
		this.addRenderableWidget(new ChessLoadFENButton(this.chessBlockEntity, x + 90, y + 31));
		
		this.addRenderableWidget(new ChessShowTileInfoButton(this.chessBlockEntity, x + 5, y + 46));
		this.addRenderableWidget(new ChessShowAvailableMovesButton(this.chessBlockEntity, x + 5, y + 60));
		this.addRenderableWidget(new ChessShowPreviousMoveButton(this.chessBlockEntity, x + 5, y + 74));
		
		this.addRenderableWidget(new ChessMoveLogDownButton(x + 161, y + 112, this));
		this.addRenderableWidget(new ChessMoveLogUpButton(x + 161, y + 167, this, this.chessBlockEntity));
	}
	
	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
	{
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, MENU_TEXTURE);
		GuiComponent.blit(poseStack, x, y, 0, 0, this.xSize, this.ySize);
		GuiComponent.blit(poseStack, x, y + 15, 0, 198, 3, 26);
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, MOVE_LOG_TEXTURE);
		GuiComponent.blit(poseStack, x + 5, y + 101, 0, 0, 155, 89);
		for(int i = 0; i < (this.moveLogOffset % 2 != 0 ? 3 : 4); i++)
		{
			GuiComponent.blit(poseStack, x + 6, y + 124 + (i * 22) - (this.moveLogOffset % 2 != 0 ? 0 : 11), 0, 89, 153, 10);
		}
		
		this.font.draw(poseStack, this.chessBoardSettingsText, ((this.width / 2) - (this.font.width(this.chessBoardSettingsText) / 2)), y + 6, 4210752);
		this.font.draw(poseStack, this.moveLogText, ((this.width / 2) - (this.font.width(this.moveLogText) / 2)), y + 90, 4210752);
		this.font.draw(poseStack, this.showTileInfoText, x + 20, y + 49, 0x000000);
		this.font.draw(poseStack, this.showAvailableMovesText, x + 20, y + 63, 0x000000);
		this.font.draw(poseStack, this.showPreviousMoveText, x + 20, y + 77, 0x000000);
		this.font.draw(poseStack, this.moveLogWhiteText, x + 54 - (this.font.width(this.moveLogWhiteText) / 2), y + 103, 0x000000);
		this.font.draw(poseStack, this.moveLogBlackText, x + 124 - (this.font.width(this.moveLogBlackText) / 2), y + 103, 0x000000);
		
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
					this.font.draw(poseStack, moveName, x - ((moveName.length() * 6) / 2) + 54 + (pieceColor == PieceColor.WHITE ? 0 : 70), y + 114 + offset, 0x000000);
					if(i % 2 == 0)
					{
						this.font.draw(poseStack, String.valueOf(currentMoveId + this.moveLogOffset), x + 7 + (currentMoveId + this.moveLogOffset > 9 ? 0 : 3), y + 114 + offset, 0x000000);
						currentMoveId += 1;
					}
				}
				
				if(i % 2 != 0)
					offset += 11;
			}
		}
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
	 * @param chessBlockEntity The Chess Tile Entity
	 */
	public static void open(ChessBlockEntity chessBlockEntity)
	{
		Minecraft.getInstance().setScreen(new ChessBoardSettingsScreen(chessBlockEntity));
	}
}