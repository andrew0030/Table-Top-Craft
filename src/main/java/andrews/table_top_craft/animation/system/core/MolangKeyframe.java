package andrews.table_top_craft.animation.system.core;

import andrews.table_top_craft.animation.system.core.types.EasingTypes;
import andrews.table_top_craft.animation.system.core.util.DynamicExpression;
import net.minecraft.client.animation.KeyframeAnimations;
import org.joml.Vector3f;

public class MolangKeyframe extends BasicKeyframe
{
    private final Vector3f vec = new Vector3f();
    private final DynamicExpression expressionX;
    private final DynamicExpression expressionY;
    private final DynamicExpression expressionZ;
    private final char type;

    public MolangKeyframe(float timestamp, char type, String rawX, String rawY, String rawZ, EasingTypes easingType)
    {
        super(timestamp, new Vector3f(), easingType);
        this.expressionX = new DynamicExpression(rawX);
        this.expressionY =  new DynamicExpression(rawY);
        this.expressionZ = new DynamicExpression(rawZ);
        this.type = type;
    }

    @Override
    public boolean isBasic()
    {
        return false;
    }

    @Override
    public Vector3f target(float elapsedSeconds)
    {
        // We update the necessary Queries
        this.expressionX.updateVariable(DynamicExpression.QueryTypes.ANIM_TIME, elapsedSeconds);
        this.expressionY.updateVariable(DynamicExpression.QueryTypes.ANIM_TIME, elapsedSeconds);
        this.expressionZ.updateVariable(DynamicExpression.QueryTypes.ANIM_TIME, elapsedSeconds);
        // We get the values
        double x = this.expressionX.getValue();
        double y = this.expressionY.getValue();
        double z = this.expressionZ.getValue();
        return switch (this.type) {
            default -> KeyframeAnimations.posVec((float) x,(float) y,(float) z);
            case 'r' -> KeyframeAnimations.degreeVec((float) x,(float) y,(float) z);
            case 's' -> KeyframeAnimations.scaleVec((float) x,(float) y,(float) z);
        };
    }

    @Override
    public Vector3f target()
    {
        return this.vec.set(0, 0, 0);
    }
}