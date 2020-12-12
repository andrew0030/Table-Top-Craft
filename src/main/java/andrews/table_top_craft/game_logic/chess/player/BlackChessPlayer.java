package andrews.table_top_craft.game_logic.chess.player;

import java.util.Collection;

import andrews.table_top_craft.game_logic.chess.PieceColor;
import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.moves.BaseMove;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;

public class BlackChessPlayer extends BaseChessPlayer
{
	public BlackChessPlayer(final Board board, final Collection<BaseMove> whiteStandardLegalMoves, final Collection<BaseMove> blackStandardLegalMoves)
	{
		super(board, blackStandardLegalMoves, whiteStandardLegalMoves);
	}

	@Override
	public Collection<BasePiece> getActivePieces()
	{
		return this.board.getBlackPieces();
	}

	@Override
	public PieceColor getPieceColor()
	{
		return PieceColor.BLACK;
	}

	@Override
	public BaseChessPlayer getOpponent()
	{
		return this.board.getWhiteChessPlayer();
	}
}
