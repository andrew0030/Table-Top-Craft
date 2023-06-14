package andrews.table_top_craft.screens.chess_timer.menus;


import andrews.table_top_craft.block_entities.ChessTimerBlockEntity;
import andrews.table_top_craft.screens.chess_timer.buttons.ChessTimerPauseButton;
import andrews.table_top_craft.screens.chess_timer.buttons.ChessTimerResetButton;
import andrews.table_top_craft.screens.chess_timer.buttons.ChessTimerTimeAlteringButton;
import andrews.table_top_craft.screens.chess_timer.buttons.ChessTimerTimeAlteringButton.TimeCategory;
import andrews.table_top_craft.screens.chess_timer.buttons.ChessTimerTimeAlteringButton.TimeModifierType;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

public class ChessTimerScreen extends Screen
{
    private static final ResourceLocation MENU_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/chess_timer_menu.png");
    private static final Component TITLE = Component.translatable("gui.table_top_craft.chess_timer.title");
    private static final Component LEFT_TIMER = Component.translatable("gui.table_top_craft.chess_timer.left_timer");
    private static final Component RIGHT_TIMER = Component.translatable("gui.table_top_craft.chess_timer.right_timer");
    private static final int X_SIZE = 157;
    private static final int Y_SIZE = 123;
    private final ChessTimerBlockEntity blockEntity;
    private int xPos;
    private int yPos;


    public ChessTimerScreen(ChessTimerBlockEntity blockEntity)
    {
        super(TITLE);
        this.blockEntity = blockEntity;
    }

    @Override
    protected void init()
    {
        this.xPos = (this.width - X_SIZE) / 2;
        this.yPos = (this.height - Y_SIZE) / 2;
        // Timer Buttons
        for(TimeCategory category : TimeCategory.values())
            for(TimeModifierType type : TimeModifierType.values())
                this.addRenderableWidget(new ChessTimerTimeAlteringButton(this.blockEntity, xPos + category.getOffsetX(), yPos + type.getOffsetY(), type, category));
        // Reset Time Button
        this.addRenderableWidget(new ChessTimerResetButton(this.blockEntity, xPos + 7, yPos + 102));
        this.addRenderableWidget(new ChessTimerPauseButton(this.blockEntity, xPos + 81, yPos + 102));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
    {
        // Background
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, MENU_TEXTURE);
        GuiComponent.blit(poseStack, this.xPos, this.yPos, 0, 0, X_SIZE, Y_SIZE);

        // Title
        drawCenteredNoShadow(poseStack, TITLE, this.width / 2, this.yPos + 6, 4210752);
        // Left and Right Timer Title's
        drawCenteredNoShadow(poseStack, LEFT_TIMER, this.xPos + 41, this.yPos + 19, 4210752);
        drawCenteredNoShadow(poseStack, RIGHT_TIMER, this.xPos + X_SIZE - 41, this.yPos + 19, 4210752);

        // Left Timer
        this.font.draw(poseStack, ((this.blockEntity.getLeftTimer() / 1000) / 3600 > 9 ? "" : "0") + (this.blockEntity.getLeftTimer() / 1000) / 3600, this.xPos + 13, this.yPos + 61, 0xffffff);
        this.font.draw(poseStack, (((this.blockEntity.getLeftTimer() / 1000) / 60) % 60 > 9 ? "" : "0") + ((this.blockEntity.getLeftTimer() / 1000) / 60) % 60, this.xPos + 35, this.yPos + 61, 0xffffff);
        this.font.draw(poseStack, ((this.blockEntity.getLeftTimer() / 1000) % 60 > 9 ? "" : "0") + (this.blockEntity.getLeftTimer() / 1000) % 60, this.xPos + 57, this.yPos + 61, 0xffffff);


//        long clientRightTimer = Math.max((blockEntity.rightTimerCache + blockEntity.getRightTimer()) - System.currentTimeMillis(), 0);


        // Right Timer
        this.font.draw(poseStack, ((this.blockEntity.getRightTimer() / 1000) / 3600 > 9 ? "" : "0") + (this.blockEntity.getRightTimer() / 1000) / 3600, this.xPos + 89, this.yPos + 61, 0xffffff);
        this.font.draw(poseStack, (((this.blockEntity.getRightTimer() / 1000) / 60) % 60 > 9 ? "" : "0") + ((this.blockEntity.getRightTimer() / 1000) / 60) % 60, this.xPos + 111, this.yPos + 61, 0xffffff);
        this.font.draw(poseStack, ((this.blockEntity.getRightTimer() / 1000) % 60 > 9 ? "" : "0") + (this.blockEntity.getRightTimer() / 1000) % 60, this.xPos + 133, this.yPos + 61, 0xffffff);

        // Renders Buttons added in init()
        super.render(poseStack, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
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

    public static void drawCenteredNoShadow(PoseStack poseStack, Component text, int x, int y, int color)
    {
        FormattedCharSequence formattedcharsequence = text.getVisualOrderText();
        Font font = Minecraft.getInstance().font;
        font.draw(poseStack, formattedcharsequence, (float)(x - font.width(formattedcharsequence) / 2), (float)y, color);
    }

    /**
     * Used to open this Gui, because class loading is a little child that screams if it does not like you
     */
    public static void open(ChessTimerBlockEntity blockEntity)
    {
        Minecraft.getInstance().setScreen(new ChessTimerScreen(blockEntity));
    }
}