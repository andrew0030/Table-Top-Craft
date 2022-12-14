package andrews.table_top_craft.registry;

import andrews.table_top_craft.objects.blocks.ChessBlock;
import andrews.table_top_craft.objects.blocks.ChessPieceFigureBlock;
import andrews.table_top_craft.objects.blocks.TicTacToeBlock;
import andrews.table_top_craft.registry.util.BEWLRBlockItem;
import andrews.table_top_craft.util.Reference;
import com.google.common.base.Supplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TTCBlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MODID);

	// Chess
	public static final RegistryObject<Block> OAK_CHESS				= createBlock("oak_chess", ChessBlock::new);
	public static final RegistryObject<Block> SPRUCE_CHESS			= createBlock("spruce_chess", ChessBlock::new);
	public static final RegistryObject<Block> BIRCH_CHESS			= createBlock("birch_chess", ChessBlock::new);
	public static final RegistryObject<Block> JUNGLE_CHESS			= createBlock("jungle_chess", ChessBlock::new);
	public static final RegistryObject<Block> ACACIA_CHESS			= createBlock("acacia_chess", ChessBlock::new);
	public static final RegistryObject<Block> DARK_OAK_CHESS		= createBlock("dark_oak_chess", ChessBlock::new);
	public static final RegistryObject<Block> CRIMSON_CHESS			= createBlock("crimson_chess", ChessBlock::new);
	public static final RegistryObject<Block> WARPED_CHESS			= createBlock("warped_chess", ChessBlock::new);
	// Chess Piece Figure
	public static final RegistryObject<Block> CHESS_PIECE_FIGURE	= createISBERBlock("chess_piece_figure", ChessPieceFigureBlock::new, false);
	//Tic Tac Toe
	public static final RegistryObject<Block> TIC_TAC_TOE			= createBlock("tic_tac_toe", TicTacToeBlock::new);
	
	public static <B extends Block> RegistryObject<B> createBlock(String name, Supplier<? extends B> supplier)
	{
		RegistryObject<B> block = TTCBlocks.BLOCKS.register(name, supplier);
		TTCItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().stacksTo(1)));
		return block;
	}

	public static <B extends Block> RegistryObject<B> createISBERBlock(String name, Supplier<? extends B> supplier, boolean isItemStackable)
	{
		RegistryObject<B> block = TTCBlocks.BLOCKS.register(name, supplier);
		TTCItems.ITEMS.register(name, () -> new BEWLRBlockItem(block.get(), new Item.Properties().stacksTo(isItemStackable ? 64 : 1)));
		return block;
	}
}