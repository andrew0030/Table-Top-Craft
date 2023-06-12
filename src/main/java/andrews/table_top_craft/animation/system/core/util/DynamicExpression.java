package andrews.table_top_craft.animation.system.core.util;

import com.google.common.collect.Maps;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;

import java.util.Map;

public class DynamicExpression
{
    // Functions for our Expression
    Map<String, Double> variables = Maps.newHashMap();
    Function dcos = new Function("dcos", 1) {
        @Override
        public double apply(double... args) {
            return Math.cos(args[0] / 180 * Math.PI);
        }
    };
    Function dsin = new Function("dsin", 1) {
        @Override
        public double apply(double... args) {
            return Math.sin(args[0] / 180 * Math.PI);
        }
    };
    // Normal Fields
    private Expression expression;
    private final boolean useQuickValue;
    float quickValue;

    public DynamicExpression(String expression)
    {
        // We check if we need an Expression, otherwise we skip
        // all the other logic to save performance.
        this.useQuickValue = useQuickValue(expression);
        if(!this.useQuickValue)
        {
            // If we need an Expression we create a builder containing the values we need.
            ExpressionBuilder builder = new ExpressionBuilder(expression);
            builder.function(dcos);
            builder.function(dsin);
//            Set variables = Set.of(
//                    "anim_time"
//            );
            builder.variable("anim_time");// Until there are more this will do.
            // We build the Expression and store it in a field.
            this.expression = builder.build();
            initVariables();// We initialize the Variables to avoid Errors.
        }
    }

    /**
     * Updates the specified QueryType to the given value. Note the logic will automatically be skipped if it isn't needed.
     * @param type The QueryType to update
     * @param value The new value the QueryType should have
     */
    public void updateVariable(QueryTypes type, double value)
    {
        if(!useQuickValue)
        {
            switch (type)
            {
                case ANIM_TIME -> variables.put(QueryTypes.ANIM_TIME.getKey(), value);
            }
        }
    }

    public double getValue()
    {
        if(!this.useQuickValue)
        {
            expression.setVariables(variables);
            return expression.evaluate();
        }
        return this.quickValue;
    }

    private void initVariables()
    {
        for(String key : this.expression.getVariableNames())
        {
            this.expression.setVariable(key, 0.0F);
        }
    }

    private boolean useQuickValue(String expression)
    {
        try {
            quickValue = Float.parseFloat(expression);
        } catch (Exception ignored) {
            return false;
        }
        return true;
    }

    public enum QueryTypes
    {
        ANIM_TIME("anim_time");

        private final String key;

        QueryTypes(String key)
        {
            this.key = key;
        }

        public String getKey()
        {
            return this.key;
        }
    }
}