package frosty.op65n.tech.tagsspigot;

import frosty.op65n.tech.tagsspigot.command.ReloadCommand;
import frosty.op65n.tech.tagsspigot.command.TagMenuCommand;
import frosty.op65n.tech.tagsspigot.database.Database;
import frosty.op65n.tech.tagsspigot.listener.JoinListener;
import frosty.op65n.tech.tagsspigot.placeholder.TagPlaceholder;
import frosty.op65n.tech.tagsspigot.storage.TagRegistry;
import frosty.op65n.tech.tagsspigot.util.FileUtil;
import me.mattstudios.mf.base.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.CompletableFuture;

public final class TagsPlugin extends JavaPlugin {

    private final TagRegistry registry = new TagRegistry();

    @Override
    public void onEnable() {
        Database.masterWorkerID = Thread.currentThread().getId();

        FileUtil.saveResources(
                "config.yml",
                "tags-menu.yml",
                "hikari-settings.yml"
        );

        getServer().getPluginManager().registerEvents(new JoinListener(), this);

        final CommandManager manager = new CommandManager(this);
        manager.register(
                new TagMenuCommand(this),
                new ReloadCommand(this)
        );

        new TagPlaceholder(this.registry).register();

        CompletableFuture.supplyAsync(() -> {
            new Database().createAdapter();

            registry.load(this);
            return null;
        }).thenRun(registry::request);
    }

    @Override
    public void onDisable() {
        super.onDisable();

        CompletableFuture.supplyAsync(() -> {
            Database.INSTANCE.terminateAdapter();
            return null;
        }).join();
    }

    public TagRegistry getRegistry() {
        return this.registry;
    }

}
