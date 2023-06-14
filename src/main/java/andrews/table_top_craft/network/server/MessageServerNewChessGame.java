package andrews.table_top_craft.network.server;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.util.NetworkUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

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
					// We make sure the TileEntity is a ChessBlockEntity
					if(blockEntity instanceof ChessBlockEntity chessBlockEntity)
			        {
						Board board = Board.createStandardBoard();
						chessBlockEntity.setBoard(board);
						chessBlockEntity.getMoveLog().clear();
						chessBlockEntity.setHumanMovedPiece(null);
						chessBlockEntity.setSourceTile(null);
						chessBlockEntity.setWaitingForPromotion(false);
						chessBlockEntity.setPromotionCoordinate((byte) -1);
						chessBlockEntity.doingAnimationTimer = 0;
						chessBlockEntity.move = null;
						chessBlockEntity.transition = null;
						level.sendBlockUpdated(message.pos, level.getBlockState(chessPos), level.getBlockState(chessPos), 2);
						// We start the placed Animation on server and client
						NetworkUtil.setChessAnimationForAllTracking(level, chessPos, (byte) 0);
			        }
				}
			});
			context.setPacketHandled(true);
		}
	}
}