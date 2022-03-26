package andrews.table_top_craft.screens.chess.menus.color_selection;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;

import andrews.table_top_craft.screens.chess.buttons.ChessCancelButton;
import andrews.table_top_craft.screens.chess.buttons.ChessCancelButton.ChessCancelButtonText;
import andrews.table_top_craft.screens.chess.buttons.ChessCancelButton.ChessCancelMenuTarget;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessConfirmColorButton;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessConfirmColorButton.ColorMenuType;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessRandomColorButton;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessResetColorButton;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessResetColorButton.DefaultColorType;
import andrews.table_top_craft.screens.chess.sliders.ChessBlueColorSlider;
import andrews.table_top_craft.screens.chess.sliders.ChessGreenColorSlider;
import andrews.table_top_craft.screens.chess.sliders.ChessRedColorSlider;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.NBTColorSaving;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class ChessTileInfoColorScreen extends Screen
{
	private static final ResourceLocation MENU_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/medium_chess_menu.png");
	private static final ResourceLocation PREVIEW_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/preview_color.png");
	private final String colorSelectionText = new TranslatableComponent("gui.table_top_craft.chess.color.tile_info").getString();
	private final String previewColorText = new TranslatableComponent("gui.table_top_craft.chess.color.color_preview").getString();
	private final ChessTileEntity chessTileEntity;
	private final int xSize = 177;
	private final int ySize = 131;
	private ChessRedColorSlider redColorSlider;
	private ChessGreenColorSlider greenColorSlider;
	private ChessBlueColorSlider blueColorSlider;
	
	public ChessTileInfoColorScreen(ChessTileEntity chessTileEntity)
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
	protected void init()
	{
		super.init();
		// Values to calculate the relative position
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

	    // Sliders 
		// NOTE: We have to load the Buttons first because we use them in one of the Sliders
	    this.addRenderableWidget(this.redColorSlider = new ChessRedColorSlider(x + 5, y + 74, 167, 12, NBTColorSaving.getRed(this.chessTileEntity.getTileInfoColor())));
	    this.addRenderableWidget(this.greenColorSlider = new ChessGreenColorSlider(x + 5, y + 87, 167, 12, NBTColorSaving.getGreen(this.chessTileEntity.getTileInfoColor())));
	    this.addRenderableWidget(this.blueColorSlider = new ChessBlueColorSlider(x + 5, y + 100, 167, 12, NBTColorSaving.getBlue(this.chessTileEntity.getTileInfoColor())));
		
		// The Buttons in the Gui Menu
	    this.addRenderableWidget(new ChessRandomColorButton(this.redColorSlider, this.greenColorSlider, this.blueColorSlider, x + 69, y + 33));
	    this.addRenderableWidget(new ChessResetColorButton(DefaultColorType.TILE_INFO_COLOR, this.redColorSlider, this.greenColorSlider, this.blueColorSlider, x + 69, y + 47));
	    this.addRenderableWidget(new ChessCancelButton(this.chessTileEntity, ChessCancelMenuTarget.CHESS_BOARD_COLORS, ChessCancelButtonText.CANCEL, x + 5, y + 113));
	    this.addRenderableWidget(new ChessConfirmColorButton(ColorMenuType.TILE_INFO, this.chessTileEntity, this.redColorSlider, this.greenColorSlider, this.blueColorSlider, x + 90, y + 113));
	}
	
	//@SuppressWarnings("deprecation")
	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
	{
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, MENU_TEXTURE);
		this.blit(poseStack, x, y, 0, 0, this.xSize, this.ySize);
		
		// The Preview of the Color
		this.blit(poseStack, x + 5, y + 18, 0, 131, 42, 42);
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, PREVIEW_TEXTURE);
		poseStack.pushPose();
		//RenderSystem.color3f((1F / 255F) * this.redColorSlider.getValueInt(), (1F / 255F) * this.greenColorSlider.getValueInt(), (1F / 255F) * this.blueColorSlider.getValueInt());
		// TODO Make sure this has the same effect
		RenderSystem.setShaderColor((1F / 255F) * this.redColorSlider.getValueInt(), (1F / 255F) * this.greenColorSlider.getValueInt(), (1F / 255F) * this.blueColorSlider.getValueInt(), 1.0F);
		poseStack.translate(x + 6, y + 19, 0);
		poseStack.scale(2.5F, 2.5F, 2.5F);
		this.blit(poseStack, 0, 0, 0, 0, 16, 16);
		//RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		// TODO Make sure this has the same effect
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		poseStack.popPose();
		
		// The Menu Title
		this.font.draw(poseStack, this.colorSelectionText, ((this.width / 2) - (this.font.width(this.colorSelectionText) / 2)), y + 6, 4210752);
		// The preview String
		this.font.draw(poseStack, this.previewColorText, x + 49, y + 18, 0x000000);
		// Renders the Buttons we added in init
		super.render(poseStack, mouseX, mouseY, partialTicks);
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers)
	{
		super.keyPressed(keyCode, scanCode, modifiers);
		InputConstants.Key mouseKey = InputConstants.getKey(keyCode, scanCode);
		if(this.minecraft.options.keyInventory.isActiveAndMatches(mouseKey))
			this.onClose();//TODO make sure this works
		return true;
	}
}