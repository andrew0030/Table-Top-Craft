package andrews.table_top_craft.registry;

import com.google.common.collect.Sets;

import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.tile_entities.render.ChessTileEntityRenderer;
import andrews.table_top_craft.util.Reference;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TTCTileEntities
{
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Reference.MODID);
	
	public static final RegistryObject<TileEntityType<ChessTileEntity>> CHESS = TILE_ENTITY_TYPES.register("chess", () -> new TileEntityType<>(ChessTileEntity::new, Sets.newHashSet(TTCBlocks.CHESS.get()), null));
	
    @OnlyIn(Dist.CLIENT)
    public static void registerTileRenders()
    {
    	ClientRegistry.bindTileEntityRenderer(CHESS.get(), ChessTileEntityRenderer::new);
    }
}