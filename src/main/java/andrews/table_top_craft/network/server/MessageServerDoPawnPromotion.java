package andrews.table_top_craft.network.server;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.board.moves.BaseMove;
import andrews.table_top_craft.game_logic.chess.board.moves.PawnPromotion;
import andrews.table_top_craft.game_logic.chess.pieces.*;
import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.util.Reference;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MessageServerDoPawnPromotion
{
    public static ResourceLocation PACKET_ID = new ResourceLocation(Reference.MODID, "do_pawn_promotion_packet");

    public static void registerPacket()
    {
        ServerPlayNetworking.registerGlobalReceiver(PACKET_ID, (minecraftServer, serverPlayer, packetListener, buf, packetSender) ->
        {
            BlockPos pos = buf.readBlockPos();
            byte type = buf.readByte();

            minecraftServer.execute(() ->
            {
                if(serverPlayer == null)
                    return;

                Level level = serverPlayer.getLevel();
                if(level != null)
                {
                    BlockEntity blockEntity = level.getBlockEntity(pos);
                    // We make sure the TileEntity is a ChessBlockEntity
                    if(blockEntity instanceof ChessBlockEntity chessBlockEntity && chessBlockEntity.getWaitingForPromotion())
                    {
                        Board board = chessBlockEntity.getBoard();
                        Board.Builder builder = new Board.Builder();
                        for(int i = 0; i < 64; i++)
                        {
                            if (board.getTile(i).isTileOccupied())
                            {
                                if(chessBlockEntity.getPromotionCoordinate() == i)
                                {
                                    BasePiece piece = board.getTile(i).getPiece();
                                    switch (type) {
                                        default -> builder.setPiece(new QueenPiece(piece.getPieceColor(), piece.getPiecePosition()));
                                        case 3 -> builder.setPiece(new BishopPiece(piece.getPieceColor(), piece.getPiecePosition()));
                                        case 4 -> builder.setPiece(new KnightPiece(piece.getPieceColor(), piece.getPiecePosition()));
                                        case 2 -> builder.setPiece(new RookPiece(piece.getPieceColor(), piece.getPiecePosition()));
                                    }
                                }
                                else
                                {
                                    builder.setPiece(board.getTile(i).getPiece());
                                }
                            }
                        }
                        builder.setMoveMaker(board.getCurrentChessPlayer().getPieceColor());
                        chessBlockEntity.setBoard(builder.build());

                        if(chessBlockEntity.getMoveLog().getMoves().get(chessBlockEntity.getMoveLog().getMoves().size() - 1).isPawnPromotion())
                        {
                            BaseMove move = chessBlockEntity.getMoveLog().getMoves().get(chessBlockEntity.getMoveLog().getMoves().size() - 1);
                            switch (type) {
                                default -> chessBlockEntity.getMoveLog().getMoves().set(chessBlockEntity.getMoveLog().getMoves().size() - 1, new PawnPromotion(move, "Q"));
                                case 3 -> chessBlockEntity.getMoveLog().getMoves().set(chessBlockEntity.getMoveLog().getMoves().size() - 1, new PawnPromotion(move, "B"));
                                case 4 -> chessBlockEntity.getMoveLog().getMoves().set(chessBlockEntity.getMoveLog().getMoves().size() - 1, new PawnPromotion(move, "N"));
                                case 2 -> chessBlockEntity.getMoveLog().getMoves().set(chessBlockEntity.getMoveLog().getMoves().size() - 1, new PawnPromotion(move, "R"));
                            }
                        }

                        chessBlockEntity.setWaitingForPromotion(false);
                        chessBlockEntity.setPromotionCoordinate((byte) -1);
                        level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
                        chessBlockEntity.setChanged();
                    }
                }
            });
        });
    }
}