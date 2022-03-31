package andrews.table_top_craft.registry.util;

import andrews.table_top_craft.tile_entities.render.item.TTCBlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.IItemRenderProperties;

import java.util.function.Consumer;

public class BEWLRBlockItem extends BlockItem
{
    public BEWLRBlockItem(Block pBlock, Properties pProperties)
    {
        super(pBlock, pProperties);
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer)
    {
        super.initializeClient(consumer);
        consumer.accept(new IItemRenderProperties()
        {
            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer()
            {
                return TTCBlockEntityWithoutLevelRenderer.INSTANCE;
            }
        });
    }
}