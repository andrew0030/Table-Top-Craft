package andrews.table_top_craft.screens.chess.menus;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece.PieceModelSet;
import andrews.table_top_craft.screens.base.BaseScreen;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessBoardColorSettingsButton;
import andrews.table_top_craft.screens.chess.buttons.pieces.ChessBoardPieceModelSelectionButton;
import andrews.table_top_craft.screens.chess.buttons.pieces.ChessBoardPieceSettingsButton;
import andrews.table_top_craft.screens.chess.buttons.settings.ChessBoardSettingsButton;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ChessPieceSelectionScreen extends BaseScreen
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/chess_menu.png");
    private static final ResourceLocation BUTTONS_TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
    private static final Component TITLE = Component.translatable("gui.table_top_craft.chess.board_pieces");
    private final ChessBlockEntity chessBlockEntity;
    private final boolean isStandardSetUnlocked;
    private final boolean isClassicSetUnlocked;
    private final boolean isPandorasCreaturesSetUnlocked;

    public ChessPieceSelectionScreen(ChessBlockEntity chessBlockEntity, boolean isStandardSetUnlocked, boolean isClassicSetUnlocked, boolean isPandorasCreaturesSetUnlocked)
    {
        super(TEXTURE, 177, 198, TITLE);
        this.chessBlockEntity = chessBlockEntity;
        this.isStandardSetUnlocked = isStandardSetUnlocked;
        this.isClassicSetUnlocked = isClassicSetUnlocked;
        this.isPandorasCreaturesSetUnlocked = isPandorasCreaturesSetUnlocked;
    }

    @Override
    protected void init()
    {
        super.init();
        // The Page Selection Buttons in the Gui Menu
        this.addRenderableWidget(new ChessBoardSettingsButton(this.chessBlockEntity, this.x - 24, this.y + 16));
        this.addRenderableWidget(new ChessBoardColorSettingsButton(this.chessBlockEntity, this.x - 24, this.y + 42));
        this.addRenderableWidget(new ChessBoardPieceSettingsButton(this.chessBlockEntity, this.x - 24, this.y + 68));
        // The Piece Type Selection Buttons
        this.addRenderableWidget(new ChessBoardPieceModelSelectionButton(this.chessBlockEntity, PieceModelSet.STANDARD, isStandardSetUnlocked, isClassicSetUnlocked, isPandorasCreaturesSetUnlocked, this.x + 5, this.y + 30));
        this.addRenderableWidget(new ChessBoardPieceModelSelectionButton(this.chessBlockEntity, PieceModelSet.CLASSIC, isStandardSetUnlocked, isClassicSetUnlocked, isPandorasCreaturesSetUnlocked, this.x + 5, this.y + 84));
        this.addRenderableWidget(new ChessBoardPieceModelSelectionButton(this.chessBlockEntity, PieceModelSet.PANDORAS_CREATURES, isStandardSetUnlocked, isClassicSetUnlocked, isPandorasCreaturesSetUnlocked, this.x + 5, this.y + 138));
    }

    @Override
    public void renderScreenContents(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        // Renders a bar over the side of the panel to make it seamless
        graphics.blit(TEXTURE, this.x, this.y + 67, 0, 198, 3, 26);
        // Page Buttons
        graphics.blit(BUTTONS_TEXTURE, this.x + 25, this.y + (this.textureHeight - 21), 36, 89, 23, 16);
        graphics.blit(BUTTONS_TEXTURE, this.x + (this.textureWidth - 48), this.y + (this.textureHeight - 21), 36, 105, 23, 16);
        // Menu Title
        this.drawCenteredString(TITLE, this.width / 2, this.y + 6, 4210752, false, graphics);
        // Page Number Text
        this.drawCenteredString(Component.literal("1/1"), this.width / 2, this.y + (this.textureHeight - 16), 4210752, false, graphics);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        super.keyPressed(keyCode, scanCode, modifiers);
        if(this.getMinecraft().options.keyInventory.isActiveAndMatches(InputConstants.getKey(keyCode, scanCode)))
            this.onClose();
        return true;
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }
}