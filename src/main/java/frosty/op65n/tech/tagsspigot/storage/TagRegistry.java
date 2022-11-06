package frosty.op65n.tech.tagsspigot.storage;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import frosty.op65n.tech.tagsspigot.struct.TagHolder;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.MetaNode;
import net.luckperms.api.node.types.PermissionNode;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public final class TagRegistry {

    private static final Cache<UUID, TagHolder> ACTIVE_DATA = CacheBuilder.newBuilder().build();
    private static final LuckPerms LUCK_PERMS = LuckPermsProvider.get();

    public static Set<TagHolder> requestTagsForUser(final Player player) {
        final LuckPerms luckPerms = LuckPermsProvider.get();
        final User user = luckPerms.getUserManager().getUser(player.getUniqueId());
        if (user == null)
            throw new RuntimeException("Retrieved invalid User from LuckPerms (Not Loaded)");

        final Set<PermissionNode> tagPermissions = user.getNodes(NodeType.PERMISSION).stream()
                .filter(it -> it.getPermission().startsWith("tag"))
                .collect(Collectors.toSet());

        return tagPermissions.stream().map(it -> TagHolder.fromPermission(it.getPermission())).collect(Collectors.toSet());
    }

    public static TagHolder requestActiveTagForUser(final Player player) {
        final TagHolder active = ACTIVE_DATA.getIfPresent(player.getUniqueId());
        if (active != null) {
            return active;
        }

        final CachedMetaData data = LUCK_PERMS.getPlayerAdapter(Player.class).getMetaData(player);
        final TagHolder created = TagHolder.fromPermission(data.getMetaValue("active_tag", String::valueOf).orElse("empty"));
        ACTIVE_DATA.put(player.getUniqueId(), created);

        return created;
    }

    public static void setActiveTagForUser(final Player player, final TagHolder holder) {
        final LuckPerms luckPerms = LuckPermsProvider.get();
        final User user = luckPerms.getPlayerAdapter(Player.class).getUser(player);

        final MetaNode node = MetaNode.builder("active_tag", holder.permission()).build();

        user.data().clear(NodeType.META.predicate(it -> it.getMetaKey().equals("active_tag")));
        user.data().add(node);

        luckPerms.getUserManager().saveUser(user);
        ACTIVE_DATA.invalidate(player.getUniqueId());
    }

}
