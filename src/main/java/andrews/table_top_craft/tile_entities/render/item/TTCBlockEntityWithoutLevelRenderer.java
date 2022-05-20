package andrews.table_top_craft.tile_entities.render.item;

import andrews.table_top_craft.registry.TTCBlocks;
import andrews.table_top_craft.tile_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.util.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.core.BlockPos;

public class TTCBlockEntityWithoutLevelRenderer
{
    public static Color color = new Color(0, 0, 0);

    public static final ChessPieceFigureBlockEntity chessPieceFigureBlockEntity = new ChessPieceFigureBlockEntity(BlockPos.ZERO, TTCBlocks.CHESS_PIECE_FIGURE.defaultBlockState());

    public static void init() {}

    // RENDERING CODE HAS BEEN MOVED INTO MIXIN:
    // BlockEntityWithoutLevelRendererMixin
    // I just kept this class for some helper methods and the fields

    public static boolean isHeldOrHead(ItemTransforms.TransformType type)
    {
        return type.equals(ItemTransforms.TransformType.HEAD) || type.equals(ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND) || type.equals(ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND);
    }

    public static float getPartialTicks()
    {
        Minecraft mc = Minecraft.getInstance();
        return mc.isPaused() ? mc.pausePartialTick : mc.getFrameTime();
    }
}