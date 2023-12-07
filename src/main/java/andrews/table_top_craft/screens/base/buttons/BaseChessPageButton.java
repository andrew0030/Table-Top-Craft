package andrews.table_top_craft.screens.base.buttons;

import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public abstract class BaseChessPageButton extends AbstractButton
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
    private final int u, v;

    public BaseChessPageButton(int pX, int pY, int u, int v, Component message)
    {
        super(pX, pY, 24, 24, message);
        this.u = u;
        this.v = v;
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
		// Renders the Button
		graphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.enableBlend();
		graphics.blit(TEXTURE, this.x, this.y, this.u, this.v, this.width, this.height);
		RenderSystem.disableBlend();
		// Renders Tooltip next to the Button
		if(this.isHoveredOrFocused())
            graphics.renderTooltip(Minecraft.getInstance().font, this.getMessage(), this.x - (15 + Minecraft.getInstance().font.width(this.getMessage().getString())), this.y + 20);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output)
    {
        this.defaultButtonNarrationText(output);
    }
}