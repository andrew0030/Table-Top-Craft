package moe.plushie.table_top_craft.util.registrymanager;

import moe.plushie.table_top_craft.Main;
import moe.plushie.table_top_craft.init.BlockInit;
import moe.plushie.table_top_craft.init.ItemInit;
import moe.plushie.table_top_craft.util.interfaces.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class RegistryManager {	
	
	@SubscribeEvent
	public static void registerItem(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
	}

	@SubscribeEvent
	public static void registerBlock(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new Block[0]));
	}
	
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {		
		for(Item item : ItemInit.ITEMS) {
			if(item instanceof IHasModel) {
				((IHasModel)item).registerModels();
			}
		}
		
		for(Block block : BlockInit.BLOCKS) {
			if(block instanceof IHasModel) {
				((IHasModel)block).registerModels();
			}
		}
	}
	
//Pre Init
	public static void preInitRegistries() {
		Main.proxy.preinit();
	}
	
//Init
	public static void initRegistries() {
		Main.proxy.init();
	}
	
//Post Init
	public static void postInitRegistries() {
		Main.proxy.postinit();
	}
}
