package andrews.table_top_craft.registry;

import andrews.table_top_craft.objects.blocks.ChessBlock;
import andrews.table_top_craft.objects.blocks.ChessTimerBlock;
import andrews.table_top_craft.objects.blocks.ConnectFourBlock;
import andrews.table_top_craft.block_entities.*;
import andrews.table_top_craft.block_entities.render.*;
import andrews.table_top_craft.util.Reference;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.ArrayList;
import java.util.List;

public class TTCBlockEntities
{
    public static final BlockEntityType<ChessBlockEntity> CHESS                              = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(Reference.MODID, "chess"), FabricBlockEntityTypeBuilder.create(ChessBlockEntity::new, TTCBlockEntities.getBlocksOfClass(ChessBlock.class)).build(null));
    public static final BlockEntityType<ChessPieceFigureBlockEntity> CHESS_PIECE_FIGURE     = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(Reference.MODID, "chess_piece_figure"), FabricBlockEntityTypeBuilder.create(ChessPieceFigureBlockEntity::new, TTCBlocks.CHESS_PIECE_FIGURE).build(null));
    public static final BlockEntityType<TicTacToeBlockEntity> TIC_TAC_TOE                   = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(Reference.MODID, "tic_tac_toe"), FabricBlockEntityTypeBuilder.create(TicTacToeBlockEntity::new, TTCBlocks.TIC_TAC_TOE).build(null));
    public static final BlockEntityType<ChessTimerBlockEntity> CHESS_TIMER                  = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(Reference.MODID, "chess_timer"), FabricBlockEntityTypeBuilder.create(ChessTimerBlockEntity::new, TTCBlockEntities.getBlocksOfClass(ChessTimerBlock.class)).build(null));
    public static final BlockEntityType<ConnectFourBlockEntity> CONNECT_FOUR                = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(Reference.MODID, "connect_four"), FabricBlockEntityTypeBuilder.create(ConnectFourBlockEntity::new, TTCBlockEntities.getBlocksOfClass(ConnectFourBlock.class)).build(null));

    public static void init() {}

    public static void registerBlockEntityRenderers()
    {
        BlockEntityRenderers.register(CHESS, ChessBlockEntityRenderer::new);
        BlockEntityRenderers.register(CHESS_PIECE_FIGURE, ChessPieceFigureBlockEntityRenderer::new);
        BlockEntityRenderers.register(TIC_TAC_TOE, TicTacToeBlockEntityRenderer::new);
        BlockEntityRenderers.register(CHESS_TIMER, ChessTimerBlockEntityRenderer::new);
        BlockEntityRenderers.register(CONNECT_FOUR, ConnectFourBlockEntityRenderer::new);
    }

    private static Block[] getBlocksOfClass(Class<?> clazz)
    {
        List<Block> blocks = new ArrayList<>();
        BuiltInRegistries.BLOCK.forEach(blockRO -> {
            if (clazz.isAssignableFrom(blockRO.getClass()))
                blocks.add(blockRO);
        });
        return blocks.toArray(new Block[0]);
    }
}