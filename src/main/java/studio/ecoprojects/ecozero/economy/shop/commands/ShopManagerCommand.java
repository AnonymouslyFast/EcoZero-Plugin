package studio.ecoprojects.ecozero.economy.shop.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import studio.ecoprojects.ecozero.EcoZero;
import studio.ecoprojects.ecozero.economy.shop.Product;
import studio.ecoprojects.ecozero.economy.shop.Shop;
import studio.ecoprojects.ecozero.economy.shop.SubShop;
import studio.ecoprojects.ecozero.utils.Colors;
import studio.ecoprojects.ecozero.utils.TabCompleteUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ShopManagerCommand implements CommandExecutor, TabCompleter {

    private Shop shop;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (shop == null) shop = EcoZero.getEconomy().getShop();

        if (!sender.hasPermission("economy.admin")) {
            sender.sendMessage(Colors.translateCodes("&cNo permission!"));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(getHelpMessage());
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(getHelpMessage());
            } else if (args[0].equalsIgnoreCase("setenabled")) {
                shop.setEnabled(!shop.isEnabled());
                sender.sendMessage(Colors.translateCodes("&aUpdated! The new enabled state is now: &7" + shop.isEnabled()));
            }
        } else {
            if (args[0].equalsIgnoreCase("delete")) {
                if (args.length != 2) {
                    sender.sendMessage(getHelpMessage());
                    return true;
                }
                String shopName = args[1];
                if (!shop.getSubShops().containsKey(shopName)) {
                    sender.sendMessage(getHelpMessage());
                    return true;
                }
                shop.removeSubShop(shopName);
                shop.reloadShop();
                sender.sendMessage(Colors.translateCodes("&aSuccessfully removed &b" + shopName + "&a shop!"));
            } else if (args[0].equalsIgnoreCase("create")) {
                if (args.length != 2) {
                    sender.sendMessage(getHelpMessage());
                    return true;
                }
                if (Objects.requireNonNull(shop).getSubShops().containsKey(args[1])) {
                    sender.sendMessage(Colors.translateCodes("&cThere's already a subshop with that name!"));
                    return true;
                }
                String shopName = args[1];
                shop.addSubShop(new SubShop(shopName, Material.BARRIER, 0));
                shop.reloadShop();
                sender.sendMessage(Colors.translateCodes("&aCreated &7" + shopName + "&a!"));


            } else if (args[0].equalsIgnoreCase("edit")) {
                if (args.length < 3) {
                    sender.sendMessage(getHelpMessage());
                    return true;
                }
                String shopName = args[1];
                if (!shop.getSubShops().containsKey(shopName)) {
                    sender.sendMessage(Colors.translateCodes("&cThere's no subshop with that name!"));
                    return true;
                }
                SubShop subshop = shop.getSubShops().get(shopName);

                if (args[2].equalsIgnoreCase("seticon")) {
                    if (args.length != 4) {
                        sender.sendMessage(getHelpMessage());
                        return true;
                    }
                    Material material = Material.getMaterial(args[3].toUpperCase());
                    if (material == null) {
                        sender.sendMessage(Colors.translateCodes("&cThat material does not exist!"));
                        return true;
                    }
                    subshop.setMaterial(material);
                    sender.sendMessage(Colors.translateCodes("&aUpdated! The new icon's material is now: &7" + material));
                    shop.reloadShop();
                } else if (args[2].equalsIgnoreCase("setslot")) {
                    if (args.length != 4) {
                        sender.sendMessage(getHelpMessage());
                        return true;
                    }

                    try {
                        int slot = Integer.parseInt(args[3]);
                        subshop.setSlot(slot);
                        sender.sendMessage(Colors.translateCodes("&aUpdated! The new slot is now: &7" + slot));
                        shop.reloadShop();
                    } catch (NumberFormatException e) {
                        sender.sendMessage(Colors.translateCodes("&cThat is not a number!"));
                        return true;
                    }

                } else if (args[2].equalsIgnoreCase("setenabled")) {
                    subshop.setEnabled(!subshop.isEnabled());
                    sender.sendMessage(Colors.translateCodes("&aUpdated! The new enabled state is now: &7" + subshop.isEnabled()));

                } else if (args[2].equalsIgnoreCase("setdisplayname")) {
                    if (args.length != 4) {
                        sender.sendMessage(getHelpMessage());
                        return true;
                    }
                    String name = Colors.translateCodes(args[3]);
                    subshop.setDisplayName(name);
                    sender.sendMessage(Colors.translateCodes("&aUpdated! The new display name is now: " + name));

                } else if (args[2].equalsIgnoreCase("addproduct")) {
                    if (args.length != 6) {
                        sender.sendMessage(getHelpMessage());
                        return true;
                    }
                    try {
                        Material material = Material.getMaterial(args[3].toUpperCase());
                        if (material == null) {
                            sender.sendMessage(Colors.translateCodes("&cThat material does not exist!"));
                            return true;
                        }
                        String name = material.toString().toLowerCase().replace("_", " ");
                        StringBuilder builder = new StringBuilder();
                        builder.append(name);
                        builder.setCharAt(0, Character.toUpperCase(name.charAt(0)));
                        name = builder.toString();
                        double buyPrice = Double.parseDouble(args[4]);
                        double sellPrice = Double.parseDouble(args[5]);
                        Product product = new Product(material, name, buyPrice, sellPrice, subshop);
                        subshop.addProduct(product);
                        subshop.reloadSubShop();
                        shop.addProduct(product.getProductUUID(), product);
                        sender.sendMessage(Colors.translateCodes(
                                "&aAdded &7" + args[3] + "&a: \n  &8- &aDisplay Name: &7" + name +
                                        "\n  &8- &aBuy Price: &7" + buyPrice + "\n  &8- &aSell Price: &7" + sellPrice +
                                        "\n  &8- &aSub Shop: &7" + subshop.getDisplayName()));
                    } catch (NumberFormatException e) {
                        sender.sendMessage(Colors.translateCodes("&cThat is not a number!"));
                        return true;
                    }

                } else if (args[2].equalsIgnoreCase("removeproduct")) {
                    if (args.length != 4) {
                        sender.sendMessage(getHelpMessage());
                        return true;
                    }
                    String productName = args[3];
                    if (!subshop.getCachedProducts().containsKey(productName)) {
                        sender.sendMessage(Colors.translateCodes("&cThat is not a valid product's name!"));
                        return true;
                    }
                    Product product = subshop.getCachedProducts().get(productName);
                    subshop.removeProduct(product);
                    shop.removeProduct(product.getProductUUID());
                    subshop.reloadSubShop();
                    sender.sendMessage(Colors.translateCodes("&aRemoved &7" + productName));

                } else if (args[2].equalsIgnoreCase("editproduct")) {
                    if (args.length < 5) {
                        sender.sendMessage(getHelpMessage());
                        return true;
                    }
                    String productName = args[3];
                    Product product = subshop.getCachedProducts().get(productName);
                    if (product == null) {
                        sender.sendMessage(Colors.translateCodes("&cThat is not a valid product's name!"));
                        return true;
                    }
                    if (args[4].equalsIgnoreCase("setbuyprice") || args[4].equalsIgnoreCase("setsellprice")) {
                        if (args.length != 6) {
                            sender.sendMessage(getHelpMessage());
                            return true;
                        }
                        try {
                            double price = Double.parseDouble(args[5]);
                            if (args[4].equalsIgnoreCase("setbuyprice")) {
                                product.setBuyPrice(price);
                                sender.sendMessage(Colors.translateCodes("&aUpdated buy price for: &7" + productName));
                            } else {
                                product.setSellPrice(price);
                                sender.sendMessage(Colors.translateCodes("&aUpdated sell price for: &7" + productName));
                            }
                        } catch (NumberFormatException e) {
                            sender.sendMessage(Colors.translateCodes("&cThat is not a number!"));
                            return true;
                        }
                    }
                }

            }
        }
        return true;
    }

    private String getHelpMessage() {

        return Colors.translateCodes("""
                \
                
                &7=== &2&lShop &cManager &7===\
                
                  &8- /shopmanger help &7- &7Sends you this messsage\
                
                  &8- /shopmanger setenabled &7- &7Toggles the enabled state for the Shop\
                
                  &8- /shopmanger delete <subshop name> &7- &7Sends you this messsage\
                
                  &8- /shopmanger create subshop <name> &7- &7Creates the subshop with the given name\
                
                  &8- /shopmanger edit <subshop name> seticon <material> &7- &7Sets the icon displayed in /shop\
                
                  &8- /shopmanger edit <subshop name> setslot <material> &7- &7Sets the slot of the icon displayed in /shop\
                
                  &8- /shopmanger edit <subshop name> setdisplayname <name> &7- &7Sets the subshops display name in /shop\
                
                  &8- /shopmanger edit <subshop name> setenabled &7- &7Toggles the enabled state of the subshop\
                
                  &8- /shopmanger edit <subshop name> addproduct <material> <buy price (Double)> <sell price (Double)> &7- &7Adds the product to the given subshop\
                
                  &8- /shopmanger edit <subshop name> removeproduct <product name> &7- &7Removes the product from the given subshop\
                
                  &8- /shopmanger edit <subshop name> editproduct <product name> setsellprice &7- &7Sets the sell price for the given product\\
                
                  &8- /shopmanger edit <subshop name> editproduct <product name> setbuyprice &7- &7Sets the buy price for the given product\
                
                """);
    }

    private final List<String> arguments1 = Arrays.asList("create", "edit", "help", "delete", "setenabled");
    private final List<String> arguments3Edit = Arrays.asList("seticon", "setslot", "setenabled", "setdisplayname", "addproduct", "removeproduct", "editproduct");
    private final List<String> arguments5EditProduct = Arrays.asList("setbuyprice", "setsellprice");
    private final List<String> arguments4Slots = Arrays.asList("1", "10", "54");
    private final List<String> argumentsDoubles = Arrays.asList("1", "8.64", "4.20");

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (shop == null) shop = EcoZero.getEconomy().getShop();
        if (sender.hasPermission("economy.admin")) {
            if (args.length == 1) {
                return arguments1;
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("edit")) {
                    return new ArrayList<>(shop.getSubShops().keySet());
                } else if (args[0].equalsIgnoreCase("delete")) {
                    return new ArrayList<>(shop.getSubShops().keySet());
                }
            } else if (args.length == 3) {
                if (args[0].equalsIgnoreCase("edit")) {
                    return arguments3Edit;
                }
            } else if (args.length == 4) {
                if (args[2].equalsIgnoreCase("seticon")) {
                    return TabCompleteUtils.getMaterialStrings();
                } else if (args[2].equalsIgnoreCase("setslot")) {
                    return arguments4Slots;
                } else if (args[2].equalsIgnoreCase("addproduct")) {
                    return TabCompleteUtils.getMaterialStrings();
                } else if (args[2].equalsIgnoreCase("removeproduct")) {
                    return new ArrayList<>(shop.getSubShops().get(args[1]).getCachedProducts().keySet());
                } else if (args[2].equalsIgnoreCase("editproduct")) {
                    return new ArrayList<>(shop.getSubShops().get(args[1]).getCachedProducts().keySet());
                }
            } else if (args.length == 5 || args.length == 6) {
                if (args[2].equalsIgnoreCase("addproduct")) {
                    return argumentsDoubles;
                }
                if (args.length == 5) {
                    if (args[2].equalsIgnoreCase("editproduct")) {
                        return arguments5EditProduct;
                    }
                } else {
                    if (args[2].equalsIgnoreCase("editproduct")) {
                        return argumentsDoubles;
                    }
                }
            }

        }
        return List.of();
    }

}
