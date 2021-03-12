package frosty.op65n.tech.tagsspigot.storage;

import frosty.op65n.tech.tagsspigot.storage.impl.TagHolder;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public final class TagRegistry {

    private static final Map<String, TagHolder> TAG_REGISTRY = new HashMap<>();

    public void updateRegistry(final String identifier, final TagHolder holder) {
        TAG_REGISTRY.put(identifier.toLowerCase(), holder);
    }

    public Map<String, TagHolder> getTagRegistry() {
        return TAG_REGISTRY;
    }

    public Map<String, TagHolder> getTagsForUser(final Player player) {
        final Map<String, TagHolder> result = new HashMap<>();

        for (final String key : TAG_REGISTRY.keySet()) {
            final TagHolder holder = TAG_REGISTRY.get(key);

            if (!player.hasPermission(holder.getPermission())) {
                continue;
            }

            result.put(key, holder);
        }

        return result;
    }

    public TagHolder getActiveTagForUser(final Player player) {
        TagHolder result = null;

        for (final String key : TAG_REGISTRY.keySet()) {
            final TagHolder holder = TAG_REGISTRY.get(key);

            if (!player.hasPermission(holder.getPermission())) {
                continue;
            }

            if (player.hasPermission(String.format("tag.active.%s", key))) {
                result = holder;
                break;
            }
        }

        return result;
    }

}
