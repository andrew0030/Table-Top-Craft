package andrews.table_top_craft.screens.chess.buttons.pieces;

import java.util.Arrays;

import andrews.table_top_craft.events.DrawScreenEvent;
import andrews.table_top_craft.screens.chess.menus.ChessPieceSelectionScreen;
import andrews.table_top_craft.tile_entities.render.BufferHelpers;
import andrews.table_top_craft.util.TTCRenderTypes;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;

import andrews.table_top_craft.screens.chess.menus.ChessBoardSettingsScreen;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class ChessBoardPieceSettingsButton extends Button
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
    private final TranslatableComponent buttonText = new TranslatableComponent("gui.table_top_craft.chess.button.pieces");
    private final Font fontRenderer;
    private static ChessTileEntity chessTileEntity;
    private static final int buttonWidth = 24;
    private static final int buttonHeight = 24;
    private int u = 48;
    private int v = 26;

    public ChessBoardPieceSettingsButton(ChessTileEntity tileEntity, int xPos, int yPos)
    {
        super(xPos, yPos, buttonWidth, buttonHeight, new TextComponent(""), (button) -> { handleButtonPress(); });
        this.fontRenderer = Minecraft.getInstance().font;
        chessTileEntity = tileEntity;
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
    {
        this.active = !(Minecraft.getInstance().screen instanceof ChessPieceSelectionScreen);

        this.isHovered = mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height || this.isFocused();

        // Renders the Button
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        poseStack.pushPose();
        RenderSystem.enableBlend();
        this.blit(poseStack, x, y, u, v, width, height);
        RenderSystem.disableBlend();
        poseStack.popPose();

        //TODO render chess piece.

        // This is used to render a tooltip
        if(isHovered)
            Minecraft.getInstance().screen.renderTooltip(poseStack, Arrays.asList(this.buttonText.getVisualOrderText()), x - (15 + this.fontRenderer.width(this.buttonText.getString())), y + 20, this.fontRenderer);
    }

    /**
     * Gets called when the Button gets pressed
     */
    private static void handleButtonPress()
    {
        Minecraft.getInstance().setScreen(new ChessPieceSelectionScreen(chessTileEntity));
    }
}