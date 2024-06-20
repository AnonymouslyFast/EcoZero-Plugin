package studio.ecoprojects.ecozero.utils;

import org.jdbi.v3.core.Jdbi;
import studio.ecoprojects.ecozero.EcoZero;
import studio.ecoprojects.ecozero.discordintergration.database.VerifiedDB;
import studio.ecoprojects.ecozero.economy.database.EconomyDB;

public class DataBaseSetUp {

    private static String databaseUsername;
    private static String databasePassword;
    private static String databaseUrl;
    public static Jdbi jdbi;

    public static void login() {
        jdbi = Jdbi.create(databaseUrl, databaseUsername, databasePassword);
        if (!VerifiedDB.dataBaseHasTable()) {
            EcoZero.logger.info("Database does not contain 'verified' table.. creating it");
            VerifiedDB.createVerifiedTable();
        }
        if (!EconomyDB.dataBaseHasTable()) {
            EcoZero.logger.info("Database does not contain 'balances' table.. creating it");
            EconomyDB.createBalancesTable();
        }

        EcoZero.logger.info("DataBase Connected");
    }

    public static void setUsername(String username) {
        databaseUsername = username;
    }

    public static void setPassword(String password) {
        databasePassword = password;
    }

    public static void setUrl(String url) {
        databaseUrl = url;
    }



}
