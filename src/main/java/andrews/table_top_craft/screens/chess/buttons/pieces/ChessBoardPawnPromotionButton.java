package andrews.table_top_craft.screens.chess.buttons.pieces;

import andrews.table_top_craft.registry.TTCBlocks;
import andrews.table_top_craft.tile_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.NetworkUtil;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;

public class ChessBoardPawnPromotionButton extends Button
{
    private static final ResourceLocation BUTTONS_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/buttons/chess_pawn_promotion_buttons.png");
    private static final Component QUEEN_TOOLTIP = Component.translatable("tooltip.table_top_craft.chess_piece_figure.type.queen");
    private static final Component BISHOP_TOOLTIP = Component.translatable("tooltip.table_top_craft.chess_piece_figure.type.bishop");
    private static final Component KNIGHT_TOOLTIP = Component.translatable("tooltip.table_top_craft.chess_piece_figure.type.knight");
    private static final Component ROOK_TOOLTIP = Component.translatable("tooltip.table_top_craft.chess_piece_figure.type.rook");
    private final ItemStack CHESS_PIECE_FIGURE = new ItemStack(TTCBlocks.CHESS_PIECE_FIGURE.get());
    private final ChessTileEntity blockEntity;
    private final PawnPromotionPieceType type;

    public ChessBoardPawnPromotionButton(ChessTileEntity blockEntity, int x, int y, boolean isWhite, PawnPromotionPieceType type)
    {
        super(x, y, 43, 43, Component.literal(""), Button::onPress);
        this.blockEntity = blockEntity;
        this.type = type;

        ChessPieceFigureBlockEntity previewBlockEntity = new ChessPieceFigureBlockEntity(BlockPos.ZERO, TTCBlocks.CHESS_PIECE_FIGURE.get().defaultBlockState());
        previewBlockEntity.setPieceSet(blockEntity.getPieceSet() + 1);
        previewBlockEntity.setPieceType(type.getPieceType());
        previewBlockEntity.setPieceColor(isWhite ? blockEntity.getWhitePiecesColor() : blockEntity.getBlackPiecesColor());
        previewBlockEntity.setRotateChessPieceFigure(false);
        previewBlockEntity.saveToItem(CHESS_PIECE_FIGURE);
    }

    @Override
    public void onPress()
    {
        NetworkUtil.doPawnPromotion(this.blockEntity.getBlockPos(), (byte) this.type.getPieceType());
        Minecraft.getInstance().player.closeContainer();
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
    {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BUTTONS_TEXTURE);
        this.blit(poseStack, this.x, this.y, ((isHovered || isFocused()) ? 43 : 0), 0, 43, 43);

        renderChessPiece(poseStack, CHESS_PIECE_FIGURE, x + (43 / 2), y + (43 / 2) - 3, 40);

        RenderSystem.disableDepthTest();
        if (this.isHoveredOrFocused() && Minecraft.getInstance().screen != null)
            switch (type) {
                default -> Minecraft.getInstance().screen.renderTooltip(poseStack, QUEEN_TOOLTIP, mouseX, mouseY);
                case BISHOP -> Minecraft.getInstance().screen.renderTooltip(poseStack, BISHOP_TOOLTIP, mouseX, mouseY);
                case KNIGHT -> Minecraft.getInstance().screen.renderTooltip(poseStack, KNIGHT_TOOLTIP, mouseX, mouseY);
                case ROOK -> Minecraft.getInstance().screen.renderTooltip(poseStack, ROOK_TOOLTIP, mouseX, mouseY);
            }
        RenderSystem.enableDepthTest();
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
        itemRenderer.render(itemStack, ItemTransforms.TransformType.GUI, false, poseStack, bufferSource, 15728880, OverlayTexture.NO_OVERLAY, itemBakedModel);
        bufferSource.endBatch();
        poseStack.popPose();
        RenderSystem.applyModelViewMatrix();
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