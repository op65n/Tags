package frosty.op65n.tech.tagsspigot.listener;

import frosty.op65n.tech.tagsspigot.placeholder.TagPlaceholder;
import frosty.op65n.tech.tagsspigot.util.TaskUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class OnJoinListener implements Listener {

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        TaskUtil.async(() -> {
            try {
                TagPlaceholder.cachePlayerTag(event.getPlayer());
            } catch (SQLException ex) {
                // TODO: (frosty) Handle errors here, idk tell a player something went wrong
                ex.printStackTrace();
            }
        });
    }

}
