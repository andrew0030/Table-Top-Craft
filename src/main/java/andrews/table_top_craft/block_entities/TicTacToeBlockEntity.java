package andrews.table_top_craft.block_entities;

import andrews.table_top_craft.registry.TTCBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TicTacToeBlockEntity extends BlockEntity
{
    private String ticTacToeGame;
    private int[] frames;
    private String circleColor;
    private String crossColor;

    public TicTacToeBlockEntity(BlockPos pos, BlockState state)
    {
        super(TTCBlockEntities.TIC_TAC_TOE.get(), pos, state);
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
//        this.loadFromNBT(compound);
        this.load(compound);
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
//        this.loadFromNBT(pkt.getTag());
        this.load(pkt.getTag());
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

        // Used to sync the frames when the game is updated
        for(int i = 0; i < 9; i++)
        {
            char charAtTile = this.getTicTacToeCharAt(i);
            if(charAtTile == '-')
            {
                if (this.getTicTacToeFrame(i) != 0 && this.getTicTacToeFrame(i) != -1)
                {
                    this.setTicTacToeFrameAt(-1, i);
                }
            }
            else if((charAtTile == 'X' || charAtTile == 'O') && this.getTicTacToeFrame(i) == -1)
            {
                this.setTicTacToeFrameAt(0, i);
            }
        }
    }

    /**
     * Used to save data to the NBT
     */
    private void saveToNBT(CompoundTag compound)
    {
        CompoundTag chessNBT = new CompoundTag();
        chessNBT.putString("TicTacToeGame", getTicTacToeGame());
        if(this.circleColor != null)
            chessNBT.putString("CircleColor", getCircleColor());
        if(this.crossColor != null)
            chessNBT.putString("CrossColor", getCrossColor());
        compound.put("TicTacToeValues", chessNBT);
    }

    /**
     * Used to load data from the NBT
     */
    private void loadFromNBT(CompoundTag compound)
    {
        CompoundTag chessNBT = compound.getCompound("TicTacToeValues");
        if (chessNBT.contains("TicTacToeGame", Tag.TAG_STRING))
            this.ticTacToeGame = chessNBT.getString("TicTacToeGame");
        if (chessNBT.contains("CircleColor", Tag.TAG_STRING))
            this.circleColor = chessNBT.getString("CircleColor");
        if (chessNBT.contains("CrossColor", Tag.TAG_STRING))
            this.crossColor = chessNBT.getString("CrossColor");
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TicTacToeBlockEntity blockEntity)
    {
        String game = blockEntity.getTicTacToeGame().replaceAll("/", "");
        for(int i = 0; i < 9; i++)
        {
            if(game.charAt(i) == 'O')
                if(blockEntity.getTicTacToeFrames()[i] >= 0 && blockEntity.getTicTacToeFrames()[i] < 15)
                    blockEntity.setTicTacToeFrameAt(blockEntity.getTicTacToeFrames()[i] + 1, i);

            if(game.charAt(i) == 'X')
                if(blockEntity.getTicTacToeFrames()[i] >= 0 && blockEntity.getTicTacToeFrames()[i] < 13)
                    blockEntity.setTicTacToeFrameAt(blockEntity.getTicTacToeFrames()[i] + 1, i);
        }
    }

    public String getTicTacToeGame()
    {
        return this.ticTacToeGame == null ? "---/---/---" : this.ticTacToeGame;
    }

    public char getTicTacToeCharAt(int index)
    {
        char[] gameChars = getTicTacToeGame().replaceAll("/", "").toCharArray();
        return gameChars[index];
    }

    public int[] getTicTacToeFrames()
    {
        return this.frames == null ? new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1} : this.frames;
    }

    public int getTicTacToeFrame(int index)
    {
        if(this.frames == null)
        {
            int c1 = getTicTacToeCharAt(0) == '-' ? -1 : 0;
            int c2 = getTicTacToeCharAt(1) == '-' ? -1 : 0;
            int c3 = getTicTacToeCharAt(2) == '-' ? -1 : 0;
            int c4 = getTicTacToeCharAt(3) == '-' ? -1 : 0;
            int c5 = getTicTacToeCharAt(4) == '-' ? -1 : 0;
            int c6 = getTicTacToeCharAt(5) == '-' ? -1 : 0;
            int c7 = getTicTacToeCharAt(6) == '-' ? -1 : 0;
            int c8 = getTicTacToeCharAt(7) == '-' ? -1 : 0;
            int c9 = getTicTacToeCharAt(8) == '-' ? -1 : 0;
            this.frames = new int[]{c1, c2, c3, c4, c5, c6, c7, c8, c9};
        }
        return this.frames[index];
    }

    public void setTicTacToeFrameAt(int value, int index)
    {
        if(this.frames == null)
            this.frames = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1};
        this.frames[index] = value;
    }

    public void resetFrames()
    {
        this.frames = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1};
    }

    public void setTicTacToeGame(String game)
    {
        this.ticTacToeGame = game;
        this.setChanged();
    }

    public String getCircleColor()
    {
        if(circleColor == null)
            this.circleColor = "249/255/254";
        return this.circleColor;
    }

    public String getCrossColor()
    {
        if(this.crossColor == null)
            this.crossColor = "249/255/254";
        return this.crossColor;
    }

    public void setCircleColor(String circleColor)
    {
        this.circleColor = circleColor;
        setChanged();
    }

    public void setCrossColor(String crossColor)
    {
        this.crossColor = crossColor;
        setChanged();
    }

    public boolean isGameOver(String game)
    {
        String[] rows = game.split("/");
        if (rows[0].equals("XXX") || rows[0].equals("OOO") ||
            rows[1].equals("XXX") || rows[1].equals("OOO") ||
            rows[2].equals("XXX") || rows[2].equals("OOO")) {
            return true;
        }
        else if ((rows[0].charAt(0) == 'X' && rows[1].charAt(0) == 'X' && rows[2].charAt(0) == 'X') ||
                 (rows[0].charAt(0) == 'O' && rows[1].charAt(0) == 'O' && rows[2].charAt(0) == 'O')) {
            return true;
        }
        else if ((rows[0].charAt(1) == 'X' && rows[1].charAt(1) == 'X' && rows[2].charAt(1) == 'X') ||
                 (rows[0].charAt(1) == 'O' && rows[1].charAt(1) == 'O' && rows[2].charAt(1) == 'O')) {
            return true;
        }
        else if ((rows[0].charAt(2) == 'X' && rows[1].charAt(2) == 'X' && rows[2].charAt(2) == 'X') ||
                 (rows[0].charAt(2) == 'O' && rows[1].charAt(2) == 'O' && rows[2].charAt(2) == 'O')) {
            return true;
        }
        else if ((rows[0].charAt(0) == 'X' && rows[1].charAt(1) == 'X' && rows[2].charAt(2) == 'X') ||
                 (rows[0].charAt(0) == 'O' && rows[1].charAt(1) == 'O' && rows[2].charAt(2) == 'O')) {
            return true;
        }
        else return (rows[0].charAt(2) == 'X' && rows[1].charAt(1) == 'X' && rows[2].charAt(0) == 'X') ||
                    (rows[0].charAt(2) == 'O' && rows[1].charAt(1) == 'O' && rows[2].charAt(0) == 'O');
    }
}