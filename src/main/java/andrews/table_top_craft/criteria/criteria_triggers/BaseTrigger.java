package andrews.table_top_craft.criteria.criteria_triggers;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

/**
 * An essentially empty CriteriaTrigger class, that allows us to easily add
 * a bunch of Criteria quickly, by just passing a ResourceLocation as an ID.
 * These Criteria are then triggered through code, as there aren't any
 * conditions to trigger them from within a Json.
 */
public class BaseTrigger extends SimpleCriterionTrigger<BaseTrigger.TriggerInstance>
{
    protected final ResourceLocation id;

    /**
     * @param id The ID we use inside Json's to call this CriteriaTrigger
     */
    public BaseTrigger(ResourceLocation id)
    {
        this.id = id;
    }

    @Override
    public ResourceLocation getId()
    {
        return this.id;
    }

    @Override
    protected BaseTrigger.TriggerInstance createInstance(JsonObject json, EntityPredicate.Composite player, DeserializationContext context)
    {
        return new TriggerInstance(this.id, player);
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
        public TriggerInstance(ResourceLocation id, EntityPredicate.Composite player)
        {
            super(id, player);
        }
    }
}