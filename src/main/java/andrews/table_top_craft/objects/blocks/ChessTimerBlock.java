package andrews.table_top_craft.objects.blocks;

import andrews.table_top_craft.screens.chess_timer.menus.ChessTimerScreen;
import andrews.table_top_craft.block_entities.ChessTimerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChessTimerBlock extends HorizontalDirectionalBlock implements EntityBlock
{
    public static final EnumProperty<PressedButton> PRESSED_BUTTON = EnumProperty.create("pressed_button", PressedButton.class);
    private static final VoxelShape X_AXIS_AABB = Block.box(5.0D, 0.0D, 1.0D, 11.0D, 6.0D, 15.0D);
    private static final VoxelShape Y_AXIS_AABB = Block.box(1.0D, 0.0D, 5.0D, 15.0D, 6.0D, 11.0D);

    public ChessTimerBlock(Material material, SoundType soundType)
    {
        super(getProperties(material, soundType));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(PRESSED_BUTTON, PressedButton.NONE));
    }

    private static Properties getProperties(Material material, SoundType soundType)
    {
        Properties properties = Block.Properties.of(material);
        properties.sound(soundType);
        properties.strength(1.8F);
        properties.noOcclusion();
        return properties;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        boolean isTimerZero = false;
        if(level.getBlockEntity(pos) instanceof ChessTimerBlockEntity blockEntity)
            isTimerZero = blockEntity.getLeftTimer() == 0 || blockEntity.getRightTimer() == 0;

        if(!isTimerZero && !player.isShiftKeyDown())
        {
            if (state.getValue(PRESSED_BUTTON).equals(PressedButton.NONE))
            {
                PressedButton pressedButton = PressedButton.LEFT;
                switch (state.getValue(FACING)) {
                    default -> {}
                    case NORTH -> pressedButton = pos.getX() + 0.5 > player.getX() ? PressedButton.LEFT : PressedButton.RIGHT;
                    case SOUTH -> pressedButton = pos.getX() + 0.5 > player.getX() ? PressedButton.RIGHT : PressedButton.LEFT;
                    case EAST -> pressedButton = pos.getZ() + 0.5 > player.getZ() ? PressedButton.LEFT : PressedButton.RIGHT;
                    case WEST -> pressedButton = pos.getZ() + 0.5 > player.getZ() ? PressedButton.RIGHT : PressedButton.LEFT;
                }
                level.setBlock(pos, state.setValue(PRESSED_BUTTON, pressedButton), 2);
            }

            if (state.getValue(PRESSED_BUTTON).equals(PressedButton.LEFT))
            {
                if(!level.isClientSide() && level.getBlockEntity(pos) instanceof ChessTimerBlockEntity blockEntity)
                    if(blockEntity.leftTimerCache < System.currentTimeMillis() && blockEntity.lastSwitchTime != 0)
                        blockEntity.leftTimerCache += System.currentTimeMillis() - blockEntity.lastSwitchTime;
                level.setBlock(pos, state.setValue(PRESSED_BUTTON, PressedButton.RIGHT), 2);
            }

            if (state.getValue(PRESSED_BUTTON).equals(PressedButton.RIGHT))
            {
                if(!level.isClientSide() && level.getBlockEntity(pos) instanceof ChessTimerBlockEntity blockEntity)
                    if(blockEntity.rightTimerCache < System.currentTimeMillis() && blockEntity.lastSwitchTime != 0)
                        blockEntity.rightTimerCache += System.currentTimeMillis() - blockEntity.lastSwitchTime;
                level.setBlock(pos, state.setValue(PRESSED_BUTTON, PressedButton.LEFT), 2);
            }
            // On Button Click we update the lastSwitchTime
            if(!level.isClientSide() && level.getBlockEntity(pos) instanceof ChessTimerBlockEntity blockEntity)
                blockEntity.lastSwitchTime = System.currentTimeMillis();
            // Sound
            level.playLocalSound(pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 1.0F, 0.6F, false);
        } else {
            if(level.getBlockEntity(pos) instanceof ChessTimerBlockEntity blockEntity)
                if(level.isClientSide)
                    ChessTimerScreen.open(blockEntity);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return switch (state.getValue(FACING))
        {
            default -> X_AXIS_AABB;
            case NORTH, SOUTH -> Y_AXIS_AABB;
        };
    }

    @Override
    public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction)
    {
        ChessTimerBlockEntity blockEntity = (ChessTimerBlockEntity) level.getBlockEntity(pos);
        if (blockEntity != null)
        {
            // Called when both timers are 00:00
            if (blockEntity.getLeftTimer() == 0 && blockEntity.getRightTimer() == 0)
            {
                return switch (state.getValue(FACING))
                {
                    default -> 0;
                    case NORTH, SOUTH -> direction.equals(Direction.WEST) || direction.equals(Direction.EAST) ? 15 : 0;
                    case EAST, WEST -> direction.equals(Direction.NORTH) || direction.equals(Direction.SOUTH) ? 15 : 0;
                };
            }
            // Called when the left timer is 00:00
            if (blockEntity.getLeftTimer() == 0)
            {
                return switch (state.getValue(FACING))
                {
                    default -> 0;
                    case NORTH -> direction.equals(Direction.WEST) ? 15 : 0;
                    case SOUTH -> direction.equals(Direction.EAST) ? 15 : 0;
                    case EAST -> direction.equals(Direction.NORTH) ? 15 : 0;
                    case WEST -> direction.equals(Direction.SOUTH) ? 15 : 0;
                };
            }
            // Called when the right timer is 00:00
            if (blockEntity.getRightTimer() == 0)
            {
                return switch (state.getValue(FACING))
                {
                    default -> 0;
                    case NORTH -> direction.equals(Direction.EAST) ? 15 : 0;
                    case SOUTH -> direction.equals(Direction.WEST) ? 15 : 0;
                    case EAST -> direction.equals(Direction.SOUTH) ? 15 : 0;
                    case WEST -> direction.equals(Direction.NORTH) ? 15 : 0;
                };
            }
        }
        return 0;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING).add(PRESSED_BUTTON);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new ChessTimerBlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state)
    {
        return RenderShape.MODEL;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntity)
    {
        return (level, pos, state, blockEntity) -> ChessTimerBlockEntity.tick(level, pos, state, (ChessTimerBlockEntity) blockEntity);
    }

    public enum PressedButton implements StringRepresentable
    {
        NONE("none"),
        LEFT("left"),
        RIGHT("right");

        private final String name;

        PressedButton(String name)
        {
            this.name = name;
        }

        @Override
        public String getSerializedName()
        {
            return this.name;
        }
    }
}