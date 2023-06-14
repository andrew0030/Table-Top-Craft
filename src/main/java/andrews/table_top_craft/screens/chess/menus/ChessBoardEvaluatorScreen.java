package andrews.table_top_craft.screens.chess.menus;

import andrews.table_top_craft.game_logic.chess.player.ai.StandardBoardEvaluator;
import andrews.table_top_craft.screens.chess.buttons.ChessCancelButton;
import andrews.table_top_craft.screens.chess.buttons.ChessCancelButton.ChessCancelButtonText;
import andrews.table_top_craft.screens.chess.buttons.ChessCancelButton.ChessCancelMenuTarget;
import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.util.Reference;
import com.google.common.primitives.Ints;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ChessBoardEvaluatorScreen extends Screen
{
	private static final ResourceLocation MENU_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/chess_menu.png");
	private final String chessBoardEvaluatorText = Component.translatable("gui.table_top_craft.chess.board_evaluator").getString();
	private final String whitePlayerText = Component.translatable("gui.table_top_craft.chess.evaluation.white_player").getString();
	private final String blackPlayerText = Component.translatable("gui.table_top_craft.chess.evaluation.black_player").getString();
	private final String currentScoreText = Component.translatable("gui.table_top_craft.chess.evaluation.current_score").getString();
	private final String boardEvaluationText;
	private final ChessBlockEntity chessBlockEntity;
	private final LocalPlayer clientPlayer;
	private final int xSize = 177;
	private final int ySize = 198;
	
	public ChessBoardEvaluatorScreen(ChessBlockEntity chessBlockEntity)
	{
		super(Component.literal(""));
		this.chessBlockEntity = chessBlockEntity;
		boardEvaluationText = StandardBoardEvaluator.get().evaluationDetails(chessBlockEntity.getBoard(), 0);
		this.clientPlayer = Minecraft.getInstance().player;
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
		this.addRenderableWidget(new ChessCancelButton(this.chessBlockEntity, ChessCancelMenuTarget.CHESS_BOARD_SETTINGS, ChessCancelButtonText.BACK, (x + (xSize / 2)) - 41, y + 180));
	}
	
	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
	{
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, MENU_TEXTURE);
		GuiComponent.blit(poseStack, x, y, 0, 0, this.xSize, this.ySize);
		GuiComponent.blit(poseStack, x + 5, y + 152, 0, 224, 167, 12);
		
		int offset = StandardBoardEvaluator.get().evaluate(chessBlockEntity.getBoard(), 1) / 23;
		offset = Ints.constrainToRange(offset, -80, 84);
		poseStack.pushPose();
		poseStack.translate(offset, 0, 0);
		if(this.clientPlayer.tickCount % 16 <= 8)
			GuiComponent.blit(poseStack, x + 83, y + 162, 3, 198, 7, 5);
		poseStack.popPose();
		offset = Ints.constrainToRange(offset, -82 + (this.font.width(this.currentScoreText) / 2), 88 - (this.font.width(this.currentScoreText) / 2));
		poseStack.pushPose();
		poseStack.translate(offset, 0, 0);
		this.font.draw(poseStack, this.currentScoreText, ((x + 86) - (this.font.width(this.currentScoreText) / 2)), y + 168, 0x000000);
		poseStack.popPose();
		
		this.font.draw(poseStack, this.chessBoardEvaluatorText, ((this.width / 2) - (this.font.width(this.chessBoardEvaluatorText) / 2)), y + 6, 4210752);
		this.font.draw(poseStack, this.blackPlayerText, x + 7, y + 154, 0xffffff);
		this.font.draw(poseStack, this.whitePlayerText, ((x + xSize) - (this.font.width(this.whitePlayerText) + 6)), y + 154, 0x000000);
		renderEvaluationText(poseStack, x, y);
		
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
	
	/**
	 * @param poseStack The PoseStack
	 * @param x The X Position
	 * @param y The Y Position
	 */
	private void renderEvaluationText(PoseStack poseStack, int x, int y)
	{
		String[] evaluationLines = this.boardEvaluationText.split("\n");
		for (String evaluationLine : evaluationLines)
		{
			this.font.draw(poseStack, evaluationLine, x + 5, y + 17, 0x000000);
			y += 9;
		}
	}
}