package andrews.table_top_craft.criteria.criteria_triggers;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

/**
 * An essentially empty CriteriaTrigger class, that allows us to easily add a bunch of Criteria quickly.
 * These Criteria are then triggered through code, as there aren't any conditions to trigger them from within a Json.
 */
public class BaseTrigger extends SimpleCriterionTrigger<BaseTrigger.TriggerInstance>
{
    @Override
    protected BaseTrigger.TriggerInstance createInstance(JsonObject json, Optional<ContextAwarePredicate> player, DeserializationContext context)
    {
        return new BaseTrigger.TriggerInstance(player);
    }

    /**
     * Called to trigger the Criteria.
     * @param serverPlayer The ServerPlayer that gets the advancement
     */
    public void trigger(ServerPlayer serverPlayer)
    {
        this.trigger(serverPlayer, triggerInstance -> true);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance
    {
        public TriggerInstance(Optional<ContextAwarePredicate> player)
        {
            super(player);
        }
    }
}