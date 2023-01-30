package andrews.table_top_craft.objects.blocks;

import andrews.table_top_craft.animation.system.base.AnimatedBlockEntity;
import andrews.table_top_craft.animation.system.core.AdvancedAnimationState;
import andrews.table_top_craft.animation.system.core.types.EasingTypes;
import andrews.table_top_craft.screens.chess.menus.ChessBoardSettingsScreen;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.NetworkUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChessBlock extends HorizontalDirectionalBlock implements EntityBlock
{
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty SHOW_PLATE = BooleanProperty.create("show_plate");
	protected static final VoxelShape CHESS_BLOCK_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);
	
	public ChessBlock()
	{
		super(getProperties());
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(SHOW_PLATE, true));
	}

	/**
	 * @return - The properties for this Block
	 */
	private static Properties getProperties()
	{	
		Properties properties = Block.Properties.of(Material.WOOD);
		properties.strength(2.0F);
		// harvestTool() Has been removed and is now handled through Tags.
		// properties.harvestTool(ToolType.AXE);
		properties.noOcclusion();
		properties.sound(SoundType.WOOD);
		
		return properties;
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context)
	{
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection()).setValue(SHOW_PLATE, true);
	}

	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving)
	{
		if(level.getBlockEntity(pos) instanceof ChessTileEntity chessTileEntity)
			if(chessTileEntity.getUseCustomPlate())
				level.setBlockAndUpdate(pos, state.setValue(ChessBlock.SHOW_PLATE, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
	{
		builder.add(FACING, SHOW_PLATE);
	}

	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
	{
		return CHESS_BLOCK_AABB;
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
	{
		if(level.getBlockEntity(pos) instanceof ChessTileEntity chessTileEntity)
		{
			if (chessTileEntity.placedState.isStarted())
			{
				chessTileEntity.placedState.interpolateAndStop(0.0F, EasingTypes.EASE_IN_OUT_QUAD, false);
				chessTileEntity.lingeringStates.add(new AdvancedAnimationState(chessTileEntity.placedState));
				chessTileEntity.placedState.stop();
			}
			else
			{
				chessTileEntity.placedState.interpolateAndStart(0.0F, EasingTypes.EASE_IN_OUT_QUAD, false, chessTileEntity.getTicksExisted());
			}
		}

		if(player.isShiftKeyDown())
		{
			if(level.getBlockEntity(pos) instanceof ChessTileEntity chessTileEntity)
			{
				if(level.isClientSide)
					ChessBoardSettingsScreen.open(chessTileEntity);
			}
		}
		else
		{
			Direction face = hit.getDirection();
			if (face.equals(Direction.UP))
			{
				Direction facing = state.getValue(FACING);
				int chessRank = this.getChessRank(hit.getLocation(), facing) + 1;
				int chessColumn = this.getChessColumn(hit.getLocation(), facing);
				byte tileCoordinate = (byte)Mth.clamp((8 - chessRank) * 8 + chessColumn, 0, 63);
				if (level.isClientSide)
					NetworkUtil.doChessBoardInteraction(pos, tileCoordinate);
			}
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
	{
		return new ChessTileEntity(pos, state);
	}

	// We have to do this as we use both a JSON and Java model on the same block,
	// otherwise the block would only render one (The Java Model) so we specify both here.
	@Override
	public RenderShape getRenderShape(BlockState state)
	{
		return RenderShape.MODEL;
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType)
	{
		return (level1, pos, state1, blockEntity) -> ChessTileEntity.tick(level1, pos, state1, (AnimatedBlockEntity) blockEntity);
	}
	
	/**
	 * @param vec3d The Vector of the BlockPosition
	 * @param facing The Direction this Block is facing
	 * @return The Rank the player pressed
	 */
	private int getChessRank(Vec3 vec3d, Direction facing)
	{
		double value = facing == Direction.NORTH || facing == Direction.SOUTH ? vec3d.z : vec3d.x;
		
		
		// We floor the value and subtract it from the original, that way we get rid of the block position value
		value -= Math.floor(value);
		// We multiply the value with 100 to make it easier to use
		value *= 100;

		return switch(facing)
		{
			// We have to add DOWN and UP because this is an enhanced switch
			case NORTH, WEST, DOWN, UP -> (int) (7 - Math.floor(value / 12.5D));
			case SOUTH, EAST -> (int) Math.floor(value / 12.5D);
		};
	}
	
	/**
	 * @param vec3d The Vector of the BlockPosition
	 * @param facing The Direction this Block is facing
	 * @return The Column the player pressed
	 */
	private int getChessColumn(Vec3 vec3d, Direction facing)
	{
		// We get either the X or Z value depending on the Direction the Block is facing
		double value = facing == Direction.NORTH || facing == Direction.SOUTH ? vec3d.x : vec3d.z;
		// We floor the value and subtract it from the original, that way we get rid of the block position value
		value -= Math.floor(value);
		// We multiply the value with 100 to make it easier to use
		value *= 100;

		return switch (facing)
		{
			// We have to add DOWN and UP because this is an enhanced switch
			case NORTH, EAST, DOWN, UP -> (int) Math.floor(value / 12.5D);
			case SOUTH, WEST -> (int) (7 - Math.floor(value / 12.5D));
			//default -> (int) Math.floor(value / 12.5D);
			// We could add the default branch and pass the value, but I chose not to as it looks cleaner.
		};
	}
}