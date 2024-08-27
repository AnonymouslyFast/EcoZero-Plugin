package studio.ecoprojects.ecozero.economy.shop.Inventories;

import de.tr7zw.nbtapi.NBT;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import studio.ecoprojects.ecozero.EcoZero;
import studio.ecoprojects.ecozero.economy.shop.Product;
import studio.ecoprojects.ecozero.economy.shop.SubShopPage;
import studio.ecoprojects.ecozero.utils.Colors;
import studio.ecoprojects.ecozero.utils.ItemUtils;

import java.text.NumberFormat;
import java.util.List;

public class SubShopInventory {

    private final String name;
    private final Inventory inventory;
    private final SubShopPage subShopPage;



    public SubShopInventory(SubShopPage subShopPage) {
        this.name = Colors.translateCodes(EcoZero.getEconomy().getShop().getShopInventory().getShopName() + " &8&l- &f" + subShopPage.getSubShop().getName()) + " &7(&b" + subShopPage.getPageNumber() + "&7)";
        this.inventory = Bukkit.createInventory(null, 54, this.name);
        this.subShopPage = subShopPage;
        setUpInventory();
    }

    public String getName() {
        return name;
    }
    public Inventory getInventory() {
        return inventory;
    }

    private void setUpInventory() {
        ItemStack[] backFills = new ItemStack[0];
        ItemStack backfill = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemUtils.setItemName(backfill, "&7");
        ItemUtils.setItemFlags(backfill, ItemFlag.HIDE_ATTRIBUTES);

        for(int i = 0; i <= inventory.getSize() - 1; ++i) {
            ItemStack[] newArray = new ItemStack[i + 1];
            System.arraycopy(backFills, 0, newArray, 0, newArray.length - 1);
            newArray[i] = backfill;
            backFills = newArray;
        }

        inventory.setContents(backFills);

        int pastPage = Math.clamp(subShopPage.getPageNumber()-1, 1, 1000);
        ItemStack previousPage = new ItemStack(Material.ARROW);
        ItemUtils.setItemName(previousPage, "&ePrevious Page");
        ItemUtils.setItemFlags(previousPage, ItemFlag.HIDE_ATTRIBUTES);
        ItemUtils.setItemLore(previousPage, List.of(Colors.translateCodes("&7Click to go to page: &f#" + pastPage)));

        ItemStack nextPage = new ItemStack(Material.ARROW);
        ItemUtils.setItemName(nextPage, "&eNext Page");
        ItemUtils.setItemFlags(nextPage, ItemFlag.HIDE_ATTRIBUTES);
        ItemUtils.setItemLore(nextPage, List.of(Colors.translateCodes("&7Click to go to page: &f#" + subShopPage.getPageNumber()+1)));

        ItemStack backToShop = new ItemStack(Material.STRUCTURE_VOID);
        ItemUtils.setItemName(backToShop, "&3&lBack to Shop");
        ItemUtils.setItemFlags(backToShop, ItemFlag.HIDE_ATTRIBUTES);
        ItemUtils.setItemLore(backToShop, List.of(Colors.translateCodes("&7Click to go back to the shop gui")));

        int slot = 10;
        for (Product product : subShopPage.getProducts()) {
            if (slot == 17 || slot == 18) { slot = 19; }
            if (slot == 26 || slot == 27) { slot = 28; }
            if (slot == 35) { break; }

            ItemStack item = new ItemStack(product.getMaterial());
            ItemMeta meta = item.getItemMeta();

            String formattedBuyPrice = NumberFormat.getInstance().format(product.getBuyPrice());
            String formattedSellPrice = NumberFormat.getInstance().format(product.getSellPrice());

            if (meta != null) {
                meta.setDisplayName(Colors.translateCodes(product.getProductName()));
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                meta.setLore(List.of(
                        Colors.translateCodes("&bItem's Information:"),
                        Colors.translateCodes("  &8- &eBuy Price: &2&l$&f" + formattedBuyPrice),
                        Colors.translateCodes("  &8- &eSell Price: &2&l$&f" + formattedSellPrice),
                        Colors.translateCodes("&7Left click to buy, right click to sell.")));
            }
            item.setItemMeta(meta);

            NBT.modify(item, nbt -> {
                nbt.setUUID("ProductUUID", product.getProductUUID());
            });

            inventory.setItem(slot, item);

            inventory.setItem(inventory.getSize()-5, backToShop);
            if (subShopPage.hasPreviousPage()) {
                inventory.setItem(inventory.getSize()-9, previousPage);
            }
            if (subShopPage.hasNextPage()) {
                inventory.setItem(inventory.getSize()-1, nextPage);
            }

            slot++;
        }



    }

    public SubShopPage getSubShopPage() {
        return subShopPage;
    }
}
