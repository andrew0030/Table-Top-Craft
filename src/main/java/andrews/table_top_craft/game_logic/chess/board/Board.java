package andrews.table_top_craft.game_logic.chess.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

import andrews.table_top_craft.game_logic.chess.PieceColor;
import andrews.table_top_craft.game_logic.chess.board.moves.BaseMove;
import andrews.table_top_craft.game_logic.chess.board.tiles.BaseChessTile;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;
import andrews.table_top_craft.game_logic.chess.pieces.BishopPiece;
import andrews.table_top_craft.game_logic.chess.pieces.KingPiece;
import andrews.table_top_craft.game_logic.chess.pieces.KnightPiece;
import andrews.table_top_craft.game_logic.chess.pieces.PawnPiece;
import andrews.table_top_craft.game_logic.chess.pieces.QueenPiece;
import andrews.table_top_craft.game_logic.chess.pieces.RookPiece;
import andrews.table_top_craft.game_logic.chess.player.BaseChessPlayer;
import andrews.table_top_craft.game_logic.chess.player.BlackChessPlayer;
import andrews.table_top_craft.game_logic.chess.player.WhiteChessPlayer;

public class Board
{
	private final List<BaseChessTile> gameBoard;
	private final Collection<BasePiece> whitePieces;
	private final Collection<BasePiece> blackPieces;
	
	private final WhiteChessPlayer whiteChessPlayer;
	private final BlackChessPlayer blackChessPlayer;
	private final BaseChessPlayer currentPlayer;
	
	private Board(final Builder builder)
	{
		this.gameBoard = createGameBoard(builder);
		this.whitePieces = calculateActivePieces(this.gameBoard, PieceColor.WHITE);
		this.blackPieces = calculateActivePieces(this.gameBoard, PieceColor.BLACK);
		
		final Collection<BaseMove> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
		final Collection<BaseMove> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces);
		
