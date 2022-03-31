package andrews.table_top_craft.screens.chess.menus;

import andrews.table_top_craft.screens.chess.buttons.colors.ChessBoardAttackMoveColorButton;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessBoardCastleMoveColorButton;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessBoardColorSettingsButton;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessBoardInvalidMoveColorButton;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessBoardLegalMoveColorButton;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessBoardPieceColorsButton;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessBoardPreviousMoveColorButton;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessBoardTilesColorButton;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessTileInfoColorsButton;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessUseCustomPlateButton;
import andrews.table_top_craft.screens.chess.buttons.pieces.ChessBoardPieceSettingsButton;
import andrews.table_top_craft.screens.chess.buttons.settings.ChessBoardSettingsButton;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class ChessPieceSelectionScreen extends Screen
{
    private static final ResourceLocation MENU_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/chess_menu.png");
    private final String chessBoardColorsText = new TranslatableComponent("gui.table_top_craft.chess.board_pieces").getString();
    private final ChessTileEntity chessTileEntity;
    private final int xSize = 177;
    private final int ySize = 198;

    public ChessPieceSelectionScreen(ChessTileEntity chessTileEntity)
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
        // The Buttons in the Gui Menu
        this.addRenderableWidget(new ChessBoardSettingsButton(this.chessTileEntity, x - 24, y + 16));
        this.addRenderableWidget(new ChessBoardColorSettingsButton(this.chessTileEntity, x - 24, y + 42));
        this.addRenderableWidget(new ChessBoardPieceSettingsButton(this.chessTileEntity, x - 24, y + 68));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
    {
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, MENU_TEXTURE);
        this.blit(poseStack, x, y, 0, 0, this.xSize, this.ySize);
        this.blit(poseStack, x, y + 67, 0, 198, 3, 26);

        this.font.draw(poseStack, this.chessBoardColorsText, ((this.width / 2) - (this.font.width(this.chessBoardColorsText) / 2)), y + 6, 4210752);

        // Renders the Buttons we added in init
        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        super.keyPressed(keyCode, scanCode, modifiers);
        InputConstants.Key mouseKey = InputConstants.getKey(keyCode, scanCode);
        if(this.minecraft.options.keyInventory.isActiveAndMatches(mouseKey))
            this.onClose();//TODO check if it works
        return true;
    }
}