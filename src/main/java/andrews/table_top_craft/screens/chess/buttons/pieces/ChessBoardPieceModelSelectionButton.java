package andrews.table_top_craft.screens.chess.buttons.pieces;

import andrews.table_top_craft.registry.TTCBlocks;
import andrews.table_top_craft.screens.chess.menus.ChessPieceSelectionScreen;
import andrews.table_top_craft.tile_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Arrays;

public class ChessBoardPieceModelSelectionButton extends Button
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/piece_model_selection_buttons.png");
    private final TranslatableComponent buttonText = new TranslatableComponent("tooltip.table_top_craft.chess.piece_type.standard");
    private final Font fontRenderer;
    private static ChessTileEntity chessTileEntity;
    private static final int buttonWidth = 167;
    private static final int buttonHeight = 37;
    private int u = 0;
    private int v = 0;
    // Chess Pieces on the Button
    private ChessPieceFigureBlockEntity chessPieceFigureBlockEntity;
    private ItemStack chessPieceStack;

    public ChessBoardPieceModelSelectionButton(ChessTileEntity tileEntity, int xPos, int yPos)
    {
        super(xPos, yPos, buttonWidth, buttonHeight, new TextComponent(""), (button) -> { handleButtonPress(); });
        this.fontRenderer = Minecraft.getInstance().font;
        chessTileEntity = tileEntity;
        chessPieceFigureBlockEntity = new ChessPieceFigureBlockEntity(BlockPos.ZERO, TTCBlocks.CHESS_PIECE_FIGURE.get().defaultBlockState());
        chessPieceStack = new ItemStack(TTCBlocks.CHESS_PIECE_FIGURE.get().asItem());
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
    {
        this.isHovered = mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height || this.isFocused();

        this.v = 0;
        if(this.isHovered)
            this.v += 37;

        // Renders the Button
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        poseStack.pushPose();
        RenderSystem.enableBlend();
        this.blit(poseStack, x, y, u, v, width, height);
        RenderSystem.disableBlend();
        poseStack.popPose();

        chessPieceFigureBlockEntity.setRotateChessPieceFigure(true);
        int scale = 32;
        for(int i = 0; i < 6; i++)
        {
            chessPieceFigureBlockEntity.setPieceType(i + 1);
            chessPieceFigureBlockEntity.saveToItem(chessPieceStack);
            renderChessPiece(poseStack, chessPieceStack, x + 16 + (27 * i), y + 16, scale);
        }
//                                                                                                                ((this.width / 2) - (this.fontRenderer.width(this.buttonText) / 2))
        Minecraft.getInstance().screen.renderTooltip(poseStack, Arrays.asList(this.buttonText.getVisualOrderText()), x - 8 + ((this.width / 2) - ((this.fontRenderer.width(this.buttonText) + 8) / 2)), y + 1, this.fontRenderer);
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

    /**
     * Gets called when the Button gets pressed
     */
    private static void handleButtonPress()
    {
        Minecraft.getInstance().setScreen(new ChessPieceSelectionScreen(chessTileEntity));
    }
}