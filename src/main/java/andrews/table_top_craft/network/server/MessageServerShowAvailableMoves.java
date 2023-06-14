package andrews.table_top_craft.network.server;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageServerShowAvailableMoves
{
	private final BlockPos pos;
	
	public MessageServerShowAvailableMoves(BlockPos pos)
	{
        this.pos = pos;
    }
	
	public void serialize(FriendlyByteBuf buf)
	{
		buf.writeBlockPos(pos);
	}
	
	public static MessageServerShowAvailableMoves deserialize(FriendlyByteBuf buf)
	{
		BlockPos pos = buf.readBlockPos();
		return new MessageServerShowAvailableMoves(pos);
	}
	
	public static void handle(MessageServerShowAvailableMoves message, Supplier<NetworkEvent.Context> ctx)
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
						chessBlockEntity.setShowAvailableMoves(!chessBlockEntity.getShowAvailableMoves());
						level.sendBlockUpdated(message.pos, level.getBlockState(chessPos), level.getBlockState(chessPos), 2);
			        }
				}
			});
			context.setPacketHandled(true);
		}
	}
}