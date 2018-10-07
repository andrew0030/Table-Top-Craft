package moe.plushie.table_top_craft.common.blocks;

import moe.plushie.table_top_craft.TableTopCraft;
import moe.plushie.table_top_craft.common.tileentities.TileEntityChessTable;
import moe.plushie.table_top_craft.util.interfaces.IHasModel;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockChessTable extends ModBlockContainer implements IHasModel {
	
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0F, 0F, 0F, 1.0F, 0.0625F * 12F, 1.0F);

    public static final PropertyDirection STATE_FACING = BlockHorizontal.FACING;
    
    public BlockChessTable(String name) {
        super(name, Material.WOOD, SoundType.WOOD);
        setDefaultState(this.blockState.getBaseState().withProperty(STATE_FACING, EnumFacing.NORTH));
    }
    
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {STATE_FACING});
    }
    
    public IBlockState getStateFromMeta(int meta) {
        boolean northSouthBit = getBitBool(meta, 0);
        boolean posNegBit = getBitBool(meta, 1);
        EnumFacing facing = EnumFacing.EAST;
        if (northSouthBit) {
            if (posNegBit) { facing = EnumFacing.SOUTH; } else { facing = EnumFacing.NORTH; }
        } else {
            if (posNegBit) { facing = EnumFacing.EAST; } else { facing = EnumFacing.WEST; }
        }
        return this.getDefaultState().withProperty(STATE_FACING, facing);
    }

    public int getMetaFromState(IBlockState state) {
        EnumFacing facing = state.getValue(STATE_FACING);
        int meta = 0;
        if (facing == EnumFacing.NORTH | facing == EnumFacing.SOUTH) {
            meta = setBit(meta, 0, true);
        }
        if (facing == EnumFacing.EAST | facing == EnumFacing.SOUTH) {
            meta = setBit(meta, 1, true);
        }
        return meta;
    }
    
    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        EnumFacing enumfacing = placer.getHorizontalFacing().getOpposite();
        return getDefaultState().withProperty(STATE_FACING, enumfacing);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public void registerModels() {
        TableTopCraft.getProxy().registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityChessTable();
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    	return BOUNDING_BOX;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	return BOUNDING_BOX;
    }
}
