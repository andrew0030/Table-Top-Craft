package andrews.table_top_craft.objects.blocks;

import andrews.table_top_craft.game_logic.chess.board.moves.BaseMove;
import andrews.table_top_craft.game_logic.chess.board.moves.MoveFactory;
import andrews.table_top_craft.game_logic.chess.board.tiles.BaseChessTile;
import andrews.table_top_craft.game_logic.chess.player.MoveTransition;
import andrews.table_top_craft.screens.chess.menus.ChessBoardSettingsScreen;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ToolType;

public class ChessBlock extends HorizontalBlock
{
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	public static final BooleanProperty SHOW_PLATE = BooleanProperty.create("show_plate");
	protected static final VoxelShape CHESS_BLOCK_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);
	
	public ChessBlock()
	{
		super(getProperties());
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(SHOW_PLATE, true));
	}

	/**
	 * @return - The properties for this Block
	 */
	private static Properties getProperties()
	{	
		Properties properties = Block.Properties.create(Material.WOOD);
		properties.hardnessAndResistance(2.0F);
		properties.setLightLevel(value -> 14);
		properties.harvestTool(ToolType.AXE);
		properties.notSolid();
		properties.sound(SoundType.WOOD);
		
		return properties;
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing()).with(SHOW_PLATE, true);
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder)
	{
		 builder.add(FACING, SHOW_PLATE);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return CHESS_BLOCK_AABB;
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		if(player.isSneaking())
		{
			if(worldIn.getTileEntity(pos) instanceof ChessTileEntity)
			{
				ChessTileEntity chessTileEntity = (ChessTileEntity) worldIn.getTileEntity(pos);
				if(worldIn.isRemote)
					Minecraft.getInstance().displayGuiScreen(new ChessBoardSettingsScreen(chessTileEntity));
			}
		}
		else
		{	
			RayTraceResult raycast = rayTraceFromPlayer(worldIn, player, FluidMode.NONE);
			if(raycast.getType() == RayTraceResult.Type.BLOCK)
			{
				if(worldIn.getBlockState(new BlockPos(raycast.getHitVec())).getBlock() instanceof ChessBlock)
				{
					Direction face = ((BlockRayTraceResult) raycast).getFace();
					
					if(face.equals(Direction.UP))
					{
						Direction facing = state.get(FACING);
						int chessRank = getChessRank(raycast.getHitVec(), facing) + 1;
						int chessColumn = getChessColumn(raycast.getHitVec(), facing);
						TileEntity tileentity = worldIn.getTileEntity(pos);
						
						// We make sure the TileEntity is a ChessTileEntity
						if(tileentity instanceof ChessTileEntity)
				        {
							ChessTileEntity chessTileEntity = (ChessTileEntity)tileentity;
							// We do not continue the game logic if there is no Chess
							if(chessTileEntity.getBoard() == null)
								return ActionResultType.SUCCESS;
							BaseChessTile chessTile = chessTileEntity.getBoard().getTile((8 - chessRank) * 8 + chessColumn);
							
							// Checks if a Tile has allready been selected
							if(chessTileEntity.getSourceTile() == null)
							{
								if(chessTile.isTileOccupied())
								{
									if(chessTile.getPiece().getPieceColor() == chessTileEntity.getBoard().getCurrentChessPlayer().getPieceColor())//TODO make sure is fine
									{
										chessTileEntity.setSourceTile(chessTile);
										chessTileEntity.setHumanMovedPiece(chessTile.getPiece());
										worldIn.notifyBlockUpdate(pos, worldIn.getBlockState(pos), worldIn.getBlockState(pos), 2);
										if(chessTileEntity.getHumanMovedPiece() == null)
											chessTileEntity.setSourceTile(null);
										
										chessTileEntity.markDirty();//TODO maybe remove?
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
									// Syncs the TileEntity TODO mayebe move
									worldIn.notifyBlockUpdate(pos, worldIn.getBlockState(pos), worldIn.getBlockState(pos), 2);//TODO fix EnPassant
									// Prints out the Move TODO remove and or repalce somehow
									if(chessTileEntity.getMoveLog().size() > 0)
									{
										final BaseMove lastMove = chessTileEntity.getMoveLog().getMoves().get(chessTileEntity.getMoveLog().size() - 1);
										final String moveText = lastMove.toString();
										
										if(worldIn.isRemote)
											player.sendMessage(new StringTextComponent(moveText), player.getUniqueID());
									}
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
		return ActionResultType.SUCCESS;
	}
	
	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new ChessTileEntity();
	}
	
	/**
	 * Casts a ray from the player towards the Block
	 * @param worldIn - The world the player is in
	 * @param player - The player
	 * @param fluidMode - The FluidMode
	 * @return - A RayTraceResult
	 */
	private RayTraceResult rayTraceFromPlayer(World worldIn, PlayerEntity player, RayTraceContext.FluidMode fluidMode)
	{
		float f = player.rotationPitch;
		float f1 = player.rotationYaw;
		Vector3d vec3d = player.getEyePosition(1.0F);
		float f2 = MathHelper.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
		float f3 = MathHelper.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
		float f4 = -MathHelper.cos(-f * ((float)Math.PI / 180F));
		float f5 = MathHelper.sin(-f * ((float)Math.PI / 180F));
		float f6 = f3 * f4;
		float f7 = f2 * f4;
		double d0 = player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue();;
		d0 = (d0 * 2);
		Vector3d vec3d1 = vec3d.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
		return worldIn.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, fluidMode, player));
	}
	
	/**
	 * @param vec3d - The Vector of the BlockPosition
	 * @param facing - The Direction this Block is facing
	 * @return - The Rank the player pressed
	 */
	private int getChessRank(Vector3d vec3d, Direction facing)
	{
		double value = facing == Direction.NORTH || facing == Direction.SOUTH ? vec3d.getZ() : vec3d.getX();
		
		
		// We floor the value and subtract it from the original, that way we get rid of the block position value
		value -= Math.floor(value);
		// We multiply the value with 100 to make it easier to use
		value *= 100;
		
		switch(facing)
		{
		default:
		case NORTH:
			return (int) (7 - Math.floor(value / 12.5D));
		case SOUTH:
			return (int) Math.floor(value / 12.5D);
		case WEST:
			return (int) (7 - Math.floor(value / 12.5D));
		case EAST:
			return (int) Math.floor(value / 12.5D);
		}
	}
	
	/**
	 * @param vec3d - The Vector of the BlockPosition
	 * @param facing - The Direction this Block is facing
	 * @return - The Column the player pressed
	 */
	private int getChessColumn(Vector3d vec3d, Direction facing)
	{
		// We get either the X or Z value depending on the Direction the Block is facing
		double value = facing == Direction.NORTH || facing == Direction.SOUTH ? vec3d.getX() : vec3d.getZ();
		// We floor the value and subtract it from the original, that way we get rid of the block position value
		value -= Math.floor(value);
		// We multiply the value with 100 to make it easier to use
		value *= 100;
		
		switch(facing)
		{
		default:
		case NORTH:
			return (int) Math.floor(value / 12.5D);
		case SOUTH:
			return (int) (7 - Math.floor(value / 12.5D));
		case WEST:
			return (int) (7 - Math.floor(value / 12.5D));
		case EAST:
			return (int) Math.floor(value / 12.5D);
		}
	}
}