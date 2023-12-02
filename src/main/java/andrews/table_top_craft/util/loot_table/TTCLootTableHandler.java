package andrews.table_top_craft.util.loot_table;

import andrews.table_top_craft.util.Reference;
import com.google.common.collect.Sets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class TTCLootTableHandler
{
    public static final Set<ResourceLocation> JUNGLE_TEMPLE_INJECTIONS = Sets.newHashSet(BuiltInLootTables.JUNGLE_TEMPLE);
    public static final Set<ResourceLocation> DESERT_PYRAMID_INJECTIONS = Sets.newHashSet(BuiltInLootTables.DESERT_PYRAMID);
    public static final Set<ResourceLocation> ABANDONED_MINESHAFT_INJECTIONS = Sets.newHashSet(BuiltInLootTables.ABANDONED_MINESHAFT);
    public static final Set<ResourceLocation> SIMPLE_DUNGEON_INJECTIONS = Sets.newHashSet(BuiltInLootTables.SIMPLE_DUNGEON);
    public static final Set<ResourceLocation> NETHER_BRIDGE_INJECTIONS = Sets.newHashSet(BuiltInLootTables.NETHER_BRIDGE);

    @SubscribeEvent
    public static void onInjectLoot(LootTableLoadEvent event)
    {
        if(JUNGLE_TEMPLE_INJECTIONS.contains(event.getName()))
            TTCLootTableHandler.addPool(event.getTable(), LootPool.lootPool().add(LootTableReference.lootTableReference(new ResourceLocation(Reference.MODID, "injections/chess_piece_jungle_temple"))).build());
        if(DESERT_PYRAMID_INJECTIONS.contains(event.getName()))
            TTCLootTableHandler.addPool(event.getTable(), LootPool.lootPool().add(LootTableReference.lootTableReference(new ResourceLocation(Reference.MODID, "injections/chess_piece_desert_pyramid"))).build());
        if(ABANDONED_MINESHAFT_INJECTIONS.contains(event.getName()))
            TTCLootTableHandler.addPool(event.getTable(), LootPool.lootPool().add(LootTableReference.lootTableReference(new ResourceLocation(Reference.MODID, "injections/chess_piece_abandoned_mineshaft"))).build());
        if(SIMPLE_DUNGEON_INJECTIONS.contains(event.getName()))
            TTCLootTableHandler.addPool(event.getTable(), LootPool.lootPool().add(LootTableReference.lootTableReference(new ResourceLocation(Reference.MODID, "injections/chess_piece_simple_dungeon"))).build());
        if(NETHER_BRIDGE_INJECTIONS.contains(event.getName()))
            TTCLootTableHandler.addPool(event.getTable(), LootPool.lootPool().add(LootTableReference.lootTableReference(new ResourceLocation(Reference.MODID, "injections/chess_piece_nether_bridge"))).build());
    }

    @Deprecated // TODO: Remove in 1.20.2 and later
    public static void addPool(LootTable table, LootPool pool)
    {
        if(table instanceof IGetPools accessiblePoolsTable)
        {
            if (table.isFrozen())
                throw new RuntimeException("Attempted to modify LootTable after being finalized!");
            accessiblePoolsTable.getPools().add(pool);
        }
    }
}