package andrews.table_top_craft.block_entities.render.item;

import andrews.table_top_craft.block_entities.render.ChessFigureInstancer;
import andrews.table_top_craft.block_entities.render.ShaderCompatTexture;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;
import andrews.table_top_craft.registry.TTCBlocks;
import andrews.table_top_craft.block_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.util.*;
import andrews.table_top_craft.util.instancing.InstanceFormats;
import com.github.andrew0030.pandora_core.modules.instancer.collective.CollectiveBufferBuilder;
import com.github.andrew0030.pandora_core.modules.instancer.collective.CollectiveDrawData;
import com.github.andrew0030.pandora_core.modules.instancer.collective.CollectiveVBO;
import com.github.andrew0030.pandora_core.modules.instancer.instancing.InstanceFormat;
import com.github.andrew0030.pandora_core.modules.instancer.instancing.builtin.ItemDrawData;
import com.github.andrew0030.pandora_core.modules.instancer.instancing.engine.BatchData;
import com.github.andrew0030.pandora_core.modules.instancer.instancing.engine.BatchKey;
import com.github.andrew0030.pandora_core.modules.instancer.instancing.engine.InstancingEnvironment;
import com.github.andrew0030.pandora_core.modules.instancer.renderers.instancing.InstancedItemRenderer;
import com.github.andrew0030.pandora_core.modules.instancer.state.PaCoRenderState;
import com.github.andrew0030.pandora_core.modules.instancer.state.PaCoShaderStateShard;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

public class PieceFigureInstancer extends InstancedItemRenderer
{
    // Instance of the class
    public static PieceFigureInstancer INSTANCE = new PieceFigureInstancer(InstanceFormats.TRANSFORM_COLOR_LIGHTMAP);
    // Block Entities
    private final ChessPieceFigureBlockEntity chessPieceFigureBlockEntity;

    private Color color = new Color(0, 0, 0);
	
	public PieceFigureInstancer(InstanceFormat format) {
		super(format);
		chessPieceFigureBlockEntity = new ChessPieceFigureBlockEntity(BlockPos.ZERO, TTCBlocks.CHESS_PIECE_FIGURE.get().defaultBlockState());
	}
	
	protected CollectiveVBO vbo() {
		return DrawScreenHelper.CHESS_PIECE_MODEL.getCollectiveVBO();
	}
	
	public final BatchKey GUI_KEY = new BatchKey() {
		public void flush(CollectiveDrawData data) {
			Lighting.setupForEntityInInventory();
			vbo().setupData(data, TTCShaders.CHESS_INSTANCED);
			data.upload();
			vbo().drawWithShader(
					RenderSystem.getModelViewMatrix(),
					RenderSystem.getProjectionMatrix(),
					RenderSystem.getShader()
			);
			Lighting.setupFor3DItems();
		}
	};
	
