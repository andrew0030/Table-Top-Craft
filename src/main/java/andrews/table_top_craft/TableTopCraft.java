package andrews.table_top_craft;

import andrews.table_top_craft.network.TTCNetwork;
import andrews.table_top_craft.registry.*;
import andrews.table_top_craft.tile_entities.model.chess.ChessBoardPlateModel;
import andrews.table_top_craft.tile_entities.model.chess.ChessHighlightModel;
import andrews.table_top_craft.tile_entities.model.chess.ChessTilesInfoModel;
import andrews.table_top_craft.tile_entities.model.chess.GhostModel;
import andrews.table_top_craft.tile_entities.model.piece_figure.ChessPieceFigureStandModel;
import andrews.table_top_craft.tile_entities.model.tic_tac_toe.TicTacToeModel;
import andrews.table_top_craft.util.Reference;
import andrews.table_top_craft.util.shader_compat.ShaderCompatHandler;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Objects;

@Mod(value = Reference.MODID)
public class TableTopCraft
{
	@Nullable
	public static ShaderInstance rendertypeSolidBlockEntityShader;

	/**
	 * @return The Table Top Craft chess piece Shader.
	 */
	public static ShaderInstance getSolidBlockEntityShader()
	{
		return Objects.requireNonNull(rendertypeSolidBlockEntityShader, "Attempted to call getSolidBlockEntityShader before shaders have finished loading.");
	}

	public TableTopCraft()
	{
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		
		TTCItems.ITEMS.register(modEventBus);
		TTCBlocks.BLOCKS.register(modEventBus);
		TTCTileEntities.BLOCK_ENTITY_TYPES.register(modEventBus);
		TTCLootItemFunctions.ITEM_FUNCTION_TYPES.register(modEventBus);
		TTCParticles.PARTICLES.register(modEventBus);
		
		DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
		{
			modEventBus.addListener(EventPriority.LOWEST, this::setupClient);
			modEventBus.addListener(this::setupLayers);
			modEventBus.addListener(this::registerShaders);
			modEventBus.addListener(this::registerParticles);
		});
		modEventBus.addListener(EventPriority.LOWEST, this::setupCommon);

		try {
			Class<?> clazz = Class.forName("net.optifine.Config");
			if (clazz != null)
				ShaderCompatHandler.initOFCompat();
		} catch (Throwable ignored) {}
	}
	
	void setupCommon(final FMLCommonSetupEvent event)
	{
		TTCNetwork.setupMessages();
	}

	void setupClient(final FMLClientSetupEvent event)
	{
		event.enqueueWork(TTCTileEntities::registerTileRenders);
	}

	void setupLayers(final EntityRenderersEvent.RegisterLayerDefinitions event)
	{
		event.registerLayerDefinition(ChessBoardPlateModel.CHESS_BOARD_PLATE_LAYER, ChessBoardPlateModel::createBodyLayer);
		event.registerLayerDefinition(ChessHighlightModel.CHESS_HIGHLIGHT_LAYER, ChessHighlightModel::createBodyLayer);
		event.registerLayerDefinition(ChessTilesInfoModel.CHESS_TILES_INFO_LAYER, ChessTilesInfoModel::createBodyLayer);
		event.registerLayerDefinition(ChessPieceFigureStandModel.CHESS_PIECE_FIGURE_LAYER, ChessPieceFigureStandModel::createBodyLayer);
		event.registerLayerDefinition(TicTacToeModel.TIC_TAC_TOE_LAYER, TicTacToeModel::createBodyLayer);
		event.registerLayerDefinition(GhostModel.LAYER, GhostModel::createBodyLayer);
	}

	void registerShaders(final RegisterShadersEvent event)
	{
		try
		{
			event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation(Reference.MODID,"rendertype_solid"), DefaultVertexFormat.BLOCK), (shader) -> {
				rendertypeSolidBlockEntityShader = shader;
			});
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	void registerParticles(final RegisterParticleProvidersEvent event)
	{
		TTCParticles.registerParticles();
	}
}