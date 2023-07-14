package andrews.table_top_craft.registry;

import andrews.table_top_craft.objects.blocks.*;
import andrews.table_top_craft.util.Reference;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class TTCBlocks
{
	//Tic Tac Toe
	public static final Block TIC_TAC_TOE						= createBlock("tic_tac_toe", new TicTacToeBlock());
	// Chess Piece Figure
	public static final Block CHESS_PIECE_FIGURE				= createISBERBlock("chess_piece_figure", new ChessPieceFigureBlock(), false);
	// Chess
	public static final Block OAK_CHESS					  		= createBlock("oak_chess", new ChessBlock(Material.WOOD, SoundType.WOOD));
	public static final Block SPRUCE_CHESS				  		= createBlock("spruce_chess", new ChessBlock(Material.WOOD, SoundType.WOOD));
	public static final Block BIRCH_CHESS				  		= createBlock("birch_chess", new ChessBlock(Material.WOOD, SoundType.WOOD));
	public static final Block JUNGLE_CHESS				  		= createBlock("jungle_chess", new ChessBlock(Material.WOOD, SoundType.WOOD));
	public static final Block ACACIA_CHESS				  		= createBlock("acacia_chess", new ChessBlock(Material.WOOD, SoundType.WOOD));
	public static final Block DARK_OAK_CHESS			  		= createBlock("dark_oak_chess", new ChessBlock(Material.WOOD, SoundType.WOOD));
	public static final Block CRIMSON_CHESS				  		= createBlock("crimson_chess", new ChessBlock(Material.WOOD, SoundType.WOOD));
	public static final Block WARPED_CHESS				  		= createBlock("warped_chess", new ChessBlock(Material.WOOD, SoundType.WOOD));
	public static final Block MANGROVE_CHESS			  		= createBlock("mangrove_chess", new ChessBlock(Material.WOOD, SoundType.WOOD));
	public static final Block WHITE_TERRACOTTA_CHESS 	  		= createBlock("white_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block ORANGE_TERRACOTTA_CHESS 	  		= createBlock("orange_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block MAGENTA_TERRACOTTA_CHESS 	  		= createBlock("magenta_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block LIGHT_BLUE_TERRACOTTA_CHESS 		= createBlock("light_blue_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block YELLOW_TERRACOTTA_CHESS 	  		= createBlock("yellow_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block LIME_TERRACOTTA_CHESS 	  		= createBlock("lime_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block PINK_TERRACOTTA_CHESS 	  		= createBlock("pink_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block GRAY_TERRACOTTA_CHESS 	  		= createBlock("gray_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block LIGHT_GRAY_TERRACOTTA_CHESS 		= createBlock("light_gray_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block CYAN_TERRACOTTA_CHESS 	  		= createBlock("cyan_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block PURPLE_TERRACOTTA_CHESS 	  		= createBlock("purple_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block BLUE_TERRACOTTA_CHESS 	  		= createBlock("blue_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block BROWN_TERRACOTTA_CHESS 	  		= createBlock("brown_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block GREEN_TERRACOTTA_CHESS 	  		= createBlock("green_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block RED_TERRACOTTA_CHESS 		  		= createBlock("red_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block BLACK_TERRACOTTA_CHESS 	  		= createBlock("black_terracotta_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block WHITE_CONCRETE_CHESS 	  	  		= createBlock("white_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block ORANGE_CONCRETE_CHESS 	  		= createBlock("orange_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block MAGENTA_CONCRETE_CHESS 	  		= createBlock("magenta_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block LIGHT_BLUE_CONCRETE_CHESS   		= createBlock("light_blue_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block YELLOW_CONCRETE_CHESS 	  		= createBlock("yellow_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block LIME_CONCRETE_CHESS 	  	  		= createBlock("lime_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block PINK_CONCRETE_CHESS 	  	  		= createBlock("pink_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block GRAY_CONCRETE_CHESS 	  	  		= createBlock("gray_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block LIGHT_GRAY_CONCRETE_CHESS   		= createBlock("light_gray_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block CYAN_CONCRETE_CHESS 	  	  		= createBlock("cyan_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block PURPLE_CONCRETE_CHESS 	  		= createBlock("purple_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block BLUE_CONCRETE_CHESS 	  	  		= createBlock("blue_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block BROWN_CONCRETE_CHESS 	  	  		= createBlock("brown_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block GREEN_CONCRETE_CHESS 	  	  		= createBlock("green_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block RED_CONCRETE_CHESS 				= createBlock("red_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	public static final Block BLACK_CONCRETE_CHESS 	  	  		= createBlock("black_concrete_chess", new ChessBlock(Material.STONE, SoundType.STONE));
	// Chess Timers
	public static final Block OAK_CHESS_TIMER					= createBlock("oak_chess_timer", new ChessTimerBlock(Material.WOOD, SoundType.WOOD));
	public static final Block SPRUCE_CHESS_TIMER         		= createBlock("spruce_chess_timer", new ChessTimerBlock(Material.WOOD, SoundType.WOOD));
	public static final Block BIRCH_CHESS_TIMER           		= createBlock("birch_chess_timer", new ChessTimerBlock(Material.WOOD, SoundType.WOOD));
	public static final Block JUNGLE_CHESS_TIMER          		= createBlock("jungle_chess_timer", new ChessTimerBlock(Material.WOOD, SoundType.WOOD));
	public static final Block ACACIA_CHESS_TIMER          		= createBlock("acacia_chess_timer", new ChessTimerBlock(Material.WOOD, SoundType.WOOD));
	public static final Block DARK_OAK_CHESS_TIMER        		= createBlock("dark_oak_chess_timer", new ChessTimerBlock(Material.WOOD, SoundType.WOOD));
	public static final Block CRIMSON_CHESS_TIMER         		= createBlock("crimson_chess_timer", new ChessTimerBlock(Material.WOOD, SoundType.WOOD));
	public static final Block WARPED_CHESS_TIMER          		= createBlock("warped_chess_timer", new ChessTimerBlock(Material.WOOD, SoundType.WOOD));
	public static final Block MANGROVE_CHESS_TIMER        		= createBlock("mangrove_chess_timer", new ChessTimerBlock(Material.WOOD, SoundType.WOOD));
	public static final Block WHITE_TERRACOTTA_CHESS_TIMER  	= createBlock("white_terracotta_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block ORANGE_TERRACOTTA_CHESS_TIMER 	= createBlock("orange_terracotta_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block MAGENTA_TERRACOTTA_CHESS_TIMER 	= createBlock("magenta_terracotta_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block LIGHT_BLUE_TERRACOTTA_CHESS_TIMER = createBlock("light_blue_terracotta_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block YELLOW_TERRACOTTA_CHESS_TIMER		= createBlock("yellow_terracotta_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block LIME_TERRACOTTA_CHESS_TIMER  		= createBlock("lime_terracotta_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block PINK_TERRACOTTA_CHESS_TIMER  		= createBlock("pink_terracotta_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block GRAY_TERRACOTTA_CHESS_TIMER   	= createBlock("gray_terracotta_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block LIGHT_GRAY_TERRACOTTA_CHESS_TIMER = createBlock("light_gray_terracotta_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block CYAN_TERRACOTTA_CHESS_TIMER		= createBlock("cyan_terracotta_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block PURPLE_TERRACOTTA_CHESS_TIMER		= createBlock("purple_terracotta_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block BLUE_TERRACOTTA_CHESS_TIMER		= createBlock("blue_terracotta_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block BROWN_TERRACOTTA_CHESS_TIMER		= createBlock("brown_terracotta_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block GREEN_TERRACOTTA_CHESS_TIMER		= createBlock("green_terracotta_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block RED_TERRACOTTA_CHESS_TIMER 		= createBlock("red_terracotta_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block BLACK_TERRACOTTA_CHESS_TIMER		= createBlock("black_terracotta_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block WHITE_CONCRETE_CHESS_TIMER  		= createBlock("white_concrete_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block ORANGE_CONCRETE_CHESS_TIMER 		= createBlock("orange_concrete_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block MAGENTA_CONCRETE_CHESS_TIMER 		= createBlock("magenta_concrete_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block LIGHT_BLUE_CONCRETE_CHESS_TIMER 	= createBlock("light_blue_concrete_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block YELLOW_CONCRETE_CHESS_TIMER 		= createBlock("yellow_concrete_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block LIME_CONCRETE_CHESS_TIMER  		= createBlock("lime_concrete_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block PINK_CONCRETE_CHESS_TIMER  		= createBlock("pink_concrete_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block GRAY_CONCRETE_CHESS_TIMER   		= createBlock("gray_concrete_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block LIGHT_GRAY_CONCRETE_CHESS_TIMER 	= createBlock("light_gray_concrete_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block CYAN_CONCRETE_CHESS_TIMER    		= createBlock("cyan_concrete_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block PURPLE_CONCRETE_CHESS_TIMER		= createBlock("purple_concrete_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block BLUE_CONCRETE_CHESS_TIMER   		= createBlock("blue_concrete_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block BROWN_CONCRETE_CHESS_TIMER 		= createBlock("brown_concrete_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block GREEN_CONCRETE_CHESS_TIMER  		= createBlock("green_concrete_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block RED_CONCRETE_CHESS_TIMER   		= createBlock("red_concrete_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	public static final Block BLACK_CONCRETE_CHESS_TIMER  		= createBlock("black_concrete_chess_timer", new ChessTimerBlock(Material.STONE, SoundType.STONE));
	// Connect Four
	public static final Block OAK_CONNECT_FOUR					= createBlock("oak_connect_four", new ConnectFourBlock(Material.WOOD, SoundType.WOOD));
	public static final Block SPRUCE_CONNECT_FOUR  				= createBlock("spruce_connect_four", new ConnectFourBlock(Material.WOOD, SoundType.WOOD));
	public static final Block BIRCH_CONNECT_FOUR   				= createBlock("birch_connect_four", new ConnectFourBlock(Material.WOOD, SoundType.WOOD));
	public static final Block JUNGLE_CONNECT_FOUR 	 			= createBlock("jungle_connect_four", new ConnectFourBlock(Material.WOOD, SoundType.WOOD));
	public static final Block ACACIA_CONNECT_FOUR  				= createBlock("acacia_connect_four", new ConnectFourBlock(Material.WOOD, SoundType.WOOD));
	public static final Block DARK_OAK_CONNECT_FOUR 			= createBlock("dark_oak_connect_four", new ConnectFourBlock(Material.WOOD, SoundType.WOOD));
	public static final Block CRIMSON_CONNECT_FOUR  			= createBlock("crimson_connect_four", new ConnectFourBlock(Material.WOOD, SoundType.WOOD));
	public static final Block WARPED_CONNECT_FOUR   			= createBlock("warped_connect_four", new ConnectFourBlock(Material.WOOD, SoundType.WOOD));
	public static final Block MANGROVE_CONNECT_FOUR 			= createBlock("mangrove_connect_four", new ConnectFourBlock(Material.WOOD, SoundType.WOOD));
	public static final Block WHITE_TERRACOTTA_CONNECT_FOUR 	= createBlock("white_terracotta_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block ORANGE_TERRACOTTA_CONNECT_FOUR 	= createBlock("orange_terracotta_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block MAGENTA_TERRACOTTA_CONNECT_FOUR 	= createBlock("magenta_terracotta_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block LIGHT_BLUE_TERRACOTTA_CONNECT_FOUR = createBlock("light_blue_terracotta_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block YELLOW_TERRACOTTA_CONNECT_FOUR 	= createBlock("yellow_terracotta_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block LIME_TERRACOTTA_CONNECT_FOUR		= createBlock("lime_terracotta_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block PINK_TERRACOTTA_CONNECT_FOUR		= createBlock("pink_terracotta_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block GRAY_TERRACOTTA_CONNECT_FOUR		= createBlock("gray_terracotta_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block LIGHT_GRAY_TERRACOTTA_CONNECT_FOUR = createBlock("light_gray_terracotta_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block CYAN_TERRACOTTA_CONNECT_FOUR		= createBlock("cyan_terracotta_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block PURPLE_TERRACOTTA_CONNECT_FOUR 	= createBlock("purple_terracotta_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block BLUE_TERRACOTTA_CONNECT_FOUR		= createBlock("blue_terracotta_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block BROWN_TERRACOTTA_CONNECT_FOUR		= createBlock("brown_terracotta_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block GREEN_TERRACOTTA_CONNECT_FOUR		= createBlock("green_terracotta_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block RED_TERRACOTTA_CONNECT_FOUR		= createBlock("red_terracotta_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block BLACK_TERRACOTTA_CONNECT_FOUR 	= createBlock("black_terracotta_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block WHITE_CONCRETE_CONNECT_FOUR 		= createBlock("white_concrete_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block ORANGE_CONCRETE_CONNECT_FOUR		= createBlock("orange_concrete_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block MAGENTA_CONCRETE_CONNECT_FOUR 	= createBlock("magenta_concrete_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block LIGHT_BLUE_CONCRETE_CONNECT_FOUR 	= createBlock("light_blue_concrete_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block YELLOW_CONCRETE_CONNECT_FOUR		= createBlock("yellow_concrete_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block LIME_CONCRETE_CONNECT_FOUR 		= createBlock("lime_concrete_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block PINK_CONCRETE_CONNECT_FOUR		= createBlock("pink_concrete_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block GRAY_CONCRETE_CONNECT_FOUR  		= createBlock("gray_concrete_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block LIGHT_GRAY_CONCRETE_CONNECT_FOUR 	= createBlock("light_gray_concrete_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block CYAN_CONCRETE_CONNECT_FOUR 		= createBlock("cyan_concrete_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block PURPLE_CONCRETE_CONNECT_FOUR 		= createBlock("purple_concrete_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block BLUE_CONCRETE_CONNECT_FOUR 		= createBlock("blue_concrete_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block BROWN_CONCRETE_CONNECT_FOUR		= createBlock("brown_concrete_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block GREEN_CONCRETE_CONNECT_FOUR		= createBlock("green_concrete_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block RED_CONCRETE_CONNECT_FOUR			= createBlock("red_concrete_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));
	public static final Block BLACK_CONCRETE_CONNECT_FOUR 		= createBlock("black_concrete_connect_four", new ConnectFourBlock(Material.STONE, SoundType.STONE));

	public static void init() {}

	public static Block createBlock(String name, Block block)
	{
		return createBlock(name, block, 64);
	}

	public static Block createBlock(String name, Block block, int maxStackSize)
	{
		Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Reference.MODID, name), new BlockItem(block, new Item.Properties().stacksTo(maxStackSize)));
		return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Reference.MODID, name), block);
	}

	public static Block createISBERBlock(String name, Block block, boolean isItemStackable)
	{
		Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Reference.MODID, name), new BlockItem(block, new Item.Properties().stacksTo(isItemStackable ? 64 : 1)));
		return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Reference.MODID, name), block);
	}
}