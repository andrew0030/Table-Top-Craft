package andrews.table_top_craft.network.server;

import andrews.table_top_craft.game_logic.chess.board.moves.BaseMove;
import andrews.table_top_craft.game_logic.chess.board.moves.MoveFactory;
import andrews.table_top_craft.game_logic.chess.board.tiles.BaseChessTile;
import andrews.table_top_craft.game_logic.chess.player.MoveTransition;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
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
        Player player = context.getSender();
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