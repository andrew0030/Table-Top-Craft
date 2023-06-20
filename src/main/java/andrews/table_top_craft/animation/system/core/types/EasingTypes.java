package andrews.table_top_craft.animation.system.core.types;

import andrews.table_top_craft.animation.system.core.BasicKeyframe;
import andrews.table_top_craft.animation.system.core.bulders.EasingBuilder;
import andrews.table_top_craft.animation.system.core.types.util.EasingMath;
import andrews.table_top_craft.animation.system.core.types.util.EasingType;
import com.mojang.math.Vector3f;
import net.minecraft.util.Mth;

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
            case LINEAR -> animationVecCache.set(Mth.lerp(keyframeDelta, cachedLastVec.x(), current.x()), Mth.lerp(keyframeDelta, cachedLastVec.y(), current.y()), Mth.lerp(keyframeDelta, cachedLastVec.z(), current.z()));
            case CATMULLROM -> {
                Vector3f old = keyframes[Math.max(0, currentKeyframeIdx - 2)].target(0.0F);
                Vector3f future = keyframes[Math.min(keyframes.length - 1, currentKeyframeIdx + 1)].target(0.0F);
                animationVecCache.set(
                        Mth.catmullrom(keyframeDelta, old.x(), cachedLastVec.x(), current.x(), future.x()),
                        Mth.catmullrom(keyframeDelta, old.y(), cachedLastVec.y(), current.y(), future.y()),
                        Mth.catmullrom(keyframeDelta, old.z(), cachedLastVec.z(), current.z(), future.z()));
            }
            case STEPS -> animationVecCache.set(Mth.lerp(EasingMath.easeSteps(keyframeDelta, this.optionalValue), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeSteps(keyframeDelta, this.optionalValue), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeSteps(keyframeDelta, this.optionalValue), cachedLastVec.z(), current.z()));
            case EASE_IN_SINE -> animationVecCache.set(Mth.lerp(EasingMath.easeInSine(keyframeDelta), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeInSine(keyframeDelta), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeInSine(keyframeDelta), cachedLastVec.z(), current.z()));
            case EASE_OUT_SINE -> animationVecCache.set(Mth.lerp(EasingMath.easeOutSine(keyframeDelta), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeOutSine(keyframeDelta), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeOutSine(keyframeDelta), cachedLastVec.z(), current.z()));
            case EASE_IN_OUT_SINE -> animationVecCache.set(Mth.lerp(EasingMath.easeInOutSine(keyframeDelta), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeInOutSine(keyframeDelta), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeInOutSine(keyframeDelta), cachedLastVec.z(), current.z()));
            case EASE_IN_QUAD -> animationVecCache.set(Mth.lerp(EasingMath.easeInQuad(keyframeDelta), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeInQuad(keyframeDelta), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeInQuad(keyframeDelta), cachedLastVec.z(), current.z()));
            case EASE_OUT_QUAD -> animationVecCache.set(Mth.lerp(EasingMath.easeOutQuad(keyframeDelta), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeOutQuad(keyframeDelta), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeOutQuad(keyframeDelta), cachedLastVec.z(), current.z()));
            case EASE_IN_OUT_QUAD -> animationVecCache.set(Mth.lerp(EasingMath.easeInOutQuad(keyframeDelta), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeInOutQuad(keyframeDelta), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeInOutQuad(keyframeDelta), cachedLastVec.z(), current.z()));
            case EASE_IN_CUBIC -> animationVecCache.set(Mth.lerp(EasingMath.easeInCubic(keyframeDelta), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeInCubic(keyframeDelta), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeInCubic(keyframeDelta), cachedLastVec.z(), current.z()));
            case EASE_OUT_CUBIC -> animationVecCache.set(Mth.lerp(EasingMath.easeOutCubic(keyframeDelta), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeOutCubic(keyframeDelta), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeOutCubic(keyframeDelta), cachedLastVec.z(), current.z()));
            case EASE_IN_OUT_CUBIC -> animationVecCache.set(Mth.lerp(EasingMath.easeInOutCubic(keyframeDelta), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeInOutCubic(keyframeDelta), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeInOutCubic(keyframeDelta), cachedLastVec.z(), current.z()));
            case EASE_IN_QUART -> animationVecCache.set(Mth.lerp(EasingMath.easeInQuart(keyframeDelta), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeInQuart(keyframeDelta), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeInQuart(keyframeDelta), cachedLastVec.z(), current.z()));
            case EASE_OUT_QUART -> animationVecCache.set(Mth.lerp(EasingMath.easeOutQuart(keyframeDelta), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeOutQuart(keyframeDelta), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeOutQuart(keyframeDelta), cachedLastVec.z(), current.z()));
            case EASE_IN_OUT_QUART -> animationVecCache.set(Mth.lerp(EasingMath.easeInOutQuart(keyframeDelta), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeInOutQuart(keyframeDelta), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeInOutQuart(keyframeDelta), cachedLastVec.z(), current.z()));
            case EASE_IN_QUINT -> animationVecCache.set(Mth.lerp(EasingMath.easeInQuint(keyframeDelta), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeInQuint(keyframeDelta), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeInQuint(keyframeDelta), cachedLastVec.z(), current.z()));
            case EASE_OUT_QUINT -> animationVecCache.set(Mth.lerp(EasingMath.easeOutQuint(keyframeDelta), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeOutQuint(keyframeDelta), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeOutQuint(keyframeDelta), cachedLastVec.z(), current.z()));
            case EASE_IN_OUT_QUINT -> animationVecCache.set(Mth.lerp(EasingMath.easeInOutQuint(keyframeDelta), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeInOutQuint(keyframeDelta), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeInOutQuint(keyframeDelta), cachedLastVec.z(), current.z()));
            case EASE_IN_EXPO -> animationVecCache.set(Mth.lerp(EasingMath.easeInExpo(keyframeDelta), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeInExpo(keyframeDelta), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeInExpo(keyframeDelta), cachedLastVec.z(), current.z()));
            case EASE_OUT_EXPO -> animationVecCache.set(Mth.lerp(EasingMath.easeOutExpo(keyframeDelta), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeOutExpo(keyframeDelta), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeOutExpo(keyframeDelta), cachedLastVec.z(), current.z()));
            case EASE_IN_OUT_EXPO -> animationVecCache.set(Mth.lerp(EasingMath.easeInOutExpo(keyframeDelta), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeInOutExpo(keyframeDelta), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeInOutExpo(keyframeDelta), cachedLastVec.z(), current.z()));
            case EASE_IN_CIRC -> animationVecCache.set(Mth.lerp(EasingMath.easeInCirc(keyframeDelta), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeInCirc(keyframeDelta), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeInCirc(keyframeDelta), cachedLastVec.z(), current.z()));
            case EASE_OUT_CIRC -> animationVecCache.set(Mth.lerp(EasingMath.easeOutCirc(keyframeDelta), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeOutCirc(keyframeDelta), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeOutCirc(keyframeDelta), cachedLastVec.z(), current.z()));
            case EASE_IN_OUT_CIRC -> animationVecCache.set(Mth.lerp(EasingMath.easeInOutCirc(keyframeDelta), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeInOutCirc(keyframeDelta), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeInOutCirc(keyframeDelta), cachedLastVec.z(), current.z()));
            case EASE_IN_BACK -> animationVecCache.set(Mth.lerp(EasingMath.easeInBack(keyframeDelta, this.optionalValue), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeInBack(keyframeDelta, this.optionalValue), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeInBack(keyframeDelta, this.optionalValue), cachedLastVec.z(), current.z()));
            case EASE_OUT_BACK -> animationVecCache.set(Mth.lerp(EasingMath.easeOutBack(keyframeDelta, this.optionalValue), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeOutBack(keyframeDelta, this.optionalValue), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeOutBack(keyframeDelta, this.optionalValue), cachedLastVec.z(), current.z()));
            case EASE_IN_OUT_BACK -> animationVecCache.set(Mth.lerp(EasingMath.easeInOutBack(keyframeDelta, this.optionalValue), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeInOutBack(keyframeDelta, this.optionalValue), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeInOutBack(keyframeDelta, this.optionalValue), cachedLastVec.z(), current.z()));
            case EASE_IN_ELASTIC -> animationVecCache.set(Mth.lerp(EasingMath.easeInElastic(keyframeDelta, this.optionalValue), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeInElastic(keyframeDelta, this.optionalValue), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeInElastic(keyframeDelta, this.optionalValue), cachedLastVec.z(), current.z()));
            case EASE_OUT_ELASTIC -> animationVecCache.set(Mth.lerp(EasingMath.easeOutElastic(keyframeDelta, this.optionalValue), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeOutElastic(keyframeDelta, this.optionalValue), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeOutElastic(keyframeDelta, this.optionalValue), cachedLastVec.z(), current.z()));
            case EASE_IN_OUT_ELASTIC -> animationVecCache.set(Mth.lerp(EasingMath.easeInOutElastic(keyframeDelta, this.optionalValue), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeInOutElastic(keyframeDelta, this.optionalValue), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeInOutElastic(keyframeDelta, this.optionalValue), cachedLastVec.z(), current.z()));
            case EASE_IN_BOUNCE -> { animationVecCache.set(Mth.lerp(EasingMath.easeInBounce(keyframeDelta, this.optionalValue), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeInBounce(keyframeDelta, this.optionalValue), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeInBounce(keyframeDelta, this.optionalValue), cachedLastVec.z(), current.z())); }
            case EASE_OUT_BOUNCE -> { animationVecCache.set(Mth.lerp(EasingMath.easeOutBounce(keyframeDelta, this.optionalValue), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeOutBounce(keyframeDelta, this.optionalValue), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeOutBounce(keyframeDelta, this.optionalValue), cachedLastVec.z(), current.z())); }
            case EASE_IN_OUT_BOUNCE -> { animationVecCache.set(Mth.lerp(EasingMath.easeInOutBounce(keyframeDelta, this.optionalValue), cachedLastVec.x(), current.x()), Mth.lerp(EasingMath.easeInOutBounce(keyframeDelta, this.optionalValue), cachedLastVec.y(), current.y()), Mth.lerp(EasingMath.easeInOutBounce(keyframeDelta, this.optionalValue), cachedLastVec.z(), current.z())); }
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