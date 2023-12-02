package andrews.table_top_craft.screens.chess.buttons.colors;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.screens.base.buttons.BaseTextButtonSmall;
import andrews.table_top_craft.screens.chess.sliders.ChessAlphaColorSlider;
import andrews.table_top_craft.screens.chess.sliders.ChessBlueColorSlider;
import andrews.table_top_craft.screens.chess.sliders.ChessGreenColorSlider;
import andrews.table_top_craft.screens.chess.sliders.ChessRedColorSlider;
import andrews.table_top_craft.util.NBTColorSaving;
import andrews.table_top_craft.util.NetworkUtil;
import net.minecraft.network.chat.Component;

public class ChessConfirmColorButton extends BaseTextButtonSmall
{
	private static final Component TEXT = Component.translatable("gui.table_top_craft.chess.confirm_color");
	private static final Component TEXT_PLURAL = Component.translatable("gui.table_top_craft.chess.confirm_colors");
	private final ChessBlockEntity blockEntity;
	private final ColorMenuType colorMenuType;
	private final ChessRedColorSlider redSlider;
	private final ChessGreenColorSlider greenSlider;
	private final ChessBlueColorSlider blueSlider;
	private ChessAlphaColorSlider alphaSlider;
	private ChessRedColorSlider optionalRedSlider;
	private ChessGreenColorSlider optionalGreenSlider;
	private ChessBlueColorSlider optionalBlueSlider;
	
	public ChessConfirmColorButton(ColorMenuType colorMenu, ChessBlockEntity blockEntity, ChessRedColorSlider red, ChessGreenColorSlider green, ChessBlueColorSlider blue, int pX, int pY)
	{
		super(pX, pY, TEXT);
		this.colorMenuType = colorMenu;
		this.blockEntity = blockEntity;
		this.redSlider = red;
		this.greenSlider = green;
		this.blueSlider = blue;
	}
	
	public ChessConfirmColorButton(ColorMenuType colorMenu, ChessBlockEntity blockEntity, ChessRedColorSlider red, ChessGreenColorSlider green, ChessBlueColorSlider blue, ChessAlphaColorSlider alpha, int pX, int pY)
	{
		this(colorMenu, blockEntity, red, green, blue, pX, pY);
		this.alphaSlider = alpha;
	}
	
	public ChessConfirmColorButton(ColorMenuType colorMenu, ChessBlockEntity blockEntity, ChessRedColorSlider red, ChessRedColorSlider optionalRed, ChessGreenColorSlider green, ChessGreenColorSlider optionalGreen, ChessBlueColorSlider blue, ChessBlueColorSlider optionalBlue, int pX, int pY)
	{
		super(pX, pY, TEXT_PLURAL);
		this.colorMenuType = colorMenu;
		this.blockEntity = blockEntity;
		this.redSlider = red;
		this.greenSlider = green;
		this.blueSlider = blue;
		this.optionalRedSlider = optionalRed;
		this.optionalGreenSlider = optionalGreen;
		this.optionalBlueSlider = optionalBlue;
	}

	@Override
	public void onPress()
	{
		switch(colorMenuType)
		{
			default:
			case TILE_INFO:
				NetworkUtil.setColorMessage(0, this.blockEntity.getBlockPos(), NBTColorSaving.saveColor(this.redSlider.getValueInt(), this.greenSlider.getValueInt(), this.blueSlider.getValueInt()));
				break;
			case BOARD_PLATE:
				if(optionalRedSlider != null && optionalGreenSlider != null && optionalBlueSlider != null)
					NetworkUtil.setColorsMessage(0, this.blockEntity.getBlockPos(), NBTColorSaving.saveColor(this.redSlider.getValueInt(), this.greenSlider.getValueInt(), this.blueSlider.getValueInt()), NBTColorSaving.saveColor(this.optionalRedSlider.getValueInt(), this.optionalGreenSlider.getValueInt(), this.optionalBlueSlider.getValueInt()));
				break;
			case PIECES:
				if(optionalRedSlider != null && optionalGreenSlider != null && optionalBlueSlider != null)
					NetworkUtil.setColorsMessage(1, this.blockEntity.getBlockPos(), NBTColorSaving.saveColor(this.redSlider.getValueInt(), this.greenSlider.getValueInt(), this.blueSlider.getValueInt()), NBTColorSaving.saveColor(this.optionalRedSlider.getValueInt(), this.optionalGreenSlider.getValueInt(), this.optionalBlueSlider.getValueInt()));
				break;
			case LEGAL_MOVE:
				if(alphaSlider != null)
					NetworkUtil.setColorMessage(1, this.blockEntity.getBlockPos(), NBTColorSaving.saveColor(this.redSlider.getValueInt(), this.greenSlider.getValueInt(), this.blueSlider.getValueInt(), this.alphaSlider.getValueInt()));
				break;
			case INVALID_MOVE:
				if(alphaSlider != null)
					NetworkUtil.setColorMessage(2, this.blockEntity.getBlockPos(), NBTColorSaving.saveColor(this.redSlider.getValueInt(), this.greenSlider.getValueInt(), this.blueSlider.getValueInt(), this.alphaSlider.getValueInt()));
				break;
			case ATTACK_MOVE:
				if(alphaSlider != null)
					NetworkUtil.setColorMessage(3, this.blockEntity.getBlockPos(), NBTColorSaving.saveColor(this.redSlider.getValueInt(), this.greenSlider.getValueInt(), this.blueSlider.getValueInt(), alphaSlider.getValueInt()));
				break;
			case PREVIOUS_MOVE:
				if(alphaSlider != null)
					NetworkUtil.setColorMessage(4, this.blockEntity.getBlockPos(), NBTColorSaving.saveColor(this.redSlider.getValueInt(), this.greenSlider.getValueInt(), this.blueSlider.getValueInt(), alphaSlider.getValueInt()));
				break;
			case CASTLE_MOVE:
				if(alphaSlider != null)
					NetworkUtil.setColorMessage(5, this.blockEntity.getBlockPos(), NBTColorSaving.saveColor(this.redSlider.getValueInt(), this.greenSlider.getValueInt(), this.blueSlider.getValueInt(), alphaSlider.getValueInt()));
		}
	}
	
	public enum ColorMenuType
	{
		TILE_INFO,
		BOARD_PLATE,
		PIECES,
		LEGAL_MOVE,
		INVALID_MOVE,
		ATTACK_MOVE,
		PREVIOUS_MOVE,
		CASTLE_MOVE;
	}
}