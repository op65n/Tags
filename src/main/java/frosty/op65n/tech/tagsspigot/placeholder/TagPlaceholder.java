package frosty.op65n.tech.tagsspigot.placeholder;

import frosty.op65n.tech.tagsspigot.database.api.ConcurrentConnection;
import frosty.op65n.tech.tagsspigot.database.api.DataSource;
import frosty.op65n.tech.tagsspigot.storage.TagRegistry;
import frosty.op65n.tech.tagsspigot.storage.impl.TagHolder;
import frosty.op65n.tech.tagsspigot.util.HexUtil;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

public final class TagPlaceholder extends PlaceholderExpansion {

    private static final ConcurrentHashMap<String, String> playerTagCache = new ConcurrentHashMap<>();

    /**
     * This is static because I don't know if I can create new instance for this class
     * TODO: (frosty) if this can be non-static I can easily change this
     */
    public static void cachePlayerTag(final Player player) throws SQLException {
        final DataSource dataSource = new ConcurrentConnection().borrow();

        final PreparedStatement tagFetchQuery = dataSource.prepare("SELECT tag FROM tag_registry WHERE player = ? LIMIT 1;");
        tagFetchQuery.setString(1, player.getName());
        final ResultSet tagFetchResult = tagFetchQuery.executeQuery();

        if (!tagFetchResult.next()) return;
        final String fetchedTag = tagFetchResult.getString("tag");

        playerTagCache.put(player.getName(), fetchedTag);

        dataSource.free();
    }

    private final TagRegistry registry;

    private String retrieveCachedDisplay(final Player player) {
        // TODO: (frosty) modify default value if this is not right, player name is cached because it's less expensive
        return playerTagCache.getOrDefault(player.getName(), "");
    }

    public TagPlaceholder(final TagRegistry registry) {
        this.registry = registry;
    }

    @Override
    public String getVersion() {
        return "1.0.0-Alpha";
    }

    @Override
    public String getAuthor() {
        return "Frcsty";
    }

    @Override
    public String getIdentifier() {
        return "tag";
    }

    @Override
    public String onPlaceholderRequest(final Player player, final String params) {
        final TagHolder holder = this.registry.getActiveTagForUser(player);
        if (params.equalsIgnoreCase("space")) {
            return holder != null ? " " : "";
        }
        if (holder == null) {
            return "";
        }
        switch (params.toLowerCase()) {
            case "identifier" -> {
                return holder.getIdentifier();
            }
            case "display" -> {
                return retrieveCachedDisplay(player);
            }
            case "display-colored" -> {
                // TODO: (frosty) If this needs to be fetched from holder change it back
                // return HexUtil.colorify(holder.getDisplay());
                return HexUtil.colorify(retrieveCachedDisplay(player));
            }
            case "description" -> {
                return holder.getDescription();
            }
            case "permission" -> {
                return holder.getPermission();
            }
            default -> {
                return "";
            }
        }
    }

}
