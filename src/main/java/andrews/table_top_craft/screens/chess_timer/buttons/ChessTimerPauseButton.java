package andrews.table_top_craft.screens.chess_timer.buttons;

import andrews.table_top_craft.block_entities.ChessTimerBlockEntity;
import andrews.table_top_craft.objects.blocks.ChessTimerBlock;
import andrews.table_top_craft.util.NetworkUtil;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.level.block.state.BlockState;

public class ChessTimerPauseButton extends Button
{
    private static final ResourceLocation BUTTONS_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/buttons/chess_timer_buttons.png");
    private static final Component TEXT = Component.translatable("gui.table_top_craft.chess_timer.buttons.pause");
    private final ChessTimerBlockEntity blockEntity;

    public ChessTimerPauseButton(ChessTimerBlockEntity blockEntity, int x, int y)
    {
        super(x, y, 70, 14, Component.literal(""), Button::onPress, DEFAULT_NARRATION);
        this.blockEntity = blockEntity;
    }

    @Override
    public void onPress()
    {
        NetworkUtil.pauseChessTimerTime(this.blockEntity.getBlockPos());
    }

    @Override
    public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
    {
        this.active = false;
        if(Minecraft.getInstance().level != null) {
            BlockState state = Minecraft.getInstance().level.getBlockState(blockEntity.getBlockPos());
            if(!state.getValue(ChessTimerBlock.PRESSED_BUTTON).equals(ChessTimerBlock.PressedButton.NONE))
                this.active = true;
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BUTTONS_TEXTURE);
        GuiComponent.blit(poseStack, this.x, this.y, this.isActive() ? ((isHovered || isFocused()) ? 70 : 0) : 140, 52, this.width, this.height);
        drawCenteredNoShadow(poseStack, TEXT, this.x + this.width / 2, this.y + 3, 0x000000);
    }

    public static void drawCenteredNoShadow(PoseStack poseStack, Component text, int x, int y, int color)
    {
        FormattedCharSequence formattedcharsequence = text.getVisualOrderText();
        Font font = Minecraft.getInstance().font;
        font.draw(poseStack, formattedcharsequence, (float)(x - font.width(formattedcharsequence) / 2), (float)y, color);
    }
}