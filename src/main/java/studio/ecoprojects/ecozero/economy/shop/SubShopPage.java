package studio.ecoprojects.ecozero.economy.shop;

import studio.ecoprojects.ecozero.economy.shop.Inventories.SubShopInventory;

import java.util.ArrayList;
import java.util.List;

public class SubShopPage {

    private final int pageNumber;
    private final SubShop subShop;
    private final SubShopInventory subShopInventory;
    private final boolean hasNextPage;
    private final boolean hasPreviousPage;
    private final List<Product> products = new ArrayList<>();

    public SubShopPage(SubShop subShop, List<Product> products, int pageNumber, boolean hasNextPage, boolean hasPreviousPage) {
        this.subShop = subShop;
        this.pageNumber = pageNumber;
        this.hasNextPage = hasNextPage;
        this.hasPreviousPage = hasPreviousPage;
        this.products.addAll(products);
        this.subShopInventory = new SubShopInventory(this);
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public SubShop getSubShop() {
        return subShop;
    }

    public SubShopInventory getSubShopInventory() {
        return subShopInventory;
    }

    public boolean hasNextPage() {
        return hasNextPage;
    }

    public boolean hasPreviousPage() {
        return hasPreviousPage;
    }

    public List<Product> getProducts() {
        return products;
    }
}
