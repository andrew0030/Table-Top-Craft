package andrews.table_top_craft.animation.model;

import andrews.table_top_craft.animation.system.core.AdvancedAnimationState;
import andrews.table_top_craft.animation.system.core.AnimationHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.joml.Vector3f;

import java.util.Optional;
import java.util.function.Function;

public abstract class AnimatedEntityModel<E extends Entity> extends EntityModel<E> implements IAnimatedModel
{
    public static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();
    public AnimatedEntityModel()
    {
        this(RenderType::entityCutoutNoCull);
    }

    public AnimatedEntityModel(Function<ResourceLocation, RenderType> renderType)
    {
        super(renderType);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        this.root().render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void animate(AdvancedAnimationState state, float ageInTicks, float speed)
    {
        state.updateTime(ageInTicks, speed);
        if(state.isStarted())
            AnimationHandler.animate(this, state, ANIMATION_VECTOR_CACHE);
    }

    @Override
    public Optional<ModelPart> getAnyDescendantWithName(String name)
    {
        if(name.equals(this.root().getName()))
            return Optional.of(this.root());
        return this.root().getAllParts().filter((part) -> part.hasChild(name)).findFirst().map((part1) -> part1.getChild(name));
    }
}