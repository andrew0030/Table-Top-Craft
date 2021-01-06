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

public class MessageServerSetColor
{
	private int colorType;
	private BlockPos pos;
	private String color;
	
	public MessageServerSetColor(int colorType, BlockPos pos, String color)
	{
		this.colorType = colorType;
        this.pos = pos;
        this.color = color;
    }
	
	public void serialize(PacketBuffer buf)
	{
		buf.writeInt(colorType);
		buf.writeBlockPos(pos);
		buf.writeString(color);
	}
	
	public static MessageServerSetColor deserialize(PacketBuffer buf)
	{
		int colorType = buf.readInt();
		BlockPos pos = buf.readBlockPos();
		String color = buf.readString();
		return new MessageServerSetColor(colorType, pos, color);
	}
	
	public static void handle(MessageServerSetColor message, Supplier<NetworkEvent.Context> ctx)
	{
		NetworkEvent.Context context = ctx.get();
		PlayerEntity player = context.getSender();
		World world = player.getEntityWorld();
		BlockPos chessPos = message.pos;
		String color = message.color;
		
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
						switch(message.colorType)
						{
						default:
						case 0:
							chessTileEntity.setTileInfoColor(color);
							break;
						case 1:
							chessTileEntity.setLegalMoveColor(color);
							break;
						case 2:
							chessTileEntity.setInvalidMoveColor(color);
							break;
						case 3:
							chessTileEntity.setAttackMoveColor(color);
							break;
						case 4:
							chessTileEntity.setPreviousMoveColor(color);
							break;
						case 5:
							chessTileEntity.setCastleMoveColor(color);
						}
						world.notifyBlockUpdate(message.pos, world.getBlockState(chessPos), world.getBlockState(chessPos), 2);
			        }
				}
			});
			context.setPacketHandled(true);
		}
	}
}