package andrews.table_top_craft.screens.base.buttons;

import andrews.table_top_craft.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public abstract class BaseTextButtonSmall extends AbstractButton
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");

    public BaseTextButtonSmall(int pX, int pY, Component message)
    {
        super(pX, pY, 82, 13, message);
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        this.active = this.setActive();
        int u = this.isActive() ? (isHoveredOrFocused() ? 82 : 0) : 164;
        int v = 0;
        // Renders the Button
        graphics.setColor(1.0f, 1.0f, 1.0f, this.alpha);
        graphics.blit(TEXTURE, this.x, this.y, u, v, this.width, this.height);
        graphics.drawString(Minecraft.getInstance().font, this.getMessage(), this.x + ((this.width / 2) - (Minecraft.getInstance().font.width(this.getMessage()) / 2)), y + 3, 0x000000, false);
    }

    public boolean setActive()
    {
        return true;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output)
    {
        this.defaultButtonNarrationText(output);
    }
}