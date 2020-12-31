package andrews.table_top_craft.screens.chess.buttons;

import java.util.Arrays;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import andrews.table_top_craft.screens.chess.menus.ChessBoardSettingsScreen;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class ChessBoardSettingsButton extends Button
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
	private final TranslationTextComponent buttonText = new TranslationTextComponent("gui.table_top_craft.chess.button.settings");
	private final FontRenderer fontRenderer;
	private static ChessTileEntity chessTileEntity;
	private static final int buttonWidth = 24;
	private static final int buttonHeight = 24;
	private int u = 0;
	private int v = 26;
	
	public ChessBoardSettingsButton(ChessTileEntity tileEntity, int xPos, int yPos) 
	{
		super(xPos, yPos, buttonWidth, buttonHeight, new StringTextComponent(""), (button) -> { handleButtonPress(); });
		this.fontRenderer = Minecraft.getInstance().fontRenderer;
		chessTileEntity = tileEntity;
	}
	
	@Override
	public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		this.active = true;
		if(Minecraft.getInstance().currentScreen instanceof ChessBoardSettingsScreen)
			this.active = false;
		
		this.isHovered = false;
		if(mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height || this.isFocused())
			this.isHovered = true;
		
		//Renders the Button
		Minecraft.getInstance().getRenderManager().textureManager.bindTexture(TEXTURE);
		matrixStack.push();
		RenderSystem.enableBlend();
		GuiUtils.drawTexturedModalRect(matrixStack, x, y, u, v, width, height, 0);
		RenderSystem.disableBlend();
		matrixStack.pop();
		//This is used to render a tooltip
		if(isHovered)
			Minecraft.getInstance().currentScreen.renderToolTip(matrixStack, Arrays.asList(this.buttonText.func_241878_f()), x - (15 + this.fontRenderer.getStringWidth(this.buttonText.getString())), y + 20, this.fontRenderer);
	}
	
	/**
	 * Gets called when the Button gets pressed
	 */
	private static void handleButtonPress()
	{
		Minecraft.getInstance().displayGuiScreen(new ChessBoardSettingsScreen(chessTileEntity));
	}
}
