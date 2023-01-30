package andrews.table_top_craft.animation.system.base;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class AnimatedBlockEntity extends BlockEntity
{
    private int ticksExisted;

    public AnimatedBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState)
    {
        super(type, pos, blockState);
    }

    /**
     * This method needs to be called inside the tick() method
     */
    public void incTicksExisted()
    {
        this.ticksExisted++;
    }

    public int getTicksExisted()
    {
        return this.ticksExisted;
    }
}