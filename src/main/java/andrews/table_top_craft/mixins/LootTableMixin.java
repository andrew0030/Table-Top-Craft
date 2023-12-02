package andrews.table_top_craft.mixins;

import andrews.table_top_craft.util.loot_table.IGetPools;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(LootTable.class)
public class LootTableMixin implements IGetPools
{
    @Shadow @Final private List<LootPool> pools;

    // TODO: remove this in 1.20.2 and use event instead (needed because mod for 1.20 and 1.20.1 wouldn't be compatible with both versions otherwise)
    @Override
    public List<LootPool> getPools()
    {
        return this.pools;
    }
}