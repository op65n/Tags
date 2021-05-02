package frosty.op65n.tech.tagsspigot.storage;

import frosty.op65n.tech.tagsspigot.TagsPlugin;
import frosty.op65n.tech.tagsspigot.storage.impl.TagHolder;
import frosty.op65n.tech.tagsspigot.util.PermissionUtil;
import frosty.op65n.tech.tagsspigot.util.TaskUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.op65n.gazelle.api.connection.ConcurrentConnection;
import org.op65n.gazelle.api.holder.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public final class TagRegistry {

    private static final Map<String, TagHolder> TAG_REGISTRY = new HashMap<>();

    public Map<String, TagHolder> getTagRegistry() {
        return TAG_REGISTRY;
    }

    public TagHolder getTagWithIdentifier(final String identifier) {
        return TAG_REGISTRY.get(identifier);
    }

    public Map<String, TagHolder> getTagsForUser(final Player player) {
        final Map<String, TagHolder> result = new HashMap<>();

        for (final String key : TAG_REGISTRY.keySet()) {
            final TagHolder holder = TAG_REGISTRY.get(key);

            if (!PermissionUtil.hasPermission(player, holder.getPermission())) {
                continue;
            }

            result.put(key, holder);
        }

        return result;
    }


    public void request(final long delay) {
        TaskUtil.async(() -> {
            try {
                final DataSource dataSource = new ConcurrentConnection().borrow();
                final PreparedStatement updateQuery = dataSource.prepare(
                        "SELECT identifier, description, display, permission FROM config_registry;"
                );

                final ResultSet result = updateQuery.executeQuery();

                dataSource.borrow(result.getFetchSize());
                while (result.next()) {
                    final String identifier = result.getString("identifier");

                    TAG_REGISTRY.put(
                            identifier,
                            new TagHolder(
                                    identifier,
                                    result.getString("description"),
                                    result.getString("display"),
                                    result.getString("permission")
                            )
                    );
                }

                updateQuery.close();
                dataSource.free();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }, delay);
    }

    public void load(final TagsPlugin plugin) {
        final FileConfiguration configuration = plugin.getConfig();

        if (!configuration.getBoolean("main-file-registry")) {
            return;
        }

        final ConfigurationSection section = configuration.getConfigurationSection("tags");
        if (section == null) {
            return;
        }

        TaskUtil.async(() ->
                section.getKeys(false).forEach(it -> {
                    try {
                        final ConfigurationSection tagSection = section.getConfigurationSection(it);
                        if (tagSection == null) {
                            return;
                        }

                        final DataSource dataSource = new ConcurrentConnection().borrow();
                        final PreparedStatement updateQuery = dataSource.prepare(
                                "REPLACE INTO config_registry (identifier, description, display, permission) VALUES (?, ?, ?, ?);"
                        );

                        updateQuery.setString(1, it);
                        updateQuery.setString(2, tagSection.getString("description"));
                        updateQuery.setString(3, tagSection.getString("display"));
                        updateQuery.setString(4, tagSection.getString("permission"));
                        updateQuery.executeQuery();

                        updateQuery.close();
                        dataSource.free();
                    } catch (final SQLException ex) {
                        ex.printStackTrace();
                    }
                })
        );
    }

}
