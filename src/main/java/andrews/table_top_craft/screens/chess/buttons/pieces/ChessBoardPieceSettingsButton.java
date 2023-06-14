package andrews.table_top_craft.screens.chess.buttons.pieces;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.registry.TTCBlocks;
import andrews.table_top_craft.screens.chess.menus.ChessPieceSelectionScreen;
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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;

public class ChessBoardPieceSettingsButton extends Button
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
    private final Component buttonText = Component.translatable("gui.table_top_craft.chess.button.pieces");
    private final Font fontRenderer;
    private static ChessBlockEntity chessBlockEntity;
    private static final int buttonWidth = 24;
    private static final int buttonHeight = 24;
    private int u = 48;
    private int v = 26;
    private final ItemStack chessPieceStack;

    public ChessBoardPieceSettingsButton(ChessBlockEntity tileEntity, int xPos, int yPos)
    {
        super(xPos, yPos, buttonWidth, buttonHeight, Component.literal(""), (button) -> { handleButtonPress(); }, DEFAULT_NARRATION);
        this.fontRenderer = Minecraft.getInstance().font;
        chessBlockEntity = tileEntity;
        chessPieceStack = new ItemStack(TTCBlocks.CHESS_PIECE_FIGURE.get().asItem());
    }

    @Override
    public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
    {
        this.active = !(Minecraft.getInstance().screen instanceof ChessPieceSelectionScreen);

        this.isHovered = mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height || this.isFocused();

        // Renders the Button
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        poseStack.pushPose();
        RenderSystem.enableBlend();
        GuiComponent.blit(poseStack, x, y, u, v, width, height);
        RenderSystem.disableBlend();
        poseStack.popPose();

        renderChessPiece(poseStack, chessPieceStack, x + 13, y + 8, 27);

        // This is used to render a tooltip
        if(isHovered)
            Minecraft.getInstance().screen.renderTooltip(poseStack, Arrays.asList(this.buttonText.getVisualOrderText()), x - (15 + this.fontRenderer.width(this.buttonText.getString())), y + 20, this.fontRenderer);
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
//        poseStack.mulPose(Vector3f.XN.rotationDegrees(30));
//        poseStack.mulPose(Vector3f.YP.rotationDegrees(135));
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

    /**
     * Gets called when the Button gets pressed
     */
    private static void handleButtonPress()
    {
        NetworkUtil.openGuiWithServerPlayer(chessBlockEntity.getBlockPos());
    }
}