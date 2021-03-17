package frosty.op65n.tech.tagsspigot;

import frosty.op65n.tech.tagsspigot.command.TagMenuCommand;
import frosty.op65n.tech.tagsspigot.database.Database;
import frosty.op65n.tech.tagsspigot.listener.ConfigurationReloadListener;
import frosty.op65n.tech.tagsspigot.placeholder.TagPlaceholder;
import frosty.op65n.tech.tagsspigot.storage.TagRegistry;
import frosty.op65n.tech.tagsspigot.util.FileUtil;
import me.mattstudios.mf.base.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class TagsPlugin extends JavaPlugin {

    private final TagRegistry registry = new TagRegistry();

    @Override
    public void onEnable() {
        Database.masterWorkerID = Thread.currentThread().getId();

        FileUtil.saveResources(
                "tags-menu.yml"
        );

        getServer().getMessenger().registerIncomingPluginChannel(
                this, "BungeeCord",
                new ConfigurationReloadListener(this.registry)
        );

        final CommandManager manager = new CommandManager(this);
        manager.register(
                new TagMenuCommand(this)
        );

        new TagPlaceholder(this.registry).register();

        TaskUtil.async(() -> new Database().createAdapter());
    }

    @Override
    public void onDisable() {
        super.onDisable();
   
        new CompletableFuture(() -> 
          Database.INSTANCE.terminateAdapter()
        ).join();
    }

    public TagRegistry getRegistry() {
        return this.registry;
    }

}
