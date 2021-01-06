package andrews.table_top_craft.tile_entities;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.ChessMoveLog;
import andrews.table_top_craft.game_logic.chess.board.tiles.BaseChessTile;
import andrews.table_top_craft.game_logic.chess.pgn.FenUtil;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;
import andrews.table_top_craft.registry.TTCTileEntities;
import andrews.table_top_craft.util.NBTColorSaving;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants.NBT;

public class ChessTileEntity extends TileEntity
{
	private Board board;
	private BaseChessTile sourceTile;
	private BaseChessTile destinationTile;
	private BasePiece humanMovedPiece;
	private ChessMoveLog moveLog;
	
	private boolean showTileInfo;
	private boolean showAvailableMoves;
	private boolean showPreviousMove;
	private boolean useCustomPlate;
	
	private String tileInfoColor;
	private String whiteTilesColor;
	private String blackTilesColor;
	private String whitePiecesColor;
	private String blackPiecesColor;
	private String legalMoveColor;
	private String invalidMoveColor;
	private String attackMoveColor;
	private String previousMoveColor;
	private String castleMoveColor;

	public ChessTileEntity()
	{
		super(TTCTileEntities.CHESS.get());
		moveLog = new ChessMoveLog();
	}
	
	//Used to synchronize the TileEntity with the client onBlockUpdate
	@Override
	public SUpdateTileEntityPacket getUpdatePacket()
	{
		CompoundNBT compound = new CompoundNBT();
		this.saveToNBT(compound);
		return new SUpdateTileEntityPacket(this.getPos(), -1, compound);
	}
	
	//Used to synchronize the TileEntity with the client onBlockUpdate
	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
	{
		CompoundNBT compound = pkt.getNbtCompound();
	    this.loadFromNBT(compound);
	}
	
	//Used to synchronize the TileEntity with the client when the chunk it is in is loaded
	@Override
	public CompoundNBT getUpdateTag()
	{
		return this.write(new CompoundNBT());
	}
	
