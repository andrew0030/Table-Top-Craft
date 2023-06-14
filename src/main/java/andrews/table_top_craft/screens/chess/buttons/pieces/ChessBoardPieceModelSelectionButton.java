package andrews.table_top_craft.screens.chess.buttons.pieces;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.block_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece.PieceModelSet;
import andrews.table_top_craft.registry.TTCBlocks;
import andrews.table_top_craft.util.NetworkUtil;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;

public class ChessBoardPieceModelSelectionButton extends Button
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/piece_model_selection_buttons.png");
    private final Component buttonText = Component.translatable("tooltip.table_top_craft.chess.piece_type.standard");
    private final Component buttonTextClassic = Component.translatable("tooltip.table_top_craft.chess.piece_type.classic");
    private final Component buttonTextPandorasCreatures = Component.translatable("tooltip.table_top_craft.chess.piece_type.pandoras_creatures");
    private final Font fontRenderer;
    private static ChessBlockEntity chessBlockEntity;
    private static final int buttonWidth = 167;
    private static final int buttonHeight = 37;
    private int u = 0;
    private int v = 0;
    // Chess Pieces on the Button
    private final ChessPieceFigureBlockEntity chessPieceFigureBlockEntity;
    private final ItemStack chessPieceStack;
    // Piece Set
    private final PieceModelSet pieceModelSet;
    private final boolean isStandardSetUnlocked;
    private final boolean isClassicSetUnlocked;
    private final boolean isPandorasCreaturesSetUnlocked;

    public ChessBoardPieceModelSelectionButton(ChessBlockEntity tileEntity, PieceModelSet pieceModelSet, boolean isStandardSetUnlocked, boolean isClassicSetUnlocked, boolean isPandorasCreaturesSetUnlocked, int xPos, int yPos)
    {
        super(xPos, yPos, buttonWidth, buttonHeight, Component.literal(""), (button) -> { handleButtonPress(); }, DEFAULT_NARRATION);
        this.fontRenderer = Minecraft.getInstance().font;
        chessBlockEntity = tileEntity;
        chessPieceFigureBlockEntity = new ChessPieceFigureBlockEntity(BlockPos.ZERO, TTCBlocks.CHESS_PIECE_FIGURE.get().defaultBlockState());
        chessPieceStack = new ItemStack(TTCBlocks.CHESS_PIECE_FIGURE.get().asItem());
        this.pieceModelSet = pieceModelSet;
        /* this should probably be done with an array or smt but I cbb ~ andrew */
        this.isStandardSetUnlocked = isStandardSetUnlocked;
        this.isClassicSetUnlocked = isClassicSetUnlocked;
        this.isPandorasCreaturesSetUnlocked = isPandorasCreaturesSetUnlocked;
    }

    @Override
    public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
    {
        this.isHovered = mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height || this.isFocused();

        this.v = 0;
        if (this.isHovered && !shouldButtonBeLocked())
            this.v += 37;

        this.active = !shouldButtonBeLocked();

        // Renders the Button
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        poseStack.pushPose();
        RenderSystem.enableBlend();
        GuiComponent.blit(poseStack, x, y, u, v, width, height);
        switch (pieceModelSet)
        {
            case STANDARD:
                if (chessBlockEntity.getPieceSet() == 0)
                    this.blit(poseStack, x - 1, y - 1, 0, 74, width + 2, height + 2);
                break;
            case CLASSIC:
                if (chessBlockEntity.getPieceSet() == 1)
                    this.blit(poseStack, x - 1, y - 1, 0, 74, width + 2, height + 2);
                break;
            case PANDORAS_CREATURES:
                if (chessBlockEntity.getPieceSet() == 2)
                    this.blit(poseStack, x - 1, y - 1, 0, 74, width + 2, height + 2);
        }
        RenderSystem.disableBlend();
        poseStack.popPose();

        switch (pieceModelSet)
        {
            case STANDARD -> chessPieceFigureBlockEntity.setPieceSet(1);
            case CLASSIC -> chessPieceFigureBlockEntity.setPieceSet(2);
            case PANDORAS_CREATURES -> chessPieceFigureBlockEntity.setPieceSet(3);
        }
        chessPieceFigureBlockEntity.setRotateChessPieceFigure(true);
        int scale = 32;
        for (int i = 0; i < 6; i++)
        {
            chessPieceFigureBlockEntity.setPieceType(i + 1);
            chessPieceFigureBlockEntity.saveToItem(chessPieceStack);
            renderChessPiece(poseStack, chessPieceStack, x + 16 + (27 * i), y + 16, scale);
        }

        switch (pieceModelSet)
        {
            case STANDARD -> Minecraft.getInstance().screen.renderTooltip(poseStack, Arrays.asList(this.buttonText.getVisualOrderText()), x - 8 + ((this.width / 2) - ((this.fontRenderer.width(this.buttonText) + 8) / 2)), y + 1, this.fontRenderer);
            case CLASSIC -> Minecraft.getInstance().screen.renderTooltip(poseStack, Arrays.asList(this.buttonTextClassic.getVisualOrderText()), x - 8 + ((this.width / 2) - ((this.fontRenderer.width(this.buttonTextClassic) + 8) / 2)), y + 1, this.fontRenderer);
            case PANDORAS_CREATURES -> Minecraft.getInstance().screen.renderTooltip(poseStack, Arrays.asList(this.buttonTextPandorasCreatures.getVisualOrderText()), x - 8 + ((this.width / 2) - ((this.fontRenderer.width(this.buttonTextPandorasCreatures) + 8) / 2)), y + 1, this.fontRenderer);
        }

        if (shouldButtonBeLocked())
        {
            RenderSystem.enableBlend();
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 0.85f);
            RenderSystem.setShaderTexture(0, TEXTURE);
            GuiComponent.blit(poseStack, x + 1, y + 11, 0, 113, 165, 15);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.disableBlend();

            poseStack.pushPose();
            poseStack.translate(0, 0, 200);
            this.fontRenderer.draw(poseStack, Component.literal("Locked"), x + (this.width / 4) - (this.fontRenderer.width("Locked") / 2), y + 15, 0xffffff);
            this.fontRenderer.draw(poseStack, Component.literal("Locked"), x + ((this.width / 4) * 3) - (this.fontRenderer.width("Locked") / 2), y + 15, 0xffffff);
            poseStack.popPose();
        }
    }

    private void renderChessPiece(PoseStack poseStack, ItemStack itemStack, int pX, int pY, int size)
    {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        BakedModel itemBakedModel = itemRenderer.getModel(itemStack, null, null, 0);
        Minecraft.getInstance().getTextureManager().getTexture(TextureAtlas.LOCATION_BLOCKS).setFilter(false, false);
        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.pushPose();
        poseStack.translate(pX, pY, 100.0F);
        poseStack.scale(1.0F, -1.0F, 1.0F);
        poseStack.scale(size, size, size);
        RenderSystem.applyModelViewMatrix();
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        Lighting.setupForFlatItems();
        itemRenderer.render(itemStack, ItemDisplayContext.GUI, false, poseStack, bufferSource, 15728880, OverlayTexture.NO_OVERLAY, itemBakedModel);
        bufferSource.endBatch();
        poseStack.popPose();
        RenderSystem.applyModelViewMatrix();
    }

    @Override
    public void onClick(double pMouseX, double pMouseY)
    {
        switch (pieceModelSet)
        {
            case STANDARD -> NetworkUtil.setChessPieceSet(chessBlockEntity.getBlockPos(), 0);
            case CLASSIC -> NetworkUtil.setChessPieceSet(chessBlockEntity.getBlockPos(), 1);
            case PANDORAS_CREATURES -> NetworkUtil.setChessPieceSet(chessBlockEntity.getBlockPos(), 2);
        }
    }

    /**
     * Gets called when the Button gets pressed
     */
    private static void handleButtonPress() {}

    private boolean shouldButtonBeLocked()
    {
        if(!Minecraft.getInstance().player.isCreative())
        {
            // If the player is in survival we lock the Button based on
            // whether the player has the given Set unlocked
            return switch (this.pieceModelSet)
            {
                case STANDARD -> !this.isStandardSetUnlocked;
                case CLASSIC -> !this.isClassicSetUnlocked;
                case PANDORAS_CREATURES -> !this.isPandorasCreaturesSetUnlocked;
            };
        }
        // If the player is in Creative the Buttons should be unlocked
        return false;
    }
}