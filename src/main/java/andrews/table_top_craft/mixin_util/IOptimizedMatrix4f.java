package andrews.table_top_craft.mixin_util;

import com.mojang.blaze3d.vertex.VertexConsumer;

public interface IOptimizedMatrix4f
{
    VertexConsumer optimizedPos(VertexConsumer bufferBuilder, float x, float y, float z);
}