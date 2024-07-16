package studio.ecoprojects.ecozero.economy.shop.subshops;

import de.tr7zw.nbtapi.NBT;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import studio.ecoprojects.ecozero.economy.Economy;
import studio.ecoprojects.ecozero.economy.shop.Product;
import studio.ecoprojects.ecozero.economy.shop.SubShop;
import studio.ecoprojects.ecozero.utils.Colors;
import studio.ecoprojects.ecozero.utils.ItemUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class SubShopOres extends SubShop {


    public SubShopOres() {
        setSubShop();
        setItems();
        addSubShop(this);
   }



    private void setItems() {
        for (int i = 0; i < 50; i++) {
            this.addProduct(new Product(Material.COAL_ORE, "Coal Ore",15d, 6d, this));
        }
    }


   private void setSubShop() {
       ItemStack icon = new ItemStack(Material.COAL_ORE);
       ItemUtils.setItemName(icon, "&8&lOres");
       ItemUtils.setItemLore(icon, List.of(Colors.translateCodes("&7Click to open shop")));
       ItemUtils.setItemFlags(icon, ItemFlag.HIDE_ATTRIBUTES);
       setShopIcon(icon);
       setShopSlot(10);
       setShopName("Ores");
   }

   @Override
   public Inventory getInventory(int page) {
        boolean hasNextPage = false;
        boolean hasPastPage = false;
        String title = Colors.translateCodes(getShopInventoryName());
        if (this.getAmountOfProducts() > 21) hasNextPage = true;
        if (page > 1) {
            hasPastPage = true;
            title = Colors.translateCodes(getShopInventoryName() + " &7(" + page + ")");
            if (items.size() > 21*page) {
                hasNextPage = true;
            } else {
                hasNextPage = false;
            }
        }
        Inventory inventory = Economy.getShop().createSubShopGUITemplate(title, hasPastPage, hasNextPage);
        int nextSlot = 10;
        List<Product> tempItems = new ArrayList<>(getItems());
        if (hasNextPage) {
            for (int i = 0; i < tempItems.size(); i++) {
                if (i <= 21*(page-1)) {
                    tempItems.remove(i);
                }
            }
        }
        for (ShopItem item : tempItems) {
            if (nextSlot == 17 || nextSlot == 18) { nextSlot = 19; }
            if (nextSlot == 26 || nextSlot == 27) { nextSlot = 28; }
            if (nextSlot > 34) { break; }

            ItemStack icon = new ItemStack(item.getMaterial());
            ItemUtils.setItemFlags(icon, ItemFlag.HIDE_ATTRIBUTES);
            ItemUtils.setItemName(icon, item.getDisplayName());
            String formatedBuyPrice = NumberFormat.getInstance().format(item.getBuyPrice());
            String formatedSellPrice = NumberFormat.getInstance().format(item.getSellPrice());
            ItemUtils.setItemLore(icon,
                    List.of(Colors.translateCodes("&bItem's Information:"),
                            Colors.translateCodes("  &7Buy price: &2&l$&f" + formatedBuyPrice),
                            Colors.translateCodes("  &7Sell price: &2&l$&f" + formatedSellPrice),
                            Colors.translateCodes("&7Left click to buy, right click to sell.")));


            NBT.modify(icon, nbt -> {
                nbt.setUUID("ItemUUID", item.getUuid());
            });

            inventory.setItem(nextSlot, icon);

            nextSlot++;
        }
        return inventory;
   }


}
