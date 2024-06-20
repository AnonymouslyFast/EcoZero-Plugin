package studio.ecoprojects.ecozero.economy;

import studio.ecoprojects.ecozero.EcoZero;
import studio.ecoprojects.ecozero.economy.database.EconomyDB;

import java.util.*;

public class Economy {

    private final static HashMap<UUID, Double> balances = new HashMap<>();


    public static Double getBalance(UUID uuid) {
        return balances.get(uuid);
    }

    public static void setBalance(UUID uuid, Double value) {
        if (hasAccount(uuid)) {
            balances.replace(uuid, getBalance(uuid), value);
        } else {
            balances.put(uuid, value);
        }

    }

    public static Boolean hasAccount(UUID uuid) {
        return balances.containsKey(uuid);
    }

    public static void createAccount(UUID uuid) {
        if (!hasAccount(uuid)) {
            balances.put(uuid, EcoZero.getPlugin().getConfig().getDouble("starting-balance"));
        }
    }

    public static void removeAccount(UUID uuid) {
        balances.remove(uuid);
        EconomyDB.removeAccount(uuid.toString());
    }

    public static Set<UUID> getBalanceKeySet() {
        return balances.keySet();
    }



}
