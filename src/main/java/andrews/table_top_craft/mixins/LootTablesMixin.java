package andrews.table_top_craft.mixins;

import andrews.table_top_craft.util.Reference;
import andrews.table_top_craft.util.loot_table.ITTCLootPool;
import andrews.table_top_craft.util.loot_table.TTCLootTableHandler;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LootTables.class)
public class LootTablesMixin
{
    @Unique
    private static final ThreadLocal<ResourceLocation> loc = new ThreadLocal<>();

    @Inject(at = @At(value = "HEAD"), method = "method_20711(Lcom/google/common/collect/ImmutableMap$Builder;Lnet/minecraft/resources/ResourceLocation;Lcom/google/gson/JsonElement;)V")
    private static void preLoad(ImmutableMap.Builder builder, ResourceLocation resourceLocation, JsonElement jsonElement, CallbackInfo ci)
    {
        loc.set(resourceLocation);
    }

    @ModifyVariable(method = "method_20711(Lcom/google/common/collect/ImmutableMap$Builder;Lnet/minecraft/resources/ResourceLocation;Lcom/google/gson/JsonElement;)V", at = @At(value = "STORE"))
    private static LootTable apply(LootTable lootTable)
    {
        if(lootTable instanceof ITTCLootPool)
        {
            if (TTCLootTableHandler.JUNGLE_TEMPLE_INJECTIONS.contains(loc.get()))
                ((ITTCLootPool) lootTable).addLootPool(LootPool.lootPool().add(LootTableReference.lootTableReference(new ResourceLocation(Reference.MODID, "injections/chess_piece_jungle_temple"))).build());
            if(TTCLootTableHandler.DESERT_PYRAMID_INJECTIONS.contains(loc.get()))
                ((ITTCLootPool) lootTable).addLootPool(LootPool.lootPool().add(LootTableReference.lootTableReference(new ResourceLocation(Reference.MODID, "injections/chess_piece_desert_pyramid"))).build());
            if(TTCLootTableHandler.ABANDONED_MINESHAFT_INJECTIONS.contains(loc.get()))
                ((ITTCLootPool) lootTable).addLootPool(LootPool.lootPool().add(LootTableReference.lootTableReference(new ResourceLocation(Reference.MODID, "injections/chess_piece_abandoned_mineshaft"))).build());
            if(TTCLootTableHandler.SIMPLE_DUNGEON_INJECTIONS.contains(loc.get()))
                ((ITTCLootPool) lootTable).addLootPool(LootPool.lootPool().add(LootTableReference.lootTableReference(new ResourceLocation(Reference.MODID, "injections/chess_piece_simple_dungeon"))).build());
            if(TTCLootTableHandler.NETHER_BRIDGE_INJECTIONS.contains(loc.get()))
                ((ITTCLootPool) lootTable).addLootPool(LootPool.lootPool().add(LootTableReference.lootTableReference(new ResourceLocation(Reference.MODID, "injections/chess_piece_nether_bridge"))).build());
        }
        return lootTable;
    }
}