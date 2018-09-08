package moe.plushie.table_top_craft.client.render.tile;

import moe.plushie.table_top_craft.client.model.ChessKnight;
import moe.plushie.table_top_craft.client.model.ChessPawn;
import moe.plushie.table_top_craft.client.model.ChessRook;
import moe.plushie.table_top_craft.common.lib.ModReference;
import moe.plushie.table_top_craft.common.tileentities.TileEntityChessTable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntiryChessTableRenderer extends TileEntitySpecialRenderer<TileEntityChessTable> {

    private static final ResourceLocation PAWN_TEXTURE_BLACK = new ResourceLocation(ModReference.MOD_ID, "textures/models/black_pieces.png");
    private static final ResourceLocation PAWN_TEXTURE_WHITE = new ResourceLocation(ModReference.MOD_ID, "textures/models/white_pieces.png");
    private final ChessPawn pawnModel;
    private final ChessRook rookModel;
    private final ChessKnight knightModel;
    
    public TileEntiryChessTableRenderer() {
        pawnModel = new ChessPawn();
        rookModel = new ChessRook();
        knightModel = new ChessKnight();
    }
    
    @Override
    public void render(TileEntityChessTable te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        float scale = 1F / 16F;
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.75F, y + 1.02F, z - 0.125F);
        GlStateManager.scale(1F, -1F, -1F);
        GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.enableNormalize();
        bindTexture(PAWN_TEXTURE_BLACK);
        
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
            rookModel.render(null, 0, 0, 0, 0, 0, scale);
        }
        GlStateManager.popMatrix();
        
        GlStateManager.translate(1.25F, 0.0F, -0.5F);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        bindTexture(PAWN_TEXTURE_WHITE);
        
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
            knightModel.render(null, 0, 0, 0, 0, 0, scale);
        }
        GlStateManager.popMatrix();

        GlStateManager.popMatrix();
    }
}
