package andrews.table_top_craft.game_logic.chess.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;

import andrews.table_top_craft.game_logic.chess.PieceColor;
import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.BoardUtils;
import andrews.table_top_craft.game_logic.chess.board.moves.BaseMove;
import andrews.table_top_craft.game_logic.chess.board.moves.MajorAttackMove;
import andrews.table_top_craft.game_logic.chess.board.moves.MajorMove;
import andrews.table_top_craft.game_logic.chess.board.tiles.BaseChessTile;

public class KingPiece extends BasePiece
{
	/*
	 * If the King is in the middle of the Board, there would be a maximum of 8 possible
	 * Tiles he could move to, assuming the start position of the knight (the middle) is 0, those
	 * Coordinates would be the ones bellow
	 * 
	 * Visual Representation:
	 * [   ][   ][   ][   ][   ][   ][   ][   ]
	 * [   ][   ][   ][   ][   ][   ][   ][   ]
	 * [   ][   ][   ][   ][   ][   ][   ][   ]
	 * [   ][   ](-9 )(-8 )(-7 )[   ][   ][   ]
	 * [   ][   ](-1 )[ K ]( 1 )[   ][   ][   ]
	 * [   ][   ]( 7 )( 8 )( 9 )[   ][   ][   ]
	 * [   ][   ][   ][   ][   ][   ][   ][   ]
	 * [   ][   ][   ][   ][   ][   ][   ][   ]
	 */
	private final static int[] CANDIDATE_MOVE_COORDINATES = {-9, -8, -7, -1, 1, 7, 8, 9};
	private final boolean isCastled;
	private final boolean kingSideCastleCapable;
	private final boolean queenSideCastleCapable;

	public KingPiece(final PieceColor pieceColor, final int piecePosition, final boolean kingSideCastleCapable, final boolean queenSideCastleCapable)
	{
		super(PieceType.KING, piecePosition, pieceColor, true);
		this.isCastled = false;
		this.kingSideCastleCapable = kingSideCastleCapable;
		this.queenSideCastleCapable = queenSideCastleCapable;
	}
	
	public KingPiece(final PieceColor pieceColor, final int piecePosition, final boolean isFirstMove, final boolean isCastled, final boolean kingSideCastleCapable, final boolean queenSideCastleCapable)
	{
		super(PieceType.KING, piecePosition, pieceColor, isFirstMove);
		this.isCastled = isCastled;
		this.kingSideCastleCapable = kingSideCastleCapable;
		this.queenSideCastleCapable = queenSideCastleCapable;
	}
	
	public boolean isCastled()
	{
		return this.isCastled;
	}
	
	public boolean isKingSideCastleCapable()
	{
		return this.kingSideCastleCapable;
	}
	
	public boolean isQueenSideCastleCapable()
	{
		return this.queenSideCastleCapable;
	}

	@Override
	public Collection<BaseMove> calculateLegalMoves(final Board board)
	{
		final List<BaseMove> legalMoves = new ArrayList<>();
		
		for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES)
		{
			final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
			
			if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) || isEighthColumnExclusion(this.piecePosition, currentCandidateOffset))
			{
				continue;
			}
			
			if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate))
			{
				final BaseChessTile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
				
				// Checks if the tile is occupied
				if(!candidateDestinationTile.isTileOccupied())
				{	
					legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
				}
				else // The Tile is occupied
				{
					final BasePiece pieceAtDestination = candidateDestinationTile.getPiece();
					final PieceColor pieceColor = pieceAtDestination.getPieceColor();
					
					// Checks if the Piece at the given Position is the same color, if it isn't it can be taken
					if(this.pieceColor != pieceColor)
					{
						legalMoves.add(new MajorAttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
					}
				}
			}
		}
		return ImmutableList.copyOf(legalMoves);
	}
	
	@Override
	public String toString()
	{
		return BasePiece.PieceType.KING.toString();
	}
	
	/**
	 * If the King Piece is in the first column, we have to skip a few of the Candidate Positions to avoid invalid Positions
	 * @param currentPosition - The current position
	 * @param candidateOffset - The offset
	 * @return - Whether or not the position should be skipped
	 */
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset)
	{
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == -1 || candidateOffset == 7);
	}
	
	/**
	 * If the King Piece is in the eighth column, we have to skip a few of the Candidate Positions to avoid invalid Positions
	 * @param currentPosition - The current position
	 * @param candidateOffset - The offset
	 * @return - Whether or not the position should be skipped
	 */
	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset)
	{
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 1 || candidateOffset == 9);
	}
	
	@Override
    public int locationBonus()
	{
        return this.pieceColor.kingBonus(this.piecePosition);
    }
	
	@Override
	public KingPiece movePiece(final BaseMove move)
	{
		return new KingPiece(move.getMovedPiece().getPieceColor(), move.getDestinationCoordinate(), false, move.isCastlingMove(), false, false);
	}
}
