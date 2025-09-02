package andrews.table_top_craft.block_entities.render;

import andrews.table_top_craft.animation.system.core.AnimationHandler;
import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.game_logic.chess.PieceColor;
import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.BoardUtils;
import andrews.table_top_craft.game_logic.chess.board.moves.BaseMove;
import andrews.table_top_craft.game_logic.chess.board.tiles.BaseChessTile;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;
import andrews.table_top_craft.game_logic.chess.player.BlackChessPlayer;
import andrews.table_top_craft.game_logic.chess.player.MoveTransition;
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
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Quaternionf;
import org.lwjgl.opengl.GL11;

import static andrews.table_top_craft.block_entities.render.ChessTileEntityRenderer.CHESS_PIECE_SCALE;
import static andrews.table_top_craft.block_entities.render.ChessTileEntityRenderer.CHESS_SCALE;

public class ChessBoardInstancer extends InstancedBlockEntityRenderer<ChessBlockEntity> {
    public ChessBoardInstancer(InstanceFormat format, CollectiveVBO vbo) {
        super(format, vbo);
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
    public void render(Level level, ChessBlockEntity tileEntityIn, BlockPos pos, BatchData batchData) {
//        ghostModel.updateAnimations(tileEntityIn, partialTicks);

        Board board = tileEntityIn.getBoard();
        if (board == null) return;

        BasePiece.PieceModelSet boardSet = BasePiece.PieceModelSet.get(tileEntityIn.getPieceSet() + 1);

        WhiteChessPlayer whiteChessPlayer = (WhiteChessPlayer) board.getWhiteChessPlayer();
        BlackChessPlayer blackChessPlayer = (BlackChessPlayer) board.getBlackChessPlayer();
        boolean isWhiteInCheckmate = tileEntityIn.isWhiteCheckMate();
        boolean isBlackInCheckmate = tileEntityIn.isBlackCheckMate();

        Direction facing = Direction.NORTH;
        if (tileEntityIn.hasLevel()) {
            BlockState blockstate = tileEntityIn.getBlockState();
            if (blockstate.getBlock() instanceof ChessBlock)
                facing = blockstate.getValue(ChessBlock.FACING);
        }

        PoseStack poseStack = new PoseStack();

        poseStack.translate(pos.getX(), pos.getY(), pos.getZ());

        poseStack.pushPose(); // Master Rotation and Position
        poseStack.translate(0.5D, 0.9D, 0.5D);
        poseStack.scale(1.0F, -1.0F, -1.0F);
        switch (facing) {
            default:
            case NORTH:
                poseStack.mulPose(Axis.YN.rotationDegrees(180.0F));
                break;
            case SOUTH:
                break;
            case WEST:
                poseStack.mulPose(Axis.YN.rotationDegrees(270.0F));
                break;
            case EAST:
                poseStack.mulPose(Axis.YN.rotationDegrees(90.0F));
        }

        // Moves the Piece away from the center of the Board, onto the center of a tile
        poseStack.translate(CHESS_SCALE / 2D, 0.0D, CHESS_SCALE / 2D);
        // Moves the Piece to the first Tile on the Board
        poseStack.translate(CHESS_SCALE * 3, 0.0D, CHESS_SCALE * -4);

        poseStack.pushPose(); // General Chess Piece Positioning

        /* get board colors */
        float wR = NBTColorSaving.getRed(tileEntityIn.getWhitePiecesColor()) / 255F;
        float wG = NBTColorSaving.getGreen(tileEntityIn.getWhitePiecesColor()) / 255F;
        float wB = NBTColorSaving.getBlue(tileEntityIn.getWhitePiecesColor()) / 255F;
        float bR = NBTColorSaving.getRed(tileEntityIn.getBlackPiecesColor()) / 255F;
        float bG = NBTColorSaving.getGreen(tileEntityIn.getBlackPiecesColor()) / 255F;
        float bB = NBTColorSaving.getBlue(tileEntityIn.getBlackPiecesColor()) / 255F;

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
                    PieceColor pieceColor = tile.getPiece().getPieceColor();
                    BasePiece.PieceType pieceType = tile.getPiece().getPieceType();

                    poseStack.pushPose(); // X and Z Position on Chess Board
                    // Offsets the Piece that is about to be rendered to the current Tile
                    poseStack.translate(CHESS_SCALE * -column, 0.0D, CHESS_SCALE * rank);

                    poseStack.pushPose(); // Move Piece to Board surface and victory dance
                    // Move the Pieces down to the board surface
                    poseStack.translate(0D, (1 / 16D) * 2.4D, 0D);

                    // We rotate the Piece 180 Degrees if its White and supposed to face the other way
                    if (pieceColor.isWhite())
                        poseStack.mulPose(Axis.YN.rotationDegrees(180F));

                    // The dance the Pieces do when you check mate the enemy
//                    if (isWhiteInCheckmate && pieceColor.isBlack()) {
//                        poseStack.translate(0.0F, (float) Math.abs(Math.sin((Minecraft.getInstance().player.tickCount + partialTicks) / 2.5)) * -0.05F, 0F);
//                        poseStack.mulPose(Axis.ZN.rotationDegrees((float) Math.cos((Minecraft.getInstance().player.tickCount + partialTicks) / 2.5) * 10));
//                    }
//                    if (isBlackInCheckmate && pieceColor.isWhite()) {
//                        poseStack.translate(0.0F, (float) Math.abs(Math.sin((Minecraft.getInstance().player.tickCount + partialTicks) / 2.5)) * -0.05F, 0F);
//                        poseStack.mulPose(Axis.ZN.rotationDegrees((float) Math.cos((Minecraft.getInstance().player.tickCount + partialTicks) / 2.5) * 10));
//                    }

//                    poseStack.translate(CHESS_SCALE * ghostModel.root.x * 0.5F, CHESS_SCALE * ghostModel.root.y * 0.5F, CHESS_SCALE * ghostModel.root.z * 0.5F);
//                    poseStack.translate(CHESS_SCALE, CHESS_SCALE, CHESS_SCALE);
//                    if (AnimationHandler.getElapsedSeconds(tileEntityIn.placedState) > tileEntityIn.placedState.getInTime())
//                        poseStack.mulPose((new Quaternionf()).rotationZYX(ghostModel.root.zRot, ghostModel.root.yRot, ghostModel.root.xRot));
//                    poseStack.scale(ghostModel.root.xScale, ghostModel.root.yScale, ghostModel.root.zScale);

//                    if (currentCoordinate == tileEntityIn.selectedPiecePos) {
//                        poseStack.translate(CHESS_SCALE * ghostModel.selected.x * 0.5F, CHESS_SCALE * ghostModel.selected.y * 0.5F, CHESS_SCALE * ghostModel.selected.z * 0.5F);
//                        poseStack.mulPose((new Quaternionf()).rotationZYX(ghostModel.selected.zRot, ghostModel.selected.yRot, ghostModel.selected.xRot));
//                    }

//                    if (tileEntityIn.moveState != null) {
//                        // Moved chess piece
//                        if (tileEntityIn.currentCord == currentCoordinate) {
//                            poseStack.translate(CHESS_SCALE * ghostModel.moved.x * 0.5F, CHESS_SCALE * ghostModel.moved.y * 0.5F, CHESS_SCALE * ghostModel.moved.z * 0.5F);
//                            poseStack.mulPose((new Quaternionf()).rotationZYX(ghostModel.moved.zRot, ghostModel.moved.yRot, ghostModel.moved.xRot));
//                        }
//                        // Affected chess piece
//                        if (tileEntityIn.destCord == currentCoordinate) {
//                            poseStack.translate(CHESS_SCALE * ghostModel.affected.x * 0.5F, CHESS_SCALE * ghostModel.affected.y * 0.5F, CHESS_SCALE * ghostModel.affected.z * 0.5F);
//                            poseStack.mulPose((new Quaternionf()).rotationZYX(ghostModel.affected.zRot, ghostModel.affected.yRot, ghostModel.affected.xRot));
//                            poseStack.scale(ghostModel.affected.xScale, ghostModel.affected.yScale, ghostModel.affected.zScale);
//                        }
//                        // White Castle Moves
//                        if (tileEntityIn.currentCord == 60)
//                            if (board.getTile(60).getPiece().getPieceType().isKing())
//                                if ((tileEntityIn.destCord == 62 && currentCoordinate == 63) || (tileEntityIn.destCord == 58 && currentCoordinate == 56)) {
//                                    poseStack.translate(CHESS_SCALE * ghostModel.affected.x * 0.5F, CHESS_SCALE * ghostModel.affected.y * 0.5F, CHESS_SCALE * ghostModel.affected.z * 0.5F);
//                                    poseStack.mulPose((new Quaternionf()).rotationZYX(ghostModel.affected.zRot, ghostModel.affected.yRot, ghostModel.affected.xRot));
//                                }
//                        // Black Castle Moves
//                        if (tileEntityIn.currentCord == 4)
//                            if (board.getTile(4).getPiece().getPieceType().isKing())
//                                if ((tileEntityIn.destCord == 6 && currentCoordinate == 7) || (tileEntityIn.destCord == 2 && currentCoordinate == 0)) {
//                                    poseStack.translate(CHESS_SCALE * ghostModel.affected.x * 0.5F, CHESS_SCALE * ghostModel.affected.y * 0.5F, CHESS_SCALE * ghostModel.affected.z * 0.5F);
//                                    poseStack.mulPose((new Quaternionf()).rotationZYX(ghostModel.affected.zRot, ghostModel.affected.yRot, ghostModel.affected.xRot));
//                                }
//
//                        // White En Passant Move
//                        if (board.getTile(tileEntityIn.currentCord).getPiece().getPieceColor().isWhite() && tileEntityIn.currentCord / 8 == 3)
//                            if ((tileEntityIn.currentCord % 8) - (tileEntityIn.destCord % 8) == -1 || (tileEntityIn.currentCord % 8) - (tileEntityIn.destCord % 8) == 1)
//                                if (board.getTile(tileEntityIn.destCord).getPiece() == null)
//                                    if (currentCoordinate == tileEntityIn.destCord + 8) {
//                                        poseStack.translate(CHESS_SCALE * ghostModel.affected.x * 0.5F, CHESS_SCALE * ghostModel.affected.y * 0.5F, CHESS_SCALE * ghostModel.affected.z * 0.5F);
//                                        poseStack.mulPose((new Quaternionf()).rotationZYX(ghostModel.affected.zRot, ghostModel.affected.yRot, ghostModel.affected.xRot));
//                                        poseStack.scale(ghostModel.affected.xScale, ghostModel.affected.yScale, ghostModel.affected.zScale);
//                                    }
//                        // Black En Passant Move
//                        if (board.getTile(tileEntityIn.currentCord).getPiece().getPieceColor().isBlack() && tileEntityIn.currentCord / 8 == 4)
//                            if ((tileEntityIn.currentCord % 8) - (tileEntityIn.destCord % 8) == -1 || (tileEntityIn.currentCord % 8) - (tileEntityIn.destCord % 8) == 1)
//                                if (board.getTile(tileEntityIn.destCord).getPiece() == null)
//                                    if (currentCoordinate == tileEntityIn.destCord - 8) {
//                                        poseStack.translate(CHESS_SCALE * ghostModel.affected.x * 0.5F, CHESS_SCALE * ghostModel.affected.y * 0.5F, CHESS_SCALE * ghostModel.affected.z * 0.5F);
//                                        poseStack.mulPose((new Quaternionf()).rotationZYX(ghostModel.affected.zRot, ghostModel.affected.yRot, ghostModel.affected.xRot));
//                                        poseStack.scale(ghostModel.affected.xScale, ghostModel.affected.yScale, ghostModel.affected.zScale);
//                                    }
//                    }

                    poseStack.scale(CHESS_PIECE_SCALE, -CHESS_PIECE_SCALE, -CHESS_PIECE_SCALE);

                    // Renders The Chess Piece
                    if (isSelectedPiece) {
                        Color colorW = new Color(Math.round(255 * wR), Math.round(255 * wG), Math.round(255 * wB));
                        float brightnessW = (0.2126F * colorW.getRed()) + (0.7152F * colorW.getGreen()) + (0.0722F * colorW.getBlue());
                        Color colorB = new Color(Math.round(255 * bR), Math.round(255 * bG), Math.round(255 * bB));
                        float brightnessB = (0.2126F * colorB.getRed()) + (0.7152F * colorB.getGreen()) + (0.0722F * colorB.getBlue());

//                        if (!ShaderCompatHandler.isShaderActive()) {
//                            poseStack.pushPose();
//                            poseStack.scale(1.001F, 1.001F, 1.001F);
//                            RenderSystem.polygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
//                            float whiteLines = brightnessW * 0.5F / 255F;
//                            float blackLines = brightnessB * 0.5F / 255F;
//                            renderPiece(poseStack, tileEntityIn.getPieceSet(), pieceType, pieceColor, whiteLines, whiteLines, whiteLines, blackLines, blackLines, blackLines);
//                            RenderSystem.polygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
//                            poseStack.popPose();
//                        }

                        colorW = brightnessW > 128 ? colorW.darker(0.8F, 0.0F) : colorW.brighter(0.8F, 0.0F);
                        colorB = brightnessB > 128 ? colorB.darker(0.8F, 0.0F) : colorB.brighter(0.8F, 0.0F);
                        // Depending on the render mode we call the corresponding renderer
                        renderPiece(
                                poseStack, boardSet, pieceType, pieceColor, colorW.getRed() / 255F, colorW.getGreen() / 255F, colorW.getBlue() / 255F, colorB.getRed() / 255F, colorB.getGreen() / 255F, colorB.getBlue() / 255F,
                                batchData
                        );
                    } else {
                        // Depending on the render mode we call the corresponding renderer
                        renderPiece(
                                poseStack, boardSet, pieceType, pieceColor, wR, wG, wB, bR, bG, bB,
                                batchData
                        );
                    }

                    poseStack.popPose(); // # Move Piece to Board surface and victory dance #
                    poseStack.popPose(); // # X and Z Position on Chess Board #
                }
            }
        }

        // Renders the taken pieces in the piece storage bellow the chess plate
        // Moves the pieces down into the taken Pieces area
        poseStack.translate(CHESS_SCALE * -6.5D, 0.58725D, 0.0625D);
//			renderTakenPieces(poseStack, bufferIn, tileEntityIn, combinedLightIn);

        poseStack.popPose(); // # General Chess Piece Positioning #
    }

    protected CollectiveVBO vbo() {
        return DrawScreenHelper.CHESS_PIECE_MODEL.getCollectiveVBO();
    }

    private void renderPiece(
            PoseStack poseStack, BasePiece.PieceModelSet pieceSet,
            BasePiece.PieceType pieceType, PieceColor pieceColor,
            float wR, float wG, float wB, float bR, float bG, float bB,
            BatchData batchData
    ) {
        CollectiveBufferBuilder.MeshRange range = DrawScreenHelper.getBuffer(
                pieceSet, pieceType
        );

        if (range == null) return;

        CollectiveDrawData data = batchData.buildBatch(STANDARD_KEY);

        data.writeMesh(range);
        data.activateData();
        data.ensureInstance();
        data.writeMatrix(poseStack.last().pose());
        if (pieceColor.isWhite()) {
            data.writeFloat(wR, wG, wB, 1);
        } else {
            data.writeFloat(bR, bG, bB, 1);
        }
        data.writeInt(LightTexture.pack(15, 15)); // TODO:

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
