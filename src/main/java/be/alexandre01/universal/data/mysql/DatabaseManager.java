package be.alexandre01.universal.data.mysql;

public enum DatabaseManager {
    api(new DatabaseCredentials("localhost", "eloriamc", "", "dev", 3306));
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
