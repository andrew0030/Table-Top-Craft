package andrews.table_top_craft.criteria;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;

import java.util.Optional;

/**
 * An essentially empty CriteriaTrigger class, that allows us to easily add a bunch of Criteria quickly.
 * These Criteria are then triggered through code, as there aren't any conditions to trigger them from within a Json.
 */
public class BaseTrigger extends SimpleCriterionTrigger<BaseTrigger.TriggerInstance>
{
    @Override
    public Codec<BaseTrigger.TriggerInstance> codec()
    {
        return BaseTrigger.TriggerInstance.CODEC;
    }

    /**
     * Called to trigger the Criteria.
     * @param serverPlayer The ServerPlayer that gets the advancement
     */
    public void trigger(ServerPlayer serverPlayer)
    {
        this.trigger(serverPlayer, triggerInstance -> true);
    }

    public static record TriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance
    {
        public static final Codec<BaseTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(BaseTrigger.TriggerInstance::player)
        ).apply(instance, BaseTrigger.TriggerInstance::new));
    }
}