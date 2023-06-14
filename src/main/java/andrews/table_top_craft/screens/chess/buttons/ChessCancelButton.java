package andrews.table_top_craft.screens.chess.buttons;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.screens.chess.menus.ChessBoardSettingsScreen;
import andrews.table_top_craft.screens.chess.menus.ChessColorSettingsScreen;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ChessCancelButton extends Button
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
	private final String buttonCancelText = Component.translatable("gui.table_top_craft.chess.cancel").getString();
	private final String buttonBackText = Component.translatable("gui.table_top_craft.chess.back").getString();
	private static ChessCancelMenuTarget targetScreen;
	private final ChessCancelButtonText targetText;
	private final Font fontRenderer;
	private static ChessBlockEntity chessBlockEntity;
	private static final int buttonWidth = 82;
	private static final int buttonHeight = 13;
	private int u = 0;
	private int v = 0;
	
	public ChessCancelButton(ChessBlockEntity tileEntity, ChessCancelMenuTarget target, ChessCancelButtonText targetText, int xPos, int yPos)
	{
		super(xPos, yPos, buttonWidth, buttonHeight, Component.literal(""), (button) -> { handleButtonPress(); }, DEFAULT_NARRATION);
		this.fontRenderer = Minecraft.getInstance().font;
		chessBlockEntity = tileEntity;
		targetScreen = target;
		this.targetText = targetText;
	}
	
	@Override
	public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
	{
		this.isHovered = mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height || this.isFocused();
		
		this.u = 0;
		if(this.isHovered)
			this.u = 82;
		
		// Renders the Button
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, TEXTURE);
		poseStack.pushPose();
		RenderSystem.enableBlend();
		GuiComponent.blit(poseStack, x, y, u, v, width, height);
		RenderSystem.disableBlend();
		poseStack.popPose();
		switch (this.targetText)
		{
			case CANCEL -> this.fontRenderer.draw(poseStack, this.buttonCancelText, x + ((this.width / 2) - (this.fontRenderer.width(this.buttonCancelText) / 2)), y + 3, 0x000000);
			case BACK -> this.fontRenderer.draw(poseStack, this.buttonBackText, x + ((this.width / 2) - (this.fontRenderer.width(this.buttonBackText) / 2)), y + 3, 0x000000);
		}
	}
	
	/**
	 * Gets called when the Button gets pressed
	 */
	private static void handleButtonPress()
	{
		switch (targetScreen)
		{
			case CHESS_BOARD_SETTINGS -> Minecraft.getInstance().setScreen(new ChessBoardSettingsScreen(chessBlockEntity));
			case CHESS_BOARD_COLORS -> Minecraft.getInstance().setScreen(new ChessColorSettingsScreen(chessBlockEntity));
		}
	}
	
	public enum ChessCancelMenuTarget
	{
		CHESS_BOARD_SETTINGS,
		CHESS_BOARD_COLORS;
	}
	
	public enum ChessCancelButtonText
	{
		CANCEL,
		BACK;
	}
}