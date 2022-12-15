package andrews.table_top_craft.registry;

import andrews.table_top_craft.util.Reference;
import andrews.table_top_craft.util.loot_table.GeneratePieceNBTFunction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

public class TTCLootItemFunctions
{
    public static final LootItemFunctionType GEN_PIECE_NBT    = Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, new ResourceLocation(Reference.MODID, "gen_piece_nbt"), new LootItemFunctionType(new GeneratePieceNBTFunction.Serializer()));

    public static void init() {}
}