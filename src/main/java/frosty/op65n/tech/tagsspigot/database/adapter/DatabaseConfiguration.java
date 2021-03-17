package frosty.op65n.tech.tagsspigot.database.adapter;

import frosty.op65n.tech.tagsspigot.TagsPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.intellij.lang.annotations.Language;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DatabaseConfiguration {

    public DatabaseConfiguration(final TagsPlugin plugin) {
        final File file = new File(plugin.getDataFolder(), "hikari-settings.yml");
        final FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        this.pool = configuration.getString("pool");
        this.poolSize = configuration.getInt("pool-size");
        this.ip = configuration.getString("ip");
        this.port = configuration.getString("port");
        this.jdbc = configuration.getString("jdbc");
        this.driverClassName = configuration.getString("driver-class");
        this.database = configuration.getString("database");
        this.username = configuration.getString("username");
        this.passwd = configuration.getString("password");

        for (final String key : configuration.getConfigurationSection("properties").getKeys(false)) {
            this.properties.put(key, configuration.getString("properties." + key));
        }
    }

    public String pool = "TagsPool";

    public Integer poolSize = 4;

    public String ip;

    public String port;

    public String jdbc = "mariadb";

    public String driverClassName = "org.mariadb.jdbc.Driver";

    @Language("MariaDB")
    public String database;

    public String username;

    public String passwd;

    public Map<String, String> properties = new HashMap<>();

}

