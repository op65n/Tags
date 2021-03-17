package frosty.op65n.tech.tagsspigot.database.adapter;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import frosty.op65n.tech.tagsspigot.database.tables.TableTagRegistry;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionAdapter {

    private InitStatus status = InitStatus.OK;
    private String error;

    private DatabaseConfiguration loadConfiguration() {
        return new DatabaseConfiguration();
    }

    private HikariDataSource createHikariDataSource(final DatabaseConfiguration configuration) {
        final Properties hikariProperties = new Properties();

        // Add all properties in an map to properties
        if (configuration.properties != null && !configuration.properties.isEmpty()) {
            configuration.properties.forEach(hikariProperties::setProperty);
        }
        // Apply the properties to HikariConfig
        HikariConfig hikariConfig = new HikariConfig(hikariProperties);
        // Set some common properties manually from the config
        hikariConfig.setPoolName(configuration.pool);
        hikariConfig.setMaximumPoolSize(configuration.poolSize);
        hikariConfig.setJdbcUrl("jdbc:" + configuration.jdbc + "://" + configuration.ip + ":" + configuration.port + "/" + configuration.database);
        hikariConfig.setUsername(configuration.username);
        hikariConfig.setPassword(configuration.passwd);
        hikariConfig.setDriverClassName(configuration.driverClassName);

        // Create hikari dataSource from the hikari config
        return new HikariDataSource(hikariConfig);
    }

    private void createTables(@Language("MariaDB") String database, final HikariDataSource hikariDataSource) throws SQLException {
        // List of all database table
        final List<ITable> tables = ITable.sort(
                new TableTagRegistry(database)
        );

        final Connection connection = hikariDataSource.getConnection();

        for (final ITable table : tables) {
            final PreparedStatement createQuery = connection.prepareStatement(table.getCreateQuery());
            createQuery.execute();
            createQuery.close();
        }

        connection.close();
    }

    public InitStatus initialize(final @NotNull ConcurrentHashMap<Integer, ConnectionHolder> connectionHolders) {
        final DatabaseConfiguration configuration = new DatabaseConfiguration();

        final HikariDataSource hikariDataSource = createHikariDataSource(configuration);

        try {
            createTables(configuration.database, hikariDataSource);
            for (int index = 0; index < configuration.poolSize; index++) {
                final ConnectionHolder holder = new ConnectionHolder(hikariDataSource.getConnection(), index);
                connectionHolders.put(index, holder);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            status = InitStatus.ERROR;
            return status;
        }

        return status;
    }

    public String error() {
        return error;
    }

    public enum InitStatus {
        OK, ERROR
    }
}

