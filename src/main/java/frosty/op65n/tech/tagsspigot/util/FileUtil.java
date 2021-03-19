package frosty.op65n.tech.tagsspigot.util;

import frosty.op65n.tech.tagsspigot.TagsPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public final class FileUtil {

    private static final TagsPlugin PLUGIN = JavaPlugin.getPlugin(TagsPlugin.class);

    public static FileConfiguration getConfiguration(final String path) {
        final File file = new File(PLUGIN.getDataFolder(), path);

        return YamlConfiguration.loadConfiguration(file);
    }

    public static void saveResources(final String... paths) {
        Arrays.stream(paths).forEach(path -> {
            if (!new File(PLUGIN.getDataFolder(), path).exists()) {
                PLUGIN.saveResource(path, false);
            }
        });
    }

    public static Properties readPropertiesFile(final String fileName) {
        FileInputStream inputStream = null;
        final Properties properties = new Properties();

        try {
            try {
                inputStream = new FileInputStream(fileName);
                properties.load(inputStream);
            } catch (final IOException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (final IOException ex) {
                ex.printStackTrace();
            }
        }

        return properties;
    }

}
