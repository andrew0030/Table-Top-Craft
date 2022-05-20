package andrews.table_top_craft.mixins;

import andrews.table_top_craft.util.loot_table.ITTCLootPool;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Arrays;

@Mixin(LootTable.class)
public class LootTableMixin implements ITTCLootPool
{
    @Shadow
    @Mutable
    LootPool[] pools;

    @Override
    public void addLootPool(LootPool lootPool)
    {
        /*
        Its a real shame literally everything is a forge patch!
        Oh well, if there is ever a duplicate and shit blows up I may come back to this
        for now "Alexa play Despacito"
         */
//        for (LootPool entry : pools)
//            if (entry.getName() == pool.getName())
//                throw new RuntimeException("Attempting to add a duplicate pool " + entry.getName() + " to loot table");
        this.pools = Arrays.copyOf(this.pools, this.pools.length + 1);
        this.pools[this.pools.length - 1] = lootPool;
    }
}