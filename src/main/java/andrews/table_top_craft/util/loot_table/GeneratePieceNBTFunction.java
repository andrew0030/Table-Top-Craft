package andrews.table_top_craft.util.loot_table;

import andrews.table_top_craft.block_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.registry.TTCBlocks;
import andrews.table_top_craft.registry.TTCLootItemFunctions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;

public class GeneratePieceNBTFunction extends LootItemConditionalFunction
{
    public static final Codec<GeneratePieceNBTFunction> CODEC = RecordCodecBuilder.create(instance -> commonFields(instance).apply(instance, GeneratePieceNBTFunction::new));

    public GeneratePieceNBTFunction(List<LootItemCondition> list)
    {
        super(list);
    }

    @Override
    public LootItemFunctionType getType()
    {
        return TTCLootItemFunctions.GEN_PIECE_NBT;
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext context)
    {
        RandomSource random = context.getRandom();
        if(stack.is(TTCBlocks.CHESS_PIECE_FIGURE.asItem()))
        {
            ChessPieceFigureBlockEntity blockEntity = new ChessPieceFigureBlockEntity(BlockPos.ZERO, TTCBlocks.CHESS_PIECE_FIGURE.defaultBlockState());
            // We randomly generate the values for this chess piece
            blockEntity.setPieceSet(random.nextInt(3) + 1);
            blockEntity.setPieceType(random.nextInt(6) + 1);
            // We store the values on the ItemStack
            blockEntity.saveToItem(stack);
        }
        return stack;
    }
}