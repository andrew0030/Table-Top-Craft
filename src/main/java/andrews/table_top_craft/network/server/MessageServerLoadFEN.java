package andrews.table_top_craft.network.server;

import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.game_logic.chess.pgn.FenUtil;
import andrews.table_top_craft.tile_entities.ChessTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageServerLoadFEN
{
	private BlockPos pos;
	private String FEN;
	
	public MessageServerLoadFEN(BlockPos pos, String FEN)
	{
        this.pos = pos;
        this.FEN = FEN;
    }
	
	public void serialize(PacketBuffer buf)
	{
		buf.writeBlockPos(pos);
		buf.writeString(FEN);
	}
	
	public static MessageServerLoadFEN deserialize(PacketBuffer buf)
	{
		BlockPos pos = buf.readBlockPos();
		String FEN = buf.readString(32767);
		return new MessageServerLoadFEN(pos, FEN);
	}
	
	public static void handle(MessageServerLoadFEN message, Supplier<NetworkEvent.Context> ctx)
	{
		NetworkEvent.Context context = ctx.get();
		PlayerEntity player = context.getSender();
		World world = player.getEntityWorld();
		BlockPos chessPos = message.pos;
		String FEN = message.FEN;
		
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
						Board board = Board.createStandardBoard();
						if(FenUtil.isFENValid(FEN))
						{
							board = FenUtil.createGameFromFEN(FEN);
						}
						else
						{
							player.sendMessage(new TranslationTextComponent("message.table_top_craft.chess.invalidFEN").mergeStyle(TextFormatting.RED), player.getUniqueID());
						}
						chessTileEntity.setBoard(board);
						chessTileEntity.getMoveLog().clear();
						world.notifyBlockUpdate(message.pos, world.getBlockState(chessPos), world.getBlockState(chessPos), 2);
			        }
				}
			});
			context.setPacketHandled(true);
		}
	}
}