package studio.ecoprojects.ecozero.economy.shop;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class SubShop {

    private static final List<SubShop> subShops = new ArrayList<>();

    private String shopName;
    private ItemStack shopIcon;
    private Integer shopSlot;
    private final HashMap<Material, Double> shopItems = new HashMap<>();
    private String shopInventoryName = null;

    public SubShop getSubShop() {
        return this;
    }

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

    public Integer getShopSlot() {
        return shopSlot;
    }

    public Inventory getInventory(Integer page) {
        return null;
    }

    public static List<SubShop> getSubShops() {
        return subShops;
    }

    public static void addSubShop(SubShop subShop) {
        subShops.add(subShop);
    }

}
