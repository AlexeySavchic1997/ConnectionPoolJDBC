package by.alexeysavchic.connection;

import by.alexeysavchic.config.ApplicationProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool
{
    private static final ApplicationProperties applicationProperties=ApplicationProperties.getApplicationProperties();
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds = new HikariDataSource(init());

    private static HikariConfig  init() {
        config.setJdbcUrl(applicationProperties.getUrl()); // you should use application yaml
        config.setUsername(applicationProperties.getUsername()); //same
        config.setPassword(System.getProperty("db.pass")); // entrypoint
        config.setMaximumPoolSize(applicationProperties.getMaximumPoolSize()); // you should use application yaml
        config.setMinimumIdle(applicationProperties.getMinimumIdle());// you should use application yaml
        return config;
    }

    public static Connection getConnection() throws SQLException
    {
        return ds.getConnection();
    }
}
