package andrews.table_top_craft.game_logic.chess.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;

import andrews.table_top_craft.game_logic.chess.PieceColor;
import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.BoardUtils;
import andrews.table_top_craft.game_logic.chess.board.moves.BaseMove;
import andrews.table_top_craft.game_logic.chess.board.moves.PawnAttackMove;
import andrews.table_top_craft.game_logic.chess.board.moves.PawnEnPassantAttackMove;
import andrews.table_top_craft.game_logic.chess.board.moves.PawnJumpMove;
import andrews.table_top_craft.game_logic.chess.board.moves.PawnMove;
import andrews.table_top_craft.game_logic.chess.board.moves.PawnPromotion;

public class PawnPiece extends BasePiece
{
	/*
	 * A Pawn Piece can only move forwards, meaning adding or subtracting 8, there
	 * are a few special cases like AttckMoves, in which the Pawn Piece moves diagonally,
	 * another thing thats worth mentioning is, that we have to get the PieceColor Direction
	 * as the Black Pawn Pieces move into the opposite direction of the White ones, Pawns
	 * can also move 2 Tiles on their first move
	 */
	private final static int[] CANDIDATE_MOVE_COORDINATES = {7, 8, 9, 16};

	public PawnPiece(final PieceColor pieceColor, final int piecePosition)
	{
		super(PieceType.PAWN, piecePosition, pieceColor, true);
	}
	
	public PawnPiece(final PieceColor pieceColor, final int piecePosition, final boolean isFirstMove)
	{
		super(PieceType.PAWN, piecePosition, pieceColor, isFirstMove);
	}

	@Override
	public Collection<BaseMove> calculateLegalMoves(final Board board)
	{
		final List<BaseMove> legalMoves = new ArrayList<>();
		
		for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES)
		{
			final int candidateDestinationCoordinate = this.piecePosition + (currentCandidateOffset * this.pieceColor.getDirection());
			
			// We check if the DestinationCoordinate is still on the board, and skip if it isnt
			if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate))
			{
				continue;
			}
			
			// We make sure the Tile infront of the Pawn is not occupied
			// Normal Pawn Move
			if(currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied())
			{
				if(this.pieceColor.isPawnPromotionSquare(candidateDestinationCoordinate))
				{
					legalMoves.add(new PawnPromotion(new PawnMove(board, this, candidateDestinationCoordinate)));
				}
				else
				{
					legalMoves.add(new PawnMove(board, this, candidateDestinationCoordinate));
				}
			}
			// Jump Pawn Move (It Moves 2 Tiles)
			else if(currentCandidateOffset == 16 && this.isFirstMove() &&
				   ((BoardUtils.SEVENTH_RANK[this.piecePosition] && this.getPieceColor().isBlack()) ||
				   (BoardUtils.SECOND_RANK[this.piecePosition] && this.getPieceColor().isWhite())))
			{
				final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceColor.getDirection() * 8);
				
				// We make sure the Tile behind the DestinationCoordinate Tile is also empty and, add it to the List if it is
				if(!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() && !board.getTile(candidateDestinationCoordinate).isTileOccupied())
				{
					legalMoves.add(new PawnJumpMove(board, this, candidateDestinationCoordinate));
				}
			}
			// Attack Pawn Move
			else if(currentCandidateOffset == 7 &&
				 !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceColor.isWhite()) ||
				   (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceColor.isBlack())))
			{
				// We check if the diagonal Tile is occupied by a Piece
				if(board.getTile(candidateDestinationCoordinate).isTileOccupied())
				{
					final BasePiece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
					
					// We check if the Piece that occupies the Tile has the same color as this Piece, and add an AttackMove to the list if it doesn't
					if(this.pieceColor != pieceOnCandidate.getPieceColor())
					{
						if(this.pieceColor.isPawnPromotionSquare(candidateDestinationCoordinate))
						{
							legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate)));
						}
						else
						{
							legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
						}
					}
				}
				else if(board.getEnPassantPawn() != null)// We make sure there is an en passant Pawn Piece on the Board
				{
					if(board.getEnPassantPawn().getPiecePosition() == this.piecePosition + (this.pieceColor.getOppositeDirection()))
					{
						final BasePiece pieceOnCandidate = board.getEnPassantPawn();
						
						if(this.pieceColor != pieceOnCandidate.getPieceColor())
						{
							legalMoves.add(new PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
						}
					}
				}
			}
			// Attack Pawn Move
			else if(currentCandidateOffset == 9 &&
				 !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceColor.isWhite()) ||
				   (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceColor.isBlack())))
			{
				// We check if the diagonal Tile is occupied by a Piece
				if(board.getTile(candidateDestinationCoordinate).isTileOccupied())
				{
					final BasePiece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
					
					// We check if the Piece that occupies the Tile has the same color as this Piece, and add an AttackMove to the list if it doesn't
					if(this.pieceColor != pieceOnCandidate.getPieceColor())
					{
						if(this.pieceColor.isPawnPromotionSquare(candidateDestinationCoordinate))
						{
							legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate)));
						}
						else
						{
							legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
						}
					}
				}
				else if(board.getEnPassantPawn() != null)// We make sure there is an en passant Pawn Piece on the Board
				{
					if(board.getEnPassantPawn().getPiecePosition() == this.piecePosition - (this.pieceColor.getOppositeDirection()))
					{
						final BasePiece pieceOnCandidate = board.getEnPassantPawn();
						
						if(this.pieceColor != pieceOnCandidate.getPieceColor())
						{
							legalMoves.add(new PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
						}
					}
				}
			}
		}
		return ImmutableList.copyOf(legalMoves);
	}
	
	@Override
	public String toString()
	{
		return BasePiece.PieceType.PAWN.toString();
	}
	
	@Override
    public int locationBonus()
	{
        return this.pieceColor.pawnBonus(this.piecePosition);
    }
	
	@Override
	public PawnPiece movePiece(final BaseMove move)
	{
		return new PawnPiece(move.getMovedPiece().getPieceColor(), move.getDestinationCoordinate(), false);
	}
	
	/**
	 * @return - The Piece this Pawn is being promoted to
	 */
	public BasePiece getPromotionPiece()
	{
		// I'm setting it to a Pawn, because I decided to handle the promotion
		// Logic on the Block Entity level, which is easier since I have to wait
		// for additional input from the players.
		return new PawnPiece(this.pieceColor, this.piecePosition, false);
	}
}