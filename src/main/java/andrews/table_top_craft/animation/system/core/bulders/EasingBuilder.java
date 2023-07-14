package andrews.table_top_craft.animation.system.core.bulders;

import andrews.table_top_craft.animation.system.core.types.EasingTypes;
import andrews.table_top_craft.animation.system.core.types.util.EasingType;

public class EasingBuilder
{
    private final EasingType easingType;
    private float value;

    private EasingBuilder(EasingType easingType)
    {
        this.easingType = easingType;
    }

    public static EasingBuilder type(EasingType easingType)
    {
        return new EasingBuilder(easingType);
    }

    public EasingBuilder argument(float value)
    {
        this.value = value;
        return this;
    }

    public EasingTypes build()
    {
        return new EasingTypes(this.easingType, this.value);
    }
}