package andrews.table_top_craft.registry.util;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class BEWLRBlockItem extends BlockItem
{
    public BEWLRBlockItem(Block pBlock, Properties pProperties)
    {
        super(pBlock, pProperties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer)
    {
        super.initializeClient(consumer);
//        consumer.accept(new IClientItemExtensions()
//        {
//            @Override
//            public BlockEntityWithoutLevelRenderer getCustomRenderer()
//            {
//                return TTCBlockEntityWithoutLevelRenderer.INSTANCE;
//            }
//        });
    }
}