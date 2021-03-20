package frosty.op65n.tech.tagsspigot.listener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import frosty.op65n.tech.tagsspigot.TagsPlugin;
import frosty.op65n.tech.tagsspigot.placeholder.TagPlaceholder;
import frosty.op65n.tech.tagsspigot.util.TaskUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

@SuppressWarnings("UnstableApiUsage")
public class JoinListener implements Listener {

    private final TagsPlugin plugin;

    public JoinListener(final TagsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        TaskUtil.async(() -> {
            try {
                TagPlaceholder.cachePlayerTag(event.getPlayer());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        if (plugin.getServer().getOnlinePlayers().size() < 2) {
            final Player player = event.getPlayer();

            plugin.getServer().getMessenger().dispatchIncomingMessage(
                    player,
                    "BungeeCord",
                    request()
            );
        }
    }

    private byte[] request() {
        final ByteArrayDataOutput data = ByteStreams.newDataOutput();

        data.writeUTF("tag-request");
        data.writeUTF("send data");

        return data.toByteArray();
    }

}
