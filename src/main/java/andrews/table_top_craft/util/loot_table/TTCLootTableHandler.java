package andrews.table_top_craft.util.loot_table;

import andrews.table_top_craft.util.Reference;
import com.google.common.collect.Sets;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;

import java.util.Set;

public class TTCLootTableHandler
{
    public static final Set<ResourceLocation> JUNGLE_TEMPLE_INJECTIONS = Sets.newHashSet(BuiltInLootTables.JUNGLE_TEMPLE);
    public static final Set<ResourceLocation> DESERT_PYRAMID_INJECTIONS = Sets.newHashSet(BuiltInLootTables.DESERT_PYRAMID);
    public static final Set<ResourceLocation> ABANDONED_MINESHAFT_INJECTIONS = Sets.newHashSet(BuiltInLootTables.ABANDONED_MINESHAFT);
    public static final Set<ResourceLocation> SIMPLE_DUNGEON_INJECTIONS = Sets.newHashSet(BuiltInLootTables.SIMPLE_DUNGEON);
    public static final Set<ResourceLocation> NETHER_BRIDGE_INJECTIONS = Sets.newHashSet(BuiltInLootTables.NETHER_BRIDGE);

    public static void init()
    {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (source.isBuiltin() && JUNGLE_TEMPLE_INJECTIONS.contains(id))
                tableBuilder.pool(LootPool.lootPool().add(LootTableReference.lootTableReference(new ResourceLocation(Reference.MODID, "injections/chess_piece_jungle_temple"))).build());
            if (source.isBuiltin() && DESERT_PYRAMID_INJECTIONS.contains(id))
                tableBuilder.pool(LootPool.lootPool().add(LootTableReference.lootTableReference(new ResourceLocation(Reference.MODID, "injections/chess_piece_desert_pyramid"))).build());
            if (source.isBuiltin() && ABANDONED_MINESHAFT_INJECTIONS.contains(id))
                tableBuilder.pool(LootPool.lootPool().add(LootTableReference.lootTableReference(new ResourceLocation(Reference.MODID, "injections/chess_piece_abandoned_mineshaft"))).build());
            if (source.isBuiltin() && SIMPLE_DUNGEON_INJECTIONS.contains(id))
                tableBuilder.pool(LootPool.lootPool().add(LootTableReference.lootTableReference(new ResourceLocation(Reference.MODID, "injections/chess_piece_simple_dungeon"))).build());
            if (source.isBuiltin() && NETHER_BRIDGE_INJECTIONS.contains(id))
                tableBuilder.pool(LootPool.lootPool().add(LootTableReference.lootTableReference(new ResourceLocation(Reference.MODID, "injections/chess_piece_nether_bridge"))).build());
        });
    }
}