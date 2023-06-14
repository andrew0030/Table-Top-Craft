package andrews.table_top_craft.screens.chess.menus;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.screens.chess.buttons.pieces.ChessBoardPawnPromotionButton;
import andrews.table_top_craft.screens.chess.buttons.pieces.ChessBoardPawnPromotionButton.PawnPromotionPieceType;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

public class ChessPawnPromotionScreen extends Screen
{
    private static final ResourceLocation MENU_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/chess_pawn_promotion_menu.png");
    private static final Component TITLE = Component.translatable("gui.table_top_craft.chess.pawn_promotion.title");
    private static final int X_SIZE = 193;
    private static final int Y_SIZE = 71;
    private final ChessBlockEntity blockEntity;
    private final boolean isWhite;
    private int xPos;
    private int yPos;


    public ChessPawnPromotionScreen(ChessBlockEntity blockEntity, boolean isWhite)
    {
        super(TITLE);
        this.blockEntity = blockEntity;
        this.isWhite = isWhite;
    }

    @Override
    protected void init()
    {
        this.xPos = (this.width - X_SIZE) / 2;
        this.yPos = (this.height - Y_SIZE) / 2;

        this.addRenderableWidget(new ChessBoardPawnPromotionButton(this.blockEntity, xPos + 6, yPos + 19, isWhite, PawnPromotionPieceType.QUEEN));
        this.addRenderableWidget(new ChessBoardPawnPromotionButton(this.blockEntity, xPos + 52, yPos + 19, isWhite, PawnPromotionPieceType.BISHOP));
        this.addRenderableWidget(new ChessBoardPawnPromotionButton(this.blockEntity, xPos + 98, yPos + 19, isWhite, PawnPromotionPieceType.KNIGHT));
        this.addRenderableWidget(new ChessBoardPawnPromotionButton(this.blockEntity, xPos + 144, yPos + 19, isWhite, PawnPromotionPieceType.ROOK));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
    {
        // Background
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, MENU_TEXTURE);
        GuiComponent.blit(poseStack, this.xPos, this.yPos, 0, 0, X_SIZE, Y_SIZE);
        // Title
        drawCenteredNoShadow(poseStack, TITLE, this.width / 2, this.yPos + 6, 4210752);



        // Renders Buttons added in init()
        super.render(poseStack, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
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

    public static void drawCenteredNoShadow(PoseStack poseStack, Component text, int x, int y, int color)
    {
        FormattedCharSequence formattedcharsequence = text.getVisualOrderText();
        Font font = Minecraft.getInstance().font;
        font.draw(poseStack, formattedcharsequence, (float)(x - font.width(formattedcharsequence) / 2), (float)y, color);
    }

    /**
     * Used to open this Gui, because class loading is a little child that screams if it does not like you
     */
    public static void open(ChessBlockEntity blockEntity, boolean isWhite)
    {
        Minecraft.getInstance().setScreen(new ChessPawnPromotionScreen(blockEntity, isWhite));
    }
}