		this.whiteChessPlayer = new WhiteChessPlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
		this.blackChessPlayer = new BlackChessPlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
		this.currentPlayer = builder.nextMoveMaker.chooseChessPlayer(this.whiteChessPlayer, this.blackChessPlayer);
	}
	
	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < BoardUtils.NUM_TILES; i++)
		{
			final String tileText = this.gameBoard.get(i).toString();
			builder.append(String.format("%3s", tileText));
			
			if((i + 1) % BoardUtils.NUM_TILES_PER_ROW == 0)
			{
				builder.append("\n");
			}
		}
		return builder.toString();
	}
	
	/**
	 * @return - The white Chess Player
	 */
	public BaseChessPlayer getWhiteChessPlayer()
	{
		return this.whiteChessPlayer;
	}
	
	/**
	 * @return - The black Chess Player
	 */
	public BaseChessPlayer getBlackChessPlayer()
	{
		return this.blackChessPlayer;
	}
	
	/**
	 * @return - The current Chess Player
	 */
	public BaseChessPlayer getCurrentChessPlayer()
	{
		return this.currentPlayer;
	}
	
	/**
	 * @return - A Collection of all the white Pieces
	 */
	public Collection<BasePiece> getWhitePieces()
	{
		return this.whitePieces;
	}
	
	/**
	 * @return - A Collection of all the black Pieces
	 */
	public Collection<BasePiece> getBlackPieces()
	{
		return this.blackPieces;
	}

	/**
	 * @param pieces - The Pieces to calculate the Legal Moves for
	 * @return - A Collection of all Legal Moves for the given Pieces
	 */
	private Collection<BaseMove> calculateLegalMoves(final Collection<BasePiece> pieces)
	{
		final List<BaseMove> legalMoves = new ArrayList<>();
		
		for(final BasePiece piece : pieces)
		{
			legalMoves.addAll(piece.calculateLegalMoves(this));
		}
		return ImmutableList.copyOf(legalMoves);
	}

	/**
	 * @param gameBoard - The gameBoard with all Tiles
	 * @param pieceColor - The Color we want the active Pieces of
	 * @return - An Immutable List of all active Pieces, that have the given PieceColor
	 */
	private static Collection<BasePiece> calculateActivePieces(final List<BaseChessTile> gameBoard, final PieceColor pieceColor)
	{
		final List<BasePiece> activePieces = new ArrayList<>();
		
		// Goes through all Tiles on the Board
		for(final BaseChessTile tile : gameBoard)
		{
			// Checks if there is a Piece on the Tile
			if(tile.isTileOccupied())
			{
				final BasePiece piece = tile.getPiece();
				
				// If a Piece was found, and it matches the requested PieceColor, it gets added to the List
				if(piece.getPieceColor() == pieceColor)
				{
					activePieces.add(piece);
				}
			}
		}
		return ImmutableList.copyOf(activePieces);
	}

	/**
	 * @param tileCoordinate - The Coordinatge to check for a Tile
	 * @return - The Tile at the given Coordinate
	 */
	public BaseChessTile getTile(final int tileCoordinate)
	{
		return gameBoard.get(tileCoordinate);
	}
	
	/**
	 * Populates a List of Tiles that represents the Chess Board
	 * @param builder - The Board.Builder
	 * @return - The Immutable List of Tiles
	 */
	private static List<BaseChessTile> createGameBoard(final Builder builder)
	{
		final BaseChessTile[] tiles = new BaseChessTile[BoardUtils.NUM_TILES];
		
		for(int i = 0; i < BoardUtils.NUM_TILES; i++)
		{
			tiles[i] = BaseChessTile.createTile(i, builder.boardConfig.get(i));
		}
		return ImmutableList.copyOf(tiles);
	}
	
	/**
	 * @return - A Chess Board with the initial Positions
	 */
	public static Board createStandardBoard()
	{
		final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new RookPiece(PieceColor.BLACK, 0));
        builder.setPiece(new KnightPiece(PieceColor.BLACK, 1));
        builder.setPiece(new BishopPiece(PieceColor.BLACK, 2));
        builder.setPiece(new QueenPiece(PieceColor.BLACK, 3));
        builder.setPiece(new KingPiece(PieceColor.BLACK, 4));
        builder.setPiece(new BishopPiece(PieceColor.BLACK, 5));
        builder.setPiece(new KnightPiece(PieceColor.BLACK, 6));
        builder.setPiece(new RookPiece(PieceColor.BLACK, 7));
        builder.setPiece(new PawnPiece(PieceColor.BLACK, 8));
        builder.setPiece(new PawnPiece(PieceColor.BLACK, 9));
        builder.setPiece(new PawnPiece(PieceColor.BLACK, 10));
        builder.setPiece(new PawnPiece(PieceColor.BLACK, 11));
        builder.setPiece(new PawnPiece(PieceColor.BLACK, 12));
        builder.setPiece(new PawnPiece(PieceColor.BLACK, 13));
        builder.setPiece(new PawnPiece(PieceColor.BLACK, 14));
        builder.setPiece(new PawnPiece(PieceColor.BLACK, 15));
        // White Layout
        builder.setPiece(new PawnPiece(PieceColor.WHITE, 48));
        builder.setPiece(new PawnPiece(PieceColor.WHITE, 49));
        builder.setPiece(new PawnPiece(PieceColor.WHITE, 50));
        builder.setPiece(new PawnPiece(PieceColor.WHITE, 51));
        builder.setPiece(new PawnPiece(PieceColor.WHITE, 52));
        builder.setPiece(new PawnPiece(PieceColor.WHITE, 53));
        builder.setPiece(new PawnPiece(PieceColor.WHITE, 54));
        builder.setPiece(new PawnPiece(PieceColor.WHITE, 55));
        builder.setPiece(new RookPiece(PieceColor.WHITE, 56));
        builder.setPiece(new KnightPiece(PieceColor.WHITE, 57));
        builder.setPiece(new BishopPiece(PieceColor.WHITE, 58));
        builder.setPiece(new QueenPiece(PieceColor.WHITE, 59));
        builder.setPiece(new KingPiece(PieceColor.WHITE, 60));
        builder.setPiece(new BishopPiece(PieceColor.WHITE, 61));
        builder.setPiece(new KnightPiece(PieceColor.WHITE, 62));
        builder.setPiece(new RookPiece(PieceColor.WHITE, 63));
        // Sets White as the move maker, as white has the first move in Chess
        builder.setMoveMaker(PieceColor.WHITE);
        // Builds the Board
        return builder.build();
	}
	
	public static class Builder
	{
		Map<Integer, BasePiece> boardConfig;
		PieceColor nextMoveMaker;
		
		public Builder()
		{
			this.boardConfig = new HashMap<>();
		}
		
		public Builder setPiece(final BasePiece piece)
		{
			this.boardConfig.put(piece.getPiecePosition(), piece);
			return this;
		}
		
		public Builder setMoveMaker(final PieceColor nextMoveMaker)
		{
			this.nextMoveMaker = nextMoveMaker;
			return this;
		}
		
		public Board build()
		{
			return new Board(this);
		}
	}
}
