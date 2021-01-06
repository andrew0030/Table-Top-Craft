package andrews.table_top_craft.game_logic.chess.player.ai;

import java.util.Collection;
import java.util.stream.Collectors;

import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;
import andrews.table_top_craft.game_logic.chess.player.BaseChessPlayer;

public final class PawnStructureAnalyzer
{
	public static final int ISOLATED_PAWN_PENALTY = -10;
	public static final int DOUBLED_PAWN_PENALTY = -10;

	private PawnStructureAnalyzer() {}

	public int isolatedPawnPenalty(final BaseChessPlayer player)
	{
		return calculateIsolatedPawnPenalty(createPawnColumnTable(calculatePlayerPawns(player)));
	}

	public int doubledPawnPenalty(final BaseChessPlayer player)
	{
		return calculatePawnColumnStack(createPawnColumnTable(calculatePlayerPawns(player)));
	}

	public static int pawnStructureScore(final BaseChessPlayer player)
	{
		final int[] pawnsOnColumnTable = createPawnColumnTable(calculatePlayerPawns(player));
		return calculatePawnColumnStack(pawnsOnColumnTable) + calculateIsolatedPawnPenalty(pawnsOnColumnTable);
	}

	private static Collection<BasePiece> calculatePlayerPawns(final BaseChessPlayer player)
	{
		return player.getActivePieces().stream().filter(piece -> piece.getPieceType() == BasePiece.PieceType.PAWN).collect(Collectors.toList());
	}

	private static int calculatePawnColumnStack(final int[] pawnsOnColumnTable)
	{
		int pawnStackPenalty = 0;
		for(final int pawnStack : pawnsOnColumnTable)
		{
			if(pawnStack > 1)
			{
				pawnStackPenalty += pawnStack;
			}
		}
		return pawnStackPenalty * DOUBLED_PAWN_PENALTY;
	}

	private static int calculateIsolatedPawnPenalty(final int[] pawnsOnColumnTable)
	{
		int numIsolatedPawns = 0;
		if(pawnsOnColumnTable[0] > 0 && pawnsOnColumnTable[1] == 0)
		{
			numIsolatedPawns += pawnsOnColumnTable[0];
		}
		if(pawnsOnColumnTable[7] > 0 && pawnsOnColumnTable[6] == 0)
		{
			numIsolatedPawns += pawnsOnColumnTable[7];
		}
		for(int i = 1; i < pawnsOnColumnTable.length - 1; i++)
		{
			if((pawnsOnColumnTable[i - 1] == 0 && pawnsOnColumnTable[i + 1] == 0))
			{
				numIsolatedPawns += pawnsOnColumnTable[i];
			}
		}
		return numIsolatedPawns * ISOLATED_PAWN_PENALTY;
	}

	private static int[] createPawnColumnTable(final Collection<BasePiece> playerPawns)
	{
		final int[] table = new int[8];
		for(final BasePiece playerPawn : playerPawns)
		{
			table[playerPawn.getPiecePosition() % 8]++;
		}
		return table;
	}
}