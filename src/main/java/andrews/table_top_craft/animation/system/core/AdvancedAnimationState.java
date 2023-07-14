package andrews.table_top_craft.animation.system.core;

import andrews.table_top_craft.animation.system.core.types.EasingTypes;
import com.google.common.collect.Maps;
import net.minecraft.world.entity.AnimationState;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class AdvancedAnimationState extends AnimationState
{
    public Map<KeyframeGroup, Integer> cachedIndex = Maps.newHashMap();
    private final AtomicReference<Animation> animation;
    // Interpolation
    private float easeInTime = 0;
    private float easeOutTime = 0;
    private float prevElapsedTime = 0;
    private boolean keepEasingIn = true;
    private EasingTypes easeInType = null;
    private EasingTypes easeOutType = null;
    private boolean forceInLinear = false;
    private boolean forceOutLinear = false;

    public AdvancedAnimationState(AtomicReference<Animation> animation)
    {
        this.animation = animation;
    }

    public AdvancedAnimationState(AdvancedAnimationState state)
    {
        this.lastTime = state.lastTime;
        this.accumulatedTime = state.accumulatedTime;
        this.cachedIndex.putAll(state.cachedIndex);
        this.animation = new AtomicReference<>(state.getAnimation());
        this.easeInTime = state.getInTime();
        this.easeOutTime = state.getOutTime();
        this.prevElapsedTime = state.getPrevElapsedTime();
        this.keepEasingIn = state.keepEasingIn();
        this.easeInType = state.getEaseInType();
        this.easeOutType = state.getEaseOutType();
        this.forceInLinear = state.forceInLinear();
        this.forceOutLinear = state.forceOutLinear();
    }

    public Animation getAnimation()
    {
        return this.animation.get();
    }

    public void setAnimation(Animation animation)
    {
        this.animation.set(animation);
    }

    @Override
    public void stop()
    {
        super.stop();
        // When we stop an Animation we need to clear the caches
        this.cachedIndex.clear();
        this.easeInTime = 0;
        this.easeOutTime = 0;
        this.prevElapsedTime = 0;
    }

    public float getInTime()
    {
        return this.easeInTime;
    }

    public void resetInTime()
    {
        this.easeInTime = 0.0F;
    }

    public float getOutTime()
    {
        return this.easeOutTime;
    }

    public boolean keepEasingIn()
    {
        return this.keepEasingIn;
    }

    public EasingTypes getEaseInType()
    {
        return this.easeInType;
    }

    public EasingTypes getEaseOutType()
    {
        return this.easeOutType;
    }

    public boolean forceInLinear()
    {
        return this.forceInLinear;
    }

    public boolean forceOutLinear()
    {
        return this.forceOutLinear;
    }

    public void interpolateAndStart(float easeInTime, EasingTypes easeInType, boolean forceInLinear, int ageInTicks)
    {
        this.interpolateAndStart(easeInTime, easeInType, forceInLinear, ageInTicks, true);
    }

    public void interpolateAndStart(float easeInTime, EasingTypes easeInType, boolean forceInLinear, int ageInTicks, boolean keepEasingIn)
    {
        this.keepEasingIn = keepEasingIn;
        this.easeInTime = easeInTime;
        this.easeInType = easeInType;
        this.forceInLinear = forceInLinear;
        this.startIfStopped(ageInTicks);
    }

    public void interpolateAndStop(float easeOutTime, EasingTypes easeOutType, boolean forceOutLinear)
    {
        if(this.getAnimation() != null && this.isStarted())
        {
            this.easeOutType = easeOutType;
            this.forceOutLinear = forceOutLinear;
            // If the Animation is easing in, the ease out time is set to the elapsed time
            float elapsedSeconds = AnimationHandler.getElapsedSeconds(this);
            if (this.getInTime() != 0 && elapsedSeconds < this.getInTime() && easeOutTime >= this.getInTime())
                easeOutTime = elapsedSeconds;

            this.prevElapsedTime = elapsedSeconds;
            this.easeOutTime = easeOutTime;
        }
    }

    public float getPrevElapsedTime()
    {
        return this.prevElapsedTime;
    }

    public boolean isFinished()
    {
        if(this.getAnimation() == null) // We need to make sure the Animation exists
            return false;
        if(this.getAnimation().isLooping() || this.getAnimation().getLengthInSeconds() == 0)
            return false;
        return AnimationHandler.getElapsedSeconds(this) > this.getAnimation().getLengthInSeconds() + this.easeInTime + this.easeOutTime;
    }
}