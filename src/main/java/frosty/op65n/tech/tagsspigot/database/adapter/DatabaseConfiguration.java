package frosty.op65n.tech.tagsspigot.database.adapter;

import frosty.op65n.tech.tagsspigot.TagsPlugin;
import frosty.op65n.tech.tagsspigot.util.FileUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.intellij.lang.annotations.Language;

import java.util.HashMap;
import java.util.Map;

public final class DatabaseConfiguration {

    public DatabaseConfiguration(final TagsPlugin plugin) {
        final FileConfiguration configuration = FileUtil.getConfiguration("hikari-settings.yml");

        this.pool = configuration.getString("pool");
        this.poolSize = Integer.parseInt(configuration.getString("pool-size"));
        this.ip = configuration.getString("ip");
        this.port = configuration.getString("port");
        this.jdbc = configuration.getString("jdbc");
        this.driverClassName = configuration.getString("driver-class");
        this.database = configuration.getString("database");
        this.username = configuration.getString("username");
        this.passwd = configuration.getString("password");

        //sudoku first
        for (final String key : configuration.getConfigurationSection("properties").getKeys(false)) {
            properties.put(key, String.valueOf(configuration.get("properties." + key)));
        }
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

