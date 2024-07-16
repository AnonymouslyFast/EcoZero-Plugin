package studio.ecoprojects.ecozero.economy.shop;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class SubShop {

    private static final List<SubShop> subShops = new ArrayList<>();
    private HashMap<UUID, Integer> openedPages = new HashMap<>();
    private List<Product> products = new ArrayList<>();

    private String shopName;
    private ItemStack shopIcon;
    private Integer shopSlot;
    private String shopInventoryName = null;


    public void setShopName(String name) {
        shopName = name;
    }

    public String getShopInventoryName() {
        return shopInventoryName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopIcon(ItemStack icon) {
        shopIcon = icon;
        shopInventoryName = "&2&lShop &7>> " + Objects.requireNonNull(icon.getItemMeta()).getDisplayName();
    }

    public ItemStack getShopIcon() {
        return shopIcon;
    }

    public void setShopSlot(Integer slot) {
        shopSlot = slot;
    }

    public int getShopSlot() {
        return shopSlot;
    }

    public Inventory getInventory(int page) {
        return null;
    }

    public static List<SubShop> getSubShops() {
        return subShops;
    }

    public static void addSubShop(SubShop subShop) {
        subShops.add(subShop);
    }

    public void addPlayerToPage(Player player, int page) {
        this.openedPages.put(player.getUniqueId(), page);
    }
    public void removePlayerFromPage(Player player) {
        this.openedPages.remove(player.getUniqueId());
    }
    public boolean shopPageContainsPlayer(Player player) {
        return this.openedPages.containsKey(player.getUniqueId());
    }
    public int getShopPagePlayerIsOn(Player player) {
        return this.openedPages.get(player.getUniqueId());
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public int getAmountOfProducts() {
        return this.products.size();
    }
}
