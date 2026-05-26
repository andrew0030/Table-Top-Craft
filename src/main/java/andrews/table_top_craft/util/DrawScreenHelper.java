package andrews.table_top_craft.util;

import andrews.table_top_craft.game_logic.chess.pieces.BasePiece.PieceModelSet;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece.PieceType;
import andrews.table_top_craft.util.instancing.ChessPieceModels;
import com.github.andrew0030.pandora_core.modules.instancer.collective.CollectiveBufferBuilder;
import com.github.andrew0030.pandora_core.client.render.obj.ObjLoader;
import com.github.andrew0030.pandora_core.client.render.obj.ObjModel;
import com.github.andrew0030.pandora_core.platform.Services;
import com.github.andrew0030.pandora_core.utils.LogicalSide;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class DrawScreenHelper {
    public static final ChessPieceModels CHESS_PIECE_MODEL = new ChessPieceModels();

    private static final ObjLoader loader = new ObjLoader(
            List.of("models/pieces"),
            (pth) -> pth.getNamespace().equals("table_top_craft") && pth.getPath().endsWith(".obj"),
            (loader) -> CHESS_PIECE_MODEL.uploadVBO(
                    collect(
                            pieceSet(loader, PieceModelSet.STANDARD),
                            pieceSet(loader, PieceModelSet.CLASSIC),
                            pieceSet(loader, PieceModelSet.PANDORAS_CREATURES)
                    )
            )
    );

    public static void setup() {
        Services.RELOAD_LISTENER.registerResourceLoader((side) -> {
            if (side == LogicalSide.CLIENT)
                return List.of(loader);
            return null;
        });
    }

    protected static Triple<PieceModelSet, PieceType, ObjModel>[] collect(
            Triple<PieceModelSet, PieceType, ObjModel>[]... models
    ) {
        int total = 0;
        for (Triple<PieceModelSet, PieceType, ObjModel>[] model : models) {
            total += model.length;
        }

        Triple<PieceModelSet, PieceType, ObjModel>[] full = new Triple[total];
        int cursor = 0;
        for (Triple<PieceModelSet, PieceType, ObjModel>[] model : models) {
            System.arraycopy(model, 0, full, cursor, model.length);
            cursor += model.length;
        }

        return full;
    }

    protected static Triple<PieceModelSet, PieceType, ObjModel>[] pieceSet(ObjLoader loader, PieceModelSet set) {
        PieceType[] types = PieceType.values();
        Triple<PieceModelSet, PieceType, ObjModel>[] models = new Triple[types.length];
        for (int i = 0; i < types.length; i++) {
            models[i] = Triple.of(
                    set, types[i],
                    loader.models.get(new ResourceLocation(Reference.MODID, set.pathFor(types[i])))
            );
        }
        return models;
    }

    public static CollectiveBufferBuilder.MeshRange getBuffer(PieceModelSet set, PieceType piece) {
        return CHESS_PIECE_MODEL.get(Pair.of(piece, set));
    }

    public ChessPieceModels getChessModels() {
        return CHESS_PIECE_MODEL;
    }
}