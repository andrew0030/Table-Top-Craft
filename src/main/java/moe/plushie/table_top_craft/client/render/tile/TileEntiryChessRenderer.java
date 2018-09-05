package moe.plushie.table_top_craft.client.render.tile;

import moe.plushie.table_top_craft.client.model.ChessPawn;
import moe.plushie.table_top_craft.common.lib.Reference;
import moe.plushie.table_top_craft.common.tileentities.TileEntityChess;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntiryChessRenderer extends TileEntitySpecialRenderer<TileEntityChess> {

    private static final ResourceLocation PAWN_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/models/chess_pawn.png");
    private final ChessPawn pawnModel;
    
    public TileEntiryChessRenderer() {
        pawnModel = new ChessPawn();
    }
    
    @Override
    public void render(TileEntityChess te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        float scale = 1F / 16F;
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.75F, y + 1.02F, z - 0.125F);
        GlStateManager.scale(1F, -1F, -1F);
        GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.enableNormalize();
        bindTexture(PAWN_TEXTURE);
        
        GlStateManager.pushMatrix();
        for (int i = 0; i < 8; i++) {
            GlStateManager.translate(0.125F, 0F, 0F);
            pawnModel.render(null, 0, 0, 0, 0, 0, scale);
        }
        GlStateManager.popMatrix();
        
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0, 0.126F);
        for (int i = 0; i < 8; i++) {
            GlStateManager.translate(0.125F, 0F, 0F);
            pawnModel.render(null, 0, 0, 0, 0, 0, scale);
        }
        GlStateManager.popMatrix();
        
        GlStateManager.popMatrix();
    }
}
