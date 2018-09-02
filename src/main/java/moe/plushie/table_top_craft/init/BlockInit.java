package moe.plushie.table_top_craft.init;

import java.util.ArrayList;
import java.util.List;

import moe.plushie.table_top_craft.objects.blocks.BlockChess;
import net.minecraft.block.Block;

public class BlockInit {
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block CHESS = new BlockChess("chess");
}
