package frosty.op65n.tech.tagsspigot;

import frosty.op65n.tech.tagsspigot.command.ReloadCommand;
import frosty.op65n.tech.tagsspigot.command.TagMenuCommand;
import frosty.op65n.tech.tagsspigot.database.tables.TableConfigRegistry;
import frosty.op65n.tech.tagsspigot.database.tables.TableTagRegistry;
import frosty.op65n.tech.tagsspigot.listener.JoinListener;
import frosty.op65n.tech.tagsspigot.placeholder.TagPlaceholder;
import frosty.op65n.tech.tagsspigot.storage.TagRegistry;
import frosty.op65n.tech.tagsspigot.util.FileUtil;
import frosty.op65n.tech.tagsspigot.util.TaskUtil;
import me.mattstudios.mf.base.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.op65n.gazelle.Gazelle;
import org.op65n.gazelle.configuration.GazelleConfiguration;
import org.op65n.gazelle.configuration.impl.TomlGazelleConfiguration;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public final class TagsPlugin extends JavaPlugin {

    private final TagRegistry registry = new TagRegistry();

    @Override
    public void onEnable() {
        FileUtil.saveResources(
                "config.yml",
                "tags-menu.yml",
                "hikari-settings.toml"
        );

        getServer().getPluginManager().registerEvents(new JoinListener(), this);

        final CommandManager manager = new CommandManager(this);
        manager.register(
                new TagMenuCommand(this),
                new ReloadCommand(this)
        );

        new TagPlaceholder(this.registry).register();

        final GazelleConfiguration configuration = new TomlGazelleConfiguration(getDataFolder() + "/hikari-settings.toml");
        final Gazelle gazelle = new Gazelle(Thread.currentThread(), configuration);
        TaskUtil.async(() -> {
            gazelle.registerTables(
                    new TableConfigRegistry(), new TableTagRegistry()
            );

            try {
                gazelle.start();
            } catch (final SQLException exception) {
                exception.printStackTrace();
            }

            registry.load(this);
            registry.request(0);
        });
    }

    @Override
    public void onDisable() {
        super.onDisable();

        CompletableFuture.supplyAsync(() -> {
            Gazelle.stop();
            return null;
        }).join();

        reloadConfig();
    }

    public TagRegistry getRegistry() {
        return this.registry;
    }

}
