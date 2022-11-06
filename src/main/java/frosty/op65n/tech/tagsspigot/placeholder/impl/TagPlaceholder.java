package frosty.op65n.tech.tagsspigot.placeholder.impl;

import frosty.op65n.tech.tagsspigot.storage.TagRegistry;
import frosty.op65n.tech.tagsspigot.struct.TagHolder;
import frosty.op65n.tech.tagsspigot.util.HexUtil;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class TagPlaceholder extends PlaceholderExpansion {

    @Override
    public @NotNull String getVersion() {
        return "1.0.1";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Frcsty";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "tag";
    }

    @Override
    public String onPlaceholderRequest(final Player player, final String params) {
        final TagHolder active = TagRegistry.requestActiveTagForUser(player);
        if (params.equalsIgnoreCase("space")) {
            return !active.isEmpty() ? " " : "";
        }

        switch (params.toLowerCase()) {
            case "display" -> {
                return active.display();
            }
            case "display-colored" -> {
                return HexUtil.colorify(active.display());
            }
            case "description" -> {
                return active.descriptionS();
            }
            default -> {
                return "";
            }
        }
    }

}
