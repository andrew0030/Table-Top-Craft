package andrews.table_top_craft.block_entities.render;

import andrews.table_top_craft.block_entities.ChessPieceFigureBlockEntity;
import com.github.andrew0030.pandora_core.client.render.collective.CollectiveVBO;
import com.github.andrew0030.pandora_core.client.render.instancing.InstanceFormat;
import com.github.andrew0030.pandora_core.client.render.instancing.engine.BatchData;
import com.github.andrew0030.pandora_core.client.render.renderers.instancing.InstancedBlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class ChessFigureInstancer extends InstancedBlockEntityRenderer<ChessPieceFigureBlockEntity> {
    public ChessFigureInstancer(InstanceFormat format, CollectiveVBO vbo) {
        super(format, vbo);
    }

    @Override
    public void render(Level level, ChessPieceFigureBlockEntity chessPieceFigureBlockEntity, BlockPos pos, BatchData batchData) {

    }

    @Override
    public void flush(Level level, BatchData batchData) {

    }
}
