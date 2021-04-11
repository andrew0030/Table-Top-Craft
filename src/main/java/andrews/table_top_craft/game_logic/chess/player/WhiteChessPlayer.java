package andrews.table_top_craft.game_logic.chess.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;

import andrews.table_top_craft.game_logic.chess.PieceColor;
import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.moves.BaseMove;
import andrews.table_top_craft.game_logic.chess.board.moves.KingSideCastleMove;
import andrews.table_top_craft.game_logic.chess.board.moves.QueenSideCastleMove;
import andrews.table_top_craft.game_logic.chess.board.tiles.BaseChessTile;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;
import andrews.table_top_craft.game_logic.chess.pieces.RookPiece;

public class WhiteChessPlayer extends BaseChessPlayer
{
	public WhiteChessPlayer(final Board board, final Collection<BaseMove> whiteStandardLegalMoves, final Collection<BaseMove> blackStandardLegalMoves)
	{
		super(board, whiteStandardLegalMoves, blackStandardLegalMoves);
	}

	@Override
	public Collection<BasePiece> getActivePieces()
	{
		return this.board.getWhitePieces();
	}

	@Override
	public PieceColor getPieceColor()
	{
		return PieceColor.WHITE;
	}

	@Override
	public BaseChessPlayer getOpponent()
	{
		return this.board.getBlackChessPlayer();
	}
	
	@Override
	public boolean isCastled()
	{
		return this.playerKing.isCastled();
	}

	@Override
	protected Collection<BaseMove> calculateKingCastles(final Collection<BaseMove> playerLegals, final Collection<BaseMove> opponentsLegals)
	{
		final List<BaseMove> kingCastles = new ArrayList<>();
		
		if(this.playerKing.isFirstMove() && !this.isInCheck())
		{
			// We check if the tiles in between the King and Rook are not occupied as thats a requirement for castling
			// Whites King Side castle
			if(!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied())
			{
				final BaseChessTile rookTile = this.board.getTile(63);
				
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove())
				{
					if(BaseChessPlayer.calculateAttacksOnTile(61, opponentsLegals).isEmpty() &&
					   BaseChessPlayer.calculateAttacksOnTile(62, opponentsLegals).isEmpty() &&
					   rookTile.getPiece().getPieceType().isRook() &&
					   this.playerKing.isKingSideCastleCapable())
					{
						kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 62, (RookPiece) rookTile.getPiece(), rookTile.getTileCoordinate(), 61));
					}
				}
			}
			// Whites Queen Side castle
			if(!this.board.getTile(59).isTileOccupied() && !this.board.getTile(58).isTileOccupied() && !this.board.getTile(57).isTileOccupied())
			{
				final BaseChessTile rookTile = this.board.getTile(56);
				
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove())
				{
					if(BaseChessPlayer.calculateAttacksOnTile(58, opponentsLegals).isEmpty() &&
					   BaseChessPlayer.calculateAttacksOnTile(59, opponentsLegals).isEmpty() &&
					   rookTile.getPiece().getPieceType().isRook() &&
					   this.playerKing.isQueenSideCastleCapable())
					{
						kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing, 58, (RookPiece) rookTile.getPiece(), rookTile.getTileCoordinate(), 59));
					}
				}
			}
		}
		return ImmutableList.copyOf(kingCastles);
	}
	
	@Override
    public String toString()
	{
        return PieceColor.WHITE.toString();
    }
}
