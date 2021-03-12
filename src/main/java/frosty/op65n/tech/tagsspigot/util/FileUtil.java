package frosty.op65n.tech.tagsspigot.util;

import frosty.op65n.tech.tagsspigot.TagsPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;

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

}
