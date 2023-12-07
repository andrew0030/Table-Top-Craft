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
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
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

import java.util.HashMap;

public class ChessBoardPieceModelSelectionButton extends AbstractButton
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/piece_model_selection_buttons.png");
    private static final Component DEFAULT_BTN_TXT = Component.translatable("tooltip.table_top_craft.chess.piece_type.standard");
    private static final Component CLASSIC_BTN_TXT = Component.translatable("tooltip.table_top_craft.chess.piece_type.classic");
    private static final Component PANDORAS_BTN_TXT = Component.translatable("tooltip.table_top_craft.chess.piece_type.pandoras_creatures");
    private final ChessBlockEntity blockEntity;
    // Chess Pieces on the Button
    private final ChessPieceFigureBlockEntity chessPieceFigureBlockEntity;
    private final ItemStack chessPieceStack;
    // Piece Set on Button
    private final PieceModelSet pieceModelSet;
    private final HashMap<PieceModelSet, Boolean> pieceSets = new HashMap<>();

    public ChessBoardPieceModelSelectionButton(ChessBlockEntity blockEntity, PieceModelSet pieceModelSet, boolean standardUnlocked, boolean classicUnlocked, boolean pandorasUnlocked, int pX, int pY)
    {
        super(pX, pY, 167, 37, Component.literal(""));
        this.blockEntity = blockEntity;
        this.chessPieceFigureBlockEntity = new ChessPieceFigureBlockEntity(BlockPos.ZERO, TTCBlocks.CHESS_PIECE_FIGURE.defaultBlockState());
        this.chessPieceStack = new ItemStack(TTCBlocks.CHESS_PIECE_FIGURE.asItem());
        this.pieceModelSet = pieceModelSet;
        this.pieceSets.put(PieceModelSet.STANDARD, standardUnlocked);
        this.pieceSets.put(PieceModelSet.CLASSIC, classicUnlocked);
        this.pieceSets.put(PieceModelSet.PANDORAS_CREATURES, pandorasUnlocked);
        this.active = !this.shouldButtonBeLocked();
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        int u = 0;
        int v = (this.isHoveredOrFocused() && !this.shouldButtonBeLocked()) ? 37 : 0;
        // Renders the Button
        graphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableBlend();
        graphics.blit(TEXTURE, this.x, this.y, u, v, this.width, this.height);
        // Renders a highlight around the current selected Button
        switch (pieceModelSet)
        {
            case STANDARD -> {
                if (this.blockEntity.getPieceSet() == 0)
                    graphics.blit(TEXTURE, x - 1, y - 1, 0, 74, width + 2, height + 2);
            }
            case CLASSIC -> {
                if (this.blockEntity.getPieceSet() == 1)
                    graphics.blit(TEXTURE, x - 1, y - 1, 0, 74, width + 2, height + 2);
            }
            case PANDORAS_CREATURES -> {
                if (this.blockEntity.getPieceSet() == 2)
                    graphics.blit(TEXTURE, x - 1, y - 1, 0, 74, width + 2, height + 2);
            }
        }
        RenderSystem.disableBlend();

        // We set the Piece Set and make the Figure rotate
        switch (this.pieceModelSet)
        {
            case STANDARD -> {
                graphics.renderTooltip(Minecraft.getInstance().font, DEFAULT_BTN_TXT, this.x - 8 + ((this.width / 2) - ((Minecraft.getInstance().font.width(DEFAULT_BTN_TXT) + 8) / 2)), this.y + 1);
                this.chessPieceFigureBlockEntity.setPieceSet(1);
            }
            case CLASSIC -> {
                graphics.renderTooltip(Minecraft.getInstance().font, CLASSIC_BTN_TXT, this.x - 8 + ((this.width / 2) - ((Minecraft.getInstance().font.width(CLASSIC_BTN_TXT) + 8) / 2)), this.y + 1);
                this.chessPieceFigureBlockEntity.setPieceSet(2);
            }
            case PANDORAS_CREATURES -> {
                graphics.renderTooltip(Minecraft.getInstance().font, PANDORAS_BTN_TXT, this.x - 8 + ((this.width / 2) - ((Minecraft.getInstance().font.width(PANDORAS_BTN_TXT) + 8) / 2)), this.y + 1);
                this.chessPieceFigureBlockEntity.setPieceSet(3);
            }
        }
        this.chessPieceFigureBlockEntity.setRotateChessPieceFigure(true);

        // Swap through all the Piece Types
        int scale = 32;
        for (int i = 0; i < 6; i++)
        {
            this.chessPieceFigureBlockEntity.setPieceType(i + 1);
            this.chessPieceFigureBlockEntity.saveToItem(this.chessPieceStack);
            this.renderChessPiece(graphics.pose(), this.chessPieceStack, this.x + 16 + (27 * i), this.y + 16, scale);
        }

        // Renders all the Locked Button features over the Button if needed
        if (this.shouldButtonBeLocked())
        {
            // Renders the locked bar over the Button
            RenderSystem.enableBlend();
            graphics.setColor(1.0f, 1.0f, 1.0f, 0.85f);
            graphics.blit(TEXTURE, this.x + 1, this.y + 11, 0, 113, 165, 15);
            graphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.disableBlend();
            // Renders the "Locked" text over the Button
            graphics.pose().pushPose();
            graphics.pose().translate(0, 0, 200);
            Component text = Component.literal("Locked");
            graphics.drawString(Minecraft.getInstance().font, text, this.x + (this.width / 4) - (Minecraft.getInstance().font.width(text) / 2), this.y + 15, 0xffffff, false);
            graphics.drawString(Minecraft.getInstance().font, text, this.x + ((this.width / 4) * 3) - (Minecraft.getInstance().font.width(text) / 2), this.y + 15, 0xffffff, false);
            graphics.pose().popPose();
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
    public void onPress()
    {
        switch (pieceModelSet)
        {
            case STANDARD -> NetworkUtil.setChessPieceSet(this.blockEntity.getBlockPos(), 0);
            case CLASSIC -> NetworkUtil.setChessPieceSet(this.blockEntity.getBlockPos(), 1);
            case PANDORAS_CREATURES -> NetworkUtil.setChessPieceSet(this.blockEntity.getBlockPos(), 2);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output)
    {
        this.defaultButtonNarrationText(output);
    }

    private boolean shouldButtonBeLocked()
    {
        // If the player is in survival we lock the Button based on
        // whether the player has the given Set unlocked
        if(Minecraft.getInstance().player != null && !Minecraft.getInstance().player.isCreative())
            return !this.pieceSets.get(this.pieceModelSet);
        // If the player is in Creative the Buttons should be unlocked
        return false;
    }
}