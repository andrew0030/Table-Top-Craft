package andrews.table_top_craft.animation.system.core.types;

import andrews.table_top_craft.animation.system.core.BasicKeyframe;
import andrews.table_top_craft.animation.system.core.bulders.EasingBuilder;
import andrews.table_top_craft.animation.system.core.types.util.EasingMath;
import andrews.table_top_craft.animation.system.core.types.util.EasingType;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

public class EasingTypes
{
    private final EasingType easingType;
    // Easing Types
    public static final EasingTypes LINEAR = EasingBuilder.type(EasingType.LINEAR).build();
    public static final EasingTypes CATMULLROM = EasingBuilder.type(EasingType.CATMULLROM).build();
    // STEPS is created within the animation to create the optionalValue that indicates the step count
    // Sine
    public static final EasingTypes EASE_IN_SINE = EasingBuilder.type(EasingType.EASE_IN_SINE).build();
    public static final EasingTypes EASE_OUT_SINE = EasingBuilder.type(EasingType.EASE_OUT_SINE).build();
    public static final EasingTypes EASE_IN_OUT_SINE = EasingBuilder.type(EasingType.EASE_IN_OUT_SINE).build();
    // Quadratic
    public static final EasingTypes EASE_IN_QUAD = EasingBuilder.type(EasingType.EASE_IN_QUAD).build();
    public static final EasingTypes EASE_OUT_QUAD = EasingBuilder.type(EasingType.EASE_OUT_QUAD).build();
    public static final EasingTypes EASE_IN_OUT_QUAD = EasingBuilder.type(EasingType.EASE_IN_OUT_QUAD).build();
    // Cubic
    public static final EasingTypes EASE_IN_CUBIC = EasingBuilder.type(EasingType.EASE_IN_CUBIC).build();
    public static final EasingTypes EASE_OUT_CUBIC = EasingBuilder.type(EasingType.EASE_OUT_CUBIC).build();
    public static final EasingTypes EASE_IN_OUT_CUBIC = EasingBuilder.type(EasingType.EASE_IN_OUT_CUBIC).build();
    // Quartic
    public static final EasingTypes EASE_IN_QUART = EasingBuilder.type(EasingType.EASE_IN_QUART).build();
    public static final EasingTypes EASE_OUT_QUART = EasingBuilder.type(EasingType.EASE_OUT_QUART).build();
    public static final EasingTypes EASE_IN_OUT_QUART = EasingBuilder.type(EasingType.EASE_IN_OUT_QUART).build();
    // Quintic
    public static final EasingTypes EASE_IN_QUINT = EasingBuilder.type(EasingType.EASE_IN_QUINT).build();
    public static final EasingTypes EASE_OUT_QUINT = EasingBuilder.type(EasingType.EASE_OUT_QUINT).build();
    public static final EasingTypes EASE_IN_OUT_QUINT = EasingBuilder.type(EasingType.EASE_IN_OUT_QUINT).build();
    // Exponential
    public static final EasingTypes EASE_IN_EXPO = EasingBuilder.type(EasingType.EASE_IN_EXPO).build();
    public static final EasingTypes EASE_OUT_EXPO = EasingBuilder.type(EasingType.EASE_OUT_EXPO).build();
    public static final EasingTypes EASE_IN_OUT_EXPO = EasingBuilder.type(EasingType.EASE_IN_OUT_EXPO).build();
    // Circular
    public static final EasingTypes EASE_IN_CIRC = EasingBuilder.type(EasingType.EASE_IN_CIRC).build();
    public static final EasingTypes EASE_OUT_CIRC = EasingBuilder.type(EasingType.EASE_OUT_CIRC).build();
    public static final EasingTypes EASE_IN_OUT_CIRC = EasingBuilder.type(EasingType.EASE_IN_OUT_CIRC).build();
    // BACK, ELASTIC, BOUNCE are created within the animation to create the optionalValue
    // Optional values modified by the EasingBuilder if needed
    private final float optionalValue;

