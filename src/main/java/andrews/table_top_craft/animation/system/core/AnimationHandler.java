package andrews.table_top_craft.animation.system.core;

import andrews.table_top_craft.animation.model.AdvancedModelPart;
import andrews.table_top_craft.animation.model.IAnimatedModel;
import andrews.table_top_craft.animation.system.core.types.EasingTypes;
import andrews.table_top_craft.animation.system.core.types.util.EasingMath;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AnimationHandler
{
    public static void animate(IAnimatedModel model, AdvancedAnimationState state, Vector3f animationVecCache)
    {
        Animation animation = state.getAnimation();
        float elapsedSeconds = AnimationHandler.getElapsedSeconds(state);
        if(state.getOutTime() != 0)
            elapsedSeconds = state.getPrevElapsedTime();

        // We need to keep track of the size of the map, so we can perform logic on its last iteration
        int entries = animation.getKeyframeGroups().entrySet().size();
        for(Map.Entry<String, List<KeyframeGroup>> map : animation.getKeyframeGroups().entrySet())
        {
            entries--;
            List<KeyframeGroup> keyframeGroupList = map.getValue();
            Optional<ModelPart> modelPartOptional = model.getAnyDescendantWithName(map.getKey());
            if(modelPartOptional.isPresent() && modelPartOptional.get() instanceof AdvancedModelPart modelPart)//TODO yeet this and use mixin to add a better performing check
            {
                for(KeyframeGroup keyframeGroup : keyframeGroupList)
                {
                    BasicKeyframe[] keyframes = keyframeGroup.getKeyframes();

                    if(state.getOutTime() == 0)
                        AnimationHandler.processIdxCache(state, keyframeGroupList, keyframeGroup, keyframes, entries, elapsedSeconds);

                    int currentKeyframeIdx = state.cachedIndex.get(keyframeGroup);
                    int lastKeyframeIdx = Math.max(0, currentKeyframeIdx - 1);

                    BasicKeyframe currentKeyframe = keyframes[currentKeyframeIdx];
                    BasicKeyframe lastKeyframe = keyframes[lastKeyframeIdx];
                    float timeStampMod = state.getInTime(); // We need this to shift timestamps
                    float elapsedDelta = elapsedSeconds - (lastKeyframe.timestamp() + timeStampMod);
                    float keyframeDelta = Mth.clamp(elapsedDelta / ((currentKeyframe.timestamp() + timeStampMod) - (lastKeyframe.timestamp() + timeStampMod)), 0.0F, 1.0F);
                    if(Float.isNaN(keyframeDelta))
                        keyframeDelta = 0.0F;

                    float actualElapsed = AnimationHandler.getElapsedSeconds(state);
                    float targetTime = state.forceOutLinear() ? elapsedSeconds : actualElapsed;
                    float lastTargetTime = AnimationHandler.getLastTargetTime(state, elapsedSeconds, actualElapsed);

                    currentKeyframe.getEasingType().storeEasedValues(animationVecCache, keyframeDelta, keyframes, lastKeyframe.target(lastTargetTime), currentKeyframeIdx, targetTime);

                    float interpolFactor = 1.0F;
                    if(state.getInTime() != 0) {
                        float time = state.getInTime();
                        float modifier = 1 - (1 - Math.min(time, elapsedSeconds) / time);
                        if(state.getEaseInType() != null)
                            modifier = applyEasing(state.getEaseInType(), modifier);
                        interpolFactor = modifier;
                    }
                    if(state.getOutTime() != 0) {
                        float time = state.getOutTime();
                        float modifier = 1 - (1 - (1 - Math.min(time, actualElapsed - elapsedSeconds) / time));
                        if(state.getEaseOutType() != null)
                            modifier = applyEasing(state.getEaseOutType(), modifier);
                        interpolFactor *= modifier;
                    }

                    keyframeGroup.getTransformType().applyValues(modelPart, animationVecCache.mul(interpolFactor));

                    // Handles stopping animations after easing out is finished
                    if(state.getOutTime() != 0)
                        if(interpolFactor == 0)
                            if(entries <= 0)
                                if(isLastTransformType(keyframeGroup, keyframeGroupList))
                                    state.stop();
                }
            }
        }
    }

    public static float getElapsedSeconds(AdvancedAnimationState state)
    {
        float accumulatedSeconds = (float)state.getAccumulatedTime() / 1000.0F;
        float animLength = state.getAnimation().getLengthInSeconds() + state.getInTime() + state.getOutTime();
        return state.getAnimation().isLooping() ? accumulatedSeconds % animLength : accumulatedSeconds;
    }

    private static void processIdxCache(AdvancedAnimationState state, List<KeyframeGroup> keyframeGroupList, KeyframeGroup keyframeGroup, BasicKeyframe[] keyframes, int entries, float elapsedSeconds)
    {
//        float elapsedSeconds = AnimationHandler.getElapsedSeconds(state);
        Animation animation = state.getAnimation();
        float animLength = animation.getLengthInSeconds() + state.getInTime() + state.getOutTime(); // We need this shift animation length

        if(!state.cachedIndex.containsKey(keyframeGroup))
            state.cachedIndex.put(keyframeGroup, 0);
        if(state.getAccumulatedTime() >= animLength * 1000.0F && animation.isLooping())
        {
            state.cachedIndex.put(keyframeGroup, 0);
            if(entries <= 0)
                //On loop if there are multiple Transform Types we need to do this on last, otherwise later don't get to reset
                if(isLastTransformType(keyframeGroup, keyframeGroupList))
                {
                    state.accumulatedTime -= animLength * 1000.0F;
                    if(!state.keepEasingIn()) // If the animation should only ease in once we reset ease in time.
                        if(state.getInTime() != 0)
                            state.resetInTime();
                }
        }
        // If enough time passed we increase the current Idx and cache the last Vec
        float timeStampMod = state.getInTime(); // We need this shift timestamps
        if(keyframes[state.cachedIndex.get(keyframeGroup)].timestamp() + timeStampMod <= elapsedSeconds)//TODO maybe optimize this by preventing its execution after executing once on last iteration
            state.cachedIndex.put(keyframeGroup, Math.min(keyframes.length - 1, state.cachedIndex.get(keyframeGroup) + 1));
    }

    private static boolean isLastTransformType(KeyframeGroup keyframeGroup, List<KeyframeGroup> keyframeGroupList)
    {
        // If there is only a single Transform Type assigned, it's automatically the last.
        if(keyframeGroupList.size() == 1) return true;
        int maxPriority = keyframeGroupList.stream()
                .mapToInt(kg -> kg.getTransformType().getPriority())
                .max()
                .orElse(0);
        return keyframeGroup.getTransformType().getPriority() == maxPriority;
    }

    private static float getLastTargetTime(AdvancedAnimationState state, float elapsedSeconds, float actualElapsed)
    {
        // In=true and Out=true
        if (state.forceInLinear() && state.forceOutLinear()) {
            return state.getInTime();
        } // In=false and Out=true
        else if (!state.forceInLinear() && state.forceOutLinear()) {
            return state.getOutTime() != 0 ? elapsedSeconds : actualElapsed;
        } // In=true and Out=false
        else if (state.forceInLinear() && !state.forceOutLinear()) {
            return state.getOutTime() != 0 ? actualElapsed - (elapsedSeconds - state.getInTime()) : state.getInTime();
        } // In=false and Out=false
        else {
            return actualElapsed;
        }
    }

    private static float applyEasing(EasingTypes type, float value)
    {
        return switch (type.getEasingType())
        {
            default -> value;
            case STEPS -> EasingMath.easeSteps(value, type.getOptionalValue());
            case EASE_IN_SINE -> EasingMath.easeInSine(value);
            case EASE_OUT_SINE -> EasingMath.easeOutSine(value);
            case EASE_IN_OUT_SINE -> EasingMath.easeInOutSine(value);
            case EASE_IN_QUAD -> EasingMath.easeInQuad(value);
            case EASE_OUT_QUAD -> EasingMath.easeOutQuad(value);
            case EASE_IN_OUT_QUAD -> EasingMath.easeInOutQuad(value);
            case EASE_IN_CUBIC -> EasingMath.easeInCubic(value);
            case EASE_OUT_CUBIC -> EasingMath.easeOutCubic(value);
            case EASE_IN_OUT_CUBIC -> EasingMath.easeInOutCubic(value);
            case EASE_IN_QUART -> EasingMath.easeInQuart(value);
            case EASE_OUT_QUART -> EasingMath.easeOutQuart(value);
            case EASE_IN_OUT_QUART -> EasingMath.easeInOutQuart(value);
            case EASE_IN_QUINT -> EasingMath.easeInQuint(value);
            case EASE_OUT_QUINT -> EasingMath.easeOutQuint(value);
            case EASE_IN_OUT_QUINT -> EasingMath.easeInOutQuint(value);
            case EASE_IN_EXPO -> EasingMath.easeInExpo(value);
            case EASE_OUT_EXPO -> EasingMath.easeOutExpo(value);
            case EASE_IN_OUT_EXPO -> EasingMath.easeInOutExpo(value);
            case EASE_IN_CIRC -> EasingMath.easeInCirc(value);
            case EASE_OUT_CIRC -> EasingMath.easeOutCirc(value);
            case EASE_IN_OUT_CIRC -> EasingMath.easeInOutCirc(value);
            case EASE_IN_BACK -> EasingMath.easeInBack(value, type.getOptionalValue());
            case EASE_OUT_BACK -> EasingMath.easeOutBack(value, type.getOptionalValue());
            case EASE_IN_OUT_BACK -> EasingMath.easeInOutBack(value, type.getOptionalValue());
            case EASE_IN_ELASTIC -> EasingMath.easeInElastic(value, type.getOptionalValue());
            case EASE_OUT_ELASTIC -> EasingMath.easeOutElastic(value, type.getOptionalValue());
            case EASE_IN_OUT_ELASTIC -> EasingMath.easeInOutElastic(value, type.getOptionalValue());
            case EASE_IN_BOUNCE -> EasingMath.easeInBounce(value, type.getOptionalValue());
            case EASE_OUT_BOUNCE -> EasingMath.easeOutBounce(value, type.getOptionalValue());
            case EASE_IN_OUT_BOUNCE -> EasingMath.easeInOutBounce(value, type.getOptionalValue());
        };
    }
}