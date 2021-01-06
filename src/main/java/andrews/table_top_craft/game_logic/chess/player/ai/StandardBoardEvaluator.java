package andrews.table_top_craft.game_logic.chess.player.ai;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.moves.BaseMove;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece.PieceType;
import andrews.table_top_craft.game_logic.chess.player.BaseChessPlayer;
import net.minecraft.util.text.TranslationTextComponent;

public final class StandardBoardEvaluator implements BoardEvaluator
{
	private static final int CHECK_BONUS = 50;
	private static final int CHECK_MATE_BONUS = 10000;
	private static final int DEPTH_BONUS = 100;
	private static final int CASTLE_BONUS = 60;
	private final static int TWO_BISHOPS_BONUS = 25;
	private static final StandardBoardEvaluator INSTANCE = new StandardBoardEvaluator();
	
	public static StandardBoardEvaluator get()
	{
        return INSTANCE;
    }
	
	@Override
	public int evaluate(final Board board, final int depth)
	{
		return scorePlayer(board, board.getWhiteChessPlayer(), depth) - scorePlayer(board, board.getBlackChessPlayer(), depth);
	}
	
	public String evaluationDetails(final Board board, final int depth)
	{
		// White Team
		String whiteMobility = new TranslationTextComponent("gui.table_top_craft.chess.evaluation.white.mobility", mobility(board.getWhiteChessPlayer())).getString();
		String whiteKingThreats = new TranslationTextComponent("gui.table_top_craft.chess.evaluation.white.king_threats", kingThreats(board.getWhiteChessPlayer(), depth)).getString();
		String whitePossibleAttacks = new TranslationTextComponent("gui.table_top_craft.chess.evaluation.white.possible_attacks", attacks(board.getWhiteChessPlayer())).getString();
		String whiteCastled = new TranslationTextComponent("gui.table_top_craft.chess.evaluation.white.castled", castle(board.getWhiteChessPlayer())).getString();
		String whitePieceValue = new TranslationTextComponent("gui.table_top_craft.chess.evaluation.white.piece_value", pieceEvaluations(board.getWhiteChessPlayer())).getString();
		String whitePawnStructure = new TranslationTextComponent("gui.table_top_craft.chess.evaluation.white.pawn_structure", pawnStructure(board.getWhiteChessPlayer())).getString();
		// Black Team
		String blackMobility = new TranslationTextComponent("gui.table_top_craft.chess.evaluation.black.mobility", mobility(board.getBlackChessPlayer())).getString();
		String blackKingThreats = new TranslationTextComponent("gui.table_top_craft.chess.evaluation.black.king_threats", kingThreats(board.getBlackChessPlayer(), depth)).getString();
		String blackPossibleAttacks = new TranslationTextComponent("gui.table_top_craft.chess.evaluation.black.possible_attacks", attacks(board.getBlackChessPlayer())).getString();
		String blackCastled = new TranslationTextComponent("gui.table_top_craft.chess.evaluation.black.castled", castle(board.getBlackChessPlayer())).getString();
		String blackPieceValue = new TranslationTextComponent("gui.table_top_craft.chess.evaluation.black.piece_value", pieceEvaluations(board.getBlackChessPlayer())).getString();
		String blackPawnStructure = new TranslationTextComponent("gui.table_top_craft.chess.evaluation.black.pawn_structure", pawnStructure(board.getBlackChessPlayer())).getString();
		// Final Score
		String finalScore = new TranslationTextComponent("gui.table_top_craft.chess.evaluation.final_score", evaluate(board, depth)).getString();
        return
        	whiteMobility + "\n" +
        	whiteKingThreats + "\n" +
            whitePossibleAttacks + "\n" +
            whiteCastled + "\n" +
            whitePieceValue + "\n" +
            whitePawnStructure + "\n" +
            "\n" +
            blackMobility + "\n" +
            blackKingThreats + "\n" +
            blackPossibleAttacks + "\n" +
            blackCastled + "\n" +
            blackPieceValue + "\n" +
            blackPawnStructure + "\n" +
            "\n" +
            finalScore;
    }

	private static int kingThreats(final BaseChessPlayer player, final int depth)
	{
		return player.getOpponent().isInCheckMate() ? CHECK_MATE_BONUS  * depthBonus(depth) : check(player);
	}
	
	private static int attacks(final BaseChessPlayer player)
	{
        int attackScore = 0;
        for(final BaseMove move : player.getLegalMoves())
        {
            if(move.isAttack())
            {
                final BasePiece movedPiece = move.getMovedPiece();
                final BasePiece attackedPiece = move.getAttackedPiece();
                if(movedPiece.getPieceValue() <= attackedPiece.getPieceValue())
                {
                    attackScore++;
                }
            }
        }
        return attackScore;
    }
	
	private static int castle(final BaseChessPlayer player)
	{
		return player.isCastled() ? CASTLE_BONUS : 0;
	}
	
	private static int pieceEvaluations(final BaseChessPlayer player)
	{
        int pieceValuationScore = 0;
        int numBishops = 0;
        for(final BasePiece piece : player.getActivePieces())
        {
            pieceValuationScore += piece.getPieceValue() + piece.locationBonus();
            if(piece.getPieceType() == PieceType.BISHOP)
            {
                numBishops++;
            }
        }
        return pieceValuationScore + (numBishops == 2 ? TWO_BISHOPS_BONUS : 0);
    }
	
	private static int pawnStructure(final BaseChessPlayer player)
	{
        return PawnStructureAnalyzer.pawnStructureScore(player);
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
