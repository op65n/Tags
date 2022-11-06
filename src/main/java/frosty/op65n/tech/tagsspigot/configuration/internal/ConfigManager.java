package frosty.op65n.tech.tagsspigot.configuration.internal;

import frosty.op65n.tech.tagsspigot.TagsPlugin;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.configurate.ConfigurateException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigManager implements AutoCloseable {

    private final Path dir;
    private final Map<Class<?>, ConfigHandler<?>> configs = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(TagsPlugin.class);
    private static ConfigManager instance;

    public ConfigManager(final TagsPlugin plugin) {
        this.dir = Paths.get("plugins", plugin.getName());
        if (!dir.toFile().exists()) {
            dir.toFile().mkdirs();
        }
        instance = this;
    }

    @Override
    public void close() {
        for (ConfigHandler<?> configHandler : configs.values()) {
            try {
                configHandler.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void saveConfig(final Class<?> config) {
        try {
            configs.get(config).saveToFile();
        } catch (ConfigurateException e) {
            e.printStackTrace();
        }
    }

    public void initConfigs(final Plugin plugin, final Class<?>... configs) {
        for (final Class<?> config : configs) {
            initConfig(dir.resolve(plugin.getName()), config);
        }
    }

    private void initConfig(final Path dir, final Class<?> config) {
        if (!dir.toFile().exists()) {
            dir.toFile().mkdirs();
        }
        logger.info("Initialising Configuration: {}/{}", dir.getFileName().toString(), config.getSimpleName());
        String fileName = config.getSimpleName().toLowerCase() + ".yml";
        configs.put(config, new ConfigHandler<>(dir, fileName, config));
    }

    @SuppressWarnings("unchecked")
    public <T> T getConfig(final Class<T> config) {
        return (T) configs.get(config).getConfig();
    }

    public static ConfigManager getInstance() {
        return instance;
    }
}
