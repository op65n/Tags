package frosty.op65n.tech.tagsspigot.command;

import frosty.op65n.tech.tagsspigot.TagsPlugin;
import frosty.op65n.tech.tagsspigot.util.TaskUtil;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.entity.Player;

@Command("tags")
public final class ReloadCommand extends CommandBase {

    private final TagsPlugin plugin;

    public ReloadCommand(final TagsPlugin plugin) {
        this.plugin = plugin;
    }

    @SubCommand("reload")
    @Permission("tags.command.reload")
    public void onReload(final Player player) {
        TaskUtil.async(() -> {
            plugin.getRegistry().request();
        });
    }

}
