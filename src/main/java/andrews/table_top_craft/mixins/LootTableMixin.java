package andrews.table_top_craft.mixins;

import andrews.table_top_craft.util.loot_table.ITTCLootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LootTable.class)
public class LootTableMixin implements ITTCLootPool
{
    @Shadow(remap = false)
    private boolean isFrozen;

    @Override
    public void setFreeze(boolean value)
    {
        isFrozen = value;
    }
}