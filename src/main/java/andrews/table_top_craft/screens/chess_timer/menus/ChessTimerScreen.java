package andrews.table_top_craft.screens.chess_timer.menus;


import andrews.table_top_craft.block_entities.ChessTimerBlockEntity;
import andrews.table_top_craft.screens.base.BaseScreen;
import andrews.table_top_craft.screens.chess_timer.buttons.ChessTimerPauseButton;
import andrews.table_top_craft.screens.chess_timer.buttons.ChessTimerResetButton;
import andrews.table_top_craft.screens.chess_timer.buttons.ChessTimerTimeAlteringButton;
import andrews.table_top_craft.screens.chess_timer.buttons.ChessTimerTimeAlteringButton.TimeCategory;
import andrews.table_top_craft.screens.chess_timer.buttons.ChessTimerTimeAlteringButton.TimeModifierType;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ChessTimerScreen extends BaseScreen
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/chess_timer_menu.png");
    private static final Component TITLE = Component.translatable("gui.table_top_craft.chess_timer.title");
    private static final Component LEFT_TIMER = Component.translatable("gui.table_top_craft.chess_timer.left_timer");
    private static final Component RIGHT_TIMER = Component.translatable("gui.table_top_craft.chess_timer.right_timer");
    private final ChessTimerBlockEntity blockEntity;

    public ChessTimerScreen(ChessTimerBlockEntity blockEntity)
    {
        super(TEXTURE, 157, 123, TITLE);
        this.blockEntity = blockEntity;
    }

    @Override
    protected void init()
    {
        super.init();
        // Timer Buttons
        for(TimeCategory category : TimeCategory.values())
            for(TimeModifierType type : TimeModifierType.values())
                this.addRenderableWidget(new ChessTimerTimeAlteringButton(this.blockEntity, this.x + category.getOffsetX(), this.y + type.getOffsetY(), type, category));
        // Reset Time Button
        this.addRenderableWidget(new ChessTimerResetButton(this.blockEntity, this.x + 7, this.y + 102));
        // Pause Time Button
        this.addRenderableWidget(new ChessTimerPauseButton(this.blockEntity, this.x + 81, this.y + 102));
    }

    @Override
    public void renderScreenContents(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        // Title
        this.drawCenteredString(TITLE, this.width / 2, this.y + 6, 4210752, false, graphics);
        // Left and Right Timer Title's
        this.drawCenteredString(LEFT_TIMER, this.x + 41, this.y + 19, 4210752, false, graphics);
        this.drawCenteredString(RIGHT_TIMER, this.x + this.textureWidth - 41, this.y + 19, 4210752, false, graphics);
        // Left Timer
        graphics.drawString(this.font, ((this.blockEntity.getLeftTimer() / 1000) / 3600 > 9 ? "" : "0") + (this.blockEntity.getLeftTimer() / 1000) / 3600, this.x + 13, this.y + 61, 0xffffff, false);
        graphics.drawString(this.font, (((this.blockEntity.getLeftTimer() / 1000) / 60) % 60 > 9 ? "" : "0") + ((this.blockEntity.getLeftTimer() / 1000) / 60) % 60, this.x + 35, this.y + 61, 0xffffff, false);
        graphics.drawString(this.font, ((this.blockEntity.getLeftTimer() / 1000) % 60 > 9 ? "" : "0") + (this.blockEntity.getLeftTimer() / 1000) % 60, this.x + 57, this.y + 61, 0xffffff, false);
        // Right Timer
        graphics.drawString(this.font, ((this.blockEntity.getRightTimer() / 1000) / 3600 > 9 ? "" : "0") + (this.blockEntity.getRightTimer() / 1000) / 3600, this.x + 89, this.y + 61, 0xffffff, false);
        graphics.drawString(this.font, (((this.blockEntity.getRightTimer() / 1000) / 60) % 60 > 9 ? "" : "0") + ((this.blockEntity.getRightTimer() / 1000) / 60) % 60, this.x + 111, this.y + 61, 0xffffff, false);
        graphics.drawString(this.font, ((this.blockEntity.getRightTimer() / 1000) % 60 > 9 ? "" : "0") + (this.blockEntity.getRightTimer() / 1000) % 60, this.x + 133, this.y + 61, 0xffffff, false);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        super.keyPressed(keyCode, scanCode, modifiers);
        if(this.getMinecraft().options.keyInventory.isActiveAndMatches(InputConstants.getKey(keyCode, scanCode)))
            this.onClose();
        return true;
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }

    /**
     * Used to open this Gui, because class loading is a little child that screams if it does not like you
     */
    public static void open(ChessTimerBlockEntity blockEntity)
    {
        Minecraft.getInstance().setScreen(new ChessTimerScreen(blockEntity));
    }
}