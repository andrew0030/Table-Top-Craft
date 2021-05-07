package andrews.table_top_craft.events;

import andrews.table_top_craft.util.Reference;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MODID, value = Dist.CLIENT)
public class LastTickEvent
{
//	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/tile/chess/pieces.png");
//	private static final ChessObjModel CHESS_PIECE_MODEL = new ChessObjModel();
//	private static final float CHESS_SCALE = 0.125F;
//	private static final float MC_SCALE = 0.0625F;
//	
//	@SubscribeEvent
//	public static void onRenderWorldLast(RenderWorldLastEvent event)
//	{
//		for(TileEntity tileEntity : Minecraft.getInstance().world.loadedTileEntityList)
//		{
//			if(tileEntity instanceof ChessTileEntity)
//			{
//				ChessTileEntity chessTileEntity = (ChessTileEntity) tileEntity;
//				
//				if(Minecraft.getInstance().player.getDistanceSq((double) tileEntity.getPos().getX(), (double) tileEntity.getPos().getY(), (double) tileEntity.getPos().getZ()) < 4000)
//				{
//					// We make sure the Chess TileEnity has an active Board because otherwise rendering pieces doesn't make sense
//					if(chessTileEntity.getBoard() != null)
//					{
//						Minecraft mc = Minecraft.getInstance();
//						BlockPos pos = tileEntity.getPos();
//						BlockState blockstate = mc.world.getBlockState(pos);
//						Vector3d cam = mc.gameRenderer.getActiveRenderInfo().getProjectedView();
//				        double camX = cam.x, camY = cam.y, camZ = cam.z;
//				        
//						GL11.glPushMatrix();
//						GL11.glRotatef(Minecraft.getInstance().player.getPitchYaw().x * (mc.gameSettings.getPointOfView() == PointOfView.THIRD_PERSON_FRONT ? -1 : 1), 1, 0, 0); // Fixes camera rotation.
//						GL11.glRotatef(Minecraft.getInstance().player.getPitchYaw().y + (mc.gameSettings.getPointOfView() == PointOfView.THIRD_PERSON_FRONT ? 0 : 180), 0, 1, 0); // Fixes camera rotation.
//						GL11.glTranslated(pos.getX() - camX, pos.getY() - camY, pos.getZ() - camZ);
//						
//						Board board = chessTileEntity.getBoard();
//						WhiteChessPlayer whiteChessPlayer = (WhiteChessPlayer) board.getWhiteChessPlayer();
//						BlackChessPlayer blackChessPlayer = (BlackChessPlayer) board.getBlackChessPlayer();
//						boolean isWhiteInCheckmate = whiteChessPlayer.isInCheckMate();
//						boolean isBlackInCheckmate = blackChessPlayer.isInCheckMate();
//						// We get the Direction this ChessBlock is facing
//						Direction facing = Direction.NORTH;
//						if(blockstate.getBlock() instanceof ChessBlock)
//						{
//							facing = blockstate.get(ChessBlock.FACING);
//						}
//						
//						// Moves the Piece to the center of the Board
//						GL11.glTranslated(MC_SCALE * 8, MC_SCALE * 12, MC_SCALE * 8);
//						
//						// Rotates the pieces on the Board depending on the Block rotation
//						switch(facing)
//						{
//						default:
//						case NORTH:
//							GL11.glRotatef(180F, 0, 1, 0);
//							break;
//						case SOUTH:
//							break;
//						case WEST:
//							GL11.glRotatef(270F, 0, 1, 0);
//							break;
//						case EAST:
//							GL11.glRotatef(90F, 0, 1, 0);
//						}
//						
//						// Moves the Piece to the center of a Tile
//						GL11.glTranslated(CHESS_SCALE / 2D, 0.0D, CHESS_SCALE / 2D);
//						
//						// Moves the Piece to the first Tile on the Board
//						GL11.glTranslated(CHESS_SCALE * 3, 0.0D, CHESS_SCALE * 3);
//						
//						// Sets the texture to use for the Model
//						Minecraft.getInstance().getRenderManager().textureManager.bindTexture(TEXTURE);
//						
//						int currentCoordinate = -1;
//						for(int rank = 0; rank < BoardUtils.NUM_TILES_PER_ROW; rank++)
//						{
//							for(int column = 0; column < BoardUtils.NUM_TILES_PER_ROW; column++)
//							{
//								currentCoordinate++;
//								boolean isSelectedPiece = false;
//								
//								// Sets the piece to selected if it is indeed selected
//								if(board.getTile(currentCoordinate) == chessTileEntity.getSourceTile() && chessTileEntity.getHumanMovedPiece() != null)
//									isSelectedPiece = true;
//								
//								// Render all the Pieces
//								if(board.getTile(currentCoordinate).isTileOccupied())
//								{
//									PieceColor pieceColor = board.getTile(currentCoordinate).getPiece().getPieceColor();
//									PieceType pieceType = board.getTile(currentCoordinate).getPiece().getPieceType();
//									
//									GL11.glPushMatrix();
//									// Offsets the Piece that is about to be rendered to the current Tile
//									GL11.glTranslated(CHESS_SCALE * -column, 0.0D, CHESS_SCALE * -rank);
//									
//									float wR = (1F / 255F) * NBTColorSaving.getRed(chessTileEntity.getWhitePiecesColor());
//									float wG = (1F / 255F) * NBTColorSaving.getGreen(chessTileEntity.getWhitePiecesColor());
//									float wB = (1F / 255F) * NBTColorSaving.getBlue(chessTileEntity.getWhitePiecesColor());
//									float bR = (1F / 255F) * NBTColorSaving.getRed(chessTileEntity.getBlackPiecesColor());
//									float bG = (1F / 255F) * NBTColorSaving.getGreen(chessTileEntity.getBlackPiecesColor());
//									float bB = (1F / 255F) * NBTColorSaving.getBlue(chessTileEntity.getBlackPiecesColor());
//									
//									if(isSelectedPiece)
//									{
//										RenderSystem.polygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
//										RenderSystem.lineWidth(2F);
//									}
//									
//									GL11.glPushMatrix();
//									if(isWhiteInCheckmate)
//									{
//										if(pieceColor.isBlack())
//										{
//											GL11.glTranslatef(0.0F, (float) Math.abs(Math.sin((Minecraft.getInstance().player.ticksExisted + getPartialTicks()) / 2.5)) * 0.05F, 0F);
//											GL11.glRotatef((float) Math.cos((Minecraft.getInstance().player.ticksExisted + getPartialTicks()) / 2.5) * 10, 0, 0, 1);
//										}
//									}
//									if(isBlackInCheckmate)
//									{
//										if(pieceColor.isWhite())
//										{
//											GL11.glTranslatef(0.0F, (float) Math.abs(Math.sin((Minecraft.getInstance().player.ticksExisted + getPartialTicks()) / 2.5)) * 0.05F, 0F);
//											GL11.glRotatef((float) Math.cos((Minecraft.getInstance().player.ticksExisted + getPartialTicks()) / 2.5) * 10, 0, 0, 1);
//										}
//									}
//									
//									CHESS_PIECE_MODEL.render(pieceType, pieceColor, wR, wG, wB, bR, bG, bB);
//									GL11.glPopMatrix();
//									
//									if(isSelectedPiece)
//										RenderSystem.polygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
//									GL11.glPopMatrix();
//								}
//							}
//						}
//						
//						// Renders the taken Pieces
////						renderTakenPieces(chessTileEntity.getMoveLog(), chessTileEntity);
//						
//						GL11.glPopMatrix();
//					}
//				}
//			}
//		}
//	}

//	private static void renderTakenPieces(ChessMoveLog moveLog, ChessTileEntity chessTileEntity)
//	{
//		final List<BasePiece> whiteTakenPieces = new ArrayList<>();
//		final List<BasePiece> blackTakenPieces = new ArrayList<>();
//		
//		for(final BaseMove move : moveLog.getMoves())
//		{
//			if(move.isAttack())
//			{
//				final BasePiece takenPiece = move.getAttackedPiece();
//				
//				if(takenPiece.getPieceColor().isWhite())
//				{
//					whiteTakenPieces.add(takenPiece);
//				}
//				else if(takenPiece.getPieceColor().isBlack())
//				{
//					blackTakenPieces.add(takenPiece);
//				}
//				else
//				{
//					throw new RuntimeException("Attemted to get a Piece that had no PieceColor");
//				}
//			}
//		}
//		
//		// Sorts all White Taken Pieces depending on their Value
//		Collections.sort(whiteTakenPieces, new Comparator<BasePiece>()
//		{
//			@Override
//			public int compare(BasePiece piece1, BasePiece piece2)
//			{
//				return Ints.compare(piece2.getPieceValue(), piece1.getPieceValue());
//			}
//		});
//		
//		// Sorts all Black Taken Pieces depending on their Value
//		Collections.sort(blackTakenPieces, new Comparator<BasePiece>()
//		{
//			@Override
//			public int compare(BasePiece piece1, BasePiece piece2)
//			{
//				return Ints.compare(piece2.getPieceValue(), piece1.getPieceValue());
//			}
//		});
//		
//		GL11.glPushMatrix();
//		// Moves the pieces down into the taken Pieces area
//		GL11.glTranslated(CHESS_SCALE * -6.5D, -0.41D, CHESS_SCALE * -0.3D);
//		renderTakenPiecesFigures(chessTileEntity, whiteTakenPieces, true);
//		renderTakenPiecesFigures(chessTileEntity, blackTakenPieces, false);
//		GL11.glPopMatrix();
//	}
//	
//	private static void renderTakenPiecesFigures(ChessTileEntity chessTileEntity, final List<BasePiece> pieceList, final boolean isWhite)
//	{
//		int currentCoordinate = -1;
//		int currentRank = 0;
//		for(final BasePiece piece : pieceList)
//		{
//			if(isWhite)
//			{
//				if(currentCoordinate < 7)
//				{
//					currentCoordinate++;
//				}
//				else
//				{
//					currentCoordinate = 0;
//					currentRank += 1;
//				}
//			}
//			else
//			{	
//				if(currentCoordinate > -9)
//				{
//					currentCoordinate--;
//				}
//				else
//				{
//					currentCoordinate = -2;
//					currentRank -= 1;
//				}
//			}
//			
//			GL11.glPushMatrix();
//			if(!isWhite)
//				GL11.glTranslated((CHESS_SCALE * 0.855D) * 9D, 0.0D, -0.8D);
//			GL11.glTranslated((CHESS_SCALE * 0.855D) * currentCoordinate, 0.0D, CHESS_SCALE * -currentRank);
//			float wR = (1F / 255F) * NBTColorSaving.getRed(chessTileEntity.getWhitePiecesColor());
//			float wG = (1F / 255F) * NBTColorSaving.getGreen(chessTileEntity.getWhitePiecesColor());
//			float wB = (1F / 255F) * NBTColorSaving.getBlue(chessTileEntity.getWhitePiecesColor());
//			float bR = (1F / 255F) * NBTColorSaving.getRed(chessTileEntity.getBlackPiecesColor());
//			float bG = (1F / 255F) * NBTColorSaving.getGreen(chessTileEntity.getBlackPiecesColor());
//			float bB = (1F / 255F) * NBTColorSaving.getBlue(chessTileEntity.getBlackPiecesColor());
//			
//			CHESS_PIECE_MODEL.render(piece.getPieceType(), piece.getPieceColor(), wR, wG, wB, bR, bG, bB);
//			
//			GL11.glPopMatrix();
//		}
//	}
//	
//	private static float getPartialTicks()
//	{
//		return Minecraft.getInstance().isGamePaused() ? Minecraft.getInstance().renderPartialTicksPaused : Minecraft.getInstance().getRenderPartialTicks();
//	}
}
