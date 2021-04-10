package frosty.op65n.tech.tagsspigot.command;

import frosty.op65n.tech.tagsspigot.TagsPlugin;
import frosty.op65n.tech.tagsspigot.util.TaskUtil;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.command.CommandSender;

@Command("tags")
public final class ReloadCommand extends CommandBase {

    private final TagsPlugin plugin;

    public ReloadCommand(final TagsPlugin plugin) {
        this.plugin = plugin;
    }

    @SubCommand("reload")
    @Permission("tags.command.reload")
    public void onReload(final CommandSender sender) {
        TaskUtil.async(() -> {
            plugin.getRegistry().request(0);

            sender.sendMessage("Gawk Gawk Requested updated config from Database.");
        });
    }

    @SubCommand("load")
    @Permission("tags.command.load")
    public void onLoad(final CommandSender sender) {
        TaskUtil.async(() -> {
            plugin.getRegistry().load(plugin);

            sender.sendMessage("Gawk Gawk Loaded Config into Database.");
        });
    }

}
