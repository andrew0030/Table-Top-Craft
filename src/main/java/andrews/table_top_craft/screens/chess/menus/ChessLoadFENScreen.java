package andrews.table_top_craft.screens.chess.menus;

import andrews.table_top_craft.screens.chess.buttons.ChessCancelButton;
import andrews.table_top_craft.screens.chess.buttons.ChessCancelButton.ChessCancelButtonText;
import andrews.table_top_craft.screens.chess.buttons.ChessCancelButton.ChessCancelMenuTarget;
import andrews.table_top_craft.screens.chess.buttons.settings.ChessConfirmFENButton;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class ChessLoadFENScreen extends Screen
{
	private static final ResourceLocation MENU_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/small_chess_menu.png");
	private final String fenLoaderText = new TranslatableComponent("gui.table_top_craft.chess.fen_loader").getString();
	private final ChessTileEntity chessTileEntity;
	private EditBox fenStringField;
	private final int xSize = 177;
	private final int ySize = 85;
	
	public ChessLoadFENScreen(ChessTileEntity chessTileEntity)
	{
		super(new TextComponent(""));
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
		this.fenStringField = new EditBox(this.font, x + 5, y + 20, 167, 16, new TextComponent("FEN Field"));
		this.fenStringField.setMaxLength(100);
	    this.fenStringField.setFocus(true);
		
		// The Buttons in the Gui Menu
	    this.addRenderableWidget(new ChessCancelButton(this.chessTileEntity, ChessCancelMenuTarget.CHESS_BOARD_SETTINGS, ChessCancelButtonText.CANCEL, x + 5, y + 39));
	    this.addRenderableWidget(new ChessConfirmFENButton(this.chessTileEntity, fenStringField, x + 90, y + 39));
	}
	
	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
	{
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, MENU_TEXTURE);
		this.blit(poseStack, x, y, 0, 0, this.xSize, this.ySize);
		// The Menu Title
		this.font.draw(poseStack, this.fenLoaderText, ((this.width / 2) - (this.font.width(this.fenLoaderText) / 2)), y + 6, 4210752);
		// The FEN Text Box
		this.fenStringField.render(poseStack, mouseX, mouseY, partialTicks);
		// Renders the Buttons we added in init
		super.render(poseStack, mouseX, mouseY, partialTicks);
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