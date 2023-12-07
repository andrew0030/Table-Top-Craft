package andrews.table_top_craft.screens.chess.buttons.pieces;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.block_entities.ChessPieceFigureBlockEntity;
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

public class ChessBoardPawnPromotionButton extends AbstractButton
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/buttons/chess_pawn_promotion_buttons.png");
    private static final Component QUEEN_TXT = Component.translatable("tooltip.table_top_craft.chess_piece_figure.type.queen");
    private static final Component BISHOP_TXT = Component.translatable("tooltip.table_top_craft.chess_piece_figure.type.bishop");
    private static final Component KNIGHT_TXT = Component.translatable("tooltip.table_top_craft.chess_piece_figure.type.knight");
    private static final Component ROOK_TXT = Component.translatable("tooltip.table_top_craft.chess_piece_figure.type.rook");
    private final ItemStack chessPieceFigure = new ItemStack(TTCBlocks.CHESS_PIECE_FIGURE);
    private final ChessBlockEntity blockEntity;
    private final PawnPromotionPieceType type;

    public ChessBoardPawnPromotionButton(ChessBlockEntity blockEntity, int pX, int pY, boolean isWhite, PawnPromotionPieceType type)
    {
        super(pX, pY, 43, 43, Component.literal(""));
        this.blockEntity = blockEntity;
        this.type = type;

        ChessPieceFigureBlockEntity previewBlockEntity = new ChessPieceFigureBlockEntity(BlockPos.ZERO, TTCBlocks.CHESS_PIECE_FIGURE.defaultBlockState());
        previewBlockEntity.setPieceSet(blockEntity.getPieceSet() + 1);
        previewBlockEntity.setPieceType(type.getPieceType());
        previewBlockEntity.setPieceColor(isWhite ? blockEntity.getWhitePiecesColor() : blockEntity.getBlackPiecesColor());
        previewBlockEntity.setRotateChessPieceFigure(false);
        previewBlockEntity.saveToItem(this.chessPieceFigure);
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        graphics.blit(TEXTURE, this.x, this.y, (this.isHoveredOrFocused() ? 43 : 0), 0, 43, 43);
        this.renderChessPiece(graphics.pose(), this.chessPieceFigure, this.x + (43 / 2), this.y + (43 / 2) - 3, 40);

        if(Minecraft.getInstance().screen != null && this.isHoveredOrFocused())
        {
            int posX = this.isHovered() ? mouseX : this.x - 8;
            int posY = this.isHovered() ? mouseY : this.y;
            graphics.pose().pushPose();
            RenderSystem.disableDepthTest();
            switch (type) {
                default -> graphics.renderTooltip(Minecraft.getInstance().font, QUEEN_TXT, posX, posY);
                case BISHOP -> graphics.renderTooltip(Minecraft.getInstance().font, BISHOP_TXT, posX, posY);
                case KNIGHT -> graphics.renderTooltip(Minecraft.getInstance().font, KNIGHT_TXT, posX, posY);
                case ROOK -> graphics.renderTooltip(Minecraft.getInstance().font, ROOK_TXT, posX, posY);
            }
            RenderSystem.enableDepthTest();
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
        NetworkUtil.doPawnPromotion(this.blockEntity.getBlockPos(), (byte) this.type.getPieceType());
        Minecraft.getInstance().player.closeContainer();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output)
    {
        this.defaultButtonNarrationText(output);
    }

    public enum PawnPromotionPieceType
    {
        QUEEN(6),
        BISHOP(3),
        KNIGHT(4),
        ROOK(2);

        final int pieceType;

        PawnPromotionPieceType(int pieceType)
        {
            this.pieceType = pieceType;
        }

        public int getPieceType()
        {
            return this.pieceType;
        }
    }
}