package andrews.table_top_craft.screens.base.buttons;

import andrews.table_top_craft.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public abstract class BaseChessPieceFigureCreativeButton extends AbstractButton
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
    private final int u,v;

    public BaseChessPieceFigureCreativeButton(int pX, int pY, int u, int v, Component message)
    {
        super(pX, pY, 26, 13, message);
        this.u = u;
        this.v = v;
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        this.active = this.visible = Minecraft.getInstance().player.isCreative();
        int u = (this.isHoveredOrFocused() ? this.u + 26 : this.u);
        int v = this.v;
        // Renders the Button
        graphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        graphics.blit(TEXTURE, this.x, this.y, u, v, this.width, this.height);
        // Renders the Tooltip
        if(this.isHovered())
        {
            Font font = Minecraft.getInstance().font;
            int xPos = this.x - 8 + (this.v == 148 ? this.width : -(8 + font.width(this.getMessage())));
            graphics.renderTooltip(font, this.getMessage(), xPos, this.y + 14);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output)
    {
        this.defaultButtonNarrationText(output);
    }
}