	@Override
	public void render(InstancingEnvironment instancingEnvironment, ItemStack itemStack, ItemDrawData itemDrawData, BatchData batchData, float v, Vec3 vec3) {
		
		try
		{
			// We get the Piece Type from the item, if there is none we just render a Pawn
			CompoundTag compoundTag = BlockItem.getBlockEntityData(itemStack);
			// We get and set Piece Type
			if(compoundTag != null && compoundTag.contains("PieceType", Tag.TAG_INT))
				chessPieceFigureBlockEntity.setPieceType(compoundTag.getInt("PieceType"));
			else
				chessPieceFigureBlockEntity.setPieceType(1);
			// We get and set Rotation
			if(compoundTag != null && compoundTag.contains("RotateChessPieceFigure", Tag.TAG_INT))
				chessPieceFigureBlockEntity.setRotateChessPieceFigure(compoundTag.getInt("RotateChessPieceFigure") != 0);
			else
				chessPieceFigureBlockEntity.setRotateChessPieceFigure(false);
			// We get and set Color
			if(compoundTag != null && compoundTag.contains("PieceColor", Tag.TAG_STRING))
				chessPieceFigureBlockEntity.setPieceColor(compoundTag.getString("PieceColor"));
			else
				chessPieceFigureBlockEntity.setPieceColor(NBTColorSaving.getString(NBTColorSaving.createWhitePiecesColor()));
			// We get and set the Piece Set
			if(compoundTag != null && compoundTag.contains("PieceSet", Tag.TAG_INT))
				chessPieceFigureBlockEntity.setPieceSet(compoundTag.getInt("PieceSet"));
			else
				chessPieceFigureBlockEntity.setPieceSet(1);
			
			if(itemStack.getHoverName().getString().equals("andrew_"))
			{
				int tickCount = Minecraft.getInstance().player.tickCount;
				int value = (tickCount % 180) * 2;
				color = color.fromHSV(value, 1.0F, 1.0F);
				chessPieceFigureBlockEntity.setPieceColor(color.getRed() + "/" + color.getGreen() + "/" + color.getBlue() + "/255");
			}
			if(itemStack.getHoverName().getString().equals("Lyzantra"))
				chessPieceFigureBlockEntity.setPieceName("Lyzantra");
			else
				chessPieceFigureBlockEntity.setPieceName(null);

			draw(itemDrawData, batchData, v);
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
	
	private void draw(ItemDrawData itemDrawData, BatchData batchData, float pct) {
		ChessPieceFigureBlockEntity blockEntity = chessPieceFigureBlockEntity;
		
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
		
		CollectiveDrawData data = batchData.buildBatch(
				PaCoRenderState.isGUI() ?
				GUI_KEY :
				ChessFigureInstancer.STANDARD_KEY
		);
		PoseStack ps = itemDrawData.getPoseStack();
		ps.translate(8 * 0.0625F, 2 * 0.0625F, 8 * 0.0625F);
		
		Quaternionf rotationQuat = Axis.YN.rotationDegrees(0);
		if (blockEntity.getRotateChessPieceFigure())
			rotationQuat.mul(Axis.YN.rotationDegrees(Minecraft.getInstance().player.tickCount + pct));
		
		float scale = 0.0625F * 5;
		boolean isInGui = itemDrawData.getDisplayContext() == ItemDisplayContext.GUI;
		boolean isHeldOrHead = isHeldOrHead(itemDrawData.getDisplayContext());
		if (isInGui || isHeldOrHead)
		{
			ps.scale(isHeldOrHead ? 1.3F : 1.4F, isHeldOrHead ? 1.3F : 1.4F, isHeldOrHead ? 1.3F : 1.4F);
			if (blockEntity.getPieceName() != null && blockEntity.getPieceName().equals("Lyzantra"))
			{
				ps.translate(0.0D, 0.05D, 0.0D);
			}
		}
		if (blockEntity.hasLevel())
			scale *= (float) blockEntity.getPieceScale();
		
		ps.scale(scale, scale, scale);
		
		if (blockEntity.getPieceName() != null && blockEntity.getPieceName().equals("Lyzantra")) {
			rotationQuat.mul(Axis.ZN.rotationDegrees(180));
//            matrix3f.translate((float) 0.0D, (float) (-0.4D), (float) 0.0D);
			ps.translate((float) 0.0D, (float) (1.75D), (float) 0.0D);
		}
		ps.mulPose(rotationQuat);
		
		data.writeMesh(pawnBuffer);
		data.ensureInstance();
		data.activateData();
		data.writeMatrix(ps.last().pose());
		int $$0 = itemDrawData.getLightmap();
		
		float red = NBTColorSaving.getRed(blockEntity.getPieceColor()) / 255F;
		float green = NBTColorSaving.getGreen(blockEntity.getPieceColor()) / 255F;
		float blue = NBTColorSaving.getBlue(blockEntity.getPieceColor()) / 255F;
		data.writeFloat(red, green, blue, 1);
		
		data.writeInt($$0);
		
		data.finishInstance();
	}
	
	@Override
	public void flush(InstancingEnvironment instancingEnvironment, BatchData batchData) {
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

    private boolean isHeldOrHead(ItemDisplayContext type)
    {
        return type.equals(ItemDisplayContext.HEAD) || type.equals(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND) || type.equals(ItemDisplayContext.THIRD_PERSON_LEFT_HAND);
    }
}