package frosty.op65n.tech.tagsspigot.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import frosty.op65n.tech.tagsspigot.storage.TagRegistry;
import frosty.op65n.tech.tagsspigot.storage.impl.TagHolder;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

@SuppressWarnings("UnstableApiUsage")
public final class ConfigurationReloadListener implements PluginMessageListener {

    private final TagRegistry registry;

    public ConfigurationReloadListener(final TagRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void onPluginMessageReceived(final String channel, final Player player, final byte[] message) {
        if (!channel.equalsIgnoreCase("BungeeCord")) {
            return;
        }

        final ByteArrayDataInput input = ByteStreams.newDataInput(message);
        final String subChannel = input.readUTF();

        if (!subChannel.equalsIgnoreCase("tag-recipient")) {
            return;
        }

        final String identifier = input.readUTF();
        final TagHolder holder = new TagHolder(
            identifier, input.readUTF(), input.readUTF(), input.readUTF()
        );

        this.registry.updateRegistry(identifier, holder);
    }

}
