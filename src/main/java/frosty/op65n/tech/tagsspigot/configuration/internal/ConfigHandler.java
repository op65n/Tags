package frosty.op65n.tech.tagsspigot.configuration.internal;

import frosty.op65n.tech.tagsspigot.TagsPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.reference.ConfigurationReference;
import org.spongepowered.configurate.reference.ValueReference;
import org.spongepowered.configurate.reference.WatchServiceListener;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.IOException;
import java.nio.file.Path;

public final class ConfigHandler<T> implements AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagsPlugin.class);

    private WatchServiceListener listener;
    private ConfigurationReference<CommentedConfigurationNode> base;
    private ValueReference<T, CommentedConfigurationNode> config;

    private final Class<T> clazz;
    private final Path configFile;

    public ConfigHandler(final Path applicationFolder, final String configName, final Class<T> clazz) {
        this.clazz = clazz;
        this.configFile = applicationFolder.resolve(configName);

        try {
            this.listener = WatchServiceListener.create();
            this.base = this.listener.listenToConfiguration(file ->
                            YamlConfigurationLoader.builder()
                                    .defaultOptions(opts -> opts
                                            .shouldCopyDefaults(true)
                                    )
                                    .nodeStyle(NodeStyle.BLOCK)
                                    .indent(2)
                                    .path(file)
                                    .build()
                    , configFile);

            this.listener.listenToFile(configFile, event -> {
                LOGGER.info("Updated ConfigFile {}/{}", applicationFolder.getFileName().toString(), configFile.getFileName().toString());
                if (getConfig() instanceof Config conf) {
                    conf.onUpdate().accept(event);
                }
            });

            this.config = this.base.referenceTo(clazz);
            this.base.save();

        } catch (final IOException exception) {
            exception.printStackTrace();
        }
    }

    public T getConfig() {
        return config.get();
    }

    public void saveToFile() throws ConfigurateException {
        this.base.node().set(clazz, clazz.cast(getConfig()));
        this.base.loader().save(this.base.node());
    }

    @Override
    public void close() throws Exception {
        try {
            this.listener.close();
            this.base.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
