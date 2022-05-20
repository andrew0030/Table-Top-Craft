package andrews.table_top_craft.mixins;

import andrews.table_top_craft.registry.TTCBlocks;
import andrews.table_top_craft.tile_entities.render.ChessPieceFigureTileEntityRenderer;
import andrews.table_top_craft.tile_entities.render.item.TTCBlockEntityWithoutLevelRenderer;
import andrews.table_top_craft.util.NBTColorSaving;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntityWithoutLevelRenderer.class)
public class BlockEntityWithoutLevelRendererMixin
{
    @Inject(method = "renderByItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;", shift = At.Shift.AFTER))
    private void renderByItem(ItemStack itemStack, ItemTransforms.TransformType type, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, CallbackInfo ci)
    {
        if(itemStack.getItem() == TTCBlocks.CHESS_PIECE_FIGURE.asItem())
        {
            try
            {
                // We get the Piece Type from the item, if there is none we just render a Pawn
                CompoundTag compoundTag = BlockItem.getBlockEntityData(itemStack);
                // We get and set Piece Type
                if(compoundTag != null && compoundTag.contains("PieceType", Tag.TAG_INT))
                    TTCBlockEntityWithoutLevelRenderer.chessPieceFigureBlockEntity.setPieceType(compoundTag.getInt("PieceType"));
                else
                    TTCBlockEntityWithoutLevelRenderer.chessPieceFigureBlockEntity.setPieceType(1);
                // We get and set Rotation
                if(compoundTag != null && compoundTag.contains("RotateChessPieceFigure", Tag.TAG_INT))
                    TTCBlockEntityWithoutLevelRenderer.chessPieceFigureBlockEntity.setRotateChessPieceFigure(compoundTag.getInt("RotateChessPieceFigure") != 0);
                else
                    TTCBlockEntityWithoutLevelRenderer.chessPieceFigureBlockEntity.setRotateChessPieceFigure(false);
                // We get and set Color
                if(compoundTag != null && compoundTag.contains("PieceColor", Tag.TAG_STRING))
                    TTCBlockEntityWithoutLevelRenderer.chessPieceFigureBlockEntity.setPieceColor(compoundTag.getString("PieceColor"));
                else
                    TTCBlockEntityWithoutLevelRenderer.chessPieceFigureBlockEntity.setPieceColor(NBTColorSaving.createWhitePiecesColor());
                // We get and set the Piece Set
                if(compoundTag != null && compoundTag.contains("PieceSet", Tag.TAG_INT))
                    TTCBlockEntityWithoutLevelRenderer.chessPieceFigureBlockEntity.setPieceSet(compoundTag.getInt("PieceSet"));
                else
                    TTCBlockEntityWithoutLevelRenderer.chessPieceFigureBlockEntity.setPieceSet(1);

                if(itemStack.getHoverName().getString().equals("andrew_"))
                {
                    int tickCount = Minecraft.getInstance().player.tickCount;
                    int value = (tickCount % 180) * 2;
                    TTCBlockEntityWithoutLevelRenderer.color = TTCBlockEntityWithoutLevelRenderer.color.fromHSV(value, 1.0F, 1.0F);
                    TTCBlockEntityWithoutLevelRenderer.chessPieceFigureBlockEntity.setPieceColor(TTCBlockEntityWithoutLevelRenderer.color.getRed() + "/" + TTCBlockEntityWithoutLevelRenderer.color.getGreen() + "/" + TTCBlockEntityWithoutLevelRenderer.color.getBlue() + "/255");
                }
                if(itemStack.getHoverName().getString().equals("Lyzantra"))
                    TTCBlockEntityWithoutLevelRenderer.chessPieceFigureBlockEntity.setPieceName("Lyzantra");
                else
                    TTCBlockEntityWithoutLevelRenderer.chessPieceFigureBlockEntity.setPieceName(null);

                ChessPieceFigureTileEntityRenderer.renderChessPieceFigure(
                        TTCBlockEntityWithoutLevelRenderer.chessPieceFigureBlockEntity,
                        poseStack,
                        buffer,
                        type.equals(ItemTransforms.TransformType.GUI),
                        TTCBlockEntityWithoutLevelRenderer.isHeldOrHead(type),
                        TTCBlockEntityWithoutLevelRenderer.getPartialTicks(),
                        packedLight,
                        packedOverlay);
            }
            catch (Exception e)
            {
                System.err.println(e.getMessage());
            }
        }
    }
}