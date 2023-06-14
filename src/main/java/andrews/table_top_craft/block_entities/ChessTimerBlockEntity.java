package andrews.table_top_craft.block_entities;

import andrews.table_top_craft.objects.blocks.ChessTimerBlock;
import andrews.table_top_craft.registry.TTCBlockEntities;
import andrews.table_top_craft.util.NetworkUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ChessTimerBlockEntity extends BlockEntity
{
    // Synced Values
    private long leftTimer = 1800000;
    private long rightTimer = 1800000;
    // Used Server-Side to update the Timer
    public long leftTimerCache;
    public long rightTimerCache;
    public long lastSwitchTime;

    public ChessTimerBlockEntity(BlockPos pos, BlockState state)
    {
        super(TTCBlockEntities.CHESS_TIMER.get(), pos, state);
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
        CompoundTag chessTimerTag = new CompoundTag();
        chessTimerTag.putLong("LeftTimer", this.leftTimer);
        chessTimerTag.putLong("RightTimer", this.rightTimer);
        compound.put("ChessTimerValues", chessTimerTag);

        if(this.getLevel() != null && !this.getLevel().isClientSide())
            if(this.rightTimerCache == 0)
            {
                this.leftTimerCache = System.currentTimeMillis();
                this.rightTimerCache = System.currentTimeMillis();
            }
    }

    private void loadFromNBT(CompoundTag compound)
    {
        CompoundTag chessTimerTag = compound.getCompound("ChessTimerValues");
        this.leftTimer = chessTimerTag.getLong("LeftTimer");
        this.rightTimer = chessTimerTag.getLong("RightTimer");
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ChessTimerBlockEntity blockEntity)
    {
        if(!level.isClientSide())
        {
            if (state.getValue(ChessTimerBlock.PRESSED_BUTTON).equals(ChessTimerBlock.PressedButton.LEFT))
            {
                if (System.currentTimeMillis() >= blockEntity.rightTimerCache)
                {
                    boolean updateNeighbors = false;
                    blockEntity.rightTimerCache = System.currentTimeMillis() + 1000;
                    if(blockEntity.getRightTimer() == 1000)
                        updateNeighbors = true;
                    blockEntity.modifyRightTimer(-1000);
                    level.sendBlockUpdated(pos, state, state, 2);
                    if(updateNeighbors) {
                        level.updateNeighborsAt(pos, state.getBlock());
                        level.setBlockAndUpdate(pos, state.setValue(ChessTimerBlock.PRESSED_BUTTON, ChessTimerBlock.PressedButton.NONE));
                        NetworkUtil.playChesTimerSoundFromServer(level, pos, (byte) 1);
                    }
                    blockEntity.setChanged();
                }
            }
            else if (state.getValue(ChessTimerBlock.PRESSED_BUTTON).equals(ChessTimerBlock.PressedButton.RIGHT))
            {
                if(System.currentTimeMillis() >= blockEntity.leftTimerCache)
                {
                    boolean updateNeighbors = false;
                    blockEntity.leftTimerCache = System.currentTimeMillis() + 1000;
                    if(blockEntity.getLeftTimer() == 1000)
                        updateNeighbors = true;
                    blockEntity.modifyLeftTimer(-1000);
                    level.sendBlockUpdated(pos, state, state, 2);
                    if(updateNeighbors) {
                        level.updateNeighborsAt(pos, state.getBlock());
                        level.setBlockAndUpdate(pos, state.setValue(ChessTimerBlock.PRESSED_BUTTON, ChessTimerBlock.PressedButton.NONE));
                        NetworkUtil.playChesTimerSoundFromServer(level, pos, (byte) 1);
                    }
                    blockEntity.setChanged();
                }
            }
        }
    }

    public long getLeftTimer()
    {
        return this.leftTimer;
    }

    public long getRightTimer()
    {
        return this.rightTimer;
    }

    public void modifyLeftTimer(long amount)
    {
        this.leftTimer = this.clamp(this.leftTimer + amount, 0, 359999000);
    }

    public void modifyRightTimer(long amount)
    {
        this.rightTimer = this.clamp(this.rightTimer + amount, 0, 359999000);
    }

    private long clamp(long value, long min, long max)
    {
        return Math.max(min, Math.min(max, value));
    }

    public void resetTimers()
    {
        this.leftTimer = 1800000;
        this.rightTimer = 1800000;
    }
}
