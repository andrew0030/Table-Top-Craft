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

	@Override
	protected Collection<BaseMove> calculateKingCastles(final Collection<BaseMove> playerLegals, final Collection<BaseMove> opponentsLegals)
	{
		final List<BaseMove> kingCastles = new ArrayList<>();
		
		if(this.playerKing.isFirstMove() && !this.isInCheck())
		{
			// We check if the tiles in between the King and Rook are not occupied as thats a requirement for castling
			// Blacks King Side castle
			if(!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied())
			{
				final BaseChessTile rookTile = this.board.getTile(7);
				
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove())
				{
					if(BaseChessPlayer.calculateAttacksOnTile(5, opponentsLegals).isEmpty() &&
					   BaseChessPlayer.calculateAttacksOnTile(6, opponentsLegals).isEmpty() &&
					   rookTile.getPiece().getPieceType().isRook())
					{
						kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 6, (RookPiece) rookTile.getPiece(), rookTile.getTileCoordinate(), 5));
					}
				}
			}
			// Blacks Queen Side castle
			if(!this.board.getTile(1).isTileOccupied() && !this.board.getTile(2).isTileOccupied() && !this.board.getTile(3).isTileOccupied())
			{
				final BaseChessTile rookTile = this.board.getTile(0);
				
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove())
				{
					if(BaseChessPlayer.calculateAttacksOnTile(2, opponentsLegals).isEmpty() &&
					   BaseChessPlayer.calculateAttacksOnTile(3, opponentsLegals).isEmpty() &&
					   rookTile.getPiece().getPieceType().isRook())
					{
						kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing, 2, (RookPiece) rookTile.getPiece(), rookTile.getTileCoordinate(), 3));
					}
				}
			}
		}
		return ImmutableList.copyOf(kingCastles);
	}
	
	@Override
    public String toString()
	{
        return PieceColor.BLACK.toString();
    }
}
