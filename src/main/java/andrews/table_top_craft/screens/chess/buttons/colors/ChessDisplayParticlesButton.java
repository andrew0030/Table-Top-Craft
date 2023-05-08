package andrews.table_top_craft.screens.chess.buttons.colors;

import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.NetworkUtil;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ChessDisplayParticlesButton extends Button
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
    private final ChessTileEntity blockEntity;

    public ChessDisplayParticlesButton(ChessTileEntity blockEntity, int xPos, int yPos)
    {
        super(xPos, yPos, 13, 13, Component.literal(""), Button::onPress, DEFAULT_NARRATION);
        this.blockEntity = blockEntity;
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
    {
        int u = blockEntity.getDisplayParticles() ? 13 : 0;
        if(this.isHovered || this.isFocused())
            u += 26;
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        this.blit(poseStack, x, y, u, 13, width, height);
    }

    @Override
    public void onPress()
    {
        NetworkUtil.toggleChessVisuals(this.blockEntity.getBlockPos(), (byte) 1);
    }
}