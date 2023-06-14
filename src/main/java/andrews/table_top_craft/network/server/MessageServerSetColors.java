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

public class MessageServerSetColors
{
	private final int colorType;
	private final BlockPos pos;
	private final String color;
	private final String color2;
	
	public MessageServerSetColors(int colorType, BlockPos pos, String color, String color2)
	{
		this.colorType = colorType;
        this.pos = pos;
        this.color = color;
        this.color2 = color2;
    }
	
	public void serialize(FriendlyByteBuf buf)
	{
		buf.writeInt(colorType);
		buf.writeBlockPos(pos);
		buf.writeUtf(color);
		buf.writeUtf(color2);
	}
	
	public static MessageServerSetColors deserialize(FriendlyByteBuf buf)
	{
		int colorType = buf.readInt();
		BlockPos pos = buf.readBlockPos();
		String color = buf.readUtf(32767);
		String color2 = buf.readUtf(32767);
		return new MessageServerSetColors(colorType, pos, color, color2);
	}
	
	public static void handle(MessageServerSetColors message, Supplier<NetworkEvent.Context> ctx)
	{
		NetworkEvent.Context context = ctx.get();
		Player player = context.getSender();
		Level level = player.getLevel();
		BlockPos chessPos = message.pos;
		String color = message.color;
		String color2 = message.color2;
		
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
						switch (message.colorType)
						{
							case 0 ->
							{
								chessBlockEntity.setWhiteTilesColor(color);
								chessBlockEntity.setBlackTilesColor(color2);
							}
							case 1 ->
							{
								chessBlockEntity.setWhitePiecesColor(color);
								chessBlockEntity.setBlackPiecesColor(color2);
							}
						}
						level.sendBlockUpdated(message.pos, level.getBlockState(chessPos), level.getBlockState(chessPos), 2);
						chessBlockEntity.setChanged();
			        }
				}
			});
			context.setPacketHandled(true);
		}
	}
}