package studio.ecoprojects.ecozero.economy.shop.subshops;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import studio.ecoprojects.ecozero.economy.Economy;
import studio.ecoprojects.ecozero.economy.shop.Shop;
import studio.ecoprojects.ecozero.economy.shop.ShopItem;
import studio.ecoprojects.ecozero.economy.shop.SubShop;
import studio.ecoprojects.ecozero.utils.Colors;
import studio.ecoprojects.ecozero.utils.ItemUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class SubShopOres extends SubShop {

    private final List<ShopItem> items = new ArrayList<>();

    public SubShopOres() {
        setSubShop();
        setItems();
        addSubShop(getSubShop());
   }

    public List<ShopItem> getItems() {
        return items;
    }

    // dupe lines for testing purposes.
    private void setItems() {
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
        items.add(new ShopItem(Material.COAL_ORE, "Coal Ore",15d, 6d));
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
   public Inventory getInventory(Integer page) {
        boolean hasNextPage = false;
        boolean hasPastPage = false;
        if (items.size() > 35*page) hasNextPage = true;
        if (page > 1) hasPastPage = true;
        Inventory inventory = Economy.getShop().createSubShopGUITemplate(Colors.translateCodes(getShopInventoryName()), hasPastPage, hasNextPage);
        int nextSlot = 10;
        for (ShopItem item : items) {
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

            inventory.setItem(nextSlot, icon);

            nextSlot++;
        }
        return inventory;
   }


}
