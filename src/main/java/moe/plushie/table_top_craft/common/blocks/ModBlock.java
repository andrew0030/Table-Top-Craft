package moe.plushie.table_top_craft.common.blocks;

import moe.plushie.table_top_craft.TableTopCraft;
import moe.plushie.table_top_craft.common.init.BlockInit;
import moe.plushie.table_top_craft.common.init.ItemInit;
import moe.plushie.table_top_craft.common.lib.ModReference;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;

public abstract class ModBlock extends Block {

    public ModBlock(String name, Material material, SoundType soundType) {
        super(material);
        this.setUnlocalizedName(name);
        this.setRegistryName(new ResourceLocation(ModReference.MOD_ID, name));
        this.setCreativeTab(TableTopCraft.getInstance().getModtab());
        this.setSoundType(soundType);
        this.setHardness(2F);
        BlockInit.BLOCKS.add(this);
        ItemInit.ITEMS.add(getItemBlock());
    }

    protected Item getItemBlock() {
        return new ItemBlock(this).setRegistryName(this.getRegistryName());
    }
    
    protected static boolean getBitBool(int value, int index) {
        return getBit(value, index) == 1;
    }
    
    protected static int getBit(int value, int index) {
        return (value >> index) & 1;
    }

    protected static int setBit(int value, int index, boolean on) {
        if (on) {
            return value | (1 << index);
        } else {
            return value & ~(1 << index);
        }
    }
}
