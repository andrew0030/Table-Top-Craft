package andrews.table_top_craft.mixin_util;

import com.mojang.blaze3d.vertex.VertexConsumer;

public interface IOptimizedMatrix3f
{
    VertexConsumer optimizedNormal(VertexConsumer bufferBuilder, float x, float y, float z);
}