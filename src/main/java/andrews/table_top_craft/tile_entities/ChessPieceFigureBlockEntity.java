package andrews.table_top_craft.tile_entities;

import andrews.table_top_craft.registry.TTCTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ChessPieceFigureBlockEntity extends BlockEntity
{

    public ChessPieceFigureBlockEntity(BlockPos pos, BlockState state)
    {
        super(TTCTileEntities.CHESS_PIECE_FIGURE.get(), pos, state);
    }
}