package frosty.op65n.tech.tagsspigot.listener;

import frosty.op65n.tech.tagsspigot.placeholder.TagPlaceholder;
import frosty.op65n.tech.tagsspigot.util.TaskUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class JoinListener implements Listener {

    private boolean requested = false;

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        TaskUtil.async(() -> {
            try {
                TagPlaceholder.cachePlayerTag(event.getPlayer());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        if (!requested) {
            final Player player = event.getPlayer();

            System.out.println(player.getServer().getMessenger().getOutgoingChannels());
            requested = true;
        }
    }

}
