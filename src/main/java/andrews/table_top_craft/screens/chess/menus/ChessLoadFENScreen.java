package andrews.table_top_craft.screens.chess.menus;

import com.mojang.blaze3d.matrix.MatrixStack;

import andrews.table_top_craft.screens.chess.buttons.ChessCancelButton;
import andrews.table_top_craft.screens.chess.buttons.ChessCancelButton.ChessCancelButtonText;
import andrews.table_top_craft.screens.chess.buttons.ChessCancelButton.ChessCancelMenuTarget;
import andrews.table_top_craft.screens.chess.buttons.settings.ChessConfirmFENButton;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.Reference;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChessLoadFENScreen extends Screen
{
	private static final ResourceLocation MENU_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/small_chess_menu.png");
	private final String fenLoaderText = new TranslationTextComponent("gui.table_top_craft.chess.fen_loader").getString();
	private ChessTileEntity chessTileEntity;
	private TextFieldWidget fenStringField;
	private final int xSize = 177;
	private final int ySize = 85;
	
	public ChessLoadFENScreen(ChessTileEntity chessTileEntity)
	{
		super(new StringTextComponent(""));
		this.chessTileEntity = chessTileEntity;
	}
	
	@Override
	public boolean isPauseScreen()
	{
		return false;
	}
	
	@Override
	public void tick()
	{
		this.fenStringField.tick();
	}
	
	@Override
	protected void init()
	{
		super.init();
		// Values to calculate the relative position
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		// The Text Field in which the Player enters the FEN String
		this.fenStringField = new TextFieldWidget(this.font, x + 5, y + 20, 167, 16, new StringTextComponent("FEN Field"));
		this.fenStringField.setMaxStringLength(100);
	    this.fenStringField.setFocused2(true);
		
		// The Buttons in the Gui Menu
	    this.addButton(new ChessCancelButton(this.chessTileEntity, ChessCancelMenuTarget.CHESS_BOARD_SETTINGS, ChessCancelButtonText.CANCEL, x + 5, y + 39));
	    this.addButton(new ChessConfirmFENButton(this.chessTileEntity, fenStringField, x + 90, y + 39));
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		
		this.minecraft.getTextureManager().bindTexture(MENU_TEXTURE);
		this.blit(matrixStack, x, y, 0, 0, this.xSize, this.ySize);
		// The Menu Title
		this.font.drawString(matrixStack, this.fenLoaderText, ((this.width / 2) - (this.font.getStringWidth(this.fenLoaderText) / 2)), y + 6, 4210752);
		// The FEN Text Box
		this.fenStringField.render(matrixStack, mouseX, mouseY, partialTicks);
		// Renders the Buttons we added in init
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public boolean charTyped(char codePoint, int modifiers)
	{
		this.fenStringField.charTyped(codePoint, modifiers);
		return super.charTyped(codePoint, modifiers);
	}
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers)
	{
		fenStringField.keyPressed(keyCode, scanCode, modifiers);
		return super.keyPressed(keyCode, scanCode, modifiers);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button)
	{
		this.fenStringField.mouseClicked(mouseX, mouseY, button);
		return super.mouseClicked(mouseX, mouseY, button);
	}
}