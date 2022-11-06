package frosty.op65n.tech.tagsspigot.command.menu;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.InteractionModifier;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import frosty.op65n.tech.tagsspigot.configuration.impl.TagsMenuConfiguration;
import frosty.op65n.tech.tagsspigot.storage.TagRegistry;
import frosty.op65n.tech.tagsspigot.struct.TagHolder;
import frosty.op65n.tech.tagsspigot.util.ColorUtil;
import frosty.op65n.tech.tagsspigot.util.ReplaceUtil;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.stream.Collectors;

public final class TagMenu {

    private final Player player;
    private final PaginatedGui menu;

    public TagMenu(final Player player) {
        this.player = player;

        this.menu = construct();
    }

    private PaginatedGui construct() {
        final TagsMenuConfiguration configuration = TagsMenuConfiguration.get();
        final Set<TagHolder> tags = TagRegistry.requestTagsForUser(player);

        final PaginatedGui gui = new PaginatedGui(
                configuration.menuSize() / 9,
                configuration.pageSize(),
                ColorUtil.parseS(ReplaceUtil.replaceString(
                        configuration.menuTitle(),
                        "{tag_acquired}", tags.size(),
                        "{tag_total}", 2
                )),
                InteractionModifier.VALUES
        );

        configuration.buttons().forEach(button -> {
            final ItemBuilder builder = ItemBuilder.from(button.material());

            builder.name(ColorUtil.parse(button.display()));
            builder.lore(button.lore().stream().map(ColorUtil::parse).collect(Collectors.toList()));

            final GuiItem item = new GuiItem(builder.build(), event -> {
                final Player viewer = (Player) event.getWhoClicked();

                switch (button.action()) {
                    case "NEXT_PAGE" -> gui.next();
                    case "PREVIOUS_PAGE" -> gui.previous();
                    case "CLOSE" -> gui.close(viewer);
                    case "UNSELECT_TAG" -> {
                        TagRegistry.setActiveTagForUser(player, TagHolder.empty());
                        gui.close(player);
                    }
                }
            });

            button.slots().forEach(it -> gui.setItem(it, item));
        });

        for (final TagHolder tag : tags) {
            final ItemBuilder builder = ItemBuilder.from(tag.icon());

            builder.name(ColorUtil.parse(tag.display(), "{tag_display}", tag.display(), "{tag_description}", tag.description()));
            builder.lore(ColorUtil.parse(tag.description(), "{tag_display}", tag.display(), "{tag_description}", tag.description()));

            final GuiItem item = new GuiItem(builder.build(), event -> {
                TagRegistry.setActiveTagForUser(player, tag);
                gui.close(player);
            });

            gui.addItem(item);
        }

        return gui;
    }

    public PaginatedGui getMenu() {
        return this.menu;
    }
}
