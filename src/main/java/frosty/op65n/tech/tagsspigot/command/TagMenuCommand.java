package frosty.op65n.tech.tagsspigot.command;

import frosty.op65n.tech.tagsspigot.TagsPlugin;
import frosty.op65n.tech.tagsspigot.command.menu.TagMenu;
import frosty.op65n.tech.tagsspigot.util.TaskUtil;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.entity.Player;

@Command("tags")
public final class TagMenuCommand extends CommandBase {

    private final TagsPlugin plugin;

    public TagMenuCommand(final TagsPlugin plugin) {
        this.plugin = plugin;
    }

    @Default
    public void onMenuCommand(final Player player) {
        TaskUtil.async(() -> {
            final TagMenu menu = new TagMenu(player, plugin.getRegistry());

            TaskUtil.queue(() -> menu.getMenu().open(player));
        });
    }

}
