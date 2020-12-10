package andrews.table_top_craft.objects.blocks;

import andrews.table_top_craft.registry.TTCBlocks;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
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

public class ChessBlock extends Block
{
	protected static final VoxelShape CHESS_BLOCK_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);
	
	public ChessBlock()
	{
		super(getProperties());
	}

	/**
	 * @return - The properties for this Block
	 */
	private static Properties getProperties()
	{	
		Properties properties = Block.Properties.create(Material.WOOD);
		properties.hardnessAndResistance(2.0F);
		properties.harvestTool(ToolType.AXE);
		properties.notSolid();
		
		return properties;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return CHESS_BLOCK_AABB;
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		RayTraceResult raycast = rayTraceFromPlayer(worldIn, player, FluidMode.NONE);
		if(raycast.getType() == RayTraceResult.Type.BLOCK)
		{
			if(worldIn.getBlockState(new BlockPos(raycast.getHitVec())).getBlock() == TTCBlocks.CHESS.get())
			{
				Direction face = ((BlockRayTraceResult) raycast).getFace();
				
				if(face.equals(Direction.UP))
				{
//					System.out.println(getPixelValue(raycast.getHitVec().getX()) + " " + raycast.getHitVec().getZ());
					System.out.println(getPixelValue(raycast.getHitVec().getX()));
					if(worldIn.isRemote)
						player.sendMessage(new StringTextComponent(getLetterFromNumber(getPixelValue(raycast.getHitVec().getZ())) + Integer.toString(getPixelValue(raycast.getHitVec().getX()) + 1)), player.getUniqueID());
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
	
	private int getPixelValue(double value)
	{
		value -= Math.floor(value);//TODO test in negative world coordinates
		value *= 100;
		value += 1;
		return (int) Math.floor(value / 12.5D);
	}
	
	private String getLetterFromNumber(int number)
	{
		switch(number)
		{
		default:
		case 0:
			return "a";
		case 1:
			return "b";
		case 2:
			return "c";
		case 3:
			return "d";
		case 4:
			return "e";
		case 5:
			return "f";
		case 6:
			return "g";
		case 7:
			return "h";
		}
	}
}