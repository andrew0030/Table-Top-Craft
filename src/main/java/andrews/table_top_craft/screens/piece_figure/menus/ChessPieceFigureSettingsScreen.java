package andrews.table_top_craft.screens.piece_figure.menus;

import andrews.table_top_craft.registry.TTCBlocks;
import andrews.table_top_craft.screens.chess.buttons.ChessCancelButton;
import andrews.table_top_craft.screens.chess.sliders.ChessBlueColorSlider;
import andrews.table_top_craft.screens.chess.sliders.ChessGreenColorSlider;
import andrews.table_top_craft.screens.chess.sliders.ChessRedColorSlider;
import andrews.table_top_craft.screens.piece_figure.buttons.ChessPieceFigureConfirmColorButton;
import andrews.table_top_craft.screens.piece_figure.buttons.ChessPieceFigureResetColorButton;
import andrews.table_top_craft.screens.piece_figure.buttons.ChessPieceFigureRotateButton;
import andrews.table_top_craft.tile_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.util.NBTColorSaving;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ChessPieceFigureSettingsScreen extends Screen
{
    private static final ResourceLocation MENU_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/menus/medium_chess_menu.png");
    private final String chessPieceFigureSettingsText = new TranslatableComponent("gui.table_top_craft.piece_figure.piece_settings").getString();
    private final String togglePieceRotationText = new TranslatableComponent("gui.table_top_craft.piece_figure.toggle_rotation").getString();
    private final ChessPieceFigureBlockEntity chessPieceFigureBlockEntity;
    private final int xSize = 177;
    private final int ySize = 131;
    // Piece Color
    private ChessRedColorSlider redColorSlider;
    private ChessGreenColorSlider greenColorSlider;
    private ChessBlueColorSlider blueColorSlider;

    private final ChessPieceFigureBlockEntity previewBlockEntity;
    private final ItemStack chessPieceFigureStack;

    public ChessPieceFigureSettingsScreen(ChessPieceFigureBlockEntity chessPieceFigureBlockEntity)
    {
        super(new TextComponent(""));
        this.chessPieceFigureBlockEntity = chessPieceFigureBlockEntity;
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
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        // The Buttons in the Gui Menu
        this.addRenderableWidget(new ChessPieceFigureRotateButton(this.chessPieceFigureBlockEntity, x + 5, y + 60));

        this.addRenderableWidget(this.redColorSlider = new ChessRedColorSlider(x + 5, y + 74, 167, 12, NBTColorSaving.getRed(this.chessPieceFigureBlockEntity.getPieceColor())));
        this.addRenderableWidget(this.greenColorSlider = new ChessGreenColorSlider(x + 5, y + 87, 167, 12, NBTColorSaving.getGreen(this.chessPieceFigureBlockEntity.getPieceColor())));
        this.addRenderableWidget(this.blueColorSlider = new ChessBlueColorSlider(x + 5, y + 100, 167, 12, NBTColorSaving.getBlue(this.chessPieceFigureBlockEntity.getPieceColor())));

        this.addRenderableWidget(new ChessPieceFigureResetColorButton(this.redColorSlider, this.greenColorSlider, this.blueColorSlider, x + 5, y + 113));
        this.addRenderableWidget(new ChessPieceFigureConfirmColorButton(this.chessPieceFigureBlockEntity, this.redColorSlider, this.greenColorSlider, this.blueColorSlider, x + 90, y + 113));
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
        itemRenderer.render(itemStack, ItemTransforms.TransformType.GUI, false, poseStack, bufferSource, 15728880, OverlayTexture.NO_OVERLAY, itemBakedModel);
        bufferSource.endBatch();
        poseStack.popPose();
        RenderSystem.applyModelViewMatrix();
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