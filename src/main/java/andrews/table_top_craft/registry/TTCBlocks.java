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
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

import javax.annotation.Nullable;

public class TTCBlocks
{
	// Tic Tac Toe
	public static final Block TIC_TAC_TOE					= createBlock("tic_tac_toe", new TicTacToeBlock(), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	// Chess Piece Figure
	public static final Block CHESS_PIECE_FIGURE			= createISBERBlock("chess_piece_figure", new ChessPieceFigureBlock(), false, TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	// Chess
	public static final Block OAK_CHESS						= createBlock("oak_chess", new ChessBlock(Material.WOOD, SoundType.WOOD), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block SPRUCE_CHESS					= createBlock("spruce_chess", new ChessBlock(Material.WOOD, SoundType.WOOD), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block BIRCH_CHESS					= createBlock("birch_chess", new ChessBlock(Material.WOOD, SoundType.WOOD), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block JUNGLE_CHESS					= createBlock("jungle_chess", new ChessBlock(Material.WOOD, SoundType.WOOD), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block ACACIA_CHESS					= createBlock("acacia_chess", new ChessBlock(Material.WOOD, SoundType.WOOD), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block DARK_OAK_CHESS				= createBlock("dark_oak_chess", new ChessBlock(Material.WOOD, SoundType.WOOD), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block CRIMSON_CHESS					= createBlock("crimson_chess", new ChessBlock(Material.WOOD, SoundType.WOOD), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block WARPED_CHESS					= createBlock("warped_chess", new ChessBlock(Material.WOOD, SoundType.WOOD), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block WHITE_TERRACOTTA_CHESS 	  	= createBlock("white_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block ORANGE_TERRACOTTA_CHESS 	  	= createBlock("orange_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block MAGENTA_TERRACOTTA_CHESS 	  	= createBlock("magenta_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block LIGHT_BLUE_TERRACOTTA_CHESS	= createBlock("light_blue_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block YELLOW_TERRACOTTA_CHESS 	  	= createBlock("yellow_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block LIME_TERRACOTTA_CHESS 	  	= createBlock("lime_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block PINK_TERRACOTTA_CHESS 	  	= createBlock("pink_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block GRAY_TERRACOTTA_CHESS 	  	= createBlock("gray_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block LIGHT_GRAY_TERRACOTTA_CHESS 	= createBlock("light_gray_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block CYAN_TERRACOTTA_CHESS 	  	= createBlock("cyan_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block PURPLE_TERRACOTTA_CHESS 	  	= createBlock("purple_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block BLUE_TERRACOTTA_CHESS 	  	= createBlock("blue_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block BROWN_TERRACOTTA_CHESS 	  	= createBlock("brown_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block GREEN_TERRACOTTA_CHESS 	  	= createBlock("green_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block RED_TERRACOTTA_CHESS 		  	= createBlock("red_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block BLACK_TERRACOTTA_CHESS 	  	= createBlock("black_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block WHITE_CONCRETE_CHESS 	  	  	= createBlock("white_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block ORANGE_CONCRETE_CHESS 	  	= createBlock("orange_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block MAGENTA_CONCRETE_CHESS 	  	= createBlock("magenta_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block LIGHT_BLUE_CONCRETE_CHESS   	= createBlock("light_blue_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block YELLOW_CONCRETE_CHESS 	  	= createBlock("yellow_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block LIME_CONCRETE_CHESS 	  	  	= createBlock("lime_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block PINK_CONCRETE_CHESS 	  	  	= createBlock("pink_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block GRAY_CONCRETE_CHESS 	  	  	= createBlock("gray_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block LIGHT_GRAY_CONCRETE_CHESS   	= createBlock("light_gray_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block CYAN_CONCRETE_CHESS 	  	  	= createBlock("cyan_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block PURPLE_CONCRETE_CHESS 	  	= createBlock("purple_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block BLUE_CONCRETE_CHESS 	  	  	= createBlock("blue_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block BROWN_CONCRETE_CHESS 	  	  	= createBlock("brown_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block GREEN_CONCRETE_CHESS 	  	  	= createBlock("green_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block RED_CONCRETE_CHESS 			= createBlock("red_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	public static final Block BLACK_CONCRETE_CHESS 	  	  	= createBlock("black_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE), TableTopCraft.TABLE_TOP_CRAFT_GROUP);

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