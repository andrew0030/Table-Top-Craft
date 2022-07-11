package andrews.table_top_craft.util.obj.models;

import andrews.table_top_craft.game_logic.chess.pieces.BasePiece.PieceModelSet;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece.PieceType;
import andrews.table_top_craft.util.Reference;
import andrews.table_top_craft.util.obj.ObjModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;

@OnlyIn(Dist.CLIENT)
public class ChessObjModel
{
	private final HashMap<Pair<PieceModelSet, PieceType>, ObjModel> MODELS = new HashMap<>();
	
	public ChessObjModel()
	{
		for (PieceModelSet value : PieceModelSet.values())
		{
			for (PieceType pieceType : PieceType.values())
			{
				MODELS.put(Pair.of(value, pieceType), ObjModel.loadModel(new ResourceLocation(Reference.MODID, value.pathFor(pieceType))));
			}
		}
	}
	
	/**
	 * Renders the ObjModel
	 * @param stack The MatrixStack
	 * @param buffer The IRenderTypeBuffer
	 * @param pieceType The Chess Piece Type
	 */
	public void render(PoseStack stack, VertexConsumer buffer, PieceType pieceType, PieceModelSet pieceModelSet)
	{	
		stack.pushPose();
		// We scale the Piece and invert the rendering
		stack.scale(1F, -1F, -1F);
		stack.scale(0.1F, 0.1F, 0.1F);
		MODELS.get(Pair.of(pieceModelSet, pieceType)).render(stack, buffer);
		stack.popPose();
	}
}