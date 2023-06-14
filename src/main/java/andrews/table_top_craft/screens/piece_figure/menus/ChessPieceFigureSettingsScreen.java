package andrews.table_top_craft.screens.piece_figure.menus;

import andrews.table_top_craft.block_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.registry.TTCBlocks;
import andrews.table_top_craft.screens.chess.buttons.pieces.creative_mode.ChessBoardPieceNextSetButton;
import andrews.table_top_craft.screens.chess.buttons.pieces.creative_mode.ChessBoardPieceNextTypeButton;
import andrews.table_top_craft.screens.chess.buttons.pieces.creative_mode.ChessBoardPiecePreviousSetButton;
import andrews.table_top_craft.screens.chess.buttons.pieces.creative_mode.ChessBoardPiecePreviousTypeButton;
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
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
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

public class ChessPieceFigureSettingsScreen extends Screen implements IColorPicker
{
    private static final ResourceLocation MENU_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/chess_piece_figure_menu.png");
    private static final ResourceLocation COLOR_PICKER_FRAME_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/color_picker/color_picker_frame.png");
    private final String chessPieceFigureSettingsText = Component.translatable("gui.table_top_craft.piece_figure.piece_settings").getString();
    private final String togglePieceRotationText = Component.translatable("gui.table_top_craft.piece_figure.toggle_rotation").getString();
    private final ChessPieceFigureBlockEntity chessPieceFigureBlockEntity;
    private final int xSize = 177;
    private final int ySize = 158;
    // Piece Color
    private ChessRedColorSlider redColorSlider;
    private ChessGreenColorSlider greenColorSlider;
    private ChessBlueColorSlider blueColorSlider;
    private ChessPieceFigureScaleSlider scaleSlider;

    public TTCColorPicker colorPicker;
    private SaturationSlider saturationSlider;

    private final ChessPieceFigureBlockEntity previewBlockEntity;
    private final ItemStack chessPieceFigureStack;

    public boolean isColorPickerActive;

    public ChessPieceFigureSettingsScreen(ChessPieceFigureBlockEntity chessPieceFigureBlockEntity, boolean isColorPickerActive)
    {
        super(Component.literal(""));
        this.chessPieceFigureBlockEntity = chessPieceFigureBlockEntity;
        this.isColorPickerActive = isColorPickerActive;
        previewBlockEntity = new ChessPieceFigureBlockEntity(BlockPos.ZERO, TTCBlocks.CHESS_PIECE_FIGURE.get().defaultBlockState());
        chessPieceFigureStack = new ItemStack(TTCBlocks.CHESS_PIECE_FIGURE.get().asItem());
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
        int x = (this.width - (this.xSize - (this.isColorPickerActive ? 136 : 0))) / 2;
        int y = (this.height - this.ySize) / 2;

        this.addRenderableWidget(new ChessPieceFigureRotateButton(this.chessPieceFigureBlockEntity, x + 5, y + 60));

        this.addRenderableWidget(new ColorPickerToggleButton(this.chessPieceFigureBlockEntity, this, false, x + 159, y + 60));

        this.addRenderableWidget(this.redColorSlider = new ChessRedColorSlider(x + 5, y + 74, 167, 12, NBTColorSaving.getRed(this.chessPieceFigureBlockEntity.getPieceColor()), this));
        this.addRenderableWidget(this.greenColorSlider = new ChessGreenColorSlider(x + 5, y + 87, 167, 12, NBTColorSaving.getGreen(this.chessPieceFigureBlockEntity.getPieceColor()), this));
        this.addRenderableWidget(this.blueColorSlider = new ChessBlueColorSlider(x + 5, y + 100, 167, 12, NBTColorSaving.getBlue(this.chessPieceFigureBlockEntity.getPieceColor()), this));

        this.addRenderableWidget(new ChessPieceFigureResetColorButton(this, x + 5, y + 113));
        this.addRenderableWidget(new ChessPieceFigureConfirmColorButton(this.chessPieceFigureBlockEntity, this.redColorSlider, this.greenColorSlider, this.blueColorSlider, x + 90, y + 113));

        this.addRenderableWidget(this.scaleSlider = new ChessPieceFigureScaleSlider(x + 5, y + 127, 167, 12, this.chessPieceFigureBlockEntity.getPieceScale()));
        this.addRenderableWidget(new ChessPieceFigureConfirmScaleButton(this.chessPieceFigureBlockEntity, this.scaleSlider, x + 5, y + 140));

        if(isColorPickerActive)
        {
            // The Color Picker
            Color color = new Color(redColorSlider.getValueInt(), greenColorSlider.getValueInt(), blueColorSlider.getValueInt());
            this.addRenderableWidget(colorPicker = new TTCColorPicker(x - 131, y + 8, this, color.getHue() / 360F, 1.0F - color.getValue()));
            // The saturation Slider
            float saturation = color.getSaturation() * 100;
            this.addRenderableWidget(saturationSlider = new SaturationSlider(x - 132, y + 138, 130, 12, Math.round(saturation), this));
        }

        // The Buttons in the Gui Menu
        this.addRenderableWidget(new ChessBoardPiecePreviousSetButton(this.chessPieceFigureBlockEntity, x + 5, y + 22));
        this.addRenderableWidget(new ChessBoardPiecePreviousTypeButton(this.chessPieceFigureBlockEntity, x + 5, y + 38));
        this.addRenderableWidget(new ChessBoardPieceNextSetButton(this.chessPieceFigureBlockEntity, x + (xSize - 31), y + 22));
        this.addRenderableWidget(new ChessBoardPieceNextTypeButton(this.chessPieceFigureBlockEntity, x + (xSize - 31), y + 38));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
    {
        int x = (this.width - (this.xSize - (this.isColorPickerActive ? 136 : 0))) / 2;
        int y = (this.height - this.ySize) / 2;

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, MENU_TEXTURE);
        GuiComponent.blit(poseStack, x, y, 0, 0, this.xSize, this.ySize);

        if(isColorPickerActive)
        {
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.setShaderTexture(0, COLOR_PICKER_FRAME_TEXTURE);
            GuiComponent.blit(poseStack, x - 136, y + 3, 0, 0, 136, 151);
        }

        // The screen text
        this.font.draw(poseStack, this.chessPieceFigureSettingsText, ((this.width / 2) - (this.font.width(this.chessPieceFigureSettingsText) / 2)) + (this.isColorPickerActive ? 68 : 0), y + 6, 4210752);
        // Toggle piece rotation text
        this.font.draw(poseStack, this.togglePieceRotationText, x + 20, y + 63, 0x000000);

        previewBlockEntity.setPieceSet(chessPieceFigureBlockEntity.getPieceSet());
        previewBlockEntity.setPieceType(chessPieceFigureBlockEntity.getPieceType());
        previewBlockEntity.setPieceColor(NBTColorSaving.saveColor(redColorSlider.getValueInt(), greenColorSlider.getValueInt(), blueColorSlider.getValueInt()));
        previewBlockEntity.setRotateChessPieceFigure(chessPieceFigureBlockEntity.getRotateChessPieceFigure());
        previewBlockEntity.saveToItem(chessPieceFigureStack);
        renderChessPiece(poseStack, chessPieceFigureStack, x + xSize / 2, y + 33, 52);

        // Renders the Buttons we added in init
        super.render(poseStack, mouseX, mouseY, partialTicks);
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
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        super.keyPressed(keyCode, scanCode, modifiers);
        InputConstants.Key pressedKey = InputConstants.getKey(keyCode, scanCode);
        if(this.minecraft.options.keyInventory.isActiveAndMatches(pressedKey))
            this.onClose();
        return true;
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