package frosty.op65n.tech.tagsspigot.listener;

import frosty.op65n.tech.tagsspigot.TagsPlugin;
import frosty.op65n.tech.tagsspigot.listener.impl.JoinListener;
import frosty.op65n.tech.tagsspigot.registry.Registerable;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;

public final class ListenerRegisterable implements Registerable {

    @Override
    public void enable(final TagsPlugin plugin) {
        final PluginManager manager = plugin.getServer().getPluginManager();

        manager.registerEvents(new JoinListener(), plugin);
    }

    @Override
    public void disable(final TagsPlugin plugin) {
        HandlerList.unregisterAll(plugin);
    }

}
