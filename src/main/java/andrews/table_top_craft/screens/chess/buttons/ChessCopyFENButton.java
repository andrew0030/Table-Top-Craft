package andrews.table_top_craft.screens.chess.buttons;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import andrews.table_top_craft.game_logic.chess.pgn.FenUtil;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class ChessCopyFENButton extends Button
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
	private final String buttonText = new TranslationTextComponent("gui.table_top_craft.chess.copy_fen").getString();
	private final FontRenderer fontRenderer;
	private static BlockPos chessTablePosition;
	private static final int buttonWidth = 82;
	private static final int buttonHeight = 13;
	private int u = 0;
	private int v = 0;
	
	public ChessCopyFENButton(BlockPos pos, int xPos, int yPos) 
	{
		super(xPos, yPos, buttonWidth, buttonHeight, new StringTextComponent(""), (button) -> { handleButtonPress(); });
		this.fontRenderer = Minecraft.getInstance().fontRenderer;
		chessTablePosition = pos;
	}
	
	@Override
	public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		this.active = true;
		if(Minecraft.getInstance().world.getTileEntity(chessTablePosition) instanceof ChessTileEntity)
		{
			ChessTileEntity chessTileEntity = (ChessTileEntity) Minecraft.getInstance().world.getTileEntity(chessTablePosition);
			if(chessTileEntity.getBoard() == null)
				this.active = false;
		}
		
		this.isHovered = false;
		if(mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height || this.isFocused())
			this.isHovered = true;
		
		this.u = 0;
		if(this.isHovered)
			this.u = 82;
		
		//Renders the Button
		Minecraft.getInstance().getRenderManager().textureManager.bindTexture(TEXTURE);
		matrixStack.push();
		RenderSystem.enableBlend();
		GuiUtils.drawTexturedModalRect(matrixStack, x, y, u, v, width, height, 0);
		RenderSystem.disableBlend();
		matrixStack.pop();
		this.fontRenderer.drawString(matrixStack, this.buttonText, x + ((this.width / 2) - (this.fontRenderer.getStringWidth(this.buttonText) / 2)), y + 3, 0x000000);
	}
	
	/**
	 * Gets called when the Button gets pressed
	 */
	private static void handleButtonPress()
	{
		ChessTileEntity chessTileEntity = (ChessTileEntity) Minecraft.getInstance().world.getTileEntity(chessTablePosition);
		if(chessTileEntity.getBoard() != null)
		{
			String FENString = FenUtil.createFENFromGame(chessTileEntity.getBoard());
			Minecraft.getInstance().keyboardListener.setClipboardString(FENString);
		}
	}
}
