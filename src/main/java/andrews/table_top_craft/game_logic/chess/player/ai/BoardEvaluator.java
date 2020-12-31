package andrews.table_top_craft.game_logic.chess.player.ai;

import andrews.table_top_craft.game_logic.chess.board.Board;

public interface BoardEvaluator
{
	int evaluate(Board board, int depth);
}
