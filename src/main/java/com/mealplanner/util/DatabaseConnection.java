package com.mealplanner.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseConnection {

    private static final HikariDataSource ds;

    static {
        Properties properties = new Properties();
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("Sorry, unable to find application.properties");
            }

            // Load properties file
            properties.load(input);

            // Create HikariCP configuration
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(properties.getProperty("db.url"));
            config.setUsername(properties.getProperty("db.username"));
            config.setPassword(properties.getProperty("db.password"));

            // Optional settings
            config.addDataSourceProperty("cachePrepStmts", properties.getProperty("db.cachePrepStmts"));
            config.addDataSourceProperty("prepStmtCacheSize", properties.getProperty("db.prepStmtCacheSize"));
            config.addDataSourceProperty("prepStmtCacheSqlLimit", properties.getProperty("db.prepStmtCacheSqlLimit"));

            ds = new HikariDataSource(config);

        } catch (IOException ex) {
            throw new RuntimeException("Failed to load database configuration", ex);
        }
    }

    private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
