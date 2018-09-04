package moe.plushie.table_top_craft.common.init;

import java.util.ArrayList;
import java.util.List;

import moe.plushie.table_top_craft.TableTopCraft;
import moe.plushie.table_top_craft.common.lib.Reference;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public final class ItemInit {
    
    public static final List<Item> ITEMS = new ArrayList<Item>();

    // example Item: public static final Item EXAMPLE = new ItemExample("example");
    
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        TableTopCraft.getLogger().info("------register items------");
        event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
    }
}
