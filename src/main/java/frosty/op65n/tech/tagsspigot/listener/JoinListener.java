package frosty.op65n.tech.tagsspigot.listener;

import frosty.op65n.tech.tagsspigot.placeholder.TagPlaceholder;
import frosty.op65n.tech.tagsspigot.util.TaskUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public final class JoinListener implements Listener {

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        TaskUtil.async(() -> {
            try {
                TagPlaceholder.cachePlayerTag(event.getPlayer());
            } catch (final SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

}
