package moe.plushie.table_top_craft.common.blocks;

import moe.plushie.table_top_craft.TableTopCraft;
import moe.plushie.table_top_craft.common.init.BlockInit;
import moe.plushie.table_top_craft.common.init.ItemInit;
import moe.plushie.table_top_craft.common.lib.Reference;
import moe.plushie.table_top_craft.common.tileentities.TileEntityChess;
import moe.plushie.table_top_craft.util.interfaces.IHasModel;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockChess extends BlockContainer implements IHasModel {

    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625F * -1F, 0, 0.0625F * -1F, 0.0625F * 17F, 0.0625F * 16.5F, 0.0625F * 17F);

    public BlockChess(String name) {
        super(Material.WOOD);
        this.setUnlocalizedName(name);
        this.setRegistryName(new ResourceLocation(Reference.MOD_ID, name));
        this.setCreativeTab(TableTopCraft.getInstance().getModtab());
        this.setSoundType(SoundType.WOOD);
        this.setHardness(2F);

        BlockInit.BLOCKS.add(this);
        ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }
    
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
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
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDING_BOX;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return BOUNDING_BOX;
    }

    @Override
    public void registerModels() {
        TableTopCraft.getProxy().registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityChess();
    }
}
