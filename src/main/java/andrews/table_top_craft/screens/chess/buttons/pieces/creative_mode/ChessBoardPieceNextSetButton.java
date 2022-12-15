package andrews.table_top_craft.screens.chess.buttons.pieces.creative_mode;

import andrews.table_top_craft.tile_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.util.NetworkUtil;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ChessBoardPieceNextSetButton extends Button
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
    private final Component buttonText = Component.translatable("gui.table_top_craft.chess_piece_figure.next_set");
    private final Font fontRenderer;
    private static ChessPieceFigureBlockEntity chessPieceFigureBlockEntity;
    private static final int buttonWidth = 26;
    private static final int buttonHeight = 13;
    private int u = 0;
    private int v = 148;

    public ChessBoardPieceNextSetButton(ChessPieceFigureBlockEntity blockEntity, int xPos, int yPos)
    {
        super(xPos, yPos, buttonWidth, buttonHeight, Component.literal(""), (button) -> { handleButtonPress(); }, DEFAULT_NARRATION);
        this.fontRenderer = Minecraft.getInstance().font;
        chessPieceFigureBlockEntity = blockEntity;
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
    {
        this.active = this.visible = Minecraft.getInstance().player.isCreative();

        this.isHovered = mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height || this.isFocused();

        this.u = 0;
        if (this.isHovered)
            this.u += 26;

        // Renders the Button
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        poseStack.pushPose();
        RenderSystem.enableBlend();
        this.blit(poseStack, x, y, u, v, width, height);
        RenderSystem.disableBlend();
        poseStack.popPose();

        // This is used to render a tooltip
        if(isHovered)
            Minecraft.getInstance().screen.renderTooltip(poseStack, buttonText, x - 8 + width, y + 14);
    }

    /**
     * Gets called when the Button gets pressed
     */
    private static void handleButtonPress()
    {
        NetworkUtil.changePieceSet(chessPieceFigureBlockEntity.getBlockPos(), (byte) 1);
    }
}