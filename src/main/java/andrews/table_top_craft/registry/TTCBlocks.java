package andrews.table_top_craft.registry;

import andrews.table_top_craft.TableTopCraft;
import andrews.table_top_craft.objects.blocks.ChessBlock;
import andrews.table_top_craft.objects.blocks.ChessPieceFigureBlock;
import andrews.table_top_craft.objects.blocks.TicTacToeBlock;
import andrews.table_top_craft.util.Reference;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;

public class TTCBlocks
{
	// Chess
	public static final Block OAK_CHESS				= createBlock("oak_chess", new ChessBlock(), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block SPRUCE_CHESS			= createBlock("spruce_chess", new ChessBlock(), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block BIRCH_CHESS			= createBlock("birch_chess", new ChessBlock(), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block JUNGLE_CHESS			= createBlock("jungle_chess", new ChessBlock(), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block ACACIA_CHESS			= createBlock("acacia_chess", new ChessBlock(), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block DARK_OAK_CHESS		= createBlock("dark_oak_chess", new ChessBlock(), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block CRIMSON_CHESS			= createBlock("crimson_chess", new ChessBlock(), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block WARPED_CHESS			= createBlock("warped_chess", new ChessBlock(), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	// Chess Piece Figure
	public static final Block CHESS_PIECE_FIGURE	= createISBERBlock("chess_piece_figure", new ChessPieceFigureBlock(), false, TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	//Tic Tac Toe
	public static final Block TIC_TAC_TOE			= createBlock("tic_tac_toe", new TicTacToeBlock(), TableTopCraft.TABLE_TOP_CRAFT_GROUP);

	public static void init() {}

	public static Block createISBERBlock(String name, Block block, boolean isItemStackable, @Nullable CreativeModeTab group)
	{
		Registry.register(Registry.ITEM, new ResourceLocation(Reference.MODID, name), new BlockItem(block, new Item.Properties().stacksTo(isItemStackable ? 64 : 1).tab(group)));
		return Registry.register(Registry.BLOCK, new ResourceLocation(Reference.MODID, name), block);
	}

	public static Block createBlock(String name, Block block, @Nullable CreativeModeTab group)
	{
		Registry.register(Registry.ITEM, new ResourceLocation(Reference.MODID, name), new BlockItem(block, new Item.Properties().tab(group)));
		return Registry.register(Registry.BLOCK, new ResourceLocation(Reference.MODID, name), block);
	}
}