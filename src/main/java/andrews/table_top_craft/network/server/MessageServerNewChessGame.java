package andrews.table_top_craft.network.server;

import java.util.function.Supplier;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

public class MessageServerNewChessGame
{
	private final BlockPos pos;
	
	public MessageServerNewChessGame(BlockPos pos)
	{
        this.pos = pos;
    }
	
	public void serialize(FriendlyByteBuf buf)
	{
		buf.writeBlockPos(pos);
	}
	
	public static MessageServerNewChessGame deserialize(FriendlyByteBuf buf)
	{
		BlockPos pos = buf.readBlockPos();
		return new MessageServerNewChessGame(pos);
	}
	
	public static void handle(MessageServerNewChessGame message, Supplier<NetworkEvent.Context> ctx)
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
					// We make sure the TileEntity is a ChessTileEntity
					if(blockEntity instanceof ChessTileEntity chessTileEntity)
			        {
						Board board = Board.createStandardBoard();
						chessTileEntity.setBoard(board);
						chessTileEntity.getMoveLog().clear();
						level.sendBlockUpdated(message.pos, level.getBlockState(chessPos), level.getBlockState(chessPos), 2);
			        }
				}
			});
			context.setPacketHandled(true);
		}
	}
}