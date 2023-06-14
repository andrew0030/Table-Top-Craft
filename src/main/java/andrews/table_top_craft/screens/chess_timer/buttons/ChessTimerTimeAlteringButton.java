package andrews.table_top_craft.screens.chess_timer.buttons;

import andrews.table_top_craft.block_entities.ChessTimerBlockEntity;
import andrews.table_top_craft.objects.blocks.ChessTimerBlock;
import andrews.table_top_craft.util.NetworkUtil;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

public class ChessTimerTimeAlteringButton extends Button
{
    private static final ResourceLocation BUTTONS_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/buttons/chess_timer_buttons.png");
    private static final String HOUR = "gui.table_top_craft.chess_timer.buttons.hour";
    private static final String HOURS = "gui.table_top_craft.chess_timer.buttons.hours";
    private static final String MINUTE = "gui.table_top_craft.chess_timer.buttons.minute";
    private static final String MINUTES = "gui.table_top_craft.chess_timer.buttons.minutes";
    private static final String SECOND = "gui.table_top_craft.chess_timer.buttons.second";
    private static final String SECONDS = "gui.table_top_craft.chess_timer.buttons.seconds";
    private final ChessTimerBlockEntity blockEntity;
    private final TimeModifierType type;
    private final TimeCategory category;

    public ChessTimerTimeAlteringButton(ChessTimerBlockEntity blockEntity, int x, int y, TimeModifierType type, TimeCategory category)
    {
        super(x, y, 13, 13, Component.literal(""), Button::onPress, DEFAULT_NARRATION);
        this.blockEntity = blockEntity;
        this.type = type;
        this.category = category;
    }

    @Override
    public void onPress()
    {
        NetworkUtil.adjustChessTimerTime(this.blockEntity.getBlockPos(), this.type.getIdx(), this.category.getIdx());
    }

    @Override
    public void render(PoseStack poseStack, int pMouseX, int pMouseY, float pPartialTick)
    {
        if (this.visible)
        {
            this.isHovered = pMouseX >= this.getX() && pMouseY >= this.getY() && pMouseX < this.getX() + this.width && pMouseY < this.getY() + this.height;
            this.renderWidget(poseStack, pMouseX, pMouseY, pPartialTick);
            if (this.isActive())
            {
                if (this.isHovered() && Minecraft.getInstance().screen != null)
                {
                    // Button Tooltip
                    RenderSystem.disableDepthTest();
                    Component component;
                    switch (this.category) {
                        case LEFT_HOUR, RIGHT_HOUR -> {
                            component = (this.type.equals(TimeModifierType.INCREASE_BIG) || this.type.equals(TimeModifierType.DECREASE_BIG)) ?
                                    Component.translatable(HOURS, ((this.type.equals(TimeModifierType.INCREASE_BIG) ? "+" : "-") + Mth.abs(this.type.getModifier()))) :
                                    Component.translatable(HOUR, ((this.type.equals(TimeModifierType.INCREASE_NORMAL) ? "+" : "-") + Mth.abs(this.type.getModifier())));
                        }
                        case LEFT_MINUTE, RIGHT_MINUTE -> {
                            component = (this.type.equals(TimeModifierType.INCREASE_BIG) || this.type.equals(TimeModifierType.DECREASE_BIG)) ?
                                    Component.translatable(MINUTES, ((this.type.equals(TimeModifierType.INCREASE_BIG) ? "+" : "-") + Mth.abs(this.type.getModifier()))) :
                                    Component.translatable(MINUTE, ((this.type.equals(TimeModifierType.INCREASE_NORMAL) ? "+" : "-") + Mth.abs(this.type.getModifier())));
                        }
                        case LEFT_SECOND, RIGHT_SECOND -> {
                            component = (this.type.equals(TimeModifierType.INCREASE_BIG) || this.type.equals(TimeModifierType.DECREASE_BIG)) ?
                                    Component.translatable(SECONDS, ((this.type.equals(TimeModifierType.INCREASE_BIG) ? "+" : "-") + Mth.abs(this.type.getModifier()))) :
                                    Component.translatable(SECOND, ((this.type.equals(TimeModifierType.INCREASE_NORMAL) ? "+" : "-") + Mth.abs(this.type.getModifier())));
                        }
                        default -> component = Component.literal("");
                    }
                    int posX = this.isHovered() ? pMouseX : this.x;
                    int posY = this.isHovered() ? pMouseY : this.y;
                    Minecraft.getInstance().screen.renderTooltip(poseStack, component, posX, posY);
                    RenderSystem.enableDepthTest();
                }
            }
        }
    }

    @Override
    public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
    {
        this.active = false;
        if(Minecraft.getInstance().level != null) {
            BlockState state = Minecraft.getInstance().level.getBlockState(blockEntity.getBlockPos());
            if(state.getValue(ChessTimerBlock.PRESSED_BUTTON).equals(ChessTimerBlock.PressedButton.NONE))
                this.active = true;
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BUTTONS_TEXTURE);
        GuiComponent.blit(poseStack, this.x, this.y, this.isActive() ? ((isHovered || isFocused()) ? 13 : 0) : 26, this.type.getTexturePos(), 13, 13);
    }

    public enum TimeModifierType
    {
        INCREASE_BIG((byte)0, 10, 0, 30),
        INCREASE_NORMAL((byte)1, 1, 13, 45),
        DECREASE_NORMAL((byte)2, -1, 26, 71),
        DECREASE_BIG((byte)3, -10, 39, 86);

        private final byte idx;
        private final int modifier;
        private final int texturePos;
        private final int offsetY;

        TimeModifierType(byte idx, int modifier, int texturePos, int offsetY)
        {
            this.idx = idx;
            this.modifier = modifier;
            this.texturePos = texturePos;
            this.offsetY = offsetY;
        }

        public byte getIdx()
        {
            return this.idx;
        }

        public int getModifier()
        {
            return this.modifier;
        }

        public int getTexturePos()
        {
            return this.texturePos;
        }

        public int getOffsetY()
        {
            return this.offsetY;
        }
    }

    public enum TimeCategory
    {
        LEFT_HOUR((byte)0, 3600000, 12),
        LEFT_MINUTE((byte)1, 60000, 34),
        LEFT_SECOND((byte)2, 1000, 56),
        RIGHT_HOUR((byte)3, 3600000, 88),
        RIGHT_MINUTE((byte)4, 60000, 110),
        RIGHT_SECOND((byte)5, 1000, 132);

        private final byte idx;
        private final long millis;
        private final int offsetX;

        TimeCategory(byte idx, long millis, int offsetX)
        {
            this.idx = idx;
            this.millis = millis;
            this.offsetX = offsetX;
        }

        public byte getIdx()
        {
            return this.idx;
        }

        public long getMillis()
        {
            return this.millis;
        }

        public int getOffsetX()
        {
            return this.offsetX;
        }
    }
}