package andrews.table_top_craft.screens.chess.buttons.settings;

import andrews.table_top_craft.screens.chess.menus.ChessBoardSettingsScreen;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ChessMoveLogDownButton extends Button
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
	private static final int buttonWidth = 12;
	private static final int buttonHeight = 23;
	private int u = 0;
	private int v = 89;
	private static ChessBoardSettingsScreen screen;
	
	public ChessMoveLogDownButton(int xPos, int yPos, ChessBoardSettingsScreen currentScreen) 
	{
		super(xPos, yPos, buttonWidth, buttonHeight, Component.literal(""), (button) -> { handleButtonPress(); }, DEFAULT_NARRATION);
		screen = currentScreen;
	}
	
	@Override
	public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
	{
		this.isHovered = mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height || this.isFocused();
		
		this.u = 0;
		if(this.isHovered)
			this.u += 12;
		
		this.active = true;
		if(screen.getMoveLogOffset() <= 0)
		{
			this.active = false;
			this.u = 24;
		}
		
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
		screen.decreaseMoveLogOffset();
	}
}