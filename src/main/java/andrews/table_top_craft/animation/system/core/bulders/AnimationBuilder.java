package andrews.table_top_craft.animation.system.core.bulders;

import andrews.table_top_craft.animation.system.core.Animation;
import andrews.table_top_craft.animation.system.core.KeyframeGroup;
import com.google.common.collect.Maps;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.Map;

public class AnimationBuilder
{
    private final float lengthInSeconds;
    private boolean looping;
    private final Map<String, List<KeyframeGroup>> keyframeGroups = Maps.newHashMap();

    private AnimationBuilder(float lengthInSeconds)
    {
        this.lengthInSeconds = lengthInSeconds;
    }

    public static AnimationBuilder withLength(float lengthInSeconds)
    {
        return new AnimationBuilder(lengthInSeconds);
    }

    public AnimationBuilder looping()
    {
        this.looping = true;
        return this;
    }

    public AnimationBuilder addAnimation(String boneName, KeyframeGroup keyframeGroup)
    {
        this.keyframeGroups.computeIfAbsent(boneName, (name) -> Lists.newArrayList()).add(keyframeGroup);
        return this;
    }

    public Animation build()
    {
        return new Animation(this.lengthInSeconds, this.looping, this.keyframeGroups);
    }
}