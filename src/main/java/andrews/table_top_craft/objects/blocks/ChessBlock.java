package andrews.table_top_craft.objects.blocks;

import andrews.table_top_craft.game_logic.chess.board.moves.BaseMove;
import andrews.table_top_craft.game_logic.chess.board.moves.MoveFactory;
import andrews.table_top_craft.game_logic.chess.board.tiles.BaseChessTile;
import andrews.table_top_craft.game_logic.chess.player.MoveTransition;
import andrews.table_top_craft.screens.chess.menus.ChessBoardSettingsScreen;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeMod;

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
			HitResult raycast = rayTraceFromPlayer(level, player, ClipContext.Fluid.NONE);
			if(raycast.getType() == HitResult.Type.BLOCK)
			{
				if(level.getBlockState(new BlockPos(raycast.getLocation())).getBlock() instanceof ChessBlock)
				{
					Direction face = ((BlockHitResult) raycast).getDirection();

					if(face.equals(Direction.UP))
					{
						Direction facing = state.getValue(FACING);
						int chessRank = getChessRank(raycast.getLocation(), facing) + 1;
						int chessColumn = getChessColumn(raycast.getLocation(), facing);
						BlockEntity blockEntity = level.getBlockEntity(pos);

						// We make sure the TileEntity is a ChessTileEntity
						if(blockEntity instanceof ChessTileEntity chessTileEntity)
						{
							// We do not continue the game logic if there is no Chess
							if(chessTileEntity.getBoard() == null)
								return InteractionResult.SUCCESS;
							BaseChessTile chessTile = chessTileEntity.getBoard().getTile((8 - chessRank) * 8 + chessColumn);

							// Checks if a Tile has allready been selected
							if(chessTileEntity.getSourceTile() == null)
							{
								if(chessTile.isTileOccupied())
								{
									if(chessTile.getPiece().getPieceColor() == chessTileEntity.getBoard().getCurrentChessPlayer().getPieceColor())
									{
										chessTileEntity.setSourceTile(chessTile);
										chessTileEntity.setHumanMovedPiece(chessTile.getPiece());
										level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
										if(chessTileEntity.getHumanMovedPiece() == null)
											chessTileEntity.setSourceTile(null);

										chessTileEntity.setChanged();//TODO make sure this is working it used to be markdirty
									}
								}
							}
							else // Gets Called when a Tile is all ready selected
							{
								chessTileEntity.setDestinationTile(chessTile);
								final BaseMove move = MoveFactory.createMove(chessTileEntity.getBoard(), chessTileEntity.getSourceTile().getTileCoordinate(), chessTileEntity.getDestinationTile().getTileCoordinate());
								final MoveTransition transition = chessTileEntity.getBoard().getCurrentChessPlayer().makeMove(move);
								if(transition.getMoveStatus().isDone())
								{
									chessTileEntity.setBoard(transition.getTransitionBoard());
									// Adds the move to the MoveLog
									chessTileEntity.getMoveLog().addMove(move);
									// Syncs the TileEntity
									level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
								}
								chessTileEntity.setSourceTile(null);
								chessTileEntity.setDestinationTile(null);
								chessTileEntity.setHumanMovedPiece(null);
							}
						}
					}
				}
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
	
	/**
	 * Casts a ray from the player towards the Block
	 * @param level The world the player is in
	 * @param player The player
	 * @param fluidMode The FluidMode
	 * @return A RayTraceResult
	 */
	private HitResult rayTraceFromPlayer(Level level, Player player, ClipContext.Fluid fluidMode)
	{
		float pitch = player.getXRot();
		float yaw = player.getYRot();
		Vec3 startVector = player.getEyePosition(1.0F);
		float f2 = Mth.cos(-yaw * ((float)Math.PI / 180F) - (float)Math.PI);
		float f3 = Mth.sin(-yaw * ((float)Math.PI / 180F) - (float)Math.PI);
		float f4 = -Mth.cos(-pitch * ((float)Math.PI / 180F));
		float f5 = Mth.sin(-pitch * ((float)Math.PI / 180F));
		float f6 = f3 * f4;
		float f7 = f2 * f4;
		double reach = player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue();;
		reach = (reach * 2);
		Vec3 targetVector = startVector.add((double)f6 * reach, (double)f5 * reach, (double)f7 * reach);
		return level.clip(new ClipContext(startVector, targetVector, ClipContext.Block.OUTLINE, fluidMode, player));
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