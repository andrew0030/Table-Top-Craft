package andrews.table_top_craft.screens.chess.menus;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.screens.base.BaseScreen;
import andrews.table_top_craft.screens.chess.buttons.pieces.ChessBoardPawnPromotionButton;
import andrews.table_top_craft.screens.chess.buttons.pieces.ChessBoardPawnPromotionButton.PawnPromotionPieceType;
import andrews.table_top_craft.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ChessPawnPromotionScreen extends BaseScreen
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/chess_pawn_promotion_menu.png");
    private static final Component TITLE = Component.translatable("gui.table_top_craft.chess.pawn_promotion.title");
    private final ChessBlockEntity blockEntity;
    private final boolean isWhite;

    public ChessPawnPromotionScreen(ChessBlockEntity blockEntity, boolean isWhite)
    {
        super(TEXTURE, 193, 71, TITLE);
        this.blockEntity = blockEntity;
        this.isWhite = isWhite;
    }

    @Override
    protected void init()
    {
        super.init();
        // Piece Selection Buttons for Promotion
        this.addRenderableWidget(new ChessBoardPawnPromotionButton(this.blockEntity, this.x + 6, this.y + 19, this.isWhite, PawnPromotionPieceType.QUEEN));
        this.addRenderableWidget(new ChessBoardPawnPromotionButton(this.blockEntity, this.x + 52, this.y + 19, this.isWhite, PawnPromotionPieceType.BISHOP));
        this.addRenderableWidget(new ChessBoardPawnPromotionButton(this.blockEntity, this.x + 98, this.y + 19, this.isWhite, PawnPromotionPieceType.KNIGHT));
        this.addRenderableWidget(new ChessBoardPawnPromotionButton(this.blockEntity, this.x + 144, this.y + 19, this.isWhite, PawnPromotionPieceType.ROOK));
    }

    @Override
    public void renderScreenContents(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        // Title
        this.drawCenteredString(TITLE, this.width / 2, this.y + 6, 4210752, false, graphics);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        super.keyPressed(keyCode, scanCode, modifiers);
        if(this.minecraft.options.keyInventory.matches(keyCode, scanCode))
            this.onClose();
        return true;
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }

    /**
     * Used to open this Gui, because class loading is a little child that screams if it does not like you
     */
    public static void open(ChessBlockEntity blockEntity, boolean isWhite)
    {
        Minecraft.getInstance().setScreen(new ChessPawnPromotionScreen(blockEntity, isWhite));
    }
}