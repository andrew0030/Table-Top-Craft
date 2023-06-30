package andrews.table_top_craft.mixins;

import andrews.table_top_craft.mixin_util.IOptimizedMatrix4f;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Matrix4f.class)
public class Matrix4fMixin implements IOptimizedMatrix4f
{
    @Shadow protected float m00;
    @Shadow protected float m01;
    @Shadow protected float m02;
    @Shadow protected float m03;
    @Shadow protected float m10;
    @Shadow protected float m11;
    @Shadow protected float m12;
    @Shadow protected float m13;
    @Shadow protected float m20;
    @Shadow protected float m21;
    @Shadow protected float m22;
    @Shadow protected float m23;

    @Override
    public VertexConsumer optimizedPos(VertexConsumer bufferBuilder, float x, float y, float z)
    {
        // Calling 'bufferBuilder.pos(matrix4f, x, y, z)' allocates a Vector4f
        // To avoid allocating so many short-lived vectors we do the transform ourselves instead
        float w = 1.0F;
        float tx = m00 * x + m01 * y + m02 * z + m03 * w;
        float ty = m10 * x + m11 * y + m12 * z + m13 * w;
        float tz = m20 * x + m21 * y + m22 * z + m23 * w;

        return bufferBuilder.vertex(tx, ty, tz);
    }
}