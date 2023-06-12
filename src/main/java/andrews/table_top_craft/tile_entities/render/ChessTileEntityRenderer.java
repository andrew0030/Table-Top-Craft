package andrews.table_top_craft.tile_entities.render;

import andrews.table_top_craft.animation.system.core.AnimationHandler;
import andrews.table_top_craft.game_logic.chess.PieceColor;
import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.BoardUtils;
import andrews.table_top_craft.game_logic.chess.board.ChessMoveLog;
import andrews.table_top_craft.game_logic.chess.board.moves.BaseMove;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece.PieceType;
import andrews.table_top_craft.game_logic.chess.player.BlackChessPlayer;
import andrews.table_top_craft.game_logic.chess.player.MoveTransition;
import andrews.table_top_craft.game_logic.chess.player.WhiteChessPlayer;
import andrews.table_top_craft.objects.blocks.ChessBlock;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.tile_entities.model.chess.ChessBoardPlateModel;
import andrews.table_top_craft.tile_entities.model.chess.ChessHighlightModel;
import andrews.table_top_craft.tile_entities.model.chess.ChessTilesInfoModel;
import andrews.table_top_craft.tile_entities.model.chess.GhostModel;
import andrews.table_top_craft.util.*;
import andrews.table_top_craft.util.shader_compat.ShaderCompatHandler;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Ints;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ChessTileEntityRenderer implements BlockEntityRenderer<ChessTileEntity>
{
	private static final ResourceLocation HIGHLIGHT_TEXTURE = new ResourceLocation(Reference.MODID, "textures/tile/chess/highlight.png");
	private static final ResourceLocation TILES_INFO_TEXTURE = new ResourceLocation(Reference.MODID, "textures/tile/chess/chess_tiles_info.png");
	private static final ResourceLocation PLATE_WHITE_TILES_TEXTURE = new ResourceLocation(Reference.MODID, "textures/tile/chess/plate_white_tiles.png");
	private static final ResourceLocation PLATE_BLACK_TILES_TEXTURE = new ResourceLocation(Reference.MODID, "textures/tile/chess/plate_black_tiles.png");
	private static final float CHESS_SCALE = 0.125F;
	private final float CHESS_PIECE_SCALE = 0.1F;

	// Dynamic Texture
	private static final NativeImage image = new NativeImage(NativeImage.Format.RGBA, 1, 1, true);
	private static final DynamicTexture texture = new DynamicTexture(image);
	private static ResourceLocation resourceLocation = null;

	// Shader Compat texture
	public static final ResourceLocation SHADER_COMPAT_WHITE = new ResourceLocation(Reference.MODID, "textures/tile/compat/full_white.png");

	// Chess Models
	private final ChessHighlightModel highlightModel;
	private final ChessTilesInfoModel tilesInfoModel;
	private final ChessBoardPlateModel chessBoardPlateModel;
	private final GhostModel ghostModel;

	// Lists
	private final List<Integer> destinationCoordinates = new ArrayList<>();
	private final List<BasePiece> whiteTakenPieces = new ArrayList<>();
	private final List<BasePiece> blackTakenPieces = new ArrayList<>();

	int cachedIdx;

	static
	{
		image.setPixelRGBA(0, 0, 16777215);
		texture.upload();
		resourceLocation = Minecraft.getInstance().getTextureManager().register("table_top_craft_dummy", texture);
	}

	public ChessTileEntityRenderer(BlockEntityRendererProvider.Context context)
	{
		highlightModel = new ChessHighlightModel(context.bakeLayer(ChessHighlightModel.CHESS_HIGHLIGHT_LAYER));
		tilesInfoModel = new ChessTilesInfoModel(context.bakeLayer(ChessTilesInfoModel.CHESS_TILES_INFO_LAYER));
		chessBoardPlateModel = new ChessBoardPlateModel(context.bakeLayer(ChessBoardPlateModel.CHESS_BOARD_PLATE_LAYER));
		ghostModel = new GhostModel(context.bakeLayer(GhostModel.LAYER));
	}

	@Override
	public void render(ChessTileEntity tileEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		ghostModel.updateAnimations(tileEntityIn, partialTicks);
		Board board;
		Direction facing = Direction.NORTH;
		if(tileEntityIn.hasLevel())
		{
			BlockState blockstate = tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos());
			if(blockstate.getBlock() instanceof ChessBlock)
				facing = blockstate.getValue(ChessBlock.FACING);
		}
		int lightU = LightTexture.block(combinedLightIn);
		int lightV = LightTexture.sky(combinedLightIn);

		poseStack.pushPose(); // Chess Plate and Info
		poseStack.translate(0.5D, 0.9D, 0.5D);
		poseStack.scale(1.0F, -1.0F, -1.0F);
		switch(facing)
		{
			default:
			case NORTH:
				poseStack.mulPose(Vector3f.YN.rotationDegrees(180.0F));
				break;
			case SOUTH:
				break;
			case WEST:
				poseStack.mulPose(Vector3f.YN.rotationDegrees(270.0F));
				break;
			case EAST:
				poseStack.mulPose(Vector3f.YN.rotationDegrees(90.0F));
		}

		// Renders the Custom Plate if needed
		if(tileEntityIn.getUseCustomPlate())
			renderChessBoardPlate(poseStack, bufferIn, combinedLightIn, combinedOverlayIn, tileEntityIn);
		// Renders the Numbers and Letters around the Chess Block
		if(tileEntityIn.getShouldShowTileInfo())
			renderTilesInfo(poseStack, bufferIn, combinedLightIn, combinedOverlayIn, tileEntityIn);
		poseStack.popPose(); // # Chess Plate and Info #

		if(tileEntityIn.getBoard() != null)
		{
			board = tileEntityIn.getBoard();
			WhiteChessPlayer whiteChessPlayer = (WhiteChessPlayer) board.getWhiteChessPlayer();
			BlackChessPlayer blackChessPlayer = (BlackChessPlayer) board.getBlackChessPlayer();
			boolean isWhiteInCheckmate = tileEntityIn.isWhiteCheckMate();
			boolean isBlackInCheckmate = tileEntityIn.isBlackCheckMate();

			poseStack.pushPose(); // Master Rotation and Position
			poseStack.translate(0.5D, 0.9D, 0.5D);
			poseStack.scale(1.0F, -1.0F, -1.0F);
			switch (facing)
			{
				default:
				case NORTH:
					poseStack.mulPose(Vector3f.YN.rotationDegrees(180.0F));
					break;
				case SOUTH:
					break;
				case WEST:
					poseStack.mulPose(Vector3f.YN.rotationDegrees(270.0F));
					break;
				case EAST:
					poseStack.mulPose(Vector3f.YN.rotationDegrees(90.0F));
			}

			// Moves the Piece away from the center of the Board, onto the center of a tile
			poseStack.translate(CHESS_SCALE / 2D, 0.0D, CHESS_SCALE / 2D);
			// Moves the Piece to the first Tile on the Board
			poseStack.translate(CHESS_SCALE * 3, 0.0D, CHESS_SCALE * -4);

			int currentCoordinate = -1;
			// This loop renders all the Chess Pieces on the Chess board
			/* setup render state */
			poseStack.pushPose(); // General Chess Piece Positioning

			/* get board colors */
			float wR = NBTColorSaving.getRed(tileEntityIn.getWhitePiecesColor()) / 255F;
			float wG = NBTColorSaving.getGreen(tileEntityIn.getWhitePiecesColor()) / 255F;
			float wB = NBTColorSaving.getBlue(tileEntityIn.getWhitePiecesColor()) / 255F;
			float bR = NBTColorSaving.getRed(tileEntityIn.getBlackPiecesColor()) / 255F;
			float bG = NBTColorSaving.getGreen(tileEntityIn.getBlackPiecesColor()) / 255F;
			float bB = NBTColorSaving.getBlue(tileEntityIn.getBlackPiecesColor()) / 255F;

			VertexConsumer consumer = bufferIn.getBuffer(RenderType.entitySolid(SHADER_COMPAT_WHITE));
			BasePiece.PieceModelSet set = BasePiece.PieceModelSet.get(tileEntityIn.getPieceSet() + 1);

			/* setup render state */
			RenderType type = TTCRenderTypes.getChessPieceSolid(resourceLocation);
			type.setupRenderState();
			ShaderInstance shaderinstance = RenderSystem.getShader();
			if (!ShaderCompatHandler.isShaderActive()) {
				if (shaderinstance.PROJECTION_MATRIX != null)
					shaderinstance.PROJECTION_MATRIX.set(RenderSystem.getProjectionMatrix());
				BufferHelpers.setupRender(RenderSystem.getShader(), lightU, lightV);
				shaderinstance.apply();
			}

			/* loop */
			for (int rank = 0; rank < BoardUtils.NUM_TILES_PER_ROW; rank++)
			{
				for (int column = 0; column < BoardUtils.NUM_TILES_PER_ROW; column++)
				{
					currentCoordinate++;

					// Sets the piece to selected if it is indeed selected
					boolean isSelectedPiece = board.getTile(currentCoordinate) == tileEntityIn.getSourceTile() && tileEntityIn.getHumanMovedPiece() != null;

					// Render all the Pieces
					if (board.getTile(currentCoordinate).isTileOccupied())
					{
						PieceColor pieceColor = board.getTile(currentCoordinate).getPiece().getPieceColor();
						PieceType pieceType = board.getTile(currentCoordinate).getPiece().getPieceType();

						poseStack.pushPose(); // X and Z Position on Chess Board
						// Offsets the Piece that is about to be rendered to the current Tile
						poseStack.translate(CHESS_SCALE * -column, 0.0D, CHESS_SCALE * rank);

						poseStack.pushPose(); // Move Piece to Board surface and victory dance
						// Move the Pieces down to the board surface
						poseStack.translate(0D, (1 / 16D) * 2.4D, 0D);

						// We rotate the Piece 180 Degrees if its White and supposed to face the other way
						if (pieceColor.isWhite())
							poseStack.mulPose(Vector3f.YN.rotationDegrees(180F));

						// The dance the Pieces do when you check mate the enemy
						if (isWhiteInCheckmate && pieceColor.isBlack())
						{
							poseStack.translate(0.0F, (float) Math.abs(Math.sin((Minecraft.getInstance().player.tickCount + partialTicks) / 2.5)) * -0.05F, 0F);
							poseStack.mulPose(Vector3f.ZN.rotationDegrees((float) Math.cos((Minecraft.getInstance().player.tickCount + partialTicks) / 2.5) * 10));
						}
						if (isBlackInCheckmate && pieceColor.isWhite())
						{
							poseStack.translate(0.0F, (float) Math.abs(Math.sin((Minecraft.getInstance().player.tickCount + partialTicks) / 2.5)) * -0.05F, 0F);
							poseStack.mulPose(Vector3f.ZN.rotationDegrees((float) Math.cos((Minecraft.getInstance().player.tickCount + partialTicks) / 2.5) * 10));
						}

						poseStack.translate(CHESS_SCALE * ghostModel.root.x * 0.5F, CHESS_SCALE * ghostModel.root.y * 0.5F, CHESS_SCALE * ghostModel.root.z * 0.5F);
						if (AnimationHandler.getElapsedSeconds(tileEntityIn.placedState) > tileEntityIn.placedState.getInTime())
						{
							poseStack.mulPose(Vector3f.ZP.rotation(ghostModel.root.zRot));
							poseStack.mulPose(Vector3f.YP.rotation(ghostModel.root.yRot));
							poseStack.mulPose(Vector3f.XP.rotation(ghostModel.root.xRot));
						}
						poseStack.scale(ghostModel.root.xScale, ghostModel.root.yScale, ghostModel.root.zScale);

						if (currentCoordinate == tileEntityIn.selectedPiecePos)
						{
							poseStack.translate(CHESS_SCALE * ghostModel.selected.x * 0.5F, CHESS_SCALE * ghostModel.selected.y * 0.5F, CHESS_SCALE * ghostModel.selected.z * 0.5F);
							poseStack.mulPose(Vector3f.ZP.rotation(ghostModel.selected.zRot));
							poseStack.mulPose(Vector3f.YP.rotation(ghostModel.selected.yRot));
							poseStack.mulPose(Vector3f.XP.rotation(ghostModel.selected.xRot));
						}

						if (tileEntityIn.moveState != null)
						{
							// Moved chess piece
							if (tileEntityIn.currentCord == currentCoordinate)
							{
								poseStack.translate(CHESS_SCALE * ghostModel.moved.x * 0.5F, CHESS_SCALE * ghostModel.moved.y * 0.5F, CHESS_SCALE * ghostModel.moved.z * 0.5F);
								poseStack.mulPose(Vector3f.ZP.rotation(ghostModel.moved.zRot));
								poseStack.mulPose(Vector3f.YP.rotation(ghostModel.moved.yRot));
								poseStack.mulPose(Vector3f.XP.rotation(ghostModel.moved.xRot));
							}
							// Affected chess piece
							if (tileEntityIn.destCord == currentCoordinate)
							{
								poseStack.translate(CHESS_SCALE * ghostModel.affected.x * 0.5F, CHESS_SCALE * ghostModel.affected.y * 0.5F, CHESS_SCALE * ghostModel.affected.z * 0.5F);
								poseStack.mulPose(Vector3f.ZP.rotation(ghostModel.affected.zRot));
								poseStack.mulPose(Vector3f.YP.rotation(ghostModel.affected.yRot));
								poseStack.mulPose(Vector3f.XP.rotation(ghostModel.affected.xRot));
								poseStack.scale(ghostModel.affected.xScale, ghostModel.affected.yScale, ghostModel.affected.zScale);
							}
							// White Castle Moves
							if (tileEntityIn.currentCord == 60)
								if (board.getTile(60).getPiece().getPieceType().isKing())
									if ((tileEntityIn.destCord == 62 && currentCoordinate == 63) || (tileEntityIn.destCord == 58 && currentCoordinate == 56))
									{
										poseStack.translate(CHESS_SCALE * ghostModel.affected.x * 0.5F, CHESS_SCALE * ghostModel.affected.y * 0.5F, CHESS_SCALE * ghostModel.affected.z * 0.5F);
										poseStack.mulPose(Vector3f.ZP.rotation(ghostModel.affected.zRot));
										poseStack.mulPose(Vector3f.YP.rotation(ghostModel.affected.yRot));
										poseStack.mulPose(Vector3f.XP.rotation(ghostModel.affected.xRot));
									}
							// Black Castle Moves
							if (tileEntityIn.currentCord == 4)
								if (board.getTile(4).getPiece().getPieceType().isKing())
									if ((tileEntityIn.destCord == 6 && currentCoordinate == 7) || (tileEntityIn.destCord == 2 && currentCoordinate == 0))
									{
										poseStack.translate(CHESS_SCALE * ghostModel.affected.x * 0.5F, CHESS_SCALE * ghostModel.affected.y * 0.5F, CHESS_SCALE * ghostModel.affected.z * 0.5F);
										poseStack.mulPose(Vector3f.ZP.rotation(ghostModel.affected.zRot));
										poseStack.mulPose(Vector3f.YP.rotation(ghostModel.affected.yRot));
										poseStack.mulPose(Vector3f.XP.rotation(ghostModel.affected.xRot));
									}

							// White En Passant Move
							if (board.getTile(tileEntityIn.currentCord).getPiece().getPieceColor().isWhite() && tileEntityIn.currentCord / 8 == 3)
								if ((tileEntityIn.currentCord % 8) - (tileEntityIn.destCord % 8) == -1 || (tileEntityIn.currentCord % 8) - (tileEntityIn.destCord % 8) == 1)
									if (board.getTile(tileEntityIn.destCord).getPiece() == null)
										if (currentCoordinate == tileEntityIn.destCord + 8)
										{
											poseStack.translate(CHESS_SCALE * ghostModel.affected.x * 0.5F, CHESS_SCALE * ghostModel.affected.y * 0.5F, CHESS_SCALE * ghostModel.affected.z * 0.5F);
											poseStack.mulPose(Vector3f.ZP.rotation(ghostModel.affected.zRot));
											poseStack.mulPose(Vector3f.YP.rotation(ghostModel.affected.yRot));
											poseStack.mulPose(Vector3f.XP.rotation(ghostModel.affected.xRot));
											poseStack.scale(ghostModel.affected.xScale, ghostModel.affected.yScale, ghostModel.affected.zScale);
										}
							// Black En Passant Move
							if (board.getTile(tileEntityIn.currentCord).getPiece().getPieceColor().isBlack() && tileEntityIn.currentCord / 8 == 4)
								if ((tileEntityIn.currentCord % 8) - (tileEntityIn.destCord % 8) == -1 || (tileEntityIn.currentCord % 8) - (tileEntityIn.destCord % 8) == 1)
									if (board.getTile(tileEntityIn.destCord).getPiece() == null)
										if (currentCoordinate == tileEntityIn.destCord - 8)
										{
											poseStack.translate(CHESS_SCALE * ghostModel.affected.x * 0.5F, CHESS_SCALE * ghostModel.affected.y * 0.5F, CHESS_SCALE * ghostModel.affected.z * 0.5F);
											poseStack.mulPose(Vector3f.ZP.rotation(ghostModel.affected.zRot));
											poseStack.mulPose(Vector3f.YP.rotation(ghostModel.affected.yRot));
											poseStack.mulPose(Vector3f.XP.rotation(ghostModel.affected.xRot));
											poseStack.scale(ghostModel.affected.xScale, ghostModel.affected.yScale, ghostModel.affected.zScale);
										}
						}

						// Renders The Chess Piece
						if (isSelectedPiece)
						{
							Color colorW = new Color(Math.round(255 * wR), Math.round(255 * wG), Math.round(255 * wB));
							float brightnessW = (0.2126F * colorW.getRed()) + (0.7152F * colorW.getGreen()) + (0.0722F * colorW.getBlue());
							Color colorB = new Color(Math.round(255 * bR), Math.round(255 * bG), Math.round(255 * bB));
							float brightnessB = (0.2126F * colorB.getRed()) + (0.7152F * colorB.getGreen()) + (0.0722F * colorB.getBlue());

							if (!ShaderCompatHandler.isShaderActive()) {
								poseStack.pushPose();
								poseStack.scale(1.001F, 1.001F, 1.001F);
								RenderSystem.polygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
								float whiteLines = brightnessW * 0.5F / 255F;
								float blackLines = brightnessB * 0.5F / 255F;
								renderPiece(poseStack, tileEntityIn.getPieceSet(), pieceType, pieceColor, whiteLines, whiteLines, whiteLines, blackLines, blackLines, blackLines);
								RenderSystem.polygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
								poseStack.popPose();
							}

							colorW = brightnessW > 128 ? colorW.darker(0.8F, 0.0F) : colorW.brighter(0.8F, 0.0F);
							colorB = brightnessB > 128 ? colorB.darker(0.8F, 0.0F) : colorB.brighter(0.8F, 0.0F);
							// Depending on the render mode we call the corresponding renderer
							if (ShaderCompatHandler.isShaderActive()) {
								DrawScreenHelper.CHESS_PIECE_MODEL.render(poseStack, consumer, pieceType, set, pieceColor.isWhite() ? colorW.getRed() / 255F : colorB.getRed() / 255F, pieceColor.isWhite() ? colorW.getGreen() / 255F : colorB.getGreen() / 255F, pieceColor.isWhite() ? colorW.getBlue() / 255F : colorB.getBlue() / 255F, combinedLightIn);
							} else {
								renderPiece(poseStack, tileEntityIn.getPieceSet(), pieceType, pieceColor, colorW.getRed() / 255F, colorW.getGreen() / 255F, colorW.getBlue() / 255F, colorB.getRed() / 255F, colorB.getGreen() / 255F, colorB.getBlue() / 255F);
							}
						}
						else
						{
							// Depending on the render mode we call the corresponding renderer
							if (ShaderCompatHandler.isShaderActive()) {
								DrawScreenHelper.CHESS_PIECE_MODEL.render(poseStack, consumer, pieceType, set, pieceColor.isWhite() ? wR : bR, pieceColor.isWhite() ? wG : bG, pieceColor.isWhite() ? wB : bB, combinedLightIn);
							} else {
								renderPiece(poseStack, tileEntityIn.getPieceSet(), pieceType, pieceColor, wR, wG, wB, bR, bG, bB);
							}
						}

						poseStack.popPose(); // # Move Piece to Board surface and victory dance #
						poseStack.popPose(); // # X and Z Position on Chess Board #
					}
				}
			}

			// Renders the taken pieces in the piece storage bellow the chess plate
			// Moves the pieces down into the taken Pieces area
			poseStack.translate(CHESS_SCALE * -6.5D, 0.58725D, 0.0625D);
			renderTakenPieces(poseStack, bufferIn, tileEntityIn, combinedLightIn);
			// clear render state
			if (!ShaderCompatHandler.isShaderActive()) {
				VertexBuffer.unbind();
				shaderinstance.clear();
				type.clearRenderState();
			}
			poseStack.popPose(); // # General Chess Piece Positioning #

			// We set the currentCoordinate back to -1 in order to reuse it instead of making a new variable
			currentCoordinate = -1;
			// This loop renders all Tile Information related stuff (Available Moves and Previous Moves)
			// We do this so all pieces are rendered in order to avoid changing the GL State so much
			for (int rank = 0; rank < BoardUtils.NUM_TILES_PER_ROW; rank++)
			{
				for (int column = 0; column < BoardUtils.NUM_TILES_PER_ROW; column++)
				{
					currentCoordinate++;
					poseStack.pushPose(); // Tile Info Positioning
					// Offsets the Tile Info that is about to be rendered to the current Tile
					poseStack.translate(CHESS_SCALE * -column, 0.0D, CHESS_SCALE * rank);

					// Render the Previous Move Info Tiles
					if (tileEntityIn.getShowPreviousMove())
					{
						if (tileEntityIn.getMoveLog().getMoves().size() > 0)
						{
							BaseMove lastMove = tileEntityIn.getMoveLog().getMoves().get(tileEntityIn.getMoveLog().getMoves().size() - 1);
							for (BaseMove move : pieceLegalMoves(tileEntityIn))
								destinationCoordinates.add(move.getDestinationCoordinate());
							if (lastMove.getCurrentCoordinate() == currentCoordinate && (!destinationCoordinates.contains(lastMove.getCurrentCoordinate()) || !tileEntityIn.getShowAvailableMoves()))
								renderHighlight(poseStack, bufferIn, combinedLightIn, combinedOverlayIn, HighlightType.LAST_MADE_MOVE, tileEntityIn);
							if (lastMove.getDestinationCoordinate() == currentCoordinate && (!destinationCoordinates.contains(lastMove.getDestinationCoordinate()) || !tileEntityIn.getShowAvailableMoves()))
								renderHighlight(poseStack, bufferIn, combinedLightIn, combinedOverlayIn, HighlightType.LAST_MADE_MOVE, tileEntityIn);
							// We clear the list after rendering its contents
							destinationCoordinates.clear();
						}
					}
					// Handles caching
					if(tileEntityIn.getHumanMovedPiece() != null && tileEntityIn.getHumanMovedPiece().getPieceColor() == tileEntityIn.getBoard().getCurrentChessPlayer().getPieceColor())
					{
						// If the move transitions havent been cached, we cache them
						if(tileEntityIn.getCachedPiece() == null || !tileEntityIn.getCachedPiece().equals(tileEntityIn.getHumanMovedPiece()))
						{
							tileEntityIn.clearMoveTransitionsCache();
							for (BaseMove move : pieceLegalMoves(tileEntityIn))
							{
								MoveTransition transition = board.getCurrentChessPlayer().makeMove(move);
								// We add the move transition to the cache
								tileEntityIn.addToMoveTransitionsCache(transition);
							}
							// After we are done caching the transitions we update the currently cached piece
							tileEntityIn.setCachedPiece(tileEntityIn.getHumanMovedPiece());
						}
					}
					// Render all the Available Moves Tiles
					if (tileEntityIn.getShowAvailableMoves())
					{
						for (int i = 0; i < pieceLegalMoves(tileEntityIn).size(); i++)
						{
							if(!tileEntityIn.getMoveTransitionsCache().isEmpty())
							{
								MoveTransition transition = tileEntityIn.getMoveTransitionsCache().get(i);
								BaseMove move = transition.getMove();

								if (move.getDestinationCoordinate() == currentCoordinate)
								{
									// We check if the Move is a Castling Move and if it is we render the Castling Highlight
									if (move.isCastlingMove())
									{
										renderHighlight(poseStack, bufferIn, combinedLightIn, combinedOverlayIn, HighlightType.CASTLE_MOVE, tileEntityIn);
									}
									else
									{
										// If it wasn't a Castling Move we check the other cases we want to cover
										switch (transition.getMoveStatus())
										{
											case DONE -> renderHighlight(poseStack, bufferIn, combinedLightIn, combinedOverlayIn, move.isAttack() ? HighlightType.ATTACK_MOVE : HighlightType.LEGAL_MOVE, tileEntityIn);
											case LEAVES_PLAYER_IN_CHECK -> renderHighlight(poseStack, bufferIn, combinedLightIn, combinedOverlayIn, HighlightType.LEAVES_PLAYER_IN_CHECK, tileEntityIn);
										}
									}
								}
							}
						}
					}
					poseStack.popPose(); // # Tile Info Positioning #
				}
			}
			poseStack.popPose(); // # Master Rotation and Position #
		}
	}

	private void renderTakenPieces(PoseStack stack, MultiBufferSource buffer, ChessTileEntity chessTileEntity, int packedLight)
	{
		ChessMoveLog moveLog = chessTileEntity.getMoveLog();

		for(final BaseMove move : moveLog.getMoves())
		{
			if(move.isAttack())
			{
				final BasePiece takenPiece = move.getAttackedPiece();

				if(takenPiece.getPieceColor().isWhite())
				{
					whiteTakenPieces.add(takenPiece);
				}
				else if(takenPiece.getPieceColor().isBlack())
				{
					blackTakenPieces.add(takenPiece);
				}
				else
				{
					throw new RuntimeException("Attempted to get a Piece that had no PieceColor");
				}
			}
		}

		/* GiantLuigi4: hey just so you know, you probably will want to move this sorting out of render code */
		// Sorts all White Taken Pieces depending on their Value
		whiteTakenPieces.sort((piece1, piece2) -> Ints.compare(piece2.getPieceValue(), piece1.getPieceValue()));
		// Sorts all Black Taken Pieces depending on their Value
		blackTakenPieces.sort((piece1, piece2) -> Ints.compare(piece2.getPieceValue(), piece1.getPieceValue()));

		// draw
		renderTakenPiecesFigures(stack, buffer, chessTileEntity, whiteTakenPieces, true, packedLight);
		renderTakenPiecesFigures(stack, buffer, chessTileEntity, blackTakenPieces, false, packedLight);

		// We have to clear the lists, otherwise we end up with the endless army of endlessness
		/* GiantLuigi4: lol */
		whiteTakenPieces.clear();
		blackTakenPieces.clear();
	}

	private void renderTakenPiecesFigures(PoseStack stack, MultiBufferSource buffer, ChessTileEntity chessTileEntity, final List<BasePiece> pieceList, final boolean isWhite, int packedLight)
	{
		int currentCoordinate = -1;
		int currentRank = 0;
		/* GiantLuigi4: I decided to move this color lookup out of the loop */
		/* reason: reduce redundant lookups, very minimal impact on performance, but it's smth */
		float wR = NBTColorSaving.getRed(chessTileEntity.getWhitePiecesColor()) / 255F;
		float wG = NBTColorSaving.getGreen(chessTileEntity.getWhitePiecesColor()) / 255F;
		float wB = NBTColorSaving.getBlue(chessTileEntity.getWhitePiecesColor()) / 255F;
		float bR = NBTColorSaving.getRed(chessTileEntity.getBlackPiecesColor()) / 255F;
		float bG = NBTColorSaving.getGreen(chessTileEntity.getBlackPiecesColor()) / 255F;
		float bB = NBTColorSaving.getBlue(chessTileEntity.getBlackPiecesColor()) / 255F;

		VertexConsumer consumer = buffer.getBuffer(RenderType.entitySolid(SHADER_COMPAT_WHITE));
		BasePiece.PieceModelSet set = BasePiece.PieceModelSet.get(chessTileEntity.getPieceSet() + 1);

		for(final BasePiece piece : pieceList)
		{
			if(currentCoordinate < 7)
			{
				currentCoordinate++;
			}
			else
			{
				currentCoordinate = 0;
				currentRank += 1;
			}

			stack.pushPose();
			// Rotates the Pieces if they are white so they face the player
			if(isWhite)
				stack.mulPose(Vector3f.YN.rotationDegrees(180F));

			if(!isWhite)
				stack.translate((CHESS_SCALE * 0.855D) * 7D, 0.0D, 0.0625D * 12);
			stack.translate((CHESS_SCALE * 0.855D) * -currentCoordinate, 0.0D, CHESS_SCALE * -currentRank);
			// Depending on the render mode we call the corresponding renderer
			if (ShaderCompatHandler.isShaderActive()) {
				DrawScreenHelper.CHESS_PIECE_MODEL.render(stack, consumer, piece.getPieceType(), set, isWhite ? wR : bR, isWhite ? wG : bG, isWhite ? wB : bB, packedLight);
			} else {
				renderPiece(stack, chessTileEntity.getPieceSet(), piece.getPieceType(), piece.getPieceColor(), wR, wG, wB, bR, bG, bB);
			}
			stack.popPose();
		}
	}

	private void renderPiece(PoseStack poseStack, int pieceModelSet, PieceType pieceType, PieceColor pieceColor, float wR, float wG, float wB, float bR, float bG, float bB)
	{
		// The RenderType for the chess pieces (the texture is just a dummy texture)
		ShaderInstance shaderinstance = RenderSystem.getShader();
		BufferHelpers.updateColor(shaderinstance, new float[]{pieceColor.isWhite() ? wR : bR, pieceColor.isWhite() ? wG : bG, pieceColor.isWhite() ? wB : bB, 1f});
		poseStack.pushPose();
		if (shaderinstance.MODEL_VIEW_MATRIX != null) shaderinstance.MODEL_VIEW_MATRIX.set(poseStack.last().pose());

		BasePiece.PieceModelSet set = BasePiece.PieceModelSet.get(pieceModelSet + 1);
		VertexBuffer pawnBuffer = DrawScreenHelper.getBuffer(set, pieceType);
		BufferHelpers.draw(pawnBuffer);

		poseStack.popPose();
		// We reset the shader color to avoid funny business during the next render call
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
	}

	/**
	 * @param chessTileEntity - The ChessTileEntity
	 * @return - A Collection of legal Moves this Piece can perform
	 */
	private Collection<BaseMove> pieceLegalMoves(ChessTileEntity chessTileEntity)
	{
		if(chessTileEntity.getHumanMovedPiece() != null && chessTileEntity.getHumanMovedPiece().getPieceColor() == chessTileEntity.getBoard().getCurrentChessPlayer().getPieceColor())
		{
			final List<BaseMove> pieceMoves = new ArrayList<>();
			for(BaseMove move : chessTileEntity.getBoard().getCurrentChessPlayer().getLegalMoves())
			{
				if(move.getMovedPiece() == chessTileEntity.getHumanMovedPiece())
				{
					pieceMoves.add(move);
				}
			}
			return ImmutableList.copyOf(pieceMoves);
		}
		return Collections.emptyList();
	}

	private void renderHighlight(PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn, HighlightType highlightType, ChessTileEntity chessTileEntity)
	{
		VertexConsumer builderHighlight = bufferIn.getBuffer(TTCRenderTypes.getEmissiveTransluscent(HIGHLIGHT_TEXTURE, false));
		poseStack.pushPose();
		poseStack.translate(0, -1.345F, 0);
		poseStack.scale(CHESS_PIECE_SCALE * 10, CHESS_PIECE_SCALE * 10, CHESS_PIECE_SCALE * 10);
		switch(highlightType)
		{
			default:
			case LEGAL_MOVE:
				float R = NBTColorSaving.getRed(chessTileEntity.getLegalMoveColor()) / 255F;
				float G = NBTColorSaving.getGreen(chessTileEntity.getLegalMoveColor()) / 255F;
				float B = NBTColorSaving.getBlue(chessTileEntity.getLegalMoveColor()) / 255F;
				float A = NBTColorSaving.getAlpha(chessTileEntity.getLegalMoveColor()) / 255F;
				highlightModel.renderToBuffer(poseStack, builderHighlight, combinedLightIn, combinedOverlayIn, R, G, B, A);
				break;
			case LEAVES_PLAYER_IN_CHECK:
				float R1 = NBTColorSaving.getRed(chessTileEntity.getInvalidMoveColor()) / 255F;
				float G1 = NBTColorSaving.getGreen(chessTileEntity.getInvalidMoveColor()) / 255F;
				float B1 = NBTColorSaving.getBlue(chessTileEntity.getInvalidMoveColor()) / 255F;
				float A1 = NBTColorSaving.getAlpha(chessTileEntity.getInvalidMoveColor()) / 255F;
				highlightModel.renderToBuffer(poseStack, builderHighlight, combinedLightIn, combinedOverlayIn, R1, G1, B1, A1);
				break;
			case CASTLE_MOVE:
				float R2 = NBTColorSaving.getRed(chessTileEntity.getCastleMoveColor()) / 255F;
				float G2 = NBTColorSaving.getGreen(chessTileEntity.getCastleMoveColor()) / 255F;
				float B2 = NBTColorSaving.getBlue(chessTileEntity.getCastleMoveColor()) / 255F;
				float A2 = NBTColorSaving.getAlpha(chessTileEntity.getCastleMoveColor()) / 255F;
				highlightModel.renderToBuffer(poseStack, builderHighlight, combinedLightIn, combinedOverlayIn, R2, G2, B2, A2);
				break;
			case ATTACK_MOVE:
				float R3 = NBTColorSaving.getRed(chessTileEntity.getAttackMoveColor()) / 255F;
				float G3 = NBTColorSaving.getGreen(chessTileEntity.getAttackMoveColor()) / 255F;
				float B3 = NBTColorSaving.getBlue(chessTileEntity.getAttackMoveColor()) / 255F;
				float A3 = NBTColorSaving.getAlpha(chessTileEntity.getAttackMoveColor()) / 255F;
				highlightModel.renderToBuffer(poseStack, builderHighlight, combinedLightIn, combinedOverlayIn, R3, G3, B3, A3);
				break;
			case LAST_MADE_MOVE:
				float R4 = NBTColorSaving.getRed(chessTileEntity.getPreviousMoveColor()) / 255F;
				float G4 = NBTColorSaving.getGreen(chessTileEntity.getPreviousMoveColor()) / 255F;
				float B4 = NBTColorSaving.getBlue(chessTileEntity.getPreviousMoveColor()) / 255F;
				float A4 = NBTColorSaving.getAlpha(chessTileEntity.getPreviousMoveColor()) / 255F;
				highlightModel.renderToBuffer(poseStack, builderHighlight, combinedLightIn, combinedOverlayIn, R4, G4, B4, A4);
		}
		poseStack.popPose();
	}

	private void renderTilesInfo(PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn, ChessTileEntity chessTileEntity)
	{
		VertexConsumer builderTilesInfo = bufferIn.getBuffer(TTCRenderTypes.getEmissiveTransluscent(TILES_INFO_TEXTURE, false));

		float red = NBTColorSaving.getRed(chessTileEntity.getTileInfoColor()) / 255F;
		float green = NBTColorSaving.getGreen(chessTileEntity.getTileInfoColor()) / 255F;
		float blue = NBTColorSaving.getBlue(chessTileEntity.getTileInfoColor()) / 255F;

		poseStack.pushPose();
		poseStack.mulPose(Vector3f.YN.rotationDegrees(180.0F));
		poseStack.translate(0.0F, -1.32F, 0.0F);
		tilesInfoModel.renderToBuffer(poseStack, builderTilesInfo, combinedLightIn, combinedOverlayIn, red, green, blue, 1.0F);
		poseStack.popPose();
	}

	private void renderChessBoardPlate(PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn, ChessTileEntity chessTileEntity)
	{
		float whiteR = NBTColorSaving.getRed(chessTileEntity.getWhiteTilesColor()) / 255F;
		float whiteG = NBTColorSaving.getGreen(chessTileEntity.getWhiteTilesColor()) / 255F;
		float whiteB = NBTColorSaving.getBlue(chessTileEntity.getWhiteTilesColor()) / 255F;
		float blackR = NBTColorSaving.getRed(chessTileEntity.getBlackTilesColor()) / 255F;
		float blackG = NBTColorSaving.getGreen(chessTileEntity.getBlackTilesColor()) / 255F;
		float blackB = NBTColorSaving.getBlue(chessTileEntity.getBlackTilesColor()) / 255F;

		// this is gonna need a custom render type, most likely
		VertexConsumer builderBoardPlateWhiteTiles = bufferIn.getBuffer(RenderType.entityCutout(PLATE_WHITE_TILES_TEXTURE));
		poseStack.pushPose();
		poseStack.mulPose(Vector3f.XN.rotationDegrees(180));
		poseStack.mulPose(Vector3f.YN.rotationDegrees(270));
		poseStack.translate(0.0F, -1.65D, 0.0F);
		// TODO: for some reason, this does not change colors?
		chessBoardPlateModel.renderToBuffer(poseStack, builderBoardPlateWhiteTiles, combinedLightIn, combinedOverlayIn, whiteR, whiteG, whiteB, 1.0F);
		poseStack.popPose();
		VertexConsumer builderBoardPlateBlackTiles = bufferIn.getBuffer(RenderType.entityCutout(PLATE_BLACK_TILES_TEXTURE));
		poseStack.pushPose();
		poseStack.mulPose(Vector3f.XN.rotationDegrees(180));
		poseStack.mulPose(Vector3f.YN.rotationDegrees(270));
		poseStack.translate(0.0F, -1.65D, 0.0F);
		chessBoardPlateModel.renderToBuffer(poseStack, builderBoardPlateBlackTiles, combinedLightIn, combinedOverlayIn, blackR, blackG, blackB, 1.0F);
		poseStack.popPose();
	}

	enum HighlightType
	{
		LEGAL_MOVE,
		LEAVES_PLAYER_IN_CHECK,
		CASTLE_MOVE,
		ATTACK_MOVE,
		LAST_MADE_MOVE
	}
}