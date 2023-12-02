package andrews.table_top_craft.screens.chess.menus;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.game_logic.chess.player.ai.StandardBoardEvaluator;
import andrews.table_top_craft.screens.base.BaseScreen;
import andrews.table_top_craft.screens.chess.buttons.ChessCancelButton;
import andrews.table_top_craft.screens.chess.buttons.ChessCancelButton.ChessCancelButtonText;
import andrews.table_top_craft.screens.chess.buttons.ChessCancelButton.ChessCancelMenuTarget;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ChessBoardEvaluatorScreen extends BaseScreen
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/chess_menu.png");
	private static final Component TITLE = Component.translatable("gui.table_top_craft.chess.board_evaluator");
	private static final Component WHITE_PLAYER_TXT = Component.translatable("gui.table_top_craft.chess.evaluation.white_player");
	private static final Component BLACK_PLAYER_TXT = Component.translatable("gui.table_top_craft.chess.evaluation.black_player");
	private static final Component CURRENT_SCORE_TXT = Component.translatable("gui.table_top_craft.chess.evaluation.current_score");
	private final String boardEvaluationText;
	private final ChessBlockEntity chessBlockEntity;
	private final LocalPlayer clientPlayer;
	
	public ChessBoardEvaluatorScreen(ChessBlockEntity chessBlockEntity)
	{
		super(TEXTURE, 177, 198, TITLE);
		this.chessBlockEntity = chessBlockEntity;
		this.boardEvaluationText = StandardBoardEvaluator.get().evaluationDetails(chessBlockEntity.getBoard(), 0);
		this.clientPlayer = Minecraft.getInstance().player;
	}
	
	@Override
	protected void init()
	{
		super.init();
		// The Buttons in the Gui Menu
		this.addRenderableWidget(new ChessCancelButton(this.chessBlockEntity, ChessCancelMenuTarget.CHESS_BOARD_SETTINGS, ChessCancelButtonText.BACK, (this.x + (this.textureWidth / 2)) - 41, this.y + 180));
	}

	@Override
	public void renderScreenContents(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
	{
		// Gradient Bar at the bottom of the Menu
		graphics.blit(TEXTURE, this.x + 5, this.y + 152, 0, 224, 167, 12);
		// Blinking Score Indicator Triangle and Text
		int offset = StandardBoardEvaluator.get().evaluate(chessBlockEntity.getBoard(), 1) / 23;
		offset = Mth.clamp(offset, -80, 84);
		graphics.pose().pushPose();
		graphics.pose().translate(offset, 0, 0);
		if(this.clientPlayer.tickCount % 16 <= 8)
			graphics.blit(TEXTURE, this.x + 83, this.y + 162, 3, 198, 7, 5);
		graphics.pose().popPose();
		offset = Mth.clamp(offset, -82 + (this.font.width(CURRENT_SCORE_TXT) / 2), 88 - (this.font.width(CURRENT_SCORE_TXT) / 2));
		graphics.pose().pushPose();
		graphics.pose().translate(offset, 0, 0);
//		this.font.draw(poseStack, this.currentScoreText, ((x + 86) - (this.font.width(this.currentScoreText) / 2)), y + 168, 0x000000);
		this.drawCenteredString(CURRENT_SCORE_TXT, this.x + 86, this.y + 168, 0x000000, false, graphics);
		graphics.pose().popPose();
		// The Title
		this.drawCenteredString(TITLE, this.width / 2, this.y + 6, 4210752, false, graphics);
		graphics.drawString(this.font, BLACK_PLAYER_TXT, this.x + 7, this.y + 154, 0xffffff, false);
		graphics.drawString(this.font, WHITE_PLAYER_TXT, ((this.x + this.textureWidth) - (this.font.width(WHITE_PLAYER_TXT) + 6)), this.y + 154, 0x000000, false);
		this.renderEvaluationText(graphics, this.x, this.y);
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

	/**
	 * @param poseStack The PoseStack
	 * @param x The X Position
	 * @param y The Y Position
	 */
	private void renderEvaluationText(GuiGraphics graphics, int x, int y)
	{
		String[] evaluationLines = this.boardEvaluationText.split("\n");
		for (String evaluationLine : evaluationLines)
		{
			graphics.drawString(this.font, evaluationLine, x + 5, y + 17, 0x000000, false);
			y += 9;
		}
	}
}