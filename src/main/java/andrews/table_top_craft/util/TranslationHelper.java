package andrews.table_top_craft.util;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.List;

public class TranslationHelper
{
    /**
     * Adds the lines it gets to the tool tip list, used for Item tool tips
     * @param tooltip An ITextComponent List, simply put in the one from the method
     * @param langPath The path for the lang file
     */
    public static void getTooltipFromLang(List<Component> tooltip, String langPath)
    {
        String rawText = Component.translatable(langPath).getString();
        rawText = rawText.replace("#c", "§");
        if(rawText.contains("\n"))
        {
            String[] textList = rawText.split("\n");
            for(String text : textList)
            {
                tooltip.add(Component.literal(text));
            }
        }
        else
        {
            tooltip.add(Component.literal(rawText));
        }
    }

    public static void getToolTipWithTextFromLang(List<Component> tooltip, String langPath, String textLangPath)
    {
        String rawText = Component.translatable(langPath, Component.translatable(textLangPath)).getString();
        rawText = rawText.replace("#c", "§");
        if(rawText.contains("\n"))
        {
            String[] textList = rawText.split("\n");
            for(String text : textList)
                tooltip.add(Component.literal(text));
        }
        else
        {
            tooltip.add(Component.literal(rawText));
        }
    }

    public static void getToolTipWithDoubleFromLang(List<Component> tooltip, String langPath, double value)
    {
        String valueString = String.valueOf(value);
        String rawText = Component.translatable(langPath, Component.literal(valueString)).getString();
        rawText = rawText.replace("#c", "§");
        if(rawText.contains("\n"))
        {
            String[] textList = rawText.split("\n");
            for(String text : textList)
                tooltip.add(Component.literal(text));
        }
        else
        {
            tooltip.add(Component.literal(rawText));
        }
    }

    public static void addEnchantmentSeparationLine(List<Component> tooltip, ItemStack stack)
    {
        // Renders another "empty" line if the item is enchanted, so the enchantments are easier to read
        if(!EnchantmentHelper.getEnchantments(stack).isEmpty())
            tooltip.add(Component.literal(""));
    }
}