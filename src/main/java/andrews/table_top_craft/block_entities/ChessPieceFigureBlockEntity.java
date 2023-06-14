package andrews.table_top_craft.block_entities;

import andrews.table_top_craft.registry.TTCBlockEntities;
import andrews.table_top_craft.util.NBTColorSaving;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.Random;

public class ChessPieceFigureBlockEntity extends BlockEntity
{
    private final Random rand = new Random();
    private int pieceType;
    private boolean rotateChessPieceFigure;
    private String pieceColor;
    private int pieceSet;
    private double pieceScale;
    private String name;

    public ChessPieceFigureBlockEntity(BlockPos pos, BlockState state)
    {
        super(TTCBlockEntities.CHESS_PIECE_FIGURE.get(), pos, state);
    }

    @Override
    public AABB getRenderBoundingBox()
    {
        double scale = this.getPieceScale();
        double verticalScale = 0.14D * scale;
        return super.getRenderBoundingBox().expandTowards(-verticalScale, 0.6D * scale, -verticalScale).move(verticalScale / 2, 0, verticalScale / 2);
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

    // Used to synchronize the BlockEntity with the client onBlockUpdate
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
        compound.putString("PieceColor", getPieceColor());
        compound.putInt("PieceSet", this.getPieceSet());
        compound.putDouble("PieceScale", this.getPieceScale());
        if(this.getPieceName() != null)
            compound.putString("PieceName", this.getPieceName());
    }

    /**
     * Used to load data from the NBT
     */
    private void loadFromNBT(CompoundTag compound)
    {
        pieceType = compound.getInt("PieceType");
        if(compound.contains("RotateChessPieceFigure", Tag.TAG_INT))
            this.rotateChessPieceFigure = compound.getInt("RotateChessPieceFigure") != 0;
        if(compound.contains("PieceColor", Tag.TAG_STRING))
            this.pieceColor = compound.getString("PieceColor");
        if(compound.contains("PieceSet", Tag.TAG_INT))
            this.pieceSet = compound.getInt("PieceSet");
        if(compound.contains("PieceScale", Tag.TAG_DOUBLE))
            this.pieceScale = compound.getDouble("PieceScale");
        if(compound.contains("PieceName", Tag.TAG_STRING))
            this.name = compound.getString("PieceName");
    }

    public int getPieceType()
    {
        if(pieceType == 0)
        {
            // The check bellow makes sure a variant is only set server side, this way the first ticks during rendering (until synchronized)
            // the client variant will be 0 meaning it wont render anything. This is used to prevent visual glitches.
            if(!this.level.isClientSide)
                this.setPieceType(rand.nextInt(6) + 1);
        }
        return pieceType;
    }

    public void setPieceType(int type)
    {
        pieceType = type;
        setChanged();
    }

    public int getPieceSet()
    {
        if(pieceSet == 0)
        {
            if(!this.level.isClientSide)
                this.setPieceSet(rand.nextInt(3) + 1);
        }
        return pieceSet;
    }

    public void setPieceSet(int pieceSet)
    {
        this.pieceSet = pieceSet;
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

    public void setPieceColor(String colorForNBT)
    {
        this.pieceColor = colorForNBT;
    }

    public String getPieceColor()
    {
        return this.pieceColor == null ? NBTColorSaving.createWhitePiecesColor() : this.pieceColor;
    }

    public void setPieceScale(double pieceScale)
    {
        this.pieceScale = pieceScale;
    }

    public double getPieceScale()
    {
        return this.pieceScale == 0 ? 1 : this.pieceScale;
    }

    public void setPieceName(String pieceName)
    {
        this.name = pieceName;
    }

    public String getPieceName()
    {
        return this.name;
    }
}