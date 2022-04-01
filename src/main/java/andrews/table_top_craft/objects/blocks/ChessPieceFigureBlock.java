package andrews.table_top_craft.objects.blocks;

import andrews.table_top_craft.screens.chess.menus.ChessBoardSettingsScreen;
import andrews.table_top_craft.screens.piece_figure.menus.ChessPieceFigureSettingsScreen;
import andrews.table_top_craft.tile_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChessPieceFigureBlock extends Block implements EntityBlock
{
    // Voxel Shape
    private static final VoxelShape CHESS_PIECE_FIGURE_BLOCK_AABB = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 2.0D, 13.0D);
    // Rotation
    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;

    public ChessPieceFigureBlock()
    {
        super(getProperties());
        this.registerDefaultState(this.stateDefinition.any().setValue(ROTATION, 0));
    }

    /**
     * @return The properties for this Block
     */
    private static Properties getProperties()
    {
        Properties properties = Block.Properties.of(Material.STONE);
        properties.strength(1.0F);
        properties.sound(SoundType.STONE);

        return properties;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        return this.defaultBlockState().setValue(ROTATION, Mth.floor((double) (pContext.getRotation() * 16.0F / 360.0F) + 0.5D) & 15);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        if (player.isShiftKeyDown())
        {
            if (level.getBlockEntity(pos) instanceof ChessPieceFigureBlockEntity chessPieceFigureBlockEntity)
            {
                if (level.isClientSide)
                    ChessPieceFigureSettingsScreen.open(chessPieceFigureBlockEntity);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        return CHESS_PIECE_FIGURE_BLOCK_AABB;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new ChessPieceFigureBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(ROTATION);
    }
}