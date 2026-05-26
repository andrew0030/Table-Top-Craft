package andrews.table_top_craft.util.instancing;

import andrews.table_top_craft.game_logic.chess.pieces.BasePiece.PieceModelSet;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece.PieceType;
import andrews.table_top_craft.util.Triple;
import com.github.andrew0030.pandora_core.client.render.AcceleratedVBO;
import com.github.andrew0030.pandora_core.client.render.BufferBuilderUtils;
import com.github.andrew0030.pandora_core.modules.instancer.collective.CollectiveBufferBuilder;
import com.github.andrew0030.pandora_core.modules.instancer.collective.CollectiveVBO;
import com.github.andrew0030.pandora_core.client.render.obj.ObjModel;
import com.mojang.blaze3d.vertex.*;
import com.mojang.datafixers.util.Pair;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;

@OnlyIn(Dist.CLIENT)
public class ChessPieceModels {
	private static CollectiveVBO collectiveVBO;
	private static BufferBuilder builder = new BufferBuilder(2048);
	//    private static final HashMap<Pair<PieceType, PieceModelSet>, CollectiveBufferBuilder.MeshRange> RANGES = new HashMap<>();
	private static final CollectiveBufferBuilder.MeshRange[][] RANGES = new CollectiveBufferBuilder.MeshRange[PieceModelSet.values().length][PieceType.values().length];
	
	public void uploadVBO(Triple<PieceModelSet, PieceType, ObjModel>[] all) {
		if (collectiveVBO == null) {
			collectiveVBO = new CollectiveVBO(
					AcceleratedVBO.AccelerationUsage.STATIC_LOCKED,
					InstanceFormats.TRANSFORM_COLOR_LIGHTMAP
			);
		}
		
		BufferBuilderUtils.enforceExtended(builder, VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.NEW_ENTITY);
		CollectiveBufferBuilder multidrawBuffer = new CollectiveBufferBuilder(builder);
		
		for (Triple<PieceModelSet, PieceType, ObjModel> toLoad : all) {
			if (toLoad.getThird() != null) {
				toLoad.getThird().render(new PoseStack(), multidrawBuffer, 15728640);
				RANGES[toLoad.getFirst().ordinal()][toLoad.getSecond().ordinal()] =
						multidrawBuffer.endMesh(
								toLoad.getFirst().pathFor(toLoad.getSecond())
						);
			}
		}
		
		collectiveVBO.bind();
		collectiveVBO.upload(builder.end());
		VertexBuffer.unbind();
	}
	
	public CollectiveBufferBuilder.MeshRange get(PieceModelSet set, PieceType type) {
		return RANGES[set.ordinal()][type.ordinal()];
	}
	
	public CollectiveVBO getCollectiveVBO() {
		return collectiveVBO;
	}
}