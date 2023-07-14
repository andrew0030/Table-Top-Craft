package andrews.table_top_craft.animation.system.core.types;

import net.minecraft.client.model.geom.ModelPart;
import org.joml.Vector3f;

public class TransformTypes
{
    private final TransformType transformType;
    private final int priority;
    // Transform Types
    public static final TransformTypes POSITION = new TransformTypes(TransformType.POSITION, 1);
    public static final TransformTypes ROTATION = new TransformTypes(TransformType.ROTATION, 2);
    public static final TransformTypes SCALE = new TransformTypes(TransformType.SCALE, 3);

    private TransformTypes(TransformType transformType, int priority)
    {
        this.transformType = transformType;
        this.priority = priority;
    }

    /**
     * @return The priority of the TransformType
     */
    public int getPriority()
    {
        return this.priority;
    }

    /**
     * Based on the current TransformType the given Vector values will
     * be applied to the corresponding ModelPart's POSITION-ROTATION-SCALE
     * @param modelPart The ModelPart that will be offset
     * @param valuesToApply A Vector3f holding the X/Y/Z values to be applied
     */
    public void applyValues(ModelPart modelPart, Vector3f valuesToApply)
    {
        switch(this.transformType)
        {
            case POSITION -> modelPart.offsetPos(valuesToApply);
            case ROTATION -> modelPart.offsetRotation(valuesToApply);
            case SCALE -> modelPart.offsetScale(valuesToApply);
        }
    }

    private enum TransformType
    {
        POSITION,
        ROTATION,
        SCALE;
    }
}