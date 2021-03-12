package frosty.op65n.tech.tagsspigot.placeholder;

import frosty.op65n.tech.tagsspigot.storage.TagRegistry;
import frosty.op65n.tech.tagsspigot.storage.impl.TagHolder;
import frosty.op65n.tech.tagsspigot.util.HexUtil;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public final class TagPlaceholder extends PlaceholderExpansion {

    private final TagRegistry registry;

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
        if (holder == null) {
            return "";
        }

        switch (params.toLowerCase()) {
            case "display" -> {
                return holder.getDisplay();
            }
            case "display-colored" -> {
                return HexUtil.colorify(holder.getDisplay());
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
