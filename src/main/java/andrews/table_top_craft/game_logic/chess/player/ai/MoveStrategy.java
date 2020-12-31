package andrews.table_top_craft.game_logic.chess.player.ai;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.moves.BaseMove;

public interface MoveStrategy
{
	BaseMove execute(Board board);
}
