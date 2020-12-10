package andrews.table_top_craft.game_logic.chess.pieces;

import java.util.Collection;

import andrews.table_top_craft.game_logic.chess.PieceColor;
import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.moves.BaseMove;

public abstract class BasePiece
{
	protected final int piecePosition;
	protected final PieceColor pieceColor;
	
	BasePiece(final int piecePosition, final PieceColor pieceColor)
	{
		this.piecePosition = piecePosition;
		this.pieceColor = pieceColor;
	}
	
	public PieceColor getPieceColor()
	{
		return this.pieceColor;
	}
	
	public abstract Collection<BaseMove> calculateLegalMoves(final Board board);
}