package andrews.table_top_craft.tile_entities.render.item;

import andrews.table_top_craft.registry.TTCBlocks;
import andrews.table_top_craft.tile_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.tile_entities.render.ChessPieceFigureTileEntityRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;

public class TTCBlockEntityWithoutLevelRenderer extends BlockEntityWithoutLevelRenderer
{
    // Instance of the class
    public static TTCBlockEntityWithoutLevelRenderer INSTANCE = new TTCBlockEntityWithoutLevelRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    // Block Entities
    private final ChessPieceFigureBlockEntity chessPieceFigureBlockEntity;

    public TTCBlockEntityWithoutLevelRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher, EntityModelSet entityModelSet)
    {
        super(blockEntityRenderDispatcher, entityModelSet);
        chessPieceFigureBlockEntity = new ChessPieceFigureBlockEntity(BlockPos.ZERO, TTCBlocks.CHESS_PIECE_FIGURE.get().defaultBlockState());
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemTransforms.TransformType type, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay)
    {
        if(itemStack.getItem() == TTCBlocks.CHESS_PIECE_FIGURE.get().asItem())
        {
            try
            {
                poseStack.pushPose();
                ChessPieceFigureTileEntityRenderer.renderChessPieceFigure(chessPieceFigureBlockEntity, poseStack, buffer, packedLight, packedOverlay);
                poseStack.popPose();
            }
            catch (NullPointerException e)
            {
                System.err.println("Tried to render chess piece inside inventory, but RenderType was still null!");
            }
        }
    }
}