package andrews.table_top_craft.game_logic.chess.board.moves;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.BoardUtils;
import andrews.table_top_craft.game_logic.chess.board.Board.Builder;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;
import andrews.table_top_craft.game_logic.chess.pieces.PawnPiece;

public class PawnPromotion extends BaseMove
{
	final BaseMove decoratedMove;
	final PawnPiece promotedPawn;
	final String promotionChar;

	public PawnPromotion(final BaseMove decoratedMove)
	{
		super(decoratedMove.getBoard(), decoratedMove.getMovedPiece(), decoratedMove.getDestinationCoordinate());
		this.decoratedMove = decoratedMove;
		this.promotedPawn = (PawnPiece) decoratedMove.getMovedPiece();
		this.promotionChar = "?";
	}

	public PawnPromotion(final BaseMove decoratedMove, String promotionChar)
	{
		super(decoratedMove.getBoard(), decoratedMove.getMovedPiece(), decoratedMove.getDestinationCoordinate());
		this.decoratedMove = decoratedMove;
		this.promotedPawn = (PawnPiece) decoratedMove.getMovedPiece();
		this.promotionChar = promotionChar;
	}
	
	@Override
	public int hashCode()
	{
		return this.decoratedMove.hashCode() + (31 * promotedPawn.hashCode());
	}
	
	@Override
	public boolean equals(final Object object)
	{
		return this == object || object instanceof PawnPromotion && (super.equals(object));
	}
	
	@Override
	public Board execute()
	{
		final Board pawnMoveBoard = this.decoratedMove.execute();
		final Builder builder = new Builder();
		
		for(final BasePiece piece : pawnMoveBoard.getCurrentChessPlayer().getActivePieces())
		{
			if(!this.promotedPawn.equals(piece))
			{
				builder.setPiece(piece);
			}
		}
		for(final BasePiece piece : pawnMoveBoard.getCurrentChessPlayer().getOpponent().getActivePieces())
		{
			builder.setPiece(piece);
		}
		builder.setPiece(this.promotedPawn.getPromotionPiece().movePiece(this));
		builder.setMoveMaker(pawnMoveBoard.getCurrentChessPlayer().getPieceColor());
		return builder.build();
	}
	
	@Override
	public boolean isAttack()
	{
		return this.decoratedMove.isAttack();
	}

	@Override
	public boolean isPawnPromotion()
	{
		return true;
	}

	@Override
	public BasePiece getAttackedPiece()
	{
		return this.decoratedMove.getAttackedPiece();
	}
	
	@Override
	public String toString()
	{
		return this.decoratedMove.toString() + "=" + this.promotionChar;
	}
	
	@Override
	public String saveToNBT()
	{
		if(this.decoratedMove.isAttack())
			return "pawn_attack_promotion/" + getColorForPiece(this.promotedPawn) + "/" + this.promotedPawn.getPiecePosition() + "/" + this.decoratedMove.getDestinationCoordinate() + "/" + this.decoratedMove.getAttackedPiece().getPiecePosition() + "/" + this.decoratedMove.getAttackedPiece().getPieceType().toString() + "/" + this.promotionChar;
		return "pawn_promotion/" + getColorForPiece(this.promotedPawn) + "/" + this.promotedPawn.getPiecePosition() + "/" + this.decoratedMove.getDestinationCoordinate() + "/" + this.promotionChar;
	}
}