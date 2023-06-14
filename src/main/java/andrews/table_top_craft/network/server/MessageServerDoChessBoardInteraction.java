package andrews.table_top_craft.network.server;

import andrews.table_top_craft.criteria.TTCCriteriaTriggers;
import andrews.table_top_craft.game_logic.chess.board.moves.BaseMove;
import andrews.table_top_craft.game_logic.chess.board.moves.MoveFactory;
import andrews.table_top_craft.game_logic.chess.board.tiles.BaseChessTile;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;
import andrews.table_top_craft.game_logic.chess.player.MoveTransition;
import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.util.NetworkUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class MessageServerDoChessBoardInteraction
{
    private final BlockPos pos;
    private final byte tileCoordinate;

    public MessageServerDoChessBoardInteraction(BlockPos pos, byte tileCoordinate)
    {
        this.pos = pos;
        this.tileCoordinate = tileCoordinate;
    }

    public void serialize(FriendlyByteBuf buf)
    {
        buf.writeBlockPos(pos);
        buf.writeByte(tileCoordinate);
    }

    public static MessageServerDoChessBoardInteraction deserialize(FriendlyByteBuf buf)
    {
        BlockPos pos = buf.readBlockPos();
        byte tileCoordinate = buf.readByte();
        return new MessageServerDoChessBoardInteraction(pos, tileCoordinate);
    }

    public static void handle(MessageServerDoChessBoardInteraction message, Supplier<NetworkEvent.Context> ctx)
    {
        NetworkEvent.Context context = ctx.get();
        ServerPlayer player = context.getSender();
        BlockPos pos = message.pos;
        byte tileCoordinate = message.tileCoordinate;

        if(context.getDirection().getReceptionSide() == LogicalSide.SERVER)
        {
            context.enqueueWork(() ->
            {
                if(player != null)
                {
                    Level level = player.getLevel();
                    BlockEntity blockEntity = level.getBlockEntity(pos);

                    // We make sure the BlockEntity is a ChessBlockEntity
                    if(blockEntity instanceof ChessBlockEntity chessBlockEntity)
                    {
                        // We do not continue the game logic if there is no Chess
                        if(chessBlockEntity.getBoard() == null)
                            return;
                        // If an interaction is still happening we skip it
                        if(chessBlockEntity.doingAnimationTimer > 0 && chessBlockEntity.move != null && chessBlockEntity.transition != null)
                        {
                            chessBlockEntity.setBoard(chessBlockEntity.transition.getTransitionBoard());
                            chessBlockEntity.getMoveLog().addMove(chessBlockEntity.move);
                            if(chessBlockEntity.move.isPawnPromotion())
                            {
                                chessBlockEntity.setWaitingForPromotion(true);
                                chessBlockEntity.setPromotionCoordinate((byte) chessBlockEntity.move.getDestinationCoordinate());

                                List<ServerPlayer> players = player.getLevel().players();
                                for(ServerPlayer serverPlayer : players) {
                                    if(serverPlayer.getUUID().equals(chessBlockEntity.getPromotionPlayerUUID()))
                                        NetworkUtil.openChessPromotionFromServer(chessBlockEntity.getBlockPos(), chessBlockEntity.move.getMovedPiece().getPieceColor().isWhite(), serverPlayer);
                                }
                                chessBlockEntity.setPromotionPlayerUUID(null);
                            }
                            // Plays Particles on Attack Moves
                            if(chessBlockEntity.getDisplayParticles())
                                if(!chessBlockEntity.playedParticles && chessBlockEntity.move.isAttack() && !chessBlockEntity.move.isEnPassantMove())
                                    NetworkUtil.playChesParticlesFromServer(level, pos, (byte) chessBlockEntity.move.getDestinationCoordinate(), chessBlockEntity.move.getMovedPiece().getPieceColor().isBlack(), 0, 0, 0);
                            chessBlockEntity.doingAnimationTimer = 0;
                            chessBlockEntity.move = null;
                            chessBlockEntity.transition = null;
                            chessBlockEntity.playedParticles = false;
                            level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
                            chessBlockEntity.setChanged();
                        }

                        BaseChessTile chessTile = chessBlockEntity.getBoard().getTile(tileCoordinate);
                        // Checks if a Tile has already been selected
                        if(chessBlockEntity.getSourceTile() == null)
                        {
                            if(chessTile.isTileOccupied()) // We make sure the clicked Tile has a Piece, otherwise we shouldn't select it
                            {
                                if(chessTile.getPiece().getPieceColor() == chessBlockEntity.getBoard().getCurrentChessPlayer().getPieceColor())
                                {
                                    chessBlockEntity.setSourceTile(chessTile);
                                    chessBlockEntity.setHumanMovedPiece(chessTile.getPiece());
                                    if(chessBlockEntity.getHumanMovedPiece() == null)
                                        chessBlockEntity.setSourceTile(null);
                                    // Syncs the TileEntity
                                    level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
                                    // Used to mark the Block Entity, so it saves its data before Chunk unloading
                                    chessBlockEntity.setChanged();
                                }
                            }
                        }
                        else // Gets Called when a Tile is already selected
                        {
                            chessBlockEntity.setDestinationTile(chessTile);
                            final BaseMove move = MoveFactory.createMove(chessBlockEntity.getBoard(), chessBlockEntity.getSourceTile().getTileCoordinate(), chessBlockEntity.getDestinationTile().getTileCoordinate());
                            final MoveTransition transition = chessBlockEntity.getBoard().getCurrentChessPlayer().makeMove(move);

                            if(transition.getMoveStatus().isDone())
                            {
                                // If the move was a pawn promotion we store the player UUID of the person that made the move
                                if(move.isPawnPromotion())
                                    chessBlockEntity.setPromotionPlayerUUID(player.getUUID());

                                // If Piece Animations are enabled we set the timer, otherwise we make the move
                                if(chessBlockEntity.getPlayPieceAnimations())
                                {
                                    chessBlockEntity.move = move;
                                    chessBlockEntity.transition = transition;
                                    int animTime = 1000;
                                    if(move.getMovedPiece().getPieceType().isKing() && !move.isCastlingMove())
                                        animTime = 750;
                                    if(move.getMovedPiece().getPieceType().equals(BasePiece.PieceType.PAWN) && Math.abs(move.getDestinationCoordinate() - move.getCurrentCoordinate()) <= 9)
                                        animTime = 750;
                                    if(move.isEnPassantMove())
                                        animTime = 1000;
                                    if(move.getMovedPiece().getPieceType().equals(BasePiece.PieceType.ROOK)) {
                                        if(Math.abs((move.getCurrentCoordinate() % 8) - (move.getDestinationCoordinate() % 8)) == 1 || Math.abs((move.getCurrentCoordinate() / 8) - (move.getDestinationCoordinate() / 8)) == 1) {
                                            animTime = 750;
                                        } else if (Math.abs((move.getCurrentCoordinate() % 8) - (move.getDestinationCoordinate() % 8)) > 3 || Math.abs((move.getCurrentCoordinate() / 8) - (move.getDestinationCoordinate() / 8)) > 3) {
                                            animTime = 1250;
                                        }
                                    }
                                    if(move.getMovedPiece().getPieceType().equals(BasePiece.PieceType.BISHOP)) {
                                        if(Math.abs((move.getCurrentCoordinate() % 8) - (move.getDestinationCoordinate() % 8)) == 1) {
                                            animTime = 750;
                                        } else if (Math.abs((move.getCurrentCoordinate() % 8) - (move.getDestinationCoordinate() % 8)) > 3) {
                                            animTime = 1250;
                                        }
                                    }
                                    if(move.getMovedPiece().getPieceType().equals(BasePiece.PieceType.QUEEN)) {
                                        if(Math.abs((move.getCurrentCoordinate() % 8) - (move.getDestinationCoordinate() % 8)) == 1 || Math.abs((move.getCurrentCoordinate() / 8) - (move.getDestinationCoordinate() / 8)) == 1) {
                                            animTime = 750;
                                        } else if (Math.abs((move.getCurrentCoordinate() % 8) - (move.getDestinationCoordinate() % 8)) > 3 || Math.abs((move.getCurrentCoordinate() / 8) - (move.getDestinationCoordinate() / 8)) > 3) {
                                            animTime = 1250;
                                        }
                                    }
                                    chessBlockEntity.doingAnimationTimer = System.currentTimeMillis() + animTime;//1000 ms are 1 second
                                    NetworkUtil.setChessAnimationForAllTracking(level, pos, (byte) 2, (byte) move.getCurrentCoordinate(), (byte) move.getDestinationCoordinate());
                                }
                                else
                                {
                                    chessBlockEntity.setBoard(transition.getTransitionBoard());
                                    // Adds the move to the MoveLog
                                    chessBlockEntity.getMoveLog().addMove(move);
                                    // If the Move was a Pawn Promotion, we make the Board wait for the Promotion choice to be made
                                    if(move.isPawnPromotion())
                                    {
                                        chessBlockEntity.setWaitingForPromotion(true);
                                        chessBlockEntity.setPromotionCoordinate((byte) move.getDestinationCoordinate());

                                        List<ServerPlayer> players = player.getLevel().players();
                                        for(ServerPlayer serverPlayer : players) {
                                            if(serverPlayer.getUUID().equals(chessBlockEntity.getPromotionPlayerUUID()))
                                                NetworkUtil.openChessPromotionFromServer(chessBlockEntity.getBlockPos(), move.getMovedPiece().getPieceColor().isWhite(), serverPlayer);
                                        }
                                        chessBlockEntity.setPromotionPlayerUUID(null);
                                    }
                                    // Plays Particles on Attack Moves
                                    if(chessBlockEntity.getDisplayParticles())
                                        if(move.isAttack() && !move.isEnPassantMove())
                                            NetworkUtil.playChesParticlesFromServer(level, pos, (byte) move.getDestinationCoordinate(), move.getMovedPiece().getPieceColor().isBlack(), 0, 0, 0);
                                }
                                // We call this in here to make sure a move was successfully made, and not just attempted
                                if(!level.isClientSide)
                                    TTCCriteriaTriggers.MAKE_CHESS_MOVE.trigger(player);
                                // If the Move was an EnPassantMove, we trigger the advancement
                                if(move.isEnPassantMove())
                                    if(!level.isClientSide)
                                        TTCCriteriaTriggers.MAKE_EN_PASSANT_MOVE.trigger(player);
                                // If the Move was a CheckMateMove, we trigger the advancement
                                if(chessBlockEntity.getBoard().getCurrentChessPlayer().isInCheckMate())
                                    if(!level.isClientSide)
                                        TTCCriteriaTriggers.MAKE_CHECK_MATE_MOVE.trigger(player);
                            }
                            chessBlockEntity.setSourceTile(null);
                            chessBlockEntity.setDestinationTile(null);
                            chessBlockEntity.setHumanMovedPiece(null);
                            // Syncs the TileEntity
                            level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
                            // Used to mark the Block Entity, so it saves its data before Chunk unloading
                            chessBlockEntity.setChanged();
                        }
                    }
                }
            });
            context.setPacketHandled(true);
        }
    }
}