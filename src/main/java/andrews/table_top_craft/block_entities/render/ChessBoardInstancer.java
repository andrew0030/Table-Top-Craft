package andrews.table_top_craft.block_entities.render;

import andrews.table_top_craft.animation.system.core.AnimationHandler;
import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.block_entities.model.chess.GhostModel;
import andrews.table_top_craft.game_logic.chess.PieceColor;
import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.BoardUtils;
import andrews.table_top_craft.game_logic.chess.board.ChessMoveLog;
import andrews.table_top_craft.game_logic.chess.board.moves.BaseMove;
import andrews.table_top_craft.game_logic.chess.board.tiles.BaseChessTile;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;
import andrews.table_top_craft.game_logic.chess.player.BlackChessPlayer;
import andrews.table_top_craft.game_logic.chess.player.WhiteChessPlayer;
import andrews.table_top_craft.objects.blocks.ChessBlock;
import andrews.table_top_craft.util.*;
import andrews.table_top_craft.util.shader_compat.ShaderCompatHandler;
import com.github.andrew0030.pandora_core.client.render.collective.CollectiveBufferBuilder;
import com.github.andrew0030.pandora_core.client.render.collective.CollectiveDrawData;
import com.github.andrew0030.pandora_core.client.render.collective.CollectiveVBO;
import com.github.andrew0030.pandora_core.client.render.instancing.InstanceFormat;
import com.github.andrew0030.pandora_core.client.render.instancing.engine.BatchData;
import com.github.andrew0030.pandora_core.client.render.instancing.engine.BatchKey;
import com.github.andrew0030.pandora_core.client.render.renderers.instancing.InstancedBlockEntityRenderer;
import com.google.common.primitives.Ints;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

import static andrews.table_top_craft.block_entities.render.ChessTileEntityRenderer.*;

public class ChessBoardInstancer extends InstancedBlockEntityRenderer<ChessBlockEntity> {
    private final PoseStack poseStack = new PoseStack();
    private final Matrix4f cpyPose = new Matrix4f();
    private final Matrix3f cpyNorm = new Matrix3f();

    public ChessBoardInstancer(InstanceFormat format, CollectiveVBO vbo) {
        super(format, vbo);
    }

    // TODO: this should not require applying and clearing the shader manually
    private final BatchKey STANDARD_KEY = new BatchKey() {
        public void flush(CollectiveDrawData data) {
//            TTCShaders.CHESS_INSTANCED.apply();
            vbo().setupData(data, TTCShaders.CHESS_INSTANCED);
            data.upload();
            vbo().drawWithShader(
                    RenderSystem.getModelViewMatrix(),
                    RenderSystem.getProjectionMatrix(),
                    RenderSystem.getShader()
            );
//            TTCShaders.CHESS_INSTANCED.clear();
        }
    };

    private final BatchKey LINE_KEY = new BatchKey() {
        public void flush(CollectiveDrawData data) {
//            TTCShaders.CHESS_INSTANCED.apply();
            RenderSystem.polygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
            vbo().setupData(data, TTCShaders.CHESS_INSTANCED);
            data.upload();
            vbo().drawWithShader(
                    RenderSystem.getModelViewMatrix(),
                    RenderSystem.getProjectionMatrix(),
                    RenderSystem.getShader()
            );
            RenderSystem.polygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
//            TTCShaders.CHESS_INSTANCED.clear();
        }
    };

