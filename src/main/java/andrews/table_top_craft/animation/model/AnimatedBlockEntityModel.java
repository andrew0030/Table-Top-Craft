package andrews.table_top_craft.animation.model;

import andrews.table_top_craft.animation.system.base.AnimatedBlockEntity;
import andrews.table_top_craft.animation.system.core.AdvancedAnimationState;
import andrews.table_top_craft.animation.system.core.AnimationHandler;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;

import java.util.Optional;
import java.util.function.Function;

public abstract class AnimatedBlockEntityModel extends Model implements IAnimatedModel
{
    public static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

    public AnimatedBlockEntityModel(Function<ResourceLocation, RenderType> renderType)
    {
        super(renderType);
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

    /**
     * Call this inside the render() method inside the Renderer class
     * @param blockEntity The AnimatedBlockEntity
     * @param partialTick The partial ticks
     */
    public abstract void updateAnimations(AnimatedBlockEntity blockEntity, float partialTick);
}