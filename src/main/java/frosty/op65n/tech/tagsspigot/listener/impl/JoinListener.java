package frosty.op65n.tech.tagsspigot.listener.impl;

import frosty.op65n.tech.tagsspigot.storage.TagRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        TagRegistry.requestActiveTagForUser(event.getPlayer());
    }

}