    private void animate(
            PoseStack poseStack, ChessBlockEntity tileEntityIn, PieceColor pieceColor,
            boolean isWhiteInCheckmate, boolean isBlackInCheckmate, float pct,
            GhostModel ghostModel, int currentCoordinate, Board board
    ) {
        // The dance the Pieces do when you check mate the enemy
        if (isWhiteInCheckmate && pieceColor.isBlack()) {
            poseStack.translate(0.0F, (float) Math.abs(Math.sin((Minecraft.getInstance().player.tickCount + pct) / 2.5)) * -0.05F, 0F);
            poseStack.mulPose(Axis.ZN.rotationDegrees((float) Math.cos((Minecraft.getInstance().player.tickCount + pct) / 2.5) * 10));
        }
        if (isBlackInCheckmate && pieceColor.isWhite()) {
            poseStack.translate(0.0F, (float) Math.abs(Math.sin((Minecraft.getInstance().player.tickCount + pct) / 2.5)) * -0.05F, 0F);
            poseStack.mulPose(Axis.ZN.rotationDegrees((float) Math.cos((Minecraft.getInstance().player.tickCount + pct) / 2.5) * 10));
        }

        poseStack.translate(CHESS_SCALE * ghostModel.root.x * 0.5F, CHESS_SCALE * ghostModel.root.y * 0.5F, CHESS_SCALE * ghostModel.root.z * 0.5F);
        if (AnimationHandler.getElapsedSeconds(tileEntityIn.placedState) > tileEntityIn.placedState.getInTime())
            poseStack.mulPose((new Quaternionf()).rotationZYX(ghostModel.root.zRot, ghostModel.root.yRot, ghostModel.root.xRot));
        poseStack.scale(ghostModel.root.xScale, ghostModel.root.yScale, ghostModel.root.zScale);

        if (currentCoordinate == tileEntityIn.selectedPiecePos) {
            poseStack.translate(CHESS_SCALE * ghostModel.selected.x * 0.5F, CHESS_SCALE * ghostModel.selected.y * 0.5F, CHESS_SCALE * ghostModel.selected.z * 0.5F);
            poseStack.mulPose((new Quaternionf()).rotationZYX(ghostModel.selected.zRot, ghostModel.selected.yRot, ghostModel.selected.xRot));
        }

        if (tileEntityIn.moveState != null) {
            // Moved chess piece
            if (tileEntityIn.currentCord == currentCoordinate) {
                poseStack.translate(CHESS_SCALE * ghostModel.moved.x * 0.5F, CHESS_SCALE * ghostModel.moved.y * 0.5F, CHESS_SCALE * ghostModel.moved.z * 0.5F);
                poseStack.mulPose((new Quaternionf()).rotationZYX(ghostModel.moved.zRot, ghostModel.moved.yRot, ghostModel.moved.xRot));
            }
            // Affected chess piece
            if (tileEntityIn.destCord == currentCoordinate) {
                poseStack.translate(CHESS_SCALE * ghostModel.affected.x * 0.5F, CHESS_SCALE * ghostModel.affected.y * 0.5F, CHESS_SCALE * ghostModel.affected.z * 0.5F);
                poseStack.mulPose((new Quaternionf()).rotationZYX(ghostModel.affected.zRot, ghostModel.affected.yRot, ghostModel.affected.xRot));
                poseStack.scale(ghostModel.affected.xScale, ghostModel.affected.yScale, ghostModel.affected.zScale);
            }
            // White Castle Moves
            if (tileEntityIn.currentCord == 60)
                if (board.getTile(60).getPiece().getPieceType().isKing())
                    if ((tileEntityIn.destCord == 62 && currentCoordinate == 63) || (tileEntityIn.destCord == 58 && currentCoordinate == 56)) {
                        poseStack.translate(CHESS_SCALE * ghostModel.affected.x * 0.5F, CHESS_SCALE * ghostModel.affected.y * 0.5F, CHESS_SCALE * ghostModel.affected.z * 0.5F);
                        poseStack.mulPose((new Quaternionf()).rotationZYX(ghostModel.affected.zRot, ghostModel.affected.yRot, ghostModel.affected.xRot));
                    }
            // Black Castle Moves
            if (tileEntityIn.currentCord == 4)
                if (board.getTile(4).getPiece().getPieceType().isKing())
                    if ((tileEntityIn.destCord == 6 && currentCoordinate == 7) || (tileEntityIn.destCord == 2 && currentCoordinate == 0)) {
                        poseStack.translate(CHESS_SCALE * ghostModel.affected.x * 0.5F, CHESS_SCALE * ghostModel.affected.y * 0.5F, CHESS_SCALE * ghostModel.affected.z * 0.5F);
                        poseStack.mulPose((new Quaternionf()).rotationZYX(ghostModel.affected.zRot, ghostModel.affected.yRot, ghostModel.affected.xRot));
                    }

            // White En Passant Move
            if (board.getTile(tileEntityIn.currentCord).getPiece().getPieceColor().isWhite() && tileEntityIn.currentCord / 8 == 3)
                if ((tileEntityIn.currentCord % 8) - (tileEntityIn.destCord % 8) == -1 || (tileEntityIn.currentCord % 8) - (tileEntityIn.destCord % 8) == 1)
                    if (board.getTile(tileEntityIn.destCord).getPiece() == null)
                        if (currentCoordinate == tileEntityIn.destCord + 8) {
                            poseStack.translate(CHESS_SCALE * ghostModel.affected.x * 0.5F, CHESS_SCALE * ghostModel.affected.y * 0.5F, CHESS_SCALE * ghostModel.affected.z * 0.5F);
                            poseStack.mulPose((new Quaternionf()).rotationZYX(ghostModel.affected.zRot, ghostModel.affected.yRot, ghostModel.affected.xRot));
                            poseStack.scale(ghostModel.affected.xScale, ghostModel.affected.yScale, ghostModel.affected.zScale);
                        }
            // Black En Passant Move
            if (board.getTile(tileEntityIn.currentCord).getPiece().getPieceColor().isBlack() && tileEntityIn.currentCord / 8 == 4)
                if ((tileEntityIn.currentCord % 8) - (tileEntityIn.destCord % 8) == -1 || (tileEntityIn.currentCord % 8) - (tileEntityIn.destCord % 8) == 1)
                    if (board.getTile(tileEntityIn.destCord).getPiece() == null)
                        if (currentCoordinate == tileEntityIn.destCord - 8) {
                            poseStack.translate(CHESS_SCALE * ghostModel.affected.x * 0.5F, CHESS_SCALE * ghostModel.affected.y * 0.5F, CHESS_SCALE * ghostModel.affected.z * 0.5F);
                            poseStack.mulPose((new Quaternionf()).rotationZYX(ghostModel.affected.zRot, ghostModel.affected.yRot, ghostModel.affected.xRot));
                            poseStack.scale(ghostModel.affected.xScale, ghostModel.affected.yScale, ghostModel.affected.zScale);
                        }
        }
    }

