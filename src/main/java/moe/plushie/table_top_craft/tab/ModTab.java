package moe.plushie.table_top_craft.tab;

import moe.plushie.table_top_craft.init.BlockInit;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModTab extends CreativeTabs {
	public ModTab(String label) {
		super("table_top_craft");
		this.setBackgroundImageName("items.png");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(Item.getItemFromBlock(BlockInit.CHESS));
	}
}