	//Used to synchronize the TileEntity with the client when the chunk it is in is loaded
	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT compound)
	{
		this.read(state, compound);
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		super.write(compound);
		return this.saveToNBT(compound);
	}
	
	@Override
	public void read(BlockState state, CompoundNBT compound)
	{
		super.read(state, compound);
		this.loadFromNBT(compound);
	}
	
	/**
	 * Used to load data from the NBT
	 */
	private CompoundNBT saveToNBT(CompoundNBT compound)
	{
		CompoundNBT chessNBT = new CompoundNBT();
		if(this.board != null)
			chessNBT.putString("BoardFEN", FenUtil.createFENFromGame(this.board));
		chessNBT.putInt("ShowTileInfo", this.showTileInfo == false ? 0 : 1);
		chessNBT.putInt("ShowAvailableMoves", this.showAvailableMoves == false ? 0 : 1);
		chessNBT.putInt("ShowPreviousMove", this.showPreviousMove == false ? 0 : 1);
		chessNBT.putInt("UseCustomPlate", this.useCustomPlate == false ? 0 : 1);
		chessNBT.putString("TileInfoColor", getTileInfoColor());
		chessNBT.putString("WhiteTilesColor", getWhiteTilesColor());
		chessNBT.putString("BlackTilesColor", getBlackTilesColor());
		chessNBT.putString("WhitePiecesColor", getWhitePiecesColor());
		chessNBT.putString("BlackPiecesColor", getBlackPiecesColor());
		chessNBT.putString("LegalMoveColor", getLegalMoveColor());
		chessNBT.putString("InvalidMoveColor", getInvalidMoveColor());
		chessNBT.putString("AttackMoveColor", getAttackMoveColor());
		chessNBT.putString("PreviousMoveColor", getPreviousMoveColor());
		chessNBT.putString("CastleMoveColor", getCastleMoveColor());
		compound.put("ChessValues", chessNBT);
		return compound;
	}
	
	/**
	 * Used to load data from the NBT
	 */
	private void loadFromNBT(CompoundNBT compound)
	{
		CompoundNBT chessNBT = compound.getCompound("ChessValues");
		if(chessNBT.contains("BoardFEN", NBT.TAG_STRING))
			this.board = FenUtil.createGameFromFEN(chessNBT.getString("BoardFEN"));
		if(chessNBT.contains("ShowTileInfo", NBT.TAG_INT))
			this.showTileInfo = chessNBT.getInt("ShowTileInfo") == 0 ? false : true;
		if(chessNBT.contains("ShowAvailableMoves", NBT.TAG_INT))
			this.showAvailableMoves = chessNBT.getInt("ShowAvailableMoves") == 0 ? false : true;
		if(chessNBT.contains("ShowPreviousMove", NBT.TAG_INT))
			this.showPreviousMove = chessNBT.getInt("ShowPreviousMove") == 0 ? false : true;
		if(chessNBT.contains("TileInfoColor", NBT.TAG_STRING))
			this.tileInfoColor = chessNBT.getString("TileInfoColor");
		if(chessNBT.contains("UseCustomPlate", NBT.TAG_INT))
			this.useCustomPlate = chessNBT.getInt("UseCustomPlate") == 0 ? false : true;
		if(chessNBT.contains("WhiteTilesColor", NBT.TAG_STRING))
			this.whiteTilesColor = chessNBT.getString("WhiteTilesColor");
		if(chessNBT.contains("BlackTilesColor", NBT.TAG_STRING))
			this.blackTilesColor = chessNBT.getString("BlackTilesColor");
		if(chessNBT.contains("WhitePiecesColor", NBT.TAG_STRING))
			this.whitePiecesColor = chessNBT.getString("WhitePiecesColor");
		if(chessNBT.contains("BlackPiecesColor", NBT.TAG_STRING))
			this.blackPiecesColor = chessNBT.getString("BlackPiecesColor");
		if(chessNBT.contains("LegalMoveColor", NBT.TAG_STRING))
			this.legalMoveColor = chessNBT.getString("LegalMoveColor");
		if(chessNBT.contains("InvalidMoveColor", NBT.TAG_STRING))
			this.invalidMoveColor = chessNBT.getString("InvalidMoveColor");
		if(chessNBT.contains("AttackMoveColor", NBT.TAG_STRING))
			this.attackMoveColor = chessNBT.getString("AttackMoveColor");
		if(chessNBT.contains("PreviousMoveColor", NBT.TAG_STRING))
			this.previousMoveColor = chessNBT.getString("PreviousMoveColor");
		if(chessNBT.contains("CastleMoveColor", NBT.TAG_STRING))
			this.castleMoveColor = chessNBT.getString("CastleMoveColor");
	}
	
	public ChessMoveLog getMoveLog()
	{
		return this.moveLog;
	}
	
	public void setCastleMoveColor(String colorForNBT)
	{
		this.castleMoveColor = colorForNBT;
	}
	
	public String getCastleMoveColor()
	{
		return this.castleMoveColor == null ? NBTColorSaving.createCastleMoveColor() : this.castleMoveColor;
	}
	
	public void setPreviousMoveColor(String colorForNBT)
	{
		this.previousMoveColor = colorForNBT;
	}
	
	public String getPreviousMoveColor()
	{
		return this.previousMoveColor == null ? NBTColorSaving.createPreviousMoveColor() : this.previousMoveColor;
	}
	
	public void setAttackMoveColor(String colorForNBT)
	{
		this.attackMoveColor = colorForNBT;
	}
	
	public String getAttackMoveColor()
	{
		return this.attackMoveColor == null ? NBTColorSaving.createAttackMoveColor() : this.attackMoveColor;
	}
	
	public void setInvalidMoveColor(String colorForNBT)
	{
		this.invalidMoveColor = colorForNBT;
	}
	
	public String getInvalidMoveColor()
	{
		return this.invalidMoveColor == null ? NBTColorSaving.createInvalidMoveColor() : this.invalidMoveColor;
	}
	
	public void setLegalMoveColor(String colorForNBT)
	{
		this.legalMoveColor = colorForNBT;
	}
	
	public String getLegalMoveColor()
	{
		return this.legalMoveColor == null ? NBTColorSaving.createLegalMoveColor() : this.legalMoveColor;
	}
	
	public void setWhitePiecesColor(String colorForNBT)
	{
		this.whitePiecesColor = colorForNBT;
	}
	
	public String getWhitePiecesColor()
	{
		return this.whitePiecesColor == null ? NBTColorSaving.createWhitePiecesColor() : this.whitePiecesColor;
	}
	
	public void setBlackPiecesColor(String colorForNBT)
	{
		this.blackPiecesColor = colorForNBT;
	}
	
	public String getBlackPiecesColor()
	{
		return this.blackPiecesColor == null ? NBTColorSaving.createBlackPiecesColor() : this.blackPiecesColor;
	}
	
	public void setWhiteTilesColor(String colorForNBT)
	{
		this.whiteTilesColor = colorForNBT;
	}
	
	public String getWhiteTilesColor()
	{
		return this.whiteTilesColor == null ? NBTColorSaving.createWhiteTilesColor() : this.whiteTilesColor;
	}
	
	public void setBlackTilesColor(String colorForNBT)
	{
		this.blackTilesColor = colorForNBT;
	}
	
	public String getBlackTilesColor()
	{
		return this.blackTilesColor == null ? NBTColorSaving.createBlackTilesColor() : this.blackTilesColor;
	}
	
	public void setTileInfoColor(String colorForNBT)
	{
		this.tileInfoColor = colorForNBT;
	}
	
	public String getTileInfoColor()
	{
		return this.tileInfoColor == null ? NBTColorSaving.createWhiteColor() : this.tileInfoColor;
	}
	
	public void setShowPreviousMove(boolean shouldShowPreviousMove)
	{
		this.showPreviousMove = shouldShowPreviousMove;
	}
	
	public boolean getShowPreviousMove()
	{
		return this.showPreviousMove;
	}
	
	public void setShowAvailableMoves(boolean shouldShowAvailableMoves)
	{
		this.showAvailableMoves = shouldShowAvailableMoves;
	}
	
	public boolean getShowAvailableMoves()
	{
		return this.showAvailableMoves;
	}
	
	public void setUseCustomPlate(boolean shouldUseCustomPlate)
	{
		this.useCustomPlate = shouldUseCustomPlate;
	}
	
	public boolean getUseCustomPlate()
	{
		return this.useCustomPlate;
	}
	
	public void setShowTileInfo(boolean shouldShowTileInfo)
	{
		this.showTileInfo = shouldShowTileInfo;
	}
	
	public boolean getShouldShowTileInfo()
	{
		return this.showTileInfo;
	}
	
	public void setBoard(Board board)
	{
		this.board = board;
		markDirty();
	}
	
	public Board getBoard()
	{
		return this.board;
	}
	
	public void setSourceTile(BaseChessTile sourceTile)
	{
		this.sourceTile = sourceTile;
	}
	
	public BaseChessTile getSourceTile()
	{
		return sourceTile;
	}
	
	public void setHumanMovedPiece(BasePiece humanMovedPiece)
	{
		this.humanMovedPiece = humanMovedPiece;
	}
	
	public BasePiece getHumanMovedPiece()
	{
		return this.humanMovedPiece;
	}
	
	public void setDestinationTile(BaseChessTile destinationTile)
	{
		this.destinationTile = destinationTile;
	}
	
	public BaseChessTile getDestinationTile()
	{
		return destinationTile;
	}
}
