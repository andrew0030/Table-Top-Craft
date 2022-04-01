package andrews.table_top_craft.screens.piece_figure.menus;

import andrews.table_top_craft.screens.chess.buttons.settings.ChessBoardSettingsButton;
import andrews.table_top_craft.screens.piece_figure.buttons.ChessPieceFigureRotateButton;
import andrews.table_top_craft.tile_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class ChessPieceFigureSettingsScreen extends Screen
{
    private static final ResourceLocation MENU_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/chess_menu.png");
    private final String chessPieceFigureSettingsText = new TranslatableComponent("gui.table_top_craft.piece_figure.piece_settings").getString();
    private final ChessPieceFigureBlockEntity chessPieceFigureBlockEntity;
    private final int xSize = 177;
    private final int ySize = 198;

    public ChessPieceFigureSettingsScreen(ChessPieceFigureBlockEntity chessPieceFigureBlockEntity)
    {
        super(new TextComponent(""));
        this.chessPieceFigureBlockEntity = chessPieceFigureBlockEntity;
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }

    @Override
    protected void init()
    {
        super.init();
        // Values to calculate the relative position
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        // The Buttons in the Gui Menu
        this.addRenderableWidget(new ChessPieceFigureRotateButton(this.chessPieceFigureBlockEntity, x + 24, y + 32));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
    {
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, MENU_TEXTURE);
        this.blit(poseStack, x, y, 0, 0, this.xSize, this.ySize);

        // The screen text
        this.font.draw(poseStack, this.chessPieceFigureSettingsText, ((this.width / 2) - (this.font.width(this.chessPieceFigureSettingsText) / 2)), y + 6, 4210752);

        // Renders the Buttons we added in init
        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        super.keyPressed(keyCode, scanCode, modifiers);
        InputConstants.Key mouseKey = InputConstants.getKey(keyCode, scanCode);
        if(this.minecraft.options.keyInventory.isActiveAndMatches(mouseKey))
            this.onClose();
        return true;
    }

    /**
     * Used to open this Gui, because class loading is a little child that screams if it does not like you
     * @param chessPieceFigureBlockEntity The ChessPieceFigureBlockEntity
     */
    public static void open(ChessPieceFigureBlockEntity chessPieceFigureBlockEntity)
    {
        Minecraft.getInstance().setScreen(new ChessPieceFigureSettingsScreen(chessPieceFigureBlockEntity));
    }
}