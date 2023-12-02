package andrews.table_top_craft.screens.piece_figure.menus;

import andrews.table_top_craft.block_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.registry.TTCBlocks;
import andrews.table_top_craft.screens.base.BaseScreen;
import andrews.table_top_craft.screens.piece_figure.buttons.creative_mode.ChessPieceFigureNextSetButton;
import andrews.table_top_craft.screens.piece_figure.buttons.creative_mode.ChessPieceFigureNextTypeButton;
import andrews.table_top_craft.screens.piece_figure.buttons.creative_mode.ChessPieceFigurePreviousSetButton;
import andrews.table_top_craft.screens.piece_figure.buttons.creative_mode.ChessPieceFigurePreviousTypeButton;
import andrews.table_top_craft.screens.chess.sliders.ChessBlueColorSlider;
import andrews.table_top_craft.screens.chess.sliders.ChessGreenColorSlider;
import andrews.table_top_craft.screens.chess.sliders.ChessRedColorSlider;
import andrews.table_top_craft.screens.piece_figure.buttons.ChessPieceFigureConfirmColorButton;
import andrews.table_top_craft.screens.piece_figure.buttons.ChessPieceFigureConfirmScaleButton;
import andrews.table_top_craft.screens.piece_figure.buttons.ChessPieceFigureResetColorButton;
import andrews.table_top_craft.screens.piece_figure.buttons.ChessPieceFigureRotateButton;
import andrews.table_top_craft.screens.piece_figure.sliders.ChessPieceFigureScaleSlider;
import andrews.table_top_craft.screens.piece_figure.util.ColorPickerToggleButton;
import andrews.table_top_craft.screens.piece_figure.util.IColorPicker;
import andrews.table_top_craft.screens.piece_figure.util.SaturationSlider;
import andrews.table_top_craft.screens.piece_figure.util.TTCColorPicker;
import andrews.table_top_craft.util.Color;
import andrews.table_top_craft.util.NBTColorSaving;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.widget.ForgeSlider;

