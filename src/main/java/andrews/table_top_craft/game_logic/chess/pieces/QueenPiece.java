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

public class QueenPiece extends BasePiece
{
	/*
	 * If the Queen is in the middle of the Board, there would be a maximum of 8 possible
	 * Directions it could move on, in order to determine those Coordinates, we can simply
	 * subtract or add the same 4 values, again and again
	 * 
	 * Visual Representation:
	 * [   ][   ][   ](-32)[   ][   ][   ](-28)
	 * (-27)[   ][   ](-24)[   ][   ](-21)[   ]
	 * [   ](-18)[   ](-16)[   ](-14)[   ][   ]
	 * [   ][   ](-9 )(-8 )(-7 )[   ][   ][   ]
	 * (-3 )(-2 )(-1 )[ Q ]( 1 )( 2 )( 3 )( 4 )
	 * [   ][   ]( 7 )( 8 )( 9 )[   ][   ][   ]
	 * [   ](14 )[   ](16 )[   ](18 )[   ][   ]
	 * (21 )[   ][   ](24 )[   ][   ](27 )[   ]
	 */
	private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-9, -8, -7, -1, 1, 7, 8, 9};
	
	public QueenPiece(final PieceColor pieceColor, final int piecePosition)
	{
		super(PieceType.QUEEN, piecePosition, pieceColor, true);
	}
	
	public QueenPiece(final PieceColor pieceColor, final int piecePosition, final boolean isFirstMove)
	{
		super(PieceType.QUEEN, piecePosition, pieceColor, isFirstMove);
	}
	
	@Override
	public Collection<BaseMove> calculateLegalMoves(final Board board)
	{
		final List<BaseMove> legalMoves = new ArrayList<>();
		
		for(final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES)
		{
			int candidateDestinationCoordinate = this.piecePosition;
			
			// We make sure the DestinationCoordinate is on the Board
			while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate))
			{
				// Some special cases in which we have to break the loop
				if(isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) ||
					isEighthColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset))
				{
					break;
				}
				
				candidateDestinationCoordinate += candidateCoordinateOffset;
				
				// We check if the DestinationCoordinate is still on the Board after applying the Offset
				if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate))
				{
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
						// We stop the loop as the Queen can't go over other Pieces
						break;
					}
				}
			}
		}
		return ImmutableList.copyOf(legalMoves);
	}
	
	@Override
	public String toString()
	{
		return BasePiece.PieceType.QUEEN.toString();
	}
	
	@Override
	public QueenPiece movePiece(final BaseMove move)
	{
		return new QueenPiece(move.getMovedPiece().getPieceColor(), move.getDestinationCoordinate(), false);
	}
	
	/**
	 * If the Queen Piece is in the first column, we have to skip a few of the Candidate Positions to avoid invalid Positions
	 * @param currentPosition - The current position
	 * @param candidateOffset - The offset
	 * @return - Whether or not the position should be skipped
	 */
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset)
	{
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == -1 || candidateOffset == 7);
	}
	
	/**
	 * If the Queen Piece is in the eighth column, we have to skip a few of the Candidate Positions to avoid invalid Positions
	 * @param currentPosition - The current position
	 * @param candidateOffset - The offset
	 * @return - Whether or not the position should be skipped
	 */
	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset)
	{
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 1 || candidateOffset == 9);
	}
}