    @Override
    public void render(Level level, ChessBlockEntity tileEntityIn, BlockPos pos, BatchData batchData) {
        GhostModel ghostModel = getInstance().ghostModel;

        float pct = 0;
        ghostModel.updateAnimations(tileEntityIn, pct);

        Board board = tileEntityIn.getBoard();
        if (board == null) return;

        BasePiece.PieceModelSet boardSet = BasePiece.PieceModelSet.get(tileEntityIn.getPieceSet() + 1);
        CollectiveDrawData batchDataStandard = batchData.buildBatch(STANDARD_KEY);
        CollectiveDrawData batchDataLine = batchData.buildBatch(LINE_KEY);

        int lightmapCoord = LightTexture.pack(
                level.getBrightness(LightLayer.BLOCK, pos),
                level.getBrightness(LightLayer.SKY, pos)
        );

        boolean isWhiteInCheckmate = tileEntityIn.isWhiteCheckMate();
        boolean isBlackInCheckmate = tileEntityIn.isBlackCheckMate();

        Direction facing = Direction.NORTH;
        if (tileEntityIn.hasLevel()) {
            BlockState blockstate = tileEntityIn.getBlockState();
            if (blockstate.getBlock() instanceof ChessBlock)
                facing = blockstate.getValue(ChessBlock.FACING);
        }

        poseStack.translate(pos.getX(), pos.getY(), pos.getZ());
        poseStack.translate(0.5D, 0.9D, 0.5D);
        poseStack.scale(1.0F, -1.0F, -1.0F);
        switch (facing) {
            default:
            case NORTH:
                poseStack.mulPose(YN_180);
                break;
            case SOUTH:
                break;
            case WEST:
                poseStack.mulPose(YN_270);
                break;
            case EAST:
                poseStack.mulPose(YN_90);
        }

        // Moves the Piece away from the center of the Board, onto the center of a tile
        poseStack.translate(CHESS_SCALE / 2D, 0.0D, CHESS_SCALE / 2D);
        // Moves the Piece to the first Tile on the Board
        poseStack.translate(CHESS_SCALE * 3, 0.0D, CHESS_SCALE * -4);
        // Move the Pieces down to the board surface
        poseStack.translate(0D, (1 / 16D) * 2.4D, 0D);

        /* get board colors */
        int white = tileEntityIn.getWhitePiecesColor();
        int black = tileEntityIn.getBlackPiecesColor();
        float wR = NBTColorSaving.getRed(white) / 255F;
        float wG = NBTColorSaving.getGreen(white) / 255F;
        float wB = NBTColorSaving.getBlue(white) / 255F;
        float bR = NBTColorSaving.getRed(black) / 255F;
        float bG = NBTColorSaving.getGreen(black) / 255F;
        float bB = NBTColorSaving.getBlue(black) / 255F;


        // mem efficient push pose
        cpyPose.set(poseStack.last().pose());
        cpyNorm.set(poseStack.last().normal());

        int currentCoordinate = -1;
        /* loop */
        for (int rank = 0; rank < BoardUtils.NUM_TILES_PER_ROW; rank++) {
            for (int column = 0; column < BoardUtils.NUM_TILES_PER_ROW; column++) {
                currentCoordinate++;

                // Sets the piece to selected if it is indeed selected
                BaseChessTile tile = board.getTile(currentCoordinate);
                boolean isSelectedPiece = tile == tileEntityIn.getSourceTile() && tileEntityIn.getHumanMovedPiece() != null;

                // Render all the Pieces
                if (tile.isTileOccupied()) {
                    BasePiece piece = tile.getPiece();
                    PieceColor pieceColor = piece.getPieceColor();
                    BasePiece.PieceType pieceType = piece.getPieceType();

                    // Offsets the Piece that is about to be rendered to the current Tile
                    poseStack.translate(CHESS_SCALE * -column, 0.0D, CHESS_SCALE * rank);

                    // We rotate the Piece 180 Degrees if its White and supposed to face the other way
                    if (pieceColor.isWhite())
                        poseStack.mulPose(YN_180);

                    animate(
                            poseStack, tileEntityIn, pieceColor,
                            isWhiteInCheckmate, isBlackInCheckmate, pct,
                            ghostModel, currentCoordinate, board
                    );

                    poseStack.scale(CHESS_PIECE_SCALE, -CHESS_PIECE_SCALE, -CHESS_PIECE_SCALE);

                    // Renders The Chess Piece
                    if (isSelectedPiece) {
                        // this happens so infrequently that there's no real purpose to precomputation
                        Color colorW = new Color(Math.round(255 * wR), Math.round(255 * wG), Math.round(255 * wB));
                        float brightnessW = (0.2126F * colorW.getRed()) + (0.7152F * colorW.getGreen()) + (0.0722F * colorW.getBlue());
                        Color colorB = new Color(Math.round(255 * bR), Math.round(255 * bG), Math.round(255 * bB));
                        float brightnessB = (0.2126F * colorB.getRed()) + (0.7152F * colorB.getGreen()) + (0.0722F * colorB.getBlue());

                        Color colorAW = brightnessW > 128 ? colorW.darker(0.8F, 0.0F) : colorW.brighter(0.8F, 0.0F);
                        Color colorAB = brightnessB > 128 ? colorB.darker(0.8F, 0.0F) : colorB.brighter(0.8F, 0.0F);
                        // Depending on the render mode we call the corresponding renderer
                        renderPiece(
                                poseStack, boardSet, pieceType, pieceColor,
                                colorAW.getRed() / 255F, colorAW.getGreen() / 255F, colorAW.getBlue() / 255F,
                                colorAB.getRed() / 255F, colorAB.getGreen() / 255F, colorAB.getBlue() / 255F,
                                batchDataStandard, lightmapCoord
                        );

//                        if (!ShaderCompatHandler.isShaderActive()) {
                            poseStack.pushPose();
                            poseStack.scale(1.001F, 1.001F, 1.001F);
                            float whiteLines = brightnessW * 0.5F / 255F;
                            float blackLines = brightnessB * 0.5F / 255F;
                            renderPiece(
                                    poseStack, boardSet, pieceType, pieceColor,
                                    whiteLines, whiteLines, whiteLines,
                                    blackLines, blackLines, blackLines,
                                    batchDataLine, lightmapCoord
                            );
                            poseStack.popPose();
//                        }
                    } else {
                        // Depending on the render mode we call the corresponding renderer
                        renderPiece(
                                poseStack, boardSet, pieceType, pieceColor,
                                wR, wG, wB,
                                bR, bG, bB,
                                batchDataStandard, lightmapCoord
                        );
                    }

                    // mem efficient pop pose
                    poseStack.last().pose().set(cpyPose);
                    poseStack.last().normal().set(cpyNorm);
                }
            }
        }

        // Renders the taken pieces in the piece storage bellow the chess plate
        // Moves the pieces down into the taken Pieces area
        poseStack.translate(0D, -(1 / 16D) * 2.4D, 0D);
        poseStack.translate(CHESS_SCALE * -6.5D, 0.58725D, 0.0625D);
        renderTakenPieces(
                boardSet, poseStack, tileEntityIn, lightmapCoord, batchDataStandard,
                wR, wG, wB, bR, bG, bB
        );

        poseStack.setIdentity();
    }

