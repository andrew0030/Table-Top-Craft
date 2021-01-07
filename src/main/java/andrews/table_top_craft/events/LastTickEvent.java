package andrews.table_top_craft.events;

import javax.swing.Renderer;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.Reference;
import andrews.table_top_craft.util.obj.ObjModel;
import andrews.table_top_craft.util.obj.models.ChessObjQueenModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.IRenderTypeBuffer.Impl;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.World;
import net.minecraft.world.lighting.LightDataMap;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class LastTickEvent
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/tile/chess/pieces.png");
	private static ChessObjQueenModel QUEEN_MODEL = new ChessObjQueenModel();
	
	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public static void onRenderWorldLast(RenderWorldLastEvent event)
	{
		for(TileEntity tileEntity : Minecraft.getInstance().world.loadedTileEntityList)
		{
			if(tileEntity instanceof ChessTileEntity)
			{
				Minecraft mc = Minecraft.getInstance();
				BlockPos pos = tileEntity.getPos();
				Vector3d cam = mc.gameRenderer.getActiveRenderInfo().getProjectedView();
		        double camX = cam.x, camY = cam.y, camZ = cam.z;
		        
				GL11.glPushMatrix();
				GL11.glRotatef(Minecraft.getInstance().player.getPitchYaw().x * (mc.gameSettings.getPointOfView() == PointOfView.THIRD_PERSON_FRONT ? -1 : 1), 1, 0, 0); // Fixes camera rotation.
				GL11.glRotatef(Minecraft.getInstance().player.getPitchYaw().y + (mc.gameSettings.getPointOfView() == PointOfView.THIRD_PERSON_FRONT ? 0 : 180), 0, 1, 0); // Fixes camera rotation.
				GL11.glTranslated(pos.getX() - camX, pos.getY() - camY, pos.getZ() - camZ);
				
				float CHESS_SCALE = 0.125F;
				
				// Moves the Piece away from the center of the Board, onto the center of a tile
				GL11.glTranslated(CHESS_SCALE / 2D, 0.0D, CHESS_SCALE / 2D);
				
				// Sets the texture to use for the Model
				Minecraft.getInstance().getRenderManager().textureManager.bindTexture(TEXTURE);
				
				for(int x = 0; x < 8; x++)
				{
					for(int y = 0; y < 8; y++)
					{
						for(int z = 0; z < 1; z++)
						{
							GL11.glPushMatrix();
							GL11.glTranslated(CHESS_SCALE * x, CHESS_SCALE * z, CHESS_SCALE * y);
							GL11.glTranslated(0.0D, 0.0625D * 12, 0.0D);
							
							// Lighting Fix
					        RenderSystem.enableRescaleNormal();
				            GL11.glEnable(GL11.GL_LIGHTING);
				            
				            // Color and or Lighting Fix
				            RenderSystem.enableLighting();
				            RenderSystem.enableColorMaterial();
				            
				            // Adjust Color
				            RenderSystem.color3f((1F / 255F) * 180F, (1F / 255F) * 170F, (1F / 255F) * 161F);
				            

				            
							QUEEN_MODEL.render();
							
							GL11.glDisable(GL11.GL_LIGHTING);
							
							GL11.glPopMatrix();
						}
					}
				}
				GL11.glPopMatrix();
			}
		}
	}
}
