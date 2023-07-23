package andrews.table_top_craft.screens.chess.buttons.colors;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
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

public class ChessBoardColorSettingsButton extends Button
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
	private final Component buttonText = Component.translatable("gui.table_top_craft.chess.button.colors");
	private final Font fontRenderer;
	private static ChessBlockEntity chessBlockEntity;
	private static final int buttonWidth = 24;
	private static final int buttonHeight = 24;
	private int u = 24;
	private int v = 26;
	
	public ChessBoardColorSettingsButton(ChessBlockEntity tileEntity, int xPos, int yPos)
	{
		super(xPos, yPos, buttonWidth, buttonHeight, Component.literal(""), (button) -> { handleButtonPress(); }, DEFAULT_NARRATION);
		this.fontRenderer = Minecraft.getInstance().font;
		chessBlockEntity = tileEntity;
	}
	
	@Override
	public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
	{
		this.active = !(Minecraft.getInstance().screen instanceof ChessColorSettingsScreen);

		this.isHovered = mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height || this.isFocused();
		
		// Renders the Button
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, TEXTURE);
		poseStack.pushPose();
		RenderSystem.enableBlend();
		GuiComponent.blit(poseStack, x, y, u, v, width, height);
		RenderSystem.disableBlend();
		poseStack.popPose();
		// This is used to render a tooltip
		if(isHovered)
			Minecraft.getInstance().screen.renderTooltip(poseStack, this.buttonText, x - (15 + this.fontRenderer.width(this.buttonText.getString())), y + 20);
	}
	
	/**
	 * Gets called when the Button gets pressed
	 */
	private static void handleButtonPress()
	{
		Minecraft.getInstance().setScreen(new ChessColorSettingsScreen(chessBlockEntity));
	}
}