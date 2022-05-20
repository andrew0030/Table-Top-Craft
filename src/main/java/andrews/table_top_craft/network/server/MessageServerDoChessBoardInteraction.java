package andrews.table_top_craft.network.server;

import andrews.table_top_craft.criteria.TTCCriteriaTriggers;
import andrews.table_top_craft.game_logic.chess.board.moves.BaseMove;
import andrews.table_top_craft.game_logic.chess.board.moves.MoveFactory;
import andrews.table_top_craft.game_logic.chess.board.tiles.BaseChessTile;
import andrews.table_top_craft.game_logic.chess.player.MoveTransition;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import andrews.table_top_craft.util.Reference;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MessageServerDoChessBoardInteraction
{
    public static ResourceLocation PACKET_ID = new ResourceLocation(Reference.MODID, "do_chess_board_interaction_packet");

    public static void registerPacket()
    {
        ServerPlayNetworking.registerGlobalReceiver(PACKET_ID, (minecraftServer, serverPlayer, packetListener, buf, packetSender) ->
        {
            BlockPos pos = buf.readBlockPos();
            byte tileCoordinate = buf.readByte();

            minecraftServer.execute(() ->
            {
                if(serverPlayer == null)
                    return;

                Level level = serverPlayer.getLevel();
                if(level != null)
                {
                    BlockEntity blockEntity = level.getBlockEntity(pos);

                    // We make sure the BlockEntity is a ChessTileEntity
                    if(blockEntity instanceof ChessTileEntity chessTileEntity)
                    {
                        // We do not continue the game logic if there is no Chess
                        if(chessTileEntity.getBoard() == null)
                            return;
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
                                chessTileEntity.setBoard(transition.getTransitionBoard());
                                // Adds the move to the MoveLog
                                chessTileEntity.getMoveLog().addMove(move);
                                // We call this in here to make sure a move was successfully made, and not just attempted
                                if(!level.isClientSide)
                                    TTCCriteriaTriggers.MAKE_CHESS_MOVE.trigger(serverPlayer);
                                // If the Move was an EnPassantMove, we trigger the advancement
                                if(move.isEnPassantMove())
                                    if(!level.isClientSide)
                                        TTCCriteriaTriggers.MAKE_EN_PASSANT_MOVE.trigger(serverPlayer);
                                // If the Move was a CheckMateMove, we trigger the advancement
                                if(chessTileEntity.getBoard().getCurrentChessPlayer().isInCheckMate())
                                    if(!level.isClientSide)
                                        TTCCriteriaTriggers.MAKE_CHECK_MATE_MOVE.trigger(serverPlayer);
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
        });
    }
}