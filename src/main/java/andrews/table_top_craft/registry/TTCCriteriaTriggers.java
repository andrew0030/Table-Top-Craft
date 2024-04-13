package andrews.table_top_craft.registry;

import andrews.table_top_craft.criteria.BaseTrigger;
import andrews.table_top_craft.util.Reference;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class TTCCriteriaTriggers
{
    public static final BaseTrigger MAKE_CHESS_MOVE                 = Registry.register(BuiltInRegistries.TRIGGER_TYPES, new ResourceLocation(Reference.MODID, "made_chess_move"), new BaseTrigger());
    public static final BaseTrigger MAKE_CHECK_MATE_MOVE            = Registry.register(BuiltInRegistries.TRIGGER_TYPES, new ResourceLocation(Reference.MODID, "made_check_mate_move"), new BaseTrigger());
    public static final BaseTrigger MAKE_EN_PASSANT_MOVE            = Registry.register(BuiltInRegistries.TRIGGER_TYPES, new ResourceLocation(Reference.MODID, "made_en_passant_move"), new BaseTrigger());
    public static final BaseTrigger MAKE_CONNECT_FOUR_VICTORY_MOVE  = Registry.register(BuiltInRegistries.TRIGGER_TYPES, new ResourceLocation(Reference.MODID, "made_connect_four_victory_move"), new BaseTrigger());

    public static void init() {}
}