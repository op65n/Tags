package frosty.op65n.tech.tagsspigot.configuration;

import frosty.op65n.tech.tagsspigot.TagsPlugin;
import frosty.op65n.tech.tagsspigot.configuration.impl.TagsMenuConfiguration;
import frosty.op65n.tech.tagsspigot.configuration.internal.ConfigManager;
import frosty.op65n.tech.tagsspigot.registry.Registerable;

public final class ConfigurationRegisterable implements Registerable {

    @Override
    public void enable(final TagsPlugin plugin) {
        try (final ConfigManager manager = new ConfigManager(plugin)) {
            manager.initConfigs(plugin,
                    TagsMenuConfiguration.class
            );
        }
    }

}
