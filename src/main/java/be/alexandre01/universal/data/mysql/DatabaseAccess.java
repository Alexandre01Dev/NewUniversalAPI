package be.alexandre01.universal.data.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseAccess {

    private DatabaseCredentials databaseCredentials;
    private HikariDataSource hikariDataSource;


    public DatabaseAccess(DatabaseCredentials databaseCredentials) {
        this.databaseCredentials = databaseCredentials;
    }

    private void setupHikariCP() {
        final HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setMaximumPoolSize(50);
        hikariConfig.setJdbcUrl(databaseCredentials.ToURL());
        hikariConfig.setUsername(databaseCredentials.getUser());
        hikariConfig.setPassword(databaseCredentials.getPass());
        hikariConfig.setMaxLifetime(600000L);
        hikariConfig.setLeakDetectionThreshold(300000L);
        hikariConfig.setConnectionTimeout(10000L);
        this.hikariDataSource = new HikariDataSource((hikariConfig));
    }

    public void initPool() {
        setupHikariCP();
    }

    public void closePool() {
        this.hikariDataSource.close();

    }

    public Connection getConnection() throws SQLException {
        if(this.hikariDataSource == null) {
            System.out.println("Not connected userbase");
            setupHikariCP();
        }
        return this.hikariDataSource.getConnection();

    }
}
