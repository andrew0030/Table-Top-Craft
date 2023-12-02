package andrews.table_top_craft.screens.base.buttons;

import andrews.table_top_craft.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public abstract class BaseChessTimerTextButton extends AbstractButton
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/buttons/chess_timer_buttons.png");

    public BaseChessTimerTextButton(int pX, int pY, Component message)
    {
        super(pX, pY, 70, 14, message);
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        this.active = this.setActive();
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        graphics.blit(TEXTURE, this.x, this.y, this.isActive() ? (this.isHoveredOrFocused() ? 70 : 0) : 140, 52, this.width, this.height);
        graphics.drawString(Minecraft.getInstance().font, this.getMessage(), this.x + ((this.width / 2) - (Minecraft.getInstance().font.width(this.getMessage()) / 2)), y + 3, 0x000000, false);
    }

    public boolean setActive()
    {
        return false;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output)
    {
        this.defaultButtonNarrationText(output);
    }
}