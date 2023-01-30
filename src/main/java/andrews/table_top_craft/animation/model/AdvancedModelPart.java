package andrews.table_top_craft.animation.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class AdvancedModelPart extends ModelPart
{
    private final float MC_SCALE = 1.0F / 16.0F;
    private final String name;
    private AdvancedModelPart parent;

    public AdvancedModelPart(List<Cube> cubes, Map<String, ModelPart> children, String name)
    {
        super(cubes, children);
        this.name = name;
        for(ModelPart childModel : children.values())
            if(childModel instanceof AdvancedModelPart advancedModelPart)
                advancedModelPart.parent = this;
    }

    public AdvancedModelPart(ModelPart modelPart)
    {
        this(modelPart.cubes, modelPart.children, null);
    }

    @Override
    public AdvancedModelPart getChild(String pName)
    {
        ModelPart modelpart = this.children.get(pName);
        if (modelpart != null)
        {
            return modelpart instanceof AdvancedModelPart ? (AdvancedModelPart) modelpart : new AdvancedModelPart(modelpart);
        }
        throw new NoSuchElementException("Can't find part " + pName);
    }

    public Vector3f getTotalRotation()
    {
        Vector3f vec = new Vector3f();
        addRotations(vec, this);
        return vec;
    }

    private void addRotations(Vector3f rotation, AdvancedModelPart part)
    {
        if(part.hasParent())
            addRotations(rotation, part.getParent());
        rotation.add(part.xRot, part.yRot, part.zRot);
    }

    public Matrix4f getMatrix4f(Entity entity)
    {
        PoseStack poseStack = new PoseStack();
        if(entity != null)
            poseStack.mulPose(Axis.YN.rotationDegrees(Mth.lerp(Minecraft.getInstance().getPartialTick(), ((LivingEntity) entity).yBodyRotO, ((LivingEntity) entity).yBodyRot)));
        poseStack.translate(0, MC_SCALE * 24, 0);
        poseStack.scale(1, -1, -1);

        getTransformForPart(poseStack, this);

        return poseStack.last().pose();
    }

    public Vector3f getModelSpace(Entity entity)
    {
        PoseStack poseStack = new PoseStack();
        if(entity != null)
            poseStack.mulPose(Axis.YN.rotationDegrees(Mth.lerp(Minecraft.getInstance().getPartialTick(), ((LivingEntity) entity).yBodyRotO, ((LivingEntity) entity).yBodyRot)));
        poseStack.translate(0, MC_SCALE * 24, 0);
        poseStack.scale(1, -1, -1);

        getTransformForPart(poseStack, this);

        Matrix4f matrix4f = poseStack.last().pose();
        Vector4f vec = new Vector4f(0, 0, 0, 1);
        vec.mul(matrix4f);

        return new Vector3f(vec.x(), vec.y(), vec.z());
    }

    private void getTransformForPart(PoseStack poseStack, AdvancedModelPart advancedModelPart)
    {
        // Check for parent and if not null we call the same method on the parent
        // this will essentially keep happening until we get to the last parent (root)
        // and once we are there it will apply the transformations going down the line.
        if(advancedModelPart.hasParent())
            getTransformForPart(poseStack, advancedModelPart.getParent());
        // Position
        poseStack.translate(MC_SCALE * advancedModelPart.x, MC_SCALE * advancedModelPart.y, MC_SCALE * advancedModelPart.z);
        // Rotation
        if (advancedModelPart.xRot != 0.0F || advancedModelPart.yRot != 0.0F || advancedModelPart.zRot != 0.0F)
            poseStack.mulPose((new Quaternionf()).rotationZYX(advancedModelPart.zRot, advancedModelPart.yRot, advancedModelPart.xRot));
        // Scale
        if (advancedModelPart.xScale != 1.0F || advancedModelPart.yScale != 1.0F || advancedModelPart.zScale != 1.0F)
            poseStack.scale(advancedModelPart.xScale, advancedModelPart.yScale, advancedModelPart.zScale);
    }

    public String getName()
    {
        return this.name;
    }

    public boolean hasParent()
    {
        return this.parent != null;
    }

    public AdvancedModelPart getParent()
    {
        return this.parent;
    }
}