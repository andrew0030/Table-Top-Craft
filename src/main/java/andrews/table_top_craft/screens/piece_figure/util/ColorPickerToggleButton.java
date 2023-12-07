package andrews.table_top_craft.screens.piece_figure.util;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.block_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.screens.chess.menus.color_selection.*;
import andrews.table_top_craft.screens.piece_figure.menus.ChessPieceFigureSettingsScreen;
import andrews.table_top_craft.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ColorPickerToggleButton extends AbstractButton
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/buttons/chess_menu_buttons.png");
    private final BlockEntity blockEntity;
    private final Screen screen;
    private final boolean isOptional;

    public ColorPickerToggleButton(BlockEntity blockEntity, Screen screen, boolean isOptional, int xPos, int yPos)
    {
        super(xPos, yPos, 13, 13, Component.literal(""));
        this.screen = screen;
        this.blockEntity = blockEntity;
        this.isOptional = isOptional;
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        int u = this.isHoveredOrFocused() || this.isColorPickerActive() ? 65 : 52;
        int v = 13;
        // Renders the Button
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        graphics.blit(TEXTURE, this.x, this.y, u, v, this.width, this.height);
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
            graphics.setColor(red / 255F, green / 255F, blue / 255F, 1.0f);
            graphics.blit(TEXTURE, this.x, this.y, 78, v, this.width, this.height);
            graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    @Override
    public void onPress()
    {
        if(Minecraft.getInstance().screen != null)
        {
            if(blockEntity instanceof ChessPieceFigureBlockEntity chessPieceFigureBlockEntity)
            {
                if (isColorPickerActive())
                    Minecraft.getInstance().setScreen(new ChessPieceFigureSettingsScreen(chessPieceFigureBlockEntity, false));
                else
                    Minecraft.getInstance().setScreen(new ChessPieceFigureSettingsScreen(chessPieceFigureBlockEntity, true));
            }
            else if(screen instanceof ChessTileInfoColorScreen && blockEntity instanceof ChessBlockEntity chessBlockEntity)
            {
                if (isColorPickerActive())
                    Minecraft.getInstance().setScreen(new ChessTileInfoColorScreen(chessBlockEntity, false));
                else
                    Minecraft.getInstance().setScreen(new ChessTileInfoColorScreen(chessBlockEntity, true));
            }
            else if(screen instanceof ChessBoardLegalMoveColorScreen && blockEntity instanceof ChessBlockEntity chessBlockEntity)
            {
                if (isColorPickerActive())
                    Minecraft.getInstance().setScreen(new ChessBoardLegalMoveColorScreen(chessBlockEntity, false));
                else
                    Minecraft.getInstance().setScreen(new ChessBoardLegalMoveColorScreen(chessBlockEntity, true));
            }
            else if(screen instanceof ChessBoardInvalidMoveColorScreen && blockEntity instanceof ChessBlockEntity chessBlockEntity)
            {
                if (isColorPickerActive())
                    Minecraft.getInstance().setScreen(new ChessBoardInvalidMoveColorScreen(chessBlockEntity, false));
                else
                    Minecraft.getInstance().setScreen(new ChessBoardInvalidMoveColorScreen(chessBlockEntity, true));
            }
            else if(screen instanceof ChessBoardAttackMoveColorScreen && blockEntity instanceof ChessBlockEntity chessBlockEntity)
            {
                if (isColorPickerActive())
                    Minecraft.getInstance().setScreen(new ChessBoardAttackMoveColorScreen(chessBlockEntity, false));
                else
                    Minecraft.getInstance().setScreen(new ChessBoardAttackMoveColorScreen(chessBlockEntity, true));
            }
            else if(screen instanceof ChessBoardPreviousMoveColorScreen && blockEntity instanceof ChessBlockEntity chessBlockEntity)
            {
                if (isColorPickerActive())
                    Minecraft.getInstance().setScreen(new ChessBoardPreviousMoveColorScreen(chessBlockEntity, false));
                else
                    Minecraft.getInstance().setScreen(new ChessBoardPreviousMoveColorScreen(chessBlockEntity, true));
            }
            else if(screen instanceof ChessBoardCastleMoveColorScreen && blockEntity instanceof ChessBlockEntity chessBlockEntity)
            {
                if (isColorPickerActive())
                    Minecraft.getInstance().setScreen(new ChessBoardCastleMoveColorScreen(chessBlockEntity, false));
                else
                    Minecraft.getInstance().setScreen(new ChessBoardCastleMoveColorScreen(chessBlockEntity, true));
            }
            else if(screen instanceof ChessBoardTilesColorScreen && blockEntity instanceof ChessBlockEntity chessBlockEntity)
            {
                // We can cast the screen because above we check what screen it is,
                // and based on that we can be sure it extends both Interfaces.
                IColorPicker colorPicker = (IColorPicker) screen;
                IColorPickerExtended colorPickerExtended = (IColorPickerExtended) screen;
                if(!isOptional)
                {
                    if(!colorPicker.isColorPickerActive())
                        Minecraft.getInstance().setScreen(new ChessBoardTilesColorScreen(chessBlockEntity, true, false));
                    else
                        Minecraft.getInstance().setScreen(new ChessBoardTilesColorScreen(chessBlockEntity, false, false));
                }
                else
                {
                    if(!colorPickerExtended.isOptionalColorPickerActive())
                        Minecraft.getInstance().setScreen(new ChessBoardTilesColorScreen(chessBlockEntity, false, true));
                    else
                        Minecraft.getInstance().setScreen(new ChessBoardTilesColorScreen(chessBlockEntity, false, false));
                }
            }
            else if(screen instanceof ChessBoardPieceColorsScreen && blockEntity instanceof ChessBlockEntity chessBlockEntity)
            {
                // We can cast the screen because above we check what screen it is,
                // and based on that we can be sure it extends both Interfaces.
                IColorPicker colorPicker = (IColorPicker) screen;
                IColorPickerExtended colorPickerExtended = (IColorPickerExtended) screen;
                if(!isOptional)
                {
                    if(!colorPicker.isColorPickerActive())
                        Minecraft.getInstance().setScreen(new ChessBoardPieceColorsScreen(chessBlockEntity, true, false));
                    else
                        Minecraft.getInstance().setScreen(new ChessBoardPieceColorsScreen(chessBlockEntity, false, false));
                }
                else
                {
                    if(!colorPickerExtended.isOptionalColorPickerActive())
                        Minecraft.getInstance().setScreen(new ChessBoardPieceColorsScreen(chessBlockEntity, false, true));
                    else
                        Minecraft.getInstance().setScreen(new ChessBoardPieceColorsScreen(chessBlockEntity, false, false));
                }
            }
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output)
    {
        this.defaultButtonNarrationText(output);
    }

    /**
     * @return whether there is already an active Color Picker on the screen
     */
    private boolean isColorPickerActive()
    {
        if(screen instanceof IColorPicker colorPicker && screen instanceof IColorPickerExtended colorPickerExtended)
        {
            return isOptional ? colorPickerExtended.isOptionalColorPickerActive() : colorPicker.isColorPickerActive();
        }
        else if(screen instanceof IColorPicker colorPicker)
        {
            return colorPicker.isColorPickerActive();
        }
        return false;
    }
}