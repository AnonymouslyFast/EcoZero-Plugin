package studio.ecoprojects.ecozero.economy.database;

import org.jdbi.v3.core.Handle;
import studio.ecoprojects.ecozero.EcoZero;
import studio.ecoprojects.ecozero.utils.DataBaseSetUp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EconomyDB {

    public static boolean dataBaseHasTable() {
        try {
            ResultSet set = DataBaseSetUp.jdbi.open().getConnection().
                    getMetaData().getTables(null, null, null, new String[] {"TABLE"});
            while (set.next()) {
                String name = set.getString("TABLE_NAME");
                if (name.equalsIgnoreCase("balances")) return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    public static void createBalancesTable() {
        try (Handle handle = DataBaseSetUp.jdbi.open()) {
            handle.execute("CREATE TABLE balances (uuid varchar(255), balance double)");
            EcoZero.logger.info("Created 'balances' table.");
        }
    }

    public static void addAccount(String Uuid, Double balance) {
        try (Handle handle = DataBaseSetUp.jdbi.open()) {
            handle.execute("INSERT INTO balances (uuid, balance) VALUES ('" + Uuid + "', '" + balance + "')");
        }
    }

    public static void removeAccount(String Uuid) {
        try (Handle handle = DataBaseSetUp.jdbi.open()) {
            handle.execute("DELETE FROM balances WHERE uuid='" + Uuid + "'");
        }
    }



    public static Optional<Double> getBalance(String Uuid) {

        try (Handle handle = DataBaseSetUp.jdbi.open()) {
            return handle.createQuery("SELECT balance FROM balances WHERE uuid='" + Uuid + "'").mapTo(Double.class).findOne();
        }
    }



    public static HashMap<UUID, Double> getAccounts() {
        List<UUID> uuids;
        HashMap<UUID, Double> values = new HashMap<>();
        try (Handle handle = DataBaseSetUp.jdbi.open()) {
            uuids = handle.createQuery("SELECT uuid FROM balances").mapTo(UUID.class).collectIntoList();
        }

        for (UUID uuid : uuids) {
            if (getBalance(uuid.toString()).isPresent()) {
                values.put(uuid, getBalance(uuid.toString()).get());
            }
        }

        return values;
    }

}
