package andrews.table_top_craft.animation.system.core.types.util;

import net.minecraft.util.Mth;

import java.util.function.IntPredicate;

public class EasingMath
{
    // Steps
    public static float easeSteps(float value, float steps) {
        if (value < 0) return 0;
        if (steps < 2) steps = 2;

        float stepLength = 1.0f / steps;
        float result = (steps - 1) * stepLength;
        if (value > result) return result;

        IntPredicate isTargetBeforeOrAt = i -> value < i * stepLength;
        int stepIndex = Mth.binarySearch(0, Math.round(steps) - 1, isTargetBeforeOrAt);
        if (stepIndex < 0) {
            stepIndex = -(stepIndex + 1);
        }
        return (stepIndex - 1) * stepLength;
    }
    // Sine
    public static float easeInSine(float value) { return 1 - Mth.cos((value * Mth.PI) / 2); }
    public static float easeOutSine(float value) { return Mth.sin((value * Mth.PI) / 2); }
    public static float easeInOutSine(float value) { return -(Mth.cos(Mth.PI * value) - 1) / 2; }
    // Quadratic
    public static float easeInQuad(float value) { return value * value; }
    public static float easeOutQuad(float value) { return 1 - (1 - value) * (1 - value); }
    public static float easeInOutQuad(float value) { return value < 0.5 ? 2 * value * value : 1 - (float) Math.pow(-2 * value + 2, 2) / 2; }
    // Cubic
    public static float easeInCubic(float value) { return value * value * value; }
    public static float easeOutCubic(float value) { return 1 - (float) Math.pow(1 - value, 3); }
    public static float easeInOutCubic(float value) { return value < 0.5 ? 4 * value * value * value : 1 - (float) Math.pow(-2 * value + 2, 3) / 2; }
    // Quartic
    public static float easeInQuart(float value) { return value * value * value * value; }
    public static float easeOutQuart(float value) { return 1 - (float) Math.pow(1 - value, 4); }
    public static float easeInOutQuart(float value) { return value < 0.5 ? 8 * value * value * value * value : 1 - (float) Math.pow(-2 * value + 2, 4) / 2; }
    // Quintic
    public static float easeInQuint(float value) { return value * value * value * value * value; }
    public static float easeOutQuint(float value) { return 1 - (float) Math.pow(1 - value, 5); }
    public static float easeInOutQuint(float value) { return value < 0.5 ? 16 * value * value * value * value * value : 1 - (float) Math.pow(-2 * value + 2, 5) / 2; }
    // Exponential
    public static float easeInExpo(float value) { return value == 0 ? 0 : (float) Math.pow(2, 10 * value - 10); }
    public static float easeOutExpo(float value) { return value == 1 ? 1 : 1 - (float) Math.pow(2, -10 * value); }
    public static float easeInOutExpo(float value) { return value == 0 ? 0 : value == 1 ? 1 : value < 0.5 ? (float) Math.pow(2, 20 * value - 10) / 2 : (2 - (float) Math.pow(2, -20 * value + 10)) / 2; }
    // Circular
    public static float easeInCirc(float value) { return 1 - Mth.sqrt(1 - (float) Math.pow(value, 2)); }
    public static float easeOutCirc(float value) { return Mth.sqrt(1 - (float) Math.pow(value - 1, 2)); }
    public static float easeInOutCirc(float value) { return value < 0.5 ? (1 - Mth.sqrt(1 - (float) Math.pow(2 * value, 2))) / 2 : (Mth.sqrt(1 - (float) Math.pow(-2 * value + 2, 2)) + 1) / 2; }
    // Back
    public static float easeInBack(float value, float overshoot) { return ((1.70158F * overshoot) + 1) * value * value * value - (1.70158F * overshoot) * value * value; }
    public static float easeOutBack(float value, float overshoot) { return 1 + ((1.70158F * overshoot) + 1) * (float) Math.pow(value - 1, 3) + (1.70158F * overshoot) * (float) Math.pow(value - 1, 2); }
    public static float easeInOutBack(float value, float overshoot) { return value < 0.5 ? ((float) Math.pow(2 * value, 2) * (((1.70158F * overshoot) + 1) * 2 * value - (1.70158F * overshoot))) / 2 : ((float) Math.pow(2 * value - 2, 2) * (((1.70158F * overshoot) + 1) * (value * 2 - 2) + (1.70158F * overshoot)) + 2) / 2; }
    // Elastic
    public static float easeInElastic(float value, float bounciness) { return 1 - (float) Math.pow(Mth.cos(value * Mth.PI / 2), 3) * Mth.cos(value * Mth.PI * bounciness); }
    public static float easeOutElastic(float value, float bounciness) { return 1 - (1 - (float) Math.pow(Mth.cos((1 - value) * Mth.PI / 2), 3) * Mth.cos((1 - value) * Mth.PI * bounciness)); }
    public static float easeInOutElastic(float value, float bounciness) { return value < 0.5F ? (1 - (float) Math.pow(Mth.cos(value * Mth.PI), 3) * Mth.cos(value * Mth.PI * bounciness)) / 2F : 1 - (1 - (float) Math.pow(Mth.cos((1 - value) * Mth.PI), 3) * Mth.cos((1 - value) * Mth.PI * bounciness)) / 2F; }
    // Bounce
    public static float easeInBounce(float value, float bounciness) {
        if (value < 1 / 2.75F) {
            return 7.5625F * value * value;
        } else if (value < 2 / 2.75F) {
            return 30.25F * bounciness * (float) Math.pow(value - 6F / 11F, 2) + 1 - bounciness;
        } else if (value < 2.5 / 2.75F) {
            return 121 * bounciness * bounciness * (float) Math.pow(value - 9F / 11F, 2) + 1 - bounciness * bounciness;
        } else {
            return 484 * bounciness * bounciness * bounciness * (float) Math.pow(value - 10.5F / 11F, 2) + 1 - bounciness * bounciness * bounciness;
        }
    }
    public static float easeOutBounce(float value, float bounciness) { return 1 - EasingMath.easeInBounce(1 - value, bounciness); }
    public static float easeInOutBounce(float value, float bounciness) { return value < 0.5 ? (1 - easeOutBounce(1 - 2 * value, bounciness)) / 2 : (1 + easeOutBounce(2 * value - 1, bounciness)) / 2; }
}