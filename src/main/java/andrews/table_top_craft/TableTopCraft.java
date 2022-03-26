package andrews.table_top_craft;

import andrews.table_top_craft.network.TTCNetwork;
import andrews.table_top_craft.registry.TTCBlocks;
import andrews.table_top_craft.registry.TTCItems;
import andrews.table_top_craft.registry.TTCTileEntities;
import andrews.table_top_craft.tile_entities.model.chess.ChessBoardPlateModel;
import andrews.table_top_craft.tile_entities.model.chess.ChessHighlightModel;
import andrews.table_top_craft.tile_entities.model.chess.ChessTilesInfoModel;
import andrews.table_top_craft.util.Reference;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
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
	public static final CreativeModeTab TABLE_TOP_CRAFT_GROUP = new CreativeModeTab(Reference.MODID)
	{
		@Override
		public ItemStack makeIcon()
		{
			return new ItemStack(Item.BY_BLOCK.getOrDefault(TTCBlocks.OAK_CHESS.get(), Items.AIR));
		}
	};
	
	@SuppressWarnings("deprecation")
	public TableTopCraft()
	{
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		
		TTCItems.ITEMS.register(modEventBus);
		TTCBlocks.BLOCKS.register(modEventBus);
		TTCTileEntities.BLOCK_ENTITY_TYPES.register(modEventBus);
		
		DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
		{
			modEventBus.addListener(EventPriority.LOWEST, this::setupClient);
			modEventBus.addListener(this::setupLayers);
		});
		modEventBus.addListener(EventPriority.LOWEST, this::setupCommon);
	}
	
	void setupCommon(final FMLCommonSetupEvent event)
	{	
		event.enqueueWork(() -> {});
		//Thread Safe Stuff
		TTCNetwork.setupMessages();
	}

	void setupClient(final FMLClientSetupEvent event)
	{
		event.enqueueWork(() -> 
		{
			TTCTileEntities.registerTileRenders();
		});
		//Thread Safe Stuff
	}

	void setupLayers(final EntityRenderersEvent.RegisterLayerDefinitions event)
	{
		event.registerLayerDefinition(ChessBoardPlateModel.CHESS_BOARD_PLATE_LAYER, ChessBoardPlateModel::createBodyLayer);
		event.registerLayerDefinition(ChessHighlightModel.CHESS_HIGHLIGHT_LAYER, ChessHighlightModel::createBodyLayer);
		event.registerLayerDefinition(ChessTilesInfoModel.CHESS_TILES_INFO_LAYER, ChessTilesInfoModel::createBodyLayer);
	}
}