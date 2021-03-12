package frosty.op65n.tech.tagsspigot.util;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.Set;

public final class PermissionUtil {

    public static boolean hasPermission(final Player player, final String permission) {
        final Set<PermissionAttachmentInfo> permissions = player.getEffectivePermissions();

        return permissions.stream().anyMatch(it -> it.getPermission().equalsIgnoreCase(permission));
    }

}