package andrews.table_top_craft.objects.blocks;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.screens.chess.menus.ChessBoardSettingsScreen;
import andrews.table_top_craft.screens.chess.menus.ChessPawnPromotionScreen;
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
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChessBlock extends HorizontalDirectionalBlock implements EntityBlock
{
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty SHOW_PLATE = BooleanProperty.create("show_plate");
	// Used by both variants
	private static final VoxelShape TOP_PLATE = Block.box(0.0D, 11.0D, 0.0D, 16.0D, 12.0D, 16.0D);
	private static final VoxelShape LEG1 = Block.box(0.0D, 0.0D, 0.0D, 2.0D, 4.0D, 2.0D);
	private static final VoxelShape LEG2 = Block.box(14.0D, 0.0D, 0.0D, 16.0D, 4.0D, 2.0D);
	private static final VoxelShape LEG3 = Block.box(0.0D, 0.0D, 14.0D, 2.0D, 4.0D, 16.0D);
	private static final VoxelShape LEG4 = Block.box(14.0D, 0.0D, 14.0D, 16.0D, 4.0D, 16.0D);
	// X Axis
	private static final VoxelShape X_SIDE1 = Block.box(0.0D, 4.0D, 0.0D, 6.0D, 11.0D, 1.0D);
	private static final VoxelShape X_SIDE2 = Block.box(10.0D, 4.0D, 0.0D, 16.0D, 11.0D, 1.0D);
	private static final VoxelShape X_BAR1 = Block.box(6.0D, 4.0D, 0.0D, 10.0D, 5.0D, 1.0D);
	private static final VoxelShape X_SIDE3 = Block.box(0.0D, 4.0D, 15.0D, 6.0D, 11.0D, 16.0D);
	private static final VoxelShape X_SIDE4 = Block.box(10.0D, 4.0D, 15.0D, 16.0D, 11.0D, 16.0D);
	private static final VoxelShape X_BAR2 = Block.box(6.0D, 4.0D, 15.0D, 10.0D, 5.0D, 16.0D);
	private static final VoxelShape X_STORAGE1 = Block.box(0.0D, 4.0D, 1.0D, 6.0D, 5.0D, 15.0D);
	private static final VoxelShape X_STORAGE2 = Block.box(10.0D, 4.0D, 1.0D, 16.0D, 5.0D, 15.0D);
	private static final VoxelShape X_INSIDE_WALL1 = Block.box(5.0D, 5.0D, 1.0D, 6.0D, 11.0D, 15.0D);
	private static final VoxelShape X_INSIDE_WALL2 = Block.box(10.0D, 5.0D, 1.0D, 11.0D, 11.0D, 15.0D);
	private static final VoxelShape X_LIP1 = Block.box(0.0D, 5.0D, 1.0D, 1.0D, 6.0D, 15.0D);
	private static final VoxelShape X_LIP2 = Block.box(15.0D, 5.0D, 1.0D, 16.0D, 6.0D, 15.0D);
	// Y Axis
	private static final VoxelShape Y_SIDE1 = Block.box(0.0D, 4.0D, 0.0D, 1.0D, 11.0D, 6.0D);
	private static final VoxelShape Y_SIDE2 = Block.box(0.0D, 4.0D, 10.0D, 1.0D, 11.0D, 16.0D);
	private static final VoxelShape Y_BAR1 = Block.box(0.0D, 4.0D, 6.0D, 1.0D, 5.0D, 10.0D);
	private static final VoxelShape Y_SIDE3 = Block.box(15.0D, 4.0D, 0.0D, 16.0D, 11.0D, 6.0D);
	private static final VoxelShape Y_SIDE4 = Block.box(15.0D, 4.0D, 10.0D, 16.0D, 11.0D, 16.0D);
	private static final VoxelShape Y_BAR2 = Block.box(15.0D, 4.0D, 6.0D, 16.0D, 5.0D, 10.0D);
	private static final VoxelShape Y_STORAGE1 = Block.box(1.0D, 4.0D, 0.0D, 15.0D, 5.0D, 6.0D);
	private static final VoxelShape Y_STORAGE2 = Block.box(1.0D, 4.0D, 10.0D, 15.0D, 5.0D, 16.0D);
	private static final VoxelShape Y_INSIDE_WALL1 = Block.box(1.0D, 5.0D, 5.0D, 15.0D, 11.0D, 6.0D);
	private static final VoxelShape Y_INSIDE_WALL2 = Block.box(1.0D, 5.0D, 10.0D, 15.0D, 11.0D, 11.0D);
	private static final VoxelShape Y_LIP1 = Block.box(1.0D, 5.0D, 0.0D, 15.0D, 6.0D, 1.0D);
	private static final VoxelShape Y_LIP2 = Block.box(1.0D, 5.0D, 15.0D, 15.0D, 6.0D, 16.0D);
	// AABB's
	private static final VoxelShape X_AXIS_AABB = Shapes.or(TOP_PLATE, LEG1, LEG2, LEG3, LEG4, X_SIDE1, X_SIDE2, X_BAR1, X_SIDE3, X_SIDE4, X_BAR2, X_STORAGE1, X_STORAGE2, X_INSIDE_WALL1, X_INSIDE_WALL2, X_LIP1, X_LIP2);
	private static final VoxelShape Y_AXIS_AABB = Shapes.or(TOP_PLATE, LEG1, LEG2, LEG3, LEG4, Y_SIDE1, Y_SIDE2, Y_BAR1, Y_SIDE3, Y_SIDE4, Y_BAR2, Y_INSIDE_WALL1, Y_INSIDE_WALL2, Y_STORAGE1, Y_STORAGE2, Y_LIP1, Y_LIP2);

	public ChessBlock(Material material, SoundType soundType)
	{
		super(getProperties(material, soundType));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(SHOW_PLATE, true));
	}

	/**
	 * @return - The properties for this Block
	 */
	private static Properties getProperties(Material material, SoundType soundType)
	{	
		Properties properties = Block.Properties.of(material);
		properties.sound(soundType);
		properties.strength(2.0F);
		properties.noOcclusion();
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
		if(level.getBlockEntity(pos) instanceof ChessBlockEntity chessBlockEntity)
			if(chessBlockEntity.getUseCustomPlate())
				level.setBlockAndUpdate(pos, state.setValue(ChessBlock.SHOW_PLATE, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
	{
		builder.add(FACING, SHOW_PLATE);
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
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
	{
		if(player.isShiftKeyDown())
		{
			if(level.getBlockEntity(pos) instanceof ChessBlockEntity chessBlockEntity)
			{
				if(level.isClientSide)
					ChessBoardSettingsScreen.open(chessBlockEntity);
			}
		}
		else
		{
			if(level.getBlockEntity(pos) instanceof ChessBlockEntity chessBlockEntity && chessBlockEntity.getWaitingForPromotion())
			{
				// If we find a block entity (should be the case unless something is going horribly wrong)
				// we check if we are waiting for a pawn promotion, and if so we open the selection menu.
				if(level.isClientSide())
					ChessPawnPromotionScreen.open(chessBlockEntity, chessBlockEntity.getBoard().getCurrentChessPlayer().getOpponent().getPieceColor().isWhite());
			}
			else
			{
				// If the game isn't waiting for a pawn promotion, we just do as we normally would
				Direction face = hit.getDirection();
				if (face.equals(Direction.UP) && (hit.getLocation().y - pos.getY()) > 0.7)
				{
					Direction facing = state.getValue(FACING);
					int chessRank = this.getChessRank(hit.getLocation(), facing) + 1;
					int chessColumn = this.getChessColumn(hit.getLocation(), facing);
					byte tileCoordinate = (byte)Mth.clamp((8 - chessRank) * 8 + chessColumn, 0, 63);
					if (level.isClientSide)
						NetworkUtil.doChessBoardInteraction(pos, tileCoordinate);
				}
			}
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
	{
		return new ChessBlockEntity(pos, state);
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
		return (level1, pos, state1, blockEntity) -> ChessBlockEntity.tick(level1, pos, state1, (ChessBlockEntity) blockEntity);
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