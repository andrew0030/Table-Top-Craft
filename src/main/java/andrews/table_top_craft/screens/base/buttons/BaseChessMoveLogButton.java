package andrews.table_top_craft.screens.base.buttons;

import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;


public abstract class BaseChessMoveLogButton extends AbstractButton
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
    private final int u,v;

    public BaseChessMoveLogButton(int pX, int pY, int u, int v)
    {
        super(pX, pY, 12, 23, Component.literal(""));
        this.u = u;
        this.v = v;
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        int u = (this.isHoveredOrFocused() ? this.u + 12 : this.u);
        if(!this.isActive())
            u = this.u + 24;
        int v = this.v;

		// Renders the Button
		graphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.enableBlend();
		graphics.blit(TEXTURE, this.x, this.y, u, v, this.width, this.height);
		RenderSystem.disableBlend();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output)
    {
        this.defaultButtonNarrationText(output);
    }
}