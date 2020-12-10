package andrews.table_top_craft.tile_entities;

import andrews.table_top_craft.registry.TTCTileEntities;
import net.minecraft.tileentity.TileEntity;

public class ChessTileEntity extends TileEntity
{
	public ChessTileEntity()
	{
		super(TTCTileEntities.CHESS.get());
	}
}
