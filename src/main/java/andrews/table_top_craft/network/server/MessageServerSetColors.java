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

public class MessageServerSetColors
{
	private int colorType;
	private BlockPos pos;
	private String color;
	private String color2;
	
	public MessageServerSetColors(int colorType, BlockPos pos, String color, String color2)
	{
		this.colorType = colorType;
        this.pos = pos;
        this.color = color;
        this.color2 = color2;
    }
	
	public void serialize(PacketBuffer buf)
	{
		buf.writeInt(colorType);
		buf.writeBlockPos(pos);
		buf.writeString(color);
		buf.writeString(color2);
	}
	
	public static MessageServerSetColors deserialize(PacketBuffer buf)
	{
		int colorType = buf.readInt();
		BlockPos pos = buf.readBlockPos();
		String color = buf.readString(32767);
		String color2 = buf.readString(32767);
		return new MessageServerSetColors(colorType, pos, color, color2);
	}
	
	public static void handle(MessageServerSetColors message, Supplier<NetworkEvent.Context> ctx)
	{
		NetworkEvent.Context context = ctx.get();
		PlayerEntity player = context.getSender();
		World world = player.getEntityWorld();
		BlockPos chessPos = message.pos;
		String color = message.color;
		String color2 = message.color2;
		
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
							chessTileEntity.setWhiteTilesColor(color);
							chessTileEntity.setBlackTilesColor(color2);
							break;
						case 1:
							chessTileEntity.setWhitePiecesColor(color);
							chessTileEntity.setBlackPiecesColor(color2);
						}
						world.notifyBlockUpdate(message.pos, world.getBlockState(chessPos), world.getBlockState(chessPos), 2);
			        }
				}
			});
			context.setPacketHandled(true);
		}
	}
}
