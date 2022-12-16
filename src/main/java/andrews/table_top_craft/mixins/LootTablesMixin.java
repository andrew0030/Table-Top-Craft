package andrews.table_top_craft.mixins;

import andrews.table_top_craft.util.Reference;
import andrews.table_top_craft.util.loot_table.ITTCLootPool;
import andrews.table_top_craft.util.loot_table.TTCLootTableHandler;
import com.google.common.collect.ImmutableMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LootTables.class)
public class LootTablesMixin
{
    @ModifyVariable(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At(value = "STORE"))
    public ImmutableMap<ResourceLocation, LootTable> modVarStoreMap(ImmutableMap<ResourceLocation, LootTable> map)
    {
        for(ResourceLocation resourceLocKey : map.keySet())
        {
            if (TTCLootTableHandler.JUNGLE_TEMPLE_INJECTIONS.contains(resourceLocKey))
            {
                boolean foundPoolFrozen = false;
                LootTable table = map.get(resourceLocKey);
                // We check if the Pool is already frozen and store the result
                if(table.isFrozen())
                    foundPoolFrozen = true;
                // If the Pool was frozen we unfreeze it
                if(foundPoolFrozen && table instanceof ITTCLootPool ttcPool)
                    ttcPool.setFreeze(false);
                // We do our thing
                table.addPool(LootPool.lootPool().add(LootTableReference.lootTableReference(new ResourceLocation(Reference.MODID, "injections/chess_piece_jungle_temple"))).build());
                // If the Pool was frozen we freeze it again
                if(foundPoolFrozen)
                    table.freeze();
            }
            if (TTCLootTableHandler.DESERT_PYRAMID_INJECTIONS.contains(resourceLocKey))
            {
                boolean foundPoolFrozen = false;
                LootTable table = map.get(resourceLocKey);
                // We check if the Pool is already frozen and store the result
                if(table.isFrozen())
                    foundPoolFrozen = true;
                // If the Pool was frozen we unfreeze it
                if(foundPoolFrozen && table instanceof ITTCLootPool ttcPool)
                    ttcPool.setFreeze(false);
                // We do our thing
                table.addPool(LootPool.lootPool().add(LootTableReference.lootTableReference(new ResourceLocation(Reference.MODID, "injections/chess_piece_desert_pyramid"))).build());
                // If the Pool was frozen we freeze it again
                if(foundPoolFrozen)
                    table.freeze();
            }
            if (TTCLootTableHandler.ABANDONED_MINESHAFT_INJECTIONS.contains(resourceLocKey))
            {
                boolean foundPoolFrozen = false;
                LootTable table = map.get(resourceLocKey);
                // We check if the Pool is already frozen and store the result
                if(table.isFrozen())
                    foundPoolFrozen = true;
                // If the Pool was frozen we unfreeze it
                if(foundPoolFrozen && table instanceof ITTCLootPool ttcPool)
                    ttcPool.setFreeze(false);
                // We do our thing
                table.addPool(LootPool.lootPool().add(LootTableReference.lootTableReference(new ResourceLocation(Reference.MODID, "injections/chess_piece_abandoned_mineshaft"))).build());
                // If the Pool was frozen we freeze it again
                if(foundPoolFrozen)
                    table.freeze();
            }
            if (TTCLootTableHandler.SIMPLE_DUNGEON_INJECTIONS.contains(resourceLocKey))
            {
                boolean foundPoolFrozen = false;
                LootTable table = map.get(resourceLocKey);
                // We check if the Pool is already frozen and store the result
                if(table.isFrozen())
                    foundPoolFrozen = true;
                // If the Pool was frozen we unfreeze it
                if(foundPoolFrozen && table instanceof ITTCLootPool ttcPool)
                    ttcPool.setFreeze(false);
                // We do our thing
                table.addPool(LootPool.lootPool().add(LootTableReference.lootTableReference(new ResourceLocation(Reference.MODID, "injections/chess_piece_simple_dungeon"))).build());
                // If the Pool was frozen we freeze it again
                if(foundPoolFrozen)
                    table.freeze();
            }
            if (TTCLootTableHandler.NETHER_BRIDGE_INJECTIONS.contains(resourceLocKey))
            {
                boolean foundPoolFrozen = false;
                LootTable table = map.get(resourceLocKey);
                // We check if the Pool is already frozen and store the result
                if(table.isFrozen())
                    foundPoolFrozen = true;
                // If the Pool was frozen we unfreeze it
                if(foundPoolFrozen && table instanceof ITTCLootPool ttcPool)
                    ttcPool.setFreeze(false);
                // We do our thing
                table.addPool(LootPool.lootPool().add(LootTableReference.lootTableReference(new ResourceLocation(Reference.MODID, "injections/chess_piece_nether_bridge"))).build());
                // If the Pool was frozen we freeze it again
                if(foundPoolFrozen)
                    table.freeze();
            }
        }
        return map;
    }
}