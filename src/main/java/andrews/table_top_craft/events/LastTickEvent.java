package andrews.table_top_craft.events;

import javax.swing.Renderer;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.Reference;
import andrews.table_top_craft.util.obj.ObjModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.IRenderTypeBuffer.Impl;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class LastTickEvent
{
	private static ObjModel QUEEN_MODEL = ObjModel.loadModel(new ResourceLocation(Reference.MODID, "models/pieces/queen.obj"));
	private static ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/tile/chess/pieces.png");
	
	@SubscribeEvent
	public static void onRenderWorldLast(RenderWorldLastEvent event)
	{
		for(TileEntity tileEntity : Minecraft.getInstance().world.loadedTileEntityList)
		{
			if(tileEntity instanceof ChessTileEntity)
			{
				BlockPos pos = tileEntity.getPos();
				Vector3d cam = Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView();
		        double camX = cam.x, camY = cam.y, camZ = cam.z;
		        
				GL11.glPushMatrix();
				GL11.glRotatef(Minecraft.getInstance().player.getPitchYaw().x, 1, 0, 0); // Fixes camera rotation.
				GL11.glRotatef(Minecraft.getInstance().player.getPitchYaw().y + 180, 0, 1, 0); // Fixes camera rotation.
				GL11.glTranslated(pos.getX() - camX, pos.getY() - camY, pos.getZ() - camZ);
				
				float CHESS_SCALE = 0.125F;
				
				// Moves the Piece away from the center of the Board, onto the center of a tile
				GL11.glTranslated(CHESS_SCALE / 2D, 0.0D, CHESS_SCALE / 2D);

				
				for(int x = 0; x < 8; x++)
				{
					for(int y = 0; y < 8; y++)
					{
						for(int z = 0; z < 2; z++)
						{
							GL11.glPushMatrix();
							GL11.glTranslated(CHESS_SCALE * x, CHESS_SCALE * z, CHESS_SCALE * y);
							GL11.glTranslated(0.0D, 0.0625D * 12, 0.0D);
							GL11.glScaled(1, -1, -1);
							GL11.glScaled(0.1, 0.1, 0.1);
							Minecraft.getInstance().getRenderManager().textureManager.bindTexture(TEXTURE);
							QUEEN_MODEL.render();
							GL11.glPopMatrix();
						}
					}
				}
				GL11.glPopMatrix();
			}
		}
	}
}
