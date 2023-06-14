package andrews.table_top_craft.network.server;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.pgn.FenUtil;
import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.util.NetworkUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageServerLoadFEN
{
	private final BlockPos pos;
	private final String FEN;
	
	public MessageServerLoadFEN(BlockPos pos, String FEN)
	{
        this.pos = pos;
        this.FEN = FEN;
    }
	
	public void serialize(FriendlyByteBuf buf)
	{
		buf.writeBlockPos(pos);
		buf.writeUtf(FEN);
	}
	
	public static MessageServerLoadFEN deserialize(FriendlyByteBuf buf)
	{
		BlockPos pos = buf.readBlockPos();
		String FEN = buf.readUtf(32767);
		return new MessageServerLoadFEN(pos, FEN);
	}
	
	public static void handle(MessageServerLoadFEN message, Supplier<NetworkEvent.Context> ctx)
	{
		NetworkEvent.Context context = ctx.get();
		Player player = context.getSender();
		Level level = player.getLevel();
		BlockPos chessPos = message.pos;
		String FEN = message.FEN;
		
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
						if(FenUtil.isFENValid(FEN))
						{
							board = FenUtil.createGameFromFEN(FEN);
						}
						else
						{
							player.sendSystemMessage(Component.translatable("message.table_top_craft.chess.invalidFEN").withStyle(ChatFormatting.RED));
						}
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