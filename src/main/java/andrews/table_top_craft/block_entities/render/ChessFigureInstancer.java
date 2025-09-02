package andrews.table_top_craft.block_entities.render;

import andrews.table_top_craft.block_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;
import andrews.table_top_craft.objects.blocks.ChessPieceFigureBlock;
import andrews.table_top_craft.util.DrawScreenHelper;
import andrews.table_top_craft.util.NBTColorSaving;
import andrews.table_top_craft.util.TTCRenderTypes;
import andrews.table_top_craft.util.TTCShaders;
import com.github.andrew0030.pandora_core.client.render.collective.CollectiveBufferBuilder;
import com.github.andrew0030.pandora_core.client.render.collective.CollectiveDrawData;
import com.github.andrew0030.pandora_core.client.render.collective.CollectiveVBO;
import com.github.andrew0030.pandora_core.client.render.instancing.InstanceFormat;
import com.github.andrew0030.pandora_core.client.render.instancing.engine.BatchData;
import com.github.andrew0030.pandora_core.client.render.instancing.engine.BatchKey;
import com.github.andrew0030.pandora_core.client.render.renderers.instancing.InstancedBlockEntityRenderer;
import com.github.andrew0030.pandora_core.test.PaCoRenderTypes;
import com.github.andrew0030.pandora_core.test.TemplateShaderTest;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Random;

public class ChessFigureInstancer extends InstancedBlockEntityRenderer<ChessPieceFigureBlockEntity> {
    public ChessFigureInstancer(InstanceFormat format, CollectiveVBO vbo) {
        super(format, vbo);
    }

    protected CollectiveVBO vbo() {
        return DrawScreenHelper.CHESS_PIECE_MODEL.getCollectiveVBO();
    }

    private final BatchKey STANDARD_KEY = new BatchKey() {
        public void flush(CollectiveDrawData data) {
            vbo().setupData(data, TTCShaders.CHESS_INSTANCED);
            data.upload();
            vbo().drawWithShader(
                    RenderSystem.getModelViewMatrix(),
                    RenderSystem.getProjectionMatrix(),
                    RenderSystem.getShader()
            );
        }
    };

    @Override
    public void render(Level level, ChessPieceFigureBlockEntity blockEntity, BlockPos pos, BatchData batchData) {
        BasePiece.PieceModelSet set = BasePiece.PieceModelSet.get(blockEntity.getPieceSet());
        BasePiece.PieceType piece = BasePiece.PieceType.get(blockEntity.getPieceType());
//        BasePiece.PieceModelSet set = BasePiece.PieceModelSet.STANDARD;
//        BasePiece.PieceType piece = BasePiece.PieceType.PAWN;
        CollectiveBufferBuilder.MeshRange pawnBuffer = DrawScreenHelper.getBuffer(
                set,
                piece
        );
        // prevent the world from catching on fire
        if (pawnBuffer == null)
            return;

        float pct = 0;

        CollectiveDrawData data = batchData.buildBatch(STANDARD_KEY);

        int rotation = 0;
        if (blockEntity.hasLevel()) {
            BlockState blockstate = blockEntity.getLevel().getBlockState(blockEntity.getBlockPos());
            if (blockstate.getBlock() instanceof ChessPieceFigureBlock) {
                rotation = blockstate.getValue(ChessPieceFigureBlock.ROTATION);
            }
        }

        Matrix4f matrix3f = new Matrix4f();
        matrix3f.translate(pos.getX() + 8 * 0.0625F, pos.getY() + 2 * 0.0625F, pos.getZ() + 8 * 0.0625F);

        Quaternionf rotationQuat = Axis.YN.rotationDegrees(rotation * 22.5F);
        if (blockEntity.getRotateChessPieceFigure())
            rotationQuat.mul(Axis.YN.rotationDegrees(Minecraft.getInstance().player.tickCount + pct));

        float scale = 0.0625F * 5;
        if (blockEntity.hasLevel())
            scale *= (float) blockEntity.getPieceScale();

        matrix3f.scale(scale, scale, scale);
        if (blockEntity.getPieceName() != null && blockEntity.getPieceName().equals("Lyzantra")) {
            rotationQuat.mul(Axis.ZN.rotationDegrees(180));
//            matrix3f.translate((float) 0.0D, (float) (-0.4D), (float) 0.0D);
            matrix3f.translate((float) 0.0D, (float) (1.75D), (float) 0.0D);
        }
        matrix3f.rotate(rotationQuat);

        data.writeMesh(pawnBuffer);
        data.ensureInstance();
        data.activateData();
        data.writeMatrix(matrix3f);
        int $$0 = LightTexture.pack(
                level.getBrightness(LightLayer.BLOCK, pos),
                level.getBrightness(LightLayer.SKY, pos)
        );

        float red = NBTColorSaving.getRed(blockEntity.getPieceColor()) / 255F;
        float green = NBTColorSaving.getGreen(blockEntity.getPieceColor()) / 255F;
        float blue = NBTColorSaving.getBlue(blockEntity.getPieceColor()) / 255F;
//        float red = 1, green = 1, blue = 1;
        data.writeFloat(red, green, blue, 0);

        data.writeInt($$0);

        data.finishInstance();
    }

    @Override
    public void flush(Level level, BatchData batchData) {
        RenderSystem.setShaderFogShape(FogShape.SPHERE);
        RenderType type = TTCRenderTypes.getChessPieceSolid(
                ChessPieceFigureTileEntityRenderer.SHADER_COMPAT_WHITE
        );
        type.setupRenderState();
        RenderSystem.getShader().apply();
        vbo().bind();
        batchData.flush();
        vbo().unbindVBO();
        type.clearRenderState();
        RenderSystem.setShaderFogShape(FogShape.CYLINDER);
    }
}