    private final List<BasePiece> whiteTakenPieces = new ArrayList<>();
    private final List<BasePiece> blackTakenPieces = new ArrayList<>();

    private void renderTakenPieces(
            BasePiece.PieceModelSet boardSet, PoseStack stack, ChessBlockEntity chessBlockEntity, int packedLight, CollectiveDrawData data,
            float wR, float wG, float wB, float bR, float bG, float bB
    ) {
        ChessMoveLog moveLog = chessBlockEntity.getMoveLog();

        for (final BaseMove move : moveLog.getMoves()) {
            if (move.isAttack()) {
                final BasePiece takenPiece = move.getAttackedPiece();

                if (takenPiece.getPieceColor().isWhite()) {
                    whiteTakenPieces.add(takenPiece);
                } else if (takenPiece.getPieceColor().isBlack()) {
                    blackTakenPieces.add(takenPiece);
                } else {
                    throw new RuntimeException("Attempted to get a Piece that had no PieceColor");
                }
            }
        }

        /* GiantLuigi4: hey just so you know, you probably will want to move this sorting out of render code */
        // Sorts all White Taken Pieces depending on their Value
        whiteTakenPieces.sort((piece1, piece2) -> Ints.compare(piece2.getPieceValue(), piece1.getPieceValue()));
        // Sorts all Black Taken Pieces depending on their Value
        blackTakenPieces.sort((piece1, piece2) -> Ints.compare(piece2.getPieceValue(), piece1.getPieceValue()));

        // mem efficient push pose
        cpyPose.set(poseStack.last().pose());
        cpyNorm.set(poseStack.last().normal());
        // draw
        renderTakenPiecesFigures(boardSet, stack, chessBlockEntity, whiteTakenPieces, true, packedLight, data, wR, wG, wB, bR, bG, bB);
        renderTakenPiecesFigures(boardSet, stack, chessBlockEntity, blackTakenPieces, false, packedLight, data, wR, wG, wB, bR, bG, bB);

        // We have to clear the lists, otherwise we end up with the endless army of endlessness
        /* GiantLuigi4: lol */
        whiteTakenPieces.clear();
        blackTakenPieces.clear();
    }

