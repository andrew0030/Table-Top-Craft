package andrews.table_top_craft.registry;

import javax.annotation.Nullable;

import com.google.common.base.Supplier;

import andrews.table_top_craft.TableTopCraft;
import andrews.table_top_craft.objects.blocks.ChessBlock;
import andrews.table_top_craft.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TTCBlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MODID);
	
	public static final RegistryObject<Block> CHESS			  	= createBlock("chess", () -> new ChessBlock(), TableTopCraft.TABLE_TOP_CRAFT_GROUP);
	
	public static <B extends Block> RegistryObject<B> createBlock(String name, Supplier<? extends B> supplier, @Nullable ItemGroup group)
	{
		RegistryObject<B> block = TTCBlocks.BLOCKS.register(name, supplier);
		TTCItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().maxStackSize(1).group(group)));
		return block;
	}
}