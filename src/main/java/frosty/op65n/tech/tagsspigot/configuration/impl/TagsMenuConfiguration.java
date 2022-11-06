package frosty.op65n.tech.tagsspigot.configuration.impl;

import frosty.op65n.tech.tagsspigot.configuration.internal.ConfigManager;
import frosty.op65n.tech.tagsspigot.util.menu.MenuItem;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import java.util.List;

@ConfigSerializable
public final class TagsMenuConfiguration {

    @Setting
    private String menuTitle = "&9| Chat Tags &8(&0{tag_acquired} &8od &0{tag_total}&8)";

    @Setting
    private int menuSize = 54;

    @Setting
    private int pageSize = 36;

    @Setting
    private List<MenuItem> buttons = List.of(new MenuItem());

    @Setting
    private MenuItem tagItem = new MenuItem();

    public String menuTitle() {
        return this.menuTitle;
    }

    public int menuSize() {
        return this.menuSize;
    }

    public int pageSize() {
        return this.pageSize;
    }

    public List<MenuItem> buttons() {
        return this.buttons;
    }

    public MenuItem tagItem() {
        return this.tagItem;
    }

    public static TagsMenuConfiguration get() {
        return ConfigManager.getInstance().getConfig(TagsMenuConfiguration.class);
    }

}
