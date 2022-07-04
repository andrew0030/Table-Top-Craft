package andrews.table_top_craft.registry;

import andrews.table_top_craft.util.Reference;
import andrews.table_top_craft.util.loot_table.GeneratePieceNBTFunction;
import net.minecraft.core.Registry;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TTCLootItemFunctions
{
    public static final DeferredRegister<LootItemFunctionType> ITEM_FUNCTION_TYPES = DeferredRegister.create(Registry.LOOT_FUNCTION_REGISTRY, Reference.MODID);

    public static final RegistryObject<LootItemFunctionType> GEN_PIECE_NBT    = ITEM_FUNCTION_TYPES.register("gen_piece_nbt", () -> new LootItemFunctionType(new GeneratePieceNBTFunction.Serializer()));
}
