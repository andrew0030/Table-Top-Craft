package andrews.table_top_craft.tile_entities;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.ChessMoveLog;
import andrews.table_top_craft.game_logic.chess.board.tiles.BaseChessTile;
import andrews.table_top_craft.game_logic.chess.pgn.FenUtil;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;
import andrews.table_top_craft.registry.TTCTileEntities;
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
	}
	
	public ChessMoveLog getMoveLog()
	{
		return this.moveLog;
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
