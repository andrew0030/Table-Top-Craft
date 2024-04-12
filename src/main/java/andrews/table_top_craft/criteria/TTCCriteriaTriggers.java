package andrews.table_top_craft.criteria;

import andrews.table_top_craft.criteria.criteria_triggers.BaseTrigger;
import andrews.table_top_craft.util.Reference;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.resources.ResourceLocation;

public class TTCCriteriaTriggers
{
    public static final BaseTrigger MAKE_CHESS_MOVE                 = TTCCriteriaTriggers.register(new ResourceLocation(Reference.MODID, "made_chess_move"), new BaseTrigger());
    public static final BaseTrigger MAKE_CHECK_MATE_MOVE            = TTCCriteriaTriggers.register(new ResourceLocation(Reference.MODID, "made_check_mate_move"), new BaseTrigger());
    public static final BaseTrigger MAKE_EN_PASSANT_MOVE            = TTCCriteriaTriggers.register(new ResourceLocation(Reference.MODID, "made_en_passant_move"), new BaseTrigger());
    public static final BaseTrigger MAKE_CONNECT_FOUR_VICTORY_MOVE  = TTCCriteriaTriggers.register(new ResourceLocation(Reference.MODID, "made_connect_four_victory_move"), new BaseTrigger());

    // TODO: probably replace this with a proper registration in the future once its added.
    private static <T extends CriterionTrigger<?>> T register(ResourceLocation resourceLocation, T criterionTrigger) {
        if (CriteriaTriggers.CRITERIA.putIfAbsent(resourceLocation, criterionTrigger) != null) {
            throw new IllegalArgumentException("Duplicate criterion id " + resourceLocation);
        } else { return criterionTrigger; }
    }

    public static void init() {}
}