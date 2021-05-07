package andrews.table_top_craft.tile_entities.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import andrews.table_top_craft.events.DrawScreenEvent;
import andrews.table_top_craft.game_logic.chess.PieceColor;
import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.BoardUtils;
import andrews.table_top_craft.game_logic.chess.board.moves.BaseMove;
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
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potions;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class ChessTileEntityRenderer extends TileEntityRenderer<ChessTileEntity>
{
    private static final ResourceLocation HIGHLIGHT_TEXTURE = new ResourceLocation(Reference.MODID, "textures/tile/chess/highlight.png");
    private static final ResourceLocation TILES_INFO_TEXTURE = new ResourceLocation(Reference.MODID, "textures/tile/chess/chess_tiles_info.png");
    private static final ResourceLocation PLATE_WHITE_TILES_TEXTURE = new ResourceLocation(Reference.MODID, "textures/tile/chess/plate_white_tiles.png");
    private static final ResourceLocation PLATE_BLACK_TILES_TEXTURE = new ResourceLocation(Reference.MODID, "textures/tile/chess/plate_black_tiles.png");
    private static final float CHESS_SCALE = 0.125F;
    private final float CHESS_PIECE_SCALE = 0.1F;
    
    // Dynamic Texture
    private static final NativeImage image = new NativeImage(NativeImage.PixelFormat.RGBA, 1, 1, true);
    private static final DynamicTexture texture = new DynamicTexture(image);
    private static ResourceLocation resourceLocation = null;
    
    // Chess Models
    private final ChessHighlightModel highlightModel;
    private final ChessTilesInfoModel tilesInfoModel;
    private final ChessBoardPlateModel chessBoardPlateModel;
    
	public ChessTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
	{
		super(rendererDispatcherIn);
		highlightModel = new ChessHighlightModel();
		tilesInfoModel = new ChessTilesInfoModel();
		chessBoardPlateModel = new ChessBoardPlateModel();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void render(ChessTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
	{	
		Board board;
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
		
		// Renders the Custom Plate if needed
		if(tileEntityIn.getUseCustomPlate())
			renderChessBoardPlate(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, tileEntityIn);
		// Renders the Numbers and Letters around the Chess Block
		if(tileEntityIn.getShouldShowTileInfo())
			renderTilesInfo(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, tileEntityIn);
		matrixStackIn.pop();
		
		if(tileEntityIn.getBoard() != null)
		{
			board = tileEntityIn.getBoard();
			WhiteChessPlayer whiteChessPlayer = (WhiteChessPlayer) board.getWhiteChessPlayer();
			BlackChessPlayer blackChessPlayer = (BlackChessPlayer) board.getBlackChessPlayer();
			boolean isWhiteInCheckmate = whiteChessPlayer.isInCheckMate();
			boolean isBlackInCheckmate = blackChessPlayer.isInCheckMate();
			
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
			
			// Moves the Piece away from the center of the Board, onto the center of a tile
			matrixStackIn.translate(CHESS_SCALE / 2D, 0.0D, CHESS_SCALE / 2D);
			// Moves the Piece to the first Tile on the Board
			matrixStackIn.translate(CHESS_SCALE * 3, 0.0D, CHESS_SCALE * -4);
			
//	TODO		int light = WorldRenderer.getPackedLightmapCoords(tileEntityIn.getWorld(), tileEntityIn.getWorld().getBlockState(tileEntityIn.getPos()), tileEntityIn.getPos());
//			CHESS_PIECE_MODEL.render(matrixStackIn, bufferIn, light);
			
			
			
			int currentCoordinate = -1;
			for(int rank = 0; rank < BoardUtils.NUM_TILES_PER_ROW; rank++)
			{
				for(int column = 0; column < BoardUtils.NUM_TILES_PER_ROW; column++)
				{
					currentCoordinate++;
					
					
					
					//TODO ______________________________________________________________________________________________________________________
					
					boolean isSelectedPiece = false;
					
					// Sets the piece to selected if it is indeed selected
					if(board.getTile(currentCoordinate) == tileEntityIn.getSourceTile() && tileEntityIn.getHumanMovedPiece() != null)
						isSelectedPiece = true;
					
					// Render all the Pieces
					if(board.getTile(currentCoordinate).isTileOccupied())
					{
						PieceColor pieceColor = board.getTile(currentCoordinate).getPiece().getPieceColor();
						PieceType pieceType = board.getTile(currentCoordinate).getPiece().getPieceType();
						
						matrixStackIn.push();
						// Offsets the Piece that is about to be rendered to the current Tile
						matrixStackIn.translate(CHESS_SCALE * -column, 0.0D, CHESS_SCALE * rank);
						
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
						
						matrixStackIn.push();
						
						// Move the Pieces down to the board surface
						matrixStackIn.translate(0D, (1 / 16D) * 2.4D, 0D);
						
						// We rotate the Piece 180 Degrees if its White and supposed to face the other way
						if(pieceColor.isWhite())
							matrixStackIn.rotate(Vector3f.YN.rotationDegrees(180F));
						
						// The dance the Pieces do when you check mate the enemy
						if(isWhiteInCheckmate)
						{
							if(pieceColor.isBlack())
							{
								matrixStackIn.translate(0.0F, (float) Math.abs(Math.sin((Minecraft.getInstance().player.ticksExisted + getPartialTicks()) / 2.5)) * -0.05F, 0F);
								matrixStackIn.rotate(Vector3f.ZN.rotationDegrees((float) Math.cos((Minecraft.getInstance().player.ticksExisted + getPartialTicks()) / 2.5) * 10));
							}
						}
						if(isBlackInCheckmate)
						{
							if(pieceColor.isWhite())
							{
								matrixStackIn.translate(0.0F, (float) Math.abs(Math.sin((Minecraft.getInstance().player.ticksExisted + getPartialTicks()) / 2.5)) * -0.05F, 0F);
								matrixStackIn.rotate(Vector3f.ZN.rotationDegrees((float) Math.cos((Minecraft.getInstance().player.ticksExisted + getPartialTicks()) / 2.5) * 10));
							}
						}
						
						
				    	image.setPixelRGBA(0, 0, getColorWithAppliedLight(new Color(pieceColor.isWhite() ? wB : bB,
				    																pieceColor.isWhite() ? wG : bG,
				    																pieceColor.isWhite() ? wR : bR),
				    																combinedLightIn));
				    	texture.updateDynamicTexture();
				    	if(resourceLocation == null)
				    		resourceLocation = Minecraft.getInstance().getTextureManager().getDynamicTextureLocation("table_top_craft_dummy", texture);
						
						// The RenderType for the chess pieces (the texture is just a dummy texture)
						RenderType type = TTCRenderTypes.getChessPieceSolid(resourceLocation);
						type.setupRenderState();
						switch(pieceType)
						{
						default:
						case PAWN:
							VertexBuffer pawnBuffer = DrawScreenEvent.pawnBuffer;
							matrixStackIn.push();
							pawnBuffer.bindBuffer();
							DefaultVertexFormats.BLOCK.setupBufferState(0L);
							RenderSystem.shadeModel(GL11.GL_FLAT);
							pawnBuffer.draw(matrixStackIn.getLast().getMatrix(), GL11.GL_TRIANGLES);
							VertexBuffer.unbindBuffer();
							RenderSystem.clearCurrentColor();
							matrixStackIn.pop();
							break;
						case ROOK:
							VertexBuffer rookBuffer = DrawScreenEvent.rookBuffer;
							matrixStackIn.push();
							rookBuffer.bindBuffer();
							DefaultVertexFormats.BLOCK.setupBufferState(0L);
							rookBuffer.draw(matrixStackIn.getLast().getMatrix(), GL11.GL_TRIANGLES);
							VertexBuffer.unbindBuffer();
							matrixStackIn.pop();
							break;
						case BISHOP:
							VertexBuffer bishopBuffer = DrawScreenEvent.bishopBuffer;
							matrixStackIn.push();
							bishopBuffer.bindBuffer();
							DefaultVertexFormats.BLOCK.setupBufferState(0L);
							bishopBuffer.draw(matrixStackIn.getLast().getMatrix(), GL11.GL_TRIANGLES);
							VertexBuffer.unbindBuffer();
							matrixStackIn.pop();
							break;
						case KNIGHT:
							VertexBuffer knightBuffer = DrawScreenEvent.knightBuffer;
							matrixStackIn.push();
							knightBuffer.bindBuffer();
							DefaultVertexFormats.BLOCK.setupBufferState(0L);
							knightBuffer.draw(matrixStackIn.getLast().getMatrix(), GL11.GL_TRIANGLES);
							VertexBuffer.unbindBuffer();
							matrixStackIn.pop();
							break;
						case KING:
							VertexBuffer kingBuffer = DrawScreenEvent.kingBuffer;
							matrixStackIn.push();
							kingBuffer.bindBuffer();
							DefaultVertexFormats.BLOCK.setupBufferState(0L);
							kingBuffer.draw(matrixStackIn.getLast().getMatrix(), GL11.GL_TRIANGLES);
							VertexBuffer.unbindBuffer();
							matrixStackIn.pop();
							break;
						case QUEEN:
							VertexBuffer queenBuffer = DrawScreenEvent.queenBuffer;
							matrixStackIn.push();
							queenBuffer.bindBuffer();
							DefaultVertexFormats.BLOCK.setupBufferState(0L);
							queenBuffer.draw(matrixStackIn.getLast().getMatrix(), GL11.GL_TRIANGLES);
							VertexBuffer.unbindBuffer();
							matrixStackIn.pop();
						}
						type.clearRenderState();

						matrixStackIn.pop();
						matrixStackIn.pop();
					}
					
					if(isSelectedPiece)
					{
						RenderSystem.polygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
					}
					
					//TODO ____________________________________________________________________________________________________________________________________________________________
					
					
					
					
					
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
								renderHighlight(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, HighlightType.LAST_MADE_MOVE, tileEntityIn);
							if(lastMove.getDestinationCoordinate() == currentCoordinate && (!destinationCoordinates.contains(lastMove.getDestinationCoordinate()) || !tileEntityIn.getShowAvailableMoves()))
								renderHighlight(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, HighlightType.LAST_MADE_MOVE, tileEntityIn);
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
									renderHighlight(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, HighlightType.CASTLE_MOVE, tileEntityIn);
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
											renderHighlight(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, HighlightType.ATTACK_MOVE, tileEntityIn);
										}
										else
										{
											renderHighlight(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, HighlightType.LEGAL_MOVE, tileEntityIn);
										}
										break;
									case LEAVES_PLAYER_IN_CHECK:
										renderHighlight(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, HighlightType.LEAVES_PLAYER_IN_CHECK, tileEntityIn);
									}
								}
							}
						}
					}
					matrixStackIn.pop();
				}
			}
			matrixStackIn.pop();
		}
	}
	
	private int getColorWithAppliedLight(Color color, int light)
	{
		int colorRed = color.getRed();
		int colorGreen = color.getGreen();
		int colorBlue = color.getBlue();
		
		// We make sure the players doesn't have night vision, because otherwise the pieces would look dark while everything else is lit
		if(!Minecraft.getInstance().player.isPotionActive(Effects.NIGHT_VISION))
		{
			colorRed *= Math.max(0.06F, Math.max((light >> 16) / 240F, (light & 0xFF) / 240F));
			colorGreen *= Math.max(0.06F, Math.max((light >> 16) / 240F, (light & 0xFF) / 240F));
			colorBlue *= Math.max(0.06F, Math.max((light >> 16) / 240F, (light & 0xFF) / 240F));
		}
		
	    return new Color(colorRed, colorGreen, colorBlue).getRGB();
	}

	private static float getPartialTicks()
	{
		return Minecraft.getInstance().isGamePaused() ? Minecraft.getInstance().renderPartialTicksPaused : Minecraft.getInstance().getRenderPartialTicks();
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
	
	private void renderHighlight(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, HighlightType highlightType, ChessTileEntity chessTileEntity)
	{
		IVertexBuilder builderHighlight = bufferIn.getBuffer(TTCRenderTypes.getEmissiveTransluscent(HIGHLIGHT_TEXTURE, false));
		matrixStackIn.push();
		matrixStackIn.translate(0, -1.345F, 0);
		matrixStackIn.scale(CHESS_PIECE_SCALE * 10, CHESS_PIECE_SCALE * 10, CHESS_PIECE_SCALE * 10);
		switch(highlightType)
		{
		default:
		case LEGAL_MOVE:
			float R = (1F / 255F) * NBTColorSaving.getRed(chessTileEntity.getLegalMoveColor());
			float G = (1F / 255F) * NBTColorSaving.getGreen(chessTileEntity.getLegalMoveColor());
			float B = (1F / 255F) * NBTColorSaving.getBlue(chessTileEntity.getLegalMoveColor());
			float A = (1F / 255F) * NBTColorSaving.getAlpha(chessTileEntity.getLegalMoveColor());
			highlightModel.highlight.render(matrixStackIn, builderHighlight, combinedLightIn, combinedOverlayIn, R, G, B, A);
			break;
		case LEAVES_PLAYER_IN_CHECK:
			float R1 = (1F / 255F) * NBTColorSaving.getRed(chessTileEntity.getInvalidMoveColor());
			float G1 = (1F / 255F) * NBTColorSaving.getGreen(chessTileEntity.getInvalidMoveColor());
			float B1 = (1F / 255F) * NBTColorSaving.getBlue(chessTileEntity.getInvalidMoveColor());
			float A1 = (1F / 255F) * NBTColorSaving.getAlpha(chessTileEntity.getInvalidMoveColor());
			highlightModel.highlight.render(matrixStackIn, builderHighlight, combinedLightIn, combinedOverlayIn, R1, G1, B1, A1);
			break;
		case CASTLE_MOVE:
			float R2 = (1F / 255F) * NBTColorSaving.getRed(chessTileEntity.getCastleMoveColor());
			float G2 = (1F / 255F) * NBTColorSaving.getGreen(chessTileEntity.getCastleMoveColor());
			float B2 = (1F / 255F) * NBTColorSaving.getBlue(chessTileEntity.getCastleMoveColor());
			float A2 = (1F / 255F) * NBTColorSaving.getAlpha(chessTileEntity.getCastleMoveColor());
			highlightModel.highlight.render(matrixStackIn, builderHighlight, combinedLightIn, combinedOverlayIn, R2, G2, B2, A2);
			break;
		case ATTACK_MOVE:
			float R3 = (1F / 255F) * NBTColorSaving.getRed(chessTileEntity.getAttackMoveColor());
			float G3 = (1F / 255F) * NBTColorSaving.getGreen(chessTileEntity.getAttackMoveColor());
			float B3 = (1F / 255F) * NBTColorSaving.getBlue(chessTileEntity.getAttackMoveColor());
			float A3 = (1F / 255F) * NBTColorSaving.getAlpha(chessTileEntity.getAttackMoveColor());
			highlightModel.highlight.render(matrixStackIn, builderHighlight, combinedLightIn, combinedOverlayIn, R3, G3, B3, A3);
			break;
		case LAST_MADE_MOVE:
			float R4 = (1F / 255F) * NBTColorSaving.getRed(chessTileEntity.getPreviousMoveColor());
			float G4 = (1F / 255F) * NBTColorSaving.getGreen(chessTileEntity.getPreviousMoveColor());
			float B4 = (1F / 255F) * NBTColorSaving.getBlue(chessTileEntity.getPreviousMoveColor());
			float A4 = (1F / 255F) * NBTColorSaving.getAlpha(chessTileEntity.getPreviousMoveColor());
			highlightModel.highlight.render(matrixStackIn, builderHighlight, combinedLightIn, combinedOverlayIn, R4, G4, B4, A4);
		}
		matrixStackIn.pop();
	}
	
	private void renderTilesInfo(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, ChessTileEntity chessTileEntity)
	{
		IVertexBuilder builderTilesInfo = bufferIn.getBuffer(TTCRenderTypes.getEmissiveTransluscent(TILES_INFO_TEXTURE, false));
	
		float red = (1F / 255F) * NBTColorSaving.getRed(chessTileEntity.getTileInfoColor());
		float green = (1F / 255F) * NBTColorSaving.getGreen(chessTileEntity.getTileInfoColor());
		float blue = (1F / 255F) * NBTColorSaving.getBlue(chessTileEntity.getTileInfoColor());
		
		matrixStackIn.push();
		matrixStackIn.rotate(Vector3f.YN.rotationDegrees(180.0F));
		matrixStackIn.translate(0.0F, -1.32F, 0.0F);
		tilesInfoModel.base.render(matrixStackIn, builderTilesInfo, combinedLightIn, combinedOverlayIn, red, green, blue, 1.0F);
		matrixStackIn.pop();
	}
	
	private void renderChessBoardPlate(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, ChessTileEntity chessTileEntity)
	{	
		float whiteR = (1F / 255F) * NBTColorSaving.getRed(chessTileEntity.getWhiteTilesColor());
		float whiteG = (1F / 255F) * NBTColorSaving.getGreen(chessTileEntity.getWhiteTilesColor());
		float whiteB = (1F / 255F) * NBTColorSaving.getBlue(chessTileEntity.getWhiteTilesColor());
		float blackR = (1F / 255F) * NBTColorSaving.getRed(chessTileEntity.getBlackTilesColor());
		float blackG = (1F / 255F) * NBTColorSaving.getGreen(chessTileEntity.getBlackTilesColor());
		float blackB = (1F / 255F) * NBTColorSaving.getBlue(chessTileEntity.getBlackTilesColor());
		
		IVertexBuilder builderBoardPlateWhiteTiles = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(PLATE_WHITE_TILES_TEXTURE));
		matrixStackIn.push();
		matrixStackIn.translate(0.0F, -1.35D, 0.0F);
		chessBoardPlateModel.plate.render(matrixStackIn, builderBoardPlateWhiteTiles, combinedLightIn, combinedOverlayIn, whiteR, whiteG, whiteB, 1.0F);
		matrixStackIn.pop();
		IVertexBuilder builderBoardPlateBlackTiles = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(PLATE_BLACK_TILES_TEXTURE));
		matrixStackIn.push();
		matrixStackIn.translate(0.0F, -1.35D, 0.0F);
		chessBoardPlateModel.plate.render(matrixStackIn, builderBoardPlateBlackTiles, combinedLightIn, combinedOverlayIn, blackR, blackG, blackB, 1.0F);
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