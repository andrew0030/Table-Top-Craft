package andrews.table_top_craft.criteria;

import andrews.table_top_craft.criteria.criteria_triggers.BaseTrigger;
import andrews.table_top_craft.util.Reference;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class TTCCriteriaTriggers
{
    public static final BaseTrigger MAKE_CHESS_MOVE = CriteriaTriggers.register(new BaseTrigger(new ResourceLocation(Reference.MODID, "made_chess_move")));
    public static final BaseTrigger MAKE_CHECK_MATE_MOVE = CriteriaTriggers.register(new BaseTrigger(new ResourceLocation(Reference.MODID, "made_check_mate_move")));
    public static final BaseTrigger MAKE_EN_PASSANT_MOVE = CriteriaTriggers.register(new BaseTrigger(new ResourceLocation(Reference.MODID, "made_en_passant_move")));
    public static final BaseTrigger MAKE_CONNECT_FOUR_VICTORY_MOVE = CriteriaTriggers.register(new BaseTrigger(new ResourceLocation(Reference.MODID, "made_connect_four_victory_move")));
}