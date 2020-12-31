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

public class KnightPiece extends BasePiece
{
	/*
	 * If the Knight is in the middle of the Board, there would be a maximum of 8 possible
	 * Tiles he could move to, assuming the start position of the knight (the middle) is 0, those
	 * Coordinates would be the ones bellow
	 * 
	 * Visual Representation:
	 * [   ][   ][   ][   ][   ][   ][   ][   ]
	 * [   ][   ][   ][   ][   ][   ][   ][   ]
	 * [   ][   ](-17)[   ](-15)[   ][   ][   ]
	 * [   ](-10)[   ][   ][   ](-6 )[   ][   ]
	 * [   ][   ][   ][ K ][   ][   ][   ][   ]
	 * [   ]( 6 )[   ][   ][   ](10 )[   ][   ]
	 * [   ][   ](15 )[   ](17 )[   ][   ][   ]
	 * [   ][   ][   ][   ][   ][   ][   ][   ]
	 */
	private final static int[] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};

	public KnightPiece(final PieceColor pieceColor, final int piecePosition)
	{
		super(PieceType.KNIGHT, piecePosition, pieceColor, true);
	}
	
	public KnightPiece(final PieceColor pieceColor, final int piecePosition, final boolean isFirstMove)
	{
		super(PieceType.KNIGHT, piecePosition, pieceColor, isFirstMove);
	}

	@Override
	public Collection<BaseMove> calculateLegalMoves(final Board board)
	{
		final List<BaseMove> legalMoves = new ArrayList<>();
		
		// Goes through all Candidate Move Coordinates to checks if they are Legal Moves
		for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES)
		{
			int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
			
			if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate))
			{
				// Some special cases in which we have to skip an iteration
				if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
					isSecondColumnExclusion(this.piecePosition, currentCandidateOffset) ||
					isSeventhColumnExclusion(this.piecePosition, currentCandidateOffset) ||
					isEighthColumnExclusion(this.piecePosition, currentCandidateOffset))
				{
					continue;
				}
				final BaseChessTile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
				
				if(!candidateDestinationTile.isTileOccupied())
				{
					legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
				}
				else
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
		return BasePiece.PieceType.KNIGHT.toString();
	}
	
	@Override
	public KnightPiece movePiece(final BaseMove move)
	{
		return new KnightPiece(move.getMovedPiece().getPieceColor(), move.getDestinationCoordinate(), false);
	}
	
	/**
	 * If the Knight Piece is in the first column, we have to skip a few of the Candidate Positions to avoid invalid Positions
	 * @param currentPosition - The current position
	 * @param candidateOffset - The offset
	 * @return - Whether or not the position should be skipped
	 */
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset)
	{
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10 || candidateOffset == 6 || candidateOffset == 15);
	}
	
	/**
	 * If the Knight Piece is in the second column, we have to skip a few of the Candidate Positions to avoid invalid Positions
	 * @param currentPosition - The current position
	 * @param candidateOffset - The offset
	 * @return - Whether or not the position should be skipped
	 */
	private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset)
	{
		return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset == -10 || candidateOffset == 6);
	}
	
	/**
	 * If the Knight Piece is in the seventh column, we have to skip a few of the Candidate Positions to avoid invalid Positions
	 * @param currentPosition - The current position
	 * @param candidateOffset - The offset
	 * @return - Whether or not the position should be skipped
	 */
	private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset)
	{
		return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset == -6 || candidateOffset == 10);
	}
	
	/**
	 * If the Knight Piece is in the eighth column, we have to skip a few of the Candidate Positions to avoid invalid Positions
	 * @param currentPosition - The current position
	 * @param candidateOffset - The offset
	 * @return - Whether or not the position should be skipped
	 */
	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset)
	{
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -15 || candidateOffset == -6 || candidateOffset == 10 || candidateOffset == 17);
	}
}
