package andrews.table_top_craft.screens.chess.buttons.settings;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.util.NetworkUtil;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ChessShowAvailableMovesButton extends Button
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
	private static ChessBlockEntity chessBlockEntity;
	private static final int buttonWidth = 13;
	private static final int buttonHeight = 13;
	private int u = 0;
	private int v = 13;
	
	public ChessShowAvailableMovesButton(ChessBlockEntity tileEntity, int xPos, int yPos)
	{
		super(xPos, yPos, buttonWidth, buttonHeight, Component.literal(""), (button) -> { handleButtonPress(); }, DEFAULT_NARRATION);
		chessBlockEntity = tileEntity;
	}
	
	@Override
	public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
	{
		this.isHovered = mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height || this.isFocused();
		
		this.u = 0;
		if(chessBlockEntity.getShowAvailableMoves())
			this.u = 13;
		
		if(this.isHovered)
			this.u += 26;
		
		// Renders the Button
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, TEXTURE);
		poseStack.pushPose();
		RenderSystem.enableBlend();
		GuiComponent.blit(poseStack, x, y, u, v, width, height);
		RenderSystem.disableBlend();
		poseStack.popPose();
	}
	
	/**
	 * Gets called when the Button gets pressed
	 */
	private static void handleButtonPress()
	{
		NetworkUtil.showAvailableMovesMessage(chessBlockEntity.getBlockPos());
	}
}