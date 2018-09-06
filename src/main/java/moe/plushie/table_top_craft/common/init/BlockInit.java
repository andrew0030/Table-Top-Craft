package moe.plushie.table_top_craft.common.init;

import java.util.ArrayList;
import java.util.List;

import moe.plushie.table_top_craft.TableTopCraft;
import moe.plushie.table_top_craft.common.blocks.BlockChessTable;
import moe.plushie.table_top_craft.common.lib.ModReference;
import moe.plushie.table_top_craft.common.tileentities.TileEntityChessTable;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber(modid = ModReference.MOD_ID)
public final class BlockInit {
    
    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public static final Block CHESS_TABLE = new BlockChessTable("chess");
    
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        TableTopCraft.getLogger().info("------register blocks------");
        event.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new Block[0]));
        registerTileEntities();
    }
    
    private static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityChessTable.class, new ResourceLocation(ModReference.MOD_ID, "tile-entity.chess-table"));
    }
}
