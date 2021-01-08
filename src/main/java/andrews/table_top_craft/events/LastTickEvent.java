package andrews.table_top_craft.events;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;

import andrews.table_top_craft.game_logic.chess.PieceColor;
import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.BoardUtils;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece.PieceType;
import andrews.table_top_craft.objects.blocks.ChessBlock;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.NBTColorSaving;
import andrews.table_top_craft.util.Reference;
import andrews.table_top_craft.util.obj.models.ChessObjModel;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MODID, value = Dist.CLIENT)
public class LastTickEvent
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/tile/chess/pieces.png");
	private static final ChessObjModel CHESS_PIECE_MODEL = new ChessObjModel();
	private static final float CHESS_SCALE = 0.125F;
	private static final float MC_SCALE = 0.0625F;
	
	@SubscribeEvent
	public static void onRenderWorldLast(RenderWorldLastEvent event)
	{
		for(TileEntity tileEntity : Minecraft.getInstance().world.loadedTileEntityList)
		{
			if(tileEntity instanceof ChessTileEntity)
			{
				ChessTileEntity chessTileEntity = (ChessTileEntity) tileEntity;
				// We make sure the Chess TileEnity has an active Board because otherwise rendering pieces doesn't make sense
				if(chessTileEntity.getBoard() != null)
				{
					Minecraft mc = Minecraft.getInstance();
					BlockPos pos = tileEntity.getPos();
					BlockState blockstate = mc.world.getBlockState(pos);
					Vector3d cam = mc.gameRenderer.getActiveRenderInfo().getProjectedView();
			        double camX = cam.x, camY = cam.y, camZ = cam.z;
			        
					GL11.glPushMatrix();
					GL11.glRotatef(Minecraft.getInstance().player.getPitchYaw().x * (mc.gameSettings.getPointOfView() == PointOfView.THIRD_PERSON_FRONT ? -1 : 1), 1, 0, 0); // Fixes camera rotation.
					GL11.glRotatef(Minecraft.getInstance().player.getPitchYaw().y + (mc.gameSettings.getPointOfView() == PointOfView.THIRD_PERSON_FRONT ? 0 : 180), 0, 1, 0); // Fixes camera rotation.
					GL11.glTranslated(pos.getX() - camX, pos.getY() - camY, pos.getZ() - camZ);
					
					Board board = chessTileEntity.getBoard();
					// We get the Direction this ChessBlock is facing
					Direction facing = Direction.NORTH;
					if(blockstate.getBlock() instanceof ChessBlock)
					{
						facing = blockstate.get(ChessBlock.FACING);
					}
					
					// Moves the Piece to the center of the Board
					GL11.glTranslated(MC_SCALE * 8, MC_SCALE * 12, MC_SCALE * 8);
					
					// Rotates the pieces on the Board depending on the Block rotation
					switch(facing)
					{
					default:
					case NORTH:
						GL11.glRotatef(180F, 0, 1, 0);
						break;
					case SOUTH:
						break;
					case WEST:
						GL11.glRotatef(270F, 0, 1, 0);
						break;
					case EAST:
						GL11.glRotatef(90F, 0, 1, 0);
					}
					
					// Moves the Piece to the center of a Tile
					GL11.glTranslated(CHESS_SCALE / 2D, 0.0D, CHESS_SCALE / 2D);
					
					// Moves the Piece to the first Tile on the Board
					GL11.glTranslated(CHESS_SCALE * 3, 0.0D, CHESS_SCALE * 3);
					
					// Sets the texture to use for the Model
					Minecraft.getInstance().getRenderManager().textureManager.bindTexture(TEXTURE);
					
					int currentCoordinate = -1;
					for(int rank = 0; rank < BoardUtils.NUM_TILES_PER_ROW; rank++)
					{
						for(int column = 0; column < BoardUtils.NUM_TILES_PER_ROW; column++)
						{
							currentCoordinate++;
							boolean isSelectedPiece = false;
							
							// Sets the piece to selected if it is indeed selected
							if(board.getTile(currentCoordinate) == chessTileEntity.getSourceTile() && chessTileEntity.getHumanMovedPiece() != null)
								isSelectedPiece = true;
							
							// Render all the Pieces
							if(board.getTile(currentCoordinate).isTileOccupied())
							{
								PieceColor pieceColor = board.getTile(currentCoordinate).getPiece().getPieceColor();
								PieceType pieceType = board.getTile(currentCoordinate).getPiece().getPieceType();
								
								GL11.glPushMatrix();
								// Offsets the Piece that is about to be rendered to the current Tile
								GL11.glTranslated(CHESS_SCALE * -column, 0.0D, CHESS_SCALE * -rank);
								
								float wR = (1F / 255F) * NBTColorSaving.getRed(chessTileEntity.getWhitePiecesColor());
								float wG = (1F / 255F) * NBTColorSaving.getGreen(chessTileEntity.getWhitePiecesColor());
								float wB = (1F / 255F) * NBTColorSaving.getBlue(chessTileEntity.getWhitePiecesColor());
								float bR = (1F / 255F) * NBTColorSaving.getRed(chessTileEntity.getBlackPiecesColor());
								float bG = (1F / 255F) * NBTColorSaving.getGreen(chessTileEntity.getBlackPiecesColor());
								float bB = (1F / 255F) * NBTColorSaving.getBlue(chessTileEntity.getBlackPiecesColor());
								
								if(isSelectedPiece)
								{
									RenderSystem.polygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
									RenderSystem.lineWidth(2F);
								}
								CHESS_PIECE_MODEL.render(pieceType, pieceColor, wR, wG, wB, bR, bG, bB);
								if(isSelectedPiece)
									RenderSystem.polygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
								GL11.glPopMatrix();
							}
						}
					}
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					GL11.glPopMatrix();
				}
				
//				// Moves the Piece away from the center of the Board, onto the center of a tile
//				GL11.glTranslated(CHESS_SCALE / 2D, 0.0D, CHESS_SCALE / 2D);
//				
//				// Sets the texture to use for the Model
//				Minecraft.getInstance().getRenderManager().textureManager.bindTexture(TEXTURE);
//				
//				for(int x = 0; x < 8; x++)
//				{
//					for(int y = 0; y < 8; y++)
//					{
//						for(int z = 0; z < 1; z++)
//						{
//							GL11.glPushMatrix();
//							GL11.glTranslated(CHESS_SCALE * x, CHESS_SCALE * z, CHESS_SCALE * y);
//							GL11.glTranslated(0.0D, 0.0625D * 12, 0.0D);
//							
//							// Lighting Fix
//					        RenderSystem.enableRescaleNormal();
//				            GL11.glEnable(GL11.GL_LIGHTING);
//				            
//				            // Color and or Lighting Fix
//				            RenderSystem.enableLighting();
//				            RenderSystem.enableColorMaterial();
//				            
//				            // Adjust Color
//				            RenderSystem.color3f((1F / 255F) * 180F, (1F / 255F) * 170F, (1F / 255F) * 161F);
//				            
//
//				            
//							QUEEN_MODEL.render();
//							
//							GL11.glDisable(GL11.GL_LIGHTING);
//							
//							GL11.glPopMatrix();
//						}
//					}
//				}
//				GL11.glPopMatrix();
			}
		}
	}
}
