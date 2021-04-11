package andrews.table_top_craft.network.server;

import java.util.function.Supplier;

import org.apache.commons.lang3.StringUtils;

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
						if(isFENValid(FEN))
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
	
	private static boolean isFENValid(String FEN)
	{
		String[] FENInfo = FEN.split(" ");
		String[] boardTileInfo = FENInfo[0].split("/");
		int splitCounter = StringUtils.countMatches(FENInfo[0], "/");
		int whiteKingCounter = StringUtils.countMatches(FENInfo[0], "K");
		int blackKingCounter = StringUtils.countMatches(FENInfo[0], "k");
		
		if(!(FENInfo.length == 6))
			return false;
		if(!(splitCounter == 7))
			return false;
		for(int i = 0; i < 8; i++)
		{
			String rowInfo = boardTileInfo[i];
			int valueCounter = 0;
			
			for(int j = 0; j < rowInfo.length(); j++)
			{
				if(rowInfo.charAt(j) == '1')
					valueCounter += 1;
				else if(rowInfo.charAt(j) == '2')
					valueCounter += 2;
				else if(rowInfo.charAt(j) == '3')
					valueCounter += 3;
				else if(rowInfo.charAt(j) == '4')
					valueCounter += 4;
				else if(rowInfo.charAt(j) == '5')
					valueCounter += 5;
				else if(rowInfo.charAt(j) == '6')
					valueCounter += 6;
				else if(rowInfo.charAt(j) == '7')
					valueCounter += 7;
				else if(rowInfo.charAt(j) == '8')
					valueCounter += 8;
				else
					valueCounter += 1;
			}
			
			if(!(valueCounter == 8))
				return false;
		}
		if(!(whiteKingCounter == 1))
			return false;
		if(!(blackKingCounter == 1))
			return false;
		if(!(FENInfo[1].equals("w") || FENInfo[1].equals("b")))
			return false;
		return true;
	}
}
