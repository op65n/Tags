package frosty.op65n.tech.tagsspigot;

import frosty.op65n.tech.tagsspigot.command.CommandRegisterable;
import frosty.op65n.tech.tagsspigot.configuration.ConfigurationRegisterable;
import frosty.op65n.tech.tagsspigot.listener.ListenerRegisterable;
import frosty.op65n.tech.tagsspigot.placeholder.PlaceholderRegisterable;
import frosty.op65n.tech.tagsspigot.registry.Registerable;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public final class TagsPlugin extends JavaPlugin {

    private static final Set<Registerable> REGISTRIES = Set.of(
            new CommandRegisterable(), new ConfigurationRegisterable(), new ListenerRegisterable(), new PlaceholderRegisterable()
    );

    @Override
    public void onEnable() {
        REGISTRIES.forEach(it -> it.enable(this));
    }

    @Override
    public void onDisable() {
        REGISTRIES.forEach(it -> it.disable(this));
    }

}
