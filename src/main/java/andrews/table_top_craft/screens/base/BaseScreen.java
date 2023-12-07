package andrews.table_top_craft.screens.base;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class BaseScreen extends Screen
{
    private final ResourceLocation texture;
    protected int x, y;
    protected final int u, v, textureWidth, textureHeight;

    public BaseScreen(ResourceLocation texture, int u, int v, int textureWidth, int textureHeight, Component component)
    {
        super(component);
        this.texture = texture;
        this.u = u;
        this.v = v;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    public BaseScreen(ResourceLocation texture, int textureWidth, int textureHeight, Component component)
    {
        this(texture, 0, 0, textureWidth, textureHeight, component);
    }

    @Override
    protected void init()
    {
        this.x = (this.width - this.textureWidth + this.offsetX()) / 2;
        this.y = (this.height - this.textureHeight + this.offsetY()) / 2;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        // Renders background gradient
        if(this.renderGradient())
            this.renderBackground(graphics);
        // Renders screen texture
        graphics.blit(this.texture, this.x, this.y, this.u, this.v, this.textureWidth, this.textureHeight);
        // Renders screen contents
        this.renderScreenContents(graphics, mouseX, mouseX, partialTick);
        // Renders the screens widgets
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    public void renderScreenContents(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {}

    /**
     * @return Whether to render a gradient behind the screen
     */
    public boolean renderGradient()
    {
        return false;
    }

    public void drawCenteredString(Component text, int x, int y, int color, boolean dropShadow, GuiGraphics graphics)
    {
        this.font.drawInBatch(text, x - this.font.width(text) / 2, y, color, dropShadow, graphics.pose().last().pose(), graphics.bufferSource(), Font.DisplayMode.NORMAL, 0, 15728880);
    }

    public int offsetX()
    {
        return 0;
    }

    public int offsetY()
    {
        return 0;
    }
}