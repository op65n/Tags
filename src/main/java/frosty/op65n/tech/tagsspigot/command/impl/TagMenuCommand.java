package frosty.op65n.tech.tagsspigot.command.impl;

import frosty.op65n.tech.tagsspigot.command.menu.TagMenu;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.entity.Player;

@Command("tags")
public final class TagMenuCommand extends CommandBase {

    @Default
    public void onMenuCommand(final Player player) {
        final TagMenu menu = new TagMenu(player);

        menu.getMenu().open(player);
    }

}
