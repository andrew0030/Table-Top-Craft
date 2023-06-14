package andrews.table_top_craft.block_entities.animations;

import andrews.table_top_craft.animation.system.core.Animation;
import andrews.table_top_craft.animation.system.core.BasicKeyframe;
import andrews.table_top_craft.animation.system.core.KeyframeGroup;
import andrews.table_top_craft.animation.system.core.MolangKeyframe;
import andrews.table_top_craft.animation.system.core.bulders.AnimationBuilder;
import andrews.table_top_craft.animation.system.core.bulders.EasingBuilder;
import andrews.table_top_craft.animation.system.core.types.EasingTypes;
import andrews.table_top_craft.animation.system.core.types.TransformTypes;
import andrews.table_top_craft.animation.system.core.types.util.EasingType;
import net.minecraft.client.animation.KeyframeAnimations;

public class ChessAnimations
{
    public static final Animation PLACED = AnimationBuilder.withLength(1.48F)
            .addAnimation("root", new KeyframeGroup(TransformTypes.POSITION,
                    new BasicKeyframe(0.04F, KeyframeAnimations.posVec(0F, 2F, 0F), EasingTypes.LINEAR),
                    new BasicKeyframe(0.8F, KeyframeAnimations.posVec(0F, 2F, 0F), EasingTypes.LINEAR),
                    new BasicKeyframe(1.48F, KeyframeAnimations.posVec(0F, 0F, 0F), EasingBuilder.type(EasingType.EASE_IN_BOUNCE).argument(0.24F).build())))
            .addAnimation("root", new KeyframeGroup(TransformTypes.ROTATION,
                    new BasicKeyframe(0.04F, KeyframeAnimations.degreeVec(-40F, -360F, 0F), EasingTypes.LINEAR),
                    new BasicKeyframe(1.4F, KeyframeAnimations.degreeVec(0F, 0F, 0F), EasingTypes.EASE_OUT_QUART)))
            .addAnimation("root", new KeyframeGroup(TransformTypes.SCALE,
                    new BasicKeyframe(0F, KeyframeAnimations.scaleVec(0F, 0F, 0F), EasingTypes.LINEAR),
                    new BasicKeyframe(0.8F, KeyframeAnimations.scaleVec(1F, 1F, 1F), EasingTypes.EASE_OUT_CUBIC))).build();

    public static final Animation SELECTED_PIECE = AnimationBuilder.withLength(0F)
            .addAnimation("selected", new KeyframeGroup(TransformTypes.POSITION,
                    new MolangKeyframe(0F, 'p', "dsin(anim_time * 100) * 0.1", "0.5", "dcos(anim_time * 100) * 0.1", EasingTypes.LINEAR)))
            .addAnimation("selected", new KeyframeGroup(TransformTypes.ROTATION,
                    new MolangKeyframe(0F, 'r', "dcos(anim_time * 100) * 3", "0", "-dsin(anim_time * 100) * 3", EasingTypes.LINEAR))).build();
}