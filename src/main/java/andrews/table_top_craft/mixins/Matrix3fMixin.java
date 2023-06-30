package andrews.table_top_craft.mixins;

import andrews.table_top_craft.mixin_util.IOptimizedMatrix3f;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Matrix3f.class)
public class Matrix3fMixin implements IOptimizedMatrix3f
{
    @Shadow protected float m00;
    @Shadow protected float m01;
    @Shadow protected float m02;
    @Shadow protected float m10;
    @Shadow protected float m11;
    @Shadow protected float m12;
    @Shadow protected float m20;
    @Shadow protected float m21;
    @Shadow protected float m22;

    @Override
    public VertexConsumer optimizedNormal(VertexConsumer bufferBuilder, float x, float y, float z)
    {
        // Calling 'bufferBuilder.normal(matrix3f, x, y, z)' allocates a Vector3f
		// To avoid allocating so many short-lived vectors we do the transform ourselves instead
	    float nx = m00 * x + m01 * y + m02 * z;
	    float ny = m10 * x + m11 * y + m12 * z;
	    float nz = m20 * x + m21 * y + m22 * z;

	    return bufferBuilder.normal(nx, ny, nz);
    }
}