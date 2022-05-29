package be.alexandre01.eloriamc.data.mysql;

public enum DatabaseManager {
    api(new DatabaseCredentials("localhost", "eloriamc", "A97m76Mv5mHGQr4zs5CWcG7vKD7Jrbj2M657nAisqzU73X45Yp", "dev", 3306));
    private DatabaseAccess databaseAccess;
    DatabaseManager(DatabaseCredentials credentials) {
        this.databaseAccess = new DatabaseAccess(credentials);
    }

    public DatabaseAccess getDatabaseAccess() {
        return databaseAccess;
    }


    public static void initAllDatabaseConnection() {
        for(DatabaseManager databaseManager : values()) {
            databaseManager.databaseAccess.initPool();
        }
    }

    public static void closeAllDatabaseConnection() {
        for(DatabaseManager databaseManager : values()) {
            databaseManager.databaseAccess.closePool();
        }
    }
}
