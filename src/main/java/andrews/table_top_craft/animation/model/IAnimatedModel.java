package andrews.table_top_craft.animation.model;

import andrews.table_top_craft.animation.system.core.AdvancedAnimationState;
import net.minecraft.client.model.geom.ModelPart;

import java.util.Optional;

public interface IAnimatedModel
{
    void animate(AdvancedAnimationState state, float ageInTicks, float speed);

    Optional<ModelPart> getAnyDescendantWithName(String name);

    AdvancedModelPart root();
}