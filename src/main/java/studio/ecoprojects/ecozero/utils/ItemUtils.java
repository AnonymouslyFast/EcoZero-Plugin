package studio.ecoprojects.ecozero.utils;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemUtils {

    public static void setItemName(ItemStack item, String name) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(itemMeta);
    }

    public static void setItemLore(ItemStack item, List<String> lore) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
    }

    public static void setItemFlags(ItemStack item, ItemFlag flag) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addItemFlags(flag);
        item.setItemMeta(itemMeta);
    }

}
