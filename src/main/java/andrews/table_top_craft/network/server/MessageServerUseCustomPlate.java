package andrews.table_top_craft.network.server;

import java.util.function.Supplier;

import andrews.table_top_craft.objects.blocks.ChessBlock;
import andrews.table_top_craft.block_entities.ChessBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

public class MessageServerUseCustomPlate
{
	private final BlockPos pos;
	
	public MessageServerUseCustomPlate(BlockPos pos)
	{
        this.pos = pos;
    }
	
	public void serialize(FriendlyByteBuf buf)
	{
		buf.writeBlockPos(pos);
	}
	
	public static MessageServerUseCustomPlate deserialize(FriendlyByteBuf buf)
	{
		BlockPos pos = buf.readBlockPos();
		return new MessageServerUseCustomPlate(pos);
	}
	
	public static void handle(MessageServerUseCustomPlate message, Supplier<NetworkEvent.Context> ctx)
	{
		NetworkEvent.Context context = ctx.get();
		Player player = context.getSender();
		Level level = player.getLevel();
		BlockPos chessPos = message.pos;
		
		if(context.getDirection().getReceptionSide() == LogicalSide.SERVER)
		{
			context.enqueueWork(() ->
			{
				if(level != null)
				{
					BlockEntity blockEntity = level.getBlockEntity(chessPos);
					// We make sure the TileEntity is a ChessBlockEntity
					if(blockEntity instanceof ChessBlockEntity chessBlockEntity)
			        {
						if(chessBlockEntity.getUseCustomPlate())
						{
							chessBlockEntity.setUseCustomPlate(false);
							level.setBlockAndUpdate(message.pos, level.getBlockState(message.pos).setValue(ChessBlock.SHOW_PLATE, true));
						}
						else
						{
							chessBlockEntity.setUseCustomPlate(true);
							level.setBlockAndUpdate(message.pos, level.getBlockState(message.pos).setValue(ChessBlock.SHOW_PLATE, false));
						}
						level.sendBlockUpdated(message.pos, level.getBlockState(chessPos), level.getBlockState(chessPos), 2);
			        }
				}
			});
			context.setPacketHandled(true);
		}
	}
}