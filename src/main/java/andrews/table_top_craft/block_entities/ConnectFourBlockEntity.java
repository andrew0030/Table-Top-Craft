package andrews.table_top_craft.block_entities;

import andrews.table_top_craft.animation.system.base.AnimatedBlockEntity;
import andrews.table_top_craft.animation.system.core.AdvancedAnimationState;
import andrews.table_top_craft.registry.TTCBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConnectFourBlockEntity extends AnimatedBlockEntity
{
    private String game = "------/------/------/------/------/------/------";
    public AdvancedAnimationState moveState;
    public byte movingPiece = -1;

    public int ironColor = 16383998;
    public int goldColor = 16701501;

    public ConnectFourBlockEntity(BlockPos pos, BlockState state)
    {
        super(TTCBlockEntities.CONNECT_FOUR.get(), pos, state);
    }

    @Override
    public AABB getRenderBoundingBox()
    {
        AABB aabb = super.getRenderBoundingBox();
        return aabb.expandTowards(0, 0.125, 0);
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        CompoundTag compound = new CompoundTag();
        this.saveToNBT(compound);
        return compound;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
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

    private void saveToNBT(CompoundTag compound)
    {
        CompoundTag connectFourTag = new CompoundTag();
        connectFourTag.putString("Game", this.game);
        connectFourTag.putInt("IronColor", this.ironColor);
        connectFourTag.putInt("GoldColor", this.goldColor);
        compound.put("ConnectFourValues", connectFourTag);
    }

    private void loadFromNBT(CompoundTag compound)
    {
        CompoundTag connectFourTag = compound.getCompound("ConnectFourValues");
        this.game = this.isValidGame(connectFourTag.getString("Game")) ? connectFourTag.getString("Game") : "------/------/------/------/------/------/------";
        this.ironColor = connectFourTag.getInt("IronColor");
        this.goldColor = connectFourTag.getInt("GoldColor");
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ConnectFourBlockEntity blockEntity)
    {
        blockEntity.incTicksExisted();

        if(level.isClientSide())
        {
            if(blockEntity.moveState != null)
            {
                if(blockEntity.moveState.isFinished())
                {
                    blockEntity.movingPiece = -1;
                    blockEntity.moveState = null;
                }
            }
        }
    }

    private boolean isValidGame(String game)
    {
        String[] columns = game.split("/");
        if(columns.length != 7) return false;
        for(String column : columns)
            if(column.length() != 6) return false;
        return true;
    }

    public String[] getColumns()
    {
        return this.game.split("/");
    }

    public void addPieceToColumn(byte column)
    {
        if(this.isValidGame(this.game))
        {
            String[] columns = this.getColumns();
            for(int i = 0; i < 6; i++)
            {
                char columnElement = columns[column].charAt(i);
                if(!(columnElement == 'g' || columnElement == 'i'))
                {
                    StringBuilder builder = new StringBuilder();
                    builder.append(this.game);
                    builder.setCharAt(column * 7 + i, this.getTotalPieces() % 2 == 0 ? 'g' : 'i');
                    this.game = builder.toString();
                    return;
                }
            }
        }
    }

    public int getTotalPieces()
    {
        int count = 0;
        for (int i = 0; i < this.game.length(); i++)
            if (this.game.charAt(i) == 'i' || this.game.charAt(i) == 'g')
                count++;
        return count;
    }

    public int getTopPieceInColumn(byte column)
    {
        if(this.isValidGame(this.game))
        {
            String[] columns = this.getColumns();
            for(int i = 5; i > 0; i--)
            {
                char columnElement = columns[column].charAt(i);
                if(columnElement == 'g' || columnElement == 'i')
                    return i;
            }
        }
        return 0;
    }

    public void reset()
    {
        this.game = "------/------/------/------/------/------/------";
    }

    public List<Integer> getFourInRow()
    {
        // If the game isn't valid we return
        if(!isValidGame(this.game)) return new ArrayList<>();
        // We convert the String to a 2D char array for easier handling
        char[][] board = new char[7][6];
        String[] columns = this.game.split("/");
        // We populate the 2D char array
        for (int i = 0; i < 7; i++)
            board[i] = columns[i].toCharArray();
        // Checks horizontally
        for (int col = 0; col < 7; col++)
            for (int row = 0; row < 6 - 3; row++)
                if (board[col][row] != '-' &&
                    board[col][row] == board[col][row + 1] &&
                    board[col][row] == board[col][row + 2] &&
                    board[col][row] == board[col][row + 3])
                    return Arrays.asList((col * 6 + row), (col * 6 + row + 1), (col * 6 + row + 2), (col * 6 + row + 3));
        // Checks vertically
        for (int col = 0; col < 7 - 3; col++)
            for (int row = 0; row < 6; row++)
                if (board[col][row] != '-' &&
                    board[col][row] == board[col + 1][row] &&
                    board[col][row] == board[col + 2][row] &&
                    board[col][row] == board[col + 3][row])
                    return Arrays.asList((col * 6 + row), ((col + 1) * 6 + row), ((col + 2) * 6 + row), ((col + 3) * 6 + row));
//      Checks diagonally
        for (int col = 0; col < 7 - 3; col++)
            for (int row = 0; row < 6 - 3; row++)
                if (board[col][row] != '-' &&
                    board[col][row] == board[col + 1][row + 1] &&
                    board[col][row] == board[col + 2][row + 2] &&
                    board[col][row] == board[col + 3][row + 3])
                    return Arrays.asList((col * 6 + row), ((col + 1) * 6 + row + 1), ((col + 2) * 6 + row + 2), ((col + 3) * 6 + row + 3));
        // Checks diagonally
        for (int col = 7 - 1; col >= 3; col--)
            for (int row = 0; row < 6 - 3; row++)
                if (board[col][row] != '-' &&
                    board[col][row] == board[col - 1][row + 1] &&
                    board[col][row] == board[col - 2][row + 2] &&
                    board[col][row] == board[col - 3][row + 3])
                    return Arrays.asList((col * 6 + row), ((col - 1) * 6 + row + 1), ((col - 2) * 6 + row + 2), ((col - 3) * 6 + row + 3));
        return new ArrayList<>();
    }

    public boolean hasFourInARow()
    {
        return !this.getFourInRow().isEmpty();
    }
}