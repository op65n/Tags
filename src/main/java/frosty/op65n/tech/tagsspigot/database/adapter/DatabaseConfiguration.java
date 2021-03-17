package frosty.op65n.tech.tagsspigot.database.adapter;

import java.util.Map;

public class DatabaseConfiguration {

    public DatabaseConfiguration() {
        // TODO: (Frosty) When this constructor is called all public variables should be loaded from file with database settings
    }

    public String pool = "TagsPool";

    public Integer poolSize = 4;

    public String ip;

    public String port;

    public String jdbc = "mariadb";

    public String driverClassName = "org.mariadb.jdbc.Driver";

    public String database;

    public String username;

    public String passwd;

    public Map<String, String> properties;

}

