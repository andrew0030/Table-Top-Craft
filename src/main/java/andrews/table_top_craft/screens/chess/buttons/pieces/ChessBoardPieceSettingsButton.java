package andrews.table_top_craft.screens.chess.buttons.pieces;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.registry.TTCBlocks;
import andrews.table_top_craft.screens.base.buttons.BaseChessPageButton;
import andrews.table_top_craft.screens.chess.menus.ChessPieceSelectionScreen;
import andrews.table_top_craft.util.NetworkUtil;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ChessBoardPieceSettingsButton extends BaseChessPageButton
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
    private static final Component TITLE = Component.translatable("gui.table_top_craft.chess.button.pieces");
    private final ChessBlockEntity blockEntity;
    private final ItemStack chessPieceStack;

    public ChessBoardPieceSettingsButton(ChessBlockEntity blockEntity, int pX, int pY)
    {
        super(pX, pY, 48, 26, TITLE);
        this.blockEntity = blockEntity;
        this.chessPieceStack = new ItemStack(TTCBlocks.CHESS_PIECE_FIGURE.asItem());
        this.active = !(Minecraft.getInstance().screen instanceof ChessPieceSelectionScreen);
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        super.renderWidget(graphics, mouseX, mouseY, partialTick);
        this.renderChessPiece(graphics.pose(), this.chessPieceStack, this.x + 13, this.y + 8, 27);
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
        // If we wanted the model to base scale ratio to be like the one the Blocks in the Level have
        // We can use HEAD for transform type, and then simply manually move the model, replicating the look of GUI
//        poseStack.translate(0, 40, 0);
//        poseStack.mulPose(Axis.XN.rotationDegrees(30));
//        poseStack.mulPose(Axis.YP.rotationDegrees(135));
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
        NetworkUtil.openGuiWithServerPlayer(this.blockEntity.getBlockPos());
    }
}