package andrews.table_top_craft.util.loot_table;

import com.google.common.collect.Sets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

import java.util.Set;

public class TTCLootTableHandler
{
    public static final Set<ResourceLocation> JUNGLE_TEMPLE_INJECTIONS = Sets.newHashSet(BuiltInLootTables.JUNGLE_TEMPLE);
    public static final Set<ResourceLocation> DESERT_PYRAMID_INJECTIONS = Sets.newHashSet(BuiltInLootTables.DESERT_PYRAMID);
    public static final Set<ResourceLocation> ABANDONED_MINESHAFT_INJECTIONS = Sets.newHashSet(BuiltInLootTables.ABANDONED_MINESHAFT);
    public static final Set<ResourceLocation> SIMPLE_DUNGEON_INJECTIONS = Sets.newHashSet(BuiltInLootTables.SIMPLE_DUNGEON);
    public static final Set<ResourceLocation> NETHER_BRIDGE_INJECTIONS = Sets.newHashSet(BuiltInLootTables.NETHER_BRIDGE);

//    public static void onInjectLoot(LootTableLoadEvent event)
//    {
//         Code has been moved to Mixin! (LootTablesMixin)
//    }
}