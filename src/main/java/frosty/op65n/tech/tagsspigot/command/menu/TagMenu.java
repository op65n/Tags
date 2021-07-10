package frosty.op65n.tech.tagsspigot.command.menu;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import frosty.op65n.tech.tagsspigot.placeholder.TagPlaceholder;
import frosty.op65n.tech.tagsspigot.storage.TagRegistry;
import frosty.op65n.tech.tagsspigot.storage.impl.TagHolder;
import frosty.op65n.tech.tagsspigot.util.FileUtil;
import frosty.op65n.tech.tagsspigot.util.HexUtil;
import frosty.op65n.tech.tagsspigot.util.ReplaceUtil;
import frosty.op65n.tech.tagsspigot.util.TaskUtil;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.op65n.gazelle.api.connection.ConcurrentConnection;
import org.op65n.gazelle.api.holder.DataSource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

@SuppressWarnings("ConstantConditions")
public final class TagMenu {

    private final FileConfiguration configuration = FileUtil.getConfiguration("tags-menu.yml");
    private final Player player;
    private final TagRegistry registry;

    private final PaginatedGui menu;

    public TagMenu(final Player player, final TagRegistry registry) {
        this.player = player;
        this.registry = registry;

        this.menu = construct();
    }

    private PaginatedGui construct() {
        final Map<String, TagHolder> tags = this.registry.getTagsForUser(this.player);

        final PaginatedGui gui = new PaginatedGui(
                this.configuration.getInt("menu-size") / 9,
                this.configuration.getInt("page-size"),
                HexUtil.colorify(ReplaceUtil.replaceString(
                        this.configuration.getString("menu-title"),
                        "{tag_acquired}", tags.size(),
                        "{tag_total}", registry.getTagRegistry().size()
                ))
        );

        gui.setDefaultClickAction(event -> event.setCancelled(true));

        final ConfigurationSection itemsSection = this.configuration.getConfigurationSection("items");
        if (itemsSection != null) {
            for (final String key : itemsSection.getKeys(false)) {
                final ConfigurationSection itemSection = itemsSection.getConfigurationSection(key);
                if (itemSection == null) {
                    continue;
                }

                final Material material = Material.matchMaterial(itemSection.getString("material"));
                final ItemBuilder builder = ItemBuilder.from(material);

                builder.setName(HexUtil.colorifyAndParse(itemSection.getString("display"), player));
                builder.setLore(HexUtil.colorifyAndParse(itemSection.getStringList("lore"), player));

                final String action = itemSection.getString("action", "NONE").toUpperCase();
                final GuiItem item = new GuiItem(builder.build(), event -> {
                    final Player viewer = (Player) event.getWhoClicked();

                    switch (action) {
                        case "NEXT_PAGE" -> gui.next();
                        case "PREVIOUS_PAGE" -> gui.previous();
                        case "CLOSE" -> gui.close(viewer);
                        case "UNSELECT_TAG" -> TaskUtil.async(() -> {
                            update(player, "");
                            gui.close(player);
                        });
                    }
                });

                if (itemSection.get("slot") != null) gui.setItem(itemSection.getInt("slot"), item);
                else gui.setItem(itemSection.getIntegerList("slots"), item);
            }
        }

        final ConfigurationSection tagSection = this.configuration.getConfigurationSection("tag-item");
        for (final String key : tags.keySet()) {
            final TagHolder holder = tags.get(key);

            final Material material = Material.matchMaterial(tagSection.getString("material"));
            final ItemBuilder builder = ItemBuilder.from(material);

            builder.setName(HexUtil.colorify(ReplaceUtil.replaceString(
                    tagSection.getString("display"),
                    "{tag_identifier}", key,
                    "{tag_display}", holder.getDisplay(),
                    "{tag_description}", holder.getDescription(),
                    "{tag_permission}", holder.getPermission()
            )));

            final StringBuilder loreBuilder = new StringBuilder();
            for (final String line : tagSection.getStringList("lore")) {
                loreBuilder.append(ReplaceUtil.replaceString(
                        line,
                        "{tag_identifier}", key,
                        "{tag_display}", holder.getDisplay(),
                        "{tag_description}", holder.getDescription(),
                        "{tag_permission}", holder.getPermission()
                )).append("\n");
            }

            builder.setLore(HexUtil.colorify(Arrays.asList(loreBuilder.toString().split("\n"))));

            final GuiItem item = new GuiItem(builder.build(), event -> {
                TaskUtil.async(() -> update(player, holder.getIdentifier()));
                gui.close(player);
            });

            gui.addItem(item);
        }

        return gui;
    }

    private void update(final Player player, final String tag) {
        try {
            final DataSource dataSource = new ConcurrentConnection().borrow();
            final PreparedStatement updateQuery = dataSource.prepare(
                    "REPLACE INTO tag_registry (player, tag) VALUES (?, ?);"
            );

            updateQuery.setString(1, player.getName());
            updateQuery.setString(2, tag);
            updateQuery.executeQuery();

            dataSource.free();
            TagPlaceholder.cachePlayerTag(player);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public PaginatedGui getMenu() {
        return this.menu;
    }
}
