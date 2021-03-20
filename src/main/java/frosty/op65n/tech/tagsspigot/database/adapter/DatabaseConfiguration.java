package frosty.op65n.tech.tagsspigot.database.adapter;

import frosty.op65n.tech.tagsspigot.util.FileUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.intellij.lang.annotations.Language;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class DatabaseConfiguration {

    public DatabaseConfiguration() {
        final FileConfiguration configuration = FileUtil.getConfiguration("hikari-settings.yml");

        this.pool = configuration.getString("pool", "TagsPool");
        this.poolSize = configuration.getInt("pool_size", 2);
        this.ip = configuration.getString("ip");
        this.port = configuration.getString("port");
        this.jdbc = configuration.getString("jdbc");
        this.driverClassName = configuration.getString("driver_class");
        this.database = configuration.getString("database");
        this.username = configuration.getString("username");
        this.passwd = configuration.getString("password");

        final ConfigurationSection propertiesSection = configuration.getConfigurationSection("properties");

        if (propertiesSection == null) return;

        final Set<String> keys = propertiesSection.getKeys(false);
        keys.forEach(key -> {
            final String value = propertiesSection.getString(key);
            properties.put("dataSource." + key, value);
        });
    }

    public final String pool;

    public final Integer poolSize;

    public final String ip;

    public final String port;

    public final String jdbc;

    public final String driverClassName;

    @Language("MariaDB")
    public final String database;

    public final String username;

    public final String passwd;

    public final Map<String, String> properties = new HashMap<>();

}

