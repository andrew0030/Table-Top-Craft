package andrews.table_top_craft.game_logic.chess.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import andrews.table_top_craft.game_logic.chess.PieceColor;
import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.moves.BaseMove;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;
import andrews.table_top_craft.game_logic.chess.pieces.KingPiece;

public abstract class BaseChessPlayer
{
	protected final Board board;
	protected final KingPiece playerKing;
	protected final Collection<BaseMove> legalMoves;
	private final boolean isInCheck;
	
	BaseChessPlayer(final Board board, final Collection<BaseMove> legalMoves, final Collection<BaseMove> opponentMoves)
	{
		this.board = board;
		this.playerKing = establishKing();
		this.isInCheck = !calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
		this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves, calculateKingCastles(legalMoves, opponentMoves)));
	}

	/**
	 * @return - The King Piece of this Chess Player
	 */
	public KingPiece getPlayerKing()
	{
		return this.playerKing;
	}
	
	/**
	 * @return - A Collection of the legal Moves this Chess Player has
	 */
	public Collection<BaseMove> getLegalMoves()
	{
		return this.legalMoves;
	}
	
	/**
	 * This Method goes through all Moves the Opponent has, and checks
	 * if any of them overlap with the Position of the King Piece, and
	 * adds them to the list, if they do
	 * @param piecePosition - The King Piece Position
	 * @param moves - The Opponents Moves
	 * @return - A Collection of Moves the enemy could do, to attack the King Piece
	 */
	protected static Collection<BaseMove> calculateAttacksOnTile(int piecePosition, Collection<BaseMove> moves)
	{
		final List<BaseMove> attackMoves = new ArrayList<>();
		
		// Iterates through all Moves the Opponent has
		for(final BaseMove move : moves)
		{
			if(piecePosition == move.getDestinationCoordinate())
			{
				attackMoves.add(move);
			}
		}
		return ImmutableList.copyOf(attackMoves);
	}

	/**
	 * We need the King because a Chess Game without a King would be really pointless
	 * @return - The KingPiece from all active Pieces
	 */
	private KingPiece establishKing()
	{
		for(final BasePiece piece : getActivePieces())
		{
			if(piece.getPieceType().isKing())
			{
				return (KingPiece) piece;
			}
		}
		throw new RuntimeException("The active Chess Pieces do not contain a King Piece! Not a valid Board!");
	}
	
	/**
	 * @param move - The Move that will be checked
	 * @return - Whether or not the given Move is a legal Move
	 */
	public boolean isMoveLegal(final BaseMove move)
	{
		return this.legalMoves.contains(move);
	}
	
	/**
	 * @return - Whether or not this Chess Player is in Check
	 */
	public boolean isInCheck()
	{
		return this.isInCheck;
	}
	
	/**
	 * @return - Whether or not this Chess Player is in Check Mate
	 */
	public boolean isInCheckMate()
	{
		return this.isInCheck && !hasEscapeMoves();
	}
	
	/**
	 * @return - Whether or not this Chess Player is in Stale Mate
	 */
	public boolean isInStaleMate()
	{
		return !this.isInCheck && !hasEscapeMoves();
	}
	
	/**
	 * @return - Whether or not this Chess player can perform a King side Castle Move
	 */
	public boolean isKingSideCastleCapable()
	{
		return this.playerKing.isKingSideCastleCapable();
	}
	
	/**
	 * @return - Whether or not this Chess player can perform a Queen side Castle Move
	 */
	public boolean isQueenSideCastleCapable()
	{
		return this.playerKing.isQueenSideCastleCapable();
	}
	
	/**
	 * @return - Whether or not the KingPiece can escape
	 */
	protected boolean hasEscapeMoves()
	{
		for(final BaseMove move : this.legalMoves)
		{
			final MoveTransition transition = makeMove(move);
			
			if(transition.getMoveStatus().isDone())
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean isCastled()
	{
		return false;
	}
	
	/**
	 * This Method basically takes in a Move and depending on the MoveStatus of
	 * the given Move it generates a new Chess Board, or it keeps the current one
	 * if the move was invalid
	 * @param move - The Move to make
	 * @return - A new MoveTransition
	 */
	public MoveTransition makeMove(final BaseMove move)
	{
		// Checks if the Move is illegal
		if(!isMoveLegal(move))
		{
			// If the Move was illegal we return the same Chess Board
			return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
		}
		
		final Board transitionBoard = move.execute();
		final Collection<BaseMove> kingAttacks = BaseChessPlayer.calculateAttacksOnTile(
												 transitionBoard.getCurrentChessPlayer().getOpponent().getPlayerKing().getPiecePosition(),
												 transitionBoard.getCurrentChessPlayer().getLegalMoves());
		if(!kingAttacks.isEmpty())
		{
			return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
		}
		return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
	}
	
	/**
	 * @return - A Collection of the active Pieces
	 */
	public abstract Collection<BasePiece> getActivePieces();
	
	/**
	 * @return - The PieceColor of the Piece
	 */
	public abstract PieceColor getPieceColor();
	
	/**
	 * @return - The BaseChessPlayer that is the Opponent of this BaseChessPlayer
	 */
	public abstract BaseChessPlayer getOpponent();
	
	protected abstract Collection<BaseMove> calculateKingCastles(Collection<BaseMove> playerLegals, Collection<BaseMove> opponentsLegals);
}
