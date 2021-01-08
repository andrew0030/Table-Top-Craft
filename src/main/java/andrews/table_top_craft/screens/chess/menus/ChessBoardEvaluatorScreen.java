package andrews.table_top_craft.screens.chess.menus;

import com.google.common.primitives.Ints;
import com.mojang.blaze3d.matrix.MatrixStack;

import andrews.table_top_craft.game_logic.chess.player.ai.StandardBoardEvaluator;
import andrews.table_top_craft.screens.chess.buttons.ChessCancelButton;
import andrews.table_top_craft.screens.chess.buttons.ChessCancelButton.ChessCancelButtonText;
import andrews.table_top_craft.screens.chess.buttons.ChessCancelButton.ChessCancelMenuTarget;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChessBoardEvaluatorScreen extends Screen
{
	private static final ResourceLocation MENU_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/chess_menu.png");
	private final String chessBoardEvaluatorText = new TranslationTextComponent("gui.table_top_craft.chess.board_evaluator").getString();
	private final String whitePlayerText = new TranslationTextComponent("gui.table_top_craft.chess.evaluation.white_player").getString();
	private final String blackPlayerText = new TranslationTextComponent("gui.table_top_craft.chess.evaluation.black_player").getString();
	private final String currentScoreText = new TranslationTextComponent("gui.table_top_craft.chess.evaluation.current_score").getString();
	private final String boardEvaluationText;
	private ChessTileEntity chessTileEntity;
	private final ClientPlayerEntity clientPlayer;
	private final int xSize = 177;
	private final int ySize = 198;
	
	public ChessBoardEvaluatorScreen(ChessTileEntity chessTileEntity)
	{
		super(new StringTextComponent(""));
		this.chessTileEntity = chessTileEntity;
		boardEvaluationText = StandardBoardEvaluator.get().evaluationDetails(chessTileEntity.getBoard(), 0);
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
		this.addButton(new ChessCancelButton(this.chessTileEntity, ChessCancelMenuTarget.CHESS_BOARD_SETTINGS, ChessCancelButtonText.BACK, (x + (xSize / 2)) - 41, y + 180));
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		
		this.minecraft.getTextureManager().bindTexture(MENU_TEXTURE);
		this.blit(matrixStack, x, y, 0, 0, this.xSize, this.ySize);
		this.blit(matrixStack, x + 5, y + 152, 0, 224, 167, 12);
		
		int offset = StandardBoardEvaluator.get().evaluate(chessTileEntity.getBoard(), 1) / 23;
		offset = Ints.constrainToRange(offset, -80, 84);
		matrixStack.push();
		matrixStack.translate(offset, 0, 0);
		if(this.clientPlayer.ticksExisted % 16 <= 8)
			this.blit(matrixStack, x + 83, y + 162, 3, 198, 7, 5);
		matrixStack.pop();
		offset = Ints.constrainToRange(offset, -82 + (this.font.getStringWidth(this.currentScoreText) / 2), 88 - (this.font.getStringWidth(this.currentScoreText) / 2));
		matrixStack.push();
		matrixStack.translate(offset, 0, 0);
		this.font.drawString(matrixStack, this.currentScoreText, ((x + 86) - (this.font.getStringWidth(this.currentScoreText) / 2)), y + 168, 0x000000);
		matrixStack.pop();
		
		this.font.drawString(matrixStack, this.chessBoardEvaluatorText, ((this.width / 2) - (this.font.getStringWidth(this.chessBoardEvaluatorText) / 2)), y + 6, 4210752);
		this.font.drawString(matrixStack, this.blackPlayerText, x + 7, y + 154, 0xffffff);
		this.font.drawString(matrixStack, this.whitePlayerText, ((x + xSize) - (this.font.getStringWidth(this.whitePlayerText) + 6)), y + 154, 0x000000);
		renderEvaluationText(matrixStack, x, y);
		
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
	
	/**
	 * @param matrixStack - The MatrixStack
	 * @param x - The X Position
	 * @param y - The Y Position
	 */
	private void renderEvaluationText(MatrixStack matrixStack, int x, int y)
	{
		String[] evaluationLines = this.boardEvaluationText.split("\n");
		for(int i = 0; i < evaluationLines.length; i++)
		{
			this.font.drawString(matrixStack, evaluationLines[i], x + 5, y + 17, 0x000000);
			y += 9;
		}
	}
}