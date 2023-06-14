package andrews.table_top_craft.screens.chess.menus;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece.PieceModelSet;
import andrews.table_top_craft.screens.chess.buttons.colors.ChessBoardColorSettingsButton;
import andrews.table_top_craft.screens.chess.buttons.pieces.ChessBoardPieceModelSelectionButton;
import andrews.table_top_craft.screens.chess.buttons.pieces.ChessBoardPieceSettingsButton;
import andrews.table_top_craft.screens.chess.buttons.settings.ChessBoardSettingsButton;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ChessPieceSelectionScreen extends Screen
{
    private static final ResourceLocation MENU_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/chess_menu.png");
    private static final ResourceLocation BUTTONS_TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
    private final String chessBoardColorsText = Component.translatable("gui.table_top_craft.chess.board_pieces").getString();
    private final ChessBlockEntity chessBlockEntity;
    private final int xSize = 177;
    private final int ySize = 198;
    private final boolean isStandardSetUnlocked;
    private final boolean isClassicSetUnlocked;
    private final boolean isPandorasCreaturesSetUnlocked;

    public ChessPieceSelectionScreen(ChessBlockEntity chessBlockEntity, boolean isStandardSetUnlocked, boolean isClassicSetUnlocked, boolean isPandorasCreaturesSetUnlocked)
    {
        super(Component.literal(""));
        this.chessBlockEntity = chessBlockEntity;
        this.isStandardSetUnlocked = isStandardSetUnlocked;
        this.isClassicSetUnlocked = isClassicSetUnlocked;
        this.isPandorasCreaturesSetUnlocked = isPandorasCreaturesSetUnlocked;
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
        this.addRenderableWidget(new ChessBoardSettingsButton(this.chessBlockEntity, x - 24, y + 16));
        this.addRenderableWidget(new ChessBoardColorSettingsButton(this.chessBlockEntity, x - 24, y + 42));
        this.addRenderableWidget(new ChessBoardPieceSettingsButton(this.chessBlockEntity, x - 24, y + 68));

        this.addRenderableWidget(new ChessBoardPieceModelSelectionButton(this.chessBlockEntity, PieceModelSet.STANDARD, isStandardSetUnlocked, isClassicSetUnlocked, isPandorasCreaturesSetUnlocked, x + 5, y + 30));
        this.addRenderableWidget(new ChessBoardPieceModelSelectionButton(this.chessBlockEntity, PieceModelSet.CLASSIC, isStandardSetUnlocked, isClassicSetUnlocked, isPandorasCreaturesSetUnlocked, x + 5, y + 84));
        this.addRenderableWidget(new ChessBoardPieceModelSelectionButton(this.chessBlockEntity, PieceModelSet.PANDORAS_CREATURES, isStandardSetUnlocked, isClassicSetUnlocked, isPandorasCreaturesSetUnlocked, x + 5, y + 138));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
    {
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, MENU_TEXTURE);
        GuiComponent.blit(poseStack, x, y, 0, 0, this.xSize, this.ySize);
        GuiComponent.blit(poseStack, x, y + 67, 0, 198, 3, 26);

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, BUTTONS_TEXTURE);
        GuiComponent.blit(poseStack, x + 25, y + (ySize - 21), 36, 89, 23, 16);
        GuiComponent.blit(poseStack, x + (xSize - 48), y + (ySize - 21), 36, 105, 23, 16);

        // Menu Text
        this.font.draw(poseStack, this.chessBoardColorsText, ((this.width / 2) - (this.font.width(this.chessBoardColorsText) / 2)), y + 6, 4210752);
        // Page Number Text
        this.font.draw(poseStack, Component.literal("1/1"), ((this.width / 2) - (this.font.width("1/1") / 2)), y + (ySize - 16), 4210752);

        // Renders the Buttons we added in init
        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        super.keyPressed(keyCode, scanCode, modifiers);
        InputConstants.Key mouseKey = InputConstants.getKey(keyCode, scanCode);
        if(this.minecraft.options.keyInventory.isActiveAndMatches(mouseKey))
            this.onClose();
        return true;
    }
}