    public void storeEasedValues(Vector3f animationVecCache, float keyframeDelta, BasicKeyframe[] keyframes, Vector3f cachedLastVec, int currentKeyframeIdx, float elapsedSeconds)
    {
        Vector3f current = keyframes[currentKeyframeIdx].target(elapsedSeconds);
        switch (easingType)
        {
            case LINEAR -> { cachedLastVec.lerp(current, keyframeDelta, animationVecCache); }
            case CATMULLROM -> {
                Vector3f old = keyframes[Math.max(0, currentKeyframeIdx - 2)].target(0.0F);
                Vector3f future = keyframes[Math.min(keyframes.length - 1, currentKeyframeIdx + 1)].target(0.0F);
                animationVecCache.set(
                        Mth.catmullrom(keyframeDelta, old.x(), cachedLastVec.x(), current.x(), future.x()),
                        Mth.catmullrom(keyframeDelta, old.y(), cachedLastVec.y(), current.y(), future.y()),
                        Mth.catmullrom(keyframeDelta, old.z(), cachedLastVec.z(), current.z(), future.z()));
            }
            case STEPS -> { cachedLastVec.lerp(current, EasingMath.easeSteps(keyframeDelta, this.optionalValue), animationVecCache); }
            case EASE_IN_SINE -> { cachedLastVec.lerp(current, EasingMath.easeInSine(keyframeDelta), animationVecCache); }
            case EASE_OUT_SINE -> { cachedLastVec.lerp(current, EasingMath.easeOutSine(keyframeDelta), animationVecCache); }
            case EASE_IN_OUT_SINE -> { cachedLastVec.lerp(current, EasingMath.easeInOutSine(keyframeDelta), animationVecCache); }
            case EASE_IN_QUAD -> { cachedLastVec.lerp(current, EasingMath.easeInQuad(keyframeDelta), animationVecCache); }
            case EASE_OUT_QUAD -> { cachedLastVec.lerp(current, EasingMath.easeOutQuad(keyframeDelta), animationVecCache); }
            case EASE_IN_OUT_QUAD -> { cachedLastVec.lerp(current, EasingMath.easeInOutQuad(keyframeDelta), animationVecCache); }
            case EASE_IN_CUBIC -> { cachedLastVec.lerp(current, EasingMath.easeInCubic(keyframeDelta), animationVecCache); }
            case EASE_OUT_CUBIC -> { cachedLastVec.lerp(current, EasingMath.easeOutCubic(keyframeDelta), animationVecCache); }
            case EASE_IN_OUT_CUBIC -> { cachedLastVec.lerp(current, EasingMath.easeInOutCubic(keyframeDelta), animationVecCache); }
            case EASE_IN_QUART -> { cachedLastVec.lerp(current, EasingMath.easeInQuart(keyframeDelta), animationVecCache); }
            case EASE_OUT_QUART -> { cachedLastVec.lerp(current, EasingMath.easeOutQuart(keyframeDelta), animationVecCache); }
            case EASE_IN_OUT_QUART -> { cachedLastVec.lerp(current, EasingMath.easeInOutQuart(keyframeDelta), animationVecCache); }
            case EASE_IN_QUINT -> { cachedLastVec.lerp(current, EasingMath.easeInQuint(keyframeDelta), animationVecCache); }
            case EASE_OUT_QUINT -> { cachedLastVec.lerp(current, EasingMath.easeOutQuint(keyframeDelta), animationVecCache); }
            case EASE_IN_OUT_QUINT -> { cachedLastVec.lerp(current, EasingMath.easeInOutQuint(keyframeDelta), animationVecCache); }
            case EASE_IN_EXPO -> { cachedLastVec.lerp(current, EasingMath.easeInExpo(keyframeDelta), animationVecCache); }
            case EASE_OUT_EXPO -> { cachedLastVec.lerp(current, EasingMath.easeOutExpo(keyframeDelta), animationVecCache); }
            case EASE_IN_OUT_EXPO -> { cachedLastVec.lerp(current, EasingMath.easeInOutExpo(keyframeDelta), animationVecCache); }
            case EASE_IN_CIRC -> { cachedLastVec.lerp(current, EasingMath.easeInCirc(keyframeDelta), animationVecCache); }
            case EASE_OUT_CIRC -> { cachedLastVec.lerp(current, EasingMath.easeOutCirc(keyframeDelta), animationVecCache); }
            case EASE_IN_OUT_CIRC -> { cachedLastVec.lerp(current, EasingMath.easeInOutCirc(keyframeDelta), animationVecCache); }
            case EASE_IN_BACK -> { cachedLastVec.lerp(current, EasingMath.easeInBack(keyframeDelta, this.optionalValue), animationVecCache); }
            case EASE_OUT_BACK -> { cachedLastVec.lerp(current, EasingMath.easeOutBack(keyframeDelta, this.optionalValue), animationVecCache); }
            case EASE_IN_OUT_BACK -> { cachedLastVec.lerp(current, EasingMath.easeInOutBack(keyframeDelta, this.optionalValue), animationVecCache); }
            case EASE_IN_ELASTIC -> { cachedLastVec.lerp(current, EasingMath.easeInElastic(keyframeDelta, this.optionalValue), animationVecCache); }
            case EASE_OUT_ELASTIC -> { cachedLastVec.lerp(current, EasingMath.easeOutElastic(keyframeDelta, this.optionalValue), animationVecCache); }
            case EASE_IN_OUT_ELASTIC -> { cachedLastVec.lerp(current, EasingMath.easeInOutElastic(keyframeDelta, this.optionalValue), animationVecCache); }
            case EASE_IN_BOUNCE -> { cachedLastVec.lerp(current, EasingMath.easeInBounce(keyframeDelta, this.optionalValue), animationVecCache); }
            case EASE_OUT_BOUNCE -> { cachedLastVec.lerp(current, EasingMath.easeOutBounce(keyframeDelta, this.optionalValue), animationVecCache); }
            case EASE_IN_OUT_BOUNCE -> { cachedLastVec.lerp(current, EasingMath.easeInOutBounce(keyframeDelta, this.optionalValue), animationVecCache); }
        }
    }

    public EasingTypes(EasingType easingType, float value)
    {
        this.easingType = easingType;
        this.optionalValue = value;
    }

    public EasingType getEasingType()
    {
        return this.easingType;
    }

    public float getOptionalValue()
    {
        return this.optionalValue;
    }
}