    private void renderTakenPiecesFigures(
            BasePiece.PieceModelSet boardSet, PoseStack stack, ChessBlockEntity chessBlockEntity, final List<BasePiece> pieceList, final boolean isWhite, int packedLight, CollectiveDrawData data,
            float wR, float wG, float wB, float bR, float bG, float bB
    ) {
        int currentCoordinate = -1;
        int currentRank = 0;

        for (final BasePiece piece : pieceList) {
            if (currentCoordinate < 7) {
                currentCoordinate++;
            } else {
                currentCoordinate = 0;
                currentRank += 1;
            }

            // Rotates the Pieces if they are white so they face the player
            if (isWhite)
                stack.mulPose(YN_180);

            if (!isWhite)
                stack.translate((CHESS_SCALE * 0.855D) * 7D, 0.0D, 0.0625D * 12);
            stack.translate((CHESS_SCALE * 0.855D) * -currentCoordinate, 0.0D, CHESS_SCALE * -currentRank);
            stack.scale(CHESS_PIECE_SCALE, -CHESS_PIECE_SCALE, -CHESS_PIECE_SCALE);

            // Depending on the render mode we call the corresponding renderer
            renderPiece(
                    stack, boardSet, piece.getPieceType(), piece.getPieceColor(),
                    wR, wG, wB, bR, bG, bB,
                    data, packedLight
            );
            // mem efficient pop pose
            poseStack.last().pose().set(cpyPose);
            poseStack.last().normal().set(cpyNorm);
        }
    }

    protected CollectiveVBO vbo() {
        return DrawScreenHelper.CHESS_PIECE_MODEL.getCollectiveVBO();
    }

    private void renderPiece(
            PoseStack poseStack, BasePiece.PieceModelSet pieceSet,
            BasePiece.PieceType pieceType, PieceColor pieceColor,
            float wR, float wG, float wB, float bR, float bG, float bB,
            CollectiveDrawData data, int lightmapCoord
    ) {
        CollectiveBufferBuilder.MeshRange range = DrawScreenHelper.getBuffer(
                pieceSet, pieceType
        );

        if (range == null) return;

        data.writeMesh(range);
        data.activateData();
        data.ensureInstance();
        data.writeMatrix(poseStack.last().pose());
        if (pieceColor.isWhite()) {
            data.writeFloat(wR, wG, wB, 1);
        } else {
            data.writeFloat(bR, bG, bB, 1);
        }
        data.writeInt(lightmapCoord);

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