public class ChessPieceFigureSettingsScreen extends BaseScreen implements IColorPicker
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/chess_piece_figure_menu.png");
    private static final ResourceLocation COLOR_PICKER_FRAME_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/color_picker/color_picker_frame.png");
    private static final Component TITLE = Component.translatable("gui.table_top_craft.piece_figure.piece_settings");
    private static final Component TOGGLE_PIECE_ROT_TXT = Component.translatable("gui.table_top_craft.piece_figure.toggle_rotation");
    private final ChessPieceFigureBlockEntity chessPieceFigureBlockEntity;
    private final ChessPieceFigureBlockEntity previewBlockEntity;
    private final ItemStack chessPieceFigureStack;
    // Sliders
    private ChessRedColorSlider redColorSlider;
    private ChessGreenColorSlider greenColorSlider;
    private ChessBlueColorSlider blueColorSlider;
    private SaturationSlider saturationSlider;
    private ChessPieceFigureScaleSlider scaleSlider;
    // Color Picker
    public TTCColorPicker colorPicker;

    public boolean isColorPickerActive;

    public ChessPieceFigureSettingsScreen(ChessPieceFigureBlockEntity chessPieceFigureBlockEntity, boolean isColorPickerActive)
    {
        super(TEXTURE, 177, 158, TITLE);
        this.chessPieceFigureBlockEntity = chessPieceFigureBlockEntity;
        this.isColorPickerActive = isColorPickerActive;
        this.previewBlockEntity = new ChessPieceFigureBlockEntity(BlockPos.ZERO, TTCBlocks.CHESS_PIECE_FIGURE.get().defaultBlockState());
        this.chessPieceFigureStack = new ItemStack(TTCBlocks.CHESS_PIECE_FIGURE.get().asItem());
    }

    @Override
    protected void init()
    {
        super.init();
        // The Creative Buttons in the Gui Menu
        this.addRenderableWidget(new ChessPieceFigurePreviousSetButton(this.chessPieceFigureBlockEntity, this.x + 5, this.y + 22));
        this.addRenderableWidget(new ChessPieceFigurePreviousTypeButton(this.chessPieceFigureBlockEntity, this.x + 5, this.y + 38));
        this.addRenderableWidget(new ChessPieceFigureNextSetButton(this.chessPieceFigureBlockEntity, this.x + (this.textureWidth - 31), this.y + 22));
        this.addRenderableWidget(new ChessPieceFigureNextTypeButton(this.chessPieceFigureBlockEntity, this.x + (this.textureWidth - 31), this.y + 38));
        // Rotation Toggle Button
        this.addRenderableWidget(new ChessPieceFigureRotateButton(this.chessPieceFigureBlockEntity, this.x + 5, this.y + 60));
        // Color Picker Toggle Button
        this.addRenderableWidget(new ColorPickerToggleButton(this.chessPieceFigureBlockEntity, this, false, this.x + 159, this.y + 60));
        // Color Sliders
        this.addRenderableWidget(this.redColorSlider = new ChessRedColorSlider(this.x + 5, this.y + 74, 167, 12, NBTColorSaving.getRed(this.chessPieceFigureBlockEntity.getPieceColor()), this));
        this.addRenderableWidget(this.greenColorSlider = new ChessGreenColorSlider(this.x + 5, this.y + 87, 167, 12, NBTColorSaving.getGreen(this.chessPieceFigureBlockEntity.getPieceColor()), this));
        this.addRenderableWidget(this.blueColorSlider = new ChessBlueColorSlider(this.x + 5, this.y + 100, 167, 12, NBTColorSaving.getBlue(this.chessPieceFigureBlockEntity.getPieceColor()), this));
        // Color Modification Buttons
        this.addRenderableWidget(new ChessPieceFigureResetColorButton(this, this.x + 5, this.y + 113));
        this.addRenderableWidget(new ChessPieceFigureConfirmColorButton(this.chessPieceFigureBlockEntity, this.redColorSlider, this.greenColorSlider, this.blueColorSlider, this.x + 90, this.y + 113));
        // Scale Widgets
        this.addRenderableWidget(this.scaleSlider = new ChessPieceFigureScaleSlider(this.x + 5, this.y + 127, 167, 12, this.chessPieceFigureBlockEntity.getPieceScale()));
        this.addRenderableWidget(new ChessPieceFigureConfirmScaleButton(this.chessPieceFigureBlockEntity, this.scaleSlider, this.x + 5, this.y + 140));
        if(isColorPickerActive)
        {
            // The Color Picker
            Color color = new Color(redColorSlider.getValueInt(), greenColorSlider.getValueInt(), blueColorSlider.getValueInt());
            this.addRenderableWidget(colorPicker = new TTCColorPicker(this.x - 131, this.y + 8, this, color.getHue() / 360F, 1.0F - color.getValue()));
            // The saturation Slider
            float saturation = color.getSaturation() * 100;
            this.addRenderableWidget(saturationSlider = new SaturationSlider(this.x - 132, this.y + 138, 130, 12, Math.round(saturation), this));
        }
    }

    @Override
    public void renderScreenContents(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        if(isColorPickerActive)
            graphics.blit(COLOR_PICKER_FRAME_TEXTURE, this.x - 136, this.y + 3, 0, 0, 136, 151);
        // The Screen Title
        this.drawCenteredString(TITLE, this.width / 2 + (this.isColorPickerActive ? 68 : 0), this.y + 6, 4210752, false, graphics);
        // Toggle piece rotation text
        graphics.drawString(this.font, TOGGLE_PIECE_ROT_TXT, this.x + 20, this.y + 63, 0x000000, false);

        previewBlockEntity.setPieceSet(chessPieceFigureBlockEntity.getPieceSet());
        previewBlockEntity.setPieceType(chessPieceFigureBlockEntity.getPieceType());
        previewBlockEntity.setPieceColor(NBTColorSaving.saveColor(redColorSlider.getValueInt(), greenColorSlider.getValueInt(), blueColorSlider.getValueInt()));
        previewBlockEntity.setRotateChessPieceFigure(chessPieceFigureBlockEntity.getRotateChessPieceFigure());
        previewBlockEntity.saveToItem(chessPieceFigureStack);
        renderChessPiece(graphics.pose(), chessPieceFigureStack, this.x + this.textureWidth / 2, this.y + 33, 52);
    }

    private void renderChessPiece(PoseStack poseStack, ItemStack itemStack, int pX, int pY, int size)
    {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        BakedModel itemBakedModel = itemRenderer.getModel(itemStack, null, null, 0);
        Minecraft.getInstance().getTextureManager().getTexture(TextureAtlas.LOCATION_BLOCKS).setFilter(false, false);
        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.pushPose();
        poseStack.translate(pX, pY, 100.0F);
        poseStack.scale(1.0F, -1.0F, 1.0F);
        poseStack.scale(size, size, size);
        RenderSystem.applyModelViewMatrix();
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        Lighting.setupForFlatItems();
        itemRenderer.render(itemStack, ItemDisplayContext.GUI, false, poseStack, bufferSource, 15728880, OverlayTexture.NO_OVERLAY, itemBakedModel);
        bufferSource.endBatch();
        poseStack.popPose();
        RenderSystem.applyModelViewMatrix();
    }

    @Override
    public int offsetX()
    {
        return this.isColorPickerActive ? 136 : super.offsetX();
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
     * @param chessPieceFigureBlockEntity The ChessPieceFigureBlockEntity
     */
    public static void open(ChessPieceFigureBlockEntity chessPieceFigureBlockEntity)
    {
        Minecraft.getInstance().setScreen(new ChessPieceFigureSettingsScreen(chessPieceFigureBlockEntity, false));
    }

    @Override
    public TTCColorPicker getColorPicker()
    {
        return this.colorPicker;
    }

    @Override
    public ForgeSlider getRedSlider()
    {
        return this.redColorSlider;
    }

    @Override
    public ForgeSlider getGreenSlider()
    {
        return this.greenColorSlider;
    }

    @Override
    public ForgeSlider getBlueSlider()
    {
        return this.blueColorSlider;
    }

    @Override
    public ForgeSlider getSaturationSlider()
    {
        return this.saturationSlider;
    }

    @Override
    public boolean isColorPickerActive()
    {
        return this.isColorPickerActive;
    }
}