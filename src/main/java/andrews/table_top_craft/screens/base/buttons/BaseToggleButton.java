package andrews.table_top_craft.screens.base.buttons;

import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public abstract class BaseToggleButton extends AbstractButton
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");

    public BaseToggleButton(int pX, int pY)
    {
        super(pX, pY, 13, 13, Component.literal(""));
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        int u = (this.isToggled() ? 13 : 0) + (this.isHoveredOrFocused() ? 26 : 0);
        int v = 13;
        // Renders the Button
        graphics.setColor(1.0f, 1.0f, 1.0f, this.alpha);
        RenderSystem.enableBlend();
        graphics.blit(TEXTURE, this.getX(), this.getY(), u, v, this.getWidth(), this.getHeight());
        RenderSystem.disableBlend();
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output)
    {
        this.defaultButtonNarrationText(output);
    }

    public abstract boolean isToggled();
}