package andrews.table_top_craft;

import andrews.table_top_craft.network.TTCNetwork;
import andrews.table_top_craft.registry.TTCBlocks;
import andrews.table_top_craft.registry.TTCItems;
import andrews.table_top_craft.registry.TTCTileEntities;
import andrews.table_top_craft.util.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = Reference.MODID)
public class TableTopCraft
{
	public static TableTopCraft instance;
	public static final ItemGroup TABLE_TOP_CRAFT_GROUP = new ItemGroup(Reference.MODID)
	{
		@Override
		public ItemStack createIcon()
		{
			return new ItemStack(Item.BLOCK_TO_ITEM.getOrDefault(TTCBlocks.CHESS.get(), Items.AIR));
		}
	};
	
	@SuppressWarnings("deprecation")
	public TableTopCraft()
	{
		instance = this;
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		
		TTCItems.ITEMS.register(modEventBus);
		TTCBlocks.BLOCKS.register(modEventBus);
//		PCSounds.SOUNDS.register(modEventBus);
		TTCTileEntities.TILE_ENTITY_TYPES.register(modEventBus);
		
		DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
		{
			modEventBus.addListener(EventPriority.LOWEST, this::setupClient);
		});
		modEventBus.addListener(EventPriority.LOWEST, this::setupCommon);
		
//		PCConfigs.registerConfigs();
	}
	
	void setupCommon(final FMLCommonSetupEvent event)
	{	
		event.enqueueWork(() -> 
		{
			
		});
		//Thread Safe Stuff
		TTCNetwork.setupMessages();
	}
	
	@OnlyIn(Dist.CLIENT)
	void setupClient(final FMLClientSetupEvent event)
	{
		event.enqueueWork(() -> 
		{
			TTCTileEntities.registerTileRenders();
		});
		//Thread Safe Stuff
	}
}
