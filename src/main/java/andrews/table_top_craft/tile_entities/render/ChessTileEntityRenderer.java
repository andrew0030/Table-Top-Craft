package andrews.table_top_craft.tile_entities.render;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Ints;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.vertex.VertexBuilderUtils;

import andrews.table_top_craft.game_logic.chess.PieceColor;
import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.BoardUtils;
import andrews.table_top_craft.game_logic.chess.board.ChessMoveLog;
import andrews.table_top_craft.game_logic.chess.board.moves.BaseMove;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece.PieceType;
import andrews.table_top_craft.game_logic.chess.player.MoveTransition;
import andrews.table_top_craft.objects.blocks.ChessBlock;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.tile_entities.model.chess.ChessBishopModel;
import andrews.table_top_craft.tile_entities.model.chess.ChessHighlightModel;
import andrews.table_top_craft.tile_entities.model.chess.ChessKingModel;
import andrews.table_top_craft.tile_entities.model.chess.ChessKnightModel;
import andrews.table_top_craft.tile_entities.model.chess.ChessPawnModel;
import andrews.table_top_craft.tile_entities.model.chess.ChessQueenModel;
import andrews.table_top_craft.tile_entities.model.chess.ChessRookModel;
import andrews.table_top_craft.tile_entities.model.chess.ChessTilesInfoModel;
import andrews.table_top_craft.util.Reference;
import andrews.table_top_craft.util.TTCRenderTypes;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class ChessTileEntityRenderer extends TileEntityRenderer<ChessTileEntity>
{
	private static final ResourceLocation PAWN_TEXTURE_BLACK = new ResourceLocation(Reference.MODID, "textures/tile/chess/black_pieces.png");
    private static final ResourceLocation PAWN_TEXTURE_WHITE = new ResourceLocation(Reference.MODID, "textures/tile/chess/white_pieces.png");
    private static final ResourceLocation HIGHLIGHT_TEXTURE = new ResourceLocation(Reference.MODID, "textures/tile/chess/highlight.png");
    private static final ResourceLocation TILES_INFO_TEXTURE = new ResourceLocation(Reference.MODID, "textures/tile/chess/chess_tiles_info.png");
    private static final float CHESS_SCALE = 0.125F;
    private final float CHESS_PIECE_SCALE = 0.1F;
	
    //Chess Figures Models
    private final ChessPawnModel pawnModel;
    private final ChessRookModel rookModel;
    private final ChessBishopModel bishopModel;
    private final ChessKnightModel knightModel;
    private final ChessKingModel kingModel;
    private final ChessQueenModel queenModel;
    private final ChessHighlightModel highlightModel;
    private final ChessTilesInfoModel tilesInfoModel;
    
	public ChessTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
	{
		super(rendererDispatcherIn);
		pawnModel = new ChessPawnModel();
		rookModel = new ChessRookModel();
		bishopModel = new ChessBishopModel();
		knightModel = new ChessKnightModel();
		kingModel = new ChessKingModel();
		queenModel = new ChessQueenModel();
		highlightModel = new ChessHighlightModel();
		tilesInfoModel = new ChessTilesInfoModel();
	}
	
	@Override
	public void render(ChessTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		if(tileEntityIn.getBoard() != null)
		{
			Board board = tileEntityIn.getBoard();
			Direction facing = Direction.NORTH;
		    if(tileEntityIn.hasWorld())
		    {
		         BlockState blockstate = tileEntityIn.getWorld().getBlockState(tileEntityIn.getPos());
		         if(blockstate.getBlock() instanceof ChessBlock)
		         {
		        	 facing = blockstate.get(ChessBlock.FACING);
		         }
		    }
		    
			matrixStackIn.push();
			
			matrixStackIn.translate(0.5D, 0.9D, 0.5D);
			matrixStackIn.scale(1.0F, -1.0F, -1.0F);
			
			switch(facing)
			{
			default:
			case NORTH:
				matrixStackIn.rotate(Vector3f.YN.rotationDegrees(180.0F));
				break;
			case SOUTH:
				break;
			case WEST:
				matrixStackIn.rotate(Vector3f.YN.rotationDegrees(270.0F));
				break;
			case EAST:
				matrixStackIn.rotate(Vector3f.YN.rotationDegrees(90.0F));
			}
			
			// Renders the Numbers and Letters around the Chess Block
			if(tileEntityIn.getShouldShowTileInfo())
				renderTilesInfo(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
			
			// Moves the Piece away from the center of the Board, onto the center of a tile
			matrixStackIn.translate(CHESS_SCALE / 2D, 0.0D, CHESS_SCALE / 2D);
			// Moves the Piece to the first Tile on the Board
			matrixStackIn.translate(CHESS_SCALE * 3, 0.0D, CHESS_SCALE * -4);
			
			int currentCoordinate = -1;
			for(int rank = 0; rank < BoardUtils.NUM_TILES_PER_ROW; rank++)
			{
				for(int column = 0; column < BoardUtils.NUM_TILES_PER_ROW; column++)
				{
					currentCoordinate++;
					boolean isSelectedPiece = false;
					
					// Render all the Pieces
					if(board.getTile(currentCoordinate).isTileOccupied())
					{
						PieceColor pieceColor = board.getTile(currentCoordinate).getPiece().getPieceColor();
						PieceType pieceType = board.getTile(currentCoordinate).getPiece().getPieceType();
						
						matrixStackIn.push();
						// Offsets the Piece that is about to be rendered to the current Tile
						matrixStackIn.translate(CHESS_SCALE * -column, 0.0D, CHESS_SCALE * rank);
						// Used to determine whether or not a piece should be highlighted
						if(board.getTile(currentCoordinate) == tileEntityIn.getSourceTile() && tileEntityIn.getHumanMovedPiece() != null)
							isSelectedPiece = true;
						
						switch(pieceType)
						{
						default:
						case PAWN:
							renderPawn(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, pieceColor, isSelectedPiece);
							break;
						case ROOK:
							renderRook(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, pieceColor, isSelectedPiece);
							break;
						case BISHOP:
							renderBishop(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, pieceColor, isSelectedPiece);
							break;
						case KNIGHT:
							renderKnight(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, pieceColor, isSelectedPiece);
							break;
						case KING:
							renderKing(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, pieceColor, isSelectedPiece);
							break;
						case QUEEN:
							renderQueen(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, pieceColor, isSelectedPiece);
						}
						matrixStackIn.pop();
					}
					
					matrixStackIn.push();
					// Offsets the Piece that is about to be rendered to the current Tile
					matrixStackIn.translate(CHESS_SCALE * -column, 0.0D, CHESS_SCALE * rank);
					
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
								renderHighlight(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, HighlightType.LAST_MADE_MOVE);
							if(lastMove.getDestinationCoordinate() == currentCoordinate && (!destinationCoordinates.contains(lastMove.getDestinationCoordinate()) || !tileEntityIn.getShowAvailableMoves()))
								renderHighlight(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, HighlightType.LAST_MADE_MOVE);
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
									renderHighlight(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, HighlightType.CASTLE_MOVE);
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
											renderHighlight(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, HighlightType.ATTACK_MOVE);
										}
										else
										{
											renderHighlight(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, HighlightType.LEGAL_MOVE);
										}
										break;
									case LEAVES_PLAYER_IN_CHECK:
										renderHighlight(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, HighlightType.LEAVES_PLAYER_IN_CHECK);
									}
								}
							}
						}
					}
					matrixStackIn.pop();
				}
			}
			
			// Renders all the taken Pieces
			renderTakenPieces(tileEntityIn.getMoveLog(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
			
			matrixStackIn.pop();
		}
	}
	
	/**
	 * Renders all the taken Pieces under the Chess Board
	 */
	private void renderTakenPieces(ChessMoveLog moveLog, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
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
					throw new RuntimeException("Attemted to get a Piece that had no PieceColor");
				}
			}
		}
		
		// Sorts all White Taken Pieces depending on their Value
		Collections.sort(whiteTakenPieces, new Comparator<BasePiece>()
		{
			@Override
			public int compare(BasePiece piece1, BasePiece piece2)
			{
				return Ints.compare(piece2.getPieceValue(), piece1.getPieceValue());
			}
		});
		
		// Sorts all Black Taken Pieces depending on their Value
		Collections.sort(blackTakenPieces, new Comparator<BasePiece>()
		{
			@Override
			public int compare(BasePiece piece1, BasePiece piece2)
			{
				return Ints.compare(piece2.getPieceValue(), piece1.getPieceValue());
			}
		});
		
		matrixStackIn.push();
		matrixStackIn.translate(CHESS_SCALE * -6.5D, 0.41D, CHESS_SCALE * 0.3D);
		renderTakenPiecesFigures(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, whiteTakenPieces, true);
		renderTakenPiecesFigures(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, blackTakenPieces, false);
		matrixStackIn.pop();
	}
	
	private void renderTakenPiecesFigures(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, final List<BasePiece> pieceList, final boolean isWhite)
	{
		int currentCoordinate = -1;
		int currentRank = 0;
		for(final BasePiece piece : pieceList)
		{
			if(isWhite)
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
			}
			else
			{	
				if(currentCoordinate > -9)
				{
					currentCoordinate--;
				}
				else
				{
					currentCoordinate = -2;
					currentRank -= 1;
				}
			}
			
			matrixStackIn.push();
			if(!isWhite)
				matrixStackIn.translate((CHESS_SCALE * 0.855D) * 9D, 0.0D, 0.8D);
			matrixStackIn.translate((CHESS_SCALE * 0.855D) * currentCoordinate, 0.0D, CHESS_SCALE * currentRank);
			switch(piece.getPieceType())
			{
			default:
			case PAWN:
				renderPawn(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, piece.getPieceColor(), false);
				break;
			case ROOK:
				renderRook(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, piece.getPieceColor(), false);
				break;
			case BISHOP:
				renderBishop(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, piece.getPieceColor(), false);
				break;
			case KNIGHT:
				renderKnight(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, piece.getPieceColor(), false);
				break;
			case KING:
				renderKing(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, piece.getPieceColor(), false);
				break;
			case QUEEN:
				renderQueen(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, piece.getPieceColor(), false);
			}
			matrixStackIn.pop();
		}
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
	
	private void renderPawn(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, PieceColor pieceColor, boolean isSelected)
	{
		IVertexBuilder builderPawn = pieceColor.isBlack() ?
									 bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(PAWN_TEXTURE_BLACK)) :
									 bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(PAWN_TEXTURE_WHITE));
		if(isSelected)
			builderPawn = VertexBuilderUtils.newDelegate(bufferIn.getBuffer(RenderType.getEntityGlint()), pieceColor.isBlack() ?
						  bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(PAWN_TEXTURE_BLACK)) :
						  bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(PAWN_TEXTURE_WHITE)));
		matrixStackIn.push();
		if(pieceColor.isBlack())
			matrixStackIn.rotate(Vector3f.YN.rotationDegrees(180.0F));
		matrixStackIn.scale(CHESS_PIECE_SCALE, CHESS_PIECE_SCALE, CHESS_PIECE_SCALE);
		pawnModel.Base.render(matrixStackIn, builderPawn, combinedLightIn, combinedOverlayIn);
		matrixStackIn.pop();
	}
	
	private void renderRook(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, PieceColor pieceColor, boolean isSelected)
	{
		IVertexBuilder builderRook = pieceColor.isBlack() ?
									 bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(PAWN_TEXTURE_BLACK)) :
									 bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(PAWN_TEXTURE_WHITE));
	    if(isSelected)
	    	builderRook = VertexBuilderUtils.newDelegate(bufferIn.getBuffer(RenderType.getEntityGlint()), pieceColor.isBlack() ?
						  bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(PAWN_TEXTURE_BLACK)) :
						  bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(PAWN_TEXTURE_WHITE)));
		matrixStackIn.push();
		if(pieceColor.isBlack())
			matrixStackIn.rotate(Vector3f.YN.rotationDegrees(180.0F));
		matrixStackIn.scale(CHESS_PIECE_SCALE, CHESS_PIECE_SCALE, CHESS_PIECE_SCALE);
		matrixStackIn.push();
		matrixStackIn.translate(0.0F, 0.1F, 0.0F);
		matrixStackIn.scale(0.6F, 0.6F, 0.6F);
		rookModel.GolemBodyTop.render(matrixStackIn, builderRook, combinedLightIn, combinedOverlayIn);
		matrixStackIn.pop();
		rookModel.Base.render(matrixStackIn, builderRook, combinedLightIn, combinedOverlayIn);
		matrixStackIn.pop();
	}
	
	private void renderBishop(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, PieceColor pieceColor, boolean isSelected)
	{
		IVertexBuilder builderBishop = pieceColor.isBlack() ?
									 bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(PAWN_TEXTURE_BLACK)) :
									 bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(PAWN_TEXTURE_WHITE));
		if(isSelected)
			builderBishop = VertexBuilderUtils.newDelegate(bufferIn.getBuffer(RenderType.getEntityGlint()), pieceColor.isBlack() ?
						    bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(PAWN_TEXTURE_BLACK)) :
						    bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(PAWN_TEXTURE_WHITE)));
		matrixStackIn.push();
		if(pieceColor.isBlack())
			matrixStackIn.rotate(Vector3f.YN.rotationDegrees(180.0F));
		matrixStackIn.scale(CHESS_PIECE_SCALE, CHESS_PIECE_SCALE, CHESS_PIECE_SCALE);
		bishopModel.Base.render(matrixStackIn, builderBishop, combinedLightIn, combinedOverlayIn);
		matrixStackIn.pop();
	}
	
	private void renderKnight(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, PieceColor pieceColor, boolean isSelected)
	{
		IVertexBuilder builderRook = pieceColor.isBlack() ?
									 bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(PAWN_TEXTURE_BLACK)) :
									 bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(PAWN_TEXTURE_WHITE));
		if(isSelected)
			builderRook = VertexBuilderUtils.newDelegate(bufferIn.getBuffer(RenderType.getEntityGlint()), pieceColor.isBlack() ?
						  bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(PAWN_TEXTURE_BLACK)) :
						  bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(PAWN_TEXTURE_WHITE)));
		matrixStackIn.push();
		if(pieceColor.isBlack())
			matrixStackIn.rotate(Vector3f.YN.rotationDegrees(180.0F));
		matrixStackIn.scale(CHESS_PIECE_SCALE, CHESS_PIECE_SCALE, CHESS_PIECE_SCALE);
		matrixStackIn.push();
		matrixStackIn.translate(0.0F, 0.3F, 0.0F);
		matrixStackIn.scale(0.6F, 0.6F, 0.6F);
		knightModel.HorseBody.render(matrixStackIn, builderRook, combinedLightIn, combinedOverlayIn);
		matrixStackIn.pop();
		knightModel.Base.render(matrixStackIn, builderRook, combinedLightIn, combinedOverlayIn);
		matrixStackIn.pop();
	}
	
	private void renderKing(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, PieceColor pieceColor, boolean isSelected)
	{
		IVertexBuilder builderKing = pieceColor.isBlack() ?
									 bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(PAWN_TEXTURE_BLACK)) :
									 bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(PAWN_TEXTURE_WHITE));
		if(isSelected)
			builderKing = VertexBuilderUtils.newDelegate(bufferIn.getBuffer(RenderType.getEntityGlint()), pieceColor.isBlack() ?
						  bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(PAWN_TEXTURE_BLACK)) :
						  bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(PAWN_TEXTURE_WHITE)));
		matrixStackIn.push();
		if(pieceColor.isBlack())
			matrixStackIn.rotate(Vector3f.YN.rotationDegrees(180.0F));
		matrixStackIn.scale(CHESS_PIECE_SCALE, CHESS_PIECE_SCALE, CHESS_PIECE_SCALE);
		kingModel.Base.render(matrixStackIn, builderKing, combinedLightIn, combinedOverlayIn);
		matrixStackIn.pop();
	}
	
	private void renderQueen(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, PieceColor pieceColor, boolean isSelected)
	{
		IVertexBuilder builderQueen = pieceColor.isBlack() ?
									 bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(PAWN_TEXTURE_BLACK)) :
									 bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(PAWN_TEXTURE_WHITE));
		if(isSelected)
			builderQueen = VertexBuilderUtils.newDelegate(bufferIn.getBuffer(RenderType.getEntityGlint()), pieceColor.isBlack() ?
						   bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(PAWN_TEXTURE_BLACK)) :
						   bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(PAWN_TEXTURE_WHITE)));
		matrixStackIn.push();
		if(pieceColor.isBlack())
			matrixStackIn.rotate(Vector3f.YN.rotationDegrees(180.0F));
		matrixStackIn.scale(CHESS_PIECE_SCALE, CHESS_PIECE_SCALE, CHESS_PIECE_SCALE);
		queenModel.Base.render(matrixStackIn, builderQueen, combinedLightIn, combinedOverlayIn);
		matrixStackIn.pop();
	}
	
	private void renderHighlight(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, HighlightType highlightType)
	{
		IVertexBuilder builderHighlight = bufferIn.getBuffer(TTCRenderTypes.getEmissiveTransluscent(HIGHLIGHT_TEXTURE, false));
		matrixStackIn.push();
		matrixStackIn.translate(0, -1.345F, 0);
		matrixStackIn.scale(CHESS_PIECE_SCALE * 10, CHESS_PIECE_SCALE * 10, CHESS_PIECE_SCALE * 10);
		switch(highlightType)
		{
		default:
		case LEGAL_MOVE:
			highlightModel.highlight.render(matrixStackIn, builderHighlight, combinedLightIn, combinedOverlayIn, 0.0F, 1.0F, 0.0F, 0.4F);
			break;
		case LEAVES_PLAYER_IN_CHECK:
			highlightModel.highlight.render(matrixStackIn, builderHighlight, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 0.0F, 0.4F);
			break;
		case CASTLE_MOVE:
			highlightModel.highlight.render(matrixStackIn, builderHighlight, combinedLightIn, combinedOverlayIn, 0.5F, 0.0F, 1.0F, 0.4F);
			break;
		case ATTACK_MOVE:
			highlightModel.highlight.render(matrixStackIn, builderHighlight, combinedLightIn, combinedOverlayIn, 1.0F, 0.0F, 0.0F, 0.4F);
			break;
		case LAST_MADE_MOVE:
			highlightModel.highlight.render(matrixStackIn, builderHighlight, combinedLightIn, combinedOverlayIn, 0.0F, 0.6F, 0.5F, 0.4F);
		}
		matrixStackIn.pop();
	}
	
	private void renderTilesInfo(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		IVertexBuilder builderTilesInfo = bufferIn.getBuffer(TTCRenderTypes.getEmissiveTransluscent(TILES_INFO_TEXTURE, false));
	
		matrixStackIn.push();
		matrixStackIn.rotate(Vector3f.YN.rotationDegrees(180.0F));
		matrixStackIn.translate(0.0F, -1.32F, 0.0F);
		tilesInfoModel.base.render(matrixStackIn, builderTilesInfo, combinedLightIn, combinedOverlayIn);
		matrixStackIn.pop();
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