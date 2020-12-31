package andrews.table_top_craft.game_logic.chess.player.ai;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;
import andrews.table_top_craft.game_logic.chess.player.BaseChessPlayer;

public final class StandardBoardEvaluator implements BoardEvaluator
{
	private static final int CHECK_BONUS = 50;
	private static final int CHECK_MATE_BONUS = 10000;
	private static final int DEPTH_BONUS = 100;
	private static final int CASTLE_BONUS = 60;
	
	@Override
	public int evaluate(final Board board, final int depth)
	{
		return scorePlayer(board, board.getWhiteChessPlayer(), depth) - scorePlayer(board, board.getBlackChessPlayer(), depth);
	}

	private int scorePlayer(final Board board, final BaseChessPlayer player, final int depth)
	{
		return pieceValue(player) + mobility(player) + check(player) + checkMate(player, depth) + castled(player);
	}
	
	private static int castled(BaseChessPlayer player)
	{
		return player.isCastled() ? CASTLE_BONUS : 0;
	}

	private static int checkMate(BaseChessPlayer player, int depth)
	{
		return player.getOpponent().isInCheckMate() ? CHECK_MATE_BONUS * depthBonus(depth) : 0;
	}
	
	private static int depthBonus(int depth)
	{
		return depth == 0 ? 1 : DEPTH_BONUS * depth;
	}

	private static int check(BaseChessPlayer player)
	{
		return player.getOpponent().isInCheck() ? CHECK_BONUS : 0;
	}

	private int mobility(BaseChessPlayer player)
	{
		return player.getLegalMoves().size();
	}

	private static int pieceValue(final BaseChessPlayer player)
	{
		int pieceValueScore = 0;
		for(final BasePiece piece : player.getActivePieces())
		{
			pieceValueScore += piece.getPieceValue();
		}
		return pieceValueScore;
	}
}
