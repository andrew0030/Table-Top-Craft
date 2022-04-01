package andrews.table_top_craft.tile_entities;

import andrews.table_top_craft.game_logic.chess.PieceColor;
import andrews.table_top_craft.game_logic.chess.board.BoardUtils;
import andrews.table_top_craft.game_logic.chess.board.moves.*;
import andrews.table_top_craft.game_logic.chess.pgn.FenUtil;
import andrews.table_top_craft.game_logic.chess.pieces.KingPiece;
import andrews.table_top_craft.game_logic.chess.pieces.PawnPiece;
import andrews.table_top_craft.game_logic.chess.pieces.RookPiece;
import andrews.table_top_craft.registry.TTCTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class ChessPieceFigureBlockEntity extends BlockEntity
{
    private final Random rand = new Random();
    private int pieceType;
    private boolean rotateChessPieceFigure;

    public ChessPieceFigureBlockEntity(BlockPos pos, BlockState state)
    {
        super(TTCTileEntities.CHESS_PIECE_FIGURE.get(), pos, state);
    }

    // Used to synchronize the BlockEntity with the client when the chunk it is in is loaded
    @Override
    public CompoundTag getUpdateTag()
    {
        CompoundTag compound = new CompoundTag();
        this.saveToNBT(compound);
        return compound;
    }

    // Used to synchronize the BlockEntity with the client when the chunk it is in is loaded
    @Override
    public void handleUpdateTag(CompoundTag compound)
    {
        this.loadFromNBT(compound);
    }

    // Used to synchronize the BlockEntity with the client when the chunk it is in is loaded
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket()
    {
        // Will get tag from #getUpdateTag
        return ClientboundBlockEntityDataPacket.create(this);
    }

    // Used to synchronize the BlockEntity with the client onBlockUpdate
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt)
    {
        this.loadFromNBT(pkt.getTag());
    }

    @Override
    public void saveAdditional(CompoundTag compound)
    {
        super.saveAdditional(compound);
        this.saveToNBT(compound);
    }

    @Override
    public void load(CompoundTag compound)
    {
        super.load(compound);
        this.loadFromNBT(compound);
    }

    /**
     * Used to save data to the NBT
     */
    private void saveToNBT(CompoundTag compound)
    {
        compound.putInt("PieceType", this.getPieceType());
        compound.putInt("RotateChessPieceFigure", !this.rotateChessPieceFigure ? 0 : 1);
    }

    /**
     * Used to load data from the NBT
     */
    private void loadFromNBT(CompoundTag compound)
    {
        pieceType = compound.getInt("PieceType");
        if(compound.contains("RotateChessPieceFigure", Tag.TAG_INT))
            this.rotateChessPieceFigure = compound.getInt("RotateChessPieceFigure") != 0;
    }

    public int getPieceType()
    {
        if(pieceType == 0)
        {
            // The check bellow makes sure a variant is only set server side, this way the first ticks during rendering (until synchronized)
            // the client variant will be 0 meaning it wont render anything. This is used to prevent visual glitches.
            if(!this.level.isClientSide)
            {
                this.setPieceType(rand.nextInt(6) + 1);
                System.out.println(pieceType); // TODO remove this debug text line
            }
            return pieceType;
        }
        else
        {
            return pieceType;
        }
    }

    public void setPieceType(int type)
    {
        pieceType = type;
        setChanged();
    }

    public void setRotateChessPieceFigure(boolean rotateChessPieceFigure)
    {
        this.rotateChessPieceFigure = rotateChessPieceFigure;
        setChanged();
    }

    public boolean getRotateChessPieceFigure()
    {
        return this.rotateChessPieceFigure;
    }
}