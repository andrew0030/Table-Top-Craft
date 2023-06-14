package andrews.table_top_craft.objects.blocks;

import andrews.table_top_craft.block_entities.ConnectFourBlockEntity;
import andrews.table_top_craft.util.Color;
import andrews.table_top_craft.util.NetworkUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ConnectFourBlock extends Block  implements EntityBlock
{
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    // X Axis
    private static final VoxelShape BASE_X = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 2.0D, 10.0D);
    private static final VoxelShape LEFT_POLE_X = Block.box(0.5D, 2.0D, 7.0D, 1.5D, 14.0D, 9.0D);
    private static final VoxelShape RIGHT_POLE_X = Block.box(14.5D, 2.0D, 7.0D, 15.5D, 14.0D, 9.0D);
    private static final VoxelShape CENTER_X = Block.box(1.5D, 2.0D, 7.5D, 14.5D, 13.0D, 8.5D);
    // Y Axis
    private static final VoxelShape BASE_Y = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 2.0D, 16.0D);
    private static final VoxelShape LEFT_POLE_Y = Block.box(7.0D, 2.0D, 0.5D, 9.0D, 14.0D, 1.5D);
    private static final VoxelShape RIGHT_POLE_Y = Block.box(7.0D, 2.0D, 14.5D, 9.0D, 14.0D, 15.5D);
    private static final VoxelShape CENTER_Y = Block.box(7.5D, 2.0D, 1.5D, 8.5D, 13.0D, 14.5D);
    // AABB's
    private static final VoxelShape X_AXIS_AABB = Shapes.or(BASE_X, LEFT_POLE_X, RIGHT_POLE_X, CENTER_X);
    private static final VoxelShape Y_AXIS_AABB = Shapes.or(BASE_Y, LEFT_POLE_Y, RIGHT_POLE_Y, CENTER_Y);

    public ConnectFourBlock(Material material, SoundType soundType)
    {
        super(getProperties(material, soundType));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    private static Properties getProperties(Material material, SoundType soundType)
    {
        Properties properties = Block.Properties.of(material);
        properties.sound(soundType);
        properties.strength(2.0F);
        properties.noOcclusion();
        return properties;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        Direction blockFace = state.getValue(FACING);
        Direction hitFace = hit.getDirection();

        if(player.isShiftKeyDown())
        {
            if(level.getBlockEntity(pos) instanceof ConnectFourBlockEntity blockEntity)
            {
                blockEntity.reset();
                level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
                blockEntity.setChanged();
                return InteractionResult.SUCCESS;
            }
        }
        else
        {
            if((hitFace.equals(blockFace) || hitFace.equals(blockFace.getOpposite())) && (hit.getLocation().y - pos.getY()) > 0.125F && (hit.getLocation().y - pos.getY()) < 0.8125F)
            {
                if(((blockFace.equals(Direction.NORTH) || blockFace.equals(Direction.SOUTH)) && (hit.getLocation().z - pos.getZ()) < 0.56F && (hit.getLocation().z - pos.getZ()) > 0.44F) ||
                   ((blockFace.equals(Direction.EAST) || blockFace.equals(Direction.WEST)) && (hit.getLocation().x - pos.getX()) < 0.56F && (hit.getLocation().x - pos.getX()) > 0.44F))
                {
                    byte column = (byte) this.getClickedColumn(hit.getLocation(), blockFace);
                    byte row = (byte) this.getClickedRow(hit.getLocation());

                    if(level.getBlockEntity(pos) instanceof ConnectFourBlockEntity blockEntity)
                    {
                        if(player.getItemInHand(hand).getItem() instanceof DyeItem dyeItem)
                        {
                            if(blockEntity.getColumns()[column].charAt(row) == 'i' || blockEntity.getColumns()[column].charAt(row) == 'g')
                            {
                                float[] rgb = dyeItem.getDyeColor().getTextureDiffuseColors();
                                int red = Mth.clamp(Math.round(255 * rgb[0]), 0, 255);
                                int green = Mth.clamp(Math.round(255 * rgb[1]), 0, 255);
                                int blue = Mth.clamp(Math.round(255 * rgb[2]), 0, 255);
                                Color color = new Color(red, green, blue);

                                if(blockEntity.getColumns()[column].charAt(row) == 'i') {
                                    blockEntity.ironColor = color.getRGB();
                                } else {
                                    blockEntity.goldColor = color.getRGB();
                                }

                                // Sync the Block
                                level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
                                blockEntity.setChanged();

                                if(!player.isCreative())
                                    player.getItemInHand(hand).shrink(1);
                                return InteractionResult.SUCCESS;
                            }
                            return InteractionResult.CONSUME;
                        }

                        // If the column is already full, or the game is won, we stop the interaction and nothing happens
                        if (blockEntity.hasFourInARow() || blockEntity.getTopPieceInColumn(column) == 5)
                            return InteractionResult.CONSUME;
                    }
                    // If the column wasn't full we do the interaction server side
                    if(level.isClientSide())
                        NetworkUtil.doConnectFourInteraction(pos, column);

                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return switch (state.getValue(FACING))
        {
            default -> Y_AXIS_AABB;
            case NORTH, SOUTH -> X_AXIS_AABB;
        };
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new ConnectFourBlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state)
    {
        return RenderShape.MODEL;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType)
    {
        return (level1, pos, state1, blockEntity) -> ConnectFourBlockEntity.tick(level1, pos, state1, (ConnectFourBlockEntity) blockEntity);
    }

    private int getClickedColumn(Vec3 vec3d, Direction facing)
    {
        double value = (facing.equals(Direction.NORTH) || facing.equals(Direction.SOUTH)) ? vec3d.x : vec3d.z;
        value -= Math.floor(value);
        value *= 100;

        return (int) (Math.floor(value / 6.25D) - 1) / 2;
    }

    private int getClickedRow(Vec3 vec3d)
    {
        double value = vec3d.y;
        value -= Math.floor(value);
        value *= 100;

        return (int) Mth.clamp((Math.floor(value / 3.125D) - 3) / 4, 0, 5);
    }
}