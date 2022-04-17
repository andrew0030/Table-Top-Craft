package andrews.table_top_craft.screens.piece_figure.util;

import andrews.table_top_craft.screens.chess.menus.color_selection.*;
import andrews.table_top_craft.screens.piece_figure.menus.ChessPieceFigureSettingsScreen;
import andrews.table_top_craft.tile_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ColorPickerToggleButton extends Button
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
    private final String buttonText = new TranslatableComponent("gui.table_top_craft.chess.color.reset_color").getString();//TODO remove
    private static BlockEntity blockEntity;
    private static final int buttonWidth = 13;
    private static final int buttonHeight = 13;
    private int u = 52;
    private int v = 13;
    private final Screen screen;
    private final boolean isOptional;

    public ColorPickerToggleButton(BlockEntity blockEntityIn, Screen screen, boolean isOptional, int xPos, int yPos)
    {
        super(xPos, yPos, buttonWidth, buttonHeight, new TextComponent(""), (button) -> {});
        this.screen = screen;
        blockEntity = blockEntityIn;
        this.isOptional = isOptional;
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
    {
        this.isHovered = mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height || this.isFocused();

        this.u = 52;
        if(this.isHovered || isColorPickerActive())
            this.u = 65;

        // Renders the Button
        poseStack.pushPose();
        RenderSystem.enableBlend();
        // The Button
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        this.blit(poseStack, x, y, u, v, width, height);
        // The Color inside the Button
        if (screen instanceof IColorPicker colorPicker)
        {
            int red = colorPicker.getRedSlider().getValueInt();
            int green = colorPicker.getGreenSlider().getValueInt();
            int blue = colorPicker.getBlueSlider().getValueInt();
            if (screen instanceof IColorPickerExtended colorPickerExtended && isOptional)
            {
                red = colorPickerExtended.getOptionalRedSlider().getValueInt();
                green = colorPickerExtended.getOptionalGreenSlider().getValueInt();
                blue = colorPickerExtended.getOptionalBlueSlider().getValueInt();
            }

            RenderSystem.setShaderColor(red / 255F, green / 255F, blue / 255F, 1.0f);
            this.blit(poseStack, x, y, 78, v, width, height);
        }
        // We disable blend after rendering the "highlight"
        RenderSystem.disableBlend();
        poseStack.popPose();
    }

    @Override
    public void onClick(double pMouseX, double pMouseY)
    {
        super.onClick(pMouseX, pMouseY);
        if(Minecraft.getInstance().screen != null)
        {
            if(blockEntity instanceof ChessPieceFigureBlockEntity chessPieceFigureBlockEntity)
            {
                if (isColorPickerActive())
                    Minecraft.getInstance().setScreen(new ChessPieceFigureSettingsScreen(chessPieceFigureBlockEntity, false));
                else
                    Minecraft.getInstance().setScreen(new ChessPieceFigureSettingsScreen(chessPieceFigureBlockEntity, true));
            }
            else if(screen instanceof ChessTileInfoColorScreen && blockEntity instanceof ChessTileEntity chessTileEntity)
            {
                if (isColorPickerActive())
                    Minecraft.getInstance().setScreen(new ChessTileInfoColorScreen(chessTileEntity, false));
                else
                    Minecraft.getInstance().setScreen(new ChessTileInfoColorScreen(chessTileEntity, true));
            }
            else if(screen instanceof ChessBoardLegalMoveColorScreen && blockEntity instanceof ChessTileEntity chessTileEntity)
            {
                if (isColorPickerActive())
                    Minecraft.getInstance().setScreen(new ChessBoardLegalMoveColorScreen(chessTileEntity, false));
                else
                    Minecraft.getInstance().setScreen(new ChessBoardLegalMoveColorScreen(chessTileEntity, true));
            }
            else if(screen instanceof ChessBoardInvalidMoveColorScreen && blockEntity instanceof ChessTileEntity chessTileEntity)
            {
                if (isColorPickerActive())
                    Minecraft.getInstance().setScreen(new ChessBoardInvalidMoveColorScreen(chessTileEntity, false));
                else
                    Minecraft.getInstance().setScreen(new ChessBoardInvalidMoveColorScreen(chessTileEntity, true));
            }
            else if(screen instanceof ChessBoardAttackMoveColorScreen && blockEntity instanceof ChessTileEntity chessTileEntity)
            {
                if (isColorPickerActive())
                    Minecraft.getInstance().setScreen(new ChessBoardAttackMoveColorScreen(chessTileEntity, false));
                else
                    Minecraft.getInstance().setScreen(new ChessBoardAttackMoveColorScreen(chessTileEntity, true));
            }
            else if(screen instanceof ChessBoardPreviousMoveColorScreen && blockEntity instanceof ChessTileEntity chessTileEntity)
            {
                if (isColorPickerActive())
                    Minecraft.getInstance().setScreen(new ChessBoardPreviousMoveColorScreen(chessTileEntity, false));
                else
                    Minecraft.getInstance().setScreen(new ChessBoardPreviousMoveColorScreen(chessTileEntity, true));
            }
            else if(screen instanceof ChessBoardCastleMoveColorScreen && blockEntity instanceof ChessTileEntity chessTileEntity)
            {
                if (isColorPickerActive())
                    Minecraft.getInstance().setScreen(new ChessBoardCastleMoveColorScreen(chessTileEntity, false));
                else
                    Minecraft.getInstance().setScreen(new ChessBoardCastleMoveColorScreen(chessTileEntity, true));
            }
            else if(screen instanceof ChessBoardTilesColorScreen && blockEntity instanceof ChessTileEntity chessTileEntity)
            {
                // We can cast the screen because above we check what screen it is,
                // and based on that we can be sure it extends both Interfaces.
                IColorPicker colorPicker = (IColorPicker) screen;
                IColorPickerExtended colorPickerExtended = (IColorPickerExtended) screen;
                if(!isOptional)
                {
                    if(!colorPicker.isColorPickerActive())
                        Minecraft.getInstance().setScreen(new ChessBoardTilesColorScreen(chessTileEntity, true, false));
                    else
                        Minecraft.getInstance().setScreen(new ChessBoardTilesColorScreen(chessTileEntity, false, false));
                }
                else
                {
                    if(!colorPickerExtended.isOptionalColorPickerActive())
                        Minecraft.getInstance().setScreen(new ChessBoardTilesColorScreen(chessTileEntity, false, true));
                    else
                        Minecraft.getInstance().setScreen(new ChessBoardTilesColorScreen(chessTileEntity, false, false));
                }
            }
            else if(screen instanceof ChessBoardPieceColorsScreen && blockEntity instanceof ChessTileEntity chessTileEntity)
            {
                // We can cast the screen because above we check what screen it is,
                // and based on that we can be sure it extends both Interfaces.
                IColorPicker colorPicker = (IColorPicker) screen;
                IColorPickerExtended colorPickerExtended = (IColorPickerExtended) screen;
                if(!isOptional)
                {
                    if(!colorPicker.isColorPickerActive())
                        Minecraft.getInstance().setScreen(new ChessBoardPieceColorsScreen(chessTileEntity, true, false));
                    else
                        Minecraft.getInstance().setScreen(new ChessBoardPieceColorsScreen(chessTileEntity, false, false));
                }
                else
                {
                    if(!colorPickerExtended.isOptionalColorPickerActive())
                        Minecraft.getInstance().setScreen(new ChessBoardPieceColorsScreen(chessTileEntity, false, true));
                    else
                        Minecraft.getInstance().setScreen(new ChessBoardPieceColorsScreen(chessTileEntity, false, false));
                }
            }
        }
    }

    /**
     * @return whether there is already an active Color Picker on the screen
     */
    private boolean isColorPickerActive()
    {
        if(screen instanceof IColorPicker colorPicker && screen instanceof IColorPickerExtended colorPickerExtended)
        {
            if(!isOptional)
                return colorPicker.isColorPickerActive();
            else
                return colorPickerExtended.isOptionalColorPickerActive();
        }
        else if(screen instanceof IColorPicker colorPicker)
        {
            return colorPicker.isColorPickerActive();
        }
        return false;
    }
}