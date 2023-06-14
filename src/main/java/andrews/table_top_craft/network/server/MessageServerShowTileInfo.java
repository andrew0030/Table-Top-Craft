package andrews.table_top_craft.network.server;

import java.util.function.Supplier;

import andrews.table_top_craft.block_entities.ChessBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

public class MessageServerShowTileInfo
{
	private final BlockPos pos;
	
	public MessageServerShowTileInfo(BlockPos pos)
	{
        this.pos = pos;
    }
	
	public void serialize(FriendlyByteBuf buf)
	{
		buf.writeBlockPos(pos);
	}
	
	public static MessageServerShowTileInfo deserialize(FriendlyByteBuf buf)
	{
		BlockPos pos = buf.readBlockPos();
		return new MessageServerShowTileInfo(pos);
	}
	
	public static void handle(MessageServerShowTileInfo message, Supplier<NetworkEvent.Context> ctx)
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
						chessBlockEntity.setShowTileInfo(!chessBlockEntity.getShouldShowTileInfo());
						level.sendBlockUpdated(message.pos, level.getBlockState(chessPos), level.getBlockState(chessPos), 2);
			        }
				}
			});
			context.setPacketHandled(true);
		}
	}
}