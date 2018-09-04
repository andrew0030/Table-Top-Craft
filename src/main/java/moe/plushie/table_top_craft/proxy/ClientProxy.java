package moe.plushie.table_top_craft.proxy;

import moe.plushie.table_top_craft.TableTopCraft;
import moe.plushie.table_top_craft.client.render.tile.TileEntiryChessRenderer;
import moe.plushie.table_top_craft.common.init.BlockInit;
import moe.plushie.table_top_craft.common.init.ItemInit;
import moe.plushie.table_top_craft.common.lib.Reference;
import moe.plushie.table_top_craft.common.tileentities.TileEntityChess;
import moe.plushie.table_top_craft.util.interfaces.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = { Side.CLIENT })
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    public ClientProxy() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }

    @Override
    public void preinit() {
        super.preinit();
    }

    @Override
    public void init() {
        super.init();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChess.class, new TileEntiryChessRenderer());
    }

    @Override
    public void postinit() {
        super.postinit();
    }
    
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        TableTopCraft.getLogger().info("------register models------");
        for (Item item : ItemInit.ITEMS) {
            if (item instanceof IHasModel) {
                ((IHasModel) item).registerModels();
            }
        }

        for (Block block : BlockInit.BLOCKS) {
            if (block instanceof IHasModel) {
                ((IHasModel) block).registerModels();
            }
        }
    }
}
