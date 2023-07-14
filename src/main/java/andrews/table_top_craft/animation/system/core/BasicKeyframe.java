package andrews.table_top_craft.animation.system.core;

import andrews.table_top_craft.animation.system.core.types.EasingTypes;
import org.joml.Vector3f;

public class BasicKeyframe
{
    private final float timestamp;
    private final Vector3f target;
    private final EasingTypes easingType;

    public BasicKeyframe(float timestamp, Vector3f target, EasingTypes easingType)
    {
        this.timestamp = timestamp;
        this.target = target;
        this.easingType = easingType;
    }

    public boolean isBasic()
    {
        return true;
    }

    public float timestamp()
    {
        return this.timestamp;
    }

    public Vector3f target(float elapsedSeconds)
    {
        return target();
    }

    public Vector3f target()
    {
        return this.target;
    }

    public EasingTypes getEasingType()
    {
        return this.easingType;
    }
}