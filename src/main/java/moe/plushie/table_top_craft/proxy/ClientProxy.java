package moe.plushie.table_top_craft.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}
	
	@Override
	public void preinit() {
		super.preinit();
	}
	
	@Override
	public void init() {
		super.init();
	}
	
	@Override
	public void postinit() {
		super.postinit();
	}
	
}
