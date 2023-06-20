package andrews.table_top_craft.animation.system.core;

import java.util.List;
import java.util.Map;

public class Animation
{
    private final float lengthInSeconds;
    private final boolean looping;
    private final Map<String, List<KeyframeGroup>> keyframeGroups;

    public Animation(float lengthInSeconds, boolean looping, Map<String, List<KeyframeGroup>> keyframeGroups)
    {
        this.lengthInSeconds = lengthInSeconds;
        this.looping = looping;
        this.keyframeGroups = keyframeGroups;
    }

    public float getLengthInSeconds()
    {
        return this.lengthInSeconds;
    }

    public boolean isLooping()
    {
        return this.looping;
    }

    public Map<String, List<KeyframeGroup>> getKeyframeGroups()
    {
        return this.keyframeGroups;
    }
}