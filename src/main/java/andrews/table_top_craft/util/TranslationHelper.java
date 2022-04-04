package andrews.table_top_craft.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.List;

public class TranslationHelper
{
    /**
     * Adds the lines it gets to the tool tip list, used for Item tool tips
     * @param tooltip An ITextComponent List, simply put in the one from the method
     * @param langPath The path for the lang file
     * @param stack The ItemStack
     */
    public static void getTooltipFromLang(List<Component> tooltip, String langPath, ItemStack stack)
    {
        String rawText = new TranslatableComponent(langPath).getString();
        rawText = rawText.replace("#c", "§");
        if(rawText.contains("\n"))
        {
            String[] textList = rawText.split("\n");
            for(String text : textList)
            {
                tooltip.add(new TextComponent(text));
            }
        }
        else
        {
            tooltip.add(new TextComponent(rawText));
        }

        //Renders another "empty" line if the item is enchanted, so the enchantments are easier to read
        if(!EnchantmentHelper.getEnchantments(stack).isEmpty())
        {
            tooltip.add(new TextComponent(""));
        }
    }

    public static void getToolTipWithTextFromLang(List<Component> tooltip, String langPath, String textLangPath, ItemStack stack)
    {
        String rawText = new TranslatableComponent(langPath, new TranslatableComponent(textLangPath)).getString();
        rawText = rawText.replace("#c", "§");
        if(rawText.contains("\n"))
        {
            String[] textList = rawText.split("\n");
            for(String text : textList)
                tooltip.add(new TextComponent(text));
        }
        else
        {
            tooltip.add(new TextComponent(rawText));
        }

        // Renders another "empty" line if the item is enchanted, so the enchantments are easier to read
        if(!EnchantmentHelper.getEnchantments(stack).isEmpty())
            tooltip.add(new TextComponent(""));
    }
}