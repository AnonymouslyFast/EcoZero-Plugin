package studio.ecoprojects.ecozero.economy.shop;

import studio.ecoprojects.ecozero.economy.shop.Inventories.ShopInventory;

import java.util.HashMap;
import java.util.UUID;

public class Shop {

    private final ShopInventory shopInventory = new ShopInventory();
    private final HashMap<UUID, Product> products = new HashMap<>();
    private final HashMap<String, SubShop> subShops = new HashMap<>();
    private boolean isEnabled = true;

    public ShopInventory getShopInventory() {
        return shopInventory;
    }

    public void reloadShop() {
        shopInventory.reloadInventory();
        subShops.forEach((uuid, subShop) -> subShop.reloadSubShop());
    }

    public Shop() {
        reloadShop();
    }


    public boolean isEnabled() {
        return isEnabled;
    }
    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public Product getProduct(UUID uuid) {
        return products.get(uuid);
    }
    public void addProduct(UUID uuid, Product product) {
        products.put(uuid, product);
    }
    public void removeProduct(UUID uuid) {
        products.remove(uuid);
    }
    public HashMap<UUID, Product> getProducts() {
        return products;
    }

    public HashMap<String, SubShop> getSubShops() {
        return subShops;
    }
    public SubShop getSubShop(String name) {
        return subShops.get(name);
    }
    public void addSubShop(SubShop subShop) {
        subShops.put(subShop.getName(), subShop);
    }
    public void removeSubShop(String name) {
        subShops.remove(name);
    }

}
