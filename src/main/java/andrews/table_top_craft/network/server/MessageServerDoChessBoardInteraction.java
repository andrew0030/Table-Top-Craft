package andrews.table_top_craft.network.server;

import andrews.table_top_craft.animation.system.core.AdvancedAnimationState;
import andrews.table_top_craft.criteria.TTCCriteriaTriggers;
import andrews.table_top_craft.game_logic.chess.board.moves.BaseMove;
import andrews.table_top_craft.game_logic.chess.board.moves.MoveFactory;
import andrews.table_top_craft.game_logic.chess.board.moves.PawnEnPassantAttackMove;
import andrews.table_top_craft.game_logic.chess.board.tiles.BaseChessTile;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;
import andrews.table_top_craft.game_logic.chess.player.MoveTransition;
import andrews.table_top_craft.network.client.util.ClientPacketHandlerClass;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.NetworkUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

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
        Level level = player.getLevel();
        BlockPos pos = message.pos;
        byte tileCoordinate = message.tileCoordinate;

        if(context.getDirection().getReceptionSide() == LogicalSide.SERVER)
        {
            context.enqueueWork(() ->
            {
                if(level != null)
                {
                    BlockEntity blockEntity = level.getBlockEntity(pos);

                    // We make sure the BlockEntity is a ChessTileEntity
                    if(blockEntity instanceof ChessTileEntity chessTileEntity)
                    {
                        // We do not continue the game logic if there is no Chess
                        if(chessTileEntity.getBoard() == null)
                            return;
                        // If an interaction is still happening we skip it
                        if(chessTileEntity.doingAnimationTimer > 0 && chessTileEntity.move != null && chessTileEntity.transition != null)
                        {
                            chessTileEntity.setBoard(chessTileEntity.transition.getTransitionBoard());
                            chessTileEntity.getMoveLog().addMove(chessTileEntity.move);
                            chessTileEntity.doingAnimationTimer = 0;
                            chessTileEntity.move = null;
                            chessTileEntity.transition = null;
                            level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
                            chessTileEntity.setChanged();
                        }

                        BaseChessTile chessTile = chessTileEntity.getBoard().getTile(tileCoordinate);
                        // Checks if a Tile has already been selected
                        if(chessTileEntity.getSourceTile() == null)
                        {
                            if(chessTile.isTileOccupied()) // We make sure the clicked Tile has a Piece, otherwise we shouldn't select it
                            {
                                if(chessTile.getPiece().getPieceColor() == chessTileEntity.getBoard().getCurrentChessPlayer().getPieceColor())
                                {
                                    chessTileEntity.setSourceTile(chessTile);
                                    chessTileEntity.setHumanMovedPiece(chessTile.getPiece());
                                    if(chessTileEntity.getHumanMovedPiece() == null)
                                        chessTileEntity.setSourceTile(null);
                                    // Syncs the TileEntity
                                    level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
                                    // Used to mark the Block Entity, so it saves its data before Chunk unloading
                                    chessTileEntity.setChanged();
                                }
                            }
                        }
                        else // Gets Called when a Tile is already selected
                        {
                            chessTileEntity.setDestinationTile(chessTile);
                            final BaseMove move = MoveFactory.createMove(chessTileEntity.getBoard(), chessTileEntity.getSourceTile().getTileCoordinate(), chessTileEntity.getDestinationTile().getTileCoordinate());
                            final MoveTransition transition = chessTileEntity.getBoard().getCurrentChessPlayer().makeMove(move);

                            if(transition.getMoveStatus().isDone())
                            {
//                                chessTileEntity.setBoard(transition.getTransitionBoard());
//                                // Adds the move to the MoveLog
//                                chessTileEntity.getMoveLog().addMove(move);
                                // We call this in here to make sure a move was successfully made, and not just attempted
                                if(!level.isClientSide)
                                    TTCCriteriaTriggers.MAKE_CHESS_MOVE.trigger(player);
                                // If the Move was an EnPassantMove, we trigger the advancement
                                if(move.isEnPassantMove())
                                    if(!level.isClientSide)
                                        TTCCriteriaTriggers.MAKE_EN_PASSANT_MOVE.trigger(player);
                                // If the Move was a CheckMateMove, we trigger the advancement
                                if(chessTileEntity.getBoard().getCurrentChessPlayer().isInCheckMate())
                                    if(!level.isClientSide)
                                        TTCCriteriaTriggers.MAKE_CHECK_MATE_MOVE.trigger(player);

                                chessTileEntity.move = move;
                                chessTileEntity.transition = transition;
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
                                chessTileEntity.doingAnimationTimer = System.currentTimeMillis() + animTime;//1000 ms are 1 second

                                NetworkUtil.setChessAnimationForAllTracking(level, pos, (byte) 2, (byte) move.getCurrentCoordinate(), (byte) move.getDestinationCoordinate());
                            }
                            chessTileEntity.setSourceTile(null);
                            chessTileEntity.setDestinationTile(null);
                            chessTileEntity.setHumanMovedPiece(null);
                            // Syncs the TileEntity
                            level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
                            // Used to mark the Block Entity, so it saves its data before Chunk unloading
                            chessTileEntity.setChanged();
                        }
                    }
                }
            });
            context.setPacketHandled(true);
        }
    }
}