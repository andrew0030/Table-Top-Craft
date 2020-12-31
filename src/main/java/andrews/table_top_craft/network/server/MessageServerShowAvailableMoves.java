package andrews.table_top_craft.network.server;

import java.util.function.Supplier;

import andrews.table_top_craft.tile_entities.ChessTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageServerShowAvailableMoves
{
	private BlockPos pos;
	
	public MessageServerShowAvailableMoves(BlockPos pos)
	{
        this.pos = pos;
    }
	
	public void serialize(PacketBuffer buf)
	{
		buf.writeBlockPos(pos);
	}
	
	public static MessageServerShowAvailableMoves deserialize(PacketBuffer buf)
	{
		BlockPos pos = buf.readBlockPos();
		return new MessageServerShowAvailableMoves(pos);
	}
	
	public static void handle(MessageServerShowAvailableMoves message, Supplier<NetworkEvent.Context> ctx)
	{
		NetworkEvent.Context context = ctx.get();
		PlayerEntity player = context.getSender();
		World world = player.getEntityWorld();
		BlockPos chessPos = message.pos;
		
		if(context.getDirection().getReceptionSide() == LogicalSide.SERVER)
		{
			context.enqueueWork(() ->
			{
				if(world != null)
				{
					TileEntity tileentity = world.getTileEntity(chessPos);
					// We make sure the TileEntity is a ChessTileEntity
					if(tileentity instanceof ChessTileEntity)
			        {
						ChessTileEntity chessTileEntity = (ChessTileEntity)tileentity;
						if(chessTileEntity.getShowAvailableMoves())
						{
							chessTileEntity.setShowAvailableMoves(false);
						}
						else
						{
							chessTileEntity.setShowAvailableMoves(true);
						}
						world.notifyBlockUpdate(message.pos, world.getBlockState(chessPos), world.getBlockState(chessPos), 2);
			        }
				}
			});
			context.setPacketHandled(true);
		}
	}
}
