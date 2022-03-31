package andrews.table_top_craft.registry;

import andrews.table_top_craft.tile_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.tile_entities.render.ChessPieceFigureTileEntityRenderer;
import com.google.common.collect.Sets;

import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.tile_entities.render.ChessTileEntityRenderer;
import andrews.table_top_craft.util.Reference;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TTCTileEntities
{
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Reference.MODID);
	
	public static final RegistryObject<BlockEntityType<ChessTileEntity>> CHESS                              = BLOCK_ENTITY_TYPES.register("chess", () -> new BlockEntityType<>(ChessTileEntity::new, Sets.newHashSet(TTCBlocks.OAK_CHESS.get(), TTCBlocks.SPRUCE_CHESS.get(), TTCBlocks.BIRCH_CHESS.get(), TTCBlocks.JUNGLE_CHESS.get(), TTCBlocks.ACACIA_CHESS.get(), TTCBlocks.DARK_OAK_CHESS.get(), TTCBlocks.CRIMSON_CHESS.get(), TTCBlocks.WARPED_CHESS.get()), null));
    public static final RegistryObject<BlockEntityType<ChessPieceFigureBlockEntity>> CHESS_PIECE_FIGURE     = BLOCK_ENTITY_TYPES.register("chess_piece_figure", () -> new BlockEntityType<>(ChessPieceFigureBlockEntity::new, Sets.newHashSet(TTCBlocks.CHESS_PIECE_FIGURE.get()), null));

    public static void registerTileRenders()
    {
        BlockEntityRenderers.register(CHESS.get(), ChessTileEntityRenderer::new);
        BlockEntityRenderers.register(CHESS_PIECE_FIGURE.get(), ChessPieceFigureTileEntityRenderer::new);
    }
}