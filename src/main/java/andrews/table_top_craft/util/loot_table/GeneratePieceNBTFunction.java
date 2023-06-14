package andrews.table_top_craft.util.loot_table;

import andrews.table_top_craft.registry.TTCBlocks;
import andrews.table_top_craft.registry.TTCLootItemFunctions;
import andrews.table_top_craft.block_entities.ChessPieceFigureBlockEntity;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class GeneratePieceNBTFunction extends LootItemConditionalFunction
{
    public GeneratePieceNBTFunction(LootItemCondition[] pConditions)
    {
        super(pConditions);
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext context)
    {
        RandomSource random = context.getRandom();
        if(stack.is(TTCBlocks.CHESS_PIECE_FIGURE.get().asItem()))
        {
            ChessPieceFigureBlockEntity blockEntity = new ChessPieceFigureBlockEntity(BlockPos.ZERO, TTCBlocks.CHESS_PIECE_FIGURE.get().defaultBlockState());
            // We randomly generate the values for this chess piece
            blockEntity.setPieceSet(random.nextInt(3) + 1);
            blockEntity.setPieceType(random.nextInt(6) + 1);
            // We store the values on the ItemStack
            blockEntity.saveToItem(stack);
        }
        return stack;
    }

    @Override
    public LootItemFunctionType getType()
    {
        return TTCLootItemFunctions.GEN_PIECE_NBT.get();
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<GeneratePieceNBTFunction>
    {
        @Override
        public void serialize(JsonObject json, GeneratePieceNBTFunction value, JsonSerializationContext serializationContext)
        {
            super.serialize(json, value, serializationContext);
            // We don't really need to serialize anything, as there is no input from the users.
        }

        @Override
        public GeneratePieceNBTFunction deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootItemCondition[] conditions)
        {
            return new GeneratePieceNBTFunction(conditions);
        }
    }
}