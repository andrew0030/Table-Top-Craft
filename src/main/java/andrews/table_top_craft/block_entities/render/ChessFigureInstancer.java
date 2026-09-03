package andrews.table_top_craft.block_entities.render;

import andrews.table_top_craft.block_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;
import andrews.table_top_craft.objects.blocks.ChessPieceFigureBlock;
import andrews.table_top_craft.util.*;
import com.github.andrew0030.pandora_core.modules.fastlib.render.CullBox;
import com.github.andrew0030.pandora_core.modules.instancer.collective.CollectiveBufferBuilder;
import com.github.andrew0030.pandora_core.modules.instancer.collective.CollectiveDrawData;
import com.github.andrew0030.pandora_core.modules.instancer.collective.CollectiveVBO;
import com.github.andrew0030.pandora_core.modules.instancer.instancing.InstanceFormat;
import com.github.andrew0030.pandora_core.modules.instancer.instancing.engine.BatchData;
import com.github.andrew0030.pandora_core.modules.instancer.instancing.engine.BatchKey;
import com.github.andrew0030.pandora_core.modules.instancer.instancing.engine.PacoInstancingLevel;
import com.github.andrew0030.pandora_core.modules.instancer.renderers.instancing.InstancedBlockEntityRenderer;
import com.github.andrew0030.pandora_core.modules.instancer.state.PaCoShaderStateShard;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class ChessFigureInstancer extends InstancedBlockEntityRenderer<ChessPieceFigureBlockEntity> {
	CollectiveVBO vbo;
	
    public ChessFigureInstancer(InstanceFormat format, CollectiveVBO vbo) {
        super(format);
		this.vbo = vbo;
    }

    protected static CollectiveVBO vbo() {
        return DrawScreenHelper.CHESS_PIECE_MODEL.getCollectiveVBO();
    }

    public static final BatchKey STANDARD_KEY = new BatchKey() {
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
    public void render(PacoInstancingLevel ilevel, ChessPieceFigureBlockEntity blockEntity, BlockPos pos, BatchData batchData, float pct, Vec3 camera) {
	    BasePiece.PieceModelSet set = BasePiece.PieceModelSet.get(blockEntity.getPieceSet());
	    if (set == null) return;
	    BasePiece.PieceType piece = BasePiece.PieceType.get(blockEntity.getPieceType());
	    CollectiveBufferBuilder.MeshRange pawnBuffer = DrawScreenHelper.getBuffer(
			    set,
			    piece
	    );
	    // prevent the world from catching on fire
	    if (pawnBuffer == null)
		    return;
	    
	    Level level = (Level) ilevel;
		
        CollectiveDrawData data = batchData.buildBatch(STANDARD_KEY);

        int rotation = 0;
        if (blockEntity.hasLevel()) {
            BlockState blockstate = blockEntity.getBlockState();
            if (blockstate.getBlock() instanceof ChessPieceFigureBlock) {
                rotation = blockstate.getValue(ChessPieceFigureBlock.ROTATION);
            }
        }

        Matrix4f matrix3f = new Matrix4f();
		matrix3f.translate((float) -camera.x, (float) -camera.y, (float) -camera.z);
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
	    
	    float red = NBTColorSaving.getRed(blockEntity.getPieceColor()) / 255F;
	    float green = NBTColorSaving.getGreen(blockEntity.getPieceColor()) / 255F;
	    float blue = NBTColorSaving.getBlue(blockEntity.getPieceColor()) / 255F;
		if (blockEntity.getPieceName() != null && blockEntity.getPieceName().equals("andrew_")) {
			int tickCount = Minecraft.getInstance().player.tickCount;
			float value = ((tickCount % 180) + pct) * 2;
			Color color = new Color(0, 0, 0).fromHSV(value, 1.0F, 1.0F);
			red = color.getRed() / 255f;
			green = color.getGreen() / 255f;
			blue = color.getBlue() / 255f;
		}

        data.writeMesh(pawnBuffer);
        data.ensureInstance();
        data.activateData();
        data.writeMatrix(matrix3f);
        int $$0 = LightTexture.pack(
                level.getBrightness(LightLayer.BLOCK, pos),
                level.getBrightness(LightLayer.SKY, pos)
        );
		
        data.writeFloat(red, green, blue, 1);

        data.writeInt($$0);

        data.finishInstance();
    }

    @Override
    public void flush(PacoInstancingLevel level, BatchData batchData) {
        RenderSystem.setShaderFogShape(FogShape.SPHERE);
        RenderType type = TTCRenderTypes.getChessPieceSolid(
                ShaderCompatTexture.SHADER_COMPAT_WHITE
        );
        type.setupRenderState();
	    PaCoShaderStateShard shaderShard = TTCShaders.CHESS_INSTANCED_SHARD;
	    if (shaderShard.shouldRender()) {
		    RenderSystem.getShader().apply();
		    vbo().bind();
		    batchData.flush();
		    vbo().unbindVBO();
	    }
        type.clearRenderState();
        RenderSystem.setShaderFogShape(FogShape.CYLINDER);
    }
	
	@Override
	public void getCullBox(CullBox box, PacoInstancingLevel level, ChessPieceFigureBlockEntity object, BlockPos pos) {
		box.set(object.getRenderBoundingBox());
	}
}
