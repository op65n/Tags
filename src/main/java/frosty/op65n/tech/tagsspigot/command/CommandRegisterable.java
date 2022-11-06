package frosty.op65n.tech.tagsspigot.command;

import frosty.op65n.tech.tagsspigot.TagsPlugin;
import frosty.op65n.tech.tagsspigot.command.impl.TagMenuCommand;
import frosty.op65n.tech.tagsspigot.registry.Registerable;
import me.mattstudios.mf.base.CommandManager;

public final class CommandRegisterable implements Registerable {

    @Override
    public void enable(final TagsPlugin plugin) {
        final CommandManager manager = new CommandManager(plugin);

        manager.register(
                new TagMenuCommand()
        );
    }

}
