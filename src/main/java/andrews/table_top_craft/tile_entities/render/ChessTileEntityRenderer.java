package andrews.table_top_craft.tile_entities.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import andrews.table_top_craft.util.obj.models.ChessObjModel;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.opengl.GL11;

import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Ints;
import com.mojang.blaze3d.systems.RenderSystem;

import andrews.table_top_craft.events.DrawScreenEvent;
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
import andrews.table_top_craft.util.NBTColorSaving;
import andrews.table_top_craft.util.Reference;
import andrews.table_top_craft.util.TTCRenderTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.DynamicTexture;

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
    
    // Chess Models
    private final ChessHighlightModel highlightModel;
    private final ChessTilesInfoModel tilesInfoModel;
    private final ChessBoardPlateModel chessBoardPlateModel;
    
	public ChessTileEntityRenderer(BlockEntityRendererProvider.Context context)
	{
		highlightModel = new ChessHighlightModel(context.bakeLayer(ChessHighlightModel.CHESS_HIGHLIGHT_LAYER));
		tilesInfoModel = new ChessTilesInfoModel(context.bakeLayer(ChessTilesInfoModel.CHESS_TILES_INFO_LAYER));
		chessBoardPlateModel = new ChessBoardPlateModel(context.bakeLayer(ChessBoardPlateModel.CHESS_BOARD_PLATE_LAYER));
	}
	
	@Override
	public void render(ChessTileEntity tileEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		if(resourceLocation == null) {
			image.setPixelRGBA(0, 0, 16777215);
			texture.upload();
			resourceLocation = Minecraft.getInstance().getTextureManager().register("table_top_craft_dummy", texture);
		}
		
		Board board;
		Direction facing = Direction.NORTH;
	    if(tileEntityIn.hasLevel())
	    {
	         BlockState blockstate = tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos());
	         if(blockstate.getBlock() instanceof ChessBlock)
	         {
	        	 facing = blockstate.getValue(ChessBlock.FACING);
	         }
	    }
	    
		poseStack.pushPose();
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
		poseStack.popPose();
		
		if(tileEntityIn.getBoard() != null)
		{
			board = tileEntityIn.getBoard();
			WhiteChessPlayer whiteChessPlayer = (WhiteChessPlayer) board.getWhiteChessPlayer();
			BlackChessPlayer blackChessPlayer = (BlackChessPlayer) board.getBlackChessPlayer();
			boolean isWhiteInCheckmate = whiteChessPlayer.isInCheckMate();
			boolean isBlackInCheckmate = blackChessPlayer.isInCheckMate();
			
			poseStack.pushPose();
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
			
			// Moves the Piece away from the center of the Board, onto the center of a tile
			poseStack.translate(CHESS_SCALE / 2D, 0.0D, CHESS_SCALE / 2D);
			// Moves the Piece to the first Tile on the Board
			poseStack.translate(CHESS_SCALE * 3, 0.0D, CHESS_SCALE * -4);

			int currentCoordinate = -1;
			for(int rank = 0; rank < BoardUtils.NUM_TILES_PER_ROW; rank++)
			{
				for(int column = 0; column < BoardUtils.NUM_TILES_PER_ROW; column++)
				{
					currentCoordinate++;
					boolean isSelectedPiece = false;
					
					// Sets the piece to selected if it is indeed selected
					if(board.getTile(currentCoordinate) == tileEntityIn.getSourceTile() && tileEntityIn.getHumanMovedPiece() != null)
						isSelectedPiece = true;
					
					// Render all the Pieces
					if(board.getTile(currentCoordinate).isTileOccupied())
					{
						PieceColor pieceColor = board.getTile(currentCoordinate).getPiece().getPieceColor();
						PieceType pieceType = board.getTile(currentCoordinate).getPiece().getPieceType();
						
						poseStack.pushPose();
						// Offsets the Piece that is about to be rendered to the current Tile
						poseStack.translate(CHESS_SCALE * -column, 0.0D, CHESS_SCALE * rank);
						
						float wR = (1F / 255F) * NBTColorSaving.getRed(tileEntityIn.getWhitePiecesColor());
						float wG = (1F / 255F) * NBTColorSaving.getGreen(tileEntityIn.getWhitePiecesColor());
						float wB = (1F / 255F) * NBTColorSaving.getBlue(tileEntityIn.getWhitePiecesColor());
						float bR = (1F / 255F) * NBTColorSaving.getRed(tileEntityIn.getBlackPiecesColor());
						float bG = (1F / 255F) * NBTColorSaving.getGreen(tileEntityIn.getBlackPiecesColor());
						float bB = (1F / 255F) * NBTColorSaving.getBlue(tileEntityIn.getBlackPiecesColor());
						
						if(isSelectedPiece)
						{
							RenderSystem.polygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
							RenderSystem.lineWidth(2F);
						}
						
						poseStack.pushPose();
						
						// Move the Pieces down to the board surface
						poseStack.translate(0D, (1 / 16D) * 2.4D, 0D);
						
						// We rotate the Piece 180 Degrees if its White and supposed to face the other way
						if(pieceColor.isWhite())
							poseStack.mulPose(Vector3f.YN.rotationDegrees(180F));
						
						// The dance the Pieces do when you check mate the enemy
						if(isWhiteInCheckmate)
						{
							if(pieceColor.isBlack())
							{
								poseStack.translate(0.0F, (float) Math.abs(Math.sin((Minecraft.getInstance().player.tickCount + getPartialTicks()) / 2.5)) * -0.05F, 0F);
								poseStack.mulPose(Vector3f.ZN.rotationDegrees((float) Math.cos((Minecraft.getInstance().player.tickCount + getPartialTicks()) / 2.5) * 10));
							}
						}
						if(isBlackInCheckmate)
						{
							if(pieceColor.isWhite())
							{
								poseStack.translate(0.0F, (float) Math.abs(Math.sin((Minecraft.getInstance().player.tickCount + getPartialTicks()) / 2.5)) * -0.05F, 0F);
								poseStack.mulPose(Vector3f.ZN.rotationDegrees((float) Math.cos((Minecraft.getInstance().player.tickCount + getPartialTicks()) / 2.5) * 10));
							}
						}
						
						// Renders The Chess Piece
						
						RenderType type = TTCRenderTypes.getChessPieceSolid(resourceLocation);
						type.setupRenderState();
						BufferHelpers.setupRender(RenderSystem.getShader());
						renderPiece(bufferIn, tileEntityIn, poseStack, pieceType, pieceColor, combinedLightIn, wR, wG, wB, bR, bG, bB);
//						BufferHelpers.teardownRender();
						type.clearRenderState();

						poseStack.popPose();
						poseStack.popPose();
					}
					
					if(isSelectedPiece)
					{
						RenderSystem.polygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
					}
					
					poseStack.pushPose();
					// Offsets the Piece that is about to be rendered to the current Tile
					poseStack.translate(CHESS_SCALE * -column, 0.0D, CHESS_SCALE * rank);
					
					if(tileEntityIn.getShowPreviousMove())
					{
						if(tileEntityIn.getMoveLog().getMoves().size() > 0)
						{
							BaseMove lastMove = tileEntityIn.getMoveLog().getMoves().get(tileEntityIn.getMoveLog().getMoves().size() - 1);
							List<Integer> destinationCoordinates = new ArrayList<>();
							for(BaseMove move : pieceLegalMoves(tileEntityIn))
							{
								destinationCoordinates.add(move.getDestinationCoordinate());
							}
							if(lastMove.getCurrentCoordinate() == currentCoordinate && (!destinationCoordinates.contains(lastMove.getCurrentCoordinate()) || !tileEntityIn.getShowAvailableMoves()))
								renderHighlight(poseStack, bufferIn, combinedLightIn, combinedOverlayIn, HighlightType.LAST_MADE_MOVE, tileEntityIn);
							if(lastMove.getDestinationCoordinate() == currentCoordinate && (!destinationCoordinates.contains(lastMove.getDestinationCoordinate()) || !tileEntityIn.getShowAvailableMoves()))
								renderHighlight(poseStack, bufferIn, combinedLightIn, combinedOverlayIn, HighlightType.LAST_MADE_MOVE, tileEntityIn);
						}
					}
					
					// Render all the Highlights
					if(tileEntityIn.getShowAvailableMoves())
					{
						for(BaseMove move : pieceLegalMoves(tileEntityIn))
						{
							MoveTransition transition = board.getCurrentChessPlayer().makeMove(move);
							if(move.getDestinationCoordinate() == currentCoordinate)
							{
								// We check if the Move is a Castling Move and if it is we render the Castling Highlight
								if(move.isCastlingMove())
								{
									renderHighlight(poseStack, bufferIn, combinedLightIn, combinedOverlayIn, HighlightType.CASTLE_MOVE, tileEntityIn);
								}	
								else
								{
									// If it wasn't a Castling Move we check the other cases we want to cover
									switch(transition.getMoveStatus())
									{
									default:
									case DONE:
										if(move.isAttack())
										{
											renderHighlight(poseStack, bufferIn, combinedLightIn, combinedOverlayIn, HighlightType.ATTACK_MOVE, tileEntityIn);
										}
										else
										{
											renderHighlight(poseStack, bufferIn, combinedLightIn, combinedOverlayIn, HighlightType.LEGAL_MOVE, tileEntityIn);
										}
										break;
									case LEAVES_PLAYER_IN_CHECK:
										renderHighlight(poseStack, bufferIn, combinedLightIn, combinedOverlayIn, HighlightType.LEAVES_PLAYER_IN_CHECK, tileEntityIn);
									}
								}
							}
						}
					}
					poseStack.popPose();
				}
			}
			
			renderTakenPieces(bufferIn, poseStack, tileEntityIn.getMoveLog(), tileEntityIn, combinedLightIn);

			poseStack.popPose();
		}
	}
	
	private void renderTakenPieces(MultiBufferSource bufferIn, PoseStack stack, ChessMoveLog moveLog, ChessTileEntity chessTileEntity, int combinedLightIn)
	{
		final List<BasePiece> whiteTakenPieces = new ArrayList<>();
		final List<BasePiece> blackTakenPieces = new ArrayList<>();
		
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
		
		// Sorts all White Taken Pieces depending on their Value
		Collections.sort(whiteTakenPieces, new Comparator<BasePiece>()
		{//TODO check if replacing the Collections.sort() with List.sort() works the same
			@Override
			public int compare(BasePiece piece1, BasePiece piece2)
			{
				return Ints.compare(piece2.getPieceValue(), piece1.getPieceValue());
			}
		});
		
		// Sorts all Black Taken Pieces depending on their Value
		Collections.sort(blackTakenPieces, new Comparator<BasePiece>()
		{//TODO check if replacing the Collections.sort() with List.sort() works the same
			@Override
			public int compare(BasePiece piece1, BasePiece piece2)
			{
				return Ints.compare(piece2.getPieceValue(), piece1.getPieceValue());
			}
		});
		
		stack.pushPose();
		// Moves the pieces down into the taken Pieces area
		stack.translate(CHESS_SCALE * -6.5D, 0.556D, CHESS_SCALE * 0.3D);
		RenderType type = TTCRenderTypes.getChessPieceSolid(resourceLocation);
		type.setupRenderState();
		BufferHelpers.setupRender(RenderSystem.getShader());
		renderTakenPiecesFigures(bufferIn, stack, chessTileEntity, whiteTakenPieces, true, combinedLightIn);
		renderTakenPiecesFigures(bufferIn, stack, chessTileEntity, blackTakenPieces, false, combinedLightIn);
		type.clearRenderState();
		stack.popPose();
	}
	
	private void renderTakenPiecesFigures(MultiBufferSource bufferIn, PoseStack stack, ChessTileEntity chessTileEntity, final List<BasePiece> pieceList, final boolean isWhite, int combinedLightIn)
	{
		int currentCoordinate = -1;
		int currentRank = 0;
		/* GiantLuigi4: I decided to move this color lookup out of the loop */
		/* reason: reduce redundant lookups, very minimal impact on performance, but it's smth */
		float wR = (1F / 255F) * NBTColorSaving.getRed(chessTileEntity.getWhitePiecesColor());
		float wG = (1F / 255F) * NBTColorSaving.getGreen(chessTileEntity.getWhitePiecesColor());
		float wB = (1F / 255F) * NBTColorSaving.getBlue(chessTileEntity.getWhitePiecesColor());
		float bR = (1F / 255F) * NBTColorSaving.getRed(chessTileEntity.getBlackPiecesColor());
		float bG = (1F / 255F) * NBTColorSaving.getGreen(chessTileEntity.getBlackPiecesColor());
		float bB = (1F / 255F) * NBTColorSaving.getBlue(chessTileEntity.getBlackPiecesColor());
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
				stack.translate((CHESS_SCALE * 0.855D) * 7D, 0.0D, 0.8D);
			stack.translate((CHESS_SCALE * 0.855D) * -currentCoordinate, 0.0D, CHESS_SCALE * -currentRank);
			
			renderPiece(bufferIn, chessTileEntity, stack, piece.getPieceType(), piece.getPieceColor(), combinedLightIn, wR, wG, wB, bR, bG, bB);
			
			stack.popPose();
		}
	}
	
	private void renderPiece(MultiBufferSource bufferIn, ChessTileEntity chessTileEntity, PoseStack poseStack, PieceType pieceType, PieceColor pieceColor, int combinedLightIn, float wR, float wG, float wB, float bR, float bG, float bB)
	{
		// The RenderType for the chess pieces (the texture is just a dummy texture)
		ShaderInstance shaderinstance = RenderSystem.getShader();
		RenderSystem.setShaderColor(pieceColor.isWhite() ? wR : bR, pieceColor.isWhite() ? wG : bG, pieceColor.isWhite() ? wB : bB, 1f);
		BufferHelpers.updateColor(shaderinstance);
		poseStack.pushPose();
		if (shaderinstance.MODEL_VIEW_MATRIX != null) shaderinstance.MODEL_VIEW_MATRIX.set(poseStack.last().pose());
		if (shaderinstance.PROJECTION_MATRIX != null) shaderinstance.PROJECTION_MATRIX.set(RenderSystem.getProjectionMatrix());
		switch(pieceType)
		{
		default:
		case PAWN:
			VertexBuffer pawnBuffer = DrawScreenEvent.pawnBuffer;
			BufferHelpers.draw(pawnBuffer, shaderinstance);
			break;
		case ROOK:
			VertexBuffer rookBuffer = DrawScreenEvent.rookBuffer;
			BufferHelpers.draw(rookBuffer, shaderinstance);
			break;
		case BISHOP:
			VertexBuffer bishopBuffer = DrawScreenEvent.bishopBuffer;
			BufferHelpers.draw(bishopBuffer, shaderinstance);
			break;
		case KNIGHT:
			VertexBuffer knightBuffer = DrawScreenEvent.knightBuffer;
			BufferHelpers.draw(knightBuffer, shaderinstance);
			break;
		case KING:
			VertexBuffer kingBuffer = DrawScreenEvent.kingBuffer;
			BufferHelpers.draw(kingBuffer, shaderinstance);
			break;
		case QUEEN:
			VertexBuffer queenBuffer = DrawScreenEvent.queenBuffer;
			BufferHelpers.draw(queenBuffer, shaderinstance);
		}
		poseStack.popPose();
	}
	
	private int getColorWithAppliedLight(Color color, int light)
	{
		int colorRed = color.getRed();
		int colorGreen = color.getGreen();
		int colorBlue = color.getBlue();
		
		// We make sure the players doesn't have night vision, because otherwise the pieces would look dark while everything else is lit
		if(!Minecraft.getInstance().player.hasEffect(MobEffects.NIGHT_VISION))
		{
			float brightnessMod = Math.max(0.06F, Math.max((light >> 16) / 240F, (light & 0xFF) / 240F));
			colorRed *= brightnessMod;
			colorGreen *= brightnessMod;
			colorBlue *= brightnessMod;
		}
		
	    return new Color(colorRed, colorGreen, colorBlue).getRGB();
	}

	private static float getPartialTicks()
	{
		return Minecraft.getInstance().isPaused() ? Minecraft.getInstance().pausePartialTick : Minecraft.getInstance().getFrameTime();
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
			float R = (1F / 255F) * NBTColorSaving.getRed(chessTileEntity.getLegalMoveColor());
			float G = (1F / 255F) * NBTColorSaving.getGreen(chessTileEntity.getLegalMoveColor());
			float B = (1F / 255F) * NBTColorSaving.getBlue(chessTileEntity.getLegalMoveColor());
			float A = (1F / 255F) * NBTColorSaving.getAlpha(chessTileEntity.getLegalMoveColor());
			highlightModel.renderToBuffer(poseStack, builderHighlight, combinedLightIn, combinedOverlayIn, R, G, B, A);
			break;
		case LEAVES_PLAYER_IN_CHECK:
			float R1 = (1F / 255F) * NBTColorSaving.getRed(chessTileEntity.getInvalidMoveColor());
			float G1 = (1F / 255F) * NBTColorSaving.getGreen(chessTileEntity.getInvalidMoveColor());
			float B1 = (1F / 255F) * NBTColorSaving.getBlue(chessTileEntity.getInvalidMoveColor());
			float A1 = (1F / 255F) * NBTColorSaving.getAlpha(chessTileEntity.getInvalidMoveColor());
			highlightModel.renderToBuffer(poseStack, builderHighlight, combinedLightIn, combinedOverlayIn, R1, G1, B1, A1);
			break;
		case CASTLE_MOVE:
			float R2 = (1F / 255F) * NBTColorSaving.getRed(chessTileEntity.getCastleMoveColor());
			float G2 = (1F / 255F) * NBTColorSaving.getGreen(chessTileEntity.getCastleMoveColor());
			float B2 = (1F / 255F) * NBTColorSaving.getBlue(chessTileEntity.getCastleMoveColor());
			float A2 = (1F / 255F) * NBTColorSaving.getAlpha(chessTileEntity.getCastleMoveColor());
			highlightModel.renderToBuffer(poseStack, builderHighlight, combinedLightIn, combinedOverlayIn, R2, G2, B2, A2);
			break;
		case ATTACK_MOVE:
			float R3 = (1F / 255F) * NBTColorSaving.getRed(chessTileEntity.getAttackMoveColor());
			float G3 = (1F / 255F) * NBTColorSaving.getGreen(chessTileEntity.getAttackMoveColor());
			float B3 = (1F / 255F) * NBTColorSaving.getBlue(chessTileEntity.getAttackMoveColor());
			float A3 = (1F / 255F) * NBTColorSaving.getAlpha(chessTileEntity.getAttackMoveColor());
			highlightModel.renderToBuffer(poseStack, builderHighlight, combinedLightIn, combinedOverlayIn, R3, G3, B3, A3);
			break;
		case LAST_MADE_MOVE:
			float R4 = (1F / 255F) * NBTColorSaving.getRed(chessTileEntity.getPreviousMoveColor());
			float G4 = (1F / 255F) * NBTColorSaving.getGreen(chessTileEntity.getPreviousMoveColor());
			float B4 = (1F / 255F) * NBTColorSaving.getBlue(chessTileEntity.getPreviousMoveColor());
			float A4 = (1F / 255F) * NBTColorSaving.getAlpha(chessTileEntity.getPreviousMoveColor());
			highlightModel.renderToBuffer(poseStack, builderHighlight, combinedLightIn, combinedOverlayIn, R4, G4, B4, A4);
		}
		poseStack.popPose();
	}
	
	private void renderTilesInfo(PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn, ChessTileEntity chessTileEntity)
	{
		//VertexConsumer builderTilesInfo = bufferIn.getBuffer(TTCRenderTypes.getEmissiveTransluscent(TILES_INFO_TEXTURE, false));
		VertexConsumer builderTilesInfo = bufferIn.getBuffer(TTCRenderTypes.getEmissiveTransluscent(TILES_INFO_TEXTURE, false));
	
		float red = (1F / 255F) * NBTColorSaving.getRed(chessTileEntity.getTileInfoColor());
		float green = (1F / 255F) * NBTColorSaving.getGreen(chessTileEntity.getTileInfoColor());
		float blue = (1F / 255F) * NBTColorSaving.getBlue(chessTileEntity.getTileInfoColor());
		
		poseStack.pushPose();
		poseStack.mulPose(Vector3f.YN.rotationDegrees(180.0F));
		poseStack.translate(0.0F, -1.32F, 0.0F);
		tilesInfoModel.renderToBuffer(poseStack, builderTilesInfo, combinedLightIn, combinedOverlayIn, red, green, blue, 1.0F);
		poseStack.popPose();
	}
	
	private void renderChessBoardPlate(PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn, ChessTileEntity chessTileEntity)
	{	
		float whiteR = (1F / 255F) * NBTColorSaving.getRed(chessTileEntity.getWhiteTilesColor());
		float whiteG = (1F / 255F) * NBTColorSaving.getGreen(chessTileEntity.getWhiteTilesColor());
		float whiteB = (1F / 255F) * NBTColorSaving.getBlue(chessTileEntity.getWhiteTilesColor());
		float blackR = (1F / 255F) * NBTColorSaving.getRed(chessTileEntity.getBlackTilesColor());
		float blackG = (1F / 255F) * NBTColorSaving.getGreen(chessTileEntity.getBlackTilesColor());
		float blackB = (1F / 255F) * NBTColorSaving.getBlue(chessTileEntity.getBlackTilesColor());
		
		// this is gonna need a custom render type, most likely
		VertexConsumer builderBoardPlateWhiteTiles = bufferIn.getBuffer(RenderType.entityCutout(PLATE_WHITE_TILES_TEXTURE));
		poseStack.pushPose();
		poseStack.mulPose(new Quaternion(180, 270, 0, true));
		poseStack.translate(0.0F, -1.65D, 0.0F);
		chessBoardPlateModel.renderToBuffer(poseStack, builderBoardPlateWhiteTiles, combinedLightIn, combinedOverlayIn, whiteR, whiteG, whiteB, 1.0F);
		poseStack.popPose();
		VertexConsumer builderBoardPlateBlackTiles = bufferIn.getBuffer(RenderType.entityCutout(PLATE_BLACK_TILES_TEXTURE));
		poseStack.pushPose();
		poseStack.mulPose(new Quaternion(180, 270, 0, true));
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