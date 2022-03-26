package andrews.table_top_craft.tile_entities;

import andrews.table_top_craft.game_logic.chess.PieceColor;
import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.BoardUtils;
import andrews.table_top_craft.game_logic.chess.board.ChessMoveLog;
import andrews.table_top_craft.game_logic.chess.board.moves.KingSideCastleMove;
import andrews.table_top_craft.game_logic.chess.board.moves.MajorAttackMove;
import andrews.table_top_craft.game_logic.chess.board.moves.MajorMove;
import andrews.table_top_craft.game_logic.chess.board.moves.PawnAttackMove;
import andrews.table_top_craft.game_logic.chess.board.moves.PawnEnPassantAttackMove;
import andrews.table_top_craft.game_logic.chess.board.moves.PawnJumpMove;
import andrews.table_top_craft.game_logic.chess.board.moves.PawnMove;
import andrews.table_top_craft.game_logic.chess.board.moves.PawnPromotion;
import andrews.table_top_craft.game_logic.chess.board.moves.QueenSideCastleMove;
import andrews.table_top_craft.game_logic.chess.board.tiles.BaseChessTile;
import andrews.table_top_craft.game_logic.chess.pgn.FenUtil;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;
import andrews.table_top_craft.game_logic.chess.pieces.BishopPiece;
import andrews.table_top_craft.game_logic.chess.pieces.KingPiece;
import andrews.table_top_craft.game_logic.chess.pieces.KnightPiece;
import andrews.table_top_craft.game_logic.chess.pieces.PawnPiece;
import andrews.table_top_craft.game_logic.chess.pieces.QueenPiece;
import andrews.table_top_craft.game_logic.chess.pieces.RookPiece;
import andrews.table_top_craft.registry.TTCTileEntities;
import andrews.table_top_craft.util.NBTColorSaving;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ChessTileEntity extends BlockEntity
{
	private Board board;
	private BaseChessTile sourceTile;
	private BaseChessTile destinationTile;
	private BasePiece humanMovedPiece;
	private final ChessMoveLog moveLog;
	
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

	public ChessTileEntity(BlockPos pos, BlockState state)
	{
		super(TTCTileEntities.CHESS.get(), pos, state);
		moveLog = new ChessMoveLog();
	}

	//TODO remove if not needed
//	public ChessTileEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state)
//	{
//		super(blockEntityType, pos, state);
//		moveLog = new ChessMoveLog();
//	}

	// Used to synchronize the BlockEntity with the client when the chunk it is in is loaded
	@Override
	public CompoundTag getUpdateTag()
	{
		CompoundTag compound = new CompoundTag();
		this.saveToNBT(compound);
		return compound;
	}

	// Used to synchronize the BlockEntity with the client when the chunk it is in is loaded
	@Override
	public void handleUpdateTag(CompoundTag compound)
	{
		this.loadFromNBT(compound);
	}

	// Used to synchronize the BlockEntity with the client when the chunk it is in is loaded
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket()
	{
		// Will get tag from #getUpdateTag
		return ClientboundBlockEntityDataPacket.create(this);
	}

	// Used to synchronize the BlockEntity with the client onBlockUpdate
	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt)
	{
		this.loadFromNBT(pkt.getTag());
	}
	
	@Override
	public void saveAdditional(CompoundTag compound)
	{
		super.saveAdditional(compound);
		this.saveToNBT(compound);
	}
	
	@Override
	public void load(CompoundTag compound)
	{
		super.load(compound);
		this.loadFromNBT(compound);
	}
	
	/**
	 * Used to save data to the NBT
	 */
	private void saveToNBT(CompoundTag compound)
	{
		CompoundTag chessNBT = new CompoundTag();
		if(this.board != null)
		{
			// We save the Board FEN
			chessNBT.putString("BoardFEN", FenUtil.createFENFromGame(this.board));
			
			// We also save a List of all pieces that are on their first move as this information is not saved otherwise
			final StringBuilder builder = new StringBuilder();
			for(int i = 0; i < BoardUtils.NUM_TILES; i++)
			{
				if(this.board.getTile(i).isTileOccupied())
				{
					if(this.board.getTile(i).getPiece().isFirstMove())
					{
						builder.append(i).append("/");
					}
				}
			}
			// We HAVE to check if the String is empty, otherwise the game will crash if we try to modify it
			if(builder.length() >= 2)
				builder.setLength(builder.length() - 1);
			
			chessNBT.putString("FirstMoves", builder.toString());
			chessNBT.putBoolean("IsWhiteCastled", this.board.getWhiteChessPlayer().isCastled());
			chessNBT.putBoolean("IsBlackCastled", this.board.getBlackChessPlayer().isCastled());
		}
		
		if(this.moveLog != null)
		{
			ListTag chessMoves = new ListTag();
			for(int i = 0; i < this.moveLog.size(); i++)
			{
				CompoundTag chessMove = new CompoundTag();
				chessMove.putString("Move" + (i + 1), moveLog.getMoves().get(i).saveToNBT());
				chessMoves.add(chessMove);
			}
			chessNBT.put("MoveLog", chessMoves);
		}
		chessNBT.putInt("ShowTileInfo", !this.showTileInfo ? 0 : 1);
		chessNBT.putInt("ShowAvailableMoves", !this.showAvailableMoves ? 0 : 1);
		chessNBT.putInt("ShowPreviousMove", !this.showPreviousMove ? 0 : 1);
		chessNBT.putInt("UseCustomPlate", !this.useCustomPlate ? 0 : 1);
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
		if(this.sourceTile != null)
			chessNBT.putInt("SourceTile", this.getSourceTile().getTileCoordinate());
		if(this.humanMovedPiece != null)
			chessNBT.putInt("HumanMovedPiece", this.getHumanMovedPiece().getPiecePosition());
		compound.put("ChessValues", chessNBT);
	}
	
	/**
	 * Used to load data from the NBT
	 */
	private void loadFromNBT(CompoundTag compound)
	{
		CompoundTag chessNBT = compound.getCompound("ChessValues");
		if(chessNBT.contains("BoardFEN", Tag.TAG_STRING) && chessNBT.contains("FirstMoves", Tag.TAG_STRING))
		{
			boolean isWhiteCastled = chessNBT.getBoolean("IsWhiteCastled");
			boolean isBlackCastled = chessNBT.getBoolean("IsBlackCastled");
			this.board = FenUtil.createGameFromFEN(chessNBT.getString("BoardFEN"), chessNBT.getString("FirstMoves"), isWhiteCastled, isBlackCastled);
		}

		if(chessNBT.contains("MoveLog"))
		{
			ListTag listNBT = chessNBT.getList("MoveLog", Tag.TAG_COMPOUND);
			moveLog.clear();
			for(int i = 0; i < listNBT.size(); i++)
			{
				CompoundTag compoundTag = listNBT.getCompound(i);
				String move = compoundTag.getString("Move" + (i + 1));
				String[] moveInfo = move.split("/");
				PieceColor pieceColor = PieceColor.WHITE;
				if(moveInfo[1].equals("B"))
					pieceColor = PieceColor.BLACK;
				
				switch(moveInfo[0])
				{
				default:
					break;
				case "pawn_jump":
					this.moveLog.addMove(new PawnJumpMove(this.getBoard(), new PawnPiece(pieceColor, Integer.parseInt(moveInfo[2])), Integer.parseInt(moveInfo[3])));
					break;
				case "pawn_move":
					this.moveLog.addMove(new PawnMove(this.getBoard(), new PawnPiece(pieceColor, Integer.parseInt(moveInfo[2])), Integer.parseInt(moveInfo[3])));
					break;
				case "pawn_attack":
					this.moveLog.addMove(new PawnAttackMove(this.getBoard(), new PawnPiece(pieceColor, Integer.parseInt(moveInfo[2])), Integer.parseInt(moveInfo[3]), getPieceFromType(moveInfo[5], pieceColor, Integer.parseInt(moveInfo[4]))));
					break;
				case "pawn_enpassant":
					this.moveLog.addMove(new PawnEnPassantAttackMove(this.getBoard(), new PawnPiece(pieceColor, Integer.parseInt(moveInfo[2])), Integer.parseInt(moveInfo[3]), new PawnPiece(getOppositeColor(pieceColor), Integer.parseInt(moveInfo[4]))));
					break;
				case "pawn_promotion":
					this.moveLog.addMove(new PawnPromotion(new PawnMove(this.getBoard(), new PawnPiece(pieceColor, Integer.parseInt(moveInfo[2])), Integer.parseInt(moveInfo[3]))));
					break;
				case "major_move":
					this.moveLog.addMove(new MajorMove(this.getBoard(), getPieceFromType(moveInfo[4], getOppositeColor(pieceColor), Integer.parseInt(moveInfo[2])), Integer.parseInt(moveInfo[3])));
					break;
				case "major_attack":
					this.moveLog.addMove(new MajorAttackMove(this.getBoard(), getPieceFromType(moveInfo[3], getOppositeColor(pieceColor), Integer.parseInt(moveInfo[2])), Integer.parseInt(moveInfo[4]), getPieceFromType(moveInfo[6], pieceColor, Integer.parseInt(moveInfo[5]))));
					break;
				case "king_side_castle":
					this.moveLog.addMove(new KingSideCastleMove(this.getBoard(), new KingPiece(pieceColor, Integer.parseInt(moveInfo[2]), false, false), Integer.parseInt(moveInfo[3]), new RookPiece(pieceColor, Integer.parseInt(moveInfo[4])), Integer.parseInt(moveInfo[5]), Integer.parseInt(moveInfo[6])));
					break;
				case "queen_side_castle":
					this.moveLog.addMove(new QueenSideCastleMove(this.getBoard(), new KingPiece(pieceColor, Integer.parseInt(moveInfo[2]), false, false), Integer.parseInt(moveInfo[3]), new RookPiece(pieceColor, Integer.parseInt(moveInfo[4])), Integer.parseInt(moveInfo[5]), Integer.parseInt(moveInfo[6])));
				}
			}
		}
		if(chessNBT.contains("ShowTileInfo", Tag.TAG_INT))
			this.showTileInfo = chessNBT.getInt("ShowTileInfo") != 0;
		if(chessNBT.contains("ShowAvailableMoves", Tag.TAG_INT))
			this.showAvailableMoves = chessNBT.getInt("ShowAvailableMoves") != 0;
		if(chessNBT.contains("ShowPreviousMove", Tag.TAG_INT))
			this.showPreviousMove = chessNBT.getInt("ShowPreviousMove") != 0;
		if(chessNBT.contains("TileInfoColor", Tag.TAG_STRING))
			this.tileInfoColor = chessNBT.getString("TileInfoColor");
		if(chessNBT.contains("UseCustomPlate", Tag.TAG_INT))
			this.useCustomPlate = chessNBT.getInt("UseCustomPlate") != 0;
		if(chessNBT.contains("WhiteTilesColor", Tag.TAG_STRING))
			this.whiteTilesColor = chessNBT.getString("WhiteTilesColor");
		if(chessNBT.contains("BlackTilesColor", Tag.TAG_STRING))
			this.blackTilesColor = chessNBT.getString("BlackTilesColor");
		if(chessNBT.contains("WhitePiecesColor", Tag.TAG_STRING))
			this.whitePiecesColor = chessNBT.getString("WhitePiecesColor");
		if(chessNBT.contains("BlackPiecesColor", Tag.TAG_STRING))
			this.blackPiecesColor = chessNBT.getString("BlackPiecesColor");
		if(chessNBT.contains("LegalMoveColor", Tag.TAG_STRING))
			this.legalMoveColor = chessNBT.getString("LegalMoveColor");
		if(chessNBT.contains("InvalidMoveColor", Tag.TAG_STRING))
			this.invalidMoveColor = chessNBT.getString("InvalidMoveColor");
		if(chessNBT.contains("AttackMoveColor", Tag.TAG_STRING))
			this.attackMoveColor = chessNBT.getString("AttackMoveColor");
		if(chessNBT.contains("PreviousMoveColor", Tag.TAG_STRING))
			this.previousMoveColor = chessNBT.getString("PreviousMoveColor");
		if(chessNBT.contains("CastleMoveColor", Tag.TAG_STRING))
			this.castleMoveColor = chessNBT.getString("CastleMoveColor");
		if(chessNBT.contains("SourceTile", Tag.TAG_INT))
			this.sourceTile = getBoard().getTile(chessNBT.getInt("SourceTile"));
		if(chessNBT.contains("HumanMovedPiece", Tag.TAG_INT))
			this.humanMovedPiece = getBoard().getTile(chessNBT.getInt("HumanMovedPiece")).getPiece();
	}
	
	private BasePiece getPieceFromType(String pieceType, PieceColor pieceColor, int piecePosition)
	{
		return switch (pieceType)
		{
			case "P" -> new PawnPiece(getOppositeColor(pieceColor), piecePosition);
			case "R" -> new RookPiece(getOppositeColor(pieceColor), piecePosition);
			case "N" -> new KnightPiece(getOppositeColor(pieceColor), piecePosition);
			case "B" -> new BishopPiece(getOppositeColor(pieceColor), piecePosition);
			case "Q" -> new QueenPiece(getOppositeColor(pieceColor), piecePosition);
			case "K" -> new KingPiece(getOppositeColor(pieceColor), piecePosition, true, true);
			default -> null;
		};
	}
	
	private PieceColor getOppositeColor(PieceColor color)
	{
		return color.isWhite() ? PieceColor.BLACK : PieceColor.WHITE;
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
		setChanged();//TODO markdirty replacement?
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
