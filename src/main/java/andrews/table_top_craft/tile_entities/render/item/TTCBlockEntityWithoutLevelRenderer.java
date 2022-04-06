package andrews.table_top_craft.tile_entities.render.item;

import andrews.table_top_craft.registry.TTCBlocks;
import andrews.table_top_craft.tile_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.tile_entities.render.ChessPieceFigureTileEntityRenderer;
import andrews.table_top_craft.util.NBTColorSaving;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.BlockItem;
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
                // We get the Piece Type from the item, if there is none we just render a Pawn
                CompoundTag compoundTag = BlockItem.getBlockEntityData(itemStack);
                // We get and set Piece Type
                if(compoundTag != null && compoundTag.contains("PieceType", Tag.TAG_INT))
                    chessPieceFigureBlockEntity.setPieceType(compoundTag.getInt("PieceType"));
                else
                    chessPieceFigureBlockEntity.setPieceType(1);
                // We get and set Rotation
                if(compoundTag != null && compoundTag.contains("RotateChessPieceFigure", Tag.TAG_INT))
                    chessPieceFigureBlockEntity.setRotateChessPieceFigure(compoundTag.getInt("RotateChessPieceFigure") != 0);
                else
                    chessPieceFigureBlockEntity.setRotateChessPieceFigure(false);
                // We get and set Color
                if(compoundTag != null && compoundTag.contains("PieceColor", Tag.TAG_STRING))
                    chessPieceFigureBlockEntity.setPieceColor(compoundTag.getString("PieceColor"));
                else
                    chessPieceFigureBlockEntity.setPieceColor(NBTColorSaving.createWhitePiecesColor());

                //TODO properly fix transform type for GUI, HEAD and THIRD_PERSON_X_HAND!
                ChessPieceFigureTileEntityRenderer.renderChessPieceFigure(chessPieceFigureBlockEntity, poseStack, buffer, type.equals(ItemTransforms.TransformType.GUI), isHeldOrHead(type), getPartialTicks(), packedLight, packedOverlay);
            }
            catch (Exception e)
            {
                System.err.println(e.getMessage());
            }
        }
    }

    private boolean isHeldOrHead(ItemTransforms.TransformType type)
    {
        return type.equals(ItemTransforms.TransformType.HEAD) || type.equals(ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND) || type.equals(ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND);
    }

    private float getPartialTicks()
    {
        Minecraft mc = Minecraft.getInstance();
        return mc.isPaused() ?mc.pausePartialTick : mc.getFrameTime();
    }
}