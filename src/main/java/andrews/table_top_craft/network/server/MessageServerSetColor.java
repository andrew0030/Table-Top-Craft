package andrews.table_top_craft.network.server;

import java.util.function.Supplier;

import andrews.table_top_craft.block_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.block_entities.ChessBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

public class MessageServerSetColor
{
	private final int colorType;
	private final BlockPos pos;
	private final String color;
	
	public MessageServerSetColor(int colorType, BlockPos pos, String color)
	{
		this.colorType = colorType;
        this.pos = pos;
        this.color = color;
    }
	
	public void serialize(FriendlyByteBuf buf)
	{
		buf.writeInt(colorType);
		buf.writeBlockPos(pos);
		buf.writeUtf(color);
	}
	
	public static MessageServerSetColor deserialize(FriendlyByteBuf buf)
	{
		int colorType = buf.readInt();
		BlockPos pos = buf.readBlockPos();
		String color = buf.readUtf(32767);
		return new MessageServerSetColor(colorType, pos, color);
	}
	
	public static void handle(MessageServerSetColor message, Supplier<NetworkEvent.Context> ctx)
	{
		NetworkEvent.Context context = ctx.get();
		Player player = context.getSender();
		Level level = player.getLevel();
		BlockPos chessPos = message.pos;
		String color = message.color;
		
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
						switch(message.colorType)
						{
							case 0 -> chessBlockEntity.setTileInfoColor(color);
							case 1 -> chessBlockEntity.setLegalMoveColor(color);
							case 2 -> chessBlockEntity.setInvalidMoveColor(color);
							case 3 -> chessBlockEntity.setAttackMoveColor(color);
							case 4 -> chessBlockEntity.setPreviousMoveColor(color);
							case 5 -> chessBlockEntity.setCastleMoveColor(color);
						}
						level.sendBlockUpdated(message.pos, level.getBlockState(chessPos), level.getBlockState(chessPos), 2);
						chessBlockEntity.setChanged();
			        }
					else if(blockEntity instanceof ChessPieceFigureBlockEntity chessPieceFigureBlockEntity)
					{
//						switch(message.colorType)
//						{
//							case 6 -> chessPieceFigureBlockEntity.setPieceColor(color);
//						}
						chessPieceFigureBlockEntity.setPieceColor(color);
						level.sendBlockUpdated(message.pos, level.getBlockState(chessPos), level.getBlockState(chessPos), 2);
						chessPieceFigureBlockEntity.setChanged();
					}
				}
			});
			context.setPacketHandled(true);
		}
	